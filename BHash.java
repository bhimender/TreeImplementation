import java.util.ArrayList;


public class BHash {
	
	private int s = 5;
	public BNode[] BArray;
	private BTree Btree = new BTree();
	int order;
	
	public BHash(int sVal,int ord){
		s= sVal;
		BArray = new BNode[s];
		order = ord;
	}
	
	public void add(int a, int val){
		if(BArray[a%s]==null)
			BArray[a%s]=new BNode(a,val,order);
		else
			BArray[a%s] = Btree.add(a,val,BArray[a%s]);
	}
	
	public BNode search(int key){
		BNode node = Btree.search(key, this.BArray[key%s]);
		return node;
	}
	
	public void level(){
		for(int i=0;i<s;i++)
			Btree.level(BArray[i]);
	}
	
	public void levelWrite(ArrayList<String> output){
		for(int i=0;i<s;i++){
			Btree.levelWrite(BArray[i],output);
			output.add("NL");
		}
	}
	
	public static void main(String[] args) {
		BHash BHashObj = new BHash(5,5);
		for(int i=0;i<10000;i++){
			BHashObj.add(i,2*i);
		}
		
	
System.out.println(BHashObj.search(100001).getValue(BHashObj.search(100001).find(100001)));
	}
}
