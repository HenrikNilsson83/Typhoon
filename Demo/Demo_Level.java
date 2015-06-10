import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


public class Demo_Level extends WorldObject {
	ResourceHandler resourceHandler;
	ObjectPool pool;// POOL FOR ALL GAME OBJECTS
	Physic physic; // GAME PHYSIC
	Scenery scenery;//THE SCENERY OF THE GAME
	private boolean twilight;// TWILIGHT IN SCENERY
	private boolean dayLight;
	private float multi;// USED FOR FADING THE LIGHT
	private static int timer;// USED FOR FADING THE LIGHT
	private float hour = 100;// PACE OF TIME
	private float grade = 0.01f;// STEP SIZE FOR FADING LIGHT
	Conductor cond = new Conductor();
	private boolean night;
	private boolean first = true;
	private GUI GUI;
	MasterAi ma;
	
	
	
	
	public Demo_Level(GameContainer gc) {
		init(gc);
	}
	public void init(GameContainer gc) {
		
		multi = 1.9f;
		this.twilight = false;
		this.dayLight =	false;
		night = false;
		timer = 0;
		setUpResources();
		//pool = new ObjectPool(0, 0, new Vector2f(0,0));
		pool = new ObjectPool();
		Color light = new Color(0.1f,0.1f,1f);

		scenery = new Scenery(((TileMapResource) resourceHandler.get("Level5.tmx")).getMap(),gc,light,pool);
		//scenery = new Scenery(((TileMapResource) resourceHandler.get("smalltest.tmx")).getMap(),gc,light);
		physic = new Physic(true);
		
		//SET UP MASTER-AI
		ma = new MasterAi(0, 0, new Vector2f(0,0), gc, pool);
		SimpleGameObject t = pool.getCollisionPool().get(0);
		ma.setTarget(t);
		pool.addToCollisionPool(ma);
		int x = 128+8;
		int y = 64+8;
		this.GUI = new GUI(128+8, 64+8, new Vector2f(gc.getWidth()/2-x/2,0), pool);
		pool.addToNonCollisionPool(new SnowFall(0,0,0, pool));
	}
	
	public void render(GameContainer gc, Graphics g) {
		this.scenery.render(gc,g);
		pool.render(gc, g);
		this.scenery.drawLightningsAndForGround(gc,g);
		//pool.renderGUI(gc,g);
		//this.physic.render(gc,g);
		GUI.render(gc, g);
		
	}
	public void update(GameContainer gc, int delta) {
		pool.update(gc, delta);
		physic.update(delta, pool);
		scenery.update(delta);
		cond.loopMusic("dawn", 0.0f);
		//cond.loopMusic("too_quiet_in_here", 0.8f);
		//pool.updateGUI();
		GUI.guiContent(gc, delta);
		//updateLightCycle(delta);
		GUI.vision = ma.attention;
	}
	
