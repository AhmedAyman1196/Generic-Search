import java.util.ArrayList;

public class Kill extends Operator {

	/**
	 * performs kill operation on the current node. The cells containing white
	 * walkers are marked as empty after kill. A new state is created and added to a
	 * new node. The cost is also calculated and is attached to the new node which
	 * is then returned.
	 */
	@Override
	public Node apply(Node node) {
		State state = node.state;
		// get the white walkers around the cell
		ArrayList<Cell> walkers_around = get_walkers_around(state.grid, state.john_cell);
		if (walkers_around.size() == 0)
			return null;

		Cell[][] new_grid = Cell.clone(state.grid);

		// mark the cells containing white walkers as empty after kill
		for (Cell walker : walkers_around)
			new_grid[walker.row][walker.col].cell_type = Cell_Type.EMPTY;

		// make a new node
		int new_last_interesting_index = -1;
		Cell new_john_cell = new Cell(state.john_cell.row, state.john_cell.col, state.john_cell.cell_type);
		State new_state = new State(new_grid, new_last_interesting_index, new_john_cell);
		Node new_parent = node;
		Operator new_operator = this;
		int new_depth = node.depth + 1;
		int new_path_cost = new SaveWesteros().path_cost(node, this);
		return new Node(new_state, new_parent, new_operator, new_depth, new_path_cost);
	}

	/**
	 * returns the position of white walkers around a given cell
	 * 
	 * @param grid
	 * @param cell
	 * @return
	 */
	static ArrayList<Cell> get_walkers_around(Cell[][] grid, Cell cell) {
		ArrayList<Cell> walkers = new ArrayList<>();
		for (int dir = 0; dir < 4; dir++) {
			int new_row = cell.row + dx[dir], new_col = cell.col + dy[dir];
			if (valid(new_row, new_col, grid) && grid[new_row][new_col].cell_type == Cell_Type.WALKER)
				walkers.add(grid[new_row][new_col]);
		}
		return walkers;
	}

	public Kill() {
	}
}
