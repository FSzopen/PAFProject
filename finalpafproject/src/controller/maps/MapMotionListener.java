package controller.maps;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import ui.maps.JMapPanel;


public class MapMotionListener implements MouseMotionListener {

	private JMapPanel map;
	
	public MapMotionListener(JMapPanel map) {
		this.map = map;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	/**
	 * Ugly. Very ugly.
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {
		map.repaint();
	}

}
