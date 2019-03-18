import java.util.Iterator;

public class ListIterator implements Iterator<Object> {
	private Node current;
	
	public ListIterator(Node head){
		current = head;
	}
	
	public boolean hasNext(){
		return current.getNext() != null;
	}
	
	public Object next(){
		if(hasNext()){
			current = current.getNext();
			return current.getData();
		}
		else
			return null;
	}
}
