(*

ERRORI: APPLY nelle visit non va bene, è sintassi che deve essere rivalutata tutte le volte, mettere direttamente la funzione valutata
*)


(*  Leonardo Vona
	545042
	06/02/2020
*)
type ide = string;;

(*Tipi di dato per il type checker statico *)
type tval = 
	| Tint 
	| Tbool 
	| Tdict
	| FunvalT of tval * tval
	| Fun2ValT of tval * tval * tval
	| RecFunValT of tval * tval;;

type exp = Eint of int
			| Ebool of bool
			| Sum of exp * exp
			| Sub of exp * exp
			| Times of exp * exp
			| Equal of exp * exp
			| Iszero of exp
			| Or of exp * exp
			| And of exp * exp
			| Not of exp 
			| Ifthenelse of exp * exp * exp
			| Let of ide * exp * exp
			| Den of ide
			| Fun of ide * tval * exp (* tval necessario per type checker statico *)
			| Fun2 of ide * ide * tval * tval * exp
			| Apply of exp * exp
			| Apply2 of exp * exp * exp
			| LetRec of ide * exp * exp
			| Edict of dict
			| Insert of ide * exp * exp
			| Delete of ide * exp
			| Has_key of ide * exp
			| Iterate of exp * exp
			| Fold of exp * exp
			| Filter of (ide list) * exp
			and dict = 
				|Empty 
				|Elem of ide * exp * dict;;

