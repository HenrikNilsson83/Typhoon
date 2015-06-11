import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.Path;

import java.util.*;

public abstract class EnemyGameObject extends SimpleGameObject{	
	//en dict med animations?
	//functiotioner för att lägga till anims
	//en var som bestämmer current anim
	//size

	//ram eller kanske helfärgad. både?
	//ramfärg

	//pos?
	//velo??


	private GameContainer container;

	Light light;
	private LinkedHashMap<String,Animation> spriteMap = new LinkedHashMap<String,Animation>();

	protected Animation currentAnimation = null;
	protected boolean showBorders = false;
	protected Color borderColor = Color.red;
	protected boolean showFillRect = false;
	protected Color fillRectColor = Color.blue;

	// ENEMY SETUP VARS 
	int dir;
	protected int visionX = 380;
	protected int visionY = 5;
	protected float walkSpeed = 0.03f;
	protected int turnCooldown = 0;
	protected int turnCooldownMax = 500;
	protected float turnJump = -0.02f;
	protected float jumpV = -0.4f;
	protected Conductor cond;
	protected int reCharge = 300;
	protected int loadTime = 300;
	protected int extendedRange = 1;
	protected boolean renderPath= true;


	int goalX = 84;
	int goalY = 105;
	int tileSize = 16;
	public int approachDir;
	protected int max = 7;
	protected Path p = null;
	protected int timeToPath=2000;
	protected int PathFreq = 2000;
	protected int currentStep = 7;
	protected Rectangle player;
	protected int tempDir = 0;


	//BEHAVIOURS
	protected boolean simpleWalk;
	protected boolean jumpAtObstacle;
	protected boolean turnAtGap;
	protected boolean jumpOverLedge;
	protected boolean followPlayerSimple;
	protected boolean followPlayerPathFinding;
	protected boolean shootAtPlayer;

	public EnemyGameObject(int x, int y, Vector2f pos, GameContainer gc, ObjectPool objPool) {
		super(x, y, pos, objPool);
		//GROUP 1
		followPlayerPathFinding = false;
		followPlayerSimple = false;
		simpleWalk = false;
		//GROUP 2
		jumpAtObstacle = false;
		turnAtGap = false;
		jumpOverLedge = false;
		// GROUP 3
		shootAtPlayer = false;
		init(gc);
		gc = container;
	}



	public EnemyGameObject(int x, int y, Vector2f pos, ObjectPool objPool) {
		super(x,y,pos, objPool);
	}

	void addAnimation(String filename, int x1, int y1, int x2, int y2, int duration, String animationName){


		SpriteSheet sprite;
		ResourceHandler rs = new ResourceHandler();
		SpriteResource sr = (SpriteResource) rs.get(filename);
		sprite = sr.getSprite();
		Animation a = new Animation(sprite, x1, y1, x2, y2, true, duration, true);
		if(spriteMap.containsKey(animationName) == false){
			spriteMap.put(animationName, a);
		}
	}

	public void addAnimation(String filename, int x1, int y1, int x2, int y2, int duration,
			String animationName, boolean b) {
		SpriteSheet sprite;
		ResourceHandler rs = new ResourceHandler();
		SpriteResource sr = (SpriteResource) rs.get(filename);
		sprite = sr.getSprite();
		Animation a = new Animation(sprite, x1, y1, x2, y2, true, duration, true);
		a.setLooping(b);
		if(spriteMap.containsKey(animationName) == false){
			spriteMap.put(animationName, a);
		}

	}

	public void addAnimation(String animationName,ArrayList<Animation> a){
		ComplexAnimation cA = new ComplexAnimation(a);
		if(spriteMap.containsKey(animationName) == false){
			spriteMap.put(animationName, cA);
		}
	}

	public void setCurrentAnimation(String animationName){

		if(spriteMap.containsKey(animationName)){ 
			currentAnimation = spriteMap.get(animationName);
		}
		else{
			System.out.println(animationName + " NOT FOUND!!!!!!");
		}
	}




	@Override
	void init(GameContainer gc) {
		// TODO Auto-generated method stub

	}

	@Override
	void update(GameContainer gc, int delta) {

		aiBehaviour(gc,delta);

		if(this.jumpAtObstacle&&!this.followPlayerPathFinding){
			climbing(delta);
		}

		if(this.simpleWalk&&!this.followPlayerSimple&&!this.followPlayerPathFinding&&!this.jumpAtObstacle){
			simpleWalk(delta);
		}

		if(this.shootAtPlayer&&target!=null){
			shootAtPlayer(gc,delta);
		}

		if(this.turnAtGap&&!this.followPlayerPathFinding){
			turnAtGap(delta);
		}

		if(this.followPlayerPathFinding){
			followPath(gc,delta);
		}

		if(turnCooldown > 0){
			turnCooldown -= delta;
		}

		setAnimation(gc,delta);

	}

	// GROUP 1


	private void simpleWalk(int delta) {
		if(this.rightObs){
			this.velocityVector.x =-this.walkSpeed;
			dir = 0;
		}
		else if(this.leftObs){
			this.velocityVector.x =this.walkSpeed;
			dir = 1;
		}
	}

