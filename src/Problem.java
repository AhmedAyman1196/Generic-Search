import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class Problem {

    /**
     * A set of operators, or actions, available to the agent.
     */
    ArrayList<Operator> operators;

    /**
     * An initial state.
     */
    State initial_state;

    /**
     * A state space: the set of states reachable from the initial state by any
     * sequence of actions.
     */
    ArrayList<State> state_space;

    private static final int infinity = (int) 1e3;

    /**
     * to be implemented in each problem that extends the class.
     *
     * @param state
     * @return
     */
    public abstract boolean goal_test(State state);

    /**
     * to be implemented in each problem that extends the class.
     *
     * @param cur_node
     * @param operator
     * @return
     */
    public abstract int path_cost(Node cur_node, Operator operator);

    /**
     * general search procedure.
     *
     * @param queuing_function
     * @return
     */
    public Solution search(Comparator<Node> queuing_function) {
        // the queue used to sort the nodes according the search strategy
        PriorityQueue<Node> nodes = new PriorityQueue<Node>(queuing_function);
        Node node = new Node(initial_state, null, null, 0, 0);

        nodes.add(node);
        int expanded_nodes = 0;
        while (!nodes.isEmpty()) {
            Node current_node = nodes.remove();
            expanded_nodes++;

            // if the goal was reached return the solution
            if (goal_test(current_node.state))
                return new Solution(current_node.get_operators(), current_node.get_nodes(), current_node.path_cost,
                        expanded_nodes);

            // apply all possible operators on the current node, add the resulting nodes to the queue
            for (Operator operator : operators) {
                Node next_node = operator.apply(current_node);
                if (next_node != null) {
                    // checking the depth, in case if iterative deepening
                    if (queuing_function instanceof IDS) {
                        if (next_node.depth < ((IDS) queuing_function).depth)
                            nodes.add(next_node);
                    } else
                        nodes.add(next_node);
                }
            }
        }

        if (queuing_function instanceof IDS && ((IDS) queuing_function).depth < infinity) {
            IDS qf = (IDS) queuing_function;
            qf.depth++;
            return search(qf);
        }
        return null;
    }


    /**
     * given a state return the possible states from this cell
     *
     * @param state
     * @return
     */
    ArrayList<State> possibleStates(State state) {
        ArrayList<State> possibleStates = new ArrayList<State>();
        for (Operator operator : operators) {
            Node node = new Node(state, null, null, 0, 0);
            // add the resulting state to the
            possibleStates.add(operator.apply(node).state);
        }
        return possibleStates;

    }


}
