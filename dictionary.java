import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;
import java.io.FileWriter;

public class dictionary {
	
	//Function to write to file
	public static void writeToFile(String filename, ArrayList<String> data) throws IOException{
		PrintWriter pw = new PrintWriter(new FileWriter(new File(".").getCanonicalPath()+"/"+filename+".out"));
		for(int i=0;i<data.size();i++){
			if(data.get(i) == "NL"){
				pw.println();
			}else{
				pw.print(data.get(i));
			}
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {

		int n = 1000000;	//number of inputs
		int s = 11;			//Hash size
		int order = 25;		//Order for Btrees
		long start = 0;		//Timer
		long stop = 0;		//Timer
		int iterations = 0;	//iterations count
		int itrCount = 10;	//number of iterations
		String run = "cmd";	//switch between command line and IDE
		String mode = "-r";	//mode select for IDE Mode
		String inputFile = "/home/bhimender/Ubuntu One/UFL Docs/Courses 1/ADS/SPsFiles/Input.txt";	//Inputfile for IDE Mode
		
		//For command line mode
		if(run == "cmd"){
			mode = args[0];	//read mode
			if(mode.equals("-r")){
				s = Integer.parseInt(args[1]);	//read s
				order = Integer.parseInt(args[2]);	//read order
			}else if(mode.equals("-u")){
				inputFile = args[1];	//read filename
			}else{
				System.out.println("Invalid Mode");
				System.exit(0);
			}
		}
		
		//random mode		
		if(mode.equals("-r")){
			long[][] times = new long[12][itrCount]; 	//Records time .in order AVL,AVLHash,RB,RBHash,BTree,BtreeHash each for insert and search
			double[] timesAggr = new double[12];		//Aggregate time
			Arrays.fill(timesAggr, 0.0);
			String[] timeNotations = {"AVL Insert Time","AVL Search Time","AVLHash Insert Time","AVLHash Search Time","RedBlackTree Insert Time","RedBlackTree Search Time","RedBlackTreeHash Insert Time","RedBlackTreeHash Search Time","BTree Insert Time","BTree Search Time","BTreeHash Insert Time","BTreeHash Search Time"};
			
			//Generate n entries
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for(int i=0;i<n;i++)
				arr.add(i);
			for(iterations =0;iterations<itrCount;iterations++){
			Collections.shuffle(arr);			//Shuffle the entries
			System.out.println("Interation : " + iterations);
			
			//Initialise
			AVLNode node = null;
			AVLNode AVLres = null;
			AVL AVLtree = new AVL();
			
			AVLHash AVLHashObj = new AVLHash(s);
			AVLNode AVLHashRes = null;
			
			TreeMap<Integer,Integer> RedBlackObj = new TreeMap<Integer,Integer>();
			
			RedBlackHash RedBlackHashObj = new RedBlackHash(s);
			
			BTree BT = new BTree();
			BNode Bres = null;
			
			BHash BHashObj = new BHash(s,order);
			
			//Start Inserting and checking time
			//AVL
			start = System.currentTimeMillis();
			for(int i=0;i<n;i++)
				node = AVLtree.add(arr.get(i),arr.get(i)*2,node);	//insert
			stop = System.currentTimeMillis();
			times[0][iterations] = stop - start;	//note time
			
			start = System.currentTimeMillis();
			for(int i=0;i<n;i++)
				AVLres = AVLtree.search(arr.get(i), node);		//run search
			stop = System.currentTimeMillis();
			times[1][iterations] = stop - start;	//note time
			
			//AVL Hash
			start = System.currentTimeMillis();
			for(int i=0;i<n;i++){
				AVLHashObj.add(arr.get(i),arr.get(i)*2);
			}
			stop = System.currentTimeMillis();
			times[2][iterations] = stop - start;
			
			start = System.currentTimeMillis();
			for(int i=0;i<n;i++)
				AVLHashRes = AVLHashObj.search(arr.get(i));
			stop = System.currentTimeMillis();
			times[3][iterations] = stop - start;
			
			
			//RedBlack
			int RBres = 0;
			start = System.currentTimeMillis();
			for(int i=0;i<n;i++){
				RedBlackObj.put(arr.get(i),arr.get(i)*2);
			}
			stop = System.currentTimeMillis();
			times[4][iterations] = stop - start;
			
			start = System.currentTimeMillis();
			for(int i=0;i<n;i++)
			RedBlackObj.get(arr.get(i));
			stop = System.currentTimeMillis();
			times[5][iterations] = stop - start;
			
			
			//RedBlackHash
			
			int RBHashRes = 0;
			
			start = System.currentTimeMillis();
			for(int i = 1;i<n;i++){
				RedBlackHashObj.add(arr.get(i),arr.get(i)*2);
			}
			stop = System.currentTimeMillis();
			times[6][iterations] = stop - start;
			
			start = System.currentTimeMillis();
			for(int i = 1;i<n;i++)
				RedBlackHashObj.search(arr.get(i));
			stop = System.currentTimeMillis();
			times[7][iterations] = stop - start;
			
			
			//BTree
			start = System.currentTimeMillis();
			BNode B = new BNode(arr.get(0),arr.get(0)*2,order);
			for(int i=1;i<n;i++){
				B = BT.add(arr.get(i),arr.get(i)*2,B);
			}
			stop = System.currentTimeMillis();
			times[8][iterations] = stop - start;
			
			start = System.currentTimeMillis();
			for(int i=1;i<n;i++)
			Bres = BT.search(arr.get(i), B);
			stop = System.currentTimeMillis();
			times[9][iterations] = stop - start;
			
			//BTreeHash
			start = System.currentTimeMillis();
			for(int i=0;i<n;i++){
				BHashObj.add(arr.get(i),arr.get(i)*2);
			}
			stop = System.currentTimeMillis();
			times[10][iterations] = stop - start;
			
			start = System.currentTimeMillis();
			for(int i=0;i<n;i++)
				BHashObj.search(arr.get(i));
			stop = System.currentTimeMillis();
			times[11][iterations] = stop - start;
			
			
	}
			//Calculate Aggregate for the 10 iterations
			for(int i=0;i<12;i++){
				for(int j=0;j<itrCount;j++){
					timesAggr[i] += times[i][j]; 
				}
				timesAggr[i] = timesAggr[i]/itrCount;
			}
			
			//Print time
			for(int i=0;i<12;i++)
				System.out.println(timeNotations[i] + " : " + timesAggr[i] + "ms");
		
		}else if(mode.equals("-u")){		//User Mode
			
			//Initialise
			s= 3;
			order = 3;
			AVLNode node = null;
			AVL AVLtree = new AVL();
			
			AVLHash AVLHashObj = new AVLHash(s);
			
			BTree BT = new BTree();
			
			BHash BHashObj = new BHash(s,order);
			
			//Read File
			
			  FileInputStream fstream = new FileInputStream(inputFile);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  strLine = br.readLine();		//readline
			  int numInputs = Integer.parseInt(strLine);
			  String[] KVString = br.readLine().split(" ");		//split and parse input
			  int key = Integer.parseInt(KVString[0]);
			  int val = Integer.parseInt(KVString[1]);
			  
			  //Add first entry
			  node = AVLtree.add(key,val,node);
			  AVLHashObj.add(key,val);
			  BNode B = new BNode(key,val,order);
			  BHashObj.add(key,val);
			  
			  //Read File Line By Line and insert into respective trees
			  while ((strLine = br.readLine()) != null)   {
				  KVString = strLine.split(" ");
				  key = Integer.parseInt(KVString[0]);
				  val = Integer.parseInt(KVString[1]);
				  
				  node = AVLtree.add(key,val,node);
				  AVLHashObj.add(key,val);
				  B= BT.add(key,val,B);
				  BHashObj.add(key,val);
			  }
			  
			  //Close the input stream
			  in.close();
			  
			  //Use ArrayLists to save output for traversals
			  ArrayList<String> AVL_inorder = new ArrayList<String>();
			  ArrayList<String> AVL_postorder = new ArrayList<String>();
			  ArrayList<String> AVLHash_inorder = new ArrayList<String>();
			  ArrayList<String> BTree_sorted = new ArrayList<String>();
			  ArrayList<String> BTree_level = new ArrayList<String>();
			  ArrayList<String> BTreeHash_level = new ArrayList<String>();
			  
			  AVLtree.inOrderWrite(node,AVL_inorder);
			  dictionary.writeToFile("AVL_inorder",AVL_inorder);	//print the traversal
			  
			  AVLtree.postOrderWrite(node,AVL_postorder);
			  dictionary.writeToFile("AVL_postorder",AVL_postorder);
			  
			  AVLHashObj.inOrderWrite(AVLHash_inorder);
			  dictionary.writeToFile("AVLHash_inorder",AVLHash_inorder);
			  
			  BT.sortedWrite(B,BTree_sorted);
			  dictionary.writeToFile("BTree_sorted",BTree_sorted);
			  
			  BT.levelWrite(B,BTree_level);
			  dictionary.writeToFile("BTree_level",BTree_level);
			  
			  BHashObj.levelWrite(BTreeHash_level);
			  dictionary.writeToFile("BTreeHash_level",BTreeHash_level);
			  
			  System.out.println("Done");

		}
}
}
