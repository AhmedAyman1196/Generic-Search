import java.util.Comparator;

public class DFS implements Comparator<Node> {
	@Override
	public int compare(Node node1, Node node2) {
		return node2.depth - node1.depth;
	}
}
