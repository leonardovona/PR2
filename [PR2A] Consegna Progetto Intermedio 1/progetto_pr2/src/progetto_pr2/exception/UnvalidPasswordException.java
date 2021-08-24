/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2.exception;

//Eccezione lanciata nel caso in cui la password inserita non rispetti i criteri
public class UnvalidPasswordException extends Exception {

	public UnvalidPasswordException() {
		super();
	}
	
	public UnvalidPasswordException(String s) {
		super(s);
	}
}
