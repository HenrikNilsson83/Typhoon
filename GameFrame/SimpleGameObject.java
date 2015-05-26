import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


public abstract class SimpleGameObject {
	protected Vector2f gamePosition;
	protected Vector2f velocityVector;
	protected int faction = 0;
	protected boolean checkForCollision=false;
	protected boolean checkForGravity=false;
	protected int width;
	protected int height;
	protected int size;
	protected boolean jump;
	protected boolean leftObs = false;
	protected boolean rightObs = false;
	protected boolean northObs = false;
	protected boolean southObs = false;
	public boolean stuck = false;
	protected String idString;
	public boolean remove = false;
	public boolean gotLight = false;
	Light light;
	private boolean update = true;
	
	public SimpleGameObject(int x,int y, Vector2f pos){
		if(pos !=null){
			gamePosition = new Vector2f(pos.x,pos.y);
		}
		else{
			gamePosition = null;
		}
		velocityVector = new Vector2f(0,0);
		width = x;
		height = y;
	}
	
	abstract void init(GameContainer gc);
	abstract void update(GameContainer gc,int delta);
	abstract void render(GameContainer gc, Graphics g);

	public int getSize() {
		
		return size;
	}
	
	public boolean getJump(){
		return jump;
	}

	abstract void reset();

	public void setJump(boolean b) {
		jump = b;
		
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

	public abstract void damage();
	
}
