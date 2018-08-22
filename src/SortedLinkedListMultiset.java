import java.io.PrintStream;

/**
 * 
 * @author Xinyu YE s3468489, Yifan ZHANG s3615625
 *
 * @param <T>
 */

public class SortedLinkedListMultiset<T extends Comparable<T>> extends Multiset<T>
{
	private Node head;
	private Node tail;

	public SortedLinkedListMultiset() 
	{
		head = null;
		tail = null;
	} // end of SortedLinkedListMultiset()


	public void add(T item) 
	{//if the head reference
		//points to null
		if(head == null) 
		{//instantiate a new node with
			//the parameter item
			//point the head reference
			//to the newly constructed node
			head = new Node(item);
			//point the tail reference to the head
			//as it is also the tail now
			tail = head;
			//immediately terminate this method
			return;
		}

		//the result of comparison between head's item
		//and the parameter item
		int headResult = head.getItem().compareTo(item);
		//if head's item matches the parameter
		if(headResult == 0) 
		{//increase head's count by 1
			head.increCount();
			//immediately terminate this method
			return;
		}
		//if the parameter is smaller than head's item  
		else if(headResult > 0) 
		{//instantiate a new node with the parameter
			Node insertion = new Node(item);
			//set head as the new node's next item
			insertion.setNext(head);
			//point the head reference to the
			//newly constructed node
			head = insertion;
			//immediately terminate this method
			return;
		}

		//the result of comparison between tail's item
		//and the parameter item
		int tailResult = tail.getItem().compareTo(item);
		//if tail's item matches the parameter
		if(tailResult == 0) 
		{//increase tail's count by 1
			tail.increCount();
			//immediately terminate this method
			return;
		}
		//If the parameter item is larger than tail's item 
		else if(tailResult < 0)
		{
			//instantiate a new node with the parameter
			Node insertion = new Node(item);
			//insert the new node after the tail
			tail.setNext(insertion);
			//update the tail reference,
			//as the newly inserted node is 
			//the current tail
			tail = insertion;
			//immediately terminate this method
			return;
		}
		//If the parameter item is smaller than tail's item
		else 
		{//point the current reference to head
			Node current = head;

			//while the current node has its next node
			//execute this while loop
			while(current.hasNext()) 
			{//the result of comparison between the item of
				//the  current node's next node and the parameter item
				int nodeResult = current.getNext().getItem().compareTo(item);
				//if the two items match each other
				if(nodeResult == 0) 
				{//increase the count of the current node's
					//next node by 1
					current.getNext().increCount();
					//immediately terminate this method
					return;
				}
				//if the current node's next node's item
				//is smaller than the parameter item
				else if(nodeResult < 0) 
				{
					//move the current node pointer to its next node
					current = current.getNext();
				}
				else 
				{//the node's item is smaller than the parameter item
					Node insertion = new Node(item);
					insertion.setNext(current.getNext());
					current.setNext(insertion);
					//immediately terminate this method
					return;
				}
			}
		}
	} // end of add()

	public int search(T item) 
	{//the result of comparison between tail's item
		//and the parameter item
		int tailResult = tail.getItem().compareTo(item);
		//if the parameter is larger than tail's item
		if(tailResult < 0)
			//nothing matched the parameter item found
			return 0;
		//if the parameter is smaller than tail's item
		else 
		{//start from head
			Node current = head;
			//while the current node is not null
			//execute this loop
			while(current != null) 
			{//the result of comparison between the item of
				//the current node and the parameter item
				int nodeResult = current.getItem().compareTo(item);

				//if the current node is the target
				//to be searched
				if(nodeResult == 0)
					//return the count of the current node
					return current.getCount();
				//if the item of the current node is
				//smaller than the parameter item
				else if(nodeResult < 0)
					//move the pointer to its next node
					current = current.getNext();
				else
				{//Target not found
					//break the while loop to return 0 
					return 0;
				}
			}
		}
		return 0;
	} // end of add()


