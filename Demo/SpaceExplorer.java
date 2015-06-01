import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class SpaceExplorer extends AdvancedGameObject{

	private int dir;
	private Vector2f lastPosition;
	private ObjectPool op;
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
	Conductor cond;

	//Player vars
	private int shotCoolDownTime = 200;
	private int shotRampUpTime = 0;
	private int shotNumMax = 1;

	private int jumpMaxTimer = 300;
	private int jumpNumMax = 3;
	private float jumpVelocity = -0.2f;

	private float runMaxX = 0.20f;
	private float runAccX = 0.0005f;
	private float runDeAccX = 0.0005f;

	private boolean dashing = false;
	private int dashTimerMax = 300;
	private int dashTimer = dashTimerMax;
	private int dashCDMax = 500;
	private int dashCD = 0;
	private boolean dashkeyPressed = false;
	private float dashSpeed = 0.4f;


	private boolean crouchkeyPressed = false;


	public SpaceExplorer(int x, int y, Vector2f gamePosition, GameContainer gc) {
		super(x, y, gamePosition, gc);
		mIn = new MouseInput(0,0,gc);
		dir = 0;
		size = 64;
		lastPosition = new Vector2f(x,y);
		jump = false;
		this.idString ="BODY_male";
		op = new ObjectPool();
		light = new SearchLight(0,0);
		setLight(light);
		this.checkForCollision = true;
		this.checkForGravity = true;
		cond = new Conductor();
		this.resource1 = 3;


	}
	@Override
	void init(GameContainer gc) {
		/*
		addAnimation("MooYeah.png", 1, 0, 1, 0, 100, "StandLeft");
		addAnimation("MooYeah.png", 0, 0, 0, 0, 100, "StandRight");
		addAnimation("MooYeah.png", 6, 0, 9, 0, 170, "WalkLeft");
		addAnimation("MooYeah.png", 2, 0, 5, 0, 170, "WalkRight");
		addAnimation("MooYeah.png", 11, 0, 11, 0, 100, "ShootLeft");
		addAnimation("MooYeah.png", 10, 0, 10, 0, 100, "ShootRight");
		addAnimation("MooYeah.png", 13, 0, 13, 0, 100, "JumpLeft");
		addAnimation("MooYeah.png", 12, 0, 12, 0, 100, "JumpRight");
		 */

		addAnimation("SpaceExplorer.png", 1, 0, 1, 0, 100, "StandLeft");
		addAnimation("SpaceExplorer.png", 0, 0, 0, 0, 100, "StandRight");
		addAnimation("SpaceExplorer.png", 6, 0, 9, 0, 170, "WalkLeft");
		addAnimation("SpaceExplorer.png", 2, 0, 5, 0, 170, "WalkRight");
		addAnimation("SpaceExplorer.png", 1, 0, 1, 0, 100, "ShootLeft");
		addAnimation("SpaceExplorer.png", 0, 0, 0, 0, 100, "ShootRight");
		addAnimation("SpaceExplorer.png", 11, 0, 11, 0, 100, "JumpLeft");
		addAnimation("SpaceExplorer.png", 10, 0, 10, 0, 100, "JumpRight");
		addAnimation("SpaceExplorer.png", 13, 0, 13, 0, 100, "FallLeft");
		addAnimation("SpaceExplorer.png", 12, 0, 12, 0, 100, "FallRight");
		addAnimation("SpaceExplorer.png", 15, 0, 15, 0, 100, "DashLeft");
		addAnimation("SpaceExplorer.png", 14, 0, 14, 0, 100, "DashRight");
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
	void update(GameContainer gc, int delta) {
		lastPosition.x = gamePosition.x;
		lastPosition.y = gamePosition.y;
		if(HP>0){
			getInput(delta,gc);
		}
		// FOR PLATFORM TILLF€LLIG L…SNING - FIXA EN FUNKTION SOM STYR VILKEN ANIMATION SOM VISAS
		if(data1>0){
			setCurrentAnimation("StandLeft");
		}
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
				jump = true;
				jumpTimer = jumpMaxTimer;
				this.velocityVector.y = jumpVelocity;
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
			jump = false;
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
		if(jump==false){
			isJump = false;
			jump = false;
			jumpTimer = 0;
			jumpNum = jumpNumMax;
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
			else{
				this.velocityVector.x = -dashSpeed;
			}
		}
		if(dashCD > 0){
			dashCD -= delta;

		}
		if(dashTimer <= 0 || this.leftObs || this.rightObs || this.stuck){
			if(dashing){


				dashCD = dashCDMax;
				if(this.velocityVector.x < 0){
					this.velocityVector.x = -this.runMaxX;
				}
				else{
					this.velocityVector.x = this.runMaxX;
				}
			}
			dashing = false;

		}

		//SHOOTING
		if( /*&& this.southObs == true*/this.resource1>0){
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				if(this.shotRampUp==0){
					this.cond.playSound("charge", 0.6f, 0.1f);
				}
				shotRampUp += delta;
				dir = 4;
				setCurrentAnimation("ShootLeft");

				if(this.shotCoolDown <= 0 && shotRampUp >= shotRampUpTime && shotNum > 0){
					this.op.addToPool(new Blast(8, 8, new Vector2f(this.gamePosition.getX(), this.gamePosition.getY() + 16), container, -1, 0));
					//resource1--;
					shotCoolDown = shotCoolDownTime;
					this.cond.playSound("shoot", 1, 0.15f);
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
					this.cond.playSound("shoot", 1, 0.15f);
					//resource1--;
					this.op.addToPool(new Blast(8, 8, new Vector2f(this.gamePosition.getX() + 48, this.gamePosition.getY() + 16), container, 2, 0));
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

		// SET JUMP ANIMATION
		if(this.southObs == false && !dashing){
			if(lastRunDir == 2){
				dir = 6;
				if(this.velocityVector.y < 0.16)
					setCurrentAnimation("JumpLeft");
				else
					setCurrentAnimation("FallLeft");
			}
			else{
				dir = 7;
				if(this.velocityVector.y < 0.16)
					setCurrentAnimation("JumpRight");
				else
					setCurrentAnimation("FallRight");
			}
		}


	}
	/*
	@Override
	void render(GameContainer gc, Graphics g) {
		super.render(gc, g);
		//this.animationList.get(dir).draw(this.gamePosition.x,this.gamePosition.y);	
		mIn.render(gc, g);

	}
	 */
	@Override
	void reset() {
		// MAYBEE?
		jumpButtonPressed = false;
		isJump = false;
		jumpTimer = 0;
		jumpNum = jumpNumMax;

		gamePosition.x = lastPosition.x;
		gamePosition.y = lastPosition.y;
		this.velocityVector.y = 0;
		//this.velocityVector.x = 0;
	}

	@Override
	public void damage() {
		if(HP>0){
			this.cond.playSound("boost", 0.4f, 0.1f);
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


	}

	public void setUpGUI() {
		int xW =250;
		int yH = 70;
		Camera cam = new Camera();
		gui = new GUI(xW,yH, new Vector2f(cam.getDisplayWidth()/2-xW/2,25)) ;
		ObjectPool op = new ObjectPool();
		op.addGUI(gui);

	}	

}
