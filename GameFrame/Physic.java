import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

/**
 * Updates GameObjects positions on the map, makes sure they are in a valid position. 
 * Checks if 2 objects collides and if so tells the objects what objects the are colliding with.  
 * 
 * TODO Move the acceleration var to Level class or make changeable here 
 * TODO Maybe move tileSize to Level class to?
 * 
 * @author Team Japan
 */
public class Physic {
	private static boolean systemGravity;
	private float acceleration = 0.028f * 0.028f;
	private static int tileSize = 16;

	/**
	 * sets the world gravity to true or false.
	 * 
	 * @param gravity true/false  
	 */
	public Physic(boolean gravity) {
		systemGravity = gravity;
	}

	/**
	 * Updates GameObjects positions on the map, makes sure they are in a valid position. 
	 * Checks if 2 objects collides and if so tells the objects what objects the are colliding with.
	 * 
	 * TODO move() should be used on the nonCollisionPool too
	 * TODO move() and checkWorldCollision() should maybe be merged to remove one iteration through the collisionPool?
	 * 
	 * @param delta		time since last update
	 * @param objPool	the objectPool
	 */
	public void update(int delta, ObjectPool objPool) {
		ConcurrentHashMap<SimpleGameObject,SimpleGameObject> collisionPool = objPool.getCollisionPool();

		// Move
		move(collisionPool, delta);

		// Check and avoid collision against world
		checkWorldCollision(collisionPool, delta);

		// Check Collision between objects
		checkObjectCollision(collisionPool, delta);
	}

	/**
	 * Updates the position of all objects in the collisionPool. 
	 * The new position is calculated by multiply current x and y positions by their velocity vectors and the delta.
	 * If the systemGravity is on and the objects checkForGravity flag is set the system gravity is applied 
	 * to the object.
	 * 
	 *  The last Position is saved to and is used if the object collides with the world after movement.
	 *  
	 *  TODO the nonCollidingPool should be moved here to?
	 *  TODO move the checkWorldCollision here to avoid going through the list again?
	 * 
	 * @param collisionPool the collisionPool
	 * @param delta			time since last update
	 */
	private void move(ConcurrentHashMap<SimpleGameObject,SimpleGameObject> collisionPool, int delta) {
		SimpleGameObject sGO;
		Iterator<SimpleGameObject> itCol = collisionPool.keySet().iterator();
		while(itCol.hasNext()){
			sGO = itCol.next();
			if(systemGravity && sGO.checkForGravity && !sGO.southObs ){
				if (sGO.checkForCollision && sGO.velocityVector.y < delta) {
					sGO.velocityVector.y += delta * this.acceleration ;
				}
			}
			sGO.lastGamePosition.x = sGO.gamePosition.x;
			sGO.lastGamePosition.y = sGO.gamePosition.y;
			sGO.gamePosition.x += delta * sGO.velocityVector.x;
			sGO.gamePosition.y += delta * sGO.velocityVector.y;
		}
	}

	/**
	 * Iterates through the collisionPool to see if objects are colliding with the world map. If collision the object is moved
	 * back to a valid position. 
	 * The north, south, left and right collisions flags are set if the object is next to a obstacle in that direction.
	 * 
	 * TODO remove the static mapInfo object. It should be accessed via the object. 
	 * TODO Is the checkForCollision flag needed? is it not obvious if the object is in the CollisionPool?
	 * TODO Is it necessary to check all 5 intersections all time? Can it be improved? maybe check only when collisions is made
	 * 		and after that check if still in next to? 
	 * TODO distToWallCheck should always be 1? Is the hitboxes that check n,s,l,r correct when distToWallCheck is not 1
	 * 
	 * @param collisionPool	the collisionPool
	 * @param delta			not used now, but could be used in the future maybe?
	 */
	private void checkWorldCollision(ConcurrentHashMap<SimpleGameObject,SimpleGameObject> collisionPool, int delta) {
		Vector2f resetPosistion = new Vector2f(0,0);
		MapInfo s = new MapInfo();
		SimpleGameObject sGO;
		int distToWallCheck = 1; //how close to wall is near? maybe always 1
		Iterator<SimpleGameObject> itCol = collisionPool.keySet().iterator();
		while(itCol.hasNext()){
			sGO = itCol.next();
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

				tempBox = new Hitbox(sGO.hitbox.getWidth() - (distToWallCheck * 2), distToWallCheck * 2,sGO.hitbox.getXPos() + distToWallCheck, sGO.hitbox.getYPos() + sGO.hitbox.getHeight() - distToWallCheck);
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

	/**
	 * Iterates through the CollsionPool and checks if 2 objects intersects.
	 * If 2 objects intersects the objectCollide(SimpleGameObject) is run in both objects with the other object as parameter
	 * 
	 * @param collisionPool	The collisionPool
	 * @param delta			Not used now, maybe in future it can be used to lower the check interval?
	 */
	private void checkObjectCollision(ConcurrentHashMap<SimpleGameObject,SimpleGameObject> collisionPool, int delta) {
		SimpleGameObject sGO1, sGO2;
		SimpleGameObject sGOs[] = collisionPool.keySet().toArray(new SimpleGameObject[0]); 
		Hitbox hb1, hb2;
		Rectangle r1, r2;
		if(sGOs.length > 1 ){
			for(int i = 0; i < sGOs.length - 1; i++){
				for(int j = i + 1; j < sGOs.length; j++){
					sGO1 = sGOs[i];
					hb1 = sGO1.hitbox;
					r1 = new Rectangle(hb1.getXPos(), hb1.getYPos(), hb1.getWidth(), hb1.getHeight());
					sGO2 = sGOs[j];
					hb2 = sGO2.hitbox;
					r2 = new Rectangle(hb2.getXPos(), hb2.getYPos(), hb2.getWidth(), hb2.getHeight());

					if(r1.intersects(r2)){
						sGO1.objectCollide(sGO2);
						sGO2.objectCollide(sGO1);
					}
				}
			}
		}
	}
	
	/**
	 * Updates the Hitbox of the GameObject to its current gamePosistion
	 *  
	 * @param sGO SimpleGameObject
	 * @return the Updated SimpleGameObject
	 */
	private SimpleGameObject updateHitbox(SimpleGameObject sGO){
		sGO.hitbox.setXPos(sGO.gamePosition.x);
		sGO.hitbox.setYPos(sGO.gamePosition.y);
		return sGO;
	}

	/**
	 * 
	 * Searches near a hitbox on a map to see if the hitbox collides with map
	 * 
	 * TODO xMax, yMax is optimal? Should it not be something like (hb.width / tilesize)?
	 * 
	 * @param hb A Hitbox
	 * @param s	A MapInfo
	 * @return True if collision, False if not
	 */
	private boolean isBlocked(Hitbox hb, MapInfo s) {

		Rectangle player = new Rectangle(hb.getXPos() ,hb.getYPos() ,hb.getWidth() ,hb.getHeight() );
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

		for (int xRange = 0; xRange < xMax; xRange++) {

			for (int yRange = 0; yRange < yMax; yRange++) {
				if(s.getBlocked(xAxis + xRange,yAxis + yRange)){
					Rectangle block = new Rectangle(xAxis * tileSize + xRange * tileSize, yAxis * tileSize + yRange * tileSize, tileSize, tileSize);
					if (player.intersects(block) ) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
