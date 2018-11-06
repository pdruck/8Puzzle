package assignment1;

import java.util.Comparator;

//Comparator for f(n) (g + h1)
public class F1Comparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		return (o1.totalPathCost() + o1.numMisplacedTiles()) - (o2.totalPathCost() + o2.numMisplacedTiles());
	}
}
