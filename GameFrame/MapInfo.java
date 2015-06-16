import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class MapInfo {
	private static int size;
	private static boolean[][] isBlocked;
	private static BlockInfo[][] blockinfo;
	private static ArrayList<Vector2f> movingBlocks;
	private TiledMap tiledmap;
	
	SimpleGameObject focus;
	GameContainer container;
	public static int HEIGHT;
	public static int WIDTH;
	
	private ObjectPool objectPool;


	public MapInfo(TiledMap map,GameContainer gc, ObjectPool objPool) {
		this.objectPool = objPool;
		this.size = map.getTileHeight();
		isBlocked = new boolean[map.getWidth()][map.getHeight()];
		blockinfo = new BlockInfo[map.getWidth()][map.getHeight()];
		setBlocked(map);
		this.tiledmap =map;
		this.container = gc;
		HEIGHT = map.getHeight();
		WIDTH = map.getWidth();
		
	}

	public MapInfo(){
	
	}

	public boolean getBlocked(int x,int y){
		if(x>=WIDTH||y>=HEIGHT||x<0||y<0){
			return true;
		}
		return this.isBlocked[x][y];
		//return this.blockinfo[x][y].getBlocked();
		//return blockinfo[x][y].southObs;
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

	

	private void setBlocked(TiledMap tileMap) {
		for (int x = 0; x < tileMap.getWidth(); x++) {

			for (int y = 0; y < tileMap.getHeight(); y++) {
				for (int i = 0; i < tileMap.getLayerCount(); i++) {
					int tileID = tileMap.getTileId(x, y, i);
					String value = tileMap.getTileProperty(tileID, "blocked","false");
					if ("true".equals(value)) {
						isBlocked[x][y] = true;
						blockinfo[x][y] = new BlockInfo(true);
						blockinfo[x][y].setAll(true);
						blockinfo[x][y].southObs = true;
					}
					else{
						blockinfo[x][y] = new BlockInfo(false);
						this.blockinfo[x][y].southObs = false;
					}
				}
			}
		}
	}

	public static boolean[][] getBlocked(){
		return isBlocked;
	}

	
}
