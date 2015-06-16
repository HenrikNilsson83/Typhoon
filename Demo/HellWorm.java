import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;


public class HellWorm extends EnemyGameObject {
	private Vector2f lastPosition;
	private Image im;


	int mode = 0;


	public int engageTimer = 3000;
	public int engageMax = 3000;
	float t;
	float tick = 0.01f;
	float A = 0.05f;
	private int delay = 0;
	private int delayTime = 380;
	private LinkedList<Vector2f> queue;
	HellWormPart p;
	float rotation = 0;






	public HellWorm(int x, int y, Vector2f pos, GameContainer gc, ObjectPool objPool) {
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
		this.walkSpeed*=6;
		this.velocityVector.x=this.walkSpeed;
		t = 0;
		p = new HellWormPart(x,y,new Vector2f(this.gamePosition.x-width,this.gamePosition.y),new Vector2f(this.velocityVector.x,this.velocityVector.y),gc,objPool,0,delayTime);
		objPool.addToCollisionPool(p);
		queue = new LinkedList<Vector2f>();
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
		addAnimation("HellWormHead.png", 0, 0, 0, 0, 1000, "head");	
		this.setCurrentAnimation("head");
		this.borderColor = Color.red;
		this.fillRectColor = Color.red;
		//this.showFillRect = true;
		this.showBorders = true;
		this.checkForCollision = true;
		this.checkForGravity = true;
		this.faction = -1;	
		this.checkForGravity = false;
		im = this.currentAnimation.getCurrentFrame();
	}

	@Override
	public void aiBehaviour(GameContainer gc, int delta) {
		this.rotation = this.getCurrentDirection();
		if(this.leftObs||this.rightObs){
			this.walkSpeed*=-1;
		}
		float yV = (float) Math.sin(t);
		float xV = (float) Math.abs(Math.cos(t+Math.PI/3.4));
		
		xV *=this.walkSpeed;
		t+=tick;
		yV*=Math.abs(this.walkSpeed);
		if(t>=Math.PI*2){
			t = 0;
		}
		this.velocityVector.y = yV;
		this.velocityVector.x = xV;
		
		
		
		
		if(delay<=delayTime){
			delay +=delta;
			this.queue.addLast(new Vector2f(this.gamePosition.x,this.gamePosition.y));
		}
		else{
			Vector2f removed = this.queue.removeFirst();
			p.goToX = removed.x;
			p.goToY = removed.y;
			this.queue.addLast(new Vector2f(this.gamePosition.x,this.gamePosition.y));
		}
		
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
		this.setCurrentAnimation("head");
		if(currentAnimation != null){
			im.rotate((float)(this.rotation*(180/Math.PI)));
			im.draw(this.gamePosition.x, this.gamePosition.y);
			im.rotate(-(float)(this.rotation*(180/Math.PI)));
		}

		if(HP>0){
			//laserSight(gc,g);
		}
		g.setColor(this.fillRectColor);
		//g.fillOval(this.gamePosition.x, this.gamePosition.y, this.width, this.height);

		if(p!=null&&player!=null&&renderPath){
			renderPath(gc,g);
			g.draw(this.player);
		}




	}





	private float getCurrentDirection() {
		float v = 0;
		float xt = 0;
		float yt = 0;
		xt = this.gamePosition.x + 10*this.velocityVector.x;
		yt = this.gamePosition.y + 10*this.velocityVector.y;
		v = this.getDirectionToTarget(xt, yt);
		return (float) (v+Math.PI);
	}



	



	protected void setAnimation(GameContainer gc, int delta) {

		

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
					setCurrentAnimation("head");
				}
				else{
					setCurrentAnimation("head");
				}
				this.velocityVector.x=0;

			}
		}
		
		else if(sGO.getClass().equals(SpaceExplorer.class)){
			
			SpaceExplorer se = (SpaceExplorer) sGO;
			if(se.dashing&&HP>0){
				HP--;
				cond.playSound("explosion", 0.8f, 0.2f);
			}
			if(HP == 0){
				walkSpeed = 0f;
				this.checkForGravity = true;
				
				if(this.velocityVector.x>0){
					setCurrentAnimation("head");
				}
				else{
					setCurrentAnimation("head");
				}
				this.velocityVector.x=0;

			}
		}
		
		//this.remove = true;
	}
}
