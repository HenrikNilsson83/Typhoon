import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Physic {
	private static boolean systemGravity;
	private float accekeration = 0.03f;
	private static int tileSize = 16;

	public Physic(boolean gravity) {
		systemGravity = gravity;
	}

	public void update(int delta) {
		ObjectPool op = new ObjectPool();
		ArrayList<SimpleGameObject> objectList = op.getList();
		
		// CHECK FOR COLLISION
		checkForCollision(objectList, delta);
		
		// MOVE
		move(objectList, delta);
		
		// UPDATE GRAVITY
		if (systemGravity) {
			gravity(objectList, delta);
		}
		
		// CHECK STUCK
		checkStuck(objectList, delta);
		
		// Check Damage
		checkDamage(delta);
	}

	private void checkDamage(int delta) {
		hj
		ObjectPool op = new ObjectPool();
		ArrayList<SimpleGameObject> heroList = op.getFriendlyList();
		ArrayList<SimpleGameObject> hostileList = op.getHostilelyList();
		ArrayList<SimpleGameObject> neutralList = op.getFriendlyObjectList();
		SimpleGameObject neu;
		SimpleGameObject host;
		for (int i = 0; i < neutralList.size(); i++) {
			neu = neutralList.get(i);
			Rectangle neuRect = new Rectangle(neu.gamePosition.x,
					neu.gamePosition.getY(), neu.size, neu.size);
			for (int j = 0; j < hostileList.size(); j++) {
				host = hostileList.get(j);
				Rectangle hostRect = new Rectangle(host.gamePosition.x,
						host.gamePosition.y, host.size, host.size);
				if (hostRect.intersects(neuRect)) {
					host.damage();
					neu.damage();
				}
			}
		}

	}

	private void checkStuck(ArrayList<SimpleGameObject> objectList, int delta) {
		Scenery s = new Scenery();
		//boolean[][] isBlocked = s.getBlocked();
		for (int i = 0; i < objectList.size(); i++) {
			if (!objectList.get(i).checkForCollision) {

			} else {
				float x = objectList.get(i).gamePosition.x;
				float y = objectList.get(i).gamePosition.y;
				int size = objectList.get(i).getSize();
				if (isBlocked(x, y, size, s)) {
					objectList.get(i).reset();
					objectList.get(i).stuck = true;
				}
				else{
					objectList.get(i).stuck = false;
				}
			}
		}
	}

	private void checkForCollision(ArrayList<SimpleGameObject> objectList, int delta) {

		Scenery s = new Scenery();
		//boolean[][] isBlocked = s.getBlocked();
		for (int i = 0; i < objectList.size(); i++) {
			
			float x = objectList.get(i).gamePosition.x;
			float y = objectList.get(i).gamePosition.y;
			int size = objectList.get(i).getSize();
			// CHECK DOWN
			if (!objectList.get(i).checkForCollision) {

			} else {
				if (isBlocked(x, y + 2.0f, size, s)
						&& objectList.get(i).velocityVector.y >= 0) {
					objectList.get(i).velocityVector.y = 0;
					//objectList.get(i).setJump(false);
					objectList.get(i).setSouthObs(true);
				} else {
					objectList.get(i).setSouthObs(false);
				}
				// CHECK UP
				if (isBlocked(x, y - 5.0f, size, s)
						&& objectList.get(i).velocityVector.y <= 0) {
					objectList.get(i).velocityVector.y = 0;
					objectList.get(i).setNorthObs(true);

				} else {
					objectList.get(i).setNorthObs(false);
				}
				// CHECK LEFT
				if (isBlocked(x - 5f, y, size, s) && objectList.get(i).velocityVector.x < 0) {
					objectList.get(i).velocityVector.x = 0;
					objectList.get(i).setLeftObs(true);
				} else {
					objectList.get(i).setLeftObs(false);
				}
				// CHECK RIGHT
				if (isBlocked(x + 5f, y, size, s) && objectList.get(i).velocityVector.x > 0) {
					objectList.get(i).velocityVector.x = 0;
					objectList.get(i).setRightObs(true);
				} else {
					objectList.get(i).setRightObs(false);
				}
			}
		}
	}

	private boolean isBlocked(float x, float y, int SIZE, Scenery s) {

		boolean isInCollision = false;
		Rectangle player = new Rectangle(x+SIZE/4 ,y+SIZE/4, SIZE/2, SIZE/1.5f);
		boolean[][] isBlocked = s.getBlocked();
		int xAxis = (int) (x / tileSize);
		int yAxis = (int) (y / tileSize);
		int xMax = 12;
		int yMax = 12;
		xAxis -= 3;
		yAxis -= 3;
		if (xAxis < 0) {
			xAxis = 0;
		}
		if (yAxis < 0) {
			yAxis = 0;
		}

		if (isBlocked.length <= xAxis + xMax) {
			xMax = isBlocked.length - xAxis;
		}
		if (isBlocked[0].length < yAxis + yMax) {
			yMax = isBlocked[1].length - yAxis;
		}

		for (int xRange = 0; xRange < xMax; xRange++) {

			for (int yRange = 0; yRange < yMax; yRange++) {
				Rectangle block = new Rectangle(xAxis * tileSize + xRange
						* tileSize, yAxis * tileSize + yRange * tileSize,
						tileSize, tileSize);
				if (player.intersects(block)
						&& isBlocked[xAxis + xRange][yAxis + yRange]) {
					return true;
				}
			}
		}
		return isInCollision;
	}

	private void gravity(ArrayList<SimpleGameObject> objectList, int delta) {

		for (int i = 0; i < objectList.size(); i++) {
			if(objectList.get(i).checkForGravity){
				if (objectList.get(i).checkForCollision && objectList.get(i).velocityVector.y < delta) {
					objectList.get(i).velocityVector.y += delta * this.accekeration * this.accekeration;
				}
			}
		}

	}

	private void move(ArrayList<SimpleGameObject> objectList, int delta) {
		for (int i = 0; i < objectList.size(); i++) {
			objectList.get(i).gamePosition.x += delta
					* objectList.get(i).velocityVector.x;
			objectList.get(i).gamePosition.y += delta
					* objectList.get(i).velocityVector.y;
		}

	}

	public void render(GameContainer gc, Graphics g) {
		boolean[][] isBlocked;
		Scenery s = new Scenery();
		isBlocked = s.getBlocked();
		for(int x = 0;x<isBlocked.length;x++){
			for(int y = 0;y<isBlocked.length;y++){
				if(isBlocked[x][y]){
					g.drawRect(x*tileSize, y*tileSize, tileSize,tileSize);
				}
			}
		}
	}
}
