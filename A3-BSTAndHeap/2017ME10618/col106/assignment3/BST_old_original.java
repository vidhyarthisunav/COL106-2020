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
	Node<T,E> head;
	HashMap<T,E> map;
	public static void main() {
		BSTDriverCode BDC = new BSTDriverCode();
		System.setOut(BDC.fileout());
	}

	/*public static void main(String[] args) {
		BST<Integer,Integer> b = new BST<Integer,Integer>();
		
		b.insert(1,60);
		b.insert(2,35);
		b.insert(3,65);
		b.insert(4,100);
		b.insert(6,500);
		//b.printBST();
		
		b.delete(3);
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

		Queue<Node> Q = new LinkedList<Node>();
		Q.add(this.head);

		outerloop:
		while(!Q.isEmpty()){
			Node temp = Q.peek();

			if(temp.key == key){

				//full node
				if(temp.Right != null & temp.Left != null){
					Node pre = FindMin(temp);
					temp.value = pre.value;
					temp.key = pre.key;
					pre = null;
					break outerloop;
				}

				//uske baap ka address parent_key
				T parent_key = (T)find_parentkey((E)temp.value, this.head);

				Queue<Node> Q1 = new LinkedList<Node>();
				Q1.add(this.head);

				while(!Q1.isEmpty()){
				Node temp1 = Q1.peek();

					if(temp1.key == parent_key){

						if(temp1.Right != null && temp1.Right.key == key){
							if(temp1.Right.Right == null && temp1.Right.Left == null)
								temp1.Right = null;
							else if(temp1.Right.Right != null)
								temp1.Right = temp1.Right.Right;
							else
								temp1.Right = temp1.Right.Left;
						}

						if(temp1.Left != null && temp1.Left.key == key){
							if(temp1.Left.Right == null && temp1.Left.Left == null)
								temp1.Left = null;
							else if(temp1.Left.Right != null)
								temp1.Left = temp1.Left.Right;
							else
								temp1.Left = temp1.Left.Left;
						}
						break outerloop;
					}
				
				
				if(temp1.Left != null)
					Q1.add(temp1.Left);
		
				if(temp1.Right != null)
					Q1.add(temp1.Right);
				
				Q1.remove();
				}
			}

			if(temp.Right != null){
				Q.add(temp.Right);
			}

			if(temp.Left != null){
				Q.add(temp.Left);
			}
			
		Q.remove();
		}
    }

	public T find_parentkey(E childvalue, Node root){

		if(root.Left != null && root.Left.value == childvalue)
			return (T)root.key;

		if(root.Right != null && root.Right.value == childvalue)
			return (T)root.key;

		if(childvalue.compareTo(root.value) > 0)
			return find_parentkey(childvalue, root.Right);

		return find_parentkey(childvalue, root.Left);
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