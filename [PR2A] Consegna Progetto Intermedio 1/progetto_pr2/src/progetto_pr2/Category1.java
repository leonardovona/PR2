/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2;

import java.util.ArrayList;
import java.util.List;

import progetto_pr2.exception.UnvalidDataException;
import progetto_pr2.exception.UnvalidFriendException;

public class Category1<E extends Data> implements Category<E> {
	/*
	 * IR:  name != null &&
	 * 		for all i. 0 <= i < esize() => el_i != null &&
	 * 		for all i. 0 <= i < fsize() => friend_i != null &&
	 * 		for all i, j. 0 <= i < j < fsize() => friend_i != friend_j
	 */
	
	private String name;	//neme della categoria
	private List<E> elements;	//elementi che sono della categoria corrispondente a this
	private List<String> friends;	//lista degli amici della categoria corrispondente a this
	
	public Category1(String name) {
		this.name = name;
		this.elements = new ArrayList<>();
		this.friends = new ArrayList<>();
	}

	public String getName() {
		return name;
	}
	
	public int esize() {
		return elements.size();		
	}
	
	public int fsize() {
		return friends.size();
	}
	
	public boolean hasAccess(String friend) throws NullPointerException{
		if(friend == null)
			throw new NullPointerException("Value can't be null");
		return friends.contains(friend);
	}
	
	public void addFriend(String friend) throws NullPointerException, UnvalidFriendException{
		if(hasAccess(friend))	//se condizione è vera allora friend è già amico in questa categoria
			throw new UnvalidFriendException(friend + " is already a friend");
		friends.add(friend);
	}
	
	public void removeFriend(String friend) throws NullPointerException, UnvalidFriendException{
		if(!hasAccess(friend))	//se condizione è vera allora friend non è un amico in questa categoria
			throw new UnvalidFriendException(friend + " is not a friend");
		
		friends.remove(friend);
	}
	
	public void put(E dato) throws NullPointerException, UnvalidDataException{
		if(isAssociated(dato))	//se condizione è vera allora dato è già associato a questa categoria
			throw new UnvalidDataException("Data is already in the category");
		
		elements.add((E) dato.clone());	//copy in di dato memorizzato in this
	}
	
	public boolean isAssociated(E dato) throws NullPointerException{
		if(dato == null)
			throw new NullPointerException("Value can't be null");

		return elements.contains(dato);
	}
	
	public E get(E dato) throws NullPointerException, UnvalidDataException{
		if(isAssociated(dato)) {	//condizione vera se dato è associato a questa categoria
			for(Data e: elements) {	//scorre gli elementi
				if(e.equals(dato)) { //ha trovato la copia locale di dato
					return (E) e.clone();	//copy out della copia locale di dato 
				}
			}
		}
		//se il metodo arriva qua allora dato non è associato a questa categoria
		throw new UnvalidDataException("Data is not in the category");
	}
	
	public E remove(E dato) throws NullPointerException, UnvalidDataException{
		E d = get(dato);	//controlli di dato sono effettuati dal metodo get(dato)
		elements.remove(dato);
		return d;
	}
	
	public List<E> getElements(){
		List<E> list = new ArrayList<>();
		for(E elem: elements) {
			list.add((E) elem.clone());	//copy out dei dati in locale
		}
		return list;
	}
	
	public void addLike(E data) {
		for(E e: elements) {
			if(e.equals(data)) {	//e corrisponde alla copia locale di data
				e.addLike();
			}
		}
	}
	
	
	public boolean checkRep() {
		/*IR:  name != null &&
				 * 		for all i. 0 <= i < esize() => el_i != null &&
				 * 		for all i. 0 <= i < fsize() => friend_i != null
		*/
		if(name == null) return false;
		for(E data: elements) {
			if(data == null) return false;
		}
		for(int i = 0; i < friends.size() - 1; i++) {	//controlla che i friend di this siano univoci
			String f = friends.get(i);
			for(int j = i + 1; j < friends.size(); j++) {
				if(f.equals(friends.get(j)))
					return false;
			}
		}
		return true;
	}
	
}