import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class DynamicBlockManager extends AdvancedGameObject {
	private DynamicBlock[][] dynamicBlock;
	public DynamicBlockManager(int x, int y, Vector2f pos, GameContainer gc,
			ObjectPool objPool,DynamicBlock[][] db) {
		super(x, y, pos, gc, objPool);
		this.width = x;
		this.height = y;
		this.checkForCollision = false;
		this.dynamicBlock = db;
	}

	@Override
	void update(GameContainer gc, int delta,StateBasedGame sbg) {
		
	}

	@Override
	void render(GameContainer gc, Graphics g) {
		Camera cam = new Camera();
		SimpleGameObject go = this.objPool.mainChar;
		int tileSize = 16;
		int xAxis = (int)(cam.cameraX/tileSize)-3;
		int yAxis = (int)(cam.cameraY/tileSize)-3;
		int xMax = (gc.getWidth()/tileSize)+4;
		int yMax = (gc.getHeight()/tileSize)+4;
		//yAxis -= 0;
		if (xAxis < 0) {
			xAxis = 0;
		}
		if (yAxis < 0) {
			yAxis = 0;
		}
		


		for (int xRange = 0; xRange < xMax; xRange++) {
			for (int yRange = 0; yRange < yMax; yRange++) {
				if(yAxis +yRange < this.height&&xAxis +xRange < this.width&&this.dynamicBlock[(xAxis + xRange)][yAxis + yRange]!=null){
					this.dynamicBlock[xAxis + xRange][yAxis + yRange].render(gc, g);
				}
			}
		}
		
		
	}
	
	@Override
	public void objectCollide(SimpleGameObject sGO) {
		// TODO Auto-generated method stub

	}



}
