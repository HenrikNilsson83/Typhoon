import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class ObjectPool {

	private ConcurrentHashMap<SimpleGameObject,SimpleGameObject> collisionPool;
	private ConcurrentHashMap<SimpleGameObject,SimpleGameObject> nonCollisionPool;
	public SimpleGameObject mainChar;
	
	public ObjectPool(){
		collisionPool = new ConcurrentHashMap<SimpleGameObject,SimpleGameObject>();
		nonCollisionPool = new ConcurrentHashMap<SimpleGameObject,SimpleGameObject>();
		
	}
	
	public void addToCollisionPool(SimpleGameObject sGO){
		collisionPool.put(sGO,sGO);
	}
	
	public void addToNonCollisionPool(SimpleGameObject sGO){
		nonCollisionPool.put(sGO,sGO);
	}

	
	public ConcurrentHashMap<SimpleGameObject,SimpleGameObject> getCollisionPool(){
		return collisionPool;


	
		



	}
	
	public ConcurrentHashMap<SimpleGameObject,SimpleGameObject> getNonCollisionPool(){
		return nonCollisionPool;
	}
	
	void render(GameContainer gc, Graphics g) {
		Iterator<SimpleGameObject> itCol = collisionPool.keySet().iterator();
		Iterator<SimpleGameObject> itNonCol = nonCollisionPool.keySet().iterator();
		while(itCol.hasNext()){
			itCol.next().render(gc, g);
		}
		while(itNonCol.hasNext()){
			itNonCol.next().render(gc, g);
		}
	}
	
	void update(GameContainer gc, int delta) {
		Iterator<SimpleGameObject> itCol = collisionPool.keySet().iterator();
		Iterator<SimpleGameObject> itNonCol = nonCollisionPool.keySet().iterator();
		while(itCol.hasNext()){
			SimpleGameObject sgo = itCol.next();
			if(!sgo.remove){
				sgo.update(gc, delta);
			}
			else{
				itCol.remove();
			}
		}
		while(itNonCol.hasNext()){
			SimpleGameObject sgo = itNonCol.next();
			if(!sgo.remove){
				sgo.update(gc, delta);
			}
			else{
				itNonCol.remove();
			}
		}
	}
}
