import java.io.PrintStream;
/**
 * 
 * @author Xinyu YE s3468489, Yifan ZHANG s3615625
 *
 * @param <T>
 */

public class BstMultiset<T extends Comparable<T>> extends Multiset<T>
{
	Node root; 
	
	public BstMultiset() 
	{
		//Initialize the root to be null
		root = null;
	} // end of BstMultiset()

	public void add(T item) 
	{
		//If the BST is empty
		if(root == null)
			//insert the new item
			//to instantiate a new node
			//and point the root reference
			//to the newly constructed node
			root = new Node(item);
		else
			//invoke the insertNode(item) method
			//to recursively find the right place
			//to insert the new node
			root.insertNode(item);
	} // end of add()

	public int search(T item) 
	{
		//if the BST is empty
		if(root == null)
			//return 0 
			//as nothing is found
			return 0;
		else 
		{//recursively search the node contains
			//the parameter item from the root
			Node node = root.searchNode(item);
			//if target not found
			if(node == null) 
				return 0;
			//if target found
			else 
				//return the count of it
				return node.getCount();
		}
	} // end of add()


	public void removeOne(T item) 
	{
		//if the BST is empty
		if(root == null) 
			//terminate this method
			//immediately
			return;
		//recursively search the node to be deleted
		Node node = root.searchNode(item);
		
		//if target not found
		if(node == null)
			//terminate this method
			//immediately
			return;
		
		//if the target has more than
		//one instance
		if(node.count > 1) 
		{//remove one instance of it
			node.count--;
			//terminate this method
			//immediately
			return;
		}
		//if the target only has one instance
		else 
		{//get the parent of the node to be deleted
			Node parent = node.getParent();
			//the root does not have a parent
			boolean isRoot = (parent == null);
			
			//if the node to be deleted is not the root
			//and it is the left child of its parent
			boolean isleftChild = (!isRoot && (node == node.getParent().getLeft()));
			
			//if the node to be deleted is a leaf node
			if(node.isLeaf()) 
			{
				//if the would-be-deleted node is the root as well as a leaf
				//which means there is only one node in the tree
				//i.e., the node to be deleted
				if(isRoot) 
					//delete the root
					this.root = null;
				//if the node to be deteled is only a leaf node but not the root
				else 
				{//if the node is the left child of its parent
					if(isleftChild)
						//set the reference from its parent
						//to itself to null
						node.getParent().setLeft(null);
					//if the node is the right child of its parent
					else
						//set the reference from its parent
						//to itself to null
						node.getParent().setRight(null);
				}
			}
			//if the node is not a leaf 
			else 
			{//and it does not have a right child
				if(node.getRight() == null) 
				{
					//if the node to be deleted is the root 
					if(isRoot)
					{//point the root reference to the
						//left child of it
						this.root = node.getLeft();
						//make the left child of the root
						//to be deleted the new root
						this.root.setParent(null);
					}
					//if the node to be deleted is not the root 
					else
					{//if the node to be deleted is the left
						//child of its parent
						if(isleftChild) 
							//set the left child of the node to be
							//deleted as the left child of its parent
							node.getParent().setLeft(node.getLeft());
						//if the node to be deleted is the right
						//child of its parent
						else
							//set the left child of the node to be
							//deleted as the right child of its parent
							node.getParent().setRight(node.getLeft());
						
						//set the parent of the node to be deleted
						//the parent of the left child of the node
						//to be deleted
						node.getLeft().setParent(node.getParent());
					}
				}
				//or if it does have a right child
				else 
				{//invoke the getRightMin() method
					//by the right child of the node to be deleted
					//to find the node containing the minimum item in
					//its right subtree
					Node min = node.getRight().getRightMin();
					//if the minimum node is a leaf
					if(min.isLeaf()) 
					{//swap the item and count of the min node
						//with the node to be deleted
						node.exchPosition(min);
						
						//if the min node is the left child of
						//its parent
						if(min.getParent().left == min)
							//delete its parent's left child 
							//which is now the node to be deleted
							min.getParent().setLeft(null);
						//if the min node is the right child of
						//its parent
						else
							//delete its parent's right child
							//which is now the node to be deleted
							min.getParent().setRight(null);
						
						//if the node to be deleted is the root
						if(isRoot) 
							//set the node containing the minimum item
							//in its right subtree as the new root, here
							//the node reference points to the min
							//node in its right subtree
							//after swapping the item and count
							this.root = node;
					}
					//if the minimum node is not a leaf
					else 
					{
						node.exchPosition(min);
						//get the parent of the min node
						Node minParent = min.getParent();
						
						if(minParent.getLeft() == min) 
							minParent.setLeft(min.getRight());
						else 
							minParent.setRight(min.getRight());
						//if the min node has a right child
						if(min.getRight()!=null)
							//set the min node's parent
							//the parent of min node's right child
							min.getRight().setParent(minParent);
					}
				}
			}
		}
	} // end of removeOne()
	
