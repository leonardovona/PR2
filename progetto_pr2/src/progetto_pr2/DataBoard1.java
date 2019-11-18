package progetto_pr2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DataBoard1<E extends Data> implements DataBoard<E> {
	/*
	 * AF: a(owner, categories) = <owner.getName(), {categories.get(category_0),
	 * ..., categories.get(category_(elements.size() -1))}>
	 * 
	 * IR: owner != null && for all i. 0 <= i < elements.size() =>
	 * categories.get(category_i) != null && for all i, j. 0 <= i < j <
	 * elements.size() =>
	 * !categories.get(category_i).getName().equals(categories.get(category_j).
	 * getName()) && for all i, j. 0 <= i <= j < elements.size() => for all k, l. 0
	 * <= k < categories.get(category_i).esize() && 0 <= l <
	 * categories.get(category_j).esize() =>
	 * !categories.get(category_i).el_k.equals(categories.get(category_j).el_l)
	 * 
	 */
	private User owner;
	private HashMap<String, Category<E>> categories;

	@Override
	public void createCategory(String Category, String passw)
			throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException {
		if (Category == null || passw == null)
			throw new NullPointerException("Value can't be null");
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		if (categories.get(Category) != null)
			throw new UnvalidCategoryException("Category " + Category + " already exists");

		Category<E> category = new Category1<>(Category);
		categories.put(Category, category); // guardare putifabsent

	}

	@Override
	public void removeCategory(String Category, String passw)
			throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException {
		if (Category == null || passw == null)
			throw new NullPointerException("Value can't be null");
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		if (categories.get(Category) == null)
			throw new UnvalidCategoryException("Category " + Category + " does not exists");

		categories.remove(Category);
	}

	@Override
	public void addFriend(String Category, String passw, String friend) throws NullPointerException,
			UnvalidCategoryException, AuthenticationFailedException, UnvalidFriendException {
		if (Category == null || passw == null)
			throw new NullPointerException("Value can't be null");
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		Category<E> category = categories.get(Category);
		if (category == null)
			throw new UnvalidCategoryException("Category " + Category + " does not exists");

		category.addFriend(friend);
	}

	@Override
	public void removeFriend(String Category, String passw, String friend) throws NullPointerException,
			UnvalidCategoryException, AuthenticationFailedException, UnvalidFriendException {
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
	public boolean put(String passw, E dato, String categoria)
			throws NullPointerException, UnvalidCategoryException, AuthenticationFailedException {
		if (categoria == null || passw == null)
			throw new NullPointerException("Value can't be null");
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		Category<E> category = categories.get(categoria);
		if (category == null)
			throw new UnvalidCategoryException("Category " + categoria + " does not exists");

		for (Category<E> c : categories.values()) {
			if (!c.isAssociated(dato))
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
	public E get(String passw, E dato)
			throws NullPointerException, AuthenticationFailedException, UnvalidDataException {
		if (passw == null)
			throw new NullPointerException("Value can't be null");
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");

		Category<E> category = getCategory(dato);
		
		return category.get(dato);
	}

	@Override
	public E remove(String passw, E dato)
			throws NullPointerException, AuthenticationFailedException, UnvalidDataException {
		if (passw == null)
			throw new NullPointerException("Value can't be null");
		if (!passw.equals(owner.getPassword()))
			throw new AuthenticationFailedException("Authentication Failed");
		
		Category<E> category = getCategory(dato);
		
		return category.remove(dato);		
	}


	// Crea la lista dei dati in bacheca su una determinata categoria se vengono
	// rispettati i controlli di identità
	@Override
	public List<E> getDataCategory(String passw, String Category)
			throws NullPointerException, AuthenticationFailedException, UnvalidCategoryException {

	}
	/*
	 * REQUIRES: passw != null, Category != null, passw.equals(owner.getPassword()),
	 * exists i. 0 <= i < csize() => Category.equals(category_i.getName()) THROWS:
	 * se passw == null o Category == null lancia NullPointerException se
	 * !passw.equals(owner.getPassword()) lancia AuthenticationFailedException se
	 * for all i. 0 <= i < csize() => !Category.equals(category_i.getName()) lancia
	 * UnvalidCategoryException EFFECTS: restituisce una lista contenente i dati
	 * della categoria Category
	 */

	// restituisce un iteratore (senza remove) che genera tutti i dati in bacheca
	// ordinati rispetto al numero di like.
	@Override
	public Iterator<E> getIterator(String passw) throws NullPointerException, AuthenticationFailedException {

	}
	/*
	 * REQUIRES: passw != null, passw.equals(owner.getPassword()) THROWS: se passw
	 * == null lancia NullPointerException se !passw.equals(owner.getPassword())
	 * lancia AuthenticationFailedException EFFECTS: restituisce un iteratore senza
	 * remove che itera su tutti i dati in bacheca ordinati rispetto al numero di
	 * like
	 */

	// Aggiunge un like a un dato
	@Override
	public void insertLike(String friend, E data)
			throws NullPointerException, UnvalidFriendException, UnvalidDataException {

	}
	/*
	 * REQUIRES: friend != null, data != null, exists i. 0 <= i < csize() => exists
	 * j. 0 <= j < category_i.esize() => data.equals(category_i.el_j) dato i tale
	 * che data appartiene a category_i, allora exists j. 0 <= j <
	 * category_i.fsize() => friend.equals(category_i.friend_j) THROWS: se friend ==
	 * null o data == null lancia NullPointerException se forall i. 0 <= i < csize()
	 * => forall j. 0 <= j < category_i.esize() => !dato.equals(category_i.el_j)
	 * lancia UnvalidDataException dato i tale che data appartiene a category_i, se
	 * for all j. 0 <= j < category_i.fsize() => !friend.equals(category_i.friend_j)
	 * lancia UnvalidFriendException MODIFIES: this EFFECTS: aumenta di 1 il numero
	 * di like relativi a data
	 */

	// Legge un dato condiviso restituisce un iteratore (senza remove) che genera
	// tutti i dati inn bacheca condivisi.
	@Override
	public Iterator<E> getFriendIterator(String friend) throws NullPointerException {

	}
	/*
	 * REQUIRES: friend != null THROWS: se friend == null lancia
	 * NullPointerException EFFECTS: restituisce un iteratore senza remove che itera
	 * su tutti i dati in bacheca condivisi con friend.
	 */

	@Override
	public int csize() {
		return categories.size();

	}
	
	public Category<E> getCategory(E dato) throws NullPointerException, UnvalidDataException{
		if(dato == null) throw new NullPointerException("Value can't be null");
		
		for(Category<E> c: categories.values()) {
			if(c.isAssociated(dato)) return c;
		}
		
		throw new UnvalidDataException("Data not found");
		 
	}
	

}
