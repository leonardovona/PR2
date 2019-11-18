package progetto_pr2;

import java.util.Iterator;
import java.util.List;

public interface DataBoard<E extends Data> {
	/*
	 * Overview: contenitore di oggetti generici che estendono il tipo di dato Data.
	 *			 Intuitivamente la collezione si comporta come uno spazio per la memorizzazione e visualizzazione di 
	 *			 dati che possono essere di vario tipo che implementano obbligatoriamente il metodo Display().
	 *			 Ogni dato presente nella bacheca ha associato la categoria del dato. Il proprietario della bacheca
	 *			 può definire le proprie categorie e stilare una lista di contatti (amici) a cui saranno visibili i
	 *			 dati per ogni tipologia di categoria. I dati condivisi sono visibili solamente in lettura: gli amici
	 *			 possono visualizzare il dato ma possono essere modificati solamente dal proprietario della bacheca. 
	 *			 Gli amici possono associare un “like” al contenuto condiviso.
	 * 
	 * Typical Element: 
	 * <
	 * 		owner,
	 * 		{
	 * 			category_0,
	 * 			...,
	 * 			category_(csize() - 1)
	 * 		}
	 * >
	 * 		
	 * 	è una coppia tale che:
	 * 		- owner è uno User proprietario della bacheca
	 * 		- il secondo elemento è un insieme di elementi di tipo Category
	 * 		- csize() è il numero di categorie
	 * 
	 * 	Proprietà:
	 *		- owner != null
	 *		- for all i. 0 <= i < size() => category_i != null
	 *		- for all i, j. 0 <= i < j < csize() => category_i.getName() != category_j.getName()
	 *		- for all i, j. 0 <= i <= j < csize() => for all k, l. 0 <= k < category_i.esize() && 0 <= l < category_j.esize() => !category_i.el_k.equals(category_j.el_l)
	 */
	
	
	// Crea l’identità una categoria di dati
	public void createCategory(String Category, String passw) throws NullPointerException, 
		UnvalidCategoryException, AuthenticationFailedException;
	/*
	 * REQUIRES: Category != null, 
	 * 			 passw != null, 
	 *        	 for all i. 0 <= i < csize() => !Category.equals(category_i.getName()),
	 *        	 passw.equals(owner.getPassword())
	 * THROWS: se Category == null o passw == null lancia NullPointerException (unchecked, disponibile in Java)
	 * 		   se exists i. 0 <= i < csize() => Category.equals(category_i.getName()) lancia UnvalidCategoryException (checked)
	 * 		   se !passw.equals(owner.getPassword()) lancia AuthenticationFailedException (checked)
	 * MODIFIES: this
	 * EFFECTS: inserisce una nuova categoria senza elementi e amici nella bacheca dello owner
	 */
	
	// Rimuove l’identità una categoria di dati
	public void removeCategory(String Category, String passw) throws NullPointerException, 
		UnvalidCategoryException, AuthenticationFailedException;
	/*
	 * REQUIRES: Category != null, 
	 * 			 passw != null, 
	 *        	 exists i. 0 <= i < csize() => Category.equals(category_i.getName()),
	 *        	 passw.equals(owner.getPassword())
	 * THROWS: se Category == null o passw == null lancia NullPointerException
	 * 		   se for all i. 0 <= i < csize() => !Category.equals(category_i.getName()) lancia UnvalidCategoryException
	 * 		   se !passw.equals(owner.getPassword()) lancia AuthenticationFailedException
	 * MODIFIES: this
	 * EFFECTS: rimuove la categoria Category, rimuove gli elementi che sono di categoria Category con il relativo numero
	 * 			di like e rimuove l'accesso agli elementi di Category agli amici che ne avevano accesso.
	 */
	
