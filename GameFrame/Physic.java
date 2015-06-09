import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Physic {
	private static boolean systemGravity;
	private float acceleration = 0.028f*0.028f;
	private static int tileSize = 16;

	private Vector2f resetPosistion = new Vector2f(0,0);

	public Physic(boolean gravity) {
		systemGravity = gravity;
	}

	public void update(int delta) {
		ObjectPool op = new ObjectPool();
		ArrayList<SimpleGameObject> objectList = op.getList();

		// MOVE
		move(objectList, delta);

		// CHECK STUCK
		checkStuck(objectList, delta);

		// Check Damage
		checkDamage(delta);
	}

	private void checkDamage(int delta) {

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

		//HOSTILEOBJECT->FRIENDLY
		SimpleGameObject hero;
		hostileList = op.getHostileObjectList();
		for (int i = 0; i < heroList.size(); i++) {

			hero = heroList.get(i);
			Rectangle heroRect = new Rectangle(hero.gamePosition.x,
					hero.gamePosition.getY(), hero.size, hero.size);
			for (int j = 0; j < hostileList.size(); j++) {
				host = hostileList.get(j);
				Rectangle hostRect = new Rectangle(host.gamePosition.x,
						host.gamePosition.y, host.size, host.size);
				if (hostRect.intersects(heroRect)) {
					host.damage();
					hero.damage();
				}
			}
		}
	}

	private SimpleGameObject updateHitbox(SimpleGameObject sGO){
		sGO.hitbox.setXPos(sGO.gamePosition.x);
		sGO.hitbox.setYPos(sGO.gamePosition.y);
		return sGO;
	}

	private void checkStuck(ArrayList<SimpleGameObject> objectList, int delta) {
		Scenery s = new Scenery();
		SimpleGameObject sGO;
		int distToWallCheck = 1;

		for (int i = 0; i < objectList.size(); i++) {
			sGO = objectList.get(i);
			if (sGO.checkForCollision) {
				sGO = this.updateHitbox(sGO);
				sGO.setSouthObs(false);
				sGO.setNorthObs(false);
				sGO.setLeftObs(false);
				sGO.setRightObs(false);
				
				if(isBlocked(sGO.hitbox, s)){

					resetPosistion.x = sGO.gamePosition.x;
					resetPosistion.y = sGO.gamePosition.y;
					
					sGO.hitbox.setXPos(resetPosistion.x);

					sGO.gamePosition.x = sGO.lastGamePosition.x;
					sGO = this.updateHitbox(sGO);
					while(isBlocked(sGO.hitbox, s) && sGO.velocityVector.y != 0){
						if (sGO.velocityVector.y >= 0){
							sGO.gamePosition.y = (float) Math.ceil(sGO.gamePosition.y);
							sGO.gamePosition.y -= 1;
							sGO = this.updateHitbox(sGO);
						}
						else {
							sGO.gamePosition.y = (float) Math.floor(sGO.gamePosition.y);
							sGO.gamePosition.y += 1;
							sGO = this.updateHitbox(sGO);
						}
					}

					sGO.gamePosition.x = resetPosistion.x;
					sGO = this.updateHitbox(sGO);
					while(isBlocked(sGO.hitbox,s)){
						if (sGO.velocityVector.x >= 0){
							sGO.gamePosition.x = (float) Math.ceil(sGO.gamePosition.x);
							sGO.gamePosition.x -= 1;
							sGO = this.updateHitbox(sGO);
						}
						else {
							sGO.gamePosition.x = (float) Math.floor(sGO.gamePosition.x);
							sGO.gamePosition.x += 1;
							sGO = this.updateHitbox(sGO);
						}
					}
				}
				//int w,int h,float x,float y
				Hitbox tempBox = new Hitbox( sGO.hitbox.getWidth() - (distToWallCheck * 2), distToWallCheck * 2,sGO.hitbox.getXPos() + distToWallCheck, sGO.hitbox.getYPos() - distToWallCheck);
				boolean top 	= isBlocked(tempBox, s);
				
				tempBox = new Hitbox(sGO.hitbox.getWidth() - (distToWallCheck * 2), distToWallCheck * 2,sGO.hitbox.getXPos() + distToWallCheck, sGO.hitbox.getYPos() + sGO.hitbox.height - distToWallCheck);
				boolean bottom 	= isBlocked(tempBox, s);
				
				tempBox = new Hitbox(distToWallCheck * 2, sGO.hitbox.getWidth() - (distToWallCheck * 2),sGO.hitbox.getXPos() - distToWallCheck, sGO.hitbox.getYPos() + distToWallCheck);
				boolean left = isBlocked(tempBox,s);
				
				tempBox = new Hitbox(distToWallCheck * 2, sGO.hitbox.getWidth() - (distToWallCheck * 2),sGO.hitbox.getXPos() + sGO.hitbox.getWidth() - distToWallCheck, sGO.hitbox.getYPos() + distToWallCheck);
				boolean right	= isBlocked(tempBox,s);

				if(bottom){
					sGO.setSouthObs(true);
					if(sGO.velocityVector.y > 0){
						sGO.velocityVector.y = 0;
					}
				}
				if(top){
					sGO.setNorthObs(true);
					if(sGO.velocityVector.y < 0){
						sGO.velocityVector.y = 0;
					}
				}
				if(left){
					sGO.setLeftObs(true);
					if(sGO.velocityVector.x < 0){
						sGO.velocityVector.x = 0;
					}
				}
				if(right){
					sGO.setRightObs(true);
					if(sGO.velocityVector.x > 0){
						sGO.velocityVector.x = 0;
					}
				}
			}
		}
	}

	private boolean isBlocked(float x, float y, int sizex, int sizey, Scenery s) {

		boolean isInCollision = false;
		Rectangle player = new Rectangle(x ,y ,sizex ,sizey );
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
				Rectangle block = new Rectangle(xAxis * tileSize + xRange * tileSize, yAxis * tileSize + yRange * tileSize, tileSize, tileSize);
				if (player.intersects(block) && isBlocked[xAxis + xRange][yAxis + yRange]) {
					return true;
				}
			}
		}
		return isInCollision;
	}

	private boolean isBlocked(Hitbox hb, Scenery s) {

		boolean isInCollision = false;
		Rectangle player = new Rectangle(hb.getXPos() ,hb.getYPos() ,hb.getWidth() ,hb.height );
		boolean[][] isBlocked = s.getBlocked();
		int xAxis = (int) (hb.getXPos() / tileSize);
		int yAxis = (int) (hb.getYPos() / tileSize);
		int xMax = 12;
		int yMax = 12;
		xAxis -= 3;
		yAxis -= 3;
		/*if (xAxis < 0) {
			xAxis = 0;
		}
		if (yAxis < 0) {
			yAxis = 0;
		}*/

		if (isBlocked.length <= xAxis + xMax) {
			xMax = isBlocked.length - xAxis;
		}
		if (isBlocked[0].length < yAxis + yMax) {
			yMax = isBlocked[1].length - yAxis;
		}

		for (int xRange = 0; xRange < xMax; xRange++) {

			for (int yRange = 0; yRange < yMax; yRange++) {
				Rectangle block = new Rectangle(xAxis * tileSize + xRange * tileSize, yAxis * tileSize + yRange * tileSize, tileSize, tileSize);
				if (player.intersects(block) && s.getBlocked(xAxis + xRange,yAxis + yRange)) {
					return true;
				}
			}
		}
		return isInCollision;
	}

	private void move(ArrayList<SimpleGameObject> objectList, int delta) {
		for (int i = 0; i < objectList.size(); i++) {
			if(systemGravity && objectList.get(i).checkForGravity && !objectList.get(i).southObs ){
				if (objectList.get(i).checkForCollision && objectList.get(i).velocityVector.y < delta) {
					objectList.get(i).velocityVector.y += delta * this.acceleration ;
				}
			}
			
			objectList.get(i).lastGamePosition.x = objectList.get(i).gamePosition.x;
			objectList.get(i).lastGamePosition.y = objectList.get(i).gamePosition.y;
			objectList.get(i).gamePosition.x += delta * objectList.get(i).velocityVector.x;
			objectList.get(i).gamePosition.y += delta * objectList.get(i).velocityVector.y;
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

	public float calculateJumpHeight(float f){

		return 0;
	}
}
