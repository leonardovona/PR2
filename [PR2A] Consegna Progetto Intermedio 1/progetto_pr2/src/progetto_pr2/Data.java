/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2;

import java.io.Serializable;	//necessario per la deep copy

public interface Data extends Serializable{
	/*
	 * Overview: Dato presente in una bacheca. Ha associato un numero di like.
	 * 
	 * Typical Element: <data, likes>
	 * 
	 * Dove:
	 * 		- data è il contenuto vero e proprio
	 * 		- likes è il numero di like ricevuti dal dato
	 * 
	 * Proprietà:
	 * 		- data != null
	 * 		- likes >= 0
	 */
	
	//visualizza data con il relativo numero di like
	public void Display();
	/*
	 * EFFECTS: visualizza data con il relativo numero di like
	 */
	
	@Override
	public boolean equals(Object d);
	/*
	 * EFFECTS: ritorna true se d.data equivale a this.data, false altrimenti
	 */
	
	public Object getData();
	/*
	 * Effects: Restitusce una this.data
	 */
	
	public int getLikes();
	/*
	 * EFFECTS: restituisce il numero di like relativi al dato
	 */
	
	public Data clone();
	/*
	 * EFFECTS: restituisce una deep copy di this
	 */
	
	public void addLike();
	/*
	 * EFFECTS: incrementa di 1 il numero di like
	 */
	
}
