import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class Blast extends AdvancedGameObject {
	private boolean jump;
	private float speed = 1.0f;
	public Blast(int x, int y, Vector2f pos, GameContainer gc, int direction, int fact, ObjectPool objPool) {
		super(x, y, pos, gc, objPool);
		if(direction==-1){
			this.velocityVector.x=-speed;
			this.velocityVector.y=-0.1f*speed;
		}
		if(direction==2){
			this.velocityVector.x=speed;
			this.velocityVector.y=-0.1f*speed;
		}
		
		if(direction==0){
			this.velocityVector.y=-speed;
		}
		
		if(direction==1){
			this.velocityVector.y=speed;
		}
		init(gc);
		jump = false;
		
		this.showBorders = true;
		this.borderColor = Color.magenta;
		
		this.showFillRect = true;
		this.fillRectColor = Color.darkGray;
		
	}

	public Blast(int x, int y, Vector2f pos, GameContainer gc,int fact,Vector2f vec, ObjectPool objPool) {
		super(x, y, pos, gc, objPool);
		init(gc);
		jump = false;
		this.velocityVector = vec;
		this.velocityVector.x = this.velocityVector.x*speed;
		this.velocityVector.y = this.velocityVector.y*speed;
		
		this.showBorders = true;
		this.borderColor = Color.red;
		
		this.showFillRect = true;
		this.fillRectColor = Color.green;
	}

	@Override
	void init(GameContainer gc) {
		this.checkForCollision = true;
		this.checkForGravity = true;

	}

	@Override
	void update(GameContainer gc, int delta,StateBasedGame sbg) {
		
		if(this.rightObs||this.leftObs||this.northObs||this.southObs){
			//explode(delta);
			//this.damage();
			this.remove = true;
		}

	}
	
	public void objectCollide(SimpleGameObject sGO){
		//ParticleFx.addExplosion(this.gamePosition.x, this.gamePosition.y); 
		this.remove = true;
	}

}
