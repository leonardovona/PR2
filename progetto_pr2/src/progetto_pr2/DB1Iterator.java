package progetto_pr2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class DB1Iterator<E extends Data> implements Iterator<E>{
	private HashMap<String, Category<E>> categories;
	private List<E> elements;
	private int index;
	public DB1Iterator(HashMap<String, Category<E>> categories) {
		this.index = 0;
		this.elements = new ArrayList<>();
		parseElements(categories);
	}
	
	private void parseElements(HashMap<String, Category<E>> categories) {
		for(Category<E> c: categories.values()) {
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
	/*
	private E deepClone(E element) {
        //Utilizzato per deep copy (vedere relazione)
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(element);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            return (E) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

}
