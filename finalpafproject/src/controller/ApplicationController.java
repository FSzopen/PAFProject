package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdesktop.swingx.JXMapViewer;

import com.csvreader.CsvReader;

import controller.maps.MapWaypoint;
import controller.maps.MapWaypointPainter;
import controller.maps.MapWaypointRenderer;
import database.AttackDate;
import database.AttackDay;
import database.AttackUsername;
import database.Country;
import database.GroupedAttack;
import database.IPAddress;
import database.MainBase;
import database.Parameters;
import database.Attack;
import ui.MainFrame;
import ui.ParameterJFrame;
import ui.maps.JMapPanel;
import ui.maps.JMapPoint;

public class ApplicationController {

	private MainFrame mainFrame;
	private Parameters parameters = new Parameters();
	private MainBase mainBase = new MainBase();

	public ApplicationController() {
		mainFrame = new MainFrame(this);	
	}

	/**
	 * Exit...
	 * @param
	 */
	public void exit() {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	/**
	 * @param
	 */
	public void openParameters() {
		// TODO Auto-generated method stub
		new ParameterJFrame(this);
	}

	/**
	 * Imports data from attacks file.
	 * @param path
	 */
	public void importData(String path) {
		FileReader fr = null;
		BufferedReader br = null;
		int year = 2012;
		AttackDate tempDate = null;
		AttackDate mailDate = null;
		int number = 0;
		ArrayList <Attack> attackList = new ArrayList<Attack>();
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			String inputLine = br.readLine();
			Pattern pattern = Pattern.compile("([a-zA-Z]*) ([0-9]*) (\\d{2}:\\d{2}:\\d{2}) www sshd\\[[0-9]*\\]: Invalid user (.*?) from (\\d{1,4}\\.\\d{1,4}\\.\\d{1,4}\\.\\d{1,4})");
			Pattern patternDate = Pattern.compile("^Received: by www \\(sSMTP sendmail emulation\\); [a-zA-Z]*, (\\d{1,2}) ([a-zA-Z]*) ([0-9]*) \\d{2}:\\d{2}:\\d{2} (.*)$");
			System.out.println("fichier ouvert");
			while(inputLine!=null) {
				Matcher matcher = pattern.matcher(inputLine);
				Matcher matcherDate = patternDate.matcher(inputLine);
				if(matcherDate.matches()) {
					year=Integer.parseInt(matcherDate.group(3));
					mailDate=new AttackDate();
					mailDate.setDay(Integer.parseInt(matcherDate.group(1)));
					mailDate.setMonth(matcherDate.group(2));
				}
				if(matcher.matches()) {
					number = number + 1;
					tempDate = new AttackDate();
					tempDate.setDay(Integer.parseInt(matcher.group(2)));
					tempDate.setMonth(matcher.group(1));
					Attack attack;
					if(mailDate.firstDay()&&tempDate.lastDay()) {
						attack = new Attack(Integer.parseInt(matcher.group(2)), tempDate.getMonth(), (year-1),matcher.group(3),matcher.group(4),matcher.group(5));
					}
					else {
						attack=new Attack(Integer.parseInt(matcher.group(2)), tempDate.getMonth(),(year),matcher.group(3),matcher.group(4),matcher.group(5));
					}
					attackList.add(attack);
				}

				inputLine = br.readLine();
			}
			System.out.println("nombre total d'attaques : "+number);
		}
		catch (FileNotFoundException e) {
			System.err.println("Error :file not found\""+path+"\"");
		}
		catch (IOException e) {
			System.err.println("error :read error on file\""+path+"\"");
		}

		catch (Exception e) {
			System.err.println("Error: inconnue");
			e.printStackTrace(System.err);
		}
		finally {
			if(fr!=null)
				try { fr.close(); } catch(Exception e) {};
				if(br!=null)
					try{ br.close(); } catch(Exception e) {};
		}
		mainBase.setDataBase(attackList);
	}

	public void sortPerCountry() throws IOException {
		sortPerIP();
		ArrayList<IPAddress> ipList = mainBase.getAttackIPAddressList();
		ArrayList<Country> countryList = new ArrayList<Country>();
		for(IPAddress ip : ipList) {
			boolean ind = false;
			for(Country country : countryList) {
				if(country.compareTo(ip))
					country.addIPAddress(ip);
			}
			if(!ind) {
				Country newCountry = new Country(ip.getCountry());
				countryList.add(newCountry);
			}
		}
		countryList = sortNumberCountry(countryList);
		mainBase.setAttackCountryList(countryList);
	}

