import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class Problem {

    ArrayList<Operator> operators;
    State initial_state;
    ArrayList<State> state_space;

    public abstract boolean goal_test(State state);

    public abstract int path_cost(Node cur_node, Operator operator);

    public Solution search(Comparator<Node> queuing_function) {
        PriorityQueue<Node> nodes = new PriorityQueue<Node>(queuing_function);
        Node node = new Node(initial_state, null, null, 0, 0);

        nodes.add(node);
        int expanded_nodes = 0;
        while (!nodes.isEmpty()) {
            Node current_node = nodes.remove();
//            Main.print(current_node.state.grid);
            expanded_nodes++;
            if (goal_test(current_node.state))
                return new Solution(current_node.get_operators(), current_node.get_nodes(), current_node.path_cost, expanded_nodes);
            for (Operator operator : operators) {
                Node next_node = operator.apply(current_node);
                if (next_node != null) {
                    nodes.add(next_node);
                }
            }
        }

        return null;
    }

}
