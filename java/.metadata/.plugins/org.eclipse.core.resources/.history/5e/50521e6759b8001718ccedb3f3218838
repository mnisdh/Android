
public class LinkedList<T> {
	private Node first;
	private Node last;
	private int count;
	
	
	private void addFirst(T data){
		Node node = new Node(data);
		
		if(first != null) node.next = first;
		
		first = node;
		count++;
	}
	
	private void addLast(T data){
		if(first == null) {
			addFirst(data);
			return;
		}
		
		Node node = new Node(data);
		
		if(last == null) first.next = node;
		else last.next = node;
		
		last = node;
		count++;
	}
	
	public void add(T data){
		if(count == 0) addFirst(data);
		else addLast(data);
	}
	
	public void add(T data, int index){
		if(index == 0){
			addFirst(data);
			return;
		}
		
		Node node = new Node(data);
		if(index == count - 1){
			node.next = last;
			last = node;
			count++;
		}
		else{
			Node temp = getNode(index);
			
			node.next = temp.next;
			temp.next = node;
			count++;
		}
	}
	
	public T getData(int index){
		return getNode(index).data;
	}
	
	private Node getNode(int index){
		Node temp = first;
		for(int i = 0; i < index; i++){
			temp = temp.next;
		}
		
		return temp;
	}
	
	
	class Node{
		Node next;
		T data;
		
		public Node(T data){
			this.data = data;
		}
	}
}
