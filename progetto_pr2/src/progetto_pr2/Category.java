package progetto_pr2;

import java.util.List;

public interface Category<E extends Data> {
	/*
	 * Overview: Categoria di dato presente in una bacheca. Una categoria ha un
	 * nome, un insieme di elementi della bacheca che sono di tale categoria e un
	 * insieme di amici che possono visualizzare i contenuti della bacheca di quella
	 * categoria.
	 * 
	 * Typical element: < name, { el_0, ..., el_esize() - 1) }, { friend_0, ...,
	 * friend_fsize() - 1) } >
	 * 
	 * è una tripla tale che: - name è il nome della categoria - il secondo elemento
	 * è un insieme di oggetti di tipo E - il terzo elemento è un insieme di User
	 * "amici" che possono visualizzare gli elementi della categoria - esize()
	 * corrisponde al numero di elementi associati alla categoria - fsize()
	 * corrisponde al numero di "amici" associati alla bacheca
	 * 
	 * Proprietà: - name != null - for all i. 0 <= i < esize() => el_i != null - for
	 * all i. 0 <= i < fsize() => friend_i != null
	 */
	
	//restituisce il nome della categoria
	public String getName();
	/*
	 * EFFECTS: restituisce il nome della categoria
	 */
	
	// restitutisce il numero di elementi della categoria
	public int esize();
	/*
	 * EFFECTS: restitutisce il numero di elementi della categoria.
	 */

	// restitutisce il numero di amici che hanno accesso agli elementi della
	// categoria
	public int fsize();
	/*
	 * EFFECTS: restitutisce il numero di amici che hanno accesso agli elementi
	 * della categoria.
	 */

	// restituisce true se friend può visualizzare gli elementi nella categoria,
	// false altrimenti
	public boolean hasAccess(String friend) throws NullPointerException;
	/*
	 * REQUIRES: friend != null THROWS: se friend == null lancia
	 * NullPointerException EFFECTS: restituisce true se friend può visualizzare gli
	 * elementi nella categoria, false altrimenti
	 */

	// aggiunge un amico alla categoria di dati
	public void addFriend(String friend) throws NullPointerException, UnvalidFriendException;
	/*
	 * REQUIRES: friend != null, 
	 * 			 for all i. 0 <= i < fsize() => !friend.equals(this.friend_i) 
	 * THROWS: se friend == null lancia NullPointerException 
	 * 		   se exists i. 0 <= i < fsize() => friend.equals(this.friend_i) lancia UnvalidFriendException 
	 * MODIFIES: this
	 * EFFECTS: viene aggiunto friend agli amici di this
	 */

	// rimuove un amico dalla categoria di dati
	public void removeFriend(String friend) throws NullPointerException, UnvalidFriendException;
	/*
	 * REQUIRES: friend != null, 
	 * 		  	 exists i. 0 <= i < fsize() => friend.equals(this.friend_i)
	 * THROWS: se friend == null lancia NullPointerException 
	 * 		   se for all i. 0 <= i < fsize() => !friend.equals(this.friend_i) lancia UnvalidFriendException 
	 * MODIFIES: this
	 * EFFECTS: viene rimosso friend dagli amici di this
	 */
	
	//associa un dato alla categoria this
	public void put(E dato) throws NullPointerException, UnvalidDataException;
	/*
	 * REQUIRES: dato != null,
	 * 			 for all i. 0 <= i < esize() => !dato.equals(this.el_i)
	 * THROWS: se dato == null lancia NullPointerException
	 * 		   se exists i. 0 <= i < esize() => dato.equals(this.el_i) lancia UnvalidDataException
	 * MODIFIES: this
	 * EFFECTS: viene aggiunto dato agli elementi della categoria this
	 */
	
	//restituisce true se dato è associato alla categoria this, false altrimenti
	public boolean isAssociated(E dato) throws NullPointerException;
	/*
	 * REQUIRES: dato != null,
	 * THROWS: se dato == null lancia NullPointerException
	 * EFFECTS: restituisce true se dato è associato alla categoria this, false altrimenti
	 */
	
	//restituisce una copia di dato associato alla categoria this
	public E get(E dato) throws NullPointerException, UnvalidDataException;
	/*
	 * REQUIRES: dato != null,
	 * 			 exists i. 0 <= i < esize() => dato.equals(this.el_i)
	 * THROWS: se dato == null lancia NullPointerException
	 * 		   se for all i. 0 <= i < esize() => !dato.equals(this.el_i) lancia UnvalidDataException
	 * EFFECTS: restituisce una copia di dato associato alla categoria this
	 */
	
	//rimuove e restituisce dato dalla categoria
	public E remove(E dato) throws NullPointerException, UnvalidDataException;
	/*
	 * REQUIRES: dato != null,
	 * 			 exists i. 0 <= i < esize() => dato.equals(this.el_i)
	 * THROWS: se dato == null lancia NullPointerException
	 * 		   se for all i. 0 <= i < esize() => !dato.equals(this.el_i) lancia UnvalidDataException
	 * MODIFIES: this
	 * EFFECTS: rimuove e restituisce dato associato alla categoria this
	 */
	
	public List<E> getElements();
	/*
	 * EFFECTS: restituisce una lista contenente le copie degli elementi di questa categoria
	 */
	
	public boolean isFriend(String friend);
	/*
	 * REQUIRES: friend != null
	 * THROWS: se friend == null lancia NullPointerException
	 * EFFECTS: restituisce true se friend appartiene all'insieme degli amici, false altrimenti
	 */
	
}
 