	public void sortPerDay() {
		ArrayList<AttackDay> dayList = new ArrayList<AttackDay>();
		boolean ind;
		for(Attack att : mainBase.getAttackList()) {
			ind = false;
			for(AttackDay day : dayList) {
				if(day.compareTo(att))
					day.add(att);
			}
			if(!ind) {
				AttackDay newDay = new AttackDay(att.getDay()+"/"+att.getMonth()+"/"+att.getYear());
				dayList.add(newDay);
			}
		}
		dayList = sortAttackDay(dayList);
		mainBase.setAttackDayList(dayList);
	}

	public void sortPerGroupedAttack() {
		ArrayList<GroupedAttack> groupList = new ArrayList<GroupedAttack>();
		boolean ind;
		for(Attack att : mainBase.getAttackList()) {
			ind = false;
			for(GroupedAttack group : groupList) {
				if(group.compareTo(att)) 
					group.add(att);
			}
			if(!ind) {
				GroupedAttack newGroup = new GroupedAttack("");
				groupList.add(newGroup);
			}
		}
		groupList = sortNumberGroupedAttack(groupList);
		mainBase.setGroupedAttackList(groupList);
	}

	public void sortPerIP() {
		ArrayList<IPAddress> ipList = new ArrayList<IPAddress>();
		boolean ind;
		for(Attack att : mainBase.getAttackList()) {
			ind = false;
			for(IPAddress ip : ipList) {
				if(ip.compareTo(att)) ip.add(att);
			}
			if(!ind) {
				IPAddress newIP = new IPAddress(att.getIP());
				ipList.add(newIP);
			}
		}
		ipList = sortNumberIPAddress(ipList);
		mainBase.setAttackIPAddressList(ipList);
	}

	public void sortPerUsername() {
		ArrayList<AttackUsername> usernameList = new ArrayList<AttackUsername>();
		boolean ind;
		for(Attack att : mainBase.getAttackList()) {
			ind = false;
			for(AttackUsername username : usernameList) {
				if(username.compareTo(att)) username.add(att);
			}
			if(!ind) {
				AttackUsername newUsername = new AttackUsername(att.getUsername());
				usernameList.add(newUsername);
			}
		}
		usernameList = sortAttackUsername(usernameList);
		mainBase.setAttackUsernameList(usernameList);
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
				if(countryList.get(i).getNumber()>(countryList.get(j).getNumber()))
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

	public ArrayList<IPAddress> sortNumberIPAddress(ArrayList<IPAddress> argList) {
		ArrayList<IPAddress> ipAddressList = argList;
		int n = ipAddressList.size();
		int min = 0;
		for(int i = 0 ; i<(n-1) ;i++) {
			min = i;
			for(int j = i+1 ; j<n ; j++) {
				if(ipAddressList.get(i).getNumber()>(ipAddressList.get(j).getNumber()))
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

	public void update() {
		// TODO Auto-generated method stub

	}

	public void displayMap() {
		ArrayList<Country> countries = this.mainBase.getAttackCountryList();
		JMapPanel map = new JMapPanel(this.mainFrame);
		for(Country country : countries) {
			String currentCountry = "blablabla";
			CsvReader countriesReader = null;
			try {
				countriesReader = new CsvReader("dbcountries.csv");
				while(!currentCountry.equals(country)) {
					countriesReader.readRecord();
					currentCountry = countriesReader.get(0);
					System.out.println(country);
				}
				System.out.println(country);
				System.out.println(currentCountry);
				int attNb = country.getNumber();
				Double longitude = new Double(countriesReader.get(2).replace(",","."));
				Double latitude = new Double(countriesReader.get(3).replace(",","."));
				this.addPointToMap(map, new JMapPoint(latitude,longitude,attNb));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Adds a point on the map (if there are a lot of attacks big red, else small green). 
	 * @param map
	 * @param point
	 */
	@SuppressWarnings("unchecked")
	public void addPointToMap(JMapPanel map, JMapPoint point) {
		MapWaypointPainter<JXMapViewer> waypainter = (MapWaypointPainter<JXMapViewer>) map.getOverlayPainter();
		@SuppressWarnings("rawtypes")
		Set waypoints = new HashSet();
		if(waypainter!=null) {
			waypoints = waypainter.getWaypoints();
		}
		Double latitude = point.getLatitude();
		Double longitude = point.getLongitude();
		Integer nb = point.getNb();
		waypoints.add(new MapWaypoint(latitude,longitude,nb));
		MapWaypointPainter<JXMapViewer> painter = new MapWaypointPainter<JXMapViewer>();
		painter.setWaypoints(waypoints);
		painter.setRenderer(new MapWaypointRenderer());
		map.setOverlayPainter(painter);
	}

	public void openSubJFrame(String finalType, String nameRow) {
		// TODO Auto-generated method stub

	}

	// GETTERS
	public Parameters getParameters() { return this.parameters; }

	// SETTERS
	public void setParameters(Parameters parameters) { this.parameters = parameters; }

}