	// Aggiunge un amico ad una categoria di dati
	public void addFriend(String Category, String passw, String friend) throws NullPointerException,
		UnvalidCategoryException, AuthenticationFailedException, UnvalidFriendException;
	/*
	 * REQUIRES: Category != null, 
	 * 			 passw != null,
	 * 			 friend != null, 
	 *        	 exists i. 0 <= i < csize() => Category.equals(category_i.getName()),
	 *        	 passw.equals(owner.getPassword())
	 *        	 dato i tale che Category.equals(category_i.getName()) => for all j. 0 <= j < category_i.fsize() => !friend.equals(category_i.friend_j)
	 * THROWS: se Category == null o passw == null o friend == null lancia NullPointerException
	 * 		   se for all i. 0 <= i < csize() => !Category.equals(category_i.getName()) lancia UnvalidCategoryException
	 * 		   se !passw.equals(owner.getPassword()) lancia AuthenticationFailedException
	 * 		   se dato i tale che Category.equals(category_i.getName()) => exists j. 0 <= j < category_i.fsize() => friend.equals(category_i.friend_j) lancia UnvalidFriendException (checked)
	 * MODIFIES: this
	 * EFFECTS: viene aggiunto friend agli amici della categoria Category
	 */
	
	// rimuove un amico ad una categoria di dati
	public void removeFriend(String Category, String passw, String friend) throws NullPointerException,
		UnvalidCategoryException, AuthenticationFailedException, UnvalidFriendException;
	/*
	 * REQUIRES: Category != null, 
	 * 			 passw != null,
	 * 			 friend != null, 
	 *        	 exists i. 0 <= i < csize() => Category.equals(category_i.getName()),
	 *        	 passw.equals(owner.getPassword())
	 *        	 dato i tale che Category.equals(category_i.getName()) => exists j. 0 <= j < category_i.fsize() => friend.equals(category_i.friend_j)
	 * THROWS: se Category == null o passw == null o friend == null lancia NullPointerException
	 * 		   se for all i. 0 <= i < csize() => !Category.equals(category_i.getName()) lancia UnvalidCategoryException
	 * 		   se !passw.equals(owner.getPassword()) lancia AuthenticationFailedException
	 * 		   se dato i tale che Category.equals(category_i.getName()) => for all j. 0 <= j < category_i.fsize() => !friend.equals(category_i.friend_j) lancia UnvalidFriendException
	 * MODIFIES: this
	 * EFFECTS: viene rimosso friend dagli amici della categoria Category
	 */
	
	// Inserisce un dato in bacheca se vengono rispettati i controlli di identità
	public boolean put(String passw, E dato, String categoria) throws NullPointerException,
		UnvalidCategoryException, AuthenticationFailedException;
	/*
	 * REQUIRES: passw != null,
	 * 			 dato != null,
	 * 			 categoria != null,
	 * 			 exists i. 0 <= i < csize() => categoria.equals(category_i.getName()),
	 *        	 passw.equals(owner.getPassword())
	 * THROWS: se passw == null o categoria == null lancia NullPointerException
	 * 		   se for all i. 0 <= i < csize() => !categoria.equals(category_i.getName()) lancia UnvalidCategoryException
	 * 		   se !passw.equals(owner.getPassword()) lancia AuthenticationFailedException
	 * MODIFIES: this
	 * EFFECTS: se il dato è già presente nella bacheca restituisce false altrimenti 
	 * 			inserisce l'elemento dato tra gli elementi della categoria e restituisce true.
	 */
	
	
	//for all i. 0 <= i < csize() => for all j. 0 <= j < category_i.esize() => !dato.equals(category_i.el_j)
	
	// Ottiene una copia del del dato in bacheca se vengono rispettati i controlli di identità
	public E get(String passw, E dato) throws NullPointerException,
		AuthenticationFailedException, UnvalidDataException;
	/*
	 * REQUIRES: passw != null,
	 * 			 dato != null,
	 *        	 passw.equals(owner.getPassword()),
	 *        	 exists i. 0 <= i < csize() => exists j. 0 <= j < category_i.esize() => dato.equals(category_i.el_j)
	 * THROWS: se passw == null o dato == null lancia NullPointerException
	 * 		   se !passw.equals(owner.getPassword()) lancia AuthenticationFailedException
	 * 		   se forall i. 0 <= i < csize() => forall j. 0 <= j < category_i.esize() => !dato.equals(category_i.el_j) lancia UnvalidDataException
	 * EFFECTS: restituisce una copia di dato presente nella bacheca. 
	 */
	
