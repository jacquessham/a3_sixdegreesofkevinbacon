import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
	private HashMap<String,ArrayList<String>> actors;
	// Use Hashmap to store actor as key, a disjoint set for its connection as value
	
	public Graph(){
		actors = new HashMap();
	} // Constructor
	
	public void addActor(String name){
		if (!actors.containsKey(name)){
			actors.put(name, new ArrayList<String>());
		}
	} // To add actor
	// Using hashmap we do not have to worry about of duplicate actors
	
	public void addConnection(String actorName, String friendName){
		// Function to add connections between two actors
		ArrayList<String> actorConnection;
		ArrayList<String> friendConnection;
		// Declare the lists from both actors
		
		if (!actors.containsKey(actorName)){
			actorConnection = new ArrayList<String>();
			actors.put(actorName, actorConnection);
		} // Create actor if the actor has not been created before adding connections
		else
			actorConnection = actors.get(actorName);
		// Obtain the arraylist of actor's connection
		
		if (!actors.containsKey(friendName)){
			friendConnection = new ArrayList<String>();
			actors.put(friendName, friendConnection);
		} // Repeat the same for second actor
		else
			friendConnection = actors.get(friendName);
		// Obtain the arraylist of friend's Connection
		
		if (!actorConnection.contains(friendName))
			actorConnection.add(friendName);
		// Only add if the second actor has not been added into first actor's connection list
		
		if (!friendConnection.contains(actorName))
			friendConnection.add(actorName);
		
	}
	
	public void printConnection(String name){
		System.out.print(name+": ");
		ArrayList<String> connection = actors.get(name);
		for(int i=0; i<connection.size();i++){
			System.out.print(connection.get(i)+" ");
		}
		System.out.println();
	} // Printing the list for testing
	

	//---------------------------------------------------------------
	// The Code below is for finding path
	//---------------------------------------------------------------
	
	public void findPath(String org, String tar){
		Queue q = new Queue(); // Create a queue
		HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
		// Create a hashmap to store whether the actor's edges has checked
		PathFinder curr = new PathFinder(org);
		// PathFinder is to keep track of the edges between origin and destination
		
		q.enqueue(curr); // Put the origin into the queue
		while (!curr.getCurrentPos().equals(tar) && !q.empty()){
			// While loop loops when the destination is not found or the queue is empty (path not found)
			curr = (PathFinder) q.dequeue();
			// Dequeue the first actor in queue
			ArrayList<String> curr_connection = actors.get(curr.getCurrentPos());
			// Obtain the next actor
			ArrayList<String> curr_path = curr.getPath();
			// To update the path 
			for (int i=0; i<curr_connection.size(); i++){
				String friend = curr_connection.get(i);
				if (!visited.containsKey(friend)){
					visited.put(friend, true);
					
					ArrayList<String> friend_path = new ArrayList<String>(curr_path);
					friend_path.add(friend);
					
					PathFinder nextStep = new PathFinder(friend, friend_path);
					q.enqueue(nextStep);
					
				} // Here to enqueue the next actors in the list
			}
		}
		String curr_name = curr.getCurrentPos();
		ArrayList<String> path = curr.getPath();
		
		if (curr_name.equals(tar)){
			System.out.print("The path between " + org +" and "+ tar +": "+org);
			for (int i=0; i<path.size();i++){
				System.out.print(" --> " +path.get(i));
			}
			System.out.println();
		} // Print the path if the path has found
		else
			System.out.println("The path does not exist!");
	} // If the path is not found, notify user
	
}