	private void followPath(GameContainer gc,int delta){
		if(p ==null&&target!=null&&target.southObs){

			int sX = (int)(this.gamePosition.x/tileSize);
			int sY = (int)(this.gamePosition.y/tileSize);
			int gX = (int)(target.gamePosition.x/tileSize);
			int gY = (int)((target.gamePosition.y +this.size)/tileSize);
			PathFinding pf = new PathFinding(sX,sY,gX,gY);
			p = pf.getPath();
			if(p.getLength()<7&&p!=null){
				this.currentStep = 0;
				max = 0;
			}
			// SET START VELOCITY_X
			if(p.getX(0)*tileSize>this.gamePosition.x){
				this.velocityVector.x=this.walkSpeed;
			}
			else{
				this.velocityVector.x=-this.walkSpeed;
			}

		}

		if(p!=null){

			if(this.velocityVector.x>0){
				System.out.println("RIGHT");
				{
					player = new Rectangle(this.gamePosition.x+size*1.2f,this.gamePosition.y,4,(int)(this.size));
					followPathR(gc,delta);
				}
			}

			if(this.velocityVector.x<0){
				System.out.println("LEFT");
				{
					player = new Rectangle(this.gamePosition.x+size*1.2f,this.gamePosition.y,4,(int)(this.size));
					followPathR(gc,delta);
				}
			}
		}
	}

	private void followPathR(GameContainer gc, int delta) {
		whereOnPath();

		int tS = 16;
		int nextSt = currentStep+1;
		if(nextSt>=p.getLength()-1){
			nextSt = p.getLength()-1;
		}
		if(nextSt+3 >p.getLength()){
			p = null;
			this.currentStep = 0;
			max = 0;
			return;
		}

		float v = getDirectionToTarget(p.getX(nextSt)*tS,p.getY(nextSt)*tS);

		if(!this.jump){
			if(this.gamePosition.x>p.getX(nextSt)*16){
				dir = 0;
				if(this.leftObs){
					this.velocityVector.y = this.jumpV;
					this.jump = true;
					this.tempDir = 0;
				}
			}
			else if(this.gamePosition.x<p.getX(nextSt)*16&&turnCooldown<=0){
				turnCooldown = this.turnCooldownMax; 
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
			if(tempDir==0&&turnCooldown<=0){
				turnCooldown = this.turnCooldownMax; 
				this.velocityVector.x = -this.walkSpeed;
			}
			else if(tempDir==1){
				turnCooldown = this.turnCooldownMax; 
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
			//player = new Rectangle(this.gamePosition.x,this.gamePosition.y,64,(int)(this.size));
			if(player.intersects(path)&&i>max){
				max = i;
			}
		}
		this.currentStep = max;

	}

	// GROUP 2

	private void climbing(int delta) {

		if(this.rightObs){
			this.velocityVector.x =this.walkSpeed;
			dir = 1;
		}
		else if(this.leftObs){
			this.velocityVector.x =-this.walkSpeed;
			dir = 0;
		}

		if(turnCooldown <= 0 && southObs   && (this.leftObs)){
			turnCooldown = turnCooldownMax;
			int xG = (int)(((this.gamePosition.x/16)-1));
			int yG = (int)(((this.gamePosition.y/16)-1));
			boolean b = isItBlocked(xG,yG);
			if(!b){
				this.velocityVector.y = jumpV;
				jump = true;
			}
			else{
				dir = 1;
				this.velocityVector.x = this.walkSpeed;
			}


		}
		else if(turnCooldown <= 0 && southObs && (this.rightObs )){
			turnCooldown = turnCooldownMax;
			int xG = (int)(((this.gamePosition.x/16)+1+size/this.tileSize));
			int yG = (int)(((this.gamePosition.y/16)-1));
			boolean b = isItBlocked(xG,yG);
			if(!b){
				this.velocityVector.y = jumpV;
				jump = true;
			}
			else{
				dir = 0;
				this.velocityVector.x = -this.walkSpeed;
			}


		}
	}

	private void turnAtGap(int delta) {
		if(turnCooldown <= 0 && !jump && dir == 0 ){
			turnCooldown = turnCooldownMax;
			int xG = (int)(((this.gamePosition.x/16)-1));
			int yG = (int)(((this.gamePosition.y/16)+size/tileSize+1));
			boolean b = isItBlocked(xG,yG);
			if(!b){
				dir = 1;
				this.velocityVector.x = this.walkSpeed;
			}


		}
		else if(turnCooldown <= 0 && !jump && dir == 1 ){
			//dir = 0;
			turnCooldown = turnCooldownMax;
			int xG = (int)(((this.gamePosition.x/16)+4));
			int yG = (int)(((this.gamePosition.y/16)+size/tileSize+1));
			boolean b = isItBlocked(xG,yG);
			if(!b){
				dir = 0;
				this.velocityVector.x = -this.walkSpeed;
			}
		}

	}

	// GROUP 3

	protected void shootAtPlayer(GameContainer gc,int delta) {

		boolean enemeyContact = enemyContact(gc,delta);

		if(enemeyContact){
			shootAtTarget(gc,delta);
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
			Blast b = new Blast(6,6 ,mouth,gc,-2,new Vector2f((float)(1*Math.cos(v)),(float) (1*Math.sin(v))), objPool);
			//ObjectPool op = new ObjectPool();
			this.objPool.addToCollisionPool(b);
			//op.addToPool(b);
			reCharge=0;
		}
		this.reCharge(delta);
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

	public abstract void aiBehaviour(GameContainer gc,int delta);

	protected abstract void setAnimation(GameContainer gc, int delta);

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
			currentAnimation.draw(gamePosition.x, gamePosition.y+1);	
		}




	}

	protected void renderPath(GameContainer gc, Graphics g) {
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

	private void reCharge(int delta) {
		if(reCharge<loadTime){
			reCharge+=delta;
		}

	}



	@Override
	void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void damage() {
		// TODO Auto-generated method stub

	}

	//inte säker på detta
	public void setFaction(int f ){
		if(f<2&&f>-2)
		{
			faction = f;
		}
		else {
			throw new IllegalArgumentException("Faction Does not Exist");
		}
	}

	public int getFaction(){
		return faction;
	}

	protected boolean isItBlocked(int xG, int yG) {
		MapInfo scen = new MapInfo();		
		return scen.getBlocked(xG, yG);
	}

}


