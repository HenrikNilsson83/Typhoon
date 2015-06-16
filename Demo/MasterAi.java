import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class MasterAi extends AdvancedGameObject {

	private SimpleGameObject target;
	private ArrayList<SimpleGameObject> minions;
	public float attention;
	private int lastPoolSize;
	private int shootCount = -1;
	private int rTimer = 5000;
	private int rMax = 5000;
	private Vector2f goToPos;
	private GameContainer gc;
	private int dropTimer = 10000;
	private int dropTimeMax = 10000;
	public MasterAi(int x, int y, Vector2f pos, GameContainer gc, ObjectPool objPool) {
		
		super(x, y, pos, gc, objPool);
		target = null;
		this.gc = gc;
		this.faction = -1;
		this.data1 = 0;
		this.data3 = 0;
		this.HP=1;
		lastPoolSize=0;
		
	}
	
	//THIS WILL BE FIXED LATER WHEN STUFF WORKS AGAIN!
	@Override
	void update(GameContainer gc, int delta,StateBasedGame sbg) {
		//SET TARGET AND SEE IF ANY MINIONS ARE DEAD
		
		/*
		if(target!=null){
			
			//ObjectPool op = new ObjectPool();
			lastPoolSize = op.size;
			minions = op.getHostilelyList();
			for(int i = 0; i<this.minions.size();i++){
				this.minions.get(i).setTarget(target);
				if(this.minions.get(i).data1>0){
					attention = 1;
					if(this.dropTimer==dropTimeMax){
						dropTimer--;
					}
					this.minions.get(i).data1=0;
				}
				// MINION KILLED?
				if(this.minions.get(i).HP==0){
					attention = 0.4f;
					this.minions.get(i).HP=-1;
					goToPos = this.minions.get(i).gamePosition;
					rTimer-=delta;
				}
				
			}
		}
		shootFired(delta);
		if(rTimer!=rMax){
			rTimer-=delta;
			if(rTimer<0){
				//sendCopTo(goToPos);
				rTimer=rMax;
			}
		}
		
		if(dropTimer!=dropTimeMax){
			dropTimer-=delta;
			if(dropTimer<0){
				//sendDropToPlayer();
				dropTimer=dropTimeMax;
			}
		}
		
		if(Math.random()<1.0/15.0){
			attention*=0.99f;
		}
		*/
	}
	/*
	private void sendDropToPlayer() {
		ObjectPool op = new ObjectPool();
		ArrayList<SimpleGameObject> minion = op.getHostilelyList();
		SimpleGameObject hero = op.getFriendlyList().get(0);
		Vector2f v = new Vector2f(hero.gamePosition.x,hero.gamePosition.y);
		v.y = 10;
		Scenery scen = new Scenery();
		if(!scen.getBlocked((int)(v.x), (int)(v.y))){
			int index = minion.size();
			index = (int) (Math.random()*index);
			minion.get(index).gamePosition =v;
		}
		
	}

	private void sendCopTo(Vector2f goToPos2) {
		Scenery scen = new Scenery();
		ObjectPool op = new ObjectPool();
		RobotCop rc = new RobotCop(64,64,goToPos,gc);
		goToPos = new Vector2f(goToPos.x,goToPos.y);
		if(!scen.getBlocked((int)(goToPos.x), (int)(goToPos.y))){
			op.addToPool(rc);
		}
		Vector2f v = new Vector2f(goToPos.x+64,goToPos.y-16);
		rc = new RobotCop(64,64,v,gc);
		if(!scen.getBlocked((int)(v.x), (int)(v.y))){
			op.addToPool(rc);
		}
		
	}

	private void shootFired(int delta) {
		
		if(target!=null){
			if(target.resource1<this.shootCount){
				ObjectPool op = new ObjectPool();
				lastPoolSize = op.size;
				this.attention+=0.2f;
				minions = op.getHostilelyList();
				for(int i = 0; i<this.minions.size();i++){
					this.minions.get(i).data3=1;
					
				}
			}
		}
		
		this.shootCount = target.resource1;
		
	}

	public void setTarget(SimpleGameObject t){
		target = t;
	}
	 */

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		// TODO Auto-generated method stub
		
	}
}
