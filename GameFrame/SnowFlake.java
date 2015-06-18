import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class SnowFlake extends SimpleGameObject {
	
	float gravity;
	float wind;
	public SnowFlake(int x, int y, int s,float g,float w) {
		this.checkForCollision = false;
		this.checkForGravity = false;
		this.gamePosition = new Vector2f(x,y);
		this.width = s;
		this.height = s;
		this.gravity = g;
		this.wind = w;
	}

	@Override
	void init(GameContainer gc) {
		
		
	}

	@Override
	void update(GameContainer gc, int delta,StateBasedGame sbg) {
		this.gamePosition.x+=(this.wind*delta);
		this.gamePosition.y+=(this.gravity*delta);
		
	}

	@Override
	void render(GameContainer gc, Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(this.gamePosition.x, this.gamePosition.y, this.width, this.height);
		
	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		// TODO Auto-generated method stub
		
	}

}
