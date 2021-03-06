import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class SuperCop extends EnemyGameObject {

	int mode = 0;
	public int engageTimer = 3000;
	public int engageMax = 3000; 


	public SuperCop(int x, int y, Vector2f pos, GameContainer gc, ObjectPool objPool) {
		super(x, y, pos, gc, objPool);
		jump = false;
		this.dir = 1;
		HP = 1;
		cond = new Conductor();
		target = null;
		this.walkSpeed *= 2;
		this.velocityVector.x=this.walkSpeed;
	}



	@Override
	void init(GameContainer gc) {
		//SETTING UP SPRITE&ANIMATION

		addAnimation("SimpleGreenGuard.png", 6, 0, 9, 0, 150, "WalkLeft");
		addAnimation("SimpleGreenGuard.png", 2, 0, 5, 0, 150, "WalkRight");
		addAnimation("SimpleGreenGuard.png", 14, 0, 17, 0, 65, "deadLeft",false);
		addAnimation("SimpleGreenGuard.png", 10, 0, 13, 0, 65, "deadRight",false);
		addAnimation("SimpleGreenGuard.png", 19, 0, 19, 0, 150, "flyLeft");
		addAnimation("SimpleGreenGuard.png", 18, 0, 18, 0, 150, "flyRight");


		setCurrentAnimation("WalkRight");		
		this.borderColor = Color.black;
		this.checkForCollision = true;
		this.checkForGravity = true;
	}

	@Override
	public void aiBehaviour(GameContainer gc, int delta) {
		this.simpleWalk = true;
		//this.jumpAtObstacle = true;
		//this.shootAtPlayer = true;
		this.turnAtGap = true;
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
			laserSight(gc,g);
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
		extendedRange = 10;
	}

	private void noEnemyVision(int delta){
	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {


		if(sGO.getClass().equals(SpaceExplorer.class)){

			SpaceExplorer se = (SpaceExplorer) sGO;
			if(se.dashing&&HP>0){
				HP--;
				cond.playSound("explosion", 0.8f, 0.2f);
			}
			if(HP == 0){
				walkSpeed = 0f;
				this.checkForGravity = true;

				if(this.velocityVector.x>0){
					setCurrentAnimation("deadRight");
				}
				else{
					setCurrentAnimation("deadLeft");
				}
				this.velocityVector.x=0;

			}
		}
	}
}