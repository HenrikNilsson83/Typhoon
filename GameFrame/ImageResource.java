import java.io.InputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class ImageResource extends Resource {
	private Image image;
	private int xSize;
	private int ySize;
	public ImageResource(String id, String path){
		super(id, path);
		
		initRes();
		
		
	}

	@Override
	protected void initRes() {
		InputStream is = this.load(path);
		try {
			//image = new Image(id,is,xSize,ySize);
			image = new Image(is,path,false);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Image getImage(){
		return image;
	}

	
}
