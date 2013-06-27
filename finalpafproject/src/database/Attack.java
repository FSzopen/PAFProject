package database;

public class Attack {

	private int day;
	private int month;
	private int year;
	private String hour;
	private String username;
	private String ip;

	/**
	 * Attack constructor
	 * @param day
	 * @param month
	 * @param year
	 * @param hour
	 * @param userName
	 * @param IP
	 */
	public Attack(int day, int month, int year, String hour, String username, String IP) {
		super();
		this.day = day;
		this.month = month;
		this.year = year;
		this.hour = hour;
		this.username = username;
		this.ip = IP;
	}

	/**
	 * Says if this is before att.
	 * @param att
	 * @return
	 */
	public boolean before(Attack att) {
		int i1 = year*10000 + month*100 + day;
		int i2 = att.getYear()*1000 + att.getMonth()*100 + att.getDay();
		if(i1<i2) return true; else return false;
	}

	/**
	 * Like before but with the hours.
	 * @param att
	 * @return
	 */
	public boolean timeBefore(Attack att) {
		String[] split1 = hour.split(":");
		String[] split2 = att.getHour().split(":");
		int i1 = (year*10000 + month*100 + day)*24*60 + 60*Integer.parseInt(split1[0]) + Integer.parseInt(split1[1]);
		int i2 = (att.getYear()*1000 + att.getMonth()*100 + att.getDay())*24*60 + 60*Integer.parseInt(split2[0]) + Integer.parseInt(split2[1]);
		if(i1<i2) 
			return true; 
		else 
			return false;
	}

	/**
	 * Says if this is on the same day as att.
	 * @param att
	 * @return
	 */
	public boolean sameDay(Attack att) {
		if((year==att.getYear())&&(month==att.getMonth())&&(day==att.getDay())) return true;
		else return false;
	}

	/**
	 * Says if this happened just before, just after, or on the same day as att.
	 * @param att
	 * @return
	 */
	public boolean closeDay(Attack att) {
		if(year==att.getYear()) {
			if(month==att.getMonth()) {
				if((day==att.getDay())||(day==att.getDay()-1)||(day==att.getDay()+1)) {
					return true ;
				}
				else {
					return false;
				}
			}
			else {
				if(month==att.getMonth()-1) {
					switch (month) {
					case 1 : if((day==31)&(att.getDay()==1)) return true; else return false;
					case 2 : if((day==28)&(att.getDay()==1)) return true; else return false;
					case 3 : if((day==31)&(att.getDay()==1)) return true; else return false;
					case 4 : if((day==30)&(att.getDay()==1)) return true; else return false;
					case 5 : if((day==31)&(att.getDay()==1)) return true; else return false;
					case 6 : if((day==30)&(att.getDay()==1)) return true; else return false;
					case 7 : if((day==31)&(att.getDay()==1)) return true; else return false;
					case 8 : if((day==31)&(att.getDay()==1)) return true; else return false;
					case 9 : if((day==30)&(att.getDay()==1)) return true; else return false;
					case 10 : if((day==31)&(att.getDay()==1)) return true; else return false;
					case 11 : if((day==30)&(att.getDay()==1)) return true; else return false;
					}
				}
				else {
					if(month==att.getMonth()+1) {
						switch (month) {
						case 2 : if((att.getDay()==31)&(day==1)) return true; else return false;
						case 3 : if((att.getDay()==28)&(day==1)) return true; else return false;
						case 4 : if((att.getDay()==31)&(day==1)) return true; else return false;
						case 5 : if((att.getDay()==30)&(day==1)) return true; else return false;
						case 6 : if((att.getDay()==31)&(day==1)) return true; else return false;
						case 7 : if((att.getDay()==30)&(day==1)) return true; else return false;
						case 8 : if((att.getDay()==31)&(day==1)) return true; else return false;
						case 9 : if((att.getDay()==31)&(day==1)) return true; else return false;
						case 10 : if((att.getDay()==30)&(day==1)) return true; else return false;
						case 11 : if((att.getDay()==31)&(day==1)) return true; else return false;
						case 12 : if((att.getDay()==30)&(day==1)) return true; else return false;
						}
					}
				}
			}
		}
		else {
			if((year==att.getYear()+1)&(month==1)&(att.getMonth()==12)&(day==1)&(att.getDay()==31)) {
				return true;
			}
			else {
				if((year==att.getYear()-1)&(month==12)&(att.getMonth()==1)&(day==31)&(att.getDay()==1)) {
					return true;
				}
				else return false;
			}
		}
		return false;
	}

	/**
	 * Says if this and att happened in the same half hour.
	 * @param att
	 * @return
	 */
	public boolean areClose(Attack att) {
		String[] hoursplit = hour.split(":");
		int hour1 = Integer.parseInt(hoursplit[0]);
		int minute1 = Integer.parseInt(hoursplit[1]);
		int hour2 = Integer.parseInt(att.getHour().split(":")[0]);
		int minute2 = Integer.parseInt(att.getHour().split(":")[1]);
		if(closeDay(att)) {
			if(sameDay(att)) {
				int time1 = hour1*60 + minute1;
				int time2 = hour2*60 + minute2;
				if(Math.abs(time1-time2)<=30)
					return true;
				else 
					return false;
			}
			else if(before(att)) {
				int time1=hour1*60+minute1;
				int time2=hour2*60+minute2+24*60;
				if(Math.abs(time1-time2)<=30)
					return true;
				else 
					return false;
			}
			else {
				int time1=hour1*60 + minute1 + 24*60;
				int time2=hour2*60 + minute2;
				if(Math.abs(time1-time2)<=30)
					return true;
				else 
					return false;
			}
		}
		else 
			return false;
	}

	/**
	 * Says if st1 and st2 are close in a dictionary.
	 * @param st1
	 * @param st2
	 * @param value
	 * @return
	 */
	public boolean compare(String st1, String st2, int value) {
		if ((st1.length()>1)&&(st2.length()>1)) {
			return (Math.abs(( 
					new Character(st1.charAt(0)).compareTo( 
							new Character(st2.charAt(0))))*26 + new Character(st1.charAt(1)).compareTo( 
									new Character(st2.charAt(1)))))<=value;
		}
		else {
			if ((st1.length()==1)&&(st2.length()==1)) {
				return (Math.abs(( 
						new Character(st1.charAt(0)).compareTo( 
								new Character(st2.charAt(0))))))<=value;
			}
			else { 
				return false;
			}
		}
	}

	/**
	 * Says if att and this are in the same AttackGroup (made by the same person).
	 * @param att
	 * @return
	 */
	public boolean sameAttackgroup(Attack att) {
		if(areClose(att)) {
			if(compare(username,att.getUsername(),6))
				return true;
			else if(ip==att.getIP()) 
				return true; 
			else 
				return false;
		}
		else 
			return false;
	}

	// GETTERS
	public int getDay() { return day; }
	public int getMonth() { return month; }
	public String getHour() { return hour; }
	public String getUsername() { return username; }
	public String getIP() { return ip; }
	public int getYear() { return year; }

	// SETTERS
	public void setDay(int day) { this.day = day; }
	public void setMonth(int month) { this.month = month; }
	public void setHour(String hour) { this.hour = hour; }
	public void setUsername(String username) { this.username = username; }
	public void setIP(String IP) { this.ip = IP; }
	public void setYear(int year) { this.year = year; }

}
