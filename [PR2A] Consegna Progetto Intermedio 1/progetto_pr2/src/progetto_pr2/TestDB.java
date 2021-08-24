/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2;

import java.util.Iterator;
import java.util.List;

import progetto_pr2.exception.AuthenticationFailedException;
import progetto_pr2.exception.UnvalidCategoryException;
import progetto_pr2.exception.UnvalidDataException;
import progetto_pr2.exception.UnvalidFriendException;

//classe che implementa una batteria di test per DataBoard
public class TestDB {
	private DataBoard<DataString> db;

	public TestDB(int impl) {	//il parametro indica l'implementazione che si vuole testare
		try {
			if (impl == 0) { // implementazione 1
				db = new DataBoard1<>("utente", "password");
			} else { // implementazione2
				db = new DataBoard2<>("utente", "password");
			}
		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
		} catch (UnvalidDataException e) {
			System.err.println(e.getMessage());
		}
	}

	public boolean test() {
		//si effettua un test per ogni metodo pubblico dell'interfaccia, 
		//oltre a verificare che la deep clone sia implementata correttamente
		if (!testCreateCategory()) {
			System.err.println("Error while testCreateCategory");
			return false;
		}

		if (!testRemoveCategory()) {
			System.err.println("Error while testRemoveCategory");
			return false;
		}

		if (!testAddFriend()) {
			System.err.println("Error while testAddFriend");
			return false;
		}

		if (!testRemoveFriend()) {
			System.err.println("Error while testRemoveFriend");
			return false;
		}
		
		if (!testPut()) {
			System.err.println("Error while testPut");
			return false;
		}

		if (!testGet()) {
			System.err.println("Error while testGet");
			return false;
		}

		if (!testRemove()) {
			System.err.println("Error while testRemove");
			return false;
		}

		if (!testGetDataCategory()) {
			System.err.println("Error while testGetDataCategory");
			return false;
		}
		
		if (!testGetIterator()) {
			System.err.println("Error while testGetIterator");
			return false;
		}
		
		if (!testInsertLike()) {
			System.err.println("Error while testInsertLike");
			return false;
		}
		
		if (!testGetFriendIterator()) {
			System.err.println("Error while testGetFriendIterator");
			return false;
		}

		if (!testCSize()) {
			System.err.println("Error while testCSize");
			return false;
		}

		if (!testDeepClone()) {
			System.err.println("Error while testDeepClone");
			return false;
		}

		System.out.println("Test successful");
		return true;
	}

	public boolean checkRep() {
		//prima e dopo l'esecuzione di un metodo dell'interfaccia si controlla se 
		//l'invariante di rappresentazione è mantenuta
		return db.checkRep();
	}
	
	private boolean testCreateCategory() {
		boolean attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.createCategory(null, "password");
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.createCategory("ciao", null);
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.createCategory("ciao", "psw");
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | NullPointerException e) {
			return false;
		} catch(AuthenticationFailedException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		try {
			if(!checkRep()) return false;
			db.createCategory("ciao", "password");
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | NullPointerException | AuthenticationFailedException e) {
			return false;
		}
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.createCategory("ciao", "password");
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | NullPointerException e) {
			return false;
		} catch(UnvalidCategoryException e) {
			attendedBehavior = true;
		}
		
		//ripristina condizioni iniziali
		try {
			db.removeCategory("ciao", "password");
		} catch(UnvalidCategoryException | NullPointerException | AuthenticationFailedException e) {
			return false;
		}
		
