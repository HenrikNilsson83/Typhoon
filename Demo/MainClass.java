import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;





public class MainClass extends StateBasedGame {
	
	private ArrayList<BasicGameState> stateList;
	

	public MainClass(String title) {
		super(title);
		stateList = new ArrayList <BasicGameState>();
	}

	public static void main(String[] cmdLn) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MainClass("GAME"));
		app.setDisplayMode(1280, 800, true);//FULLSCREN
		app.setDisplayMode((int)(0.8*1280), (int)(0.8*800), false);//smallFULLSCREN
		//app.setDisplayMode(1280, 800, true);//FULLSCREN
		app.setVSync(true);
		app.start();
	}

	

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		//addState(new MainMenu());
		addState(new Level1());
		addState(new MainMenu());
		addState(new LevelClear());
		
		
	}

}
