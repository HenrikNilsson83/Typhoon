import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.Path;

public class Patrol extends AdvancedGameObject {
	private int HP;
	private Vector2f lastPosition;
	private int dir;
	private boolean jump;
	private int turnCooldown = 0;
	private int turnCooldownMax = 500;
	private float turnJump = -0.02f;
	private float jumpV = -0.4f;
	private float walkSpeed = 0.03f;
	private Conductor cond;
	private int visionX = 480;
	private int visionY = 5;
	private int reCharge = 600;
	private int loadTime = 600;
	private int extendedRange = 1;


	public Patrol(int x, int y, Vector2f pos, GameContainer gc, ObjectPool objPool) {
		super(x, y, pos, gc, objPool);
		jump = false;
		lastPosition = new Vector2f(x, y);	
		dir = 1;
		HP = 1;
		cond = new Conductor();
		target = null;
	}

	@Override
	void init(GameContainer gc) {
		//SETTING UP SPRITE&ANIMATION

		addAnimation("patrol.png", 6, 0, 9, 0, 300, "WalkLeft");
		addAnimation("patrol.png", 2, 0, 5, 0, 300, "WalkRight");
		addAnimation("patrol.png", 12, 0, 13, 0, 300, "deadLeft");
		addAnimation("patrol.png", 10, 0, 11, 0, 300, "deadRight");
		addAnimation("patrol.png", 16, 0, 17, 0, 200, "flyLeft");
		addAnimation("patrol.png", 14, 0, 15, 0, 200, "flyRight");
		setCurrentAnimation("WalkRight");		
		this.borderColor = Color.black;
		this.checkForCollision = true;
		this.checkForGravity = true;
		//this.faction = -1;	
	}

	@Override
	void update(GameContainer gc, int delta,StateBasedGame sbg) {


		if(HP>0){
			lastPosition.x = gamePosition.x;
			lastPosition.y = gamePosition.y;
			updateWalk(delta);
			reCharge(delta);
			setAnimation(gc,delta);
			if(target!=null&&Math.random()<10.0/60.0&&HP>0){
				AI(gc,delta);
			}
		}

	}

	private void setAnimation(GameContainer gc, int delta) {

		if(this.velocityVector.x>0){
			setCurrentAnimation("WalkRight");
		}
		else{
			setCurrentAnimation("WalkLeft");
		}

		if(this.velocityVector.y<0){
			//BUGG_DENNA SETTS MITT I HOPPET!?
			if(dir==1){
				setCurrentAnimation("flyRight");
			}
			else{
				setCurrentAnimation("flyLeft");
			}
		}
	}



	private void AI(GameContainer gc,int delta) {

		boolean enemeyContact = enemyContact(gc,delta);

		if(enemeyContact||extendedRange>1){
			if(extendedRange<=1)
				reportEnemy(delta);
			shootAtTarget(gc);
		}
		else {
			noEnemyVision(delta);
		}

	}

	private boolean enemyContact(GameContainer gc, int delta) {
		boolean blocked = checkForObstaclesX(target);
		boolean inVisionRange = inVisionRange(target);
		boolean facingTarget = facingTarget(target);
		return !blocked&&inVisionRange&&facingTarget;
	}



	private void reCharge(int delta) {
		if(reCharge<loadTime){
			reCharge+=delta;
		}

	}



	private void reportEnemy(int delta) {
		extendedRange = 10;
	}

	private void noEnemyVision(int delta){
	}

	public void shootAtTarget(GameContainer gc){

		if(reCharge>=loadTime){
			float v = getDirectionToTarget(target);
			//BUGG MED -1 i BLAST
			Vector2f mouth = new Vector2f(this.gamePosition.x+32,this.gamePosition.y+16);
			HeatSeekingMisile b = new HeatSeekingMisile(target,mouth, gc,-2,new Vector2f((float)(1*Math.cos(v)),(float) (1*Math.sin(v))), objPool);
			//ObjectPool op = new ObjectPool();
			this.objPool.addToCollisionPool(b);
			//op.addToPool(b);
			reCharge=0;
		}
	}

	void updateWalk(int delta){
		// CLIMBING 
		climbing(delta);
		goDown(delta);

		if(this.southObs){
			jump = false;
		}

		if(dir == 0){
			this.velocityVector.x = -walkSpeed;
		}
		else{
			this.velocityVector.x = walkSpeed;
		}
		if(turnCooldown > 0){
			turnCooldown -= delta;
		}
		if(this.HP<1){
			if(dir == 0){
				setCurrentAnimation("deadLeft");
			}
			else{
				setCurrentAnimation("deadRight");
			}
		}
	}

