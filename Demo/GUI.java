import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;


public class GUI extends SimpleGUI {
	private Image button1;
	private Image[] buttons;
	private int buttonCount = 2;
	private int exception=0;
	private ObjectPool op;
	private static SpriteSheet wepInf;
	public float vision;
	public GUI(int x, int y, Vector2f gamePosition) {
		super(x, y, gamePosition);
		
		buttons = new Image[buttonCount];
		op = new ObjectPool();
		/*ResourceHandler rh = new ResourceHandler();
		ImageResource resource;
		resource = (ImageResource) rh.get("button1.png");*/
		SpriteSheet wepInf;
		ResourceHandler rs = new ResourceHandler();
		SpriteResource sr = (SpriteResource) rs.get("weaponinfo.png");
		wepInf = sr.getSprite();
		button1 = wepInf.getSprite(0, 0);
		buttons[0]=button1;
		
		sr = (SpriteResource) rs.get("SpaceExplorer.png");
		wepInf = sr.getSprite();
		button1 = wepInf.getSprite(0, 0);
		buttons[1]=button1;
		vision = 0.1f;
		
		
	}

	@Override
	public void guiContent(GameContainer gc, int delta) {
		exception = delta;
		// WEAPON STATS
		SimpleGameObject hero = op.getFriendlyList().get(0);
		int r = hero.resource1;
		ResourceHandler rs = new ResourceHandler();
		SpriteResource sr = (SpriteResource) rs.get("weaponinfo.png");
		wepInf = sr.getSprite();
		button1 = this.wepInf.getSprite(r, 0);
		buttons[0]=button1;
		
		//VISIBILITY
		
		sr = (SpriteResource) rs.get("SpaceExplorer.png");
		wepInf = sr.getSprite();
		button1 = this.wepInf.getSprite(0, 0);
		buttons[1]=button1;
		
		
	}

	@Override
	public void renderContent(GameContainer gc, Graphics g) {
		
		
	}

	@Override
	public void drawContent(GameContainer gc, Graphics g) {
		g.setColor(new Color(0.3f,0.3f,0.3f));
		
		
		
		/*for(int i = 0;i<buttons.length;i++){
			g.setDrawMode(g.MODE_COLOR_MULTIPLY);
			if(0==this.exception){
				g.setDrawMode(g.MODE_NORMAL);
				button1.draw(this.gamePosition.x+4,this.gamePosition.y+4);
			}else{
				g.fillRect(this.gamePosition.x+4,this.gamePosition.y+4, 64, 64);
				button1.draw(this.gamePosition.x+4,this.gamePosition.y+4);
			}
			
		}*/
		
		g.setDrawMode(g.MODE_NORMAL);
		int i = 0;
		buttons[0].draw(this.gamePosition.x+4+i*64,this.gamePosition.y+4);
		i = 1;
		
		g.setDrawMode(g.MODE_NORMAL);
		buttons[1].draw(this.gamePosition.x+4+i*64,this.gamePosition.y+4);
		g.setDrawMode(g.MODE_COLOR_MULTIPLY);
		g.setColor(new Color(1f*vision,1f*vision,1f*vision));
		g.fillRect(this.gamePosition.x+4+i*64,this.gamePosition.y+4, 64, 64);
		g.setDrawMode(g.MODE_NORMAL);
		
	}
	

	

	

}
