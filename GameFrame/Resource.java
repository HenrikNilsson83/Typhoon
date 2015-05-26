import java.io.InputStream;


public abstract class Resource {
	protected String id, path;
	public Resource(String id, String path) {
		this.id = id;
		this.path = path;
		
	}
	
	public String getResId(){
		return id;
	}
	
	public static InputStream load(String path){
	    InputStream input = Resource.class.getResourceAsStream(path);
	        input = Resource.class.getResourceAsStream("/"+path);
	    return input;
	}
	
	// ABSTRACT RESOURCES
	abstract void initRes();
	
	
}
