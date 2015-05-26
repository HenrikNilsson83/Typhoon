import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;


public class PickUpItem extends AdvancedGameObject{

	public PickUpItem(int x, int y, Vector2f pos, GameContainer gc) {
		super(x, y, pos, gc);
		size = 64;
		init(gc);
		jump = false;
		this.idString ="blast";
		this.showBorders = true;
		this.borderColor = Color.red;
		this.showFillRect = true;
		this.fillRectColor = Color.blue;
	}

	@Override
	void init(GameContainer gc) {
		this.checkForCollision = true;
		//this.checkForGravity = true;

	}
	
	@Override
	public void damage(){
		this.remove = true;
	}
}
