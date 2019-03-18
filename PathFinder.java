import java.util.ArrayList;

public class PathFinder {
	private String currentPos; // The current vertex
	private ArrayList<String> path; // The path stored in order between origin and destination
	
	public PathFinder(String currentPos){
		this.currentPos = currentPos;
		path = new ArrayList<String>();
	}
	
	public PathFinder(String currentPos, ArrayList<String> path){
		this.currentPos = currentPos;
		this.path = path;
	}
	
	public void addStep(String step){
		path.add(step);
	}
	
	public String getCurrentPos(){
		return currentPos;
	}
	
	public ArrayList<String> getPath(){
		return path;
	}
	
	public void move(String newPos){
		path.add(currentPos);
		currentPos = newPos;
	}
	
	public String toString(){
		String str = "";
		for (int i=0; i<path.size();i++){
			str += path.get(i);
			str += " ";
		}
		return str;
	}
	

}
