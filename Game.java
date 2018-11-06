package assignment1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Hashtable;

// a Game class that can implement different types of searches
public class Game {
	Node root;									// root Node based on the initial State
	
	// creates a game using a 3x3 grid
	public Game(int[][] initialState) {
		this.root = new Node(new State(initialState));
		this.root.setParent(null);
		this.root.setDepth(0);
	}
	
	// output function
	public static void print(ArrayList<Node> path) {
		for (Node n : path) {
			String s = n.getAction();
			if (n.getAction() == "") {
				s = "ROOT";
			}
			System.out.println(s + ",Cost = " + n.cost + ",Total Cost = " + n.totalPathCost());
			System.out.println(n);
		}
	}
	
	// BREADTH-FIRST SEARCH implementation
	public ArrayList<Node> bfs() {
		HashSet<Node> seen = new HashSet<Node>();					// for repeated state checking
		Queue<Node> q = new LinkedList<Node>();						// FIFO data structure
		HashSet<Node> qDupCheck = new HashSet<Node>();				// checks queue for duplicates
		q.add(root);
		qDupCheck.add(root);

		if (root.isGoal()) {
			return root.findPath(root);
		}
		while (!q.isEmpty()) {
			Node current = q.remove();
			qDupCheck.remove(current);
			seen.add(current);
			
			for (Node n : current.successors()) {
				if (!seen.contains(n) && !qDupCheck.contains(n)) {
					n.setParentInformation(current);
					q.add(n);
					qDupCheck.add(n);
					if (n.isGoal()) {
						return n.findPath(root);
					}
				}
			}
		}
		return null;
	}
	
	// DEPTH-FIRST SEARCH implementation
	public ArrayList<Node> dfs() {
		HashSet<Node> seen = new HashSet<Node>();					// for repeated state checking
		Stack<Node> q = new Stack<Node>();							// LIFO data structure
		HashSet<Node> qDupCheck = new HashSet<Node>();				// checks queue for duplicates
		q.push(root);
		qDupCheck.add(root);

		if (root.isGoal()) {
			return root.findPath(root);
		}
		while (!q.isEmpty()) {
			Node current = q.pop();
			qDupCheck.remove(current);
			
			for (Node n : current.successors()) {
				if (!seen.contains(n) && !qDupCheck.contains(n)) {
					seen.add(current);
					n.setParentInformation(current);
					q.push(n);
					qDupCheck.add(n);
					if (n.isGoal()) {
						return n.findPath(root);
					}
				}
			}
		}
		return null;
	}

	// UNIFORM-COST SEARCH implementation
	public ArrayList<Node> ucs() {
		Comparator<Node> comparator = new GComparator();					// Comparator for g
		PriorityQueue<Node> q = new PriorityQueue<Node>(10, comparator);	// PriorityQueue
		Hashtable<Node,Integer> qValues = new Hashtable<Node,Integer>(); 	// Hashtable for O(1) searching Nodes in the Queue
																			// < Node, totalPathCost from that Node >
		HashSet<Node> seen = new HashSet<Node>();							// HashSet keeping track of visited Nodes
		q.add(root);
		qValues.put(root, root.totalPathCost());
		
		while(!q.isEmpty()) {
			Node current = q.poll();
			qValues.remove(current);
			
			if (current.isGoal())
				return current.findPath(root);
			seen.add(current);
			
			for (Node n : current.successors()) {
				n.setParentInformation(current);
				if (!seen.contains(n) && !qValues.containsKey(n)) {
					q.add(n);
					qValues.put(n, n.totalPathCost());
				}
				else if (qValues.containsKey(n)) {				// check values in the Queue for a smaller path cost
					int oldCost = qValues.get(n);
					int newCost = n.totalPathCost();
					if (newCost < oldCost) {
						qValues.replace(n, newCost);			// replace in the Hashtable
						q.remove(n);							// get rid of old Node with higher path cost
						q.add(n);								// replace with new Node with lower path cost
					}
				}
			}
		}
		return null;
	}	

