import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


public class HellWormPart extends EnemyGameObject {
	private Vector2f lastPosition;


	int mode = 0;
	public int engageTimer = 3000;
	public int engageMax = 3000;
	public float goToX;
	public float goToY;
	public static int maxLength = 12;
	private int delay = 0;
	private int delayTime;

	private LinkedList<Vector2f> queue;
	HellWormPart p;






	public HellWormPart(int x, int y, Vector2f pos,Vector2f vel, GameContainer gc, ObjectPool objPool,int currentL, int delayTime) {
		super(x, y, pos, gc, objPool);
		System.out.println(currentL);
		size = 64;
		this.delayTime = delayTime;
		jump = false;
		this.idString = "SimpleEnemy";
		lastPosition = new Vector2f(x, y);	
		this.dir = 1;
		HP = 1;
		cond = new Conductor();
		target = null;
		this.data1 =0;
		this.data3 = 0;
		this.walkSpeed*=4;
		this.velocityVector.x=this.walkSpeed;
		this.goToX = -1;
		this.goToY = -1;
		currentL++;
		if(currentL<maxLength){
			p = new HellWormPart(x,y,new Vector2f(this.gamePosition.x-width,this.gamePosition.y),new Vector2f(this.velocityVector.x,this.velocityVector.y),gc,objPool,currentL,delayTime);
			objPool.addToCollisionPool(p);
		}
		queue = new LinkedList<Vector2f>();
	}



	@Override
	void init(GameContainer gc) {
		//SETTING UP SPRITE&ANIMATION

		/*addAnimation("LiquidSoldier.png", 20, 0, 23, 0, 150, "WalkLeft");
		addAnimation("LiquidSoldier.png", 0, 0, 3, 0, 150, "WalkRight");
		addAnimation("LiquidSoldier.png", 27, 0, 39, 0, 65, "deadLeft",false);
		addAnimation("LiquidSoldier.png", 7, 0, 19, 0, 65, "deadRight",false);
		addAnimation("LiquidSoldier.png", 24, 0, 25, 0, 150, "flyLeft");*/
		addAnimation("HellWormHead.png", 1, 0, 1, 0, 1000, "head");	
		this.setCurrentAnimation("head");
		float r = (float) Math.random();
		float g = (float) Math.random();
		float b = (float) Math.random();
		//this.borderColor = Color.darkGray;
		this.fillRectColor = Color.darkGray;
		//this.showFillRect = true;
		this.checkForCollision = true;
		this.checkForGravity = true;
		//this.faction = -1;	
		this.checkForGravity = false;
	}

	@Override
	public void aiBehaviour(GameContainer gc, int delta) {

		if(this.goToX!=-1){
			this.gamePosition.x = this.goToX;
			this.gamePosition.y = this.goToY;
		}
		
		if(delay<=delayTime){
			delay +=delta;
			this.queue.addLast(new Vector2f(this.gamePosition.x,this.gamePosition.y));
		}
		else if(p!=null){
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

		if(currentAnimation != null){
			currentAnimation.draw((int)(gamePosition.x), (int)(gamePosition.y+1));	
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
			

			
		}

		else if(sGO.getClass().equals(SpaceExplorer.class)){

			

		}
			
		

		
	}
}