	public void removeOne(T item) 
	{//the result of comparison between head's item
		//and the parameter item
		int headResult = head.getItem().compareTo(item);
		//if head's item matches the parameter
		if(headResult == 0) 
		{//if head has more than one instance
			if(head.getCount() > 1) 
				//remove one instance of it
				head.setCount(head.getCount() - 1);
			//if head only has one instance
			else
				//point the head reference to head's next node
				//i.e., remove the only one instance of head
				head = head.getNext();
			//terminate this method immediately
			return;
		}
		//if the parameter smaller than head's item
		else if(headResult > 0) 
			//Target not found, terminate this method immediately
			return;

		//the result of comparison between tail's item
		//and the parameter item
		int tailResult = tail.getItem().compareTo(item);
		//If the parameter item is
		//larger than tail's item
		if(tailResult < 0)
			//terminate this method immediately
			//as the target is not in this linkedList
			return;
		//If the parameter item is
		//smaller than or equal to tail's item
		else 
		{//start from head
			Node current = head;

			//while the current node has its next node
			//execute this while loop
			while(current.hasNext()) 
			{//the result of comparison between the item of
				//the  current node's next node and the parameter item
				int nodeResult = current.getNext().getItem().compareTo(item);
				//if the two items match each other
				if(nodeResult == 0) 
				{//if the current node's next node has
					//more than one instances
					if(current.getNext().getCount() > 1) 
					{//Remove one instance of it
						current.getNext().setCount(current.getNext().getCount() - 1);
						break;
					}
					//if the current node's next node has
					//only one instance
					else 
					{//Remove the only one instance of it
						//by saving the current node's next node's
						//next node into a node type variable named
						//next
						Node next = current.getNext().getNext();
						//then set the node next as the current node's
						//next node
						current.setNext(next);

						//if the node to be deleted is the tail
						if(next == null) 
							//Update the tail
							//as the current node's next node served
							//as tail, after deleted it, point the tail
							//reference to the current node
							tail = current;
					}

					return;
				}
				else if(nodeResult < 0) 
					//continue searching for the target element
					current = current.getNext();
				else 
					//Target not found
					//break the while loop to terminate the method 
					break;
			}
		}
	} // end of removeOne()


	public void removeAll(T item) 
	{
		int headResult = head.getItem().compareTo(item);

		if(headResult == 0) 
		{
			head = head.getNext();
			return;
		}
		else if(headResult > 0) 
			return;

		int tailResult = tail.getItem().compareTo(item);

		if(tailResult < 0)
			return;
		else 
		{
			Node current = head;

			while(current.hasNext()) 
			{
				int nodeResult = current.getNext().getItem().compareTo(item);

				if(nodeResult ==  0) 
				{
					Node next = current.getNext().getNext();
					current.setNext(next);

					//if the node to be deleted is the tail
					if(next == null) 
						//Update the tail
						tail = current;

					return;
				}
				else if(nodeResult < 0) 
					current = current.getNext();
				else 
					//Item not found
					//break the while loop to terminate the method 
					break;
			}
		}
	} // end of removeAll()


	public void print(PrintStream out) 
	{//start from head
		Node current = head;

		//while the current node is not null
		//execute this loop
		while(current != null) 
		{
			//print the current node's item
			out.println(current.getItem() + printDelim + current.getCount());
			//then move the current node pointer to its next node
			current = current.getNext();
		}
	} // end of print()

	private class Node
	{
		private T item;
		private Node next;
		private int count;

		public int getCount() 
		{
			return count;
		}

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

		public Node getNext() 
		{
			return next;
		}

		public void setNext(Node next) 
		{
			this.next = next;
		}

		public boolean hasNext()
		{
			if(next == null)
				return false;
			else
				return true;
		}

		public void increCount() 
		{
			this.count++;
		}

		public void setCount(int count)
		{
			this.count = count;
		}
	}
} // end of class SortedLinkedListMultiset