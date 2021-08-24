/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2.exception;

//Eccezione lanciata nel caso in cui l'utente inserito non sia valido
public class UnvalidFriendException extends Exception {
	
	public UnvalidFriendException() {
		super();
	}
	
	public UnvalidFriendException(String s) {
		super(s);
	}
}
