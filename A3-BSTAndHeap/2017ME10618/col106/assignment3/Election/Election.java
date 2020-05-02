package col106.assignment3.Election;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Arrays;

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

class Node_1<T extends Comparable, E extends Comparable>{
	T key;
	E value;
	Node_1(T key, E value){
		this.key = key;
		this.value = value;
	};
}

class Data implements Comparable<Data>{
	String name;
	String candID;
	String state; 
	String district; 
	String constituency;
	String party;

	Data( String name,  String candID,  String state,  String district,  String constituency,  String party){
		this.name = name;
		this.candID = candID;
		this.state = state;
		this.district = district;
		this.constituency = constituency;
		this.party = party;
	}
	public int compareTo( Data other){
		return this.candID.compareTo(other.candID);
	}
}

public class Election implements ElectionInterface {

	BST<Data, Integer> tree;
	
	public static void main() {
		ElectionDriverCode EDC = new ElectionDriverCode();
		System.setOut(EDC.fileout());
	}
	
	/*public static void main( String[] args) {
		 Election E = new Election();
		E.insert("Cand1" ,"1" ,"S1","D11","C11","P1","10");
		E.insert("Cand2" ,"2" ,"S1","D11","C11","P2","50");
		E.insert("Cand3" ,"3" ,"S1","D11","C11","P3","30");
		E.insert("Cand4" ,"4" ,"S1","D12","C12","P1","5");
		E.insert("Cand5" ,"5" ,"S1","D12","C12","P2","11");
		E.insert("Cand6" ,"6" ,"S1","D12","C12","P3","15");
		E.insert("Cand7" ,"7" ,"S1","D13","C13","P1","12");
		E.insert("Cand8" ,"8" ,"S1","D13","C13","P2","13");
		E.insert("Cand9" ,"9" ,"S1","D13","C13","P3","14");
		E.insert("Cand10" ,"10" ,"S2","D21","C21","P1","0");
		E.insert("Cand11" ,"11" ,"S2","D21","C21","P2","32");
		E.insert("Cand12" ,"12" ,"S2","D21","C21","P3","6");
		E.insert("Cand13" ,"13" ,"S3","D31","C31","P1","18");
		E.insert("Cand14" ,"14" ,"S3","D31","C31","P3","19");
		E.insert("Cand15" ,"15" ,"S3","D31","C31","P3","55");
		E.insert("Cand16" ,"16" ,"S3","D32","C32","P1","255");
		E.insert("Cand17" ,"17" ,"S3","D32","C32","P2","75");
		E.insert("Cand18" ,"18" ,"S3","D33","C32","P3","180");
		E.printElectionLevelOrder();
		E.leadingPartyOverall();
		E.updateVote("Cand3", "3", "100");
		E.printElectionLevelOrder();
		E.leadingPartyOverall();
		E.leadingPartyInState("S1");
		E.leadingPartyInState("S2");
		E.leadingPartyInState("S3");
		E.topkInConstituency("C11", "4");
		E.topkInConstituency("C12", "2");
		E.topkInConstituency("C21", "3");
		E.cancelVoteConstituency("C11");
		E.printElectionLevelOrder();
		E.leadingPartyOverall();
		E.voteShareInState("P2", "S2");
		E.voteShareInState("P3", "S1");
		E.voteShareInState("P1", "S1");
		E.voteShareInState("P2", "S1");
	}*/

	Election(){
		this.tree = new BST<Data, Integer>();
	}

    public void insert( String name,  String candID,  String state,  String district,  String constituency,  String party,  String votes){
		 Data node_data = new Data(name, candID, state, district, constituency, party);
		this.tree.insert(node_data,Integer.parseInt(votes));
		//map.put(votes, node_data);
	}

