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

public class SuperCop extends AdvancedGameObject {

	private Vector2f lastPosition;
	private int dir;

	private int turnCooldown = 0;
	private int turnCooldownMax = 500;
	private float turnJump = -0.02f;
	private float jumpV = -0.4f;
	private float walkSpeed = 0.03f;
	private Conductor cond;
	private int visionX = 380;
	private int visionY = 5;
	private int reCharge = 300;
	private int loadTime = 300;
	private int extendedRange = 1;
	int goalX = 84;
	int goalY = 105;
	int mode = 0;
	private int max = 7;
	private Path p = null;
	public int currentStep = 7;
	public int tempDir = 0;
	public int approachDir;
	public int engageTimer = 3000;
	public int engageMax = 3000; 
	Rectangle player;
	private int timeToPath=2000;
	private int PathFreq = 2000;
	private boolean renderPath= false;



	public SuperCop(int x, int y, Vector2f pos, GameContainer gc) {
		super(x, y, pos, gc);
		size = 64;
		jump = false;
		this.idString = "SimpleEnemy";
		lastPosition = new Vector2f(x, y);	
		dir = 1;
		HP = 1;
		cond = new Conductor();
		target = null;
		this.data1 =0;
		this.data3 = 0;
		this.velocityVector.x=this.walkSpeed;
	}



	@Override
	void init(GameContainer gc) {
		//SETTING UP SPRITE&ANIMATION

		addAnimation("LiquidSoldier.png", 20, 0, 23, 0, 250, "WalkLeft");
		addAnimation("LiquidSoldier.png", 0, 0, 3, 0, 250, "WalkRight");
		addAnimation("LiquidSoldier.png", 27, 0, 39, 0, 65, "deadLeft",false);
		addAnimation("LiquidSoldier.png", 7, 0, 19, 0, 65, "deadRight",false);
		addAnimation("LiquidSoldier.png", 24, 0, 25, 0, 150, "flyLeft");
		addAnimation("LiquidSoldier.png", 4, 0, 5, 0, 150, "flyRight");
		setCurrentAnimation("WalkRight");		
		this.borderColor = Color.black;
		this.checkForCollision = true;
		this.checkForGravity = true;
		this.faction = -1;	
	}

	@Override
	void update(GameContainer gc, int delta) {
		
		lastPosition.x = gamePosition.x;
		lastPosition.y = gamePosition.y;
		// MODE 0 - SIMPLE PATROL WALK
		//System.out.println(mode);
		if(HP>0){
			if(mode==0){
				simpleWalk(delta);
				setAnimation(gc,delta);
				if(this.target!=null&&enemyContact(gc,delta)){
					mode = 3;
					
				}
			}
			// MODE 1 - SET UP PATHFINDING 
			else if(mode ==1&&target!=null){
				if(target.southObs&&this.southObs){
					PathFinding pf = new PathFinding((int)(this.gamePosition.x/16.0),(int)(this.gamePosition.y/16.0),(int)(target.gamePosition.x/16+1),(int)(target.gamePosition.y/16));
					p = pf.getPath();
					mode = 2;
					System.out.println("MODE 2");
					// PATHFINDING KOMMER INTE FUNKA OM SPELAREN ST�R N�RA EN V�G
					if(p==null){
						
						System.out.println("NULL PATH");
						mode = 0;
						return;
					}
					this.currentStep=7;
					this.max =7;
				}
				else{
					mode =1;
				}
			}
			// MODE 2 - FOLLOW PATH
			else if(mode==2&&p!=null){
				if(this.timeToPath<this.PathFreq){
					timeToPath+=delta;
				}
				else{
					mode = 1;
					timeToPath = 0;
				}
				if(this.enemyContact(gc, delta)){
					mode = 3;
					this.currentStep = 7;
					this.max = 7;
				}
				setAnimation(gc,delta);

				//CHOSE PATH- ALGORITHM
				if(dir ==1){
					followPathR(gc,delta);
				}
				else{
					followPathL(gc,delta);
				}

				//ARRIVED AT GOAL LOCATION ?
				if(this.currentStep>=p.getLength()-1){
					mode =0;
					p = null;
				}
			}
			// MODE 3 - ENGAGE
			else if(mode == 3){
				this.extendedRange =30; 
				setAnimation(gc,delta);
				boolean b = enemyContact(gc,delta);
				if(b){
					//reportEnemy(delta);
					shootAtTarget(gc,delta);
				}
				else{
					this.engageTimer--;
				}
				if(engageTimer<0){
					this.engageTimer=engageMax;
					mode = 0;
					engageTimer =engageMax;
					this.extendedRange =1;
					return;
				}
				if(this.target.gamePosition.x>this.gamePosition.x){
					dir = 1;
					this.velocityVector.x =this.walkSpeed;
				}
				else{
					dir = 0;
					this.velocityVector.x = -this.walkSpeed;
				}
				// USE PATHFINDING TO FIND PLAYER
				if(this.leftObs||this.rightObs){
					System.out.println(this.leftObs);
					mode = 1;
					engageTimer =engageMax;
					this.extendedRange =1;
					return;
				}

			}
		}

	}