	public void removeAll(T item) 
	{
		if(root == null) 
			return;
		else 
		{
			Node node = root.searchNode(item);
			
			if(node == null)
				return;
			else 
			{
				Node parent = node.getParent();
				boolean isRoot = (parent == null);
				
				boolean isleftChild = (!isRoot && (node == node.getParent().getLeft()));
				
				if(node.isLeaf()) 
				{
					if(isRoot) 
						this.root = null;
					else 
					{
						if(isleftChild) 
							node.getParent().setLeft(null);
						else
							node.getParent().setRight(null);
					}
				}
				else 
				{
					if(node.getRight() == null) 
					{
						if(isRoot)
						{
							this.root = node.getLeft();
							this.root.setParent(null);
						}
						else
						{
							if(isleftChild) 
								node.getParent().setLeft(node.getLeft());
							else
								node.getParent().setRight(node.getLeft());
							
							node.getLeft().setParent(node.getParent());
						}
					}
					else 
					{
						Node min = node.getRight().getRightMin();
						
						if(min.isLeaf()) 
						{
							node.exchPosition(min);
							
							if(min.getParent().left == min)
								min.getParent().setLeft(null);
							else
								min.getParent().setRight(null);
							
							if(isRoot) 
								this.root = node;
						}
						else 
						{
							node.exchPosition(min);
							
							Node minParent = min.getParent();
							
							if(minParent.getLeft() == min) 
							{
								minParent.setLeft(min.getRight());
								
								if(min.getRight()!=null)
									min.getRight().setParent(minParent);
							}
							else 
							{
								minParent.setRight(min.getRight());
								
								if(min.getRight()!=null)
									min.getRight().setParent(minParent);
							}
						}
					}
				}
			}
		}
	} // end of removeAll()

	/**
	 * Traverse and print the BST in inorder
	 */
	public void print(PrintStream out)
	{
		Node node = root;
		
		if (node != null)
			inOrderTraversal(node, out);
	}// end of print()

	/**
	 * Traverse the BST in inorder
	 * @param node
	 * @param out
	 */
	private void inOrderTraversal(Node node, PrintStream out)
	{
	    if(node.getLeft() != null)
	    	inOrderTraversal(node.getLeft(), out);
	    
	    out.println(node.getItem() + printDelim + node.getCount());
	    
	    if(node.getRight() != null)
	    	inOrderTraversal(node.getRight(), out);
	}
	
	private class Node
	{
		private T item;
		private Node parent;
		private Node left;
		private Node right;
		//the counter of 
		//number of instances
		private int count;
		
		public Node(T item) 
		{
			this.item = item;
			parent = null;
			left = null;
			right = null;
			count = 1;
		}
		
		public T getItem() 
		{
			return item;
		}

		public void setItem(T item) 
		{
			this.item = item;
		}
		
		public Node getParent() 
		{
			return parent;
		}

		public void setParent(Node parent) 
		{
			this.parent = parent;
		}

		public Node getLeft() 
		{
			return left;
		}

		public void setLeft(Node left) 
		{
			this.left = left;
		}

		public Node getRight()
		{
			return right;
		}

		public void setRight(Node right) 
		{
			this.right = right;
		}

		public int getCount() 
		{
			return count;
		}

		public void setCount(int count) 
		{
			this.count = count;
		}

		public boolean isLeaf()
		{
			return (this.getLeft() == null && this.getRight() == null);
		}
		
