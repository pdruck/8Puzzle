package assignment1;

import java.util.Comparator;

//Comparator for f(n) (g + h2)
public class F2Comparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		return (o1.totalPathCost() + o1.sumManhattanDistances()) - (o2.totalPathCost() + o2.sumManhattanDistances());
	}
}