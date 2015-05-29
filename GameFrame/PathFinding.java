import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;



public class PathFinding {
	private int MAX_PATH_LENGTH = 200;
	private int START_X;
    private int START_Y;
    private int GOAL_X;
    private int GOAL_Y;
    PathMap pMap;
    
    public PathFinding(int start_x,int start_y,int goal_x,int goal_y){
    	//System.out.println("INT X: "+start_x);
    	//System.out.println("INT Y: "+start_y);
    	this.START_X = start_x;
    	this.START_Y = start_y;
    	this.GOAL_X = goal_x;
    	this.GOAL_Y = goal_y;
    	pMap = new PathMap();
    	AStarPathFinder pathFinder = new AStarPathFinder(pMap, MAX_PATH_LENGTH, false);
    	
    }
    
    public Path getPath(){
    	AStarPathFinder pathFinder = new AStarPathFinder(pMap, MAX_PATH_LENGTH, false);
    	Path path = pathFinder.findPath(null, START_X, START_Y, GOAL_X, GOAL_Y);
    	//System.out.println("PATH_START X: "+START_X);
    	//System.out.println("PATH_START Y: "+START_Y);
    	return path;
    }
}
