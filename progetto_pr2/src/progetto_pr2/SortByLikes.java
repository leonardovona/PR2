package progetto_pr2;

import java.util.Comparator;

public class SortByLikes implements Comparator<Data>{

	@Override
	public int compare(Data a, Data b) {
		return a.getLikes() - b.getLikes();
	}

}
