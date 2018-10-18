import java.util.Comparator;

public class IDS implements Comparator<Node> {
	int depth;

	public IDS() {
		depth = 0;
	}

	/**
	 * compare the nodes using depths, high priority to higher depths. Additionally,
	 * the max depth will be checked before calling this
	 */
	@Override
	public int compare(Node node1, Node node2) {
		return node2.depth - node1.depth;
	}
}
