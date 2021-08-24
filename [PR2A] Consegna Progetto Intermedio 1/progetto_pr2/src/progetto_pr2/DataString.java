/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//Classe che rappresenta una implementazione del tipo data con dati di tipo String
public class DataString implements Data {
	
	private String data;	//dato effettivo
	private int likes;	//numero di like ricevuti dal dato
	
	public DataString(String s) {
		if(s == null) throw new NullPointerException("Value can't be null");
		this.data = s;
		likes = 0;
	}
	
	@Override
	public void Display() {
		System.out.println(data + "\nLikes: " + likes);
	}

	@Override
	public boolean equals(Object d) {
		if(!(d instanceof DataString)) return false;
		String ddata = (String) ((DataString) d).getData();
		return data.equals(ddata);
	}

	public Object getData() {
		return data;
	}
	
	@Override
	public int getLikes() {
		return likes;
	}

	@Override
	public Data clone() {
		//Utilizzato per deep copy (vedere relazione)
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            return (Data) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public void addLike() {
		likes++;
		
	}

}
