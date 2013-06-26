package controller.maps;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Comparator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.mapviewer.Tile;
import org.jdesktop.swingx.mapviewer.TileCache;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.jdesktop.swingx.mapviewer.util.GeoUtil;

/**
 * Not homemade. :-(
 * @author https://java.net/nonav/projects/swingx-ws/sources/svn/content/trunk/src/java/org/jdesktop/swingx/mapviewer/AbstractTileFactory.java?rev=315
 */

public class MapTileFactory extends TileFactory {

	private static final Logger LOG = Logger.getLogger(MapTileFactory.class.getName()); 
	static {
		LOG.setLevel(Level.OFF);
	}

	/**
	 * Creates a new instance of DefaultTileFactory using the specified TileFactoryInfo
	 * @param info a TileFactoryInfo to configure this TileFactory
	 */
	public MapTileFactory(TileFactoryInfo info) {
		super(info);
	}

	private int threadPoolSize = 4;
	private ExecutorService service;    

	//TODO the tile map should be static ALWAYS, regardless of the number
	//of GoogleTileFactories because each tile is, really, a singleton.
	private WeakHashMap<String, Tile> tileMap = new WeakHashMap<String, Tile>();

	private TileCache cache = new TileCache();
	
	/**
	 * Returns the tile that is located at the given tilePoint for this zoom. For example,
	 * ifgetMapSize() returns 10x20 for this zoom, and the tilePoint is (3,5), then the
	 * appropriate tile will be located and returned.
	 * @param tilePoint
	 * @param zoom
	 * @return
	 */
	public Tile getTile(int x, int y, int zoom) {
		return getTile(x, y , zoom, true);
	}


