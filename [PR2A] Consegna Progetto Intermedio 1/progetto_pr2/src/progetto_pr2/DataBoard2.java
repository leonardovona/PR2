/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import progetto_pr2.exception.AuthenticationFailedException;
import progetto_pr2.exception.UnvalidCategoryException;
import progetto_pr2.exception.UnvalidDataException;
import progetto_pr2.exception.UnvalidFriendException;
import progetto_pr2.iterator.DBIterator;
import progetto_pr2.iterator.FriendIterator;

public class DataBoard2<E extends Data> implements DataBoard<E> {
	/*
	* AF: a(owner, categories) = <owner.getName(), {categories.get(0),
	 * 		..., categories.get(categories.size() -1)}>
	 * 
	 * IR:  owner != null &&
	 * 		categories != null &&
	 * 		for all i. 0 <= i < categories.size() => categories.get(i) != null &&
	 * 		for all i, j. 0 <= i < j < elements.size() => !categories.get(i).getName().equals(categories.get(j).getName()) &&
	 *  	for all i, j. 0 <= i <= j < categories.size() =>
	 *  		(for all k, l. (0 <= k < categories.get(i).esize() && 0 <= l < categories.get(j).esize()) =>
	 * 			!categories.get(i).el_k.equals(categories.get(j).el_l))
	 */
	private User owner;	//utente proprietario della bacheca associata a this
	private List<Category<E>> categories;	//categorie contenenti i dati in bacheca
	
	public DataBoard2(String owner, String passw) throws NullPointerException, UnvalidDataException{
		this.owner = new User1(owner, passw);
		categories = new ArrayList<>();
	}
	
