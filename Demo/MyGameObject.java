import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public abstract class MyGameObject extends SimpleGameObject {
	
	Light light;
	public MyGameObject(int x, int y,Vector2f gamePosition, ObjectPool objPool) {
		super(x, y, gamePosition, objPool);
	}

	@Override
	abstract void init(GameContainer gc);

	@Override
	abstract void update(GameContainer gc, int delta,StateBasedGame sbg);

	@Override
	abstract void render(GameContainer gc, Graphics g);
	
	public void setFaction(int f ){
		if(f<2&&f>-2)
		{
			faction = f;
		}
		else {
			throw new IllegalArgumentException("Faction Does not Exist");
		}
	}
	
	public int getFaction(){
		return faction;
	}
	

}
