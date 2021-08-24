/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2;

public interface User {
	/*
	 * Overview: Utente che possiede una bacheca. E' identificato tramite un nome univoco e possiede una password per
	 * 			 l'autenticazione.
	 * 
	 * Typical Element: <name, password>
	 * 	
	 * Dove:
	 * 		- name è il nome dell'utente
	 * 		- password è la password dell'utente
	 * 
	 * Proprietà:
	 * 		- name != null
	 * 		- password != null
	 * 		- password.length() >= 8
	 */
	
	public String getName();	//getter
	
	public String getPassword();	//getter
}
