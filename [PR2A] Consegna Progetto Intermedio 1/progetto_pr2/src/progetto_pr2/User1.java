/*
 * Leonardo Vona
 * 545042
 */

package progetto_pr2;

import progetto_pr2.exception.UnvalidDataException;

//implementazione dell'interfaccia User
public class User1 implements User{
	
	private String name;	//nome dell'utente
	private String passw;	//password dell'utente
	
	public User1(String name, String passw) throws NullPointerException, UnvalidDataException {
		if(name == null || passw == null)
			throw new NullPointerException("Value can't be null");
		if(passw.length() < 8)
			throw new UnvalidDataException("Password length must be at least 8");
		
		this.name = name;
		this.passw = passw;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPassword() {
		return passw;
	}
}
