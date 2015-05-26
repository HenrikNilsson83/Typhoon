import java.io.InputStream;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Music;

public class MusicResource extends Resource {
	private Music music;
	
	public MusicResource(String id, String path){
		super(id, path);
		initRes();
		
		
	}

	@Override
	protected void initRes() {
		InputStream is = this.load(path);
		try {
			//music = new Music(is,id);
			music = new Music(is,path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Music getMusic(){
		return music;
	}

}
