import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public abstract class SimpleGUI extends MyGameObject {

	private int width;
	private Rectangle back;
	private int hight;
	private Camera cam = new Camera();
	public SimpleGUI(int x, int y, Vector2f gamePosition, ObjectPool objPool) {
		super(x, y, gamePosition, objPool);
		// TODO Auto-generated constructor stub
		width = x;
		hight = y;
		
		
	}
	
	@Override
	void init(GameContainer gc) {
		
		
	}
	@Override
	void update(GameContainer gc, int delta,StateBasedGame sbg) {
		guiContent(gc,delta);
		
	}
	@Override
	void render(GameContainer gc, Graphics g) {
		
		cam.untranslateGraphics();
		gc.getGraphics().setColor(Color.darkGray);
		gc.getGraphics().fillRect(this.gamePosition.x,this.gamePosition.y,width,height);
		gc.getGraphics().setColor(Color.black);
		//gc.getGraphics().drawRect(this.gamePosition.x,0,width,height);
		gc.getGraphics().drawRect(this.gamePosition.x,this.gamePosition.y,width,height);
		drawContent(gc,g);
		cam.translateGraphics();
		
		
	}
	
	public abstract void drawContent(GameContainer gc, Graphics g);
	@Override
	void reset() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void objectCollide(SimpleGameObject sGO) {
		// TODO Auto-generated method stub
		
	}
	
	public abstract void guiContent(GameContainer gc, int delta);
	public abstract void renderContent(GameContainer gc, Graphics g);

}