	private void goDown(int delta) {
		if(turnCooldown <= 0 && !jump && dir == 0 ){
			//dir =1;
			turnCooldown = turnCooldownMax;
			int xG = (int)(((this.gamePosition.x/16)-1));
			int yG = (int)(((this.gamePosition.y/16)+4));
			boolean b = isItBlocked(xG,yG);
			if(!b){
				dir = 1;
			}


		}
		else if(turnCooldown <= 0 && !jump && dir == 1 ){
			//dir = 0;
			turnCooldown = turnCooldownMax;
			int xG = (int)(((this.gamePosition.x/16)+4));
			int yG = (int)(((this.gamePosition.y/16)+4));
			boolean b = isItBlocked(xG,yG);
			if(!b){
				dir = 0;
			}
		}

	}



	private void climbing(int delta) {
		if(turnCooldown <= 0 && !jump && dir == 0 && (this.leftObs)){
			//dir =1;
			turnCooldown = turnCooldownMax;
			int xG = (int)(((this.gamePosition.x/16)-1));
			int yG = (int)(((this.gamePosition.y/16)-5));
			boolean b = isItBlocked(xG,yG);
			if(!b){
				this.velocityVector.y = jumpV;
				jump = true;
			}
			else{
				dir = 1;
			}

		}
		else if(turnCooldown <= 0 && !jump && dir == 1 && (this.rightObs )){
			//dir = 0;
			turnCooldown = turnCooldownMax;
			int xG = (int)(((this.gamePosition.x/16)+4));
			int yG = (int)(((this.gamePosition.y/16)-5));
			boolean b = isItBlocked(xG,yG);
			if(!b){
				this.velocityVector.y = jumpV;
				jump = true;
			}
			else{
				dir = 0;
			}
		}
	}



	private boolean isItBlocked(int xG, int yG) {
		//System.out.println("X: "+xG);
		//System.out.println("Y: "+yG);
		MapInfo scen = new MapInfo();
		return scen.getBlocked(xG, yG);
	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		System.out.println("hehe");
		if(sGO.getClass().equals(SpaceExplorer.class)){
			walkSpeed = 0f;
			HP--;
			if(HP == 0){
				cond.playSound("explosion", 0.8f, 0.2f);
				if(this.velocityVector.x>0){
					setCurrentAnimation("deadRight");
				}
				else{
					setCurrentAnimation("deadRight");
				}
				this.velocityVector.x=0;

			}
		}
		//this.remove = true;
	}


	//-------------DESSA KLASSER BORDE LIGGA I EN ABSTRAKT KLASS
	float getDirectionToTarget(SimpleGameObject t){

		float v = 0;
		if(this.gamePosition.x>t.gamePosition.x){
			float a = (t.gamePosition.x-this.gamePosition.x);
			float b = t.gamePosition.y -this.gamePosition.y;
			v = (float) Math.atan(b/a);
			v+=Math.PI;
		}
		else{
			float a = (t.gamePosition.x-this.gamePosition.x);
			float b = t.gamePosition.y -this.gamePosition.y;
			v = (float) Math.atan(b/a);

		}

		return v;
	}
	// KANSKE EN ABSTRAKT KLASS??
	public boolean checkForObstaclesX(SimpleGameObject t){
		MapInfo scen = new MapInfo();
		int tileSize = 16;
		int checkRangeX = this.visionX/tileSize;
		int x = (int) (this.gamePosition.x/tileSize);
		int y = (int) (this.gamePosition.y/tileSize);
		int xRange = (int) Math.abs(this.gamePosition.x-target.gamePosition.x);
		xRange/=tileSize;
		if(xRange<checkRangeX){
			checkRangeX = xRange;
		}
		else {
			return true;
		}
		//LEFT SIDE
		if((target.gamePosition.x<this.gamePosition.x)){
			for(int i = 0;i<checkRangeX;i++){
				if(scen.getBlocked(x-i, y)){
					return true;
				}
			}
		}
		else{

			//RIGHT SIDE
			for(int i = 0;i<checkRangeX;i++){
				if(scen.getBlocked(x+i, y)){
					return true;
				}
			}
		}
		return false;
	}

	public boolean inVisionRange(SimpleGameObject target){
		int yRange = (int) Math.abs(this.gamePosition.y-target.gamePosition.y);
		int xRange = (int) Math.abs(this.gamePosition.x-target.gamePosition.x);
		float v = getDirectionToTarget(target);
		//int checkRangeX = this.visionX/tileSize;

		if(yRange<visionY*extendedRange&&xRange<visionX&&v>0&&v<Math.PI){
			return true;
		}
		return false;
	}

	public boolean facingTarget(SimpleGameObject t){

		if(this.velocityVector.x>0&&t.gamePosition.x>this.gamePosition.x){
			return true;
		}
		else if(this.velocityVector.x<0&&t.gamePosition.x<this.gamePosition.x){
			return true;
		}
		return false;
	}



}
