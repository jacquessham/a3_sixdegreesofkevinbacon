
public class Movie {
	int movie_id;
	String movie_name;
	String[] casts;
	int casts_size;
	
	public Movie(int id, String name){
		this.movie_id = id;
		this.movie_name = name;
		casts = new String[10];
		casts_size = 0;
	}
	
	public int getID(){
		return movie_id;
	}
	
	public String getName(){
		return movie_name;
	}
	
	public String[] getCasts(){
		return casts;
	}
	
	public int getCastsSize(){
		return casts_size;
	}
	
	private void grow_casts(){
		String [] temp = casts;
		casts = new String [casts.length*2];
		System.arraycopy(temp, 0, casts, 0, temp.length);
	}
	
	private void adjust_arr_size(){
		String[] temp = casts;
		casts = new String [casts_size];
		for (int i=0; i<casts.length; i++)
			casts[i] = temp[i];
	}
	
	public void addCast(String name){
		if (casts_size == casts.length)
			grow_casts();
		casts[casts_size++] = name;
	}
	
	
	
	public boolean isActor(String name){
		int position = MergeSort.search(casts, name);
		if (position == -1)
			return false;
		return true;
		
	}
	
	public void printCastList(){
		for (int i=0; i<casts.length; i++)
			System.out.print(i+1 + ". " + casts[i] +" ");
		System.out.println();
	}
	
	public boolean sameCast(String name1, String name2){
		if (isActor(name1) && isActor(name2))
			return true;
		return false;
	}
	

	
	public void sortCasts(){
		adjust_arr_size();
		MergeSort.sort(casts);
	}
}