		/**
		 * Recursively insert the new node
		 * constructed by the parameter item
		 * into its right place in the BST
		 * @param item
		 */
		public void insertNode(T item) 
		{//the result of comparison of the item of the node
			//invoking this method with the parameter item
			int compareResult = this.getItem().compareTo(item);
			//if the two items match each other
			if(compareResult == 0)
				//increase the count of the node invoking
				//this method by 1
				this.count++;
			//if the parameter item is smaller than
			//the item of the node invoking this method
			else if(compareResult > 0) 
			{//if the node invoking this method has
				//a left child
				if(this.getLeft() != null) 
					//invoke this method by its left child
					this.getLeft().insertNode(item);
				//if the node invoking this method 
				//does not have a left child
				else 
				{//instantiate a new node with the parameter item
					Node left = new Node(item);
					//set the newly constructed node
					//as the left child of the node
					//invoking this method
					this.setLeft(left);
					//set the node invoking this method
					//the parent of the newly constructed node
					left.setParent(this);
				}
			}
			//if the parameter item is larger than
			//the item of the node invoking this method
			else 
			{//if the node invoking this method has a right child
				if(this.getRight() != null)
					//invoke this method by its right child
					this.getRight().insertNode(item);
				//if the node invoking this method 
				//does not have a right child
				else 
				{//instantiate a new node with the parameter item
					Node right = new Node(item);
					//set the newly constructed node
					//as the right child of the node
					//invoking this method
					this.setRight(right);
					//set the node invoking this method
					//the parent of the newly constructed node
					right.setParent(this);
				}
			}
		}
		
		/**
		 * Recursively search the node contains
		 * the parameter item in the BST
		 * @param item
		 * @return
		 */
		public Node searchNode(T item) 
		{//the result of comparison of the item of the node
			//invoking this method with the parameter item
			int compareResult = this.getItem().compareTo(item);
			//if the two items match each other
			if(compareResult == 0) 
				//return the node invoking this method
				//as the target to be found
				return this;
			//if the parameter item is smaller than
			//the item of the node invoking this method
			else if(compareResult > 0 )
			{//if the node invoking this method has
				//a left child
				if(this.getLeft() != null) 
					//recursively search the left subtree of 
					//its left child
					return this.getLeft().searchNode(item);
				//if the node invoking this method does not
				//have a left child
				else
					//this indicates target not found in
					//this BST
					return null;
			}
			//if the parameter item is larger than
			//the item of the node invoking this method
			else 
			{//if the node invoking this method has
				//a right child
				if(this.getRight()!=null) 
					//recursively search the right subtree of 
					//its right child
					return this.getRight().searchNode(item);
				//if the node invoking this method does not
				//have a right child
				else 
					//this indicates target not found in
					//this BST
					return null;
			}
		}
		
		/**
		 * Recursively search the
		 * node containing the minimum item
		 * in the right subtree of the node
		 * to be deleted
		 * 
		 * @return the min node in the right
		 * subtree of the node to be deleted
		 */
		public Node getRightMin() 
		{//if the node invoking this method has
			//a left child
			if(this.getLeft() != null)
				//recursively search its left subtree
				//of its left child
				return this.getLeft().getRightMin();
			//if the node invoking this method does not
			//have a left child
			else 
				//return the node invoking this method
				//as the minimum node in its right subtree
				return this;
		}
		
		/**
		 * Swap the item and count of the node
		 * contains the minimum item
		 * in the right subtree with 
		 * the node to be deleted
		 * 
		 * @param min
		 */
		public void exchPosition(Node min) 
		{//save the minimum node in the
			//right subtree
			//to a temp node
			Node temp = copy();
			
			this.setItem(min.getItem());
			this.setCount(min.getCount());
			
			min.setItem(temp.getItem());
			min.setCount(temp.getCount());
		}
		
		/**
		 * save the node to be deleted
		 * to a temp node
		 * @return
		 */
		public Node copy() 
		{
			Node temp = new Node(this.getItem());
			
			temp.setCount(this.getCount());
			
			temp.setParent(this.getParent());
			
			temp.setLeft(this.getLeft());
			
			temp.setRight(this.getRight());
			
			return temp;
		}
	}
} // end of class BstMultiset
