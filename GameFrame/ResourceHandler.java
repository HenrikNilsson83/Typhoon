import java.util.ArrayList;

public class ResourceHandler {
	private static ArrayList<Resource> rsList;
	private static boolean first = true;

	public ResourceHandler() {
		if (first) {
			rsList = new ArrayList<Resource>();
			first = false;
		}
	}

	public void add(Resource rs) {
		
		if (rs != null) {
			rsList.add(rs);
		}
	}

	public Resource get(String rsName) {
		Resource rs = null;
		for (int i = 0; i < rsList.size(); i++) {
			if (rsList.get(i).getResId().equals(rsName)) {
				rs = rsList.get(i);
				break;
			}
		}
		return rs;
	}
}
