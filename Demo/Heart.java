import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class Heart extends AdvancedGameObject {

	

	public Heart(int x, int y, Vector2f pos, GameContainer gc,
			ObjectPool objPool) {
		super(x, y, pos, gc, objPool);
		addAnimation("heart.png", 0, 0, 0, 0, 1000, "heart",false);
		this.setCurrentAnimation("heart");
	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		if(sGO.getClass().equals(SpaceExplorer.class)){
			SpaceExplorer se = (SpaceExplorer)sGO;
			if(se.HP<se.maxHP){
			se.HP++;
			this.remove = true;
			}
		}
		
	}

	@Override
	void init(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void update(GameContainer gc, int delta, StateBasedGame sbg) {
		// TODO Auto-generated method stub
		
	}

}
