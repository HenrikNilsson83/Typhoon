import java.io.InputStream;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class TileMapResource extends Resource {
	private TiledMap tm;

	public TileMapResource(String id, String path) {
		super(id, path);
		initRes();
	}

	@Override
	void initRes() {
		InputStream is = this.load(path);
		try {
			tm = new TiledMap(is,"TileMap");
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	
	public TiledMap getMap(){
		return tm;
	}

}
