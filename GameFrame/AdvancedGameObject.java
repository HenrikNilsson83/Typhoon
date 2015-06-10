import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import java.util.*;

public class AdvancedGameObject extends SimpleGameObject{	
	//en dict med animations?
	//functiotioner för att lägga till anims
	//en var som bestämmer current anim
	//size

	//ram eller kanske helfärgad. både?
	//ramfärg

	//pos?
	//velo??


	private GameContainer container;

	Light light;
	private LinkedHashMap<String,Animation> spriteMap = new LinkedHashMap<String,Animation>();

	protected Animation currentAnimation = null;

	protected boolean showBorders = false;
	protected Color borderColor = Color.red;

	protected boolean showFillRect = false;
	protected Color fillRectColor = Color.blue;

	public AdvancedGameObject(int x, int y, Vector2f pos, GameContainer gc, ObjectPool objPool) {
		super(x, y, pos, objPool);
		init(gc);
		gc = container;
	}



	public AdvancedGameObject(int x, int y, Vector2f pos, ObjectPool objPool) {
		super(x,y,pos, objPool);
	}



	/*
		frames - The sprite sheet containing the frames
		x1 - The x coordinate of the first sprite from the sheet to appear in the animation
		y1 - The y coordinate of the first sprite from the sheet to appear in the animation
		x2 - The x coordinate of the last sprite from the sheet to appear in the animation
		y2 - The y coordinate of the last sprite from the sheet to appear in the animation
		horizontalScan - True if the sprites are arranged in hoizontal scan lines. Otherwise vertical is assumed
		duration - The duration each frame should be displayed for
		autoUpdate - True if this animation should automatically update based on the render times
	 */
	void addAnimation(String filename, int x1, int y1, int x2, int y2, int duration, String animationName){


		SpriteSheet sprite;
		ResourceHandler rs = new ResourceHandler();
		SpriteResource sr = (SpriteResource) rs.get(filename);
		sprite = sr.getSprite();
		Animation a = new Animation(sprite, x1, y1, x2, y2, true, duration, true);
		if(spriteMap.containsKey(animationName) == false){
			spriteMap.put(animationName, a);
		}
	}

	public void addAnimation(String filename, int x1, int y1, int x2, int y2, int duration,
			String animationName, boolean b) {
		SpriteSheet sprite;
		ResourceHandler rs = new ResourceHandler();
		SpriteResource sr = (SpriteResource) rs.get(filename);
		sprite = sr.getSprite();

		Animation a = new Animation(sprite, x1, y1, x2, y2, true, duration, true);
		a.setLooping(b);
		if(spriteMap.containsKey(animationName) == false){
			spriteMap.put(animationName, a);
		}

	}

	public void addAnimation(String animationName,ArrayList<Animation> a){
		ComplexAnimation cA = new ComplexAnimation(a);
		if(spriteMap.containsKey(animationName) == false){
			spriteMap.put(animationName, cA);
		}
	}

	public void setCurrentAnimation(String animationName){

		if(spriteMap.containsKey(animationName)){ 
			currentAnimation = spriteMap.get(animationName);
		}
		else{
			System.out.println(animationName + " NOT FOUND!!!!!!");
		}
	}
	
	public void resetAnimation(String animationName){

		if(spriteMap.containsKey(animationName)&&spriteMap.get(animationName).isStopped()){ 
			spriteMap.get(animationName).restart();
		}
		
	}
	


	@Override
	void init(GameContainer gc) {
		// TODO Auto-generated method stub

	}

	@Override
	void update(GameContainer gc, int delta) {


	}

	@Override
	void render(GameContainer gc, Graphics g) {

		if(showBorders){
			g.setColor(borderColor);
			g.drawRect(this.hitbox.getXPos(), this.hitbox.yPos, this.hitbox.getWidth(), this.hitbox.getHeight());
		}
		if(showFillRect){
			g.setColor(fillRectColor);
			g.fillRect(this.gamePosition.x, this.gamePosition.y, width, height);
		}

		if(currentAnimation != null){
			currentAnimation.draw((int)(gamePosition.x),(int)( gamePosition.y));	
		}
		
		
	}
	
	

	@Override
	void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void damage() {
		// TODO Auto-generated method stub

	}

	//inte säker på detta
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


