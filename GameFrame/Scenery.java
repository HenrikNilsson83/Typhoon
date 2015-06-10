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
	public static AlphaMap alphaMap;
	private ObjectPool objectPool;


	public Scenery(TiledMap map,GameContainer gc,Color c, ObjectPool objPool) {
		this.objectPool = objPool;
		this.size = map.getTileHeight();
		isBlocked = new boolean[map.getWidth()][map.getHeight()];
		setBlocked(map);
		this.tiledmap =map;
		cam = new Camera(gc, this.tiledmap);
		spawnObjects(gc);
		this.container = gc;
		HEIGHT = map.getHeight();
		WIDTH = map.getWidth();
		alphaMap = new AlphaMap(gc,c, objectPool);
		
		ParticleFx pFx = new ParticleFx(0, 0, new Vector2f(0,0), this.objectPool);
		objectPool.addToNonCollisionPool(pFx);

	}

	public void Scenery(){

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

	public Scenery(){

	}

	private void spawnObjects(GameContainer gc) {

		//ArrayList<SimpleGameObject> fList =  op.getFriendlyList();
		for(int i=0;i<tiledmap.getObjectGroupCount();i++){

			// PLAYER_SPAWN
			if(tiledmap.getObjectName(i,0).equals("PlayerSpawn")){

				for(int j = 0;j<tiledmap.getObjectCount(i);j++){

					String tempString = tiledmap.getObjectProperty(i, j,"spawn" , "false");


					if(tempString.equals("player1")){

						int px = tiledmap.getObjectX(i, j);
						int py = tiledmap.getObjectY(i, j);
						SpaceExplorer p = new SpaceExplorer(64, 64, new Vector2f(px,py),gc, objectPool);
						p.setFaction(1);
						objectPool.addToCollisionPool(p);
						objectPool.mainChar = p;
						p.setUpGUI();
						this.focus = p;
					}
				}
			}

			
			// ENEMY SPAWN
			if(tiledmap.getObjectName(i,0).equals("EnemySpawn")){
				for(int j = 0;j<tiledmap.getObjectCount(i);j++){

					String tempString = tiledmap.getObjectProperty(i, j,"spawn" , "false");

					if(tempString.equals("enemy")){

						int px = tiledmap.getObjectX(i, j);
						int py = tiledmap.getObjectY(i, j);
						objectPool.addToCollisionPool(new Patrol(64, 64, new Vector2f(px,py),container, objectPool));
					}
					if(tempString.equals("2")){
					}
				}
			}

			if(tiledmap.getObjectName(i,0).equals("EnemySpawn")){
				for(int j = 0;j<tiledmap.getObjectCount(i);j++){

					String tempString = tiledmap.getObjectProperty(i, j,"spawn" , "false");

					if(tempString.equals("robotcop")){

						int px = tiledmap.getObjectX(i, j);
						int py = tiledmap.getObjectY(i, j);
						objectPool.addToCollisionPool(new SuperCop(64, 64, new Vector2f(px,py),container, objectPool));
					}
					if(tempString.equals("2")){
					}
				}
			}
			
			if(tiledmap.getObjectName(i,0).equals("EnemySpawn")){
				for(int j = 0;j<tiledmap.getObjectCount(i);j++){

					String tempString = tiledmap.getObjectProperty(i, j,"spawn" , "false");

					if(tempString.equals("angel")){

						int px = tiledmap.getObjectX(i, j);
						int py = tiledmap.getObjectY(i, j);
						objectPool.addToCollisionPool(new Angel(64, 64, new Vector2f(px,py),container, objectPool));
					}
					if(tempString.equals("2")){
					}
				}
			}

			if(tiledmap.getObjectName(i,0).equals("EnemySpawn")){
				for(int j = 0;j<tiledmap.getObjectCount(i);j++){

					String tempString = tiledmap.getObjectProperty(i, j,"spawn" , "false");

					if(tempString.equals("birdbear")){

						int px = tiledmap.getObjectX(i, j);
						int py = tiledmap.getObjectY(i, j);
						objectPool.addToCollisionPool(new BirdBear(64, 64, new Vector2f(px,py),container, objectPool));
					}
					if(tempString.equals("2")){
					}
				}
			}
			
			if(tiledmap.getObjectName(i,0).equals("EnemySpawn")){
				for(int j = 0;j<tiledmap.getObjectCount(i);j++){

					String tempString = tiledmap.getObjectProperty(i, j,"spawn" , "false");

					if(tempString.equals("mrgray")){

						int px = tiledmap.getObjectX(i, j);
						int py = tiledmap.getObjectY(i, j);
						objectPool.addToCollisionPool(new MrGray(128, 128, new Vector2f(px,py),container, objectPool));
					}
					if(tempString.equals("2")){
					}
				}
			}
			// MOVING PLATFORM
			if(tiledmap.getObjectName(i,0).equals("EnemySpawn")){
				for(int j = 0;j<tiledmap.getObjectCount(i);j++){

					String tempString = tiledmap.getObjectProperty(i, j,"spawn" , "false");

					if(tempString.equals("movingplattform")){

						int px = tiledmap.getObjectX(i, j);
						int py = tiledmap.getObjectY(i, j);
						objectPool.addToCollisionPool(new MovingPlattform(128, 5, new Vector2f(px,py), objectPool));
					}
					if(tempString.equals("2")){
					}
				}
			}

			//Items spawn
			if(tiledmap.getObjectName(i,0).equals("PickUpSpawn")){

				for(int j = 0;j<tiledmap.getObjectCount(i);j++){

					String tempString = tiledmap.getObjectProperty(i, j,"spawn" , "false");

					if(tempString.equals("pickup")){
						int px = tiledmap.getObjectX(i, j);
						int py = tiledmap.getObjectY(i, j);
						objectPool.addToCollisionPool(new PickUpItem(64, 64, new Vector2f(px,py),container, objectPool));
					}
					if(tempString.equals("2")){
					}
				}
			}

			if(tiledmap.getObjectName(i,0).equals("PlayerSpawn")){

				for(int j = 0;j<tiledmap.getObjectCount(i);j++){

					String tempString = tiledmap.getObjectProperty(i, j,"spawn" , "false");

					if(tempString.equals("Army")){

						/*int px = tiledmap.getObjectX(i, j);
						int py = tiledmap.getObjectY(i, j);
						SmallArmy p = new SmallArmy(32, 32, new Vector2f(px,py),gc);
						p.setFaction(1);
						op.addToPool(p);*/
					}
				}
			}

		}

	}



	public Scenery(TiledMap tileMap) {
		this.size = tileMap.getTileHeight();
		isBlocked = new boolean[tileMap.getWidth()][tileMap.getHeight()];
		setBlocked(tileMap);


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

	public void update(int delta){
		
	}

	

	public void updateLight(float multi,int delta){
		this.alphaMap.update(multi, delta);
	}



	public void render(GameContainer gc,Graphics g){
		centerOn();
		cam.drawMap2();
		cam.translateGraphics();

	}

	public void drawLightningsAndForGround(GameContainer gc, Graphics g) {


		cam.untranslateGraphics();
		cam.drawForGround();
		cam.translateGraphics();
		//renderLight(gc,g);

	}
	public void renderLight(GameContainer gc, Graphics g){
		//ObjectPool op = new ObjectPool();
		//ArrayList<Light>lightList = op.getLights();
		//alphaMap.render((int)(cam.getLightX()),(int)(cam.getLightY()),lightList);
		//alphaMap.setList(lightList);
	}



	public void centerOn() {
		cam.centerOn((this.focus.gamePosition.x), (this.focus.gamePosition.y));
	}

	public int getXOffSet() {

		return (int)cam.cameraX;
	}
	public int getYOffSet() {

		return (int)cam.cameraY;
	}
}
