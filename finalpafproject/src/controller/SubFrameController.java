package controller;

import java.io.IOException;
import java.util.ArrayList;
import ui.CountryFrame;
import ui.DateFrame;
import ui.GroupedAttackFrame;
import ui.IPFrame;
import ui.SubJFrame;
import ui.UsernameFrame;
import database.Attack;
import database.AttackDay;
import database.AttackUsername;
import database.Country;
import database.GroupedAttack;
import database.IPAddress;
import database.MainBase;
import database.Parameters;

public class SubFrameController {

	private MainBase mainBase = new MainBase();
	private SubJFrame frame;
	private Parameters parameters;
	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<Integer> attackNumbers = new ArrayList<Integer>();

	public SubFrameController(int type, ArrayList<Attack> attackList, Parameters parameters, String name) {
		mainBase.setDataBase(attackList);
		this.parameters = parameters;
		switch(type) {
		case 0: frame = new IPFrame("Résultats pour l'IP : " + name, this); break;
		case 1: frame = new CountryFrame("Résultats pour le pays : " + name, this); break;
		case 2: frame = new DateFrame("Résultats pour la date : " + name, this); break;
		case 3: frame = new UsernameFrame("Résultats pour le nom d'utilisateur : " + name, this); break;
		case 4: frame = new GroupedAttackFrame("Résultats pour le groupe : " + name, this); break;
		default: ;
		}
	}

	public void sortPerCountry() throws IOException {
		if(this.mainBase.getAttackCountryList().size()==0) {
			if(mainBase.getAttackIPAddressList().size()==0) {
				this.sortPerIP();
			}
			ArrayList<Country> countryList = new ArrayList<Country>();
			ArrayList<String> countryStringList = new ArrayList<String>();
			ArrayList<IPAddress> tempList = mainBase.getAttackIPAddressList();
			for(IPAddress ipAdd : tempList) {
				String country = ipAdd.getCountry();
				// System.out.println(country);
				if(countryStringList.contains(country)) {
					int i = countryStringList.indexOf(country);
					countryList.get(i).addIPAddress(ipAdd);}
				else {
					countryList.add(new Country(country,ipAdd));
					countryStringList.add(country);
				}
			}
			countryList = sortNumberCountry(countryList);
			mainBase.setAttackCountryList(countryList);
		}
		names = new ArrayList<String>();
		attackNumbers = new ArrayList<Integer>();
		for(Country country : this.mainBase.getAttackCountryList()) {
			this.names.add(country.getName());
			attackNumbers.add(country.getNumber());
		}
	}

	public void sortPerDay() {
		if(this.mainBase.getAttackDayList().size()==0) {
			ArrayList<AttackDay> dayList = new ArrayList<AttackDay>();
			boolean ind;
			for(Attack att : mainBase.getAttackList()) {
				ind = false;
				for(AttackDay day : dayList) {
					if(day.compareTo(att)) {
						day.add(att);
						ind = true;
						break;
					}
				}
				if(!ind) {
					AttackDay newDay = new AttackDay(att.getDay()+"/"+att.getMonth()+"/"+att.getYear(), att);
					dayList.add(newDay);
				}
			}
			dayList = sortAttackDay(dayList);
			mainBase.setAttackDayList(dayList);
		}
		names = new ArrayList<String>();
		attackNumbers = new ArrayList<Integer>();
		for(AttackDay day : this.mainBase.getAttackDayList()) {
			this.names.add(day.getName());
			attackNumbers.add(day.getNumber());
		}
	}

	//TODO: FAIRE LE IF
	public void sortPerGroupedAttack() {
		if(this.mainBase.getGroupedAttackList().size()==0) {
			ArrayList<GroupedAttack> groupList = new ArrayList<GroupedAttack>();
			boolean ind;
			int counter = 0;
			int attCounter = 0;
			for(Attack att : mainBase.getAttackList()) {
				attCounter++;
				ind = false;
				for(GroupedAttack group : groupList) {
					if(group.compareTo(att)) {
						group.add(att);
						ind = true;
					}
				}
				if(!ind) {
					counter++;
					GroupedAttack newGroup = new GroupedAttack("groupe n°"+counter, att);
					System.out.println("groupe n°"+counter+", attaque n°"+attCounter);
					groupList.add(newGroup);
				}
			}
			groupList = sortNumberGroupedAttack(groupList);
			mainBase.setGroupedAttackList(groupList);
		}
		names = new ArrayList<String>();
		attackNumbers = new ArrayList<Integer>();
		for(GroupedAttack group : this.mainBase.getGroupedAttackList()) {
			this.names.add(group.getName());
			attackNumbers.add(group.getNumber());
		}
	}

