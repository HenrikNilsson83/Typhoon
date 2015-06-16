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

		SimpleGameObject go = this.objPool.mainChar;
		int tileSize = 16;
		int xAxis = (int) (go.gamePosition.x / tileSize);
		int yAxis = (int) (go.gamePosition.y / tileSize);
		int xMax = 120;
		int yMax = 120;
		xAxis -= xMax/4;
		xAxis -= yMax/3;
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
