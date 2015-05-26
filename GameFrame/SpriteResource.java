import java.io.InputStream;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SpriteResource extends Resource {
	private SpriteSheet sprite;
	private int xSize;
	private int ySize;
	public SpriteResource(String id, String path,int xSize,int ySize){
		super(id, path);
		this.xSize = xSize;
		this.ySize = ySize;
		initRes();	
	}

	@Override
	protected void initRes() {
		InputStream is = this.load(path);
		try {
			sprite = new SpriteSheet(id,is,xSize,ySize);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public SpriteSheet getSprite(){
		return sprite;
	}
}
