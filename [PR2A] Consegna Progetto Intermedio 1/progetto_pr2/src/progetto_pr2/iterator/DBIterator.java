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
import progetto_pr2.SortByLikes;

//Iteratore per i dati presenti in una DataBoard.
//Itera su tutti i dati ordinati descrescenti in base al numero di like
public class DBIterator<E extends Data> implements Iterator<E>{
	private List<E> elements;	//elementi su cui iterare
	private int index;	//indice attuale
	
	public DBIterator(HashMap<String, Category<E>> categories) {	//costruttore per implementazione 1
		this.index = 0;
		this.elements = new ArrayList<>();
		parseElements(categories.values());
	}
	
	public DBIterator(List<Category<E>> categories) {	//costruttore per implementazione 2
		this.index = 0;
		this.elements = new ArrayList<>();
		parseElements(categories);
	}
	
	//aggiunge tutti i dati alla lista e li ordina in base al numero di like
	private void parseElements(Collection<Category<E>> categories) {
		for(Category<E> c: categories) {
			for(E elem: c.getElements()) {
				elements.add(elem);
			}
		}
		elements.sort(new SortByLikes());
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
