package col106.assignment3.Heap;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

class Node_1<T extends Comparable, E extends Comparable>{
	T key;
	E value;
	Node_1(T key, E value){
		this.key = key;
		this.value = value;
	};
}

public class Heap<T extends Comparable, E extends Comparable> implements HeapInterface <T, E>{
	ArrayList<Node_1> H;
	
	public static void main() {
		HeapDriverCode HDC = new HeapDriverCode();
		System.setOut(HDC.fileout());
	}
	
	/*public static void main(String[] args){
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
	}*/

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
