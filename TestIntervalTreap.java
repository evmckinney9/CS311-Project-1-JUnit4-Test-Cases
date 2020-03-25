
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * Junit4 for CS311 PROJ1.
 * Currently, only tests for general functionality of insert, search.
 * To do: test for correct height, imax, delete, EC
 * 
 * @author Evan McKinney
 *
 */
public class TestIntervalTreap {

	private IntervalTreap it1;
	private ArrayList<Interval> TP;
	private ArrayList<Interval> TN;
	private Scanner sc;

	@Before
	public void setUp() throws Exception {
		it1 = new IntervalTreap();
		TP = new ArrayList<Interval>();
		TN = new ArrayList<Interval>();
	}

	@After
	public void tearDown() throws Exception {
		it1 = null;
		sc.close();
		sc = null;
		TP = null;
		TN = null;
	}

	@Test
	public void testMini() {
		scanConstruct("src/mini_1.txt");
		for (Interval i : TP) {
			assertNotNull(it1.intervalSearch(i));
		}
		for (Interval j : TN) {
			assertNull(it1.intervalSearch(j));
		}
		testTreapStructure(it1);
	}

	@Test
	public void testSmall() {
		scanConstruct("src/small_1.txt");
		for (Interval i : TP) {
			assertNotNull(it1.intervalSearch(i));
		}
		for (Interval j : TN) {
			assertNull(it1.intervalSearch(j));
		}
		testTreapStructure(it1);
	}

	@Test
	public void testMedium() {
		scanConstruct("src/medium_1.txt");
		for (Interval i : TP) {
			assertNotNull(it1.intervalSearch(i));
		}
		for (Interval j : TN) {
			assertNull(it1.intervalSearch(j));
		}
		testTreapStructure(it1);
	}

	@Test
	public void testLarge() {
		scanConstruct("src/large_1.txt");
		for (Interval i : TP) {
			assertNotNull(it1.intervalSearch(i));
		}
		for (Interval j : TN) {
			assertNull(it1.intervalSearch(j));
		}
		testTreapStructure(it1);
	}
	
	private void testTreapStructure(IntervalTreap it0) {
		//Do an InOrder Traversal and append the nodes into an array
		ArrayList<Node> inOrder = new ArrayList<Node>();
		int index = 0;
		inOrder(it0.getRoot(), inOrder, index);
		
		//Check if the array is sorted. If it is not sorted, it's not a valid treap. 
		for (int k =0; k < inOrder.size()-1; k++) {
			if (inOrder.get(k).getInterv().getLow() > inOrder.get(k+1).getInterv().getLow()) {
				fail("failed treap's BST property!");
			}
		}
	}
	
	private void inOrder(Node node, ArrayList<Node> array, int index){
	    if(node == null){  
	       return;
	    }
	    inOrder(node.getLeft(), array, index);
	    array.add(node);
	    
	    //As you visit each node, check for the heap property.
	    if (node.getParent()!=null && node.getPriority() < node.getParent().getPriority()) {
	    	fail("failed treap's min-heap property!");
	    }
	    
	    inOrder(node.getRight(), array, index);
	}
	
	
	
	
	
	private void scanConstruct(String fn) {
		File f = new File(fn);
		String line;
		String [] split;
		try {
			sc = new Scanner(f);
			sc.nextLine(); //skip first line "TP"
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (line.equals("TN")) break;
				split = line.split(" ");
				TP.add(new Interval(Integer.parseInt(split[0]),Integer.parseInt(split[1])));
			}
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (line.equals("Intervals")) break;
				split = line.split(" ");
				TN.add(new Interval(Integer.parseInt(split[0]),Integer.parseInt(split[1])));
			}
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				split = line.split(" ");
				it1.intervalInsert(new Node(new Interval(Integer.parseInt(split[0]),Integer.parseInt(split[1]))));
			}
		} catch (FileNotFoundException e) {
			fail("File not found exception");
		}

	}
}
