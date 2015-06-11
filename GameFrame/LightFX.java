import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class LightFX {
	public static ArrayList<Light> lightList;
	public static AlphaMap alphaMap;
	
	public LightFX(GameContainer gc,Color c){
		this.lightList = new ArrayList<Light>();
		alphaMap = new AlphaMap(gc,c);
	}
	
	public LightFX(){
		
	}
	
	public void renderLight(GameContainer gc){
		Camera cam = new Camera();
		alphaMap.render((int)(cam.getLightX()),(int)(cam.getLightY()),lightList);
		alphaMap.setList(lightList);
		this.lightList = new ArrayList<Light>();
	}
	
	public void updateLight(float multi,int delta){
		this.alphaMap.update(multi, delta);
	}
	
	public void addLight(Light l){
		this.lightList.add(l);
	}

	public void updateLight(float r, float g, float b) {
		this.alphaMap.update(r,b,g);
		
	}

	public void updateLight(Color c) {
		this.alphaMap.update(c);
		
	}
	
}
