import java.util.ArrayList;


public class AVL {
	//Search AVL tree recursively
	public AVLNode search(int a, AVLNode node){
		if(node.getKey() == a)
			return node;		//if found, return node
		if((node.getLc() == null)&&(node.getRc() == null))
			return node;		//if reached leaf return the last node before falling out of tree
		if(a > node.getKey()){
			if(node.getRc() == null)
				return node;
			return search(a, node.getRc());		//if key is greater than current node then search in right child
		}
		else{
			if(node.getLc() == null)
				return node;
			return search(a, node.getLc());		//if key is less than current node then search in left child
		}
	}
	
	public AVLNode add(int a, int val, AVLNode node){
		//System.out.println(node);
		if(node == null){
			return new AVLNode(a,val);	//first entry, create root
		}
			if(a > node.getKey()){
				AVLNode node2 = add(a,val,node.getRc());	//recursively reach the leaf, where entry has to be made
				node.setRc(node2);							//add the new generated node as right child
				node.setHeight(Math.max(height(node.getLc()),height(node.getRc()))+1);	//update height	
				node.setBalanceFator(height(node.getLc())-height(node.getRc()));	//update balance factor
				if(Math.abs(node.getBalanceFator()) > 1){	////Rotate if Balance factor is out of range
					if(a > node2.getKey()){
						node = RR(node);		//RR rotation					
					}
					else{
						//RL rotation
						node.setRc(LL(node.getRc()));
						node = RR(node);
					}
				}
				//System.out.println("Data:"+node.getKey()+"BF:"+node.getBalanceFator());
				
				return node;
			}
			else if(a < node.getKey()){
				AVLNode node2 = add(a,val,node.getLc());	//recursively reach the leaf, where entry has to be made
				node.setLc(node2);
				node.setHeight(Math.max(height(node.getLc()),height(node.getRc()))+1);	
				node.setBalanceFator(height(node.getLc())-height(node.getRc()));
				if(Math.abs(node.getBalanceFator()) > 1){
					if(a < node2.getKey()){
						node = LL(node);	//LL Rotation
					}
					else{
						//LR Rotation
						node.setLc(RR(node.getLc()));
						node = LL(node);
					}
				}				
				return node;
			}
			else{//If the key already exits
				System.out.println("Repeated Entry. Ignored");
				return node;
			}
	}

	//Get Height
	public int height(AVLNode node){
		if(node == null)
			return 0;	//If node is null, consider height as 0
		return node.getHeight();
	}
	
	public AVLNode LL(AVLNode A){
		AVLNode B = A.getLc();	//Create B copied from Left child of A 
		A.setLc(B.getRc());		//Set left child of A from Right child of B
		B.setRc(A);				//Set A as Right child of A
		A.setHeight(Math.max(height(A.getLc()),height(A.getRc()))+1);	//Update height
		B.setHeight(Math.max(height(B.getLc()),height(B.getRc()))+1);
		A.setBalanceFator(height(A.getLc())-height(A.getRc()));			//Update Balance Factor
		B.setBalanceFator(height(B.getLc())-height(B.getRc()));
		return B;
	}
	
	public AVLNode RR(AVLNode A){
		AVLNode B = A.getRc();	//create B copied from right child of A
		A.setRc(B.getLc());		//Set right child of A from left child of B
		B.setLc(A);				//Set A is the left child of B
		A.setHeight(Math.max(height(A.getLc()),height(A.getRc()))+1);	//Update Height
		B.setHeight(Math.max(height(B.getLc()),height(B.getRc()))+1);
		A.setBalanceFator(height(A.getLc())-height(A.getRc()));			//Update Balance Factor
		B.setBalanceFator(height(B.getLc())-height(B.getRc()));
		return B;
	}
	
	//Print to console in inorder
	public int inOrder(AVLNode node){
		if(node == null)
			return 1;	//When reached the leaf, return 1
		inOrder(node.getLc());	//Traverse left child
		System.out.println(node.getKey() + " " + node.getValue());	//Print current node
		inOrder(node.getRc());	//traverse right child
		return 1;
	}
	
	//Print to Arraylist in inorder. That Array list of then written to file
	public int inOrderWrite(AVLNode node, ArrayList<String> output){
		if(node == null)
			return 1;			//When reached the leaf, return 1
		inOrderWrite(node.getLc(),output);	//Traverse left child
		output.add(node.getValue()+" ");	//Print current node
		inOrderWrite(node.getRc(),output);	//traverse right child
		return 1;
	}
	
	//Print to console in postorder. Same as Inorder, except prints as L-R-N
	public int postOrder(AVLNode node){
		if(node == null)
			return 1;
		postOrder(node.getLc());
		postOrder(node.getRc());
		System.out.println(node.getKey() + " " + node.getValue());
		return 1;
	}
	
	//Print to ArrayList in postorder. Same as Inorder, except prints as L-R-N
	public int postOrderWrite(AVLNode node, ArrayList<String> output){
		if(node == null)
			return 1;
		postOrderWrite(node.getLc(),output);
		postOrderWrite(node.getRc(),output);
		output.add(node.getValue()+" ");
		return 1;
	}
	
}



