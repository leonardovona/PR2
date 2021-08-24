/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//Classe che rappresenta una implementazione del tipo data con dati di tipo int
public class DataInt implements Data {
	
	private int data;	//dato effettivo
	private int likes;	//numero di like ricevuti dal dato
	
	public DataInt(int n) {
		this.data = n;
		likes = 0;
	}
	
	@Override
	public void Display() {
		System.out.println(data + "\nLikes: " + likes);
	}

	@Override
	public boolean equals(Object d) {
		if(!(d instanceof DataInt)) return false;
		int ddata = (int) ((DataInt) d).getData();
		return data == ddata;
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
