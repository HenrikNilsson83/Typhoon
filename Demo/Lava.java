import java.util.LinkedHashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;


public class Lava extends DynamicBlock {
	public Lava(GameContainer gc,int x,int y){
		this.gamePosition = new Vector2f(x,y);
		duration = (int) (145+10*Math.random());
		this.addAnimation("lava.png", 0, 0, 3, 0, duration, "lavaAnimation");
		this.setCurrentAnimation("lavaAnimation");
		
	}
	
	
}
