

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class LevelClear extends BasicGameState {

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {

		g.setColor(Color.red);
		int width = 0;
		
		//STRING 1
		String s ="LEVEL CLEARED";
		width = g.getFont().getWidth(s);
		g.drawString(s, gc.getWidth()/2-width/2,gc.getHeight()/3 );
		
		//STRING 2
		s ="PRESS ENTER TO CONTINUE TO NEXT LEVEL";
		width = g.getFont().getWidth(s);
		g.drawString("PRESS ENTER TO CONTINUE TO NEXT LEVEL", gc.getWidth()/2-width/2,gc.getHeight()/2 );

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			System.out.println("GO TO LEVEL1");
			sbg.enterState(0);
		}
		if(input.isKeyDown( Input.KEY_R ))
        {
            
			System.out.println("RESET LEVEL1");
			sbg.getState(0).init(gc, sbg);
            sbg.enterState(0);
        }

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

}