	public void sortPerIP() {
		if(this.mainBase.getAttackIPAddressList().size()==0) {
			ArrayList<IPAddress> ipList = new ArrayList<IPAddress>();
			boolean ind;
			for(Attack att : mainBase.getAttackList()) {
				ind = false;
				for(IPAddress ip : ipList) {
					if(ip.compareTo(att)) {
						ip.add(att);
						ind = true;
						break;
					}
				}
				if(!ind) {
					IPAddress newIP = new IPAddress(att.getIP(), att);
					ipList.add(newIP);
				}
			}
			ipList = sortNumberIPAddress(ipList);
			mainBase.setAttackIPAddressList(ipList);
		}
		names = new ArrayList<String>();
		attackNumbers = new ArrayList<Integer>();
		for(IPAddress ipAdd : this.mainBase.getAttackIPAddressList()) {
			this.names.add(ipAdd.getName());
			attackNumbers.add(ipAdd.getNumber());
		}
	}

	public void sortPerUsername() {
		if(this.mainBase.getAttackUsernameList().size()==0) {
			ArrayList<AttackUsername> usernameList = new ArrayList<AttackUsername>();
			boolean ind;
			int test = 0; // TEST
			for(Attack att : mainBase.getAttackList()) {
				test++; // TEST
				System.out.println(test); // TEST
				ind = false;
				for(AttackUsername username : usernameList) {
					if(username.compareTo(att)) {
						username.add(att);
						ind = true;
						break;
					}
				}
				if(!ind) {
					AttackUsername newUsername = new AttackUsername(att.getUsername(), att);
					usernameList.add(newUsername);
				}
			}
			usernameList = sortNumberAttackUsername(usernameList);
			mainBase.setAttackUsernameList(usernameList);
		}
		names = new ArrayList<String>();
		attackNumbers = new ArrayList<Integer>();
		for(AttackUsername username : this.mainBase.getAttackUsernameList()) {
			this.names.add(username.getName());
			attackNumbers.add(username.getNumber());
		}
	}

	public ArrayList<AttackDay> sortAttackDay(ArrayList<AttackDay> argList) {
		ArrayList<AttackDay> attackDayList = argList;
		int n = attackDayList.size();
		int min = 0;
		for(int i = 0 ; i<(n-1) ; i++) {
			min = i;
			for(int j = i+1 ; j<n ; j++){
				if(attackDayList.get(j).before(attackDayList.get(i)))
					min = j;
			}
			if(min!=i) {
				AttackDay tempAttackDay = attackDayList.get(i);
				attackDayList.set(i, attackDayList.get(min));
				attackDayList.set(min, tempAttackDay);
			}
		}
		return attackDayList;
	}

	public ArrayList<AttackDay> sortNumberAttackDay(ArrayList<AttackDay> argList) {
		ArrayList<AttackDay> attackDayList = argList;
		int n = attackDayList.size();
		int min = 0;
		for(int i = 0 ; i<(n-1) ; i++) {
			min = i;
			for(int j = i+1 ; j<n ; j++) {
				if(attackDayList.get(i).getNumber()>(attackDayList.get(j).getNumber()))
					min = j;
			}
			if(min!=i) {
				AttackDay tempAttackDay = attackDayList.get(i);
				attackDayList.set(i, attackDayList.get(min));
				attackDayList.set(min, tempAttackDay);
			}
		}
		return attackDayList;
	}

	public ArrayList<Country> sortCountry(ArrayList<Country> argList) {
		ArrayList<Country> countryList = argList;
		int n = countryList.size();
		int min = 0;
		for(int i = 0 ; i<(n-1) ; i++) {
			min = i;
			for(int j = i+1 ; j<n ; j++) {
				if (countryList.get(j).before(countryList.get(i)))
					min=j;
			}
			if(min!=i) {
				Country tempCountry=countryList.get(i);
				countryList.set(i, countryList.get(min));
				countryList.set(min, tempCountry);
			}
		}
		return countryList;
	}

	public ArrayList<Country> sortNumberCountry(ArrayList<Country> argList) {
		ArrayList<Country> countryList = argList;
		int n = countryList.size();
		int min = 0;
		for(int i = 0 ; i<(n-1) ; i++) {
			min = i;
			for(int j = i+1 ; j<n ; j++) {
				if(!(countryList.get(i).getNumber()>(countryList.get(j).getNumber())))
					min = j;
			}
			if(min!=i) {
				Country tempCountry = countryList.get(i);
				countryList.set(i, countryList.get(min));
				countryList.set(min, tempCountry);
			}
		}
		return countryList;
	}

	public ArrayList<AttackUsername> sortAttackUsername(ArrayList<AttackUsername> argList) {
		ArrayList<AttackUsername> attackUsernameList = argList;
		int n = attackUsernameList.size();
		int min = 0;
		for(int i = 0 ; i<(n-1) ; i++) {
			min = i;
			for(int j = i+1 ; j<n ; j++) {
				if(attackUsernameList.get(j).before(attackUsernameList.get(i)))
					min = j;
			}
			if(min!=i) {
				AttackUsername tempAttackUsername = attackUsernameList.get(i);
				attackUsernameList.set(i, attackUsernameList.get(min));
				attackUsernameList.set(min, tempAttackUsername);
			}
		}
		return attackUsernameList;
	}

