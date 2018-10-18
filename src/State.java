public class State {
	/**
	 * current instance of the grid.
	 */
	Cell[][] grid;

	/**
	 * the index of the last interesting node, used to decide upon the next node to
	 * visit.
	 */
	int last_interesting_index;

	/**
	 * cell representing Jon current position.
	 */
	Cell john_cell;

	/**
	 * Constructor
	 * 
	 * @param grid
	 * @param last_interesting_index
	 * @param john_cell
	 */
	public State(Cell[][] grid, int last_interesting_index, Cell john_cell) {
		this.grid = grid;
		this.last_interesting_index = last_interesting_index;
		this.john_cell = john_cell;
	}
}
