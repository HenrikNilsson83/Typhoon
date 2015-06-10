import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainIsland extends BasicGameState {
	ResourceHandler resourceHandler;
	//private static Pirate p;
	ObjectPool pool;			//Needed?!
	Physic physic;
	Scenery scenery;
	Demo_Level wObj;
	
	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		wObj = new Demo_Level(gc);
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		wObj.render(gc,g);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		wObj.update(gc,delta);
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

	

}