	// Rimuove il dato dalla bacheca se vengono rispettati i controlli di identità
	public E remove(String passw, E dato) throws NullPointerException,
		AuthenticationFailedException, UnvalidDataException;
	/*
	 * REQUIRES: passw != null,
	 * 			 dato != null,
	 *        	 passw.equals(owner.getPassword()),
	 *        	 exists i. 0 <= i < csize() => exists j. 0 <= j < category_i.esize() => dato.equals(category_i.el_j)
	 * THROWS: se passw == null o dato == null lancia NullPointerException
	 * 		   se !passw.equals(owner.getPassword()) lancia AuthenticationFailedException
	 * 		   se forall i. 0 <= i < csize() => forall j. 0 <= j < category_i.esize() => !dato.equals(category_i.el_j) lancia UnvalidDataException
	 * MODIFIES: this
	 * EFFECTS: rimuove e restituisce il dato dalla bacheca.
	 */
	
	// Crea la lista dei dati in bacheca su una determinata categoria se vengono rispettati i controlli di identità
	public List<E> getDataCategory(String passw, String Category) throws NullPointerException,
		AuthenticationFailedException, UnvalidCategoryException;
	/*
	 * REQUIRES: passw != null,
	 * 			 Category != null,
	 *        	 passw.equals(owner.getPassword()),
	 *        	 exists i. 0 <= i < csize() => Category.equals(category_i.getName())
	 * THROWS: se passw == null o Category == null lancia NullPointerException
	 * 		   se !passw.equals(owner.getPassword()) lancia AuthenticationFailedException
	 *		   se for all i. 0 <= i < csize() => !Category.equals(category_i.getName()) lancia UnvalidCategoryException
	 * EFFECTS: restituisce una lista contenente i dati della categoria Category
	 */
	
	// restituisce un iteratore (senza remove) che genera tutti i dati in bacheca ordinati rispetto al numero di like.
	public Iterator<E> getIterator(String passw) throws NullPointerException, AuthenticationFailedException;
	/*
	 * REQUIRES: passw != null,
	 * 			 passw.equals(owner.getPassword())
	 * THROWS: se passw == null lancia NullPointerException
	 * 		   se !passw.equals(owner.getPassword()) lancia AuthenticationFailedException
	 * EFFECTS: restituisce un iteratore senza remove che itera su tutti i dati in bacheca ordinati rispetto al numero di like
	 */
	
	// Aggiunge un like a un dato
	public void insertLike(String friend, E data) throws NullPointerException,
		UnvalidFriendException, UnvalidDataException;
	/*
	 * REQUIRES: friend != null,
	 * 			 data != null,
	 * 			 exists i. 0 <= i < csize() => exists j. 0 <= j < category_i.esize() => data.equals(category_i.el_j)
	 *           dato i tale che data appartiene a category_i, allora exists j. 0 <= j < category_i.fsize() => friend.equals(category_i.friend_j)
	 * THROWS: se friend == null o data == null lancia NullPointerException
	 * 		   se forall i. 0 <= i < csize() => forall j. 0 <= j < category_i.esize() => !dato.equals(category_i.el_j) lancia UnvalidDataException
	 * 		   dato i tale che data appartiene a category_i, se for all j. 0 <= j < category_i.fsize() => !friend.equals(category_i.friend_j) lancia UnvalidFriendException
	 * MODIFIES: this
	 * EFFECTS: aumenta di 1 il numero di like relativi a data
	 */
	
	// Legge un dato condiviso restituisce un iteratore (senza remove) che genera tutti i dati inn bacheca condivisi.
	public Iterator<E> getFriendIterator(String friend) throws NullPointerException;
	/*
	 * REQUIRES: friend != null 
	 * THROWS: se friend == null lancia NullPointerException
	 * EFFECTS: restituisce un iteratore senza remove che itera su tutti i dati in bacheca condivisi con friend.
	 */
	
	//restituisce il numero di categorie nella bacheca
	public int csize();
	/*
	 * EFFECTS: restitutisce il numero di categorie nella bacheca.
	 */
	
	//restituisce la categoria di dato
	public Category<E> getCategory(E dato) throws NullPointerException, UnvalidDataException;
		/*
		 * REQUIRES: dato != null,
		 * 			 exists i. 0 <= i < csize() => exists j. 0 <= j < category_i.esize() => dato.equals(category_i.el_j)
		 * THROWS: se dato == null lancia NullPointerException
		 * 		   se for all i. 0 <= i < csize() => for all j. 0 <= j < category_i.esize() => !dato.equals(category_i.el_j) lancia UnvalidDataException
		 * EFFECTS: restituisce la categoria di dato
		 */

}
