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
	Conductor cond;

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
		tiledmap = ((TileMapResource) resourceHandler.get(s)).getMap();
		scenery = new MapInfo(tiledmap,gc,pool);
		this.spawnGameObjects(gc);
		this.initState(gc, arg1);
		GUI = this.getGUI(gc);
	}

	public void update(GameContainer gc, StateBasedGame arg1, int delta) {
		pool.update(gc, delta);
		GUI.guiContent(gc, delta);
		this.updateState(gc, arg1, delta);
	}

	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)throws SlickException {
		this.renderState(gc, arg1, g);
		this.scenery.drawForGround(gc,g);
		this.lFx.renderLight(gc);
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
	
	protected void updateLight(float r,float g,float b){
		this.lFx.updateLight(r,g,b);
	}
	
	protected void updateLight(Color c){
		this.lFx.updateLight(c);
	}

}
