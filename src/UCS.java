import java.util.Comparator;

public class UCS implements Comparator<Node> {
	@Override
	public int compare(Node node1, Node node2) {
		return node1.path_cost - node2.path_cost;
	}
}
