
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class Camera {

	/** the map used for our scene */
	protected TiledMap map;

	/** the number of tiles in x-direction (width) */
	protected int numTilesX;

	/** the number of tiles in y-direction (height) */
	protected int numTilesY;

	/** the height of the map in pixel */
	protected int mapHeight;

	/** the width of the map in pixel */
	protected int mapWidth;

	/** the width of one tile of the map in pixel */
	protected int tileWidth;

	/** the height of one tile of the map in pixel */
	protected int tileHeight;

	/** the GameContainer, used for getting the size of the GameCanvas */
	protected static GameContainer gc;

	/** the x-position of our "camera" in pixel */
	public static float cameraX;

	/** the y-position of our "camera" in pixel */
	public static float cameraY;

	protected static float displayWidth;
	protected static float displayHeight;
	protected float myMapWidth;
	protected float myMapHeight;

	/**
	 * Create a new camera
	 * 
	 * @param gc
	 *            the GameContainer, used for getting the size of the GameCanvas
	 * @param map
	 *            the TiledMap used for the current scene
	 */
	public Camera(GameContainer gc, TiledMap map) {
		this.map = map;
		this.numTilesX = map.getWidth();
		this.numTilesY = map.getHeight();

		this.tileWidth = map.getTileWidth();
		this.tileHeight = map.getTileHeight();

		this.mapHeight = this.numTilesY * this.tileWidth;
		this.mapWidth = this.numTilesX * this.tileWidth;

		this.gc = gc;
		displayWidth = gc.getWidth();
		displayHeight = gc.getHeight();
		myMapWidth = map.getWidth() * map.getTileWidth();
		myMapHeight = map.getHeight() * map.getTileHeight();
	}



	public Camera() {
		
	}



	/**
	 * "locks" the camera on the given coordinates. The camera tries to keep the
	 * location in it's center.
	 * 
	 * @param x
	 *            the real x-coordinate (in pixel) which should be centered on
	 *            the screen
	 * @param y
	 *            the real y-coordinate (in pixel) which should be centered on
	 *            the screen
	 */

	public void centerOn(float x, float y) {
		// try to set the given position as center of the camera by default

		if (true) {
			
			cameraX = x - gc.getWidth() / 2;
			cameraY = y - (gc.getHeight() / 2);

			// Check Camera X
			if (cameraX < 0)
				cameraX = 0;

			else if ((cameraX) > myMapWidth - displayWidth)
				cameraX = myMapWidth - displayWidth;

			// Check Camera Y
			if (cameraY < 0)
				cameraY = 0;

			else if ((cameraY) > (myMapHeight - displayHeight))
				cameraY = myMapHeight - displayHeight;
		}
		

	}

	/**
	 * "locks" the camera on the center of the given Rectangle. The camera tries
	 * to keep the location in it's center.
	 * 
	 * @param x
	 *            the x-coordinate (in pixel) of the top-left corner of the
	 *            rectangle
	 * @param y
	 *            the y-coordinate (in pixel) of the top-left corner of the
	 *            rectangle
	 * @param height
	 *            the height (in pixel) of the rectangle
	 * @param width
	 *            the width (in pixel) of the rectangle
	 */
	public void centerOn(float x, float y, float height, float width) {
		this.centerOn(x + width / 2, y + height / 2);
	}

	/**
	 * "locks the camera on the center of the given Shape. The camera tries to
	 * keep the location in it's center.
	 * 
	 * @param shape
	 *            the Shape which should be centered on the screen
	 */
	public void centerOn(Shape shape) {
		this.centerOn(shape.getCenterX(), shape.getCenterY());
	}

	/**
	 * draws the part of the map which is currently focussed by the camera on
	 * the screen
	 * 
	 * @return
	 */
	public Vector2f drawMap2() {
		return this.drawMap2(0, 0);
	}

	public void drawMap() {
		//this.drawMap(0, 0);
	}

	/**
	 * draws the part of the map which is currently focussed by the camera on
	 * the screen.<br>
	 * You need to draw something over the offset, to prevent the edge of the
	 * map to be displayed below it<br>
	 * Has to be called before Camera.translateGraphics() !
	 * 
	 * @param offsetX
	 *            the x-coordinate (in pixel) where the camera should start
	 *            drawing the map at
	 * @param offsetY
	 *            the y-coordinate (in pixel) where the camera should start
	 *            drawing the map at
	 */

	public Vector2f drawMap2(int offsetX, int offsetY) {

		// calculate the offset to the next tile (needed by TiledMap.render())
		int tileOffsetX = (int) -(cameraX % tileWidth);
		int tileOffsetY = (int) -(cameraY % tileHeight);

		// calculate the index of the leftmost tile that is being displayed
		int tileIndexX = (int) (cameraX / tileWidth);
		int tileIndexY = (int) (cameraY / tileHeight);

		// finally draw the section of the map on the screen
		
		
		Graphics g = new Graphics();
		g.flush();
		g.setDrawMode(g.MODE_NORMAL);
		map.render((int)(tileOffsetX + offsetX),
				(int)(tileOffsetY + offsetY), (int)(tileIndexX),
				(int)(tileIndexY),
				(int)((gc.getWidth() - tileOffsetX) / tileWidth + 1),
				(int)((gc.getHeight() - tileOffsetY) / tileHeight + 1));
		g.setDrawMode(g.MODE_NORMAL);
		return new Vector2f(tileOffsetX, tileOffsetY);

	}

	/*public void drawMap(int offsetX, int offsetY) {
		// calculate the offset to the next tile (needed by TiledMap.render())
		int tileOffsetX = (int) -(cameraX % tileWidth);
		int tileOffsetY = (int) -(cameraY % tileHeight);

		// calculate the index of the leftmost tile that is being displayed
		int tileIndexX = (int) (cameraX / tileWidth);
		int tileIndexY = (int) (cameraY / tileHeight);

		// finally draw the section of the map on the screen

		map.render((int)(tileOffsetX + offsetX),
				(int)(tileOffsetY + offsetY), (int)(tileIndexX),
				(int)(tileIndexY),
				(int)((gc.getWidth() - tileOffsetX) / tileWidth + 1),
				(int)((gc.getHeight() - tileOffsetY) / tileHeight + 1));

	}*/

	/**
	 * Translates the Graphics-context to the coordinates of the map - now
	 * everything can be drawn with it's NATURAL coordinates.
	 */
	public void translateGraphics() {
		
		gc.getGraphics().translate(-cameraX, -cameraY);
		
		// gc.getGraphics().draw(new Rectangle(50,50,50,50));
	}

	/**
	 * Reverses the Graphics-translation of Camera.translatesGraphics(). Call
	 * this before drawing HUD-elements or the like
	 */
	public void untranslateGraphics() {
		
		gc.getGraphics().translate(cameraX, cameraY);
		
	}

	public int getLightX() {
		return (int)(cameraX);
	}

	public int getLightY() {
		return (int)(cameraY);
	}



	public Vector2f drawForGround(int offsetX, int offsetY) {

		Graphics g = new Graphics();
		g.flush();
		g.setDrawMode(g.MODE_NORMAL);
		// calculate the offset to the next tile (needed by TiledMap.render())
		int tileOffsetX = (int) -(cameraX % tileWidth);
		int tileOffsetY = (int) -(cameraY % tileHeight);

		// calculate the index of the leftmost tile that is being displayed
		int tileIndexX = (int) (cameraX / tileWidth);
		int tileIndexY = (int) (cameraY / tileHeight);

		// finally draw the section of the map on the screen
		
		map.render((int)(tileOffsetX + offsetX),
				(int)(tileOffsetY + offsetY), (int)(tileIndexX),
				(int)(tileIndexY),
				(int)((gc.getWidth() - tileOffsetX) / tileWidth + 1),
				(int)((gc.getHeight() - tileOffsetY) / tileHeight + 1),map.getLayerCount()-1,true);
		g.setDrawMode(g.MODE_NORMAL);
		return new Vector2f(tileOffsetX, tileOffsetY);


	}



	public void drawForGround() {
		drawForGround(0,0);
		
	}
	
	public float getDisplayWidth(){
		return this.displayWidth;
	}
	public float getDisplayHeight(){
		return this.displayHeight;
	}



	

}