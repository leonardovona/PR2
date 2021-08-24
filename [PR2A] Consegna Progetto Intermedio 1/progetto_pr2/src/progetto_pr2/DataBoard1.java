/*
 * Leonardo Vona 
 * 545042
 */

package progetto_pr2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import progetto_pr2.exception.AuthenticationFailedException;
import progetto_pr2.exception.UnvalidCategoryException;
import progetto_pr2.exception.UnvalidDataException;
import progetto_pr2.exception.UnvalidFriendException;
import progetto_pr2.iterator.DBIterator;
import progetto_pr2.iterator.FriendIterator;

public class DataBoard1<E extends Data> implements DataBoard<E> {
	/*
	 * AF: a(owner, categories) = <owner.getName(),
	 * 							 {categories.get(category_0),
	 * 							  ...,
	 * 							  categories.get(category_(categories.size() -1))}>
	 * 
	 * IR: owner != null &&
	 * 	   categories != null &&
	 * 	   for all i. 0 <= i < categories.size() => categories.get(category_i) != null &&
	 * 	   for all i, j. 0 <= i < j < elements.size() => !categories.get(category_i).getName().equals(categories.get(category_j).getName()) &&
	 *     for all i, j. 0 <= i <= j < categories.size() => for all k, l. 0 <= k < categories.get(category_i).esize() &&
	 *      	0 <= l < categories.get(category_j).esize() => !categories.get(category_i).el_k.equals(categories.get(category_j).el_l) 
	 */
	private User owner;		//utente proprietario della bacheca associata a this
	private HashMap<String, Category<E>> categories;	//categorie contenenti i dati in bacheca

	public DataBoard1(String owner, String passw) throws NullPointerException, UnvalidDataException {
		this.owner = new User1(owner, passw);	//controllo integrità demandato a User1(., .)
		categories = new HashMap<>();
	}

	@Override
	public void createCategory(String Category, String passw) throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException {
		if (Category == null || passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		if (categories.get(Category) != null)	//condizione vera se Category è già una categoria di this 
			throw new UnvalidCategoryException("Category " + Category + " already exists");

		Category<E> category = new Category1<>(Category);
		categories.put(Category, category);

	}

	@Override
	public void removeCategory(String Category, String passw) throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException {
		if (Category == null || passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		if (categories.get(Category) == null) //condizione vera se Category non è una categoria di this 
			throw new UnvalidCategoryException("Category " + Category + " does not exists");

		categories.remove(Category);
	}

	@Override
	public void addFriend(String Category, String passw, String friend) throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException, UnvalidFriendException {
		if (Category == null || passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		Category<E> category = categories.get(Category);
		
		if (category == null)	//condizione vera se Category non è una categoria di this 
			throw new UnvalidCategoryException("Category " + Category + " does not exists");

		category.addFriend(friend);
	}

	@Override
	public void removeFriend(String Category, String passw, String friend) throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException, UnvalidFriendException {
		if (Category == null || passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		Category<E> category = categories.get(Category);
		
		if (category == null)
			throw new UnvalidCategoryException("Category " + Category + " does not exists");

		category.removeFriend(friend);
	}

	@Override
	public boolean put(String passw, E dato, String categoria) throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException {
		if (categoria == null || passw == null)
			throw new NullPointerException("Value can't be null");
		
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		Category<E> category = categories.get(categoria);
		
		if (category == null)
			throw new UnvalidCategoryException("Category " + categoria + " does not exists");

		for (Category<E> c : categories.values()) {	//controlla che dato non sia già presente in bacheca
			if (c.isAssociated(dato))
				return false;
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
		
		if (categories.get(Category) == null)
			throw new UnvalidCategoryException("Category " + Category + " does not exists");

		return categories.get(Category).getElements();

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
		
		if (!c.hasAccess(friend))	//se condizione vera allora friend non è un amico della categoria di data
			throw new UnvalidFriendException(friend + " is not a friend");

		try {
			get(owner.getPassword(), data).addLike();
		} catch (AuthenticationFailedException e) {} //non può essere lanciata perchè la password è sicuramente corretta

		c.addLike(data);
	}

	@Override
	public Iterator<E> getFriendIterator(String friend) throws NullPointerException {
		return new FriendIterator<E>(this.categories, friend);
	}

	@Override
	public int csize() {
		return categories.size();

	}

	private Category<E> getCategory(E dato) throws NullPointerException, UnvalidDataException {
		if (dato == null)
			throw new NullPointerException("Value can't be null");

		for (Category<E> c : categories.values()) {	//cicla sulle categorie ricercando quella associata a dato
			if (c.isAssociated(dato))
				return c;
		}
		//non è stata trovata la categoria, quindi il dato non è presente in bacheca
		throw new UnvalidDataException("Data not found");

	}

	public boolean checkRep() {
		if (owner == null) {
			return false;
		}
		if (categories == null) {
			return false;
		}
		
		for(Category<E> c: categories.values()){	//cicla sulle categorie
			if(c == null)
				return false;
			if(!c.checkRep())	//true se la categoria non soddisfa la propria invariante di rappresentazione
				return false;
			int numEquals = 0;	//deve essere uguale a 1, perchè una categoria può essere uguale solo a sé stessa
			Collection<Category<E>> coll = categories.values();
			for(Category<E> c1: coll) {	//cicla su tutte le categorie
				if(c1.getName().equals(c.getName())) {
					if(numEquals == 0) {	//prima corrispondenza
						numEquals++;
					} else {	//successiva corrispondenza, invariante violata
						return false;
					}
				}
			}				
		}

		Iterator<?> iterator = null;
		try {
			iterator = this.getIterator(this.owner.getPassword());
		} catch (NullPointerException | AuthenticationFailedException e) {
			e.printStackTrace();
		}

		while (iterator.hasNext()) {	//cicla su tutti i dati
			E elem = (E) iterator.next();
			Iterator<?> iterator2 = null;
			try {
				iterator2 = this.getIterator(this.owner.getPassword());
			} catch (NullPointerException | AuthenticationFailedException e) {
				e.printStackTrace();
			}
			int countEquals = 0;	//deve essere uguale a 1 (perchè un dato è uguale solo a sé stesso)
			while (iterator2.hasNext()) {	//cicla su tutti i dati
				E elem2 = (E) iterator2.next();
				if (elem2.equals(elem))	//due dati nella bacheca sono uguali
					countEquals++;
			}
			if (countEquals != 1)	//esistono distinti d1, d2 tali che d1.data equaivale a d2.data
				return false;
		}

		return true;
	}

}
