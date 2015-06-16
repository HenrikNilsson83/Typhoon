import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class SpaceExplorer extends AdvancedGameObject{

	private int dir;
	private Vector2f lastPosition;
	//private ObjectPool op;
	private GameContainer container;
	private GUI gui;
	MouseInput mIn;

	private int shotCoolDown = 0;
	private int shotRampUp = 0;
	private int shotNum = 0;
	private boolean isJump = false;
	private boolean jumpButtonPressed = false;
	private int jumpTimer = 0;
	private int jumpNum = 0;
	private int lastRunDir = 0;
	boolean wallJump = false;
	Conductor cond;
	private boolean changeState;
	private int nextState;

	//Player vars
	private int shotCoolDownTime = 300;
	private int shotRampUpTime = 50;
	private int shotNumMax = 1;

	private int jumpMaxTimer = 300;
	private int jumpNumMax = 3;
	private float jumpVelocity = -0.25f;

	private float runMaxX = 0.36f;
	private float runAccX = 0.0005f;
	private float runDeAccX = 0.001f;

	public boolean dashing = false;
	private int dashTimerMax = 300;
	private int dashTimer = dashTimerMax;
	private int dashCDMax = 500;
	private int dashCD = 0;
	private boolean dashkeyPressed = false;
	private float dashSpeed = 0.6f;

	private boolean crouchkeyPressed = false;


	public SpaceExplorer(int x, int y, Vector2f gamePosition, GameContainer gc, ObjectPool objPool){
		super(x, y, gamePosition, gc, objPool);
		mIn = new MouseInput(0,0,gc);
		dir = 0;
		size = 64;
		lastPosition = new Vector2f(x,y);
		jump = false;
		this.idString ="BODY_male";
		//op = new ObjectPool();
		light = new SpotLight(0,0);
		//setLight(light);
		this.checkForCollision = true;
		this.checkForGravity = true;
		this.changeState = false;
		this.nextState = -1;
		cond = new Conductor();
		this.resource1 = 9;
		this.faction = 1;
		//HITBOX SETUP
		this.hitbox.setWidth(21);
		this.hitbox.setXOffset(21);
		this.hitbox.setHeight(55);
		this.hitbox.setYOffset(3);


	}
	@Override
	void init(GameContainer gc) {
		addAnimation("Death.png", 5, 0, 9, 0, 200, "StandLeft");
		addAnimation("Death.png", 0, 0, 4, 0, 200, "StandRight");
		addAnimation("Death.png", 16, 0, 19, 0, 170, "WalkLeft");
		addAnimation("Death.png", 12, 0, 15, 0, 170, "WalkRight");
		addAnimation("Death.png", 11, 0, 11, 0, 100, "ShootLeft");
		addAnimation("Death.png", 10, 0, 10, 0, 100, "ShootRight");
		addAnimation("Death.png", 22, 0, 23, 0, 150, "JumpLeft");
		addAnimation("Death.png", 20, 0, 21, 0, 150, "JumpRight");
		addAnimation("Death.png", 25, 0, 25, 0, 100, "FallLeft");
		addAnimation("Death.png", 24, 0, 24, 0, 100, "FallRight");
		addAnimation("Death.png", 27, 0, 27, 0, 100, "DashLeft");
		addAnimation("Death.png", 26, 0, 26, 0, 100, "DashRight");
		addAnimation("Death.png", 30, 0, 31, 0, 100, "WallSlideLeft");
		addAnimation("Death.png", 28, 0, 29, 0, 100, "WallSlideRight");
		//CREATING COMPLEX ANIMATION: DEAD RIGHT
		SpriteSheet sprite;
		ResourceHandler rs = new ResourceHandler();
		SpriteResource sr = (SpriteResource) rs.get("SpaceExplorer.png");
		sprite = sr.getSprite();
		ArrayList<Animation> aList = new ArrayList<Animation>();
		Animation a = new Animation(sprite, 16, 0, 20, 0,true, 180,true);
		a.setLooping(false);
		aList.add(a);
		a = new Animation(sprite, 21, 0, 22, 0,true, 250,true);
		aList.add(a);
		addAnimation("DeadRight",aList);

		//CREATING COMPLEX ANIMATION DEAD LEFT
		aList = new ArrayList<Animation>();
		a = new Animation(sprite, 23, 0, 27, 0,true, 180,true);
		a.setLooping(false);
		aList.add(a);
		a = new Animation(sprite, 28, 0, 29, 0,true, 250,true);
		aList.add(a);
		addAnimation("DeadLeft",aList);

		setCurrentAnimation("StandLeft");

	}

	@Override
	void update(GameContainer gc, int delta,StateBasedGame sbg) {
		if(this.changeState){
			System.out.println("HOPP");
			this.changeState = false;
			sbg.enterState(this.nextState);
		}
		lastPosition.x = gamePosition.x;
		lastPosition.y = gamePosition.y;
		//setLight();
		if(HP>0){
			getInput(delta,gc);
		}
	}

	private void setLight(){
		LightFX lfx = new LightFX();
		light.lightPosition.x = this.gamePosition.x+this.width/2;
		light.lightPosition.y = this.gamePosition.y+this.height/2;
		lfx.addLight(light);
	}


	private void getInput(int delta,GameContainer gc) {

		//Crouch
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			if(!crouchkeyPressed){
				runMaxX = runMaxX / 2;
				runAccX = runAccX * 10;
				runDeAccX = runDeAccX * 10;
				dashTimerMax = dashTimerMax / 2;
				this.showBorders = true;
			}
			crouchkeyPressed = true;
		}
		else{
			if(crouchkeyPressed){
				runMaxX = runMaxX * 2;
				runAccX = runAccX / 10;
				runDeAccX = runDeAccX / 10;
				dashTimerMax = dashTimerMax * 2;
				this.showBorders = false;
			}
			crouchkeyPressed = false;
		}
		//JUMP
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			if((!isJump && this.southObs == true) || (jumpNum > 0 && !jumpButtonPressed)){
				isJump = true;
				jumpTimer = jumpMaxTimer;
				this.velocityVector.y = jumpVelocity;
				if(!this.wallJump)
					this.cond.playSound("boost", 1, 0.15f);
			}
			else if (jumpTimer > 0){
				this.velocityVector.y = jumpVelocity;
				jumpTimer -= delta;
			}
			jumpButtonPressed = true;
		}
		else if(this.southObs == true){
			jumpButtonPressed = false;
			isJump = false;
			jumpTimer = 0;
			jumpNum = jumpNumMax;
		}
		else{
			if(jumpButtonPressed || !isJump){
				jumpNum--;
			}
			jumpButtonPressed = false;
			isJump = true;
			jumpTimer = 0;
		}

		//LEFT RIGHT
		if (Keyboard.isKeyDown(Keyboard.KEY_D) && !this.stuck && !dashing) {
			setCurrentAnimation("WalkRight");
			dir = 3;
			lastRunDir = 3;
			if(this.velocityVector.x <= runMaxX){
				this.velocityVector.x += delta * runAccX;
				if(this.velocityVector.x > runMaxX){
					this.velocityVector.x = runMaxX;
				}
			}
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_A) && !this.stuck && !dashing) {
			setCurrentAnimation("WalkLeft");
			dir = 2;
			lastRunDir = 2;
			if(this.velocityVector.x >= -runMaxX){
				this.velocityVector.x -= delta * runAccX;
				if(this.velocityVector.x < -runMaxX){
					this.velocityVector.x = -runMaxX;
				}
			}

		}
		else if(!dashing){		//deacceleration
			if(lastRunDir == 2){
				setCurrentAnimation("StandLeft");
				dir = 0;
			}
			else{
				setCurrentAnimation("StandRight");
				dir = 1;
			}
			if(this.velocityVector.x > delta * runDeAccX){
				this.velocityVector.x -= delta * runDeAccX;
			}
			else if(this.velocityVector.x < delta * -runDeAccX){
				this.velocityVector.x += delta * runDeAccX;
			}
			else {  //to make sure the player not oscillates around zero
				this.velocityVector.x = 0;
			}
		}

		//Dash
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			if(!dashing && dashCD <= 0 && !dashkeyPressed){
				setCurrentAnimation("DashLeft");
				lastRunDir = 2;
				dashing = true;
				dashTimer = dashTimerMax;
				this.velocityVector.x = -dashSpeed;

				this.velocityVector.y = jumpVelocity;
				this.cond.playSound("boost", 0.6f, 0.15f);
				//dashCD = dashCDMax;
			}
			dashkeyPressed = true;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			if(!dashing && dashCD <= 0 && !dashkeyPressed){
				setCurrentAnimation("DashRight");
				lastRunDir = 1;
				dashing = true;
				dashTimer = dashTimerMax;
				this.velocityVector.y = jumpVelocity;
				this.velocityVector.x = dashSpeed;
				this.cond.playSound("boost", 0.6f, 0.15f);
				//dashCD = dashCDMax;
				/*this.melee = new Melee(8,8,this.gamePosition,gc);
				ObjectPool op = new ObjectPool();
				this.melee.setFaction(0);
				op.addToPool(this.melee);*/

			}
			dashkeyPressed = true;
		}
		else{
			dashkeyPressed = false;
		}
		if(dashing && dashTimer > 0){
			dashTimer -= delta;
			if(this.velocityVector.x > 0){
				this.velocityVector.x = dashSpeed;

			}
			else if(this.velocityVector.x > 0){
				this.velocityVector.x = -dashSpeed;

			}
		}
		if(dashCD > 0){
			dashCD -= delta;	
		}
		//if(dashTimer <= 0 || this.leftObs || this.rightObs || this.stuck){
		if(dashTimer <= 0 ){
			if(dashing){
				dashCD = dashCDMax;
				if(this.velocityVector.x < 0){
					this.velocityVector.x = -this.runMaxX;
				}
				else if(this.velocityVector.x > 0) {
					this.velocityVector.x = this.runMaxX;
				}
			}
			dashing = false;
		}

		//SHOOTING
		if(this.resource1>0){
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				if(this.shotRampUp==0){
					this.cond.playSound("charge", 0.6f, 0.1f);
				}
				shotRampUp += delta;
				dir = 4;
				setCurrentAnimation("ShootLeft");

				if(this.shotCoolDown <= 0 && shotRampUp >= shotRampUpTime && shotNum > 0){
					this.objPool.addToCollisionPool(new Blast(8, 8, new Vector2f(this.gamePosition.getX(), this.gamePosition.getY() + 16), container, -1, 0, objPool));
					resource1--;
					shotCoolDown = shotCoolDownTime;
					this.cond.playSound("shoot", 0.7f, 0.15f);
					shotNum--;
				}
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				if(this.shotRampUp==0){
					this.cond.playSound("charge", 0.6f, 0.1f);
				}
				shotRampUp += delta;
				dir = 5;
				setCurrentAnimation("ShootRight");
				if(this.shotCoolDown <= 0  && shotRampUp >= shotRampUpTime && shotNum > 0){
					this.cond.playSound("shoot", 0.68f, 0.15f);
					resource1--;
					this.objPool.addToCollisionPool(new Blast(8, 8, new Vector2f(this.gamePosition.getX() + 48, this.gamePosition.getY() + 16), container, 2, 0, objPool));
					shotCoolDown = shotCoolDownTime;
					shotNum--;
				}
			}
			else{
				shotRampUp = 0;
				shotNum = shotNumMax;
			}
		}
		else{
			shotRampUp = 0;
			shotNum = shotNumMax;
		}
		if(shotCoolDown > 0){
			shotCoolDown-=delta;
		}


		setAnimation(gc,delta);
		wallJump(gc,delta);

	}

	private void setAnimation(GameContainer gc, int delta) {
		// SET JUMP ANIMATION
		if(this.southObs == false && !dashing){
			if(lastRunDir == 2){
				dir = 6;
				if(this.velocityVector.y < 0)
					setCurrentAnimation("JumpLeft");
				else
					setCurrentAnimation("FallLeft");
			}
			else{
				dir = 7;
				if(this.velocityVector.y < 0)
					setCurrentAnimation("JumpRight");
				else
					setCurrentAnimation("FallRight");
			}
		}

		// SET WALL SLIDE ANIMATION
		if(!this.southObs&&(this.leftObs)&&(!Keyboard.isKeyDown(Keyboard.KEY_D))&&(!Keyboard.isKeyDown(Keyboard.KEY_SPACE))){
			setCurrentAnimation("WallSlideLeft");
		}
		else if(!this.southObs&&(this.rightObs)&&(!Keyboard.isKeyDown(Keyboard.KEY_A))&&(!Keyboard.isKeyDown(Keyboard.KEY_SPACE))){
			setCurrentAnimation("WallSlideRight");
		}


	}

	public void wallJump(GameContainer gc, int delta){
		// WALL JUMP
		if(!this.southObs&&(this.leftObs)&&(!Keyboard.isKeyDown(Keyboard.KEY_D))&&!this.dashing){
			this.velocityVector.y *= 0.8;
			this.velocityVector.x = 0;
			jumpButtonPressed = false;
			isJump = false;
			jumpTimer = 0;
			jumpNum = jumpNumMax;
			this.wallJump = true;
		}
		else if(!this.southObs&&(this.rightObs)&&(!Keyboard.isKeyDown(Keyboard.KEY_A))&&!this.dashing){
			this.velocityVector.y *= 0.8;
			this.velocityVector.x=0;
			jumpButtonPressed = false;
			isJump = false;
			jumpTimer = 0;
			jumpNum = jumpNumMax;
			this.wallJump = true;
		}
		else{
			this.wallJump = false;
		}
	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		if(HP>0){
			//this.cond.playSound("boost", 0.4f, 0.1f);
			//HP--;
		}
		if(HP<=0){
			if(this.velocityVector.x>=0){
				setCurrentAnimation("DeadRight");
			}
			else{
				setCurrentAnimation("DeadRight");
			}
			this.velocityVector.x = 0;
		}
		if(sGO.getClass().equals(SuperCop.class)){
			float v = this.getDirectionToTarget(sGO.gamePosition.x-20,sGO.gamePosition.y);
			float distance = this.hitbox.yPos+this.hitbox.height-sGO.gamePosition.y;
			v*=(180/(2*Math.PI));
			if(distance<18){
				this.velocityVector.y = jumpVelocity*2;
				jumpButtonPressed = false;
				isJump = false;
				jumpTimer = 0;
				jumpNum = jumpNumMax;
			}


		}
		
		if(sGO.getClass().equals(GoalPoint.class)){
			GoalPoint gp = (GoalPoint) sGO;
			this.nextState = gp.nextState;
			this.changeState = true;
		}


	}

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
}
