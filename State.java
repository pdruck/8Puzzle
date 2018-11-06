package assignment1;

import java.util.Arrays;
import java.util.ArrayList;

// represents a game State
public class State {
	int[][] grid = new int[3][3];			// a 9-by-9 8-puzzle grid - use 0 for missing tile
	int[] moveCosts = new int[4];			// cost of moves from this State (0 if impossible move)
											// [ upCost, downCost, leftCost, rightCost ]
	Action previousAction;					// the Action that the previous State took to get to this State
	
	// constructor for a State - takes in a grid as a parameter
	public State(int[][] grid) {
		this.grid = grid;
		findMoveCosts();
		previousAction = null;
	}
	
	// default constructor
	public State() {
	}
	
	// copy constructor
	public State(State state) {
		for (int i = 0; i < state.grid.length; i++) {
			for (int j = 0; j < state.grid[i].length; j++) {
				this.grid[i][j] = state.grid[i][j];
			}
		}
		state.findMoveCosts();
	}
	
	// swaps 2 values in the given array
	private void swap(int[][] arr, int row1, int col1, int row2, int col2) {
		int temp = arr[row1][col1];
		arr[row1][col1] = arr[row2][col2];
		arr[row2][col2] = temp;
	}
	
	// reflects the changes of an UP move to this State
	// returns the resulting State after the move
	private State moveUp() {
		State nextState = new State(this);
		nextState.previousAction = Action.UP;
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col] == 0 && row == 2) {
					return null; 			// impossible move
				}
				else if (grid[row][col] == 0 && row != 2) {
					nextState.swap(nextState.grid, row, col, row+1, col);
					return nextState;
				}
			}
		}
		return null;
	}
	
	// reflects the changes of a DOWN move to this State
	// returns the resulting State after the move
	private State moveDown() {
		State nextState = new State(this);
		nextState.previousAction = Action.DOWN;
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col] == 0 && row == 0) {
					return null; 			// impossible move
				}
				else if (grid[row][col] == 0 && row != 0) {
					nextState.swap(nextState.grid, row, col, row-1, col);
					return nextState;
				}
			}
		}
		return null;
	}
	
	// reflects the changes of a LEFT move to this State
	// returns the resulting State after the move
	private State moveLeft() {
		State nextState = new State(this);
		nextState.previousAction = Action.LEFT;
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col] == 0 && col == 2) {
					return null; 			// impossible move
				}
				else if (grid[row][col] == 0 && col != 2) {
					nextState.swap(nextState.grid, row, col, row, col+1);
					return nextState;
				}
			}
		}
		return null;
	}
	
	// reflects the changes of a RIGHT move to this State
	// returns the resulting State after the move
	private State moveRight() {
		State nextState = new State(this);
		nextState.previousAction = Action.RIGHT;
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col] == 0 && col == 0) {
						return null; 			// impossible move
				}
				else if (grid[row][col] == 0 && col != 0) {
					nextState.swap(nextState.grid, row, col, row, col-1);
					return nextState;
				}
			}
		}
		return null;
	}
	
	// populates and returns the moveCosts array
	private void findMoveCosts() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 0) {
					if (i+1 < 3) {
						moveCosts[0] = grid[i+1][j];
					}
					if (i-1 >= 0) {
						moveCosts[1] = grid[i-1][j];
					}
					if (j+1 < 3) {
						moveCosts[2] = grid[i][j+1];
					}
					if (j-1 >= 0) {
						moveCosts[3] = grid[i][j-1];
					}
				}
			}
		}
	}
	
	// returns the cost it took to get to this state
	public int getPreviousActionCost() {
		if (this.previousAction == Action.UP) {
			return moveCosts[0];
		}
		else if (this.previousAction == Action.DOWN) {
			return moveCosts[1];
		}
		else if (this.previousAction == Action.LEFT) {
			return moveCosts[2];
		}
		else if (this.previousAction == Action.RIGHT) {
			return moveCosts[3];
		}
		else {
			return 0;
		}
	}
	
	public Action getPreviousAction() {
		return previousAction;
	}
	
	// successors function - finds all possible states you can reach from this State
	public ArrayList<State> successors() {
		ArrayList<State> adj = new ArrayList<State>();
		State up = this.moveUp();
		State down = this.moveDown();
		State left = this.moveLeft();
		State right = this.moveRight();
		if (up != null) {
			adj.add(up);
		}
		if (down != null) {
			adj.add(down);
		}
		if (left != null) {
			adj.add(left);
		}
		if (right != null) {
			adj.add(right);
		}
		return adj;
	}
	
	// checks if this State is equal to the goal state
	public boolean isGoal() {
		if (this.equals(getGoalState())) {
			return true;
		}
		return false;
	}
	
	// returns the goal state
	public static State getGoalState() {
		return new State(new int[][]{{1, 2, 3},{8, 0, 4},{7, 6, 5}});
	}
	
	// getter for the moveCosts variable
	public int[] getMoveCosts() {
		return moveCosts;
	}
	
	// getting for the grid variable
	public int[][] getGrid() {
		return grid;
	}
	
	// overridden hashCode() method for States
	public int hashCode() {
		int hash = 1;
		for (int i = 1; i <= grid.length; i++) {
			for (int j = 1; j <= grid[i-1].length; j++) {
				hash = hash + (i * j * grid[i-1][j-1]);
			}
		}
		return hash;
	}
	
	// compares two States for equality
	public boolean equals(Object o) {
	    if (!(o instanceof State)) {
	    	return false;
	    }
		State state = (State) o;
		if (Arrays.deepEquals(this.grid, state.grid)) {
			return true;
		}
		return false;
	}
	
	// overridden toString() method for States
	public String toString() {
		String s = "";
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (count % 3 == 0 && count != 0) {
					s += " ";
				}
				s += grid[i][j];
				count++;
			}
		}
		return s;
	}
}
