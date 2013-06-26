package database;

public class AttackDay extends Classification {

	private int day;
	private int month;
	private int year;
	
	public AttackDay(String name) {
		super(name);
		this.setType("temporal");
		String[] splitString = name.split("/");
		day = Integer.parseInt(splitString[0]);
		month = Integer.parseInt(splitString[1]);
		year = Integer.parseInt(splitString[2]);
	}

	@Override
	public boolean compareTo(String otherName) {
		String[] splitString = otherName.split("/");
		int otherDay = Integer.parseInt(splitString[0]);
		int otherMonth = Integer.parseInt(splitString[1]);
		int otherYear = Integer.parseInt(splitString[2]);
		if((day==otherDay)&&((month==otherMonth)&&(year==otherYear)))
			return true;
		else 
			return false;
	}
	
	@Override
	public boolean compareTo(Attack attack) {
		if ((day==attack.getDay())&&(month==attack.getMonth())&&(year==attack.getYear())) 
			return true;
		else
			return false;
	}
	
	@Override
	public boolean before(Classification classification) {
		String[] splitString = classification.getName().split("/");
		int otherDay = Integer.parseInt(splitString[0]);
		int otherMonth = Integer.parseInt(splitString[1]);
		int otherYear = Integer.parseInt(splitString[2]);
		if((year*10000+month*100+day)<(otherYear*10000+otherMonth*100+otherDay))
			return true;
		else
			return false;
	}

}