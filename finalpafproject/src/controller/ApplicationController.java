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

import controller.maps.MapWaypoint;
import controller.maps.MapWaypointPainter;
import controller.maps.MapWaypointRenderer;
import database.AttackDate;
import database.Country;
import database.MainBase;
import database.Parameters;
import database.Attack;
import ui.MainFrame;
import ui.ParameterJFrame;
import ui.maps.JMapPanel;
import ui.maps.JMapPoint;

public class ApplicationController {

	@SuppressWarnings("unused")
	private MainFrame mainFrame;
	private Parameters parameters = new Parameters();
	private MainBase mainBase;

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

	public void sortPerCountry() {
		// TODO Auto-generated method stub

	}

	public void sortPerDay() {
		// TODO Auto-generated method stub

	}

	public void sortPerGroupedAttack() {
		// TODO Auto-generated method stub

	}

	public void sortPerIP() {
		// TODO Auto-generated method stub

	}

	public void sortPerUsername() {
		// TODO Auto-generated method stub

	}

	public void update() {
		// TODO Auto-generated method stub

	}

	public void displayMap() {
		ArrayList<Country> countries = this.mainBase.getAttackCountryList();
		JMapPanel map = new JMapPanel(this.mainFrame);
		for(Country country : countries) {
		}
	}

	/**
	 * Adds a point on the map (if there are a lot of attacks big red, else small green). 
	 * @param map
	 * @param point
	 */
	@SuppressWarnings({ "unchecked" })
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
