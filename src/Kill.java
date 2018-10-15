import java.util.ArrayList;
import java.util.LinkedList;

public class Kill extends Operator {

    @Override
    public Node apply(Node node) {
        State state = node.state;
        ArrayList<Cell> walkers_around = get_walkers_around(state.grid, state.john_cell);
        if (walkers_around.size() == 0)return null;
        Cell[][] new_grid = Cell.clone(state.grid);
        for(Cell walker : walkers_around)
            new_grid[walker.row][walker.col].cell_type = Cell_Type.EMPTY;
        int new_last_interesting_index = -1;
        Cell new_john_cell = new Cell(state.john_cell.row, state.john_cell.col, state.john_cell.cell_type);
        State new_state = new State(new_grid, new_last_interesting_index, new_john_cell);
        Node new_parent = node;
        Operator new_operator = this;
        int new_depth = node.depth + 1;
        int new_path_cost = new SaveWesteros().path_cost(node, this);
        return new Node(new_state, new_parent, new_operator, new_depth, new_path_cost);
    }

    static ArrayList<Cell> get_walkers_around(Cell[][] grid, Cell cell){
        ArrayList<Cell> walkers = new ArrayList<>();
        for(int dir = 0; dir < 4; dir++){
            int new_row = cell.row + dx[dir], new_col = cell.col + dy[dir];
            if(valid(new_row, new_col, grid) && grid[new_row][new_col].cell_type == Cell_Type.WALKER)
                walkers.add(grid[new_row][new_col]);
        }
        return walkers;
    }

    public Kill() {
    }
}
