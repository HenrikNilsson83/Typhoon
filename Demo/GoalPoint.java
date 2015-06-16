import org.newdawn.slick.geom.Vector2f;


public class GoalPoint extends AdvancedGameObject {

	public int nextState;
	public GoalPoint(int x, int y, Vector2f pos, ObjectPool objPool,int nextState) {
		super(x, y, pos, objPool);
		this.showBorders = true;
		this.checkForCollision = true;
		this.checkForGravity = false;
		this.nextState = nextState;
	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		// TODO Auto-generated method stub
		
	}

}
