import java.util.Comparator;
import java.util.LinkedList;

public class Greedy implements Comparator<Node> {

    // index to choose the heuristic function
    int function_idx;

    public Greedy(int function_idx) {
        this.function_idx = function_idx;
    }

    /**
     * comparing the nodes using the desired heuristic function
     */
    @Override
    public int compare(Node node1, Node node2) {
        if (function_idx == 1) {
            return h1(node1) - h1(node2);
        } else {
            return h2(node1) - h2(node2);
        }
    }

    /**
     * heuristic function in the number of connected white walkers components.
     *
     * @param node
     * @return
     */
    public static int h1(Node node) {
        return connected_components(node.state.grid) / 4;
    }

    /**
     * heuristic function in the number of white walkers divided by 4 as the agent
     * can kill in 4 directions.
     *
     * @param node
     * @return
     */
    public static int h2(Node node) {
        return white_walkers_count(node.state.grid);
    }

    /**
     * returns the number of white walkers divided by 4
     *
     * @param grid
     * @return
     */
    static int white_walkers_count(Cell[][] grid) {
        int cnt = 0;
        for (Cell[] cells : grid)
            for (Cell cell : cells)
                if (cell.cell_type == Cell_Type.WALKER)
                    cnt++;
        return cnt / 4;
    }

    /**
     * returns the number of connected white walkers components.
     *
     * @param grid
     * @return
     */
    static int connected_components(Cell[][] grid) {
        // checking connectedness in 8 directions
        int[] dx = {0, 0, 1, -1, 1, 1, -1, -1};
        int[] dy = {1, -1, 0, 0, 1, -1, 1, -1};
        boolean[][] reached = new boolean[grid.length][grid[0].length];
        int connected_components = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++) {

                // if this is a white walker, get to the white walkers connected to it and mark them as reached
                if (!reached[i][j] && grid[i][j].cell_type == Cell_Type.WALKER) {
                    connected_components++;
                    LinkedList<Cell> queue = new LinkedList<>();
                    queue.addLast(grid[i][j]);
                    reached[i][j] = true;

                    // getting the surrounding white walkers
                    while (!queue.isEmpty()) {
                        Cell current = queue.pollFirst();
                        for (int dir = 0; dir < 8; dir++) {
                            int new_row = current.row + dx[dir];
                            int new_col = current.col + dy[dir];
                            if (Operator.valid(new_row, new_col, grid) && !reached[new_row][new_col]
                                    && grid[new_row][new_col].cell_type == Cell_Type.WALKER) {
                                reached[new_row][new_col] = true;
                                queue.addLast(grid[new_row][new_col]);
                            }
                        }
                    }
                }
            }
        return connected_components;
    }
}
