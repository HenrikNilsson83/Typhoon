
public class BlockInfo{
	boolean northObs;
	boolean southObs;
	boolean eastObs;
	boolean westObs;
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

	public void setAll(boolean b) {
		this.northObs = b;
		this.southObs= b;
		this.eastObs= b;
		this.westObs= b;
	}
}
