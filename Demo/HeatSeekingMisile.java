import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class HeatSeekingMisile extends AdvancedGameObject {

	private float speed = 0.15f;
	private SimpleGameObject target;
	private int lifeTime = 5000;
	private boolean jump;

	public HeatSeekingMisile(SimpleGameObject target, Vector2f pos, GameContainer gc,int fact,Vector2f vec, ObjectPool objPool) {
		super(6, 6, pos, gc, objPool);
		init(gc);
		jump = false;
		this.target = target;
		this.velocityVector = vec;
		this.showBorders = true;
		this.borderColor = Color.red;
		this.showFillRect = true;
		this.fillRectColor = Color.green;
		this.velocityVector.x = this.velocityVector.x*speed;
		this.velocityVector.y = this.velocityVector.y*speed;
	}

	@Override
	void init(GameContainer gc) {
		this.checkForCollision = true;
		//this.checkForGravity = true;

	}

	@Override
	void update(GameContainer gc, int delta,StateBasedGame sbg) {

		if(this.rightObs||this.leftObs||this.northObs||this.southObs){

			this.remove = true;
		}
		AI(gc,delta);
		
	}

	private void AI(GameContainer gc, int delta) {
		float v = getDirectionToTarget(target);
		this.velocityVector.x= (float) (speed*Math.cos(v));
		this.velocityVector.y= (float) (speed*Math.sin(v));
		this.lifeTime-=delta;
		if(lifeTime<0){
			this.remove = true;
		}
		
	}

	float getDirectionToTarget(SimpleGameObject t){

		float v = 0;
		if(this.gamePosition.x>t.gamePosition.x+32){
			float a = (t.gamePosition.x+32-this.gamePosition.x);
			float b = t.gamePosition.y+32 -this.gamePosition.y;
			v = (float) Math.atan(b/a);
			v+=Math.PI;
		}
		else{
			float a = (t.gamePosition.x+32-this.gamePosition.x);
			float b = t.gamePosition.y+32 -this.gamePosition.y;
			v = (float) Math.atan(b/a);
		}
		return v;
	}

	public void damage(){
		this.remove = true;
	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		// TODO Auto-generated method stub
		
	}

}
