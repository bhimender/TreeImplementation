import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class BNode {
	private ArrayList<Integer> key;	//child nodes of a node
	private ArrayList<Integer> value;	//keys
	private ArrayList<BNode> pointers;	//values attached to keys
	private int weight;	//Current number of keys in the node - 1
	private int order;	//Order of Btree
	
	//Constructor
	public BNode(int a, int val, int ord){
		pointers = new ArrayList<BNode>();	
		key = new ArrayList<Integer>();		
		value = new ArrayList<Integer>();	
		key.add(a);	//Add first Key
		value.add(val);	//Add first Value
		order = ord;
		weight = 0;
	}
	
	//Getter Setters
	public void setKey(int k, int index){
		key.set(index, k);
	}
	
	public int getKey(int index){
		return key.get(index);
	}
	
	public void setValue(int val, int index){
		value.set(index, val);
	}
	
	public int getValue(int index){
		return value.get(index);
	}
	
	public int getFirstKey(){
		return key.get(0);
	}
	
	public int getLastKey(){
		return key.get(weight);
	}
	
	public BNode getChild(int index){
		if(index >= pointers.size())		//For a leaf node, child nodes don't exist, so return null
			return null;
		return pointers.get(index);
	}
	
	public void addChild(BNode node, String pos){
		if(pos == "start")					//Insert at the start of the child array
			pointers.add(0, node);
		else if(pos == "end"){				//Iniserts at the ned of the child Array
			if(pointers.size() == 0)
				pointers.add(node);
			else{
				pointers.add(pointers.size()-1,node);
				Collections.swap(pointers, pointers.size()-1, pointers.size()-2);	//To insert at the end, insert at the position 'current size' and then swap the last two entries
			}
		}
	}
	
	public int getOrder(){
		return order;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public void insertIntoLeaf(int k, int val){
		if(k < key.get(0)){	//Insert at the start
			key.add(0, k);
			value.add(0, val);
		}
		else if(k > key.get(weight)){	//Insert at the end
			key.add(weight, k);
			value.add(weight, val);
			key.set(weight, key.get(weight+1));	//To insert at the end, insert at the position 'current size' and then swap the last two entries
			value.set(weight, value.get(weight+1));
			key.set(weight+1, k);
			value.set(weight+1, val);
		}
		else{	//Insert in between
			int index =  -(Arrays.binarySearch(key.toArray(), k)+1);
			key.add(index, k);
			value.add(index, val);
			}
		weight++;	//Increase Weight
	}
	
	public int find(int k){
		int i  = Arrays.binarySearch(key.toArray(new Integer[key.size()]), k);
		return i;	//return the index if value is found. Or index where the value should be inserted (in negative)
	}
	
	public BNode split() {
		int splitPoint = (int)Math.ceil(order/2);	//Point where to slit the node
		
		BNode middle = new BNode(key.get(splitPoint),value.get(splitPoint),order);	//Create Middle
		BNode right = new BNode(key.get(splitPoint+1),value.get(splitPoint+1),order);	//Create Right, using the next key
		
		if(this.getChild(splitPoint+1)!=null)		//Keep inserting key/value to right node. Also add child nodes if available 
			right.addChild(this.getChild(splitPoint+1),"end");
		for(int i=splitPoint+2;i<=weight;i++){
			right.insertIntoLeaf(key.get(i), value.get(i));
			if(this.getChild(i)!=null)
				right.addChild(this.getChild(i),"end");
		}
		if(this.getChild(weight+1)!=null)
			right.addChild(this.getChild(weight+1),"end");		//Insert the last child
		
		middle.addChild(right,"end");		//Make right, the rightChild of middle
		
		int lim = weight;			//remove the keys, values and pointers, which were transferred to middle and right, from the node being split
		for(int i=splitPoint;i<=lim;i++){
			key.remove(splitPoint);
			value.remove(splitPoint);
			if(this.getChild(splitPoint)!=null)
				pointers.remove(splitPoint+1);
			weight--;
		}
		return middle;
	}
	
	public void merge(BNode node){
		int k = node.getKey(0);	//Extract the keys, values and pointers from the node to be merged to 'this'
		int val = node.getValue(0);
		BNode ptr = node.getChild(0);
		
		if(k < key.get(0)){		//If the key of node is less than all keys in 'this':
			key.add(0, k);		//then, insert key and value at the beginning
			value.add(0, val);
			pointers.add(1,ptr);	//And add the child to second position in 'this' 
		}
		else if(k > key.get(weight)){	//If the key of node is more than all keys in 'this': then add everythin at the end of 'this'
			key.add(weight, k);
			value.add(weight, val);
			pointers.add(weight+1,ptr);
			key.set(weight, key.get(weight+1));
			value.set(weight, value.get(weight+1));
			pointers.set(weight+1, pointers.get(weight+2));
			key.set(weight+1, k);
			value.set(weight+1, val);
			pointers.set(weight+2,ptr);
			//pointer check
		}
		else{	//Otherwise add at the appropriate location, st that the key array stays sorted
			int index =  -(Arrays.binarySearch(key.toArray(), k)+1);
			key.add(index, k);
			value.add(index, val);
			pointers.add(index+1,ptr);	//child is added at one greater location
			}
		weight = weight + 1;	//increment weight
	}
}
