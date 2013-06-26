package controller.maps;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;

/**
 * Same as Waypoint.
 *
 */

public class MapWaypoint extends Waypoint {
	
	private Integer nb;

	public MapWaypoint(String nb) {
		super();
		this.nb = new Integer(nb);
		// TODO Auto-generated constructor stub
	}

	public MapWaypoint(GeoPosition coord, String nb) {
		super(coord);
		this.nb = new Integer(nb);
		// TODO Auto-generated constructor stub
	}

	public MapWaypoint(double latitude, double longitude, String nb) {
		super(latitude, longitude);
		this.nb = new Integer(nb);
		// TODO Auto-generated constructor stub
	}
	
	public MapWaypoint(Integer nb) {
		super();
		this.nb = nb;
		// TODO Auto-generated constructor stub
	}

	public MapWaypoint(GeoPosition coord, Integer nb) {
		super(coord);
		this.nb = nb;
		// TODO Auto-generated constructor stub
	}

	public MapWaypoint(double latitude, double longitude, Integer nb) {
		super(latitude, longitude);
		this.nb = nb;
		// TODO Auto-generated constructor stub
	}
	
	public Integer getNb() { return this.nb; }

}