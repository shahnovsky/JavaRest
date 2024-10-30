package Model;

import java.io.Serializable;
import java.time.LocalDate;

import Utils.Expertise;
import Utils.Gender;

public class Cook extends Person implements Serializable{
	
	private static final long serialVersionUID = 7289478406770958928L;
	private static int idCounter = 1;
	private Expertise expert;
	private boolean isChef;
	
	public Cook(String firstName, String lastName, LocalDate birthDay, Gender gender, Expertise expert,
			boolean isChef) {
		super(idCounter++, firstName, lastName, birthDay, gender);
		this.expert = expert;
		this.isChef = isChef;
	}
	
	public Cook(int id) {
		super(id);
	}

	public Expertise getExpert() {
		return expert;
	}

	public void setExpert(Expertise expert) {
		this.expert = expert;
	}

	public boolean getIsChef() {
		return isChef;
	}

	public void setIsChef(boolean isChef) {
		this.isChef = isChef;
	}
	

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Cook.idCounter = idCounter;
	}

	@Override
	public String toString() {
		return super.toString()+" Cook [expert=" + expert + "]";
	}
}
