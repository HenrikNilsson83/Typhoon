import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class MovingPlattform extends AdvancedGameObject {
	private boolean jump;
	private float speed;
	private Vector2f pos1;
	private Vector2f pos2;
	private boolean powerd;
	private int yRange;
	private int xRange;
	private int dir;
	private Rectangle zone;
	private Rectangle p;
	private boolean debugg = false;
	public MovingPlattform(int x, int y, Vector2f pos, ObjectPool objPool) {
		
		super(x, y, pos, objPool);
		this.checkForCollision =false;
		this.checkForGravity = false;
		this.showFillRect=true;
		//PLATTFORM VARS
		speed = 0.06f;
		this.dir = 2;
		powerd = true;
		xRange = 0;
		yRange = 300;
		pos1 = new Vector2f(this.gamePosition.x,this.gamePosition.y);
		pos2 = new Vector2f(this.gamePosition.x+xRange,this.gamePosition.y-yRange);


	}

	@Override
	void update(GameContainer gc, int delta,StateBasedGame sbg) {
		
		//ObjectPool op = new ObjectPool();
		// GET THE PLAYER 
		SimpleGameObject player = this.objPool.mainChar;
		p = new Rectangle(player.gamePosition.x,player.gamePosition.y+62,player.width,7);
		zone = new Rectangle(this.gamePosition.x,this.gamePosition.y,this.width,3);
		if(player.velocityVector.y>0&&p.intersects(zone)){
			player.velocityVector.y = this.velocityVector.y;
			player.gamePosition.y = this.gamePosition.y-player.height;
			//player.jump = false;
		}
		else{
		}
		if(dir==2 &&player.velocityVector.y+this.speed>0&&p.intersects(zone)){
			player.velocityVector.y=this.velocityVector.y;
			//player.jump = false;
		}
		
		
		// GOING UP
		if(dir == 2){
			if(this.gamePosition.y>pos2.y){
				this.velocityVector.y=-speed;
			}
			else{
				dir = 3;
			}
			
		}
		// GOING DOWN
		else if(dir == 3){
			
			if(this.gamePosition.y<pos1.y){
				this.velocityVector.y=speed;
			}
			else{
				dir = 2;
			}
			
		}
	}
	
	@Override
	void render(GameContainer gc, Graphics g) {

		if(showBorders){
			g.setColor(borderColor);
			g.drawRect(this.gamePosition.x, this.gamePosition.y, width, height);
		}
		if(showFillRect){
			g.setColor(fillRectColor);
			g.fillRect(this.gamePosition.x, this.gamePosition.y, width, height);
		}

		if(currentAnimation != null){
			currentAnimation.draw(gamePosition.x, gamePosition.y);
		}
		
		if(p!=null&&debugg){
			g.setColor(Color.cyan);
			g.draw(p);
			g.setColor(Color.orange);
			g.draw(zone);
		}
		
		
	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void init(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}



}
