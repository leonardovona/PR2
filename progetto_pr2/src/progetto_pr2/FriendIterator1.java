package progetto_pr2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class FriendIterator1<E extends Data> implements Iterator<E> {
	private String friend;
	private List<E> elements;
	private int index;
	
	public FriendIterator1(HashMap<String, Category<E>> categories, String friend) {
		this.friend = friend;
		this.elements = new ArrayList<>();
		this.index = 0;
		parseElements(categories);
	}
	
	public void parseElements(HashMap<String, Category<E>> categories) {
		for(Category<E> c: categories.values()) {
			if(c.isFriend(friend)) {
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
