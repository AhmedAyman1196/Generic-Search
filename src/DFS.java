import java.util.Comparator;

public class DFS implements Comparator<Node> {

	/**
	 * compare the nodes using depths, high priority to higher depths
	 */
	@Override
	public int compare(Node node1, Node node2) {
		return node2.depth - node1.depth;
	}
}