	private void simpleWalk(int delta) {
		if(this.rightObs){
			this.velocityVector.x =-this.walkSpeed;
			dir = 0;
		}
		else if(this.leftObs){
			this.velocityVector.x =this.walkSpeed;
			dir = 1;
		}
		if(this.visionX==0){
			this.velocityVector.x = this.walkSpeed;
		}
	}

	private void followPathR(GameContainer gc, int delta) {
		whereOnPath();

		int tS = 16;
		int nextSt = currentStep+1;
		if(nextSt>=p.getLength()-1){
			nextSt = p.getLength()-1;
		}
		float v = getDirectionToTarget(p.getX(nextSt)*tS,p.getY(nextSt)*tS);

		if(!this.jump){
			if(this.gamePosition.x>p.getX(nextSt)*16){
				dir = 0;
				if(this.leftObs||v>4f&&v<4.76f||(v<0&&v>-0.35f)){
					this.velocityVector.y = this.jumpV;
					this.jump = true;
					this.tempDir = 0;
				}
			}
			else if(this.gamePosition.x<p.getX(nextSt)*16){
				this.velocityVector.x =this.walkSpeed;
				dir = 1;
				// RIGHT
				if(this.rightObs||(v<-0.5f&&v>-0.7f)){
					this.velocityVector.y = this.jumpV;
					this.jump = true;
					this.tempDir = 1;
				}

			}
		}
		else{
			if(tempDir==0){
				this.velocityVector.x = -this.walkSpeed;
			}
			else if(tempDir==1){
				this.velocityVector.x = this.walkSpeed;
			}

		}
		if(this.southObs){
			this.jump=false;
		}





	}

	private void followPathL(GameContainer gc, int delta) {
		whereOnPath();

		int tS = 16;
		int nextSt = currentStep+1;
		if(nextSt>=p.getLength()-1){
			nextSt = p.getLength()-1;
		}
		float v = getDirectionToTarget(p.getX(nextSt)*tS,p.getY(nextSt)*tS);
		if(!this.jump){
			if(this.gamePosition.x+64>p.getX(nextSt)*16){
				// LEFT
				this.velocityVector.x =-this.walkSpeed;
				dir = 0;
				if(this.leftObs||v>4f&&v<4.76f){
					this.velocityVector.y = this.jumpV;
					this.jump = true;
					this.tempDir = 0;
				}
			}
			else if(this.gamePosition.x<p.getX(nextSt)*16){

				this.velocityVector.x =this.walkSpeed;
				dir = 1;
				// RIGHT
				if(this.rightObs){
					this.velocityVector.y = this.jumpV;
					this.jump = true;
					this.tempDir = 1;
				}

			}
		}
		else{
			if(tempDir==0){
				this.velocityVector.x = -this.walkSpeed;
			}
			else if(tempDir==1){
				this.velocityVector.x = this.walkSpeed;
			}

		}
		if(this.southObs){
			this.jump=false;
		}





	}



	private void whereOnPath() {

		Rectangle path;
		int multi;
		if(dir == 0){
			multi = 1;
		}
		else{
			multi =-1;
		}
		int tS = 16;
		for(int i = 0;i<p.getLength();i++){
			path = new Rectangle(p.getX(i)*tS+32*multi,p.getY(i)*tS,1,1);
			player = new Rectangle(this.gamePosition.x+32,this.gamePosition.y,1,(int)(this.size*1.5));
			if(player.intersects(path)&&i>max){
				max = i;
			}
		}
		this.currentStep = max;

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
			currentAnimation.draw(gamePosition.x, gamePosition.y);	
		}

