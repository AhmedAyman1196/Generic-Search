public abstract class Operator {

	/**
	 * 
	 * @param node
	 * @return
	 */
	public abstract Node apply(Node node);

	/**
	 * array used to calculate the movements in the 4 directions
	 */
	static int[] dx = { 0, 0, 1, -1 };

	/**
	 * array used to calculate the movements in the 4 directions
	 */
	static int[] dy = { 1, -1, 0, 0 };

	/**
	 * 
	 * @param row
	 * @param col
	 * @param grid
	 * @return
	 */
	public static boolean valid(int row, int col, Cell[][] grid) {
		return row >= 0 && col >= 0 && row < grid.length && col < grid[0].length;
	}

	/**
	 * check if there are white walkers around this cell.
	 * @param cell
	 * @param grid
	 * @return
	 */
	public static boolean walker_around(Cell cell, Cell[][] grid) {
		for (int dir = 0; dir < 4; dir++) {
			int nrow = cell.row + dx[dir], ncol = cell.col + dy[dir];
			if (valid(nrow, ncol, grid) && grid[nrow][ncol].cell_type == Cell_Type.WALKER)
				return true;
		}
		return false;
	}
}