		return attendedBehavior;
	}


	private boolean testRemoveCategory() {
		boolean attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.removeCategory(null, "password");
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.removeCategory("ciao", null);
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.removeCategory("ciao", "psw");
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | NullPointerException e) {
			return false;
		} catch(AuthenticationFailedException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.removeCategory("ciao", "password");
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | NullPointerException e) {
			return false;
		} catch(UnvalidCategoryException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		try {
			if(!checkRep()) return false;
			db.createCategory("ciao", "password");
			db.createCategory("ciao2", "password");
			db.removeCategory("ciao", "password");
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | NullPointerException | UnvalidCategoryException e) {}
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.removeCategory("ciao", "password");
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | NullPointerException e) {
			return false;
		} catch(UnvalidCategoryException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		//ripristina condizioni iniziali
		try {
			db.removeCategory("ciao2", "password");
		} catch(UnvalidCategoryException | NullPointerException | AuthenticationFailedException e) {
			return false;
		}
		
		return attendedBehavior;
	}


	private boolean testAddFriend() {
		boolean attendedBehavior;
		
		try {
			db.createCategory("categoria", "password");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e1) {}
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.addFriend(null, "password", "friend");
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException | UnvalidFriendException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.addFriend("categoria", null, "friend");
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException | UnvalidFriendException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.addFriend("categoria", "password", null);
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException | UnvalidFriendException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.addFriend("cat", "password", "friend");
			if(!checkRep()) return false;
		} catch(NullPointerException | AuthenticationFailedException | UnvalidFriendException e) {
			return false;
		} catch(UnvalidCategoryException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.addFriend("categoria", "psw", "friend");
			if(!checkRep()) return false;
		} catch(NullPointerException | UnvalidCategoryException | UnvalidFriendException e) {
			return false;
		} catch(AuthenticationFailedException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		try {
			if(!checkRep()) return false;
			db.addFriend("categoria", "password", "friend");
			if(!checkRep()) return false;
		} catch(NullPointerException | UnvalidCategoryException | UnvalidFriendException | AuthenticationFailedException e) {
			return false;
		}

		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.addFriend("categoria", "password", "friend");
			if(!checkRep()) return false;
		} catch(NullPointerException | UnvalidCategoryException | AuthenticationFailedException e) {
			return false;
		} catch(UnvalidFriendException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		//ripristina condizioni iniziali
		try {
			db.removeCategory("categoria", "password");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e1) {
			return false;
		}
		
		return true;
	}

	private boolean testRemoveFriend() {
		boolean attendedBehavior;
		
		try {
			db.createCategory("categoria", "password");
			db.addFriend("categoria", "password", "friend");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException | UnvalidFriendException e1) {}
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.removeFriend(null, "password", "friend");
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException | UnvalidFriendException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.removeFriend("categoria", null, "friend");
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException | UnvalidFriendException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.removeFriend("categoria", "password", null);
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException | UnvalidFriendException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.removeFriend("cat", "password", "friend");
			if(!checkRep()) return false;
		} catch(NullPointerException | AuthenticationFailedException | UnvalidFriendException e) {
			return false;
		} catch(UnvalidCategoryException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.removeFriend("categoria", "psw", "friend");
			if(!checkRep()) return false;
		} catch(NullPointerException | UnvalidCategoryException | UnvalidFriendException e) {
			return false;
		} catch(AuthenticationFailedException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		try {
			if(!checkRep()) return false;
			db.removeFriend("categoria", "password", "friend");
			if(!checkRep()) return false;
		} catch(NullPointerException | UnvalidCategoryException | UnvalidFriendException | AuthenticationFailedException e) {
			return false;
		}
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.removeFriend("categoria", "password", "friend");
			if(!checkRep()) return false;
		} catch(NullPointerException | UnvalidCategoryException | AuthenticationFailedException e) {
			return false;
		} catch(UnvalidFriendException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		//ripristina condizioni iniziali
		try {
			db.removeCategory("categoria", "password");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e) {
			return false;
		}
		
		return true;
	}
	
	public boolean testPut() {
		boolean attendedBehavior;
		DataString d = new DataString("dato");
		
		try {
			db.createCategory("categoria", "password");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e1) {}
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.put("password", null, "categoria");
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.put(null, d, "categoria");
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.put("password", d, null);
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | AuthenticationFailedException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.put("psw", d, "categoria");
			if(!checkRep()) return false;
		} catch(UnvalidCategoryException | NullPointerException e) {
			return false;
		} catch(AuthenticationFailedException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			boolean put = db.put("password", d, "cat");
			if(put) return false;
			if(!checkRep()) return false;
		} catch(NullPointerException | AuthenticationFailedException e) {
			return false;
		} catch(UnvalidCategoryException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		
		// verifica corretto valore di ritorno put
		try {
			if(!checkRep()) return false;
			boolean put0 = db.put("password", d, "categoria");
			if(!checkRep()) return false;
			boolean put1 = db.put("password", d, "categoria");
			if(!checkRep()) return false;
			if (!put0 || put1) return false;
		} catch (NullPointerException | AuthenticationFailedException | UnvalidCategoryException e) {
			e.printStackTrace();
			return false;
		}
		
		// verifica presenza dei valori inseriti
		try {
			if(!checkRep()) return false;
			db.get("password", d);
			if(!checkRep()) return false;
		} catch (NullPointerException | AuthenticationFailedException | UnvalidDataException e) {
			return false;
		}
		
		// ripristino condizioni iniziali
		try {
			db.removeCategory("categoria", "password");
		} catch (NullPointerException | AuthenticationFailedException | UnvalidCategoryException e) {
			return false;
		}

		return true;
	}

	public boolean testGet() {
		boolean attendedBehavior;
		DataString d = new DataString("dato");
		DataString d1 = new DataString("dato1");
		
		try {
			db.createCategory("categoria", "password");
			db.put("password", d, "categoria");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e1) {}
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.get("password", null);
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | UnvalidDataException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.get(null, d);
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | UnvalidDataException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.get("psw", d);
			if(!checkRep()) return false;
		} catch(NullPointerException | UnvalidDataException e) {
			return false;
		} catch(AuthenticationFailedException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.get("password", d1);
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | NullPointerException e) {
			return false;
		} catch(UnvalidDataException e) {
			attendedBehavior = true;
		}
		
		try {
			if(!checkRep()) return false;
			Data d2 = db.get("password", d);
			if(!checkRep()) return false;
			if(!d2.equals(d)) return false;
		} catch(AuthenticationFailedException | NullPointerException | UnvalidDataException e) {
			return false;
		}
		
		//ripristina condizioni iniziali
		try {
			db.removeCategory("categoria", "password");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e) {}
		
		return true;
	}

	public boolean testRemove() {
		boolean attendedBehavior;
		DataString d = new DataString("dato");
		DataString d1 = new DataString("dato1");
		
		try {
			db.createCategory("categoria", "password");
			db.put("password", d, "categoria");
			db.put("password", d1, "categoria");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e1) {}
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.remove("password", null);
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | UnvalidDataException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.remove(null, d);
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | UnvalidDataException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.remove("psw", d);
			if(!checkRep()) return false;
		} catch(NullPointerException | UnvalidDataException e) {
			return false;
		} catch(AuthenticationFailedException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		try {
			if(!checkRep()) return false;
			Data d2 = db.remove("password", d);
			if(!checkRep()) return false;
			if(!d2.equals(d)) return false;
		} catch(AuthenticationFailedException | NullPointerException | UnvalidDataException e) {
			return false;
		}
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.remove("password", d);
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | NullPointerException e) {
			return false;
		} catch(UnvalidDataException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		try {
			if(!checkRep()) return false;
			Data d2 = db.remove("password", d1);
			if(!checkRep()) return false;
			if(!d2.equals(d1)) return false;
		} catch(AuthenticationFailedException | NullPointerException | UnvalidDataException e) {
			return false;
		}
		
		//ripristina condizioni iniziali
		try {
			db.removeCategory("categoria", "password");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e) {}
		
		return true;
	}
	
	public boolean testGetDataCategory() {
		boolean attendedBehavior;
		DataString d1 = new DataString("dato1");
		DataString d2 = new DataString("dato2");
		DataString d3 = new DataString("dato3");
		DataString d4 = new DataString("dato4");
		DataString d5 = new DataString("dato5");
		
		try {
			db.createCategory("categoria1", "password");
			db.createCategory("categoria2", "password");
			db.put("password", d1, "categoria1");
			db.put("password", d2, "categoria2");
			db.put("password", d3, "categoria1");
			db.put("password", d4, "categoria2");
			db.put("password", d5, "categoria1");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e1) {}
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.getDataCategory(null, "categoria1");
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | UnvalidCategoryException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;

		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.getDataCategory("password", null);
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | UnvalidCategoryException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.getDataCategory("psw", "categoria1");
			if(!checkRep()) return false;
		} catch(NullPointerException | UnvalidCategoryException e) {
			return false;
		} catch(AuthenticationFailedException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.getDataCategory("password", "categoria");
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException | NullPointerException e) {
			return false;
		} catch(UnvalidCategoryException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		try {
			if(!checkRep()) return false;
			List<DataString> l = db.getDataCategory("password", "categoria1");
			if(!checkRep()) return false;
			if(!l.contains(d1) || l.contains(d2) || !l.contains(d3) || l.contains(d4) || !l.contains(d5))
				return false;
		} catch(AuthenticationFailedException | NullPointerException | UnvalidCategoryException e) {
			return false;
		}
		
		//ripristina condizioni iniziali
		try {
			db.removeCategory("categoria1", "password");
			db.removeCategory("categoria2", "password");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e) {}
		
		return true;
	}
	
	public boolean testGetIterator() {
		boolean attendedBehavior;
		DataString d1 = new DataString("dato1");
		DataString d2 = new DataString("dato2");
		DataString d3 = new DataString("dato3");
		DataString d4 = new DataString("dato4");
		DataString d5 = new DataString("dato5");
		
		try {
			db.createCategory("categoria1", "password");
			db.createCategory("categoria2", "password");
			db.put("password", d1, "categoria1");
			db.put("password", d2, "categoria2");
			db.put("password", d3, "categoria1");
			db.put("password", d4, "categoria2");
			db.put("password", d5, "categoria1");
			db.addFriend("categoria1", "password", "friend1");
			db.addFriend("categoria2", "password", "friend1");
			db.addFriend("categoria1", "password", "friend2");
			db.insertLike("friend1", d1);
			db.insertLike("friend2", d1);
			db.insertLike("friend1", d2);
			db.insertLike("friend1", d3);
			db.insertLike("friend1", d5);
			db.insertLike("friend2", d3);
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException | UnvalidFriendException | UnvalidDataException e1) {}
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.getIterator(null);
			if(!checkRep()) return false;
		} catch(AuthenticationFailedException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.getIterator("psw");
			if(!checkRep()) return false;
		} catch(NullPointerException e) {
			return false;
		} catch(AuthenticationFailedException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		Iterator<DataString> iterator;
		try {
			if(!checkRep()) return false;
			iterator = db.getIterator("password");
			if(!checkRep()) return false;
		} catch(NullPointerException | AuthenticationFailedException e) {
			return false;
		}
		
		try {
			DataString d = iterator.next();
			if(!d.equals(d1) && ! d.equals(d3)) return false;
			
			d = iterator.next();
			if(!d.equals(d1) && ! d.equals(d3)) return false;
			
			d = iterator.next();
			if(!d.equals(d2) && ! d.equals(d4) && ! d.equals(d5)) return false;
			
			d = iterator.next();
			if(!d.equals(d2) && ! d.equals(d4) && ! d.equals(d5)) return false;
			
			d = iterator.next();
			if(!d.equals(d2) && ! d.equals(d4) && ! d.equals(d5)) return false;
			
			if(iterator.hasNext()) return false;
		}catch(Exception e) {
			return false;
		}
		
		//ripristina condizioni iniziali
		try {
			db.removeCategory("categoria1", "password");
			db.removeCategory("categoria2", "password");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e) {}
		
		return true;
	}
	
	public boolean testInsertLike() {
		boolean attendedBehavior;
		DataString d1 = new DataString("dato1");
		DataString d2 = new DataString("dato2");
		DataString d3 = new DataString("dato3");
		DataString d4 = new DataString("dato4");
		
		try {
			db.createCategory("categoria1", "password");
			db.createCategory("categoria2", "password");
			db.addFriend("categoria1", "password", "friend");
			db.put("password", d1, "categoria1");
			db.put("password", d2, "categoria2");
			db.put("password", d3, "categoria1");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e1) {} catch (UnvalidFriendException e) {
			e.printStackTrace();
		}
		
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.insertLike(null, d1);
			if(!checkRep()) return false;
		} catch(UnvalidDataException | UnvalidFriendException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.insertLike("friend", null);
			if(!checkRep()) return false;
		} catch(UnvalidDataException | UnvalidFriendException e) {
			return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.insertLike("friend1", d1);
			if(!checkRep()) return false;
		} catch(UnvalidDataException | NullPointerException e) {
			return false;
		} catch(UnvalidFriendException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;

		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.insertLike("friend", d4);
			if(!checkRep()) return false;
		} catch(UnvalidFriendException | NullPointerException e) {
			return false;
		} catch(UnvalidDataException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		try {
			if(!checkRep()) return false;
			db.insertLike("friend", d1);
			if(!checkRep()) return false;
			if(db.get("password", d1).getLikes() != 1) return false;
		} catch(AuthenticationFailedException | UnvalidDataException | UnvalidFriendException | NullPointerException e) {
			return false;
		}
		
		//ripristina condizioni iniziali
		try {
			db.removeCategory("categoria1", "password");
			db.removeCategory("categoria2", "password");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e) {}
		
		return true;
	}
	
	public boolean testGetFriendIterator() {
		boolean attendedBehavior;
		DataString d1 = new DataString("dato1");
		DataString d2 = new DataString("dato2");
		DataString d3 = new DataString("dato3");
		
		try {
			db.createCategory("categoria1", "password");
			db.createCategory("categoria2", "password");
			db.createCategory("categoria3", "password");
			db.put("password", d1, "categoria1");
			db.put("password", d2, "categoria2");
			db.put("password", d3, "categoria3");
			db.addFriend("categoria1", "password", "friend1");
			db.addFriend("categoria2", "password", "friend1");
			db.addFriend("categoria1", "password", "friend2");
			db.addFriend("categoria3", "password", "friend2");
			db.addFriend("categoria1", "password", "friend3");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException | UnvalidFriendException e1) {}
		
		attendedBehavior = false;
		try {
			if(!checkRep()) return false;
			db.getFriendIterator(null);
			if(!checkRep()) return false;
		} catch(NullPointerException e) {
			attendedBehavior = true;
		}
		
		if(!attendedBehavior) return false;
		
		try {
			if(!checkRep()) return false;
			Iterator<DataString> i = db.getFriendIterator("friend");
			if(!checkRep()) return false;
			if(i.hasNext()) return false;
		} catch(NullPointerException e) {
			return false;
		}
				
		Iterator<DataString> iterator;
		try {
			if(!checkRep()) return false;
			iterator = db.getFriendIterator("friend1");
			if(!checkRep()) return false;
		} catch(NullPointerException e) {
			return false;
		}
		
		try {
			DataString d = iterator.next();
			if(!d.equals(d1) && ! d.equals(d2)) return false;
			
			d = iterator.next();
			if(!d.equals(d1) && ! d.equals(d2)) return false;
			
			if(iterator.hasNext()) return false;
		}catch(Exception e) {
			return false;
		}
		
		//ripristina condizioni iniziali
		try {
			db.removeCategory("categoria1", "password");
			db.removeCategory("categoria2", "password");
			db.removeCategory("categoria3", "password");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e) {}
		
		return true;
	}
	
	public boolean testCSize() {
		try {
			if(db.csize() != 0) return false;
			db.createCategory("categoria1", "password");
			if(db.csize() != 1) return false;
			db.createCategory("categoria2", "password");
			if(db.csize() != 2) return false;
			db.createCategory("categoria3", "password");
			if(db.csize() != 3) return false;
			db.removeCategory("categoria1", "password");
			if(db.csize() != 2) return false;
			db.removeCategory("categoria2", "password");
			if(db.csize() != 1) return false;
			db.removeCategory("categoria3", "password");
			if(db.csize() != 0) return false;
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e1) {
			return false;
		}
		
		//ripristina condizioni iniziali
		try {
			db.removeCategory("categoria1", "password");
			db.removeCategory("categoria2", "password");
			db.removeCategory("categoria3", "password");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e1) {}
		
		return true;
	}

	public boolean testDeepClone() {
		DataString d1 = new DataString("dato1");
				
		try {
			db.createCategory("categoria1", "password");
			db.put("password", d1, "categoria1");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e1) {}
		
		// verifica elemento non presente (quindi dato in collezione non modificato)
		try {
			d1.addLike();
			Iterator<DataString> i = db.getIterator("password");
			if(i.next().getLikes() != 0) return false;
		} catch(NullPointerException | AuthenticationFailedException e) {}

		// ripristino condizioni iniziali
		try {
			db.removeCategory("categoria1", "password");
		} catch (NullPointerException | UnvalidCategoryException | AuthenticationFailedException e1) {}

		return true;
	}
}
