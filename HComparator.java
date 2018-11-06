package assignment1;

import java.util.Comparator;

//Comparator for h1 (compares the number of misplaced tiles)
public class HComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		return o1.numMisplacedTiles() - o2.numMisplacedTiles();
	}
}