type 't env = ide -> 't;;
let emptyenv (v : 't) = function x -> v;;
let applyenv (r : 't env) (i : ide) = r i;;
let bind (r : 't env) (i : ide) (v : 't) = 
	function x -> if x = i then v else applyenv r x;;

type tenv = ide -> tval;;
	
let tenv0 = fun (x: ide) -> failwith "Empty env";;

type evT = 
		| Int of int
		| Bool of bool
		| FunVal of ide * exp * evT env
		| Fun2Val of ide * ide * exp * evT env
		| RecFunVal of ide * ide * exp * evT env
		| Dict of dict 
		| Unbound;;
	

let typecheck (s : string) (v : evT) : bool = match s with
							|"int" -> (match v with
										|Int(_) -> true
										| _ -> false)
							|"bool" -> (match v with
										|Bool(_) -> true
										| _ -> false)
							|"dict" -> (match v with
										|Dict(_) -> true
										| _ -> false)
							
							| _ -> failwith ("Not a valid type");;


let cast (n : evT) : exp = match n with
	|Int(x) -> Eint x
	|Bool(b) -> Ebool b 
	|Dict(x) -> Edict x
	|_ -> failwith("not valid cast");;

let rec isIn (i: ide) (l: ide list): bool = match l with
	| [] -> false
	| x::xs -> if i = x then true else (isIn i xs)

and iterateVisit (d:dict) (f:exp) (r:'v env): dict = match d with
	| Empty -> Empty
	| Elem(i, e, ds) -> 
		Elem(i, cast(eval (Apply(f, e)) r), iterateVisit ds f r)
		
and has_keyVisit (d:dict) (k:ide): evT = match d with
	| Empty -> Bool(false)
	| Elem(i, e, ds) -> if i = k then Bool(true) else (has_keyVisit ds k)
	
and deleteVisit (prev: dict) (d:dict) (k:ide): dict = match d with
	| Empty -> Empty
	| Elem(i, e, ds) -> 
		if i = k then
			ds
		else
			let elem = Elem(i, e ,ds) in
			Elem(i, e, (deleteVisit elem ds k))

and foldApply (f:exp) (s:exp) (d:dict) (r:'v env): exp = match d with
	| Empty -> s
	| Elem(i, e, ds) -> let v = (foldApply f s ds r) in
		let a = Apply2(f, v, e) in
			cast (eval a r)
		
and filterApply (prev:dict) (l:ide list) (d:dict): dict = match d with
	| Empty -> Empty
	| Elem(i, e, ds) -> 
		if (isIn i l) then
			let elem = Elem(i, e, ds) in
			Elem(i, e, (filterApply elem l ds))
		else
			(filterApply prev l ds)

and eval (e : exp) (r: 'v env) : evT =
		match e with
			|Eint(n) -> Int(n)
			|Ebool(b)-> Bool(b)
			|Sum(e1, e2) ->	let v1 = (eval e1 r) in
								let v2 = (eval e2 r) in (
									match(typecheck "int"  v1 , typecheck "int" v2, v1, v2) with
										|(true, true, Int(u), Int(v)) -> Int(u + v)
										|(_,_,_,_) -> failwith("Type error") )
			
			|Sub(e1, e2) -> let v1 = (eval e1 r) in
								let v2 = (eval e2 r) in (
									match(typecheck "int"  v1 , typecheck "int" v2, v1, v2) with
										|(true, true, Int(u), Int(v)) -> Int(u - v)
										|(_,_,_,_) -> failwith("Type error") )
			
			|Times(e1, e2) -> let v1 = (eval e1 r) in
									let v2 = (eval e2 r) in (
										match(typecheck "int" v1 , typecheck "int"  v2, v1, v2) with
										|(true, true, Int(u), Int(v)) -> Int(u * v)
										|(_,_,_,_) -> failwith("Type error") )

			|Equal(e1, e2) -> let v1 = (eval e1 r) in
									let v2 = (eval e2 r) in (
										match (typecheck "int" v1, typecheck "int" v2, v1, v2) with
										|(true, true, Int(u), Int(v)) -> Bool(u = v)
										|(_,_,_,_) -> failwith("Type error") )

			|Iszero(e) -> let v = (eval e r) in (
										match (typecheck "int" v, v) with
										|(true, Int(u)) -> Bool(u = 0)
										|(_,_) -> failwith("Type error") )


			|Or(e1, e2) -> let v1 = (eval e1 r) in 
									let v2 = (eval e2 r) in (
										match (typecheck "bool"  v1 , typecheck "bool" v2, v1, v2) with
										|(true, true, Bool(u), Bool(v)) -> Bool(u || v)
										|(_,_,_,_) -> failwith("Type error") )

			|And(e1, e2) -> let v1 = (eval e1 r) in
									let v2 = (eval e2 r) in (
										match (typecheck "bool" v1, typecheck "bool" v2, v1, v2) with
										|(true, true, Bool(u), Bool(v)) -> Bool(u && v)
										|(_,_,_,_) -> failwith("Type error") )

			|Not(e) -> let v = (eval e r) in (
								match (typecheck "true" v, v) with
								|(true, Bool(u)) -> Bool(not(u))
								|(_,_) -> failwith("Type error") )

			
			|Ifthenelse(e1, e2, e3) -> let g = (eval e1 r) in (
												match (typecheck "bool" g , g) with
												|(true, Bool(true)) -> eval e2 r
												|(true, Bool(false)) -> eval e3 r
												|(_,_) -> failwith("Guard isn't a boolean") )

			|Let(i, e, ebody) -> eval ebody (bind r i (eval e r))

			|Den(i) -> applyenv r i

			|Fun(arg, argT, ebody) -> FunVal(arg, ebody, r)
			
			|Fun2(arg1, arg2, arg1T, arg2T, ebody) -> Fun2Val(arg1, arg2, ebody, r)

			|Apply(f, eArg) ->
						let fclosure = (eval f r) in
							(match fclosure with
								|FunVal(arg, fbody, fDecEnv) -> eval fbody (bind fDecEnv arg (eval eArg r) )
								|RecFunVal(g, arg, fbody, fDecEnv) -> 
									let aVal = (eval eArg r) in
										let rEnv = (bind fDecEnv g fclosure) in
											let aenv = (bind rEnv arg aVal) in
												eval fbody aenv
								| _ -> failwith("It isn't a functional value") )
			
			|Apply2(f, eArg1, eArg2) ->
						let fclosure = (eval f r) in
							(match fclosure with
								|Fun2Val(arg1, arg2, fbody, fDecEnv) -> 
									let r1 = bind fDecEnv arg1 (eval eArg1 r) in
									let r2 = bind r1 arg2 (eval eArg2 r) in
									eval fbody r2
								| _ -> failwith("It isn't a functional value") )

			| LetRec(f, funDef, letbody) ->
							(match funDef with
								|Fun(i, iT, fbody) -> let r1 = (bind r f (RecFunVal(f, i, fbody,r))) in
															eval letbody r1
								| _ -> failwith("It isn't a functional def"))

			| Edict(t) -> Dict(t) 
			
			| Has_key(k, d) -> let vd = (eval d r) in
				(match vd with
					| Dict(Elem(i, e, ds)) -> let elem = Elem(i, e, ds) in
						has_keyVisit elem k
					| Dict(Empty) -> Bool(false)
					| _ -> failwith "Argument passed is not a dictionary")
					
			| Insert(k, e, d) -> let vd = (eval d r) in
				(match vd with
					| Dict(Empty) -> Dict(Elem(k, e, Empty))
					| Dict(Elem(i, e1, ds)) -> let elem = Elem(i, e1, ds) in
						(match (has_keyVisit elem k) with
							| Bool(true) -> failwith "Key already present"
							| Bool(false) -> Dict(Elem(k, e, Elem(i, e1, ds) ) ) 
							| _ -> failwith "Error" )
					| _ -> failwith "Argument passed is not a dictionary")
				
			| Delete(k, d) -> let vd = (eval d r) in
				(match vd with
					| Dict(Empty) -> Dict(Empty)
					| Dict(Elem(i, e, ds)) -> let elem = Elem(i, e, ds) in
						let prev = Empty in
						 Dict(deleteVisit prev elem k) 
					| _ -> failwith "Argument passed is not a dictionary")
				
			| Iterate(f, d) -> let vd = (eval d r) in
					(match vd with
						| Dict(Elem(i, e, ds)) -> let elem = Elem(i, e, ds) in
							Dict(iterateVisit elem f r)
						| _ -> failwith "Argument passed is not a dictionary")
	
			| Fold(f, d) -> let vd = (eval d r) in
				(match vd with
					| Dict(Elem(i, Eint(e), ds)) -> let elem = Elem(i, Eint(e), ds) in
						let s = Eint(0) in
						eval (foldApply f s elem r) r
						
					| Dict(Elem(i, Ebool(e), ds)) -> let elem = Elem(i, Ebool(e), ds) in
						let s = Ebool(false) in
						eval (foldApply f s elem r) r	
						
					| Dict(Elem(i, Edict(e), ds)) -> let elem = Elem(i, Edict(e), ds) in
						let s = Edict(Empty) in
						eval (foldApply f s elem r) r	
						
					| _ -> failwith "Argument passed is not a valid dictionary")

			| Filter(l, d) -> let vd = (eval d r) in
					(match vd with
						| Dict(Elem(i, e, ds)) -> let elem = Elem(i, e, ds) in
							let prev = Empty in
								Dict(filterApply prev l elem)
						| Dict(Empty) -> Dict(Empty)
						| _ -> failwith "Argument passed is not a dictionary");;
	
(* type checker statico *)	
let rec teval e tenv = match e with
	| Eint i -> Tint
	
	| Ebool b -> Tbool
	
	| Den s -> tenv s
	
	| Sum (e1,e2) -> let t1 = teval e1 tenv in
		let t2 = teval e2 tenv in
		( match (t1, t2) with
			| (Tint, Tint) -> Tint
			| (_,_) -> failwith "Type error" )
			
	| Sub (e1, e2) -> let t1 = teval e1 tenv in
		let t2 = teval e2 tenv in
		( match (t1, t2) with
			| (Tint, Tint) -> Tint
			| (_,_) -> failwith "Type error" )
			
	| Times (e1,e2) -> let t1 = teval e1 tenv in
		let t2 = teval e2 tenv in
		( match (t1, t2) with
			| (Tint, Tint) -> Tint
			| (_,_) -> failwith "Type error" )
			
	| Iszero (e) -> let t = teval e tenv in
		(match t with
			| Tint -> Tbool
			| _ -> failwith "Type error")
			
	| Not (e) -> let t1 = teval e tenv in
		( match t1 with
			| Tbool -> Tbool
			| _ -> failwith "Type error" )
			
	| And (e1, e2) -> let t1 = teval e1 tenv in
		let t2 = teval e2 tenv in
		( match (t1, t2) with
			| (Tbool, Tbool) -> Tbool
			| (_,_) -> failwith "Type error" )
			
	| Or (e1, e2) -> let t1 = teval e1 tenv in
		let t2 = teval e2 tenv in
		( match (t1, t2) with
			| (Tbool, Tbool) -> Tbool
			| (_,_) -> failwith "Type error" )
			
	| Ifthenelse (e,e1,e2) -> let t = teval e tenv in
		( match t with
			| Tbool -> let t1 = teval e1 tenv in
				let t2 = teval e2 tenv in
				( match (t1, t2) with
					| (Tint, Tint) -> Tint
					| (Tbool, Tbool) -> Tbool
					| (_,_) -> failwith "Type error" )
			| _ -> failwith "Type error" )
			
	| Equal (e1, e2) ->
		if ((teval e1 tenv) = (teval e2 tenv)) then
			Tbool
		else 
			failwith "Type error"
			
	| Let(i, e1, e2) -> let t = teval e1 tenv in
		teval e2 (bind tenv i t)
	
	| LetRec(f, fdef, letbody) ->  (match fdef with
			|Fun (i, t1, e) -> let tenv1 = bind tenv i t1 in
				let t2 = teval e tenv1 in
					RecFunValT(t1, t2)
			| _ -> failwith "Type error")
	
	| Fun(i, t1, e) -> let tenv1 = bind tenv i t1 in
		let t2 = teval e tenv1 in FunvalT(t1,t2)
		
	| Fun2(i, j, t1, t2, e) -> let tenv1 = bind tenv i t1 in
		let tenv2 = bind tenv1 j t2 in
			let t3 = teval e tenv2 in Fun2ValT(t1,t2,t3)
	
	| Apply(e1, e2) -> let f = teval e1 tenv in
		( match f with
			| FunvalT(t1,t2) -> if ((teval e2 tenv) = t1 ) then 
					t2 
				else 
					failwith "Type error"
			| RecFunValT(t1, t2) -> if ((teval e2 tenv) = t1 ) then
					t2
				else failwith "Type error"
			| _ -> failwith "Type error" )
	
	| Apply2(e1, e2, e3) -> let f = teval e1 tenv in
		( match f with
			| Fun2ValT(t1,t2,t3) -> let et1 = teval e2 tenv in
				let et2 = teval e3 tenv in
				if (et1 = t1) && (et2 = t2) then 
					t3 
				else 
					failwith "Type error"
			| _ -> failwith "Type error" )
			
	| Edict d -> Tdict
	
	| Insert(k, e, d) -> let td = (teval d tenv) in
					(match td with
						| Tdict -> Tdict
						| _ -> failwith "Type error")
						
	| Delete(k , d) -> let td = (teval d tenv) in
					(match td with
						| Tdict -> Tdict
						| _ -> failwith "Type error")
						
	| Has_key(k, d) -> let td = (teval d tenv) in
					(match td with
						| Tdict -> Tbool
						| _ -> failwith "Type error")
						
	| Iterate(f, d) -> let td = (teval d tenv) in
					(match td with
						| Tdict -> (match (teval f tenv) with
							| FunvalT(t1, t2) -> Tdict
							| _ -> failwith "Type error")
						| _ -> failwith "Type error")
						
	| Fold(f, d) -> let td = (teval d tenv) in
					(match td with
						| Tdict -> (match (teval f tenv) with
							| FunvalT(t1, t2) -> t2
							| Fun2ValT(t1, t2, t3) -> t3
							| _ -> failwith "Type error")
						| _ -> failwith "Type error")
						
	| Filter(l, d) -> let td = (teval d tenv) in
					(match td with
						| Tdict -> Tdict
						| _ -> failwith "Type error");;
			
(*TEST*)

let env0 = emptyenv Unbound ;;

(* Test Iterate(fun, dict) *)
let dict1 = Edict(Elem("a", Eint(1), Elem("b", Eint(2), Elem("c", Eint(3), Elem("d", Eint(4), Empty)))));;
let f1 = Fun("x", Tint, Sum(Den "x", Eint(2)));;
let iter = Iterate(f1, dict1);;
eval iter env0;;
teval iter tenv0;;

(* Test has_key(key, dict) *)
let hk1 = Has_key("a", dict1);;
eval hk1 env0;;
teval hk1 tenv0;;

let hk2 = Has_key("b", dict1);;
eval hk2 env0;;
teval hk2 tenv0;;

let hk3 = Has_key("c", dict1);;
eval hk3 env0;;
teval hk3 tenv0;;

let hk4 = Has_key("d", dict1);;
eval hk4 env0;;
teval hk4 tenv0;;

let hk5 = Has_key("e", dict1);;
eval hk5 env0;;
teval hk5 tenv0;;

(* Test insert(key, exp, dict) *)
let ins1 = Insert("e", Eint(6), dict1);;
let dict2 = eval ins1 env0;;
teval ins1 tenv0;;

let ins2 = Insert("e", Eint(7), cast dict2);;
eval ins2 env0;;
teval ins2 tenv0;;

let ins3 = Insert("f", Eint(6), cast dict2);;
eval ins3 env0;;
teval ins3 tenv0;;


(* Test delete(key, dict) *)
let del1 = Delete("a", dict1);;
let dict2 = eval del1 env0;;
teval del1 tenv0;;

let del2 = Delete("a", cast dict2);;
eval del2 env0;;
teval del2 tenv0;;

let del3 = Delete("d", dict1);;
eval del3 env0;;
teval del3 tenv0;;

let del4 = Delete("c", dict1);;
eval del4 env0;;
teval del4 tenv0;;

(* Test Filter(key list, dict) *)
let keylist = ["a"; "c"];;
let fil1 = Filter(keylist, dict1);;
let dict2 = eval fil1 env0;;
teval fil1 tenv0;;

let keylist = ["a"; "d"];;
let fil2 = Filter(keylist, cast dict2);;
eval fil2 env0;;
teval fil2 tenv0;;

let fil3 = Filter(["a"; "b"; "c"; "d"], dict1);;
let dict4 = eval fil3 env0;;
teval fil3 tenv0;;

let fil4 = Filter([], dict1);;
eval fil4 env0;;
teval fil4 tenv0;;

(* Test Fold(fun, dict) *)
let f = Fun2("acc", "x", Tint, Tint, Sum(Den("acc"), Sum(Den("x"), Eint(1))));;
let dict = Edict(Elem("a", Eint(430), Elem("b", Eint(312), Elem("c", Eint(525), Elem("d", Eint(217), Empty)))));;
let fol = Fold(f, dict);;
eval fol env0;;
teval fol tenv0;;

let f = Fun2("acc", "x", Tbool, Tbool, Or(Den("acc"), Den("x")));;
let dict = Edict(Elem("a", Ebool(true), Elem("b", Ebool(false), Empty)));;
let fol = Fold(f, dict);;
eval fol env0;;
teval fol tenv0;;
