import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Move extends Operator {

    /**
     * performs move operation on the current node. This is done by choosing the
     * next interesting cell and moving the agent to that cell. The list of the
     * cells that are of interest to the agent contains the cells having white
     * walkers around them. A new state is created and added to a new node which is
     * then returned. The choice of the nodes is done using an index pointing to the
     * last chosen cell which is attached to the state.
     */
    @Override
    public Node apply(Node node) {
        State state = node.state;
        Cell[][] grid = state.grid;

        // get a list of the cells that are of interest to the agent, which are cells
        // having white walkers around them
        ArrayList<Cell> interesting_cells = get_interesting_cells(grid, state.john_cell);
        Collections.sort(interesting_cells);

        int next_interesting = state.last_interesting_index + 1;
        if (next_interesting >= interesting_cells.size())
            return null;

        // make a new node, return it
        Cell[][] new_grid = Cell.clone(grid);
        int new_last_interesting_index = next_interesting;
        Cell new_john_cell = Cell.clone(interesting_cells.get(next_interesting));
        new_john_cell.cell_type = Cell_Type.JOHN;
        new_grid[new_john_cell.row][new_john_cell.col] = new_john_cell;
        new_grid[state.john_cell.row][state.john_cell.col] = Cell.clone(state.john_cell);
        new_grid[state.john_cell.row][state.john_cell.col].cell_type = Cell_Type.EMPTY;
        State new_state = new State(new_grid, new_last_interesting_index, new_john_cell);
        Node new_parent = node;
        Operator new_operator = this;
        int new_depth = node.depth + 1;
        int new_path_cost = new SaveWesteros().path_cost(node, this);
//        Main.print(new_grid);
//        System.out.println(interesting_cells + " " + next_interesting);
        return new Node(new_state, new_parent, new_operator, new_depth, new_path_cost);
    }

    /**
     * returns a list containing all the cells of interest to the agent. These are
     * cells that have white walkers adjacent to them.
     *
     * @param grid
     * @param john_cell
     * @return
     */
    static ArrayList<Cell> get_interesting_cells(Cell[][] grid, Cell john_cell) {
        boolean[][] reached = new boolean[grid.length][grid[0].length];
        LinkedList<Cell> queue = new LinkedList<Cell>();
        queue.push(john_cell);
        reached[john_cell.row][john_cell.col] = true;
        ArrayList<Cell> interesting_cells = new ArrayList<>();

        while (!queue.isEmpty()) {
            Cell current = queue.pollFirst();

            // if there are white walkers around the cell, then it is of interest
            if (walker_around(current, grid))
                interesting_cells.add(current);

            // move in the 4 directions and
            for (int dir = 0; dir < 4; dir++) {
                int new_row = current.row + dx[dir];
                int new_col = current.col + dy[dir];
                if (valid(new_row, new_col, grid) && !reached[new_row][new_col]
                        && (grid[new_row][new_col].cell_type == Cell_Type.EMPTY
                        || grid[new_row][new_col].cell_type == Cell_Type.STONE
                        || grid[new_row][new_col].cell_type == Cell_Type.JOHN)) {
                    reached[new_row][new_col] = true;
                    queue.addLast(grid[new_row][new_col]);
                }
            }
        }
        return interesting_cells;
    }

    public Move() {
    }
}
