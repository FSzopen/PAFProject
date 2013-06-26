package database;

import java.io.IOException;

public class Country extends Classification {

	public Country(String name) {
		super(name);
		this.setType("username");
	}
	
	public boolean compareTo(String name) {
	if (this.getName().equals(name))
		return true;
	else
		return false;
	}
	
	@Override
	public boolean compareTo(Attack attack) {
		return false;
	}
	
	public boolean compareTo(IPAddress ipAddress) throws IOException {
		ipAddress.setCountry();
		if (this.getName().equals(ipAddress.getCountry())) 
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
}