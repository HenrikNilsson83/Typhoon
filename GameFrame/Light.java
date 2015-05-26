

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;



public abstract class Light{
	public Image light;
	public Vector2f lightPosition;
	public int degres;
	public boolean visible = true;
	
	public Light(int x,int y){
		
		this.lightPosition = new Vector2f(x,y);
		
	}
	public abstract void init();
	public Image getImage(){
		return light;
	}
	
	


}