	public void updateVote( String name,  String candID,  String votes){
		
		 Queue<Node> Q = new LinkedList<Node>();
		Q.add(this.tree.head);
		while(!Q.isEmpty()){
			 Node temp = Q.peek();
			 Data A = (Data)temp.key;
			 
			if(candID.equals(A.candID)){
				this.tree.update(A,Integer.parseInt(votes));
				break;
			}
			if(temp.Left != null){
				Q.add(temp.Left);
			}
			if(temp.Right != null){
				Q.add(temp.Right);
			}
		Q.remove();
		}
	}

	public void topkInConstituency( String constituency,  String k){
		
		 Heap<Data,Integer> temp_heap = new Heap<Data,Integer>();
		 Queue<Node> Q = new LinkedList<Node>();
		Q.add(this.tree.head);
		int count = 0;
		while(!Q.isEmpty()){
			 Node temp = Q.peek();
			 Data A = (Data)temp.key;
			if(constituency.equals(A.constituency)){
				temp_heap.insert(A,(int)temp.value);
				count++;
			}
			if(temp.Left != null){
				Q.add(temp.Left);
			}
			if(temp.Right != null){
				Q.add(temp.Right);
			}
		Q.remove();
		}

		 int j = Integer.parseInt(k);
		for(int i = 0; i < j && i < count; i++){
			 int V = temp_heap.extractMax();
			//System.out.println(V+" "+i);
			 Data P = votesToData(this.tree.head, V);
			System.out.println(P.name+", "+P.candID+", "+P.party);
		}
	}
	Data votesToData( Node root,  int V){
		
		if((int)root.value == V)
			return (Data)root.key;
		if((int)root.value < V)
			return votesToData(root.Right, V);
		return votesToData(root.Left, V);
	}

	public void leadingPartyInState( String state){
		HashMap<String,Integer> H = new HashMap<String,Integer>();
		Queue<Node> Q = new LinkedList<Node>();
		Q.add(this.tree.head);
		
		while(!Q.isEmpty()){
			Node temp = Q.peek();
			Data A = (Data)temp.key;
			if(state.equals(A.state)){
				try{
					H.put(A.party, (int)temp.value + H.get(A.party));
				}
				catch( Exception e){
					H.put(A.party,(int)temp.value);
				}
			}
			if(temp.Left != null){
				Q.add(temp.Left);
			}
			if(temp.Right != null){
				Q.add(temp.Right);
			}
		Q.remove();
		}
		int temp_max = 0;
		for(String name : H.keySet()){
			int value = H.get(name);
			if(value > temp_max)
				temp_max = value;
		}
		int count = 0;
		for(String name : H.keySet()){
			int value = H.get(name);
			if(value == temp_max)
				count++;
		}
		String[] state_parties = new String[count];
		int i = 0;
		for(String name : H.keySet()){
			int value = H.get(name);
			if(value == temp_max){
				state_parties[i] = name;
				i++;
			}
		}
		Arrays.sort(state_parties);
		for(int j = 0; j < count; j++){
			System.out.println(state_parties[j]);
		}
	}

	public void cancelVoteConstituency( String constituency){
		 Queue<Node> Q = new LinkedList<Node>();
		 int count = 0;
		Q.add(this.tree.head);
		while(!Q.isEmpty()){
			 Node temp = Q.peek();
			 Data A = (Data)temp.key;
			if(constituency.equals(A.constituency)){
				count++;
			}
			if(temp.Left != null){
				Q.add(temp.Left);
			}
			if(temp.Right != null){
				Q.add(temp.Right);
			}
		Q.remove();
		}
		
		String[] sorted_candID = new String[count];
		int index = 0;
		Q.add(this.tree.head);
		while(!Q.isEmpty()){
			Node temp = Q.peek();
			Data A = (Data)temp.key;
		   if(constituency.equals(A.constituency)){
			   sorted_candID[index] = A.candID;
			   index++;
		   }
		   if(temp.Left != null){
			   Q.add(temp.Left);
		   }
		   if(temp.Right != null){
			   Q.add(temp.Right);
		   }
	   Q.remove();
	   }

	   Arrays.sort(sorted_candID);

	   int delete_index = 0;
	   while(delete_index < count){
		   String temp_candID = sorted_candID[delete_index];
		   Q.add(this.tree.head);
			while(!Q.isEmpty()){
				Node temp = Q.peek();
				Data A = (Data)temp.key;
		   		if(temp_candID.equals(A.candID)){
					//System.out.println(A.candID);
			    	this.tree.delete(A);
		   		}
		   		if(temp.Left != null){
			   		Q.add(temp.Left);
		   		}
		   		if(temp.Right != null){
			   		Q.add(temp.Right);
		   		}
	   			Q.remove();
	   		}
	    delete_index++;
		}
	}
	
