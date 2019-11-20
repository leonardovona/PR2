package progetto_pr2;

public interface Data {
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
	
	public boolean equals();
	
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
