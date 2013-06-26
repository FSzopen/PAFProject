package controller.maps;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import org.jdesktop.swingx.JXMapViewer;

public class MapListener implements MouseListener {
	
	private JXMapViewer pan1;
	//private boolean first = true;
	private Point2D previous;
	
	public MapListener(JXMapViewer pan1) {
		this.pan1 = pan1;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	    /*System.out.println(pan1.getViewportBounds());
	    System.out.println(pan1.getBounds());
	    System.out.println(pan1.getZoom());*/
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	/**
	 * Saves center position when mouse pressed. 
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		previous = pan1.getCenter();
		//System.out.println("SAVED POSITION: " + previous);
	}

	/**
	 * If user is out of bounds, gets back to the previous position.
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Rectangle rec = pan1.getViewportBounds();
		double y = rec.getY();
		if(y < 0) {
			pan1.setCenter(previous);
			//System.out.println("PREVIOUS POSITION: " + previous);
			
		}

	}

}