

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SpotLight extends Light {

	public SpotLight(int x, int y) {
		super(x, y);
		init();
	}

	@Override
	public void init() {
		this.degres = 0;
		ResourceHandler rs = new ResourceHandler();
		ImageResource sr = (ImageResource) rs.get("Light6.png");
		this.light =  sr.getImage();
		this.xOffset = -light.getWidth()/2;
		this.yOffset = -light.getHeight()/2;
		
	}

}
