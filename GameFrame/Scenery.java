import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class Scenery {
	private static int size;
	private static boolean[][] isBlocked;
	private static ArrayList<Vector2f> movingBlocks;
	private TiledMap tiledmap;
	private static Camera cam;
	SimpleGameObject focus;
	GameContainer container;
	public static int HEIGHT;
	public static int WIDTH;
	
	private ObjectPool objectPool;


	public Scenery(TiledMap map,GameContainer gc, ObjectPool objPool) {
		this.objectPool = objPool;
		this.size = map.getTileHeight();
		isBlocked = new boolean[map.getWidth()][map.getHeight()];
		setBlocked(map);
		this.tiledmap =map;
		cam = new Camera(gc, this.tiledmap);
		this.container = gc;
		HEIGHT = map.getHeight();
		WIDTH = map.getWidth();
		
	}

	public Scenery(){
	
	}

	public boolean getBlocked(int x,int y){
		if(x>=WIDTH||y>=HEIGHT||x<0||y<0){
			return true;
		}
		return this.isBlocked[x][y];
	}

	public boolean getMovingBlocked(float x,float y){
		Rectangle object = new Rectangle(x,y,1,1);
		Rectangle block;
		if(x>=WIDTH||y>=HEIGHT||x<0||y<0){
			for(int i = 0;i<this.movingBlocks.size();i++){
				block = new Rectangle(movingBlocks.get(i).x,movingBlocks.get(i).y,size,size);
				if(block.intersects(object)){
					return true;
				}
			}
		}

		return false;
	} 

	public void translateGFX(){
		cam.translateGraphics();
	}
	public void unTranslateGFX(){
		cam.untranslateGraphics();
	}

	private void setBlocked(TiledMap tileMap) {
		for (int x = 0; x < tileMap.getWidth(); x++) {

			for (int y = 0; y < tileMap.getHeight(); y++) {
				for (int i = 0; i < tileMap.getLayerCount(); i++) {
					int tileID = tileMap.getTileId(x, y, i);
					String value = tileMap.getTileProperty(tileID, "blocked","false");
					if ("true".equals(value)) {
						isBlocked[x][y] = true;
					} 
				}
			}
		}
	}

	public static boolean[][] getBlocked(){
		return isBlocked;
	}

	public void render(GameContainer gc,Graphics g){
		centerOn();
		cam.drawMap2();
		cam.translateGraphics();

	}

	public void drawForGround(GameContainer gc, Graphics g) {
		cam.untranslateGraphics();
		cam.drawForGround();
		cam.translateGraphics();

	}
	
	public void centerOn() {
		cam.centerOn((objectPool.mainChar.gamePosition.x), (objectPool.mainChar.gamePosition.y));
	}

	public int getXOffSet() {

		return (int)cam.cameraX;
	}
	public int getYOffSet() {

		return (int)cam.cameraY;
	}
}
