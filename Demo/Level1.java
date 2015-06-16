import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class Level1 extends TiledMapGameState {

	Physic physic; // GAME PHYSIC
	private int nextState = 0;
	private boolean switchToNextState;
	

	public void initState(GameContainer gc, StateBasedGame arg1) {

		pool.addToNonCollisionPool(new SnowFall(0,0,0, pool));
		physic = new Physic(true);
		Color tempColor = new Color(0.8f,0.2f,0,2f);
		tempColor.a = 0.2f;
		updateLight(tempColor);
		this.useLightFX = false;

	}

	public void renderState(GameContainer gc, StateBasedGame sbg, Graphics g) {
		this.unTranslateGFX();
		g.setColor(Color.white);
		g.drawString("Player Pos: X = "+(int)(pool.mainChar.gamePosition.x)+" Y = "+(int)(pool.mainChar.gamePosition.y),5,  gc.getHeight()-25);
		this.translateGFX();
	}
	public void updateState(GameContainer gc, StateBasedGame sbg, int delta) {
		if(delta<500){
			switchState(gc,sbg);
			physic.update(delta, pool);
			//cond.loopMusic("dawn", 0.0f);
			//cond.loopMusic("too_quiet_in_here", 0.8f);
			//pool.updateGUI();
		}
	}
	private void switchState(GameContainer gc,StateBasedGame sbg){
		if(this.switchToNextState){
			sbg.enterState(1);
			switchToNextState = false;
		}
		else if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			switchToNextState = true;
		}
	}
	protected void setUpResources() {
		resourceHandler = new ResourceHandler();
		
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
		
		id = "HellWormHead.png";
		path = "images/HellWormHead.png";
		xSize = 80;
		ySize = 80;
		resource = new SpriteResource(id, path, xSize, ySize);
		resourceHandler.add(resource);

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

		id ="Level5.tmx";
		path = "TileMap/Level5.tmx";
		resource = new TileMapResource(id, path);
		resourceHandler.add(resource);

		id ="Hell.tmx";
		path = "TileMap/Hell.tmx";
		resource = new TileMapResource(id, path);
		resourceHandler.add(resource);

		id ="Light6.png";
		path = "images/Light6.png";
		resource = new ImageResource(id, path);
		resourceHandler.add(resource);


		//AUDIO
		cond.addMusic("too_quiet_in_here","audio/music/too_quiet_in_here.aif");
		cond.addMusic("dawn","audio/music/Dawn.aif");
		cond.addSound("boost","audio/sound/boost1.aif");
		cond.addSound("charge","audio/sound/charge1.aif");
		cond.addSound("shoot","audio/sound/shoot1.aif");
		cond.addSound("explosion","audio/sound/explosion.aif");

		//cond.addMusic("birds","audio/music/birds.aif");
		//cond.addSound("zombie1","audio/sound/zombie1.aif");

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

		/*
		id ="Level1.tmx";
		path = "TileMap/Level1.tmx";
		resource = new TileMapResource(id, path);
		resourceHandler.add(resource);
		 */

		/*id ="button1.png";
		path = "images/button1.png";
		resource = new ImageResource(id, path);
		resourceHandler.add(resource);
		 */

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


	}

	@Override
	protected void spawnGameObjects(GameContainer gc) {
		//ArrayList<SimpleGameObject> fList =  op.getFriendlyList();
		ArrayList<Vector2f> cord = new ArrayList<Vector2f>();

		cord = this.scanMapForObject("PlayerSpawn", "player1", "spawn");
		for(int i = 0; i<cord.size();i++){
			int px = (int) cord.get(i).x;
			int py = (int) cord.get(i).y;
			SpaceExplorer p = new SpaceExplorer(64, 64, new Vector2f(px,py),gc, pool);
			pool.addToCollisionPool(p);
			pool.mainChar = p;
		}

		cord = this.scanMapForObject("EnemySpawn", "enemy", "spawn");
		for(int i = 0; i<cord.size();i++){
			int px = (int) cord.get(i).x;
			int py = (int) cord.get(i).y;
			pool.addToCollisionPool(new Patrol(64, 64, new Vector2f(px,py),gc, pool));
		}
		
		cord = this.scanMapForObject("EnemySpawn", "hellworm", "spawn");
		for(int i = 0; i<cord.size();i++){
			int px = (int) cord.get(i).x;
			int py = (int) cord.get(i).y;
			pool.addToCollisionPool(new HellWorm(80, 80, new Vector2f(px,py),gc, pool));
		}

		cord = this.scanMapForObject("EnemySpawn", "angel", "spawn");
		for(int i = 0; i<cord.size();i++){
			int px = (int) cord.get(i).x;
			int py = (int) cord.get(i).y;
			pool.addToCollisionPool(new Angel(64, 64, new Vector2f(px,py),gc, pool));
		}

		cord = this.scanMapForObject("EnemySpawn", "movingPlattform", "spawn");
		for(int i = 0; i<cord.size();i++){
			int px = (int) cord.get(i).x;
			int py = (int) cord.get(i).y;
			pool.addToCollisionPool(new MovingPlattform(128, 5, new Vector2f(px,py), pool));
		}

		cord = this.scanMapForObject("EnemySpawn", "robotcop", "spawn");
		for(int i = 0; i<cord.size();i++){
			int px = (int) cord.get(i).x;
			int py = (int) cord.get(i).y;
			pool.addToCollisionPool(new SuperCop(64, 64, new Vector2f(px,py),gc, pool));
		}
		
		cord = this.scanMapForObject("EnemySpawn", "goal", "spawn");
		for(int i = 0; i<cord.size();i++){
			int px = (int) cord.get(i).x;
			int py = (int) cord.get(i).y;
			pool.addToCollisionPool(new GoalPoint(64, 64, new Vector2f(px,py), pool,2));
		}
	}

	@Override
	public int getID() {

		return 0;
	}


	@Override
	public String getTiledMapName() {
		return "Hell";
	}

	@Override
	protected GUI getGUI(GameContainer gc) {
		int x = 128+8;
		int y = 64+8;
		return new GUI(x, y, new Vector2f(gc.getWidth()/2-x/2,0), pool);
	}



}
