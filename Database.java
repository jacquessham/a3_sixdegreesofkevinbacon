import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
/* Database class is the place to store actor's name and movie info
 * There are two hashmap to store movies and actors, in movie_list and actor_list
 * movie_list stores by movie name as key, and a Movie class as value
 * Movie would store movie name and casting list
 * actor_list is intended to store actor's name and to find actor's name quick, 
 * key and value are the actor's name but there is no use for value
 * graph in Graph class is the networks of actor to find the paths between actors
 * 
 * PS:
 * I sometimes call movie a film. 
 * In this project, movies and films mean the same thing.
 */


public class Database {
	private HashMap<String,Movie> movie_list;
	private HashMap<String,String> actor_list;
	private Graph graph;
	// See comment at the top
	
	public Database(){
		movie_list = new HashMap();
		actor_list = new HashMap();
		graph = new Graph();
	} // Constructor
	
	
	public void addMovie(Movie movie){
		movie_list.put(movie.getName(), movie);
	} // Add movie into movie_list
	
	public void upload(String path){ // For upload the csv file
		JSONParser parser = new JSONParser(); 
		// Use Json simple, please add that in the library
		BufferedReader br;
		
		
		try{
		    FileReader file = new FileReader(path);
		    System.out.println("Your path is valid!");
			br = new BufferedReader(file);
			// Try to get the file provide by the command line
		}
		catch(FileNotFoundException e){ 
			// Here is the exception when the path in command line is not working
			try{ // First ask user to enter the path again
				System.out.println("Your path is not invalid, please enter your path again.");
				System.out.println("Be sure to include /User/(your machine name)");
				System.out.print("Enter here: ");
				Scanner sc = new Scanner(System.in);
				path = sc.nextLine();
				System.out.println();
				br = new BufferedReader(new FileReader(path));
			} 
			/* If the path user provided in the second time not working,
			 * Use the file in src instead
			 */
			
			catch (FileNotFoundException e2){
				System.out.println("File not found again");

				try{
					System.out.println("Your path is not invalid, use default data");
					br = new BufferedReader(new FileReader("src/tmdb_5000_credits.csv"));
				}
				catch (FileNotFoundException e3){ // If src does not exist, then quit the program
				System.out.println("File not found, please try again");
				return;
				}
			}
			
		}
		
		
		try{
			String line ="";
			int counter = 0; // For keep tracking the row to avoid header
			
			System.out.println("Loading..."); // Notify user that program is running
			while ((line = br.readLine()) != null){ // Read rows
				/* The csv file consists of 4 column, 
				 * due to the csv file is design for python to run with JSON,
				 * my program will first separate the column into 3 on an array called cells:
				 *  cells[0] consists Movie ID and Movie name
				 *  cells[1] consists list of cast
				 *  cells[2] consists list of crews
				 *  
				 * Then I separate cells[0] into movie_info with array length of 2
				 *   movie_info[0] is Movie ID
				 *   movie_info[1] is Movie name
				 *  
				 * Reason of doing that is the structure of comma is hard to separate and
				 * this is the easiest way for me to separate
				 * 
				 * However, there are cases columns are not accurately separate into length of 3
				 * there are if statements to fix those cases
				 * 
				 */
				
				String[] cells;
				String[] movie_info;
				Movie movie = null;
				
				if(counter!=0){ // First line is header, skip header
					if (line.contains(",\"\", \"\"")){
						line = line.replace(",\"\", \"\"", "\"\", \"\"");
					}
					/* Some actor name JSON format was corrupted, "name": "John Dickerson,", 
					 * JSON simple cannot read comma within the double quote,
					 * this if statement is to clean up the commas
					 * Therefore not able to implement, clean up
					 * There are two movies involve this issues
					 */
					
					if (line.contains(",\""))
						cells = line.split(",\"");
					/* To check whether the film at least have the
					 * list of cast or crew
					 * If either one is missing, will handle later
					 */
					else{
						/* There are films without neither cast nor crew information
						 * (Both cast list and crew list are empty at the same time)
						 * There is nothing else we can do but to skip that row
						 */
						continue;
						
						
					}
	
					
					if(!cells[1].equals("[]"))
						/*To check whether the cast list is empty
						 * Here is the case where cast list is not empty but crew list is empty
						 */
						 
						cells[1] = cells[1].replaceAll("\"\"","\""); 
					/* Replace double quote with single quote for parsing if cast list is not empty
					 * because those movie with crew list empty miss the replacement line above
					 */
					
					movie_info = cells[0].split(",",2);
					//Split movie id and movie name to build Movie object
					
					if(movie_info.length != 2){
						counter++;
						/* Here to catch all the films that did not split cells array into length of 3
						 * Then to fix the format to upload the data
						 */
						
						if(cells.length == 4){
							/* For the film name with comma, it split into 4 cells instead of 3,
							 * Clean up the data here
							 * 
							 * Observation found:
							 * cells[0] is the movie id
							 * cells[1] is the movie name
							 * cells[2] is the cast list
							 * cells[3] is the crew list
							 * 
							 * With this pattern, build movie class with cells[0] and cells[1]
							 * make cells[1] become cast list so that it can run later lines
							 */
							movie_info = new String[2];
							movie_info[0] = cells[0];
							movie_info[1] = cells[1].substring(0, cells[1].length()-1);
							cells[0] = null;
							cells[1] =cells[2];
							cells[1] = cells[1].replaceAll("\"\"","\"");
							/* Replace double quote with single quote for parsing
							 * because those movie with crew list empty miss the replacement line above
							 */
							cells[2] = cells[3];
							cells[3] = null;
							
						}
						else {
							// There are two films missing information on crews
							// Clean up data for those films
							movie_info = new String[2];
							movie_info[0] = cells[0];
							movie_info[1] = cells[1].substring(0, cells[1].length()-1);
							cells[0] = null;
							cells[1] =cells[2];
							cells[1] = cells[1].replaceAll("\"\"","\"");
							cells[2] = null;
							
						}
					}
					
					
					
					
					if (movie == null)
						movie = new Movie(Integer.parseInt(movie_info[0]),movie_info[1]);
					// Create Movie object and store movie id and movie name if data did not corrupted
					Object obj = null; // Declare object for JSON
					
					
					try{
					obj = parser.parse(cells[1]); // Parse casts into JSON 
					}
					catch (ParseException e){
						e.printStackTrace();
						counter++;
						continue;
					} // Throw exception if it is not parse-able
					catch (ArrayIndexOutOfBoundsException e){
						System.out.println("Array out of Bound at row: " +counter);
						counter++;
						continue;
					} 
					
					JSONArray casts = (JSONArray) obj;
					Iterator<JSONObject> it = casts.iterator(); 
					// Create iterator for the array list of actors
					
					while (it.hasNext()){
						JSONObject cast_arr = (JSONObject) it.next();
						String name = (String) cast_arr.get("name");
						// Use JSON to get the actor name
						if(name != null){// Ensure not adding null
							movie.addCast(name);  // Add actor's name in movie's cast list
							actor_list.put(name, name); // Add actor's name into actor list
						}
						// Parse JSON array into the array in Movie object
					}  // End inner while
					movie.sortCasts(); // Sort actor list for binary search
					String[] movie_cast = movie.getCasts();
					for (int i=0; i<movie_cast.length; i++){
						for(int j=0; j<movie_cast.length;j++){
							if (!movie_cast[i].equals(movie_cast[j]))
								graph.addConnection(movie_cast[i], movie_cast[j]);
						} // End inner for
					}// End outer for
					// Here to add actors into the graph
					
					
					addMovie(movie); 
					// Add to movie_list in this class after the data in Movie object is clean and ready
				
				}// End if
				
				counter++;
			} // End while
			
		}// End function
		
		catch(IOException e){
			System.out.println("File has problem");
			return;
		}
		
		catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
			return;
		}
		catch (Exception e){
			e.printStackTrace();
			return;
		}
		
	}
	
	public boolean actorExistence(String name){
		if (actor_list.containsKey(name))
			return true;
		else
			return false;	
	 } // To verify whether actor exist for ui to verify 
	
	public int return_db_movieSize(){
		return movie_list.size();
	} // Return movie list size
	
	public int return_db_actorSize(){
		return actor_list.size();
	} // Return actor list size
	
	
	public void findPath(String org, String tar){
		graph.findPath(org, tar);
	} // For ui to find a path between two actors
}
