

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class AlphaMap extends SimpleGameObject {
	Graphics g;
	Graphics g2;
	Image background;
	Image darkness;
	public int WIDTH;
	public int HEIGHT;
	
	
	Image im;
	Color c;
	float R; 
	float G; 
	float B;
	float brightness =1.7f;
	
	
	
	
	public AlphaMap(GameContainer container, Color color) {
		super(0, 0, new Vector2f(0,0),null);
		init(container);
		this.WIDTH = container.getWidth();
		this.HEIGHT = container.getHeight();
		this.c = color;
		B = c.b;
		G = c.g;
		R = c.r;
		
	}


	public void init(GameContainer container){
		try {
			
			background = new Image(container.getScreenWidth(),container.getScreenHeight());
			g = background.getGraphics();
			g2 = new Graphics();
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		//ResourceHandler rs = new ResourceHandler();
		//ImageResource sr = (ImageResource) rs.get("LightBulb.png");
		//im =  sr.getImage();
		
		
		
	}

	
	public void update(float multi, int delta) {
		c .b =B*multi*1f;
		c.g = G*multi*1f;
		c.r = R*multi*1f;
	}

	
	public void render(int x, int y,ArrayList<Light> lights) {
		g.setBackground(new Color(0,0,0,0));
		g.clear();
		g.setColor(c);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for(int i = 0;i<lights.size();i++){
			if(lights.get(i).visible){
				g.drawImage(lights.get(i).getImage(), lights.get(i).lightPosition.getX() -x+lights.get(i).xOffset, lights.get(i).lightPosition.getY()-y+lights.get(i).yOffset);
			}
		}
		g.flush();
		g2.setDrawMode(g2.MODE_COLOR_MULTIPLY);
		background.draw(x,y);
		g2.setDrawMode(g2.MODE_NORMAL);	
	}
	
	public void setList(ArrayList<Light> lList){
		//this.lights = lList;
	}
	
	
	
	public void addLight(Light l){
		//this.lights.add(l);
	}


	@Override
	void update(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
		
	}


	@Override
	void render(GameContainer gc, Graphics g) {
		// TODO Auto-generated method stub
		
	}


	@Override
	void reset() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void damage() {
		// TODO Auto-generated method stub
		
	}
}