	// BEST-FIRST SEARCH implementation
	public ArrayList<Node> bestfs() {
		Comparator<Node> comparator = new HComparator();			// Comparator for h1 (misplaced tiles)
		HashSet<Node> seen = new HashSet<Node>();					// for repeated state checking
		HashSet<Node> qDupCheck = new HashSet<Node>();				// checks queue for duplicates
		PriorityQueue<Node> q = new PriorityQueue<Node>(10, comparator);
		q.add(root);
		qDupCheck.add(root);

		if (root.isGoal()) {
			return root.findPath(root);
		}
		while (!q.isEmpty()) {
			Node current = q.poll();
			qDupCheck.remove(current);
			seen.add(current);
			
			for (Node n : current.successors()) {
				if (!seen.contains(n) && !qDupCheck.contains(n)) {
					n.setParentInformation(current);
					q.add(n);
					qDupCheck.add(n);
					if (n.isGoal()) {
						return n.findPath(root);
					}
				}
			}
		}
		return null;
	}
	
	// A*1 SEARCH implementation
	public ArrayList<Node> a1() {
		Comparator<Node> comparator = new F1Comparator();					// Comparator for f1 (g + h1)
		PriorityQueue<Node> q = new PriorityQueue<Node>(10, comparator);	// PriorityQueue
		Hashtable<Node,Integer> qValues = new Hashtable<Node,Integer>(); 	// Hashtable for O(1) searching Nodes in the Queue
																			// < Node, totalPathCost from that Node + num misplaced tiles>
		HashSet<Node> seen = new HashSet<Node>();							// HashSet keeping track of visited Nodes
		q.add(root);
		qValues.put(root, root.totalPathCost() + root.numMisplacedTiles());
		
		while(!q.isEmpty()) {
			Node current = q.poll();
			qValues.remove(current);
			
			if (current.isGoal()) {
				return current.findPath(root);
			}
			seen.add(current);
			
			for (Node n : current.successors()) {
				n.setParentInformation(current);
				if (!seen.contains(n) && !qValues.containsKey(n)) {
					q.add(n);
					qValues.put(n, n.totalPathCost() + n.numMisplacedTiles());
				}
				else if (qValues.containsKey(n)) {				// check values in the Queue for a smaller f value
					int oldCost = qValues.get(n);
					int newCost = n.totalPathCost() + n.numMisplacedTiles();
					if (newCost < oldCost) {
						qValues.replace(n, newCost);			// replace in the Hashtable
						q.remove(n);							// get rid of old Node with higher f value
						q.add(n);								// replace with new Node with lower f value
					}
				}
			}
		}
		return null;
	}
	
	
	// A*2 SEARCH implementation
	public ArrayList<Node> a2() {
		Comparator<Node> comparator = new F2Comparator();					// Comparator for f2 (g + h2)
		PriorityQueue<Node> q = new PriorityQueue<Node>(100, comparator);	// PriorityQueue
		Hashtable<Node,Integer> qValues = new Hashtable<Node,Integer>(); 	// Hashtable for O(1) searching Nodes in the Queue
																			// < Node, totalPathCost from that Node + Manhattan dist >
		HashSet<Node> seen = new HashSet<Node>();							// HashSet keeping track of visited Nodes
		q.add(root);
		qValues.put(root, root.totalPathCost() + root.sumManhattanDistances());
		
		while(!q.isEmpty()) {
			Node current = q.poll();
			qValues.remove(current);
			
			if (current.isGoal()) {
				return current.findPath(root);
			}
			seen.add(current); 
			
			for (Node n : current.successors()) {
				n.setParentInformation(current);
				if (!seen.contains(n) && !qValues.containsKey(n)) {
					q.add(n);
					qValues.put(n, n.totalPathCost() + n.sumManhattanDistances());
				}
				else if (qValues.containsKey(n)) {				// check values in the Queue for a smaller path cost
					int oldCost = qValues.get(n);
					int newCost = n.totalPathCost() + n.sumManhattanDistances();
					if (newCost < oldCost) {
						qValues.replace(n, newCost);			// replace in the Hashtable
						q.remove(n);							// get rid of old Node with higher path cost
						q.add(n);								// replace with new Node with lower path cost
					}
				}
			}
		}
		return null;
	}
}