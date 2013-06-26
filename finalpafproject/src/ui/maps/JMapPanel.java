package ui.maps;

import javax.swing.JFrame;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import controller.maps.MapListener;
import controller.maps.MapMotionListener;
import controller.maps.MapTileFactory;
import controller.maps.MapWheelListener;

@SuppressWarnings("serial")
public class JMapPanel extends JXMapViewer {

	// private int previousZoom = 15;
	private JFrame mainFrame;
	
	/**
	 * Creates a map. Tiles come from http://b.tile.openstreetmap.org, default zoom is 15.
	 * @param mainFrame
	 */
	public JMapPanel(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.setZoom(15);
		this.setCenterPosition(new GeoPosition(0,0));
		TileFactoryInfo infos = new TileFactoryInfo(1, 17, 17, 256, true, true, "http://b.tile.openstreetmap.org", "x", "y", "z"){
			public String getTileUrl(int x, int y, int zoom) {
				zoom = 17-zoom;
				return this.baseURL +"/"+zoom+"/"+x+"/"+y+".png";
			}
		};
		this.setTileFactory(new MapTileFactory(infos));
		this.addMouseListener(new MapListener(this));
		this.addMouseWheelListener(new MapWheelListener(this));
		this.addMouseMotionListener(new MapMotionListener(this));
	}
	
	// GETTERS
	public JFrame getMainFrame() { return this.mainFrame; }
	
}