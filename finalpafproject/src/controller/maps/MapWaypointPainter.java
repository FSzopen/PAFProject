package controller.maps;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.painter.AbstractPainter;


/**
 * Just like WaypointPainter
 * For further information please visit http://grepcode.com/file/repo1.maven.org/maven2/org.swinglabs/swingx-ws/1.0/org/jdesktop/swingx/mapviewer/WaypointPainter.java
 * 
 */

public class MapWaypointPainter<T extends JXMapViewer> extends AbstractPainter<T> {
	
	private MapWaypointRenderer renderer;
	private Set<MapWaypoint> waypoints;

	public MapWaypointPainter() {
		setAntialiasing(true);
		setCacheable(false);
		waypoints = new HashSet<MapWaypoint>();
	}

	public void setRenderer(MapWaypointRenderer r) {
		this.renderer = r;
	}

	public Set<MapWaypoint> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(Set<MapWaypoint> waypoints) {
		this.waypoints = waypoints;
	}

	@Override
	protected void doPaint(Graphics2D g, T map, int width, int height) {
		if(renderer == null) {
			return;
		}
		Rectangle viewportBounds = map.getViewportBounds();
		int zoom = map.getZoom();
		Dimension sizeInTiles = map.getTileFactory().getMapSize(zoom);
		int tileSize = map.getTileFactory().getTileSize(zoom);
		Dimension sizeInPixels = new Dimension(sizeInTiles.width*tileSize, sizeInTiles.height*tileSize);
		double vpx = viewportBounds.getX();
		while(vpx < 0) {
			vpx += sizeInPixels.getWidth();
		}
		while(vpx > sizeInPixels.getWidth()) {
			vpx -= sizeInPixels.getWidth();
		}
		Rectangle2D vp2 = new Rectangle2D.Double(vpx,
				viewportBounds.getY(),viewportBounds.getWidth(),viewportBounds.getHeight());
		Rectangle2D vp3 = new Rectangle2D.Double(vpx-sizeInPixels.getWidth(),
				viewportBounds.getY(),viewportBounds.getWidth(),viewportBounds.getHeight());
		try {
			for (MapWaypoint w : getWaypoints()) {
				Point2D point = map.getTileFactory().geoToPixel(w.getPosition(), map.getZoom());
				if(vp2.contains(point)) {
					int x = (int)(point.getX() - vp2.getX());
					int y = (int)(point.getY() - vp2.getY());
					g.translate(x,y);
					paintWaypoint(w, map, g);
					g.translate(-x,-y);
				}
				if(vp3.contains(point)) {
					int x = (int)(point.getX() - vp3.getX());
					int y = (int)(point.getY() - vp3.getY());
					g.translate(x,y);
					paintWaypoint(w, map, g);
					g.translate(-x,-y);
				}
			}
		}
		catch(Exception e) {

		}
	}

	protected void paintWaypoint(final MapWaypoint w, final T map, final Graphics2D g) {
		renderer.paintWaypoint(g, map, w);
	}
	
}