		if(p!=null&&player!=null&&renderPath){
			renderPath(gc,g);
			g.draw(this.player);
		}
		if(HP>0){
			laserSight(gc,g);
		}






	}

	private void renderPath(GameContainer gc, Graphics g) {
		int tS = 16;
		Color c = Color.green;
		g.setColor(c);
		g.fillRect(goalX*tS-8, goalY*tS-8, 16, 16);
		if(p!=null){
			for(int i = currentStep;i<p.getLength();i++){
				int stepX = p.getX(i);
				int stepY = p.getY(i);
				stepX*=tS;
				stepY*=tS;
				g.fillRect(stepX, stepY, 4, 4);
			}
		}

	}



	private void laserSight(GameContainer gc, Graphics g) {
		Scenery scen = new Scenery();
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



	private void setAnimation(GameContainer gc, int delta) {

		if(this.velocityVector.x>0){
			setCurrentAnimation("WalkRight");
		}
		else{
			setCurrentAnimation("WalkLeft");
		}
		if(jump){
			if(this.velocityVector.x>0){
				setCurrentAnimation("flyRight");
			}
			else{
				setCurrentAnimation("flyLeft");
			}
		}
	}



	private void AI(GameContainer gc,int delta) {

		boolean enemeyContact = enemyContact(gc,delta);

		if(enemeyContact){
			reportEnemy(delta);
			shootAtTarget(gc,delta);
		}
		else {
			noEnemyVision(delta);
		}

	}

	private boolean enemyContact(GameContainer gc, int delta) {
		boolean blocked = checkForObstaclesX(target);
		boolean inVisionRange = inVisionRange(target);
		boolean facingTarget = facingTarget(target);
		boolean retur = !blocked&&inVisionRange&&facingTarget&&target.HP>0;
			if(retur&&target.gamePosition.x>this.gamePosition.x){
				this.approachDir =1; 
			}
			else if(retur&&target.gamePosition.x<this.gamePosition.x){
				this.approachDir =0; 
			}
			return !blocked&&inVisionRange&&facingTarget&&target.HP>0;
	}



	private void reCharge(int delta) {
		if(reCharge<loadTime){
			reCharge+=delta;
		}

	}



	private void reportEnemy(int delta) {
		data1 =1;
		extendedRange = 10;
	}

	private void noEnemyVision(int delta){
		data1 = 0;
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
			if(!b&&this.data3<=0){
				dir = 1;
			}


		}
		else if(turnCooldown <= 0 && !jump && dir == 1 ){
			//dir = 0;
			turnCooldown = turnCooldownMax;
			int xG = (int)(((this.gamePosition.x/16)+4));
			int yG = (int)(((this.gamePosition.y/16)+4));
			boolean b = isItBlocked(xG,yG);
			if(!b&&this.data3<=0){
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
			if(!b&&data3!=0){
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
			if(!b&&data3!=0){
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
		Scenery scen = new Scenery();
		
		return scen.getBlocked(xG, yG);
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
			if(this.velocityVector.x>0){
				setCurrentAnimation("deadRight");
			}
			else{
				setCurrentAnimation("deadRight");
			}
			this.velocityVector.x=0;

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

	float getDirectionToTarget(float x,float y){

		float v = 0;
		if(this.gamePosition.x>x){
			float a = (x-this.gamePosition.x);
			float b = y -this.gamePosition.y;
			v = (float) Math.atan(b/a);
			v+=Math.PI;
		}
		else{
			float a = (x-this.gamePosition.x);
			float b = y -this.gamePosition.y;
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
		if(yRange<visionY*extendedRange&&xRange<visionX){
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






	public void shootAtTarget(GameContainer gc,int delta){

		if(reCharge>=loadTime){
			float v = getDirectionToTarget(target);
			//BUGG MED -1 i BLAST
			Vector2f mouth = new Vector2f(this.gamePosition.x+32,this.gamePosition.y+16);
			Blast b = new Blast(6,6 ,mouth,gc,-2,new Vector2f((float)(1*Math.cos(v)),(float) (1*Math.sin(v))));
			ObjectPool op = new ObjectPool();
			op.addToPool(b);
			reCharge=0;
		}
		this.reCharge(delta);
	}



}
/**/