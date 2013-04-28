import java.io.IOException;
import java.util.ArrayList;

public class AVLHash {
	private int s = 0;	//Hash size
	public AVLNode[] AVLArray;	//Array of AVL tress to serve as hashedtree. AVL Node serves as the root of a tree
	private AVL AVLtree;
	
	//Constructor
	public AVLHash(int sVal) throws IOException{
		s= sVal;
		AVLtree =   new AVL();
		AVLArray = new AVLNode[s];
	}
	
	public void add(int a, int val){
		this.AVLArray[a%s] = AVLtree.add(a,val,this.AVLArray[a%s]);	//Calls on the function 'add' of class AVL for a particular entry in hashedTree
	}
	
	public AVLNode search(int key){
		AVLNode node = AVLtree.search(key, this.AVLArray[key%s]);	//Calls on the function 'search' of class AVL for a particular entry in hashedTree
		return node;
	}
	
	public void inOrder(){
		for(int i=0;i<s;i++)
			AVLtree.inOrder(this.AVLArray[i]);			//Calls on the function 'inOrder' of class AVL for a particular entry in hashedTree
	}
	
	public void inOrderWrite(ArrayList<String> output){
		for(int i=0;i<s;i++){
			AVLtree.inOrderWrite(this.AVLArray[i],output);	//Calls on the function 'inOrderWrite' of class AVL for a particular entry in hashedTree
			output.add("NL");							//Add NewLine character after every tree
		}
	}
}
