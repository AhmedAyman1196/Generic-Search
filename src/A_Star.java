import java.util.Comparator;
import java.util.LinkedList;

public class A_Star implements Comparator<Node> {
    
    @Override
    public int compare(Node node1, Node node2) {
        return (Greedy.h1(node1) + node1.path_cost) - (Greedy.h1(node2) + node2.path_cost);
    }

}
