import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;


public class ComplexAnimation extends Animation {

	private ArrayList<Animation> aList;
	private int pointer;
	public ComplexAnimation(ArrayList<Animation> a){
			pointer = 0;
			aList = a;
	}
	
	public void addAnimation(String filename, int x1, int y1, int x2, int y2, int duration, String animationName, boolean b){
		SpriteSheet sprite;
		ResourceHandler rs = new ResourceHandler();
		SpriteResource sr = (SpriteResource) rs.get(filename);
		sprite = sr.getSprite();

		Animation a = new Animation(sprite, x1, y1, x2, y2, true, duration, true);
		a.setLooping(b);
		aList.add(a);
	}
	@Override
	public void draw(float x,float y){
		if(pointer==aList.size()-1){
			aList.get(pointer).draw(x, y);
			return;
		}
		else{
			if(aList.get(pointer).isStopped()){
				pointer++;
			}
		}
		aList.get(pointer).draw(x, y);
	}
	
}
