import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.Path;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.Path;

public class Angel extends EnemyGameObject {

	private Vector2f lastPosition;


	int mode = 0;


	public int engageTimer = 3000;
	public int engageMax = 3000; 






	public Angel(int x, int y, Vector2f pos, GameContainer gc, ObjectPool objPool) {
		super(x, y, pos, gc, objPool);
		size = 64;
		jump = false;
		this.idString = "SimpleEnemy";
		lastPosition = new Vector2f(x, y);	
		this.dir = 1;
		HP = 1;
		cond = new Conductor();
		target = null;
		this.data1 =0;
		this.data3 = 0;
		this.walkSpeed*=2;
		this.velocityVector.x=this.walkSpeed;
	}



	@Override
	void init(GameContainer gc) {
		//SETTING UP SPRITE&ANIMATION

		/*addAnimation("LiquidSoldier.png", 20, 0, 23, 0, 150, "WalkLeft");
		addAnimation("LiquidSoldier.png", 0, 0, 3, 0, 150, "WalkRight");
		addAnimation("LiquidSoldier.png", 27, 0, 39, 0, 65, "deadLeft",false);
		addAnimation("LiquidSoldier.png", 7, 0, 19, 0, 65, "deadRight",false);
		addAnimation("LiquidSoldier.png", 24, 0, 25, 0, 150, "flyLeft");
		addAnimation("LiquidSoldier.png", 4, 0, 5, 0, 150, "flyRight");*/


		addAnimation("Angel.png", 4, 0, 7, 0, 200, "WalkLeft");
		addAnimation("Angel.png", 0, 0, 3, 0, 200, "WalkRight");
		addAnimation("SimpleGreenGuard.png", 14, 0, 17, 0, 65, "deadLeft",false);
		addAnimation("SimpleGreenGuard.png", 10, 0, 13, 0, 65, "deadRight",false);
		addAnimation("Angel.png", 4, 0, 7, 0, 150, "flyLeft");
		addAnimation("Angel.png", 0, 0, 3, 0, 150, "flyRight");


		setCurrentAnimation("WalkRight");		
		this.borderColor = Color.black;
		this.checkForCollision = true;
		this.checkForGravity = true;
		this.faction = -1;	
		this.checkForGravity = false;
	}

	@Override
	public void aiBehaviour(GameContainer gc, int delta) {
		this.simpleWalk = true;
		//this.jumpAtObstacle = true;
		//this.shootAtPlayer = true;
		//this.turnAtGap = true;
		//this.followPlayerPathFinding = true;

	}


	@Override
	void render(GameContainer gc, Graphics g) {

		if(showBorders){
			g.setColor(borderColor);
			g.drawRect(this.gamePosition.x, this.gamePosition.y, width, height);
		}
		if(showFillRect){
			g.setColor(fillRectColor);
			g.fillRect(this.gamePosition.x, this.gamePosition.y, width, height);
		}

		if(currentAnimation != null){
			currentAnimation.draw((int)(gamePosition.x), (int)(gamePosition.y+1));	
		}

		if(HP>0){
			//laserSight(gc,g);
		}

		if(p!=null&&player!=null&&renderPath){
			renderPath(gc,g);
			g.draw(this.player);
		}




	}





	private void laserSight(GameContainer gc, Graphics g) {
		MapInfo scen = new MapInfo();
		int tS = 16;
		Color c = Color.red;
		int spacing = 16;
		int w = 1;
		int h = 2;
		if(dir==0){
			//LEFT
			for(int i = 0;i<this.visionX;i+=spacing){
				float xL = this.gamePosition.x-i;
				float yL = this.gamePosition.y+33;
				if(!scen.getBlocked((int)(xL/tS), (int)(yL/tS))){
					g.setColor(c);
					g.fillRect(xL,yL , w, h);
				}
				else{

					return;
				}
			}
		}
		else{
			for(int i = 0;i<this.visionX-64;i+=spacing){
				float xL = this.gamePosition.x+i+64;
				float yL = this.gamePosition.y+33;
				if(!scen.getBlocked((int)(xL/tS), (int)(yL/tS))){
					g.setColor(Color.red);
					g.fillRect(xL,yL , w, h);
				}
				else{

					return;
				}
			}
		}



	}



	protected void setAnimation(GameContainer gc, int delta) {

		if(this.HP>0){


			if(this.velocityVector.x>0){
				setCurrentAnimation("WalkRight");
			}
			else{
				setCurrentAnimation("WalkLeft");
			}
			if(!southObs){
				if(this.velocityVector.x>0){
					setCurrentAnimation("flyRight");
				}
				else{
					setCurrentAnimation("flyLeft");
				}
			}
		}

	}


	private void reportEnemy(int delta) {
		data1 =1;
		extendedRange = 10;
	}

	private void noEnemyVision(int delta){
		data1 = 0;
	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		if(sGO.getClass().equals(Blast.class)){
			walkSpeed = 0f;
			HP--;
			this.checkForGravity = true;
			if(HP == 0){
				cond.playSound("explosion", 0.8f, 0.2f);
				if(this.velocityVector.x>0){
					setCurrentAnimation("deadRight");
				}
				else{
					setCurrentAnimation("deadLeft");
				}
				this.velocityVector.x=0;

			}
		}
		//this.remove = true;
	}

}
