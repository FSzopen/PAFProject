package ui.maps;

public class JMapPoint {

	private double latitude;
	private double longitude;
	private Integer nb = 0;

	/**
	 * Creates a new point.
	 * @param latitude
	 * @param longitude
	 * @param attacksNb
	 */
	public JMapPoint(double latitude, double longitude, Integer attacksNb) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.nb = attacksNb;
	}

	/**
	 * Creates a new point. CAUTIOUS : nb MUST be a number.
	 * @param latitude
	 * @param longitude
	 * @param nb
	 */
	public JMapPoint(double latitude, double longitude, String nb) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.nb = new Integer(nb);
	}

	// Getters
	public double getLatitude() { return this.latitude; }
	public double getLongitude() { return this.longitude; }
	public Integer getNb() { return this.nb; }

}
