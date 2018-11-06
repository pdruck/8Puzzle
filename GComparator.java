package assignment1;

import java.util.Comparator;

//Comparator for g(n) (compares total path costs)
public class GComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		return o1.totalPathCost() - o2.totalPathCost();
	}
}