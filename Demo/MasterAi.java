import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;


public class MasterAi extends AdvancedGameObject {

	private SimpleGameObject target;
	private ArrayList<SimpleGameObject> minions;
	public float attention;
	private int lastPoolSize;
	private int shootCount = -1;
	public MasterAi(int x, int y, Vector2f pos, GameContainer gc) {
		
		super(x, y, pos, gc);
		target = null;
		this.faction = -1;
		this.data1 = 0;
		this.data3 = 0;
		this.HP=1;
		lastPoolSize=0;
		
	}
	
	@Override
	void update(GameContainer gc, int delta) {
		//SET TARGET AND SEE IF ANY MINIONS ARE DEAD
		if(target!=null){
			ObjectPool op = new ObjectPool();
			lastPoolSize = op.size;
			minions = op.getHostilelyList();
			for(int i = 0; i<this.minions.size();i++){
				this.minions.get(i).setTarget(target);
				if(this.minions.get(i).data1>0){
					attention = 1;
				}
				// MINION KILLED?
				if(this.minions.get(i).HP==0){
					attention = 0.4f;
					this.minions.get(i).HP=-1;
				}
				
			}
		}
		shootFired(delta);
		if(Math.random()<1.0/15.0){
			attention*=0.99f;
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

}
