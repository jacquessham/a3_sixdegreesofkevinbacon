
public class Node {
	private Object data;
	private Node next;
	private Node previous;
	
	public Node(Object data){
		this.data = data;
		next = null;
		previous = null;
	}
	
	public void setData(Object data){
		this.data = data;
	}
	
	public void setNext(Node next){
		this.next = next;
	}
	
	public void setPrevious(Node previous){
		this.previous = previous;
	}
	
	public Object getData(){
		return this.data;
	}
	
	public Node getNext(){
		return this.next;
	}
	
	public Node getPrevious(){
		return this.previous;
	}
}
