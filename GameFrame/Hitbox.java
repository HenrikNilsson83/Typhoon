
public class Hitbox {
	private int width;
	private int height;
	private float xPos;
	private float yPos;
	private int xOffset;
	private int yOffset;

	public Hitbox(int w,int h,float x,float y){
		this.width = w;
		this.height = h;
		this.xPos = x;
		this.yPos = y;
		this.xOffset = 0;
		this.yOffset = 0;
	}
	
	public Hitbox(int w,int h,float x,float y,int xOff,int yOff){
		this.width = w;
		this.height = h;
		this.xPos = x;
		this.yPos = y;
		this.xOffset = xOff;
		this.yOffset = yOff;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}
	
	public float getXPos(){
		return (xPos);
	}

	public float getYPos(){
		return (yPos + this.yOffset);
	}
	
	public int getXOffset(){
		return xOffset;
	}

	public int getYOffset(){
		return yOffset;
	}
	
	public void setWidth(int w){
		width = w;
	}

	public void setHeight(int h){
		height = h;
	}
	
	public void setXPos(float x){
		xPos = x + this.xOffset;
	}

	public void setYPos(float y){
		yPos = y + this.yOffset;
	}
	
	public void setXOffset(int xOff){
		xOffset = xOff;
	}

	public void setYOffset(int yOff){
		yOffset = yOff;
	}
}
