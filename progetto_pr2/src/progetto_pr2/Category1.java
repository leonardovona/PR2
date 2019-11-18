package progetto_pr2;

public class Category1<E extends Data> implements Category<E> {

	public Category1(String name) {
		
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int esize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int fsize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasAccess(String friend) throws NullPointerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addFriend(String friend) throws NullPointerException, UnvalidFriendException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFriend(String friend) throws NullPointerException, UnvalidFriendException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void put(E dato) throws NullPointerException, UnvalidDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAssociated(E dato) throws NullPointerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E get(E dato) throws NullPointerException, UnvalidDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E remove(E dato) throws NullPointerException, UnvalidDataException {
		// TODO Auto-generated method stub
		return null;
	}

}
