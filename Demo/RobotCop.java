import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.Path;

public class RobotCop extends AdvancedGameObject {

	private Vector2f lastPosition;
	private int dir;

	private int turnCooldown = 0;
	private int turnCooldownMax = 200;
	private float turnJump = -0.02f;
	private float walkSpeed = 0.038f;
	private int HP;
	private Conductor cond;
	private int visionX = 600;
	private int visionY = 5;
	private int reCharge = 600;
	private int loadTime = 200;


	public RobotCop(int x, int y, Vector2f pos, GameContainer gc) {
		super(x, y, pos, gc);
		size = 64;
		jump = false;
		this.idString = "SimpleEnemy";
		lastPosition = new Vector2f(x, y);	
		dir = 0;
		HP = 1;
		cond = new Conductor();
		target = null;
		this.data1 = 0;
	}



	@Override
	void init(GameContainer gc) {
		//SETTING UP SPRITE&ANIMATION

		addAnimation("robotcop.png", 4, 0, 7, 0, 250, "WalkLeft");
		addAnimation("robotcop.png", 0, 0, 3, 0, 250, "WalkRight");
		addAnimation("robotcop.png", 10, 0, 11, 0, 300, "deadLeft");
		addAnimation("robotcop.png", 8, 0, 9, 0, 300, "deadRight");
		//addAnimation("Gris.png", 2, 0, 2, 0, 100, "WalkLeft");
		//addAnimation("Gris.png", 1, 0, 1, 0, 100, "WalkRight");
		setCurrentAnimation("WalkLeft");		
		//this.showBorders = true;
		this.borderColor = Color.black;
		//this.showFillRect = true;
		//this.fillRectColor = Color.pink;

		this.checkForCollision = true;
		this.checkForGravity = true;
		this.faction = -1;	
	}

	@Override
	void update(GameContainer gc, int delta) {
		lastPosition.x = gamePosition.x;
		lastPosition.y = gamePosition.y;
		updateWalk(delta);
		reCharge(delta);
		if(target!=null&&Math.random()<10.0/60.0&&HP>0){
			AI(gc,delta);
		}				
	}

	private void AI(GameContainer gc,int delta) {
		
		boolean enemeyContact = enemyContact(gc,delta);
		if(enemeyContact){
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
		return !blocked&&inVisionRange&&facingTarget&&target.HP>0;
	}



	private void reCharge(int delta) {
		if(reCharge<loadTime){
			reCharge+=delta;
		}
		
	}



	private void reportEnemy(int delta) {
		data1 =1;
	}

	private void noEnemyVision(int delta){
		data1 = 0;
	}

	public void shootAtTarget(GameContainer gc){
		
		if(reCharge>=loadTime){
			float v = getDirectionToTarget(target);
			//BUGG MED -1 i BLAST
			Vector2f mouth = new Vector2f(this.gamePosition.x+32,this.gamePosition.y+16);
			Blast b = new Blast(6,6 ,mouth,gc,-2,new Vector2f((float)(1*Math.cos(v)),(float) (1*Math.sin(v))));
			ObjectPool op = new ObjectPool();
			op.addToPool(b);
			reCharge=0;
		}
	}

	void updateWalk(int delta){

		if(turnCooldown <= 0 && !jump && dir == 0 && (this.leftObs || !this.southObs)){
			dir = 1;
			setCurrentAnimation("WalkRight");
			this.velocityVector.y = turnJump;
			jump = true;
			turnCooldown = turnCooldownMax;
		}
		else if(turnCooldown <= 0 && !jump && dir == 1 && (this.rightObs || !this.southObs)){
			dir = 0;
			setCurrentAnimation("WalkLeft");
			this.velocityVector.y = turnJump;
			jump = true;
			turnCooldown = turnCooldownMax;
		}

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

	/*
	@Override
	void render(GameContainer gc, Graphics g) {
		this.animationList.get(dir).draw(this.gamePosition.x,this.gamePosition.y);
	}
	 */
	@Override
	void reset() {
		gamePosition.x = lastPosition.x;
		gamePosition.y = lastPosition.y;
		this.velocityVector.y = 0;
		//this.velocityVector.x = 0;
	}

	@Override
	public void damage() {
		walkSpeed = 0f;
		HP--;
		if(HP == 0){
			cond.playSound("explosion", 0.8f, 0.2f);
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
		Scenery scen = new Scenery();
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
		int tileSize = 16;
		int checkRangeX = this.visionX/tileSize;

		if(yRange<visionY&&xRange<visionX){
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
