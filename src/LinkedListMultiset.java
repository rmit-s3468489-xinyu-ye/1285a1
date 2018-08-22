import java.io.PrintStream;

/**
 * 
 * @author Xinyu YE s3468489, Yifan ZHANG s3615625
 *
 * @param <T>
 */

public class LinkedListMultiset<T> extends Multiset<T>
{
	private Node head;
	
	public LinkedListMultiset() 
	{
		head = null;
	} // end of LinkedListMultiset()
	
	public void add(T item) 
	{
		//if the head reference
		//points to null
		if(head == null) 
		{//instantiate a new node with
			//the parameter item
			//point the head reference
			//to the newly constructed node
			head = new Node(item);
			//immediately terminate this method
			return;
		}
		
		//if head's item matches the parameter
		//passed into the add(T item) method
		if(head.itemEquals(item)) 
		{//increase the count of head by 1
			head.increCount();
			//immediately terminate this method
			return;
		}
		
		//point the reference current to head
		//i.e., start from head 
		Node current = head;
		
		//while the current node has its next node
		//execute this while loop
		while(current.hasNext()) 
		{
			//if the item of the current node's next one
			//matches the parameter item
			if(current.getNext().itemEquals(item)) 
			{//increase the count of the current node's
				//next node by 1
				current.getNext().increCount();
				//immediately terminate this method
				return;
			}
			//move the pointer from current to its next node
			current = current.getNext();
		}
			//After iterated through the whole linkedList
			//the target to be matched with the parameter item
			//is not found
			//so instantiate a new node with the parameter
			Node insertion = new Node(item);
			//then add the target item 
			//to the end of the linkedList
			current.setNext(insertion);
		
	} // end of add()
	
	public int search(T item) 
	{//start from head
		Node current = head;

		//while the current node is not null
		//execute this while loop
		while(current != null) 
		{//if the item of the current node matches
			//the parameter item
			if(current.itemEquals(item)) 
			{//return the count of the current node
 				return current.getCount();
			}
			//move the pointer from current to its next node
			current = current.getNext();
		}
		//if the linkedlist has been iterated through
		//but nothing matches the parameter were found
		//this indicates that there is not such a node containing
		//the same item as the parameter
		//hence return 0
		return 0;
	} // end of search()
		
	public void removeOne(T item) 
	{//check whether head is null
		if(head == null) 
			//if so, terminate this method immediately
			return;
		//if head's item matches the parameter
		if(head.itemEquals(item)) 
		{//if head has more than one instances
			if(head.getCount() > 1)
				//remove one instance of it
				head.setCount(head.getCount()-1);
			//if head only has one instance
			else 
				//point the head reference to head's next node
				//i.e., remove the only one instance of head
				head = head.getNext();
			//terminate this method immediately
			return;
		}
		
		//start from head
		Node current = head;

		//while the current node has its next node
		//execute this while loop
		while(current.hasNext()) 
		{
			//if the item of the current node's next one matches
			//the parameter item
			if(current.getNext().itemEquals(item)) 
			{//if the next node of the current one
				//has more than one instances
				if(current.getNext().getCount() > 1) 
					//remove one instance of it
					current.getNext().setCount(current.getNext().getCount() - 1);
				//if the next node of the current one
				//has only one instance
				else 
					//remove the only one instance of it
					current.setNext(current.getNext().getNext());
				//Skip the while loop(for improving the efficiency)
				break;
			}
			//move the pointer from current to its next node
			current = current.getNext();
		}
	} // end of removeOne()
	
	
	public void removeAll(T item) 
	{//check whether head is null
		if(head == null) 
			//if so, terminate this method immediately
			return;
		
		//if head's item matches the parameter
		if(head.itemEquals(item)) 
		{
			//point the head reference to its
			//next node, i.e., delete the current
			//head
			head = head.getNext();
			//terminate this method immediately
			return;
		}
		
		//start from head
		Node current = head;

		//while the current node has its next node
		//execute this while loop
		while(current.hasNext()) 
		{//if the item of the current node's next one matches
			//the parameter item
			if(current.getNext().itemEquals(item)) 
			{//set the next node of the current one's next node
				//as the next node of the current one
				//i.e., delete the next node of the current one
				current.setNext(current.getNext().getNext());
				//Skip the while loop(for improving the efficiency)
				break;
			}
			//move the pointer from current to its next node
			current = current.getNext();
		}
	} // end of removeAll()
	
	
	public void print(PrintStream out) 
	{//start from head 
		Node current = head;
		
		//while the current node is not null
		//execute this loop
		while(current!=null) 
		{//print the current node
			out.println(current.getItem() + printDelim + current.getCount());
			//then move the current node pointer to its next node
			current = current.getNext();
		}
	} // end of print()
	
	class Node
	{
		private T item;
		private Node next;
		private int count;

		public Node(T item) 
		{
			this.item = item;
			count = 1;
			next = null;
		}

		public T getItem() 
		{
			return item;
		}

		public void setItem(T item) 
		{
			this.item = item;
		}

		public Node getNext() 
		{
			return next;
		}

		public void setNext(Node next) 
		{
			this.next = next;
		}
		
		public boolean itemEquals(T item) 
		{
			return this.getItem().equals(item);
		}
		
		public void increCount() 
		{
			this.count++;
		}
		
		public int getCount() 
		{
			return count;
		}
		
		public void setCount(int count)
		{
			this.count = count;
		}
		
		
		public boolean hasNext()
		{
			if(next == null)
				return false;
			else
				return true;
		}
	}
} // end of class LinkedListMultiset