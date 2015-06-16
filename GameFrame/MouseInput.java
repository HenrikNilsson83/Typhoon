import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;


public class MouseInput {
	private boolean leftMB;
	private boolean rightMB;
	private Vector2f mPos;
	private int screenHeight;
	private Image mouseIcon;
	private int size;

	public MouseInput(int x, int y, GameContainer gc){
		mPos = new Vector2f(x,y);
		screenHeight = gc.getHeight();
		size = 6;
	}

	public boolean updateMouse(int delta){
		leftMB = Mouse.isButtonDown(0);
		rightMB = Mouse.isButtonDown(1);
		mPos.x = Mouse.getX();
		mPos.y = screenHeight - Mouse.getY();
		return leftMB||rightMB;
	}

	public boolean getLMB(){
		return this.leftMB;
	}
	public boolean getRMB(){
		return this.rightMB;
	}
	public Vector2f getMousePos(){
		return this.mPos;
	}

	public void render(GameContainer gc, Graphics g){
		g.setColor(Color.orange);
		MapInfo temScen = new MapInfo();
		Camera cam = new Camera();
		cam.untranslateGraphics();
		g.fillOval(mPos.x+size/2, mPos.y+size/2, size, size);
		cam.translateGraphics();
	}
}

