import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Player extends AdvancedGameObject{

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
	
	//Player vars
	private int shotCoolDownTime = 300;
	private int shotRampUpTime = 700;
	private int shotNumMax = 1;
	
	private int jumpMaxTimer = 300;
	private int jumpNumMax = 2;
	private float jumpVelocity = -0.2f;
	
	private float runMaxX = 0.20f;
	private float runAccX = 0.0005f;
	private float runDeAccX = 0.0005f;
	
	private boolean dashing = false;
	private int dashTimerMax = 100;
	private int dashTimer = dashTimerMax;
	private int dashCDMax = 500;
	private int dashCD = 0;
	private boolean dashkeyPressed = false;
	private float dashSpeed = 3f;
	
	private boolean crouchkeyPressed = false;
	

	public Player(int x, int y, Vector2f gamePosition, GameContainer gc) {
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
		
		addAnimation("NinjaKid.png", 1, 0, 1, 0, 100, "StandLeft");
		addAnimation("NinjaKid.png", 0, 0, 0, 0, 100, "StandRight");
		addAnimation("NinjaKid.png", 6, 0, 9, 0, 170, "WalkLeft");
		addAnimation("NinjaKid.png", 2, 0, 5, 0, 170, "WalkRight");
		addAnimation("NinjaKid.png", 13, 0, 13, 0, 100, "ShootLeft");
		addAnimation("NinjaKid.png", 12, 0, 12, 0, 100, "ShootRight");
		addAnimation("NinjaKid.png", 15, 0, 15, 0, 100, "JumpLeft");
		addAnimation("NinjaKid.png", 14, 0, 14, 0, 100, "JumpRight");
		addAnimation("NinjaKid.png", 17, 0, 17, 0, 100, "FallLeft");
		addAnimation("NinjaKid.png", 16, 0, 16, 0, 100, "FallRight");
		addAnimation("NinjaKid.png", 11, 0, 11, 0, 100, "DashLeft");
		addAnimation("NinjaKid.png", 10, 0, 10, 0, 100, "DashRight");
		
		setCurrentAnimation("StandLeft");
	}

	@Override
	void update(GameContainer gc, int delta) {
		lastPosition.x = gamePosition.x;
		lastPosition.y = gamePosition.y;
		getInput(delta,gc);
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
				this.velocityVector.x = dashSpeed;
				//dashCD = dashCDMax;
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
		if((dir == 0 || dir == 1) && this.southObs == true){
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				shotRampUp += delta;
				dir = 4;
				setCurrentAnimation("ShootLeft");
				if(this.shotCoolDown <= 0 && shotRampUp >= shotRampUpTime && shotNum > 0){
					this.op.addToPool(new Blast(8, 8, new Vector2f(this.gamePosition.getX(), this.gamePosition.getY() + 16), container, -1, 0));
					shotCoolDown = shotCoolDownTime;
					shotNum--;
				}
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				shotRampUp += delta;
				dir = 5;
				setCurrentAnimation("ShootRight");
				if(this.shotCoolDown <= 0  && shotRampUp >= shotRampUpTime && shotNum > 0){
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
		gamePosition.x = lastPosition.x;
		gamePosition.y = lastPosition.y;
		this.velocityVector.y = 0;
		//this.velocityVector.x = 0;
	}

	@Override
	public void damage() {
		runAccX = 0.0f;
		// TODO Auto-generated method stub

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
