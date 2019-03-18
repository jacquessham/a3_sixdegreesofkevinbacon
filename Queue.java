import java.util.Iterator;

public class Queue{
	private Node head;
	private Node tail;
	private int size;
	private Iterator<Object> it;
	
	public Queue(){
		head = null;
		tail = null;
		size = 0;
	}
	
	public Iterator<Object> iterator(){
		return new ListIterator(head);
	}
	
	public Object dequeue(){
		Node temp = head;
		head = head.getNext();
		size--;
		return temp.getData();
	}
	
	public void enqueue(Object item){
		if (size == 0)
			head = tail = new Node(item);
		else{
			Node newNode = new Node(item);
			tail.setNext(newNode);
			tail = newNode;	
		}
		size++;
	}
	
	
	public boolean empty(){
		return size == 0;
	}
}
