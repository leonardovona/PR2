/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import progetto_pr2.Category;
import progetto_pr2.Data;

//Iteratore per tutti i dati di una bacheca condivisi con un dato amico
public class FriendIterator<E extends Data> implements Iterator<E> {
	private String friend;	//amico di cui si vogliono iterare i dati condivisi
	private List<E> elements;	//dati condivisi con l'amico
	private int index;	//indice attuale del prossimo elemento da restituire
	
	public FriendIterator(HashMap<String, Category<E>> categories, String friend) {
		//costruttore per implementazione 1
		this.friend = friend;
		this.elements = new ArrayList<>();
		this.index = 0;
		parseElements(categories.values());
	}
	
	public FriendIterator(List<Category<E>> categories, String friend) {
		//costruttore per implementazione 2
		this.friend = friend;
		this.elements = new ArrayList<>();
		this.index = 0;
		parseElements(categories);
	}
	
	//aggiunge tutti i dati condivisi con friend alla lista di elementi su cui iterare
	public void parseElements(Collection<Category<E>> categories) {
		for(Category<E> c: categories) {
			if(c.hasAccess(friend)) {
				for(E elem: c.getElements()) {
					elements.add(elem);
				}
			}
		}
	}
	
	@Override
	public boolean hasNext() {
		return index < elements.size();
	}

	@Override
	public E next() {
		if (!hasNext()) {
            throw new NoSuchElementException("No elements in Collection");
        }
        return (E) (elements.get(index++)).clone();
	}

}
