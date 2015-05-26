import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;


public class GUI extends SimpleGUI {
	private Image button1;
	private Image[] buttons;
	private int buttonCount = 1;
	private int exception=0;
	public GUI(int x, int y, Vector2f gamePosition) {
		super(x, y, gamePosition);
		
		buttons = new Image[buttonCount];
		ResourceHandler rh = new ResourceHandler();
		ImageResource resource;
		resource = (ImageResource) rh.get("button1.png");
		button1 = resource.getImage();
		System.out.println(button1.getWidth());
		buttons[0]=button1;
	}

	@Override
	public void guiContent(GameContainer gc, int delta) {
		exception = delta;
		
	}

	@Override
	public void renderContent(GameContainer gc, Graphics g) {
		
		
	}

	@Override
	public void drawContent(GameContainer gc, Graphics g) {
		g.setColor(new Color(0.3f,0.3f,0.3f));
		
		
		
		for(int i = 0;i<buttons.length;i++){
			g.setDrawMode(g.MODE_COLOR_MULTIPLY);
			if(0==this.exception){
				g.setDrawMode(g.MODE_NORMAL);
				button1.draw(this.gamePosition.x+4,this.gamePosition.y+4);
			}else{
				g.fillRect(this.gamePosition.x+4,this.gamePosition.y+4, 64, 64);
				button1.draw(this.gamePosition.x+4,this.gamePosition.y+4);
			}
			
		}
		g.setDrawMode(g.MODE_NORMAL);
		
	}
	

	

	

}
