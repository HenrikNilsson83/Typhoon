import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class PathMap implements TileBasedMap {
	private boolean[][] MAP;
	private int HEIGHT;
	private int WIDTH;
	public PathMap(){
		Scenery scen = new Scenery();
		MAP = scen.getBlocked();
		HEIGHT = scen.HEIGHT;
		WIDTH = scen.WIDTH;
	}

	@Override
	public boolean blocked(PathFindingContext arg0, int x, int y) {

		return MAP[x][y];//&&MAP[x+1][y]&&MAP[x+1][y+1]&&MAP[x][y+1]&&MAP[x-1][y]&&MAP[x-1][y-1]&&MAP[x][y-1];
	}

	/*@Override
	public float getCost(PathFindingContext arg0, int x, int y) {
		if(x+3<HEIGHT&&x-2>0&&y+3<HEIGHT&&y-2>0){
			if(MAP[x-1][y]){
				return 1000;
			}
			if(MAP[x+1][y]){
				return 1000;
			}
			if(MAP[x][y+1]){
				return 1000;
			}
			if(MAP[x][y-1]){
				return 1000;
			}
			if(MAP[x-1][y-1]){
				return 1000;
			}
			if(MAP[x+1][y-1]){
				return 1000;
			}
			if(MAP[x+1][y+1]){
				return 1000;
			}
			if(MAP[x-1][y+1]){
				return 1000;
			}


			//X2
			if(MAP[x-2][y]){
				return 1000;
			}
			if(MAP[x+2][y]){
				return 1000;
			}
			if(MAP[x][y+2]){
				return 1000;
			}
			if(MAP[x][y-2]){
				return 1000;
			}
			if(MAP[x-2][y-2]){
				return 1000;
			}
			if(MAP[x+2][y-2]){
				return 1000;
			}
			if(MAP[x+2][y+2]){
				return 1000;
			}
			if(MAP[x-2][y+2]){
				return 1000;
			}
		}

		return 1.0f;
	}*/

	@Override
	public int getHeightInTiles() {
		return HEIGHT;
	}

	@Override
	public int getWidthInTiles() {
		// TODO Auto-generated method stub
		return WIDTH;
	}

	@Override
	public void pathFinderVisited(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getCost(PathFindingContext arg0, int x, int y) {
		

		// DOWN
		
		if((y+2<this.HEIGHT)&&this.MAP[x][y+2]){
			return 0;
		}
		/*if(this.MAP[x+-1][y]||this.MAP[x+1][y]){
			return 0;
		}*/
		else{
			return 2;
		}

	}

}
