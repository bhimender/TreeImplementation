import java.util.*;

class RedBlackHash { 
	
	private int s = 0;	//Hash Size
	public TreeMap<Integer,Integer>[] TMArray;	//Treemap
	
	//Constructor
	public RedBlackHash(int sVal){
		s= sVal;
		TMArray = new TreeMap[s];
		for(int i=0;i<s;i++){
			TMArray[i] = new TreeMap();	//Initialize all the treemaps in hash array
		}
	}
	
	public void add(int key, int value){
		TMArray[key%s].put(key, value);	//Calls on the function 'put' of class TreeMap for a particular entry in hashedTree
	}
	
	public int search(int key){
		return TMArray[key%s].get(key);	//Calls on the function 'get' of class TreeMap for a particular entry in hashedTree
	}
}