	private Tile getTile(int tpx, int tpy, int zoom, boolean eagerLoad) {
		//wrap the tiles horizontally --> mod the X with the max width
		//and use that
		int tileX = tpx;//tilePoint.getX();
		int numTilesWide = (int)getMapSize(zoom).getWidth();
		if(tileX < 0) {
			tileX = numTilesWide - (Math.abs(tileX)  % numTilesWide);
		}

		tileX = tileX % numTilesWide;
		int tileY = tpy;
		//TilePoint tilePoint = new TilePoint(tileX, tpy);
		String url = getInfo().getTileUrl(tileX, tileY, zoom);//tilePoint);
		//System.out.println("loading: " + url);


		Tile.Priority pri = Tile.Priority.High;
		if(!eagerLoad) {
			pri = Tile.Priority.Low;
		}
		Tile tile = null;
		//System.out.println("testing for validity: " + tilePoint + " zoom = " + zoom);
		if(!tileMap.containsKey(url)) {
			if(!GeoUtil.isValidTile(tileX, tileY, zoom, getInfo())) {
				tile = new Tile(tileX, tileY, zoom);
			}
			else {
				Constructor<Tile> constructor = null;
				@SuppressWarnings("rawtypes")
				Class[] cArg = new Class[6];
				cArg[0] = int.class;
				cArg[1] = int.class;
				cArg[2] = int.class;
				cArg[3] = String.class;
				cArg[4] = Tile.Priority.class;
				cArg[5] = TileFactory.class;
				try {
					constructor = Tile.class.getDeclaredConstructor(cArg);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				constructor.setAccessible(true);
				try {
					tile = constructor.newInstance(tileX, tileY, zoom, url, pri, this);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// tile = new Tile(tileX, tileY, zoom, url, pri, this);
				startLoading(tile);
			}
			tileMap.put(url,tile);
		}  else {
			tile = tileMap.get(url);
			// ifits in the map but is low and isn't loaded yet
			// but we are in high mode
			if(tile.getPriority()  == Tile.Priority.Low && eagerLoad && !tile.isLoaded()) {
				//System.out.println("in high mode and want a low");
				//tile.promote();
				promote(tile);
			}
		}
		return tile;
	}

	public TileCache getTileCache() { return cache; }

	public void setTileCache(TileCache cache) { this.cache = cache; }

	@SuppressWarnings("unchecked")
	public void clearTileCache() {
		((Map<String, Tile>) cache).clear();
		tileMap.clear();
	}

	public void shutdownTileServicePool() {
		if(service != null) {
			service.shutdownNow();
			service = null;
		}
	}

	/**
	 * Thread pool for loading the tiles
	 */
	private static BlockingQueue<Tile> tileQueue = new PriorityBlockingQueue<Tile>(5,
			new Comparator<Tile>() {
		public int compare(Tile o1, Tile o2) {
			if(o1.getPriority() == Tile.Priority.Low && o2.getPriority() == Tile.Priority.High) {
				return 1;
			}
			if(o1.getPriority() == Tile.Priority.High && o2.getPriority() == Tile.Priority.Low ) {
				return -1;
			}
			return 0;
		}

		@Override
		public boolean equals(Object obj) {
			return obj == this;
		}
	});

	/**
	 * Subclasses may override this method to provide their own executor services. This 
	 * method will be called each time a tile needs to be loaded. Implementations should 
	 * cache the ExecutorService when possible.
	 * @return ExecutorService to load tiles with
	 */
	protected synchronized ExecutorService getService() {
		if(service == null) {
			//System.out.println("creating an executor service with a threadpool of size " + threadPoolSize);
			service = Executors.newFixedThreadPool(threadPoolSize, new ThreadFactory() {
				private int count = 0;

				public Thread newThread(Runnable r) {
					Thread t = new Thread(r, "tile-pool-" + count++);
					t.setPriority(Thread.MIN_PRIORITY);
					t.setDaemon(true);
					return t;
				}
			});
		}
		return service;
	}



	/**
	 * Set the number of threads to use for loading the tiles. This controls the number of threads
	 * used by the ExecutorService returned from getService(). Note, this method should
	 * be called before loading the first tile. Calls after the first tile are loaded will
	 * have no effect by default.
	 * @param size 
	 */
	public void setThreadPoolSize(int size) {
		if(size <= 0) {
			throw new IllegalArgumentException("size invalid: " + size + ". The size of the threadpool must be greater than 0.");
		}
		threadPoolSize = size;
	}


	protected synchronized void startLoading(Tile tile) {
		if(tile.isLoading()) {
			//System.out.println("already loading. bailing");
			return;
		}
		tile.setLoading(true);
		try {
			tileQueue.put(tile);
			getService().submit(createTileRunner(tile));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Subclasses can override this if they need custom TileRunners for some reason
	 * @return
	 */
	protected Runnable createTileRunner(Tile tile) {
		return new TileRunner();
	}


	/**
	 * Increase the priority of this tile so it will be loaded sooner.
	 */
	public synchronized void promote(Tile tile) {
		if(tileQueue.contains(tile)) {
			try {
				tileQueue.remove(tile);
				tile.setPriority(Tile.Priority.High);
				tileQueue.put(tile);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * An inner class which actually loads the tiles. Used by the thread queue. Subclasses
	 * can override this ifnecessary.
	 */
	private class TileRunner implements Runnable {

		/**
		 * Gets the full URI of a tile.
		 * @param tile
		 * @throws java.net.URISyntaxException
		 * @return
		 */
		protected URI getURI(Tile tile) throws URISyntaxException {
			if(tile.getURL() == null) {
				return null;
			}
			return new URI(tile.getURL());
		}
		/**
		 * implementation of the Runnable interface.
		 */
		public void run() {
			/*
			 * 3 strikes and you're out. Attempt to load the url. ifit fails,
			 * decrement the number of tries left and try again. Log failures.
			 * ifI run out of try s just get out. This way, ifthere is some
			 * kind of serious failure, I can get out and let other tiles
			 * try to load.
			 */
			final Tile tile = tileQueue.remove();

			int trys = 3;
			while (!tile.isLoaded() && trys > 0) {
				try {
					BufferedImage img = null;
					URI uri = getURI(tile);
					img = cache.get(uri);
					if(img == null) {
						byte[] bimg = cacheInputStream(uri.toURL());
						img = GraphicsUtilities.loadCompatibleImage(new ByteArrayInputStream(bimg));//ImageIO.read(new URL(tile.url));
						cache.put(uri,bimg,img);
						img = cache.get(uri);
					}
					if(img == null) {
						//System.out.println("error loading: " + uri);
						LOG.log(Level.INFO, "Failed to load: " + uri);
						trys--;
					} else {
						final BufferedImage i = img;
						SwingUtilities.invokeAndWait(new Runnable() {
							public void run() {
								Field image = null;
								try {
									image = tile.getClass().getDeclaredField("image");
								} catch (SecurityException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (NoSuchFieldException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								image.setAccessible(true);
								try {
									image.set(tile,new SoftReference<BufferedImage>(i));
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Field loaded = null;
								try {
									loaded = tile.getClass().getDeclaredField("loaded");
								} catch (SecurityException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (NoSuchFieldException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								loaded.setAccessible(true);
								try {
									loaded.set(tile,true);
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//tile.image = new SoftReference<BufferedImage>(i);
								//tile.setLoaded(true);
							}
						});
					}
				} catch (OutOfMemoryError memErr) {
					cache.needMoreMemory();
				} catch (Throwable e) {
					LOG.log(Level.SEVERE,
							"Failed to load a tile at url: " + tile.getURL() + ", retrying", e);
					Object oldError = tile.getError();
					tile.setError(e);
					Method firePropertyChangeOnEDT = null;
					@SuppressWarnings("rawtypes")
					Class[] cArg1 = new Class[3];
					cArg1[0] = String.class;
					cArg1[1] = Object.class;
					cArg1[2] = Object.class;
					try {
						firePropertyChangeOnEDT = Tile.class.getDeclaredMethod("firePropertyChangeOnEDT", cArg1);
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchMethodException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					firePropertyChangeOnEDT.setAccessible(true);
					try {
						firePropertyChangeOnEDT.invoke(tile, "loadingError", oldError, e);
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//tile.firePropertyChangeOnEDT("loadingError", oldError, e);
					if(trys == 0) {
						try {
							firePropertyChangeOnEDT.invoke(tile, "unrecoverableError", null, e);
						} catch (IllegalArgumentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvocationTargetException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// tile.firePropertyChangeOnEDT("unrecoverableError", null, e);
					} else {
						trys--;
					}
				}
			}
			tile.setLoading(false);
		}

		private byte[] cacheInputStream(URL url) throws IOException {
			InputStream ins = url.openStream();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buf = new byte[256];
			while(true) {
				int n = ins.read(buf);
				if(n == -1) break;
				bout.write(buf,0,n);
			}
			return bout.toByteArray();
		}

	}
}