	@Override
	public void createCategory(String Category, String passw) throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException {
		if (Category == null || passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		if (isCategory(Category))
			throw new UnvalidCategoryException("Category " + Category + " already exists");
		
		Category<E> category = new Category1<>(Category);
		categories.add(category);

	}
	
	//ritorna true se category è una categoria di this, false altrimenti
	private boolean isCategory(String category) {
		for(Category<E> c: categories) {	//cicla sulle categorie di this
			if(c.getName().equals(category)) {	//category è una categoria di this
				return true;
			}
		}
		//category non è una categoria di this
		return false;
	}

	@Override
	public void removeCategory(String Category, String passw) throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException {
		if (Category == null || passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		for(int i = 0; i < categories.size(); i++) {	//cicla sulle categorie
			Category<E> c = categories.get(i);
			if(c.getName().equals(Category)) {	//ha trovato la categoria Category
				categories.remove(i);
				return;
			}
		}
		//Category non è stata trovata
		throw new UnvalidCategoryException("Category " + Category + " does not exists");
	}

	@Override
	public void addFriend(String Category, String passw, String friend) throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException, UnvalidFriendException {
		if (Category == null || passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		for(int i = 0; i < categories.size(); i++) {	//cicla sulle categorie
			Category<E> c = categories.get(i);
			if(c.getName().equals(Category)) {	//Category è una categoria di this
				c.addFriend(friend);
				return;
			}
		}
		//Category non è stata trovata
		throw new UnvalidCategoryException("Category " + Category + " does not exists");
	}

	@Override
	public void removeFriend(String Category, String passw, String friend) throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException, UnvalidFriendException {
		if (Category == null || passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		for(int i = 0; i < categories.size(); i++) {
			Category<E> c = categories.get(i);
			if(c.getName().equals(Category)) {
				c.removeFriend(friend);
				return;
			}
		}
		
		throw new UnvalidCategoryException("Category " + Category + " does not exists");
	}

	@Override
	public boolean put(String passw, E dato, String categoria) throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException {
		if (categoria == null || passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
				
		Category<E> category = null;
		
		for(int i = 0; i < categories.size(); i++) {
			Category<E> c1 = categories.get(i);
			if(c1.isAssociated(dato))	//true se dato è già presente in bacheca
				return false;
			if(c1.getName().equals(categoria)) {	//categoria è una categoria di this
				category = c1;
			}
		}
		
		if(category == null) {	//non è stata trovata categoria in this
			throw new UnvalidCategoryException("Category " + categoria + " does not exists");
		}
		
		
		try {
			category.put(dato);
		} catch (UnvalidDataException e) {
			return false;
		}
		return true;
	}

	@Override
	public E get(String passw, E dato) throws NullPointerException, AuthenticationFailedException, UnvalidDataException {
		if (passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");

		Category<E> category = getCategory(dato);
		
		return category.get(dato);
	}

	@Override
	public E remove(String passw, E dato) throws NullPointerException, AuthenticationFailedException, UnvalidDataException {
		if (passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		Category<E> category = getCategory(dato);
		
		return category.remove(dato);		
	}


	@Override
	public List<E> getDataCategory(String passw, String Category) throws NullPointerException, AuthenticationFailedException, UnvalidCategoryException {
		if (Category == null || passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		for(int i = 0; i < categories.size(); i++) {
			Category<E> c = categories.get(i);
			if(c.getName().equals(Category)) {	//Category è una categoria di this, restituisce i dati associati
				return c.getElements();
			}
		}
		throw new UnvalidCategoryException("Category " + Category + " does not exists");
	}
	
	@Override
	public Iterator<E> getIterator(String passw) throws NullPointerException, AuthenticationFailedException {
		if (passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		return new DBIterator<E>(categories);
	}

	@Override
	public void insertLike(String friend, E data) throws NullPointerException, UnvalidFriendException, UnvalidDataException {
		Category<E> c = getCategory(data);
		
		if(!c.hasAccess(friend))	//friend non è tra gli amici della categoria di data
			throw new UnvalidFriendException(friend + " is not a friend");

		c.addLike(data);
	}

	@Override
	public Iterator<E> getFriendIterator(String friend) throws NullPointerException {
		if(friend == null) throw new NullPointerException("Value can't be null");
		
		return new FriendIterator<E>(this.categories, friend);
	}


	@Override
	public int csize() {
		return categories.size();

	}
	
	//restituisce la categoria associata a dato
	private Category<E> getCategory(E dato) throws NullPointerException, UnvalidDataException{
		if(dato == null) throw new NullPointerException("Value can't be null");
		
		for(Category<E> c: categories) {
			if(c.isAssociated(dato)) return c;
		}
		
		throw new UnvalidDataException("Data not found");
		 
	}
	
	public boolean checkRep() {
		if (owner == null) {
			return false;
		}
		
		if (categories == null) {
			return false;
		}

		for (int i = 0; i < categories.size() - 1; i++) {	//cicla sulle categorie
			if (categories.get(i) == null)	//controlla che ogni categoria sia != null
				return false;
			if (!categories.get(i).checkRep())	//controlla che ogni categoria rispetti la propria invariante di rappresentazione
				return false;
			for (int j = i + 1; j < categories.size(); j++) {	//cicla sulle categorie rimanenti oltre la i-esima
				if (categories.get(i).getName().equals(categories.get(j).getName()))	//controlla che le categorie siano univoche
					return false;
			}
		}

		Iterator<?> iterator = null;
		try {
			iterator = this.getIterator(this.owner.getPassword());
		} catch (NullPointerException | AuthenticationFailedException e) {}

		while (iterator.hasNext()) {	//cicla su tutti i dati
			E elem = (E) iterator.next();
			Iterator<?> iterator2 = null;
			try {
				iterator2 = this.getIterator(this.owner.getPassword());
			} catch (NullPointerException | AuthenticationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int countEquals = 0; //deve essere uguale a 1 (perchè un dato è uguale solo a sé stesso)
			while (iterator2.hasNext()) { //cicla su tutti i dati
				E elem2 = (E) iterator2.next();
				if (elem2.equals(elem))	//due dati nella bacheca sono uguali
					countEquals++;
			}
			if (countEquals != 1) //esistono distinti d1, d2 tali che d1.data equaivale a d2.data
				return false;
		}

		return true;
	}
}
