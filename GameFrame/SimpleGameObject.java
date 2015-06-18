import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public abstract class SimpleGameObject {
	protected Vector2f gamePosition;					//i advanced? eller inte. osynliga saker man kan springa på, vettigt?
	protected Vector2f lastGamePosition;				//i advanced?
	protected Vector2f velocityVector;					//i advanced?
	protected boolean checkForCollision = false;		//i advanced?
	protected boolean checkForGravity = false;			//i advanced?
	protected int width;								//i advanced?
	protected int height;								//i advanced?
	protected int size;									//i advanced?
	public Hitbox hitbox;								//i advanced?
	protected boolean jump;								//borde tas bort tror jag
	protected boolean leftObs = false;					//i advanced?
	protected boolean rightObs = false;					//i advanced?
	protected boolean northObs = false;					//i advanced?
	protected boolean southObs = false;					//i advanced?
	protected String idString;							//av nytta?				
	public boolean remove = false;
	public boolean gotLight = false;					//i advanced? saker med render borde vara advnaced?
	Light light;										//i advanced?
	private boolean update = true;
	protected SimpleGameObject target;
	public int HP = 1;									//borde tas bort tror jag
	public int resource1 = 0;							//borde tas bort tror jag
	protected ObjectPool objPool;   					//Kanske ska ha till vilken Level objectet tillhör istället??!?
	public int maxHP;									//i spel?

	public SimpleGameObject(int w,int h, Vector2f pos, ObjectPool objPool){
		if(pos !=null){
			gamePosition = new Vector2f(pos.x,pos.y);
			lastGamePosition = new Vector2f(pos.x,pos.y);
			this.hitbox = new Hitbox(w,h,(int)(pos.x),(int)(pos.y));
		}
		else{
			gamePosition = null;
			this.hitbox = new Hitbox(w,h,0,0);
		}
		velocityVector = new Vector2f(0,0);
		width = w;
		height = h;
		this.objPool = objPool;

	}

	public SimpleGameObject() {
		// TODO Auto-generated constructor stub
	}

	abstract void init(GameContainer gc);
	abstract void update(GameContainer gc,int delta, StateBasedGame sbg);
	abstract void render(GameContainer gc, Graphics g);

	public int getSize() {
		return size;
	}

	public void setLeftObs(boolean b) {
		this.leftObs = b;	
	}

	public void setRightObs(boolean b) {
		this.rightObs = b;	
	}

	public boolean getRightObs(){
		return rightObs;
	}
	public boolean getleftObs(){
		return leftObs;
	}

	public void setIdString(String s){
		this.idString = s;
	}

	public String getIdString(){
		return idString;
	}

	public void setSouthObs(boolean b) {
		this.southObs = b;
	}

	public void setNorthObs(boolean b) {
		this.northObs = b;
	}

	public Light getLight() {
		light.lightPosition.x = (this.gamePosition.getX()-light.getImage().getWidth()/2+this.size/2);
		light.lightPosition.y = (this.gamePosition.getY()-light.getImage().getHeight()/2+this.size/1.6f);
		return light;
	}

	public void setLight(Light l){
		this.gotLight = true;
		this.light=l;
	}

	public boolean getUpdate(){
		return update ;
	}

	public void setUpdate(boolean b){
		this.velocityVector.set(0, 0);
		this.update = b;
	}

	public void setTarget(SimpleGameObject t){
		this.target = t;
	}

	public abstract void objectCollide(SimpleGameObject sGO);

}
