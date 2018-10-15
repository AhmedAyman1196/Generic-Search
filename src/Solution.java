import java.util.ArrayList;

public class Solution {

	ArrayList<Operator> operators;
	ArrayList<Node> nodes;
	int cost;
	int expandedNodes;

	public Solution(ArrayList<Operator> operators, ArrayList<Node> nodes, int cost, int expandedNodes) {
		this.operators = operators;
		this.nodes = nodes;
		this.cost = cost;
		this.expandedNodes = expandedNodes;
	}
}
