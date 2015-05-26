import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class ObjectPool extends SimpleGameObject {
	private static ArrayList<SimpleGameObject> friendlyObjectPool;
	private static ArrayList<SimpleGameObject> hostileObjectPool;
	private static ArrayList<SimpleGameObject> hostilePool;
	private static ArrayList<SimpleGameObject> friendlyPool;
	private static boolean first = true;
	private static SimpleGUI GUI;

	public ObjectPool(int x, int y, Vector2f pos) {
		super(x, y, pos);
		if (first) {
			friendlyObjectPool = new ArrayList<SimpleGameObject>();
			hostileObjectPool = new ArrayList<SimpleGameObject>();
			hostilePool = new ArrayList<SimpleGameObject>();
			friendlyPool = new ArrayList<SimpleGameObject>();
			first = false;
		}

	}

	public ObjectPool() {
		super(0, 0, null);
	}

	@Override
	void init(GameContainer gc) {
	}

	@Override
	void update(GameContainer gc, int delta) {
		Iterator<SimpleGameObject> it = hostilePool.iterator();
		while(it.hasNext()) {
			SimpleGameObject tempObj = it.next();
			if(tempObj.remove){
				it.remove();
			}
			else if(tempObj.getUpdate()){
				tempObj.update(gc, delta);
			}
		}
		
		it = this.friendlyPool.iterator();
		while(it.hasNext()) {
			SimpleGameObject tempObj = it.next();
			if(tempObj.remove){
				it.remove();
			}
			else{
				tempObj.update(gc, delta);
			}
		}
		
		it = this.friendlyObjectPool.iterator();
		while(it.hasNext()) {
			SimpleGameObject tempObj = it.next();
			if(tempObj.remove){
				it.remove();
			}
			else{
				tempObj.update(gc, delta);
			}
		}
		
		it = this.hostileObjectPool.iterator();
		while(it.hasNext()) {
			SimpleGameObject tempObj = it.next();
			if(tempObj.remove){
				it.remove();
			}
			else{
				tempObj.update(gc, delta);
			}
		}
	}

	@Override
	void render(GameContainer gc, Graphics g) {
		for (int i = 0; i < this.hostilePool.size(); i++) {
			this.hostilePool.get(i).render(gc, g);
		}
		for (int i = 0; i < this.friendlyObjectPool.size(); i++) {
			this.friendlyObjectPool.get(i).render(gc, g);
		}
		for(int i = 0;i<hostileObjectPool.size();i++){
			this.hostileObjectPool.get(i).render(gc, g);
		}
		for (int i = 0; i < this.friendlyPool.size(); i++) {
			this.friendlyPool.get(i).render(gc, g);
		}
		

	}

	public void addToPool(SimpleGameObject obj) {
		if (obj.faction == 1) {
			this.friendlyPool.add(obj);
		}
		if (obj.faction == 0) {
			this.friendlyObjectPool.add(obj);
		}
		if (obj.faction == -1) {
			this.hostilePool.add(obj);
		}
		if(obj.faction == -2){
			hostileObjectPool.add(obj);
		}
	}

	public ArrayList<SimpleGameObject> getList() {
		ArrayList<SimpleGameObject> retur = new ArrayList<SimpleGameObject>();
		retur.addAll(friendlyObjectPool);
		retur.addAll(hostileObjectPool);
		retur.addAll(hostilePool);
		retur.addAll(friendlyPool);
		return retur;
	}
	
	public ArrayList<SimpleGameObject> getFriendlyList() {
		ArrayList<SimpleGameObject> retur = new ArrayList<SimpleGameObject>();
		retur.addAll(friendlyPool);
		return retur;
	}
	
	public ArrayList<SimpleGameObject> getHostilelyList() {
		ArrayList<SimpleGameObject> retur = new ArrayList<SimpleGameObject>();
		retur.addAll(hostilePool);
		return retur;
	}
	
	public ArrayList<SimpleGameObject> getFriendlyObjectList() {
		ArrayList<SimpleGameObject> retur = new ArrayList<SimpleGameObject>();
		retur.addAll(friendlyObjectPool);
		return retur;
	}
	public ArrayList<SimpleGameObject> getHostileObjectList(){
		ArrayList<SimpleGameObject> retur = new ArrayList<SimpleGameObject>();
		retur.addAll(hostileObjectPool);
		return retur;
	}

	@Override
	void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void damage() {
		// TODO Auto-generated method stub
		
	}

	public void addGUI(SimpleGUI gui) {
		this.GUI = gui;
		
	}

	public void renderGUI(GameContainer gc, Graphics g) {
		this.GUI.render(gc, g);
		
	}

	public ArrayList<Light> getLights() {
		ArrayList<Light> retur = new ArrayList<Light>();
		for (int i = 0; i < this.friendlyPool.size(); i++) {
			if(this.friendlyPool.get(i).gotLight){
				retur.add(this.friendlyPool.get(i).getLight());
			}
		}
		return retur;
	}

}
