import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;


public abstract class TiledMapGameState extends BasicGameState {

	protected ObjectPool pool;
	protected MapInfo scenery;//THE SCENERY OF THE GAME
	protected ResourceHandler resourceHandler;
	protected TiledMap tiledmap;
	protected LightFX lFx;
	protected SimpleGUI GUI;
	protected DynamicBlock dynamicBlocks[][];
	Conductor cond;
	private static Camera cam;

	//ABSTRACT CLASSES
	protected abstract void setUpResources();
	protected abstract void spawnGameObjects(GameContainer gc);
	protected Color light;
	protected boolean useLightFX;
	public abstract void updateState(GameContainer gc, StateBasedGame arg1, int delta);
	public abstract void renderState(GameContainer gc, StateBasedGame arg1, Graphics g);
	public abstract void initState(GameContainer gc, StateBasedGame arg1);
	public abstract String getTiledMapName();
	protected abstract GUI getGUI(GameContainer gc);

	public TiledMapGameState(){
		light = new Color(0.05f,0.05f,0.2f);
		cond = new Conductor();
		setUpResources();
	}

	public void init(GameContainer gc, StateBasedGame arg1){

		pool = new ObjectPool();
		lFx = new LightFX(gc,light);
		String s = this.getTiledMapName();
		if(!s.contains(".tmx")){
			s +=".tmx"; 
		}
		TileMapResource tmr = (TileMapResource) this.resourceHandler.get(s);
		this.tiledmap = tmr.getMap();
		dynamicBlocks = new DynamicBlock[tiledmap.getWidth()][tiledmap.getHeight()];
		scenery = new MapInfo(tiledmap,gc,pool);
		cam = new Camera(gc, this.tiledmap);
		this.spawnGameObjects(gc);
		this.initState(gc, arg1);
		GUI = this.getGUI(gc);
		pool.addToNonCollisionPool(new DynamicBlockManager(tiledmap.getWidth(), tiledmap.getHeight(), new Vector2f(0,0), gc, pool, dynamicBlocks));
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		pool.update(gc, delta,sbg);
		GUI.guiContent(gc, delta);
		this.updateState(gc, sbg, delta);
	}

	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)throws SlickException {
		centerOn();
		cam.drawMap2();
		cam.translateGraphics();
		pool.render(gc, g);
		drawForGround(gc,g);
		if(this.useLightFX){
			this.lFx.renderLight(gc);
		}
		this.renderState(gc, arg1, g);
		if(this.GUI!=null){
			this.GUI.render(gc, g);
		}
	}

	public ArrayList<Vector2f> scanMapForObject(String objectLayer,String object,String type){
		ArrayList<Vector2f> retur = new ArrayList<Vector2f>();
		for(int i=0;i<tiledmap.getObjectGroupCount();i++){
			// PLAYER_SPAWN
			if(tiledmap.getObjectName(i,0).equals(objectLayer)){
				for(int j = 0;j<tiledmap.getObjectCount(i);j++){
					String tempString = tiledmap.getObjectProperty(i, j, type,"false");
					if(tempString.equals(object)){
						int px = tiledmap.getObjectX(i, j);
						int py = tiledmap.getObjectY(i, j);
						retur.add(new Vector2f(px,py));


					}
				}
			}
		}
		return retur;
	}
	
	public boolean[][] getBlockAtribute(String s) {
		
		boolean retur[][] = new boolean[tiledmap.getWidth()][tiledmap.getHeight()];
		for (int x = 0; x < this.tiledmap.getWidth(); x++) {
			for (int y = 0; y < tiledmap.getHeight(); y++) {
				for (int i = 0; i < tiledmap.getLayerCount(); i++) {
					int tileID = tiledmap.getTileId(x, y, i);
					String value = tiledmap.getTileProperty(tileID, s,"false");
					if ("true".equals(value)) {
						retur[x][y] = true;	
					}
					
				}
			}
		}
		return retur;
	}
	
	protected void addDynamicBlock(int i, int j, DynamicBlock dynamicBlock) {
		this.dynamicBlocks[i][j] = dynamicBlock;
		
	}

	public void render(GameContainer gc,Graphics g){


	}

	public void drawForGround(GameContainer gc, Graphics g) {
		cam.untranslateGraphics();
		cam.drawForGround();
		cam.translateGraphics();

	}

	public void centerOn() {
		cam.centerOn((pool.mainChar.gamePosition.x), (pool.mainChar.gamePosition.y));
	}

	public int getXOffSet() {

		return (int)cam.cameraX;
	}
	public int getYOffSet() {

		return (int)cam.cameraY;
	}

	public void translateGFX(){
		cam.translateGraphics();
	}
	public void unTranslateGFX(){
		cam.untranslateGraphics();
	}

	protected void updateLight(float r,float g,float b){
		this.lFx.updateLight(r,g,b);
	}

	protected void updateLight(Color c){
		this.lFx.updateLight(c);
	}

}
