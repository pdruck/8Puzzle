package assignment1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	// calls the menu
	public static void main(String[] args) throws IOException {
		menu();
	}
	
	// creates a menu / takes input
	public static void menu() throws IOException {
		boolean allDone = false;			// boolean for the entire menu
		boolean diffDone = false;			// boolean for the first part of the menu
		boolean algDone = false;			// boolean for the second part of the menu
		boolean quit = false;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Game game = null;
		String choice;
		do {
			do {
				System.out.println("8-PUZZLE - Please select a difficulty:");
				System.out.println("\t1) Easy\n" +
								   "\t2) Medium\n" +
								   "\t3) Hard\n" +
								   "\t4) Quit");
				choice = reader.readLine().trim();
				if(choice.equals("1") || choice.toUpperCase().equals("EASY") || choice.toUpperCase().equals("E")) {
					game = new Game(new int[][]{{1, 3, 4},{8, 6, 2},{7, 0, 5}});
					choice = "EASY";
					diffDone = true;
				}
				else if(choice.equals("2") || choice.toUpperCase().equals("MEDIUM") || choice.toUpperCase().equals("M")) {
					game = new Game(new int[][]{{2, 8, 1},{0, 4, 3},{7, 6, 5}});
					choice = "MEDIUM";
					diffDone = true;
				}
				else if(choice.equals("3") || choice.toUpperCase().equals("HARD") || choice.toUpperCase().equals("H")) {
					game = new Game(new int[][]{{5, 6, 7},{4, 0, 8},{3, 2, 1}});
					choice = "HARD";
					diffDone = true;
				}
				else if (choice.equals("4") || choice.toUpperCase().equals("QUIT") || choice.toUpperCase().equals("Q")) {
					quit = true;
				}
			} while(!diffDone && !quit);
			
			if (quit) {									// break if user chose to quit
				break;
			}
			
			do {
				System.out.println("8-PUZZLE (" + choice + ") - Please select a search algorithm:");
				System.out.println("\t1) Breadth-First Search (BFS)\n" +
								   "\t2) Depth-First Search (DFS)\n" +
								   "\t3) Uniform-Cost Search (UCS)\n" +
								   "\t4) Best-First Search (BEST)\n" +
								   "\t5) A*1 Search (A1)\n" +
								   "\t6) A*2 Search (A2)\n" +
								   "\t7) Quit");
				choice = reader.readLine().trim();
				if(choice.equals("1") || choice.toUpperCase().equals("BREADTH-FIRST SEARCH")
									  || choice.toUpperCase().equals("BREADTH FIRST SEARCH")
									  || choice.toUpperCase().equals("BFS")) {
					System.out.println("Performing BREADTH-FIRST SEARCH...\n");
					Game.print(game.bfs());
					algDone = true;
				}
				else if(choice.equals("2") || choice.toUpperCase().equals("DEPTH-FIRST SEARCH")
						  				   || choice.toUpperCase().equals("DEPTH FIRST SEARCH")
						  				   || choice.toUpperCase().equals("DFS")) {
					System.out.println("Performing DEPTH-FIRST SEARCH...\n");
					Game.print(game.dfs());
					algDone = true;
				}
				else if(choice.equals("3") || choice.toUpperCase().equals("UNIFORM-COST SEARCH")
						  				   || choice.toUpperCase().equals("UNIFORM COST SEARCH")
						  				   || choice.toUpperCase().equals("UCS")) {
					System.out.println("Performing UNIFORM-COST SEARCH...\n");
					Game.print(game.ucs());
					algDone = true;
				}
				else if(choice.equals("4") || choice.toUpperCase().equals("BEST-FIRST SEARCH")
		  				   				   || choice.toUpperCase().equals("BEST FIRST SEARCH")
		  				   				   || choice.toUpperCase().equals("BEST")) {
					System.out.println("Performing BEST-FIRST SEARCH...\n");
					Game.print(game.bestfs());
					algDone = true;
				}
				else if(choice.equals("5") || choice.toUpperCase().equals("A*1 SEARCH")
										   || choice.toUpperCase().equals("A1 SEARCH")
										   || choice.toUpperCase().equals("A1")) {
					System.out.println("Performing A*1 SEARCH...\n");
					Game.print(game.a1());
					algDone = true;
				}
				else if(choice.equals("6") || choice.toUpperCase().equals("A*2 SEARCH")
										   || choice.toUpperCase().equals("A2 SEARCH")
										   || choice.toUpperCase().equals("A2")) {
					System.out.println("Performing A*2 SEARCH...\n");
					Game.print(game.a2());
					algDone = true;
				}
				else if(choice.equals("7") || choice.toUpperCase().equals("Q")
										   || choice.toUpperCase().equals("QUIT")) {
					quit = true;
				}
			} while(!algDone && !quit);
			
			if (quit) {									// break if user chose to quit
				break;
			}
			
			System.out.println("\nAgain? (Y/N)");
			choice = reader.readLine().trim();
			if (choice.toUpperCase().equals("Y") || choice.toUpperCase().equals("YES")) {
			}
			else if (choice.toUpperCase().equals("N") || choice.toUpperCase().equals("NO")) {
				allDone = true;
			}
		} while(!allDone);
		System.out.println("Done.");
		reader.close();
	}
}
