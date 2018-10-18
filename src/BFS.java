import java.util.Comparator;

public class BFS implements Comparator<Node> {
	
	/**
	 * Compare the nodes using the depth, higher priority to lower depth
	 */
	@Override
	public int compare(Node node1, Node node2) {
		return node1.depth - node2.depth;
	}
}
