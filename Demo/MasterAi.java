import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;


public class MasterAi extends AdvancedGameObject {

	private SimpleGameObject target;
	private ArrayList<SimpleGameObject> minions;
	public float spotted;
	private int lastPoolSize;
	public MasterAi(int x, int y, Vector2f pos, GameContainer gc) {
		
		super(x, y, pos, gc);
		target = null;
		this.faction = -1;
		this.data1 = 0;
		this.HP=1;
		lastPoolSize=0;
		
	}
	
	@Override
	void update(GameContainer gc, int delta) {
		
		if(target!=null){
			ObjectPool op = new ObjectPool();
			lastPoolSize = op.size;
			minions = op.getHostilelyList();
			for(int i = 0; i<this.minions.size();i++){
				this.minions.get(i).setTarget(target);
				if(this.minions.get(i).data1>0){
					spotted = 1;
				}
				// MINION KILLED?
				if(this.minions.get(i).HP==0){
					spotted = 0.4f;
					this.minions.get(i).HP=-1;
				}
				
			}
		}
		spotted*=0.999f;
		
		
		
	}
	
	public void setTarget(SimpleGameObject t){
		target = t;
	}

}
