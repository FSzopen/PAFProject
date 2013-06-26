package database;

import java.util.ArrayList;

public class AttackDate {

	private int day;
	private int month;
	private int attackNumber = 0;
	private int year;
	private ArrayList<Attack> attacks = new ArrayList<Attack>();



	public AttackDate() {}
	
	/**
	 * Says if this is before other.
	 * @param other
	 * @return
	 */
	public boolean before (AttackDate other) {
		if(month<other.getMonth()) return true;
		else {
			if(month>other.getMonth()) 
				return false;
			else {
				if(day<other.getDay()) 
					return true;
				else 
					return false;
			}
		}
	}

	/**
	 * Says if this equals date.
	 * @param date
	 * @return
	 */
	public boolean sameDay(AttackDate date) {
		boolean b = (date.getDay()==day)&&(date.getMonth()==month)&&(date.getYear()==year);
		return b;
	}

	/**
	 * Says if this is January, the 1st.
	 * @return
	 */
	public boolean firstDay() {
		if((day==1)&&(month==1)) 
			return true;
		else 
			return false;
	}

	/**
	 * Says if this is December, the 31st (happy new year!).
	 * @return
	 */
	public boolean lastDay() {
		if((day==31)&&(month==12))
			return true;
		else
			return false;
	}

	public void add(Attack attack) {
		attacks.add(attack);
		attackNumber = attackNumber + 1;
	}
	
	// GETTERS
	public int getYear() { return year; }
	public int getAttackNumber() { return attackNumber; }
	public ArrayList<Attack> getAttacks() { return attacks; }
	public int getDay() { return day; }
	public int getMonth() { return month; }

	// SETTERS
	public void setYear(int year) { this.year = year; }
	public void setDay(int day) { this.day = day; }
	public void setMonth (int m){ month=m; }
	public void setMonth(String m) {
		if(m.equals("Jan")) month = 1;
		if(m.equals("Feb")) month = 2;
		if(m.equals("Mar")) month = 3;
		if(m.equals("Apr")) month = 4;
		if(m.equals("May")) month = 5;
		if(m.equals("Jun")) month = 6;
		if(m.equals("Jul")) month = 7;
		if(m.equals("Aug")) month = 8;
		if(m.equals("Sep")) month = 9;
		if(m.equals("Oct")) month = 10;
		if(m.equals("Nov")) month = 11;
		if(m.equals("Dec")) month = 12;
	}

}