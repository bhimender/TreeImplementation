class AVLNode {
	private int key;	//key of node
	private int value;	//value attached to key
	private AVLNode rc;	//right child
	private AVLNode lc;	//left child
	private int balanceFactor;	//balance factor node
	private int height;	//height of node
	
	//Constructor. takes parameters (key,value)
	public AVLNode(int a, int val){
		key = a;
		value = val;
		rc = null;
		lc = null;
		balanceFactor = 0;
		height = 1;
	}
	
	//Getter Setters
	public void setKey(int k){
		key = k;
	}
	
	public int getKey(){
		return key;
	}
	
	public void setValue(int val){
		value = val;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setRc(AVLNode n){
		rc = n;
	}
	
	public AVLNode getRc(){
		return rc;
	}
	
	public void setLc(AVLNode n){
		lc = n;
	}
	
	public AVLNode getLc(){
		return lc;
	}
	
	public void setBalanceFator(int a){
		balanceFactor = a;
	}
	
	public int getBalanceFator(){
		return balanceFactor;
	}
	
	public void setHeight(int h){
		height = h;
	}
	
	public int getHeight(){
		return height;
	}
}