	public void leadingPartyOverall(){
		 HashMap<String,Integer> H = new HashMap<String,Integer>();
		 Queue<Node> Q = new LinkedList<Node>();
		 
		Q.add(this.tree.head);
		while(!Q.isEmpty()){
			Node temp = Q.peek();
			Data A = (Data)temp.key;
			try{
				H.put(A.party, (int)temp.value + H.get(A.party));
			}
			catch( Exception e){
				H.put(A.party,(int)temp.value);
			}
			if(temp.Left != null){
				Q.add(temp.Left);
			}
			if(temp.Right != null){
				Q.add(temp.Right);
			}
		Q.remove();
		}
		int temp_max = 0;
		for(String name : H.keySet()){
			int value = H.get(name);
			if(value > temp_max)
				temp_max = value;
		}
		int count = 0;
		for(String name : H.keySet()){
			int value = H.get(name);
			if(value == temp_max)
				count++;
		}
		String[] overall_parties = new String[count];
		int i = 0;
		for(String name : H.keySet()){
			int value = H.get(name);
			if(value == temp_max){
				overall_parties[i] = name;
				i++;
			}
		}
		Arrays.sort(overall_parties);
		for(int j = 0; j < count; j++){
			System.out.println(overall_parties[j]);
		}
	}
	public void voteShareInState( String party, String state){

		int total = 0, of_party = 0;
		Queue<Node> Q = new LinkedList<Node>();
		Q.add(this.tree.head);
		while(!Q.isEmpty()){
			 Node temp = Q.peek();
			 Data A = (Data)temp.key;
			if(state.equals(A.state)){
				total = total + (int)temp.value;
				if(party.equals(A.party))
					of_party += (int)temp.value;
			}
			if(temp.Left != null){
				Q.add(temp.Left);
			}
			if(temp.Right != null){
				Q.add(temp.Right);
			}
		Q.remove();
		}
		int ans = 0;
		if(total != 0)
			ans = (int)100*of_party/total;
		System.out.println(ans);
	}

