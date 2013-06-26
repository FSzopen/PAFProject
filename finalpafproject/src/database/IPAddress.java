package database;

import java.io.IOException;
import com.csvreader.CsvReader;

public class IPAddress extends Classification {

	public final static String DATA_BASE = "GeoIPCountryWhois.csv";
	private String country;

	public IPAddress(String name) {
		super(name);
		this.setType("username");
	}

	public boolean compareTo(String name) {
		if(this.getName().equals(name))
			return true;
		else
			return false;
	}

	private String complete(String ip) {
		switch(ip.length()) {
		case 1 : return "00"+ip;
		case 2 : return "0"+ip;
		default : return ip;
		}
	}

	@Override
	public boolean compareTo(Attack attack) {
		return this.compareTo(attack.getIP());
	}

	@Override
	public boolean before(Classification classification) {
		if(this.before(classification.getName()))
			return true;
		else
			return false;
	}

	public boolean before (String ip) {
		String[] nameSplit = this.getName().split(":");
		String firstOctet = complete(nameSplit[0]);
		String secondOctet = complete(nameSplit[1]);
		String thirdOctet = complete(nameSplit[2]);
		String fourthOctet = complete(nameSplit[3]);
		String[] otherNameSplit = ip.split(":");
		String otherFirstOctet = complete(otherNameSplit[0]);
		String otherSecondOctet = complete(otherNameSplit[1]);
		String otherThirdOctet = complete(otherNameSplit[2]);
		String otherFourthOctet = complete(otherNameSplit[3]);
		if(Integer.parseInt(firstOctet+secondOctet+thirdOctet+fourthOctet)<Integer.parseInt(otherFirstOctet+otherSecondOctet+otherThirdOctet+otherFourthOctet))
			return true;
		else
			return false;
	}

	// GETTERS
	public String getCountry() { return country; }

	// SETTERS
	public void setCountry(String country) { this.country = country; }
	public void setCountry() throws IOException {
		String tempcountry = "Error: Unknown country!";
		//IpAddress initialisation
		String currentIp = ("0.0.0.0");
		//Database reader
		CsvReader dataBase = new CsvReader(DATA_BASE);
		//Database headers (ow I'm a poet!)
		String[] headers = new String[6];
		headers[0] = "Adr1";
		headers[1] = "Adr2";
		headers[2] = "Dec1";
		headers[3] = "Dec2";
		headers[4] = "CY";
		headers[5] = "Country";
		dataBase.setHeaders(headers);
		dataBase.readRecord();
		String ipBound = ("0.0.0.0");
		//TODO: clean condition
		while(1!=0) {
			tempcountry = dataBase.get("Country");
			if(!dataBase.get("Adr2").equals("Adr2"))
				ipBound = (dataBase.get("Adr2"));
			dataBase.readRecord();
			currentIp = (dataBase.get("Adr1"));
			if(!(this.before(currentIp))) {
				if(this.before(ipBound)) {
					dataBase.close();
					tempcountry = "Error: Unknown country!";
					country = tempcountry;
				}
				else {
					dataBase.close();
					country = tempcountry;
				}
			}
		}
	}

}