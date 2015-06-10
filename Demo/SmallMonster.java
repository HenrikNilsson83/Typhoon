import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.Path;
/*
public class SmallMonster extends AdvancedGameObject {

	private Vector2f lastPosition;
	private int dir;
	
	private int turnCooldown = 0;
	private int turnCooldownMax = 200;
	private float turnJump = -0.02f;
	private float walkSpeed = 0.04f;
	
	public SmallMonster(int x, int y, Vector2f pos, GameContainer gc) {
		super(x, y, pos, gc);
		size = 64;
		jump = false;
		this.idString = "SimpleEnemy";
		lastPosition = new Vector2f(x, y);	
		dir = 0;
	}

	@Override
	void init(GameContainer gc) {
		//SETTING UP SPRITE&ANIMATION
		
		addAnimation("Mr_Deo.png", 0, 0, 7, 0, 200, "WalkLeft");
		addAnimation("Mr_Deo.png", 8, 0, 15, 0, 200, "WalkRight");
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
	}
	
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
		//this.remove = true;
	}

}
*/