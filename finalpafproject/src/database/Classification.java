package database;

import java.util.ArrayList;

public abstract class Classification {

	private String name;
	private int number;
	private ArrayList<Attack> attackList;
	private String type;
	
	public Classification(String name) {
		this.name = name;
		number = 0;
		attackList = new ArrayList<Attack>();
	}
	
	public void add(Attack attack) {
		attackList.add(attack);
		number++;
	}
	
	public abstract boolean compareTo(String name);
	public abstract boolean compareTo(Attack attack);
	public abstract boolean before(Classification classification);

	// GETTERS
	public String getName() { return name; }
	public int getNumber() { return number; }
	public ArrayList<Attack> getAttackList() { return attackList; }
	public String getType() { return type; }
	
	// SETTERS
	public void setType(String type) { this.type = type; }
	
}