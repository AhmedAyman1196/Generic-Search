import java.util.ArrayList;
import java.util.Collections;

public class Node {

	State state;
	Node parent;
	Operator operator;
	int depth;
	int path_cost;

	/**
	 * constructor
	 * 
	 * @param state
	 * @param parent
	 * @param operator
	 * @param depth
	 * @param path_cost
	 */
	public Node(State state, Node parent, Operator operator, int depth, int path_cost) {
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		this.depth = depth;
		this.path_cost = path_cost;
	}

	/**
	 * a method to get all the operators applied from the initial state in order to
	 * reach this node.
	 * 
	 * @return
	 */
	public ArrayList<Operator> get_operators() {
		ArrayList<Operator> operators = new ArrayList<>();
		Node current_node = this;
		while (current_node.operator != null) {
			operators.add(current_node.operator);
			current_node = current_node.parent;
		}
		Collections.reverse(operators);
		return operators;
	}

	/**
	 * a method to get the sequence of nodes to reach this node, starting from the
	 * root.
	 * 
	 * @return
	 */
	public ArrayList<Node> get_nodes() {
		ArrayList<Node> nodes = new ArrayList<>();
		Node current_node = this;
		while (current_node.operator != null) {
			nodes.add(current_node);
			current_node = current_node.parent;
		}
		nodes.add(current_node);
		Collections.reverse(nodes);
		return nodes;
	}
}
