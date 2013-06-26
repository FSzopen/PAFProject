package database;

public class AttackUsername extends Classification {

	public AttackUsername(String name) {
		super(name);
		this.setType("username");
	}
	
	public boolean compareTo(String name) {
	if(this.getName().equals(name))
		return true;
	else
		return false;
	}
	
	@Override
	public boolean before(Classification classification) {
		if(getName().compareToIgnoreCase(classification.getName())<0)
			return true;
		else
			return false;
	}

	@Override
	public boolean compareTo(Attack attack) {
		return this.compareTo(attack.getUsername());
	}
	
}