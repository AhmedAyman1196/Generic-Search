import java.util.Comparator;

public class A_Star implements Comparator<Node> {

    int function_idx;

    public A_Star(int function_idx) {
        this.function_idx = function_idx;
    }

    /**
     * comparing nodes according to the heuristic function + the cost
     */
    @Override
    public int compare(Node node1, Node node2) {
        if(function_idx == 1) {
            return (Greedy.h1(node1) + node1.path_cost) - (Greedy.h1(node2) + node2.path_cost);
        }
        else{
            return (Greedy.h2(node1) + node1.path_cost) - (Greedy.h2(node2) + node2.path_cost);
        }
    }

}
