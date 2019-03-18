
public class Main {

	public static void main(String[] args) {
		UserInterface ui ;
		if(args.length != 0) // To check if the user enter anything on the command line
			ui = new UserInterface(args[0]);
		else
			ui = new UserInterface(""); 
		/* Declare nothing to for path, 
		 * but the program later will ask user to enter the path again in data base class
		 */
		ui.interact();

	}

}
