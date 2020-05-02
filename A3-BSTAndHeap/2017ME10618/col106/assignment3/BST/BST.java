package col106.assignment3.BST;

import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;

class Node<T,E>{
	T key;
	E value;
	Node Left;
	Node Right;
	Node(T key, E value){
		this.key = key;
		this.value = value;
		Left = Right = null;
	};
}

public class BST <T extends Comparable, E extends Comparable> implements BSTInterface<T, E>{
	public Node<T,E> head;
	HashMap<T,E> map;
	public static void main() {
		BSTDriverCode BDC = new BSTDriverCode();
		System.setOut(BDC.fileout());
	}

	/*public static void main(String[] args) {
		BST<Integer,String> b = new BST<Integer,String>();
		
		b.insert(1,"1200");
		b.insert(2,"1000");
		b.insert(3,"1500");
		b.insert(4,"2000");
		b.insert(6,"500");	
		b.printBST();
	}*/
	BST(){
		this.head = null;
		this.map = new HashMap<T,E>();
	}

    public void insert(T key, E value) {
		
		map.put(key, value);

		if(this.head == null){
			this.head = new Node(key, value);
			return;
		}
		Node temp = this.head;
		outerloop:
		while(true){
			if(value.compareTo(temp.value) > 0){
				if(temp.Right == null){
					temp.Right = new Node(key, value);
					break outerloop;
				}
				else
					temp = temp.Right;
			}
			else if(value.compareTo(temp.value) < 0){
				if(temp.Left == null){
					temp.Left = new Node(key, value);
					break outerloop;
				}
				else
					temp = temp.Left;
			}
		}
    }

    public void update(T key, E value) {
		delete(key);
		insert(key,value);
    }

    public void delete(T key) {

		E value = map.get(key);
		Node target = Delete(this.head, value);

	}
	Node Delete(Node root, E value){

		if(root == null)
			return root;
		else if( value.compareTo(root.value) < 0)
			root.Left = Delete(root.Left, value);

		else if( value.compareTo(root.value) > 0)
			root.Right = Delete(root.Right, value);

		else{

			if(root.Left == null && root.Right == null){
				root = null;
			}
			else if (root.Left == null){
				Node temp = root;
				root = root.Right;
			}
			else if (root.Right == null){
				Node temp = root;
				root = root.Left;
			}
			else {
				Node temp = FindMin(root.Right);
				root.value = temp.value;
				root.key = temp.key;
				root.Right = Delete(root.Right, (E)temp.value);
			}
		}
		return root;
	}

	public Node FindMin(Node N){

		Node current = N;
		
		if(current.Left == null){
			return current;
		}

		current = current.Left;
		while(current.Left != null){
			current = current.Left;
		}
		return current;
	}
		/*Node current = this.head;
		Node parent = null;
		while(true){

			if(current.value == value){

				if(current.Right != null){
					Node pre = predecessor(current);
					current.value = pre.value;
					current.key = pre.key;
				}
				if(current.Right == null && current.Left == null){
					if(parent.Right != null && parent.Right.value == value)
						parent.Right = null;
					else
						parent.Left = null;
				}
				else if(current.Right == null){
					if(parent.Right != null && parent.Right.value == value)
						parent.Right = current.Left;
					else
						parent.Left = current.Left;
				}
				break;
			}

			else if(value.compareTo(current.value) > 0){
				parent = current;
				current = current.Right;
			}

			else{
				parent = current;
				current = current.Left;
			}
		}
	}

	public Node predecessor(Node N){

		Node current = N.Right;
		
		if(current.Left == null){
			
			return current;
		}

		Node parent = current;
		current = current.Left;
		while(current.Left != null){
			parent = parent.Left;
			current = current.Left;
		}
		parent.Left = null;
		return current;
	}*/

    public void printBST(){
		Queue<Node> Q = new LinkedList<Node>();
		Q.add(this.head);
		while(!Q.isEmpty()){
			Node temp = Q.peek();
			System.out.println(temp.key+", "+temp.value);
			if(temp.Left != null){
				Q.add(temp.Left);
			}
			if(temp.Right != null){
				Q.add(temp.Right);
			}
		Q.remove();
		}
    }
}