import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


public class SnowFlake extends SimpleGameObject {
	
	float gravity;
	float wind;
	public SnowFlake(int x, int y, int s,float g,float w) {
		this.checkForCollision = false;
		this.checkForGravity = false;
		this.gamePosition = new Vector2f(x,y);
		this.size = s;
		this.gravity = g;
		this.wind = w;
	}

	@Override
	void init(GameContainer gc) {
		
		
	}

	@Override
	void update(GameContainer gc, int delta) {
		this.gamePosition.x+=(this.wind*delta);
		this.gamePosition.y+=(this.gravity*delta);
		
	}

	@Override
	void render(GameContainer gc, Graphics g) {
		g.setColor(Color.white);
		g.fillRect(this.gamePosition.x, this.gamePosition.y, size, size);
		
	}

	@Override
	void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		// TODO Auto-generated method stub
		
	}

}
