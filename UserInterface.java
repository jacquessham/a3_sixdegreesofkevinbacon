import java.util.Scanner;

public class UserInterface {
	private Database db; // Use Database class to store data
	
	public UserInterface(String path){
		db = new Database();
		long startTime = System.currentTimeMillis(); // Timing is for testing use
		db.upload(path); // Upload file
		
		// The below code is for time and size for testing, no use for implementation
		long endTime = System.currentTimeMillis();
		int duration = ((int) (endTime - startTime))/1000;
		int min = duration/60;
		int sec = duration % 60;
		System.out.println( min +" mins " +sec+" second was spent to upload");
		System.out.println("Number of movies in the database: "+db.return_db_movieSize());
		System.out.println("Number of actors in the database: "+db.return_db_actorSize());
	}
	
	private String getAnswer(){
		/* Since this program is only finding a path between two actor,
		 * exiting is not an option
		 * Therefore, the program would keep asking the user to enter a valid actor
		 */
		System.out.println("Please enter an actor: ");
		Scanner sc = new Scanner(System.in);
		String ans = sc.nextLine();
		// Obtain actor's name
		if (db.actorExistence(ans)){
			System.out.println(ans);
			return ans;
			// Find the user, return actor's name if exist
		}
		else{
			System.out.println("No such actor");
			return getAnswer();
			// If the actor's name not exist in the database, keep asking user to obtain a valid actor name
		}
	}
	
	private void printActor(int counter, String name){
		System.out.println("Actor " + counter + " name: " + name);
	} // For interact to print actor's name
	
	public void interact(){
		int counter =1;
		String ans1 = getAnswer();
		printActor(counter++,ans1);
		String ans2 = getAnswer();
		printActor(counter++,ans2);
		db.findPath(ans1, ans2);
	} // Interaction with user
}
