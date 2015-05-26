import java.io.InputStream;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundResource extends Resource {
	private Sound sound;
	
	public SoundResource(String id, String path){
		super(id, path);
		initRes();
		
		
	}

	@Override
	protected void initRes() {
		InputStream is = this.load(path);
		try {
			//music = new Music(is,id);
			sound = new Sound(is,path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Sound getSound(){
		return sound;
	}
	
	

}
