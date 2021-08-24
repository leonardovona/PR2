/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2.exception;

//Eccezione lanciata nel caso in cui la password non corrisponde a quella dell'owner
public class AuthenticationFailedException extends Exception {

	public AuthenticationFailedException() {
		super();
	}
	
	public AuthenticationFailedException(String s) {
		super(s);
	}
	
}
