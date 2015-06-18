import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class SnowFall extends SimpleGameObject {

	ArrayList<SnowFlake> snow;
	SimpleGameObject  target;
	float fallFreq;
	float gravity;
	float wind;
	int maxFlakes;
	int mapHeight;
	
	public SnowFall(int x,int y,int s, ObjectPool objPool){
		this.objPool = objPool;
		this.gamePosition = new Vector2f(x,y);
		//this.size = s;
		this.checkForCollision = false;
		this.checkForGravity = false;
		//ObjectPool op = new ObjectPool();
		ObjectPool op = this.objPool;
		target = op.mainChar;
		snow = new ArrayList<SnowFlake>();
		this.lastGamePosition = new Vector2f(0,0);
		this.velocityVector = new Vector2f(0,0);
		Camera cam = new Camera();
		this.mapHeight = cam.mapHeight;
		//SETUP
		gravity = 0.04f;
		gravity = (float) (gravity + Math.random()*gravity/4);
		wind = -0.06f;
		fallFreq  = 0.01f;
		maxFlakes = 100;
		
	}
	
	void init(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void update(GameContainer gc, int delta,StateBasedGame sbg) {
		if(Math.random()<fallFreq){
			if(!(snow.size()>=maxFlakes)){
				spawnFlake(gc,delta,sbg);
			}
		}
		
		Iterator<SnowFlake> sIt = snow.iterator();
		while(sIt.hasNext()){
			SnowFlake s = sIt.next();
			s.update(gc, delta,sbg);
			if(s.gamePosition.y>this.mapHeight){
				sIt.remove();
			}
		}
		
	}

	private void spawnFlake(GameContainer gc, int delta,StateBasedGame sbg) {
		int x = (int) (target.gamePosition.x-800+Math.random()*1600);
		int y = (int) (target.gamePosition.y-400+80*Math.random());
		int s = (int) (2+4*Math.random());
		snow.add(new SnowFlake(x,y,s,gravity,wind));
		
	}

	@Override
	void render(GameContainer gc, Graphics g) {
		for(int i = 0;i<snow.size();i++){
			snow.get(i).render(gc, g);
		}
		
	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		// TODO Auto-generated method stub
		
	}
	
}
