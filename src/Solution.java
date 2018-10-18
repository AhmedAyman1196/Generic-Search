import java.util.ArrayList;

public class Solution {

	ArrayList<Operator> operators;
	ArrayList<Node> nodes;
	int cost;
	int expandedNodes;

	/**
	 * Constructor
	 * 
	 * @param operators
	 * @param nodes
	 * @param cost
	 * @param expandedNodes
	 */
	public Solution(ArrayList<Operator> operators, ArrayList<Node> nodes, int cost, int expandedNodes) {
		this.operators = operators;
		this.nodes = nodes;
		this.cost = cost;
		this.expandedNodes = expandedNodes;
	}
	
	@Override
	public String toString() {
		return "Cost: " + cost + ", Expaned Nodes: " + expandedNodes;
	}
}
