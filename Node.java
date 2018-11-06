package assignment1;

import java.util.ArrayList;
import java.util.Collections;

// Node class that represents a single Node in a tree
public class Node {
	State state;				// the state of this node
	Node parent;				// the parent of this node
	Action action;				// the Action that was taken to get to this Node
	int depth;					// the depth of this Node in the tree
	int cost;					// the cost of the move that was taken to get to this Node
	
	// constructor for Nodes, takes in a state to initialize as a parameter
	public Node(State state) {
		this.state = state;
		action = state.getPreviousAction();
	}
	
	// isGoal function for Nodes
	public boolean isGoal() {
		if (state.isGoal()) {
			return true;
		}
		return false;
	}
	
	// finds and returns the path from the root Node to this Node by backtracking
	public ArrayList<Node> findPath(Node root) {
		Node n = this;
		ArrayList<Node> path = new ArrayList<Node>();
		path.add(n);
		while (!n.parent.equals(root)) {
			path.add(n.parent);
			n = n.parent;
		}
		path.add(root);
		Collections.reverse(path);
		return path;
	}
	
	// calculates the entire path cost of the given ArrayList of Nodes
	public int totalPathCost(ArrayList<Node> path) {
		int totalCost = 0;
		for (Node n : path) {
			totalCost += n.cost;
		}
		return totalCost;
	}
	
	// calculates the entire path cost from this Node to the root
	public int totalPathCost() {
		int totalCost = 0;
		Node n = this;
		while (n.parent != null) {
			totalCost += n.cost;
			n = n.parent;
		}
		return totalCost;	
	}
	
	// sets the parent of this Node, the cost to get to this Node, and the depth
	public void setParentInformation(Node n) {
		this.parent = n;
		this.setPreviousMoveCost();
		if (this.parent != null)  {
			this.setDepth(this.parent.depth + 1);
		}
	}
	
	// h1: returns the number of misplaced tiles in a Node's State
	public int numMisplacedTiles() {
		int[][] goalGrid = State.getGoalState().getGrid();
		int[][] nGrid = this.state.getGrid();
		int count = 0;
		for (int i = 0; i < nGrid.length; i++) {
			for (int j = 0; j < nGrid[i].length; j++) {
				if (nGrid[i][j] != goalGrid[i][j]) {
					count++;
				}
			}
		}
		return count;
	}
	
	// h2: returns sum of the Manhattan distance between all tiles and correct tiles
	public int sumManhattanDistances() {
		int[][] goalGrid = State.getGoalState().getGrid();
		int[][] nGrid = this.state.getGrid();
		int sum = 0;
		for (int i = 0; i < nGrid.length; i++) {							// this state loop
			for (int j = 0; j < nGrid[i].length; j++) {
				if (nGrid[i][j] != goalGrid[i][j]) {						// if not already in correct spot
					for (int a = 0; a < goalGrid.length; a++) {				// Goal state loop
						for (int b = 0; b < goalGrid[a].length; b++) {
							if (nGrid[i][j] == goalGrid[a][b]) {
								sum += (a - i) + (b - j);					// add Manhattan distance
							}
						}
					}
				}
			}
		}
		
		return sum;
	}
	
	// successors function for Nodes (returns a List of adjacent Nodes)
	public ArrayList<Node> successors() {
		ArrayList<Node> adj = new ArrayList<Node>();
		for (State s : state.successors()) {
			Node n = new Node(s);
			adj.add(n);
		}
		return adj;
	}
	
	// setter for parent variable
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	// getter for parent variable
	public Node getParent() {
		return parent;
	}
	
	// setter for depth variable
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	// getter for depth variable
	public int getDepth() {
		return depth;
	}
	
	// returns this Node's action as a String
	public String getAction() {
		String s = "";
		if (action == Action.UP) {
			s = "UP";
		}
		else if (action == Action.DOWN) {
			s = "DOWN";
		}
		else if (action == Action.LEFT) {
			s = "LEFT";
		}
		else if (action == Action.RIGHT) {
			s = "RIGHT";
		}
		return s;
	}
	
	// sets the previous move cost, determined by the possible move costs of the parent state
	public void setPreviousMoveCost() {
		if (this.action == Action.UP) {
			cost = parent.state.getMoveCosts()[0];
		}
		else if (this.action == Action.DOWN) {
			cost = parent.state.getMoveCosts()[1];
		}
		else if (this.action == Action.LEFT) {
			cost = parent.state.getMoveCosts()[2];
		}
		else if (this.action == Action.RIGHT) {
			cost = parent.state.getMoveCosts()[3];
		}
		else {
			cost = 0;
		}
	}
	
	// getter for state object
	public State getState() {
		return state;
	}
	
	// overriding the hashCode method
	public int hashCode() {
		return state.hashCode();
	}

	// overriding the equals() method
	public boolean equals(Object o) {
	    if (!(o instanceof Node)) return false;
		Node n = (Node) o;
		if (this.state.equals(n.state))
			return true;
		return false;
	}
	
	// overriding the toString() method
	public String toString() {
		return state.toString();
	}
}