	public ArrayList<AttackUsername> sortNumberAttackUsername(ArrayList<AttackUsername> argList) {
		ArrayList<AttackUsername> attackUsernameList = argList;
		int n = attackUsernameList.size();
		int min = 0;
		for(int i = 0 ; i<(n-1) ; i++) {
			min = i;
			for(int j = i+1 ; j<n ; j++) {
				if(attackUsernameList.get(i).getNumber()>(attackUsernameList.get(j).getNumber()))
					min = j;
			}
			if(min!=i) {
				AttackUsername tempAttackUsername = attackUsernameList.get(i);
				attackUsernameList.set(i, attackUsernameList.get(min));
				attackUsernameList.set(min, tempAttackUsername);
			}
		}
		return attackUsernameList;
	}

	public ArrayList<IPAddress> sortIPAddress(ArrayList<IPAddress> argList) {
		ArrayList<IPAddress> ipAddressList = argList;
		int n = ipAddressList.size();
		int min = 0;
		for(int i = 0 ; i<(n-1) ; i++) {
			min = i;
			for(int j = i+1 ; j<n ; j++) {
				if(ipAddressList.get(j).before(ipAddressList.get(i))) min = j;
			}
			if(min!=i) {
				IPAddress tempIPAddress = ipAddressList.get(i);
				ipAddressList.set(i, ipAddressList.get(min));
				ipAddressList.set(min, tempIPAddress);
			}
		}
		return ipAddressList;
	}

	public ArrayList<IPAddress> sortNumberIPAddress(ArrayList<IPAddress> ipAddressList) {
		int n = ipAddressList.size();
		int min = 0;
		for(int i = 0 ; i<(n-1) ;i++) {
			min = i;
			for(int j = i+1 ; j<n ; j++) {
				if(!(ipAddressList.get(min).getNumber()>(ipAddressList.get(j).getNumber())))
					min = j;
			} 
			if(min!=i) {
				IPAddress tempIPAddress = ipAddressList.get(i);
				ipAddressList.set(i, ipAddressList.get(min));
				ipAddressList.set(min, tempIPAddress);
			}
		}
		return ipAddressList;
	}

	public ArrayList<GroupedAttack> sortNumberGroupedAttack(ArrayList<GroupedAttack> argList) {
		ArrayList<GroupedAttack> groupedAttackList = argList;
		int n = groupedAttackList.size();
		int min = 0;
		for(int i = 0 ; i<(n-1) ; i++) {
			min = i;
			for(int j = i+1 ; j<n ; j++) {
				if(groupedAttackList.get(i).getNumber()>(groupedAttackList.get(j).getNumber()))
					min = j;
			}
			if(min!=i) {
				GroupedAttack tempGroupedAttack = groupedAttackList.get(i);
				groupedAttackList.set(i, groupedAttackList.get(min));
				groupedAttackList.set(min, tempGroupedAttack);
			}
		}
		return groupedAttackList;
	}

	// GETTERS
	public Parameters getParameters() { return this.parameters; }
	public ArrayList<String> getNames() { return this.names; }
	public ArrayList<Integer> getAttackNumbers() { return this.attackNumbers; }

	public void sortPerHour() {
		// TODO Auto-generated method stub
		
	}

	public void sortPerMonth() {
		// TODO Auto-generated method stub
		
	}
	public void openSubJFrame(String finalType, String nameRow) {
		int i = 0;
		if(finalType.equals("IP")) {
			i = 0;
			for(IPAddress ip : this.mainBase.getAttackIPAddressList()) {
				if(ip.getName().equals(nameRow)) {
					new SubFrameController(i, ip.getAttackList(), this.parameters, nameRow);
					break;
				}
			}
		}
		if(finalType.equals("Pays")) {
			i = 1;
			for(Country country : this.mainBase.getAttackCountryList()) {
				if(country.getName().equals(nameRow)) {
					new SubFrameController(i, country.getAttackList(), this.parameters, nameRow);
					break;
				}
			}
		}
		if(finalType.equals("Jour")) {
			i = 2;
			for(AttackDay day : this.mainBase.getAttackDayList()) {
				if(day.getName().equals(nameRow)) {
					new SubFrameController(i, day.getAttackList(), this.parameters, nameRow);
					break;
				}
			}
		}
		if(finalType.equals("Username")) {
			i = 3;
			for(AttackUsername username : this.mainBase.getAttackUsernameList()) {
				if(username.getName().equals(nameRow)) {
					new SubFrameController(i, username.getAttackList(), this.parameters, nameRow);
					break;
				}
			}
		}
		if(finalType.equals("Groupe")) {
			i = 4;
			for(GroupedAttack group : this.mainBase.getGroupedAttackList()) {
				if(group.getName().equals(nameRow)) {
					new SubFrameController(i, group.getAttackList(), this.parameters, nameRow);
					break;
				}
			}
		}
		

	}
}
