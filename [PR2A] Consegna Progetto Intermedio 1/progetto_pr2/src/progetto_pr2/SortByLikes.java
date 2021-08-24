/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2;

import java.util.Comparator;

//classe utilizzata per ordinare in modo decrescente i dati in base al numero di like
public class SortByLikes implements Comparator<Data>{

	@Override
	public int compare(Data a, Data b) {
		return b.getLikes() - a.getLikes();
	}

}
