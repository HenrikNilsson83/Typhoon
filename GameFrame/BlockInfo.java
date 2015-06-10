
public class BlockInfo{
	public boolean northObs;
	public boolean southObs;
	public boolean eastObs;
	public boolean westObs;
	public int a;
	
	public BlockInfo(boolean allDir){
		this.northObs = allDir;
		this.southObs = allDir;
		this.eastObs = allDir;
		this.westObs = allDir;
		a = (int) (Math.random()*4);
	}
	
	public boolean getBlocked(){
		return(this.northObs&&this.southObs&&this.eastObs&&this.westObs);
	}

	public boolean getBlocked(int dir) {
		return(this.northObs&&this.southObs&&this.eastObs&&this.westObs);
	}
}
