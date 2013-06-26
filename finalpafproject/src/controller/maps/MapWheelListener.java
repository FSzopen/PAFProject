package controller.maps;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import ui.maps.JMapPanel;
import org.jdesktop.swingx.mapviewer.GeoPosition;

public class MapWheelListener implements MouseWheelListener {

	private JMapPanel map;
	private int previousZoom = 15;
	
	public MapWheelListener(JMapPanel map) {
		this.map = map;
	}

	/**
	 * Zoom limitation.
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(map.getZoom() > 14) {
			map.setZoom(15);
			map.setAddressLocation(new GeoPosition(48.856578,2.351828));
		}
		if(map.getZoom() != previousZoom) {
			previousZoom = map.getZoom();
		}
		
	}

}
