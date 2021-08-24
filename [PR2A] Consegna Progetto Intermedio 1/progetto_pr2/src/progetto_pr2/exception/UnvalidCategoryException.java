/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2.exception;

//Eccezione lanciata nel caso in cui la categoria inserita non è valida (già presente o assente dalla bacheca)
public class UnvalidCategoryException extends Exception {
	
	public UnvalidCategoryException() {
		super();
	}
	
	public UnvalidCategoryException(String s) {
		super(s);
	}

}
