package controller.maps;

/**
 * Not a real implementation of WaypointRenderer...
 */

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import org.jdesktop.swingx.JXMapViewer;

public class MapWaypointRenderer {
	
	public boolean paintWaypoint(Graphics2D g, JXMapViewer map, MapWaypoint wp) {
		Image markerImg = null;
		if(wp.getNb() < 9000) {
			markerImg = new ImageIcon("small.png").getImage();
			g.setFont(g.getFont().deriveFont(Font.BOLD,14f));
			g.drawString(" " + wp.getNb().toString(), 0, 5);
			g.drawImage(markerImg,-16,-27,null);
		}
		else {
			markerImg = new ImageIcon("big.png").getImage();
			g.setFont(g.getFont().deriveFont(Font.BOLD,14f));
			g.drawString("  " + wp.getNb().toString(), 0, 5);
			g.drawImage(markerImg,-32,-58,null);
		}
		return true;
	}

}