	public void printElectionLevelOrder() {
		Queue<Node> Q = new LinkedList<Node>();
		Q.add(this.tree.head);
		while(!Q.isEmpty()){
			 Node temp = Q.peek();
			 Data A = (Data)temp.key;
			System.out.println(A.name+", "+A.candID+", "+A.state+", "+A.district+", "+A.constituency+", "+A.party+", "+temp.value);
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

class BST <T extends Comparable, E extends Comparable> /*implements BSTInterface<T, E>*/{
	Node<T,E> head;
	HashMap<T,E> map;
	/*public static void main() {
		BSTDriverCode BDC = new BSTDriverCode();
		System.setOut(BDC.fileout());
	}*/

	public static void main(String[] args) {
		BST<Integer,String> b = new BST<Integer,String>();
		
		b.insert(1,"1200");
		b.insert(2,"1000");
		b.insert(3,"1500");
		b.insert(4,"2000");
		b.insert(6,"500");	
		b.printBST();
	}
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
	public Node Delete(Node root, E value){

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
class Heap<T extends Comparable, E extends Comparable> /*implements HeapInterface <T, E>*/ {
	ArrayList<Node_1> H;
	
	/*public static void main() {
		HeapDriverCode HDC = new HeapDriverCode();
		System.setOut(HDC.fileout());
	}*/
	
	public static void main(String[] args){
		Heap<Integer,Integer> b = new Heap<Integer,Integer>();
		b.insert(1,1200);
		b.insert(2,1000);
		b.insert(3,1500);
		b.insert(5,500);
		b.insert(4,2000);
		
		b.printHeap();
		System.out.println(b.extractMax());
		System.out.println(b.extractMax());
		System.out.println(b.extractMax());
	}

	Heap(){
		this.H = new ArrayList<Node_1>();
	}
	
	// write your code here	
	public void insert(T key, E value) {
		Node_1 temp = new Node_1(key,value);
		this.H.add(temp);
		
		SiftUp(this.H.size());
		
		return;
	}

	public E extractMax() {

		E temp = (E)this.H.get(0).value;

		//delete the max element

		swapNode_1s(this.H.get(0), this.H.get(this.H.size()-1));

		ArrayList<Node_1> temp_2 = new ArrayList<Node_1>();

		for(int j = 0; j < this.H.size()-1 ; j++){
			temp_2.add(this.H.get(j));
		}
		this.H = temp_2;

		SiftDown(0);
		return temp;
	}

	public void delete(T key) {
		int i;
		for(i = 0; i < this.H.size(); i++)
			if(this.H.get(i).key == key)
				break;
		
		swapNode_1s(this.H.get(i), this.H.get(this.H.size()-1));

		ArrayList<Node_1> temp = new ArrayList<Node_1>();

		for(int j = 0; j < this.H.size() - 1 ; j++){
			temp.add(this.H.get(j));
		}
		this.H = temp;

		SiftDown(i);
	}

	public void increaseKey(T key, E value) {
		int i;

		for(i = 0; i < this.H.size(); i++){
			if(this.H.get(i).key == key){
				this.H.get(i).value = value;
				break;
			}
		}
		
		SiftUp(i+1);
		SiftDown(i);
	}

	public void printHeap() {
		//System.out.println(this.H.size());
		for(int i = 0; i < this.H.size(); i++){
			System.out.println(this.H.get(i).key+", "+this.H.get(i).value);
		}
	}

	public void SiftUp(int i){

		while(i > 1 && this.H.get(i/2-1).value.compareTo(this.H.get(i-1).value) < 0){
			swapNode_1s(this.H.get(i/2-1),this.H.get(i-1));
			i = i/2;
		}

	}

	public void SiftDown(int i){
		
		while(2*(i+1)-1 < this.H.size()){
			if(2*(i+1) < this.H.size()){
				if(this.H.get(2*(i+1)).value.compareTo(this.H.get(2*(i+1)-1).value) > 0){
					if(this.H.get(2*(i+1)).value.compareTo(this.H.get(i).value) > 0){
						swapNode_1s(this.H.get(i),this.H.get(2*(i+1)));
						i = 2*(i+1);
					}
					else
						break;			
				}
				else if(this.H.get(2*(i+1)).value.compareTo(this.H.get(2*(i+1)-1).value) < 0){
					if(this.H.get(2*(i+1)-1).value.compareTo(this.H.get(i).value) > 0){
						swapNode_1s(this.H.get(i),this.H.get(2*(i+1)-1));
						i = 2*(i+1)-1;
					}
					else
						break;
				}
			}
			else if (this.H.get(2*(i+1)-1).value.compareTo(this.H.get(i).value) > 0){
				swapNode_1s(this.H.get(i),this.H.get(2*(i+1)-1));
				i = 2*(i+1)-1;
			}
			else
				break;
		}
	}

	public void swapNode_1s(Node_1 A, Node_1 B){

		Node_1 temp_swap = new Node_1(A.key,A.value);

			A.value = B.value ;
			A.key = B.key ;

			B.value = temp_swap.value ;
			B.key = temp_swap.key ;
	}
}