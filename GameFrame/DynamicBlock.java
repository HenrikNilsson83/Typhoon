import java.util.LinkedHashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;


public class DynamicBlock extends SimpleGameObject {

	private Animation a;
	int duration;
	private LinkedHashMap<String,Animation> spriteMap = new LinkedHashMap<String,Animation>();

	public void addAnimation(String filename, int x1, int y1, int x2, int y2, int duration, String animationName){


		SpriteSheet sprite;
		ResourceHandler rs = new ResourceHandler();
		SpriteResource sr = (SpriteResource) rs.get(filename);
		sprite = sr.getSprite();
		a = new Animation(sprite, x1, y1, x2, y2, true, duration, true);
		if(spriteMap.containsKey(animationName) == false){
			spriteMap.put(animationName, a);
		}
	}

	public void setCurrentAnimation(String animationName){

		if(spriteMap.containsKey(animationName)){ 
			a = spriteMap.get(animationName);
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
	void update(GameContainer gc, int delta, StateBasedGame sbg) {
		// TODO Auto-generated method stub

	}

	@Override
	void render(GameContainer gc, Graphics g) {
		if(this.a!=null){
			this.a.draw(this.gamePosition.x*16 ,this.gamePosition.y*16);
		}
	}

	@Override
	void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		// TODO Auto-generated method stub

	}

}
