/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2.exception;

//Eccezione lanciata nel caso in cui il dato passato non sia valido
public class UnvalidDataException extends Exception {

	public UnvalidDataException() {
		super();
	}
	
	public UnvalidDataException(String s) {
		super(s);
	}
}