	private void updateLightCycle(int delta) {
		timer+=delta;
		
		if(twilight){
			progressTwilight(delta);
			
		}
		else{
			progressDayLight(delta);
			
		}
		
		
		
	}
	private void progressTwilight(int delta) {
		
		if(timer>hour&&twilight&&multi>-1){
			timer = 0;
			scenery.updateLight(multi, delta);
			multi-=grade;
			
			if(multi<-1){
				twilight=false;
				dayLight = true;
				first = true;
			}
			if(multi<0.6){
				cond.loopMusic("forest_night", 0);
				cond.fadeMusic("forest_night", 50000, 0.25f, false);
			}
			else if(multi>0.8){
				//cond.fadeMusic("birds", 5000, 0, true);
			}
		}
		
	}
	private void progressDayLight(int delta) {
		if(timer>hour&&dayLight&&multi<2){
			timer = 0;
			scenery.updateLight(multi, delta);
			multi+=grade;
			if(multi>=2){
				twilight=true;
				dayLight = false;
			}
			if(first&&multi>0.7){
				cond.fadeMusic("forest_night", 5000, 0, true);
				first = false;
			}
			else if(multi>0.8){
				//cond.loopMusic("birds",0);
				//cond.fadeMusic("birds", 50000, 0.25f, false);
				
			}
		}
	}
	private void setUpResources() {
		resourceHandler = new ResourceHandler();


		/*
		String id = "MooYeah.png";
		String path = "images/MooYeah.png";
		int xSize = 64;
		int ySize = 64;
		Resource resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "NinjaKid.png";
		path = "images/NinjaKid.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		*/
		String id = "SpaceExplorer.png";
		String path = "images/SpaceExplorer.png";
		int xSize = 64;
		int ySize = 64;
		Resource resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "Death.png";
		path = "images/Death.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		/*
		id = "Mr_Deo.png";
		path = "images/Mr_Deo.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "Gris.png";
		path = "images/Gris.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		*/
		id = "patrol.png";
		path = "images/patrol.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "Angel.png";
		path = "images/Angel.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "LiquidSoldier.png";
		path = "images/LiquidSoldier.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "BirdBear.png";
		path = "images/BirdBear.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		/*
		id = "PixelNightWithSheild.png";
		path = "images/PixelNightWithSheild.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "unicornB.png";
		path = "images/unicornB.png";
		xSize = 32;
		ySize = 32;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "rainbow.png";
		path = "images/rainbow.png";
		xSize = 32;
		ySize = 32;
		//resource = new SpriteResource(id, path, xSize, ySize);
		//resourceHandler.add(resource);
		
		id = "Pirat2.png";
		path = "images/Pirat2.png";
		xSize = 48;
		ySize = 48;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "bellafigur.png";
		path = "images/bellafigur.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "PixelNightWithSheildSlash.png";
		path = "images/PixelNightWithSheildSlash.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		id = "BODY_skeleton.png";
		path = "images/BODY_skeleton.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "BODY_skeleton_die.png";
		path = "images/BODY_skeleton_die.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		*/
		id = "robotcop.png";
		path = "images/robotcop.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "SimpleGreenGuard.png";
		path = "images/SimpleGreenGuard.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "MrGray.png";
		path = "images/MrGray.png";
		xSize = 128;
		ySize = 128;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		
		id = "weaponinfo.png";
		path = "images/weaponinfo.png";
		xSize = 64;
		ySize = 64;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);
		/*
		id ="Level1.tmx";
		path = "TileMap/Level1.tmx";
		resource = new TileMapResource(id, path);
		resourceHandler.add(resource);
		*/
		id ="Level5.tmx";
		path = "TileMap/Level5.tmx";
		resource = new TileMapResource(id, path);
		resourceHandler.add(resource);
		
		id ="Hell.tmx";
		path = "TileMap/Hell.tmx";
		resource = new TileMapResource(id, path);
		resourceHandler.add(resource);
		
		
		
		/*id ="smalltest.tmx";
		path = "TileMap/smalltest.tmx";
		resource = new TileMapResource(id, path);
		resourceHandler.add(resource);*/
		/*
		id ="Level3.tmx";
		path = "TileMap/Level3.tmx";
		resource = new TileMapResource(id, path);
		resourceHandler.add(resource);
		
		id ="LightBulb.png";
		path = "images/LightBulb.png";
		resource = new ImageResource(id, path);
		resourceHandler.add(resource);
		id ="Light6.png";
		path = "images/Light6.png";
		resource = new ImageResource(id, path);
		resourceHandler.add(resource);
		
		id ="button1.png";
		path = "images/button1.png";
		resource = new ImageResource(id, path);
		resourceHandler.add(resource);
		*/
		
		
		//AUDIO
		cond.addMusic("too_quiet_in_here","audio/music/too_quiet_in_here.aif");
		cond.addMusic("dawn","audio/music/Dawn.aif");
		//cond.addMusic("birds","audio/music/birds.aif");
		//cond.addSound("zombie1","audio/sound/zombie1.aif");
		cond.addSound("boost","audio/sound/boost1.aif");
		cond.addSound("charge","audio/sound/charge1.aif");
		cond.addSound("shoot","audio/sound/shoot1.aif");
		cond.addSound("explosion","audio/sound/explosion.aif");
	}
}
