import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Main {

	static int rows, cols, max_dragon_glasses, stone_row, stone_col;
	static Cell[][] grid;

	public static void main(String[] args) {
		GenGrid();
		System.out.println("MAX Dragon Glasses = " + max_dragon_glasses);
		print(grid);

		Solution solution = search(grid, "DF", false);
		System.out.println("Strategy: DFS, " + solution);

		solution = search(grid, "BF", false);
		System.out.println("Strategy: BFS, " + solution);
		
		solution = search(grid, "UC", false);
		System.out.println("Strategy: UCS, " + solution);
		
		solution = search(grid, "ID", false);
		System.out.println("Strategy: IDS, " + solution);
		
		solution = search(grid, "GR1", false);
		System.out.println("Strategy: GR1, " + solution);
		
		solution = search(grid, "GR2", false);
		System.out.println("Strategy: GR2, " + solution);
		
		solution = search(grid, "AS1", false);
		System.out.println("Strategy: A*1, " + solution);

		solution = search(grid, "AS2", false);
		System.out.println("Strategy: A*2, " + solution);
	}

	public static Solution search(Cell[][] grid, String strategy, boolean visualize) {
		State initial = new State(grid, -1, new Cell(rows - 1, cols - 1, Cell_Type.JOHN));
		ArrayList<Operator> operators = new ArrayList<>();
		operators.add(new Kill());
		operators.add(new Move());

		SaveWesteros saveWesteros = new SaveWesteros(initial, operators);
		Solution solution = null;

		switch (strategy) {
		case "BF":
			solution = saveWesteros.search(new BFS());
			break;
		case "DF":
			solution = saveWesteros.search(new DFS());
			break;
		case "ID":
			solution = saveWesteros.search(new IDS());
			break;
		case "UC":
			solution = saveWesteros.search(new UCS());
			break;
		case "GR1":
			solution = saveWesteros.search(new Greedy(1));
			break;
		case "GR2":
			solution = saveWesteros.search(new Greedy(2));
			break;
		case "AS1":
			solution = saveWesteros.search(new A_Star(1));
			break;
		case "AS2":
			solution = saveWesteros.search(new A_Star(2));
			break;
		default:
			break;
		}

		if (visualize)
			if (solution == null) {
				System.out.println("John can't reach some white walkers due to obstacles!!");
			} else {
				print(solution);
				System.out.println("Cost is " + solution.cost);
			}
		return solution;
	}

	/**
	 * used to generate a random grid
	 */
	public static void GenGrid() {
		while (true) {
			rows = Math.max(6, new Random().nextInt(6 + 1));
			cols = Math.max(6, new Random().nextInt(6 + 1));

			grid = new Cell[rows][cols];

			for (int i = 0; i < rows; i++)
				for (int j = 0; j < cols; j++)
					grid[i][j] = new Cell(i, j, Cell_Type.EMPTY);
			// Placing jon snow
			grid[rows - 1][cols - 1].cell_type = Cell_Type.JOHN;

			// Placing dragonstone
			int x = rows - 1, y = cols - 1;
			while (x == rows - 1 && y == cols - 1) {
				x = new Random().nextInt(rows);
				y = new Random().nextInt(cols);
			}
			stone_row = x;
			stone_col = y;
			grid[x][y].cell_type = Cell_Type.STONE;

			// Adding random elements
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (grid[i][j].cell_type != Cell_Type.JOHN && grid[i][j].cell_type != Cell_Type.STONE) {
						// chances of empty cell is 3/6
						// chances of a white walker 1/6
						// chances of an obstacle 2/6
						int r = new Random().nextInt(6);
						if (r == 1 || r == 2)
							grid[i][j].cell_type = Cell_Type.OBSTACLE;
						else if (r == 3)
							grid[i][j].cell_type = Cell_Type.WALKER;
						else
							grid[i][j].cell_type = Cell_Type.EMPTY;
					}
				}
			}
			grid[x][y].cell_type = Cell_Type.EMPTY;
			// setting the maximum dragon glass that can be carried
			max_dragon_glasses = Math.max(1, new Random().nextInt(rows * cols / 4));
			if (can_reach(stone_row, stone_col))
				return;
		}
	}

	/**
	 * check if the agent can reach this cell.
	 *
	 * @param row
	 * @param col
	 * @return
	 */
	static boolean can_reach(int row, int col) {
		int[] dx = { 0, 0, 1, -1 };
		int[] dy = { 1, -1, 0, 0 };

		boolean[][] reached = new boolean[grid.length][grid[0].length];
		reached[row][col] = true;
		LinkedList<Cell> queue = new LinkedList<>();
		queue.addLast(new Cell(row, col, Cell_Type.EMPTY));
		while (!queue.isEmpty()) {
			Cell current = queue.pollFirst();

			// for each of the 4 directions
			for (int dir = 0; dir < 4; dir++) {
				int new_row = current.row + dx[dir];
				int new_col = current.col + dy[dir];

				if (valid(new_row, new_col, grid) && !reached[new_row][new_col]
						&& (grid[new_row][new_col].cell_type == Cell_Type.EMPTY
								|| grid[new_row][new_col].cell_type == Cell_Type.JOHN)) {
					reached[new_row][new_col] = true;
					queue.addLast(grid[new_row][new_col]);
				}
			}
		}

		return reached[rows - 1][cols - 1];
	}

	/**
	 * prints the Grid
	 *
	 * @param grid
	 */
	public static void print(Cell[][] grid) {
		String RED = "\u001B[31m";
		String GREEN = "\u001B[32m";
		String BLACK = "\u001B[30m";
		String YELLOW = "\u001B[33m";
		String BLUE = "\u001B[34m";
		String ANSI_RESET = "\u001B[0m";

		System.out.println("Start");
		for (int i = 0; i < grid.length; i++) {
			System.out.print(" | ");
			for (int j = 0; j < grid[i].length; j++) {
				Cell cell = i == stone_row && j == stone_col ? new Cell(i, j, Cell_Type.STONE) : Cell.clone(grid[i][j]);
				String before = cell.cell_type == Cell_Type.STONE ? BLUE
						: cell.cell_type == Cell_Type.JOHN ? GREEN
								: cell.cell_type == Cell_Type.WALKER ? RED
										: cell.cell_type == Cell_Type.OBSTACLE ? YELLOW : BLACK;
				String type = cell.cell_type.toString();
				while (type.length() < 8)
					type += " ";
				System.out.print(before + type + ANSI_RESET + " | ");
			}

			System.out.println();
			for (int j = 0; j < grid[i].length * 11; j++)
				System.out.print("-");
			System.out.println("--");
		}
		System.out.println("End");
	}

	/**
	 * used to print the solution; the cost, the number of expanded nodes and the
	 * steps to reach that cost.
	 *
	 * @param s
	 */
	static void print(Solution s) {
		System.out.println("Solution Cost is : " + s.cost);
		System.out.println("Number of expanded nodes are : " + s.expandedNodes);
		ArrayList<Node> nodes = s.nodes;
		print(nodes.get(0).state.grid);
		ArrayList<Operator> operators = s.operators;
		int dragon_classes = 0;
		for (int i = 0; i < operators.size(); i++) {
			// if the current operation is move, print the steps used to reach this cell
			if (operators.get(i) instanceof Move) {
				go(nodes.get(i).state.john_cell, nodes.get(i + 1).state.john_cell, nodes.get(i).state.grid);
			} else {
				// if the dragon glass is 0, then the agent must have gone to the dragonstone,
				// therefore, the steps to reach the dragon stone are printed, then the steps to
				// reach the previous node are printed
				if (dragon_classes == 0) {
					go(nodes.get(i).state.john_cell, new Cell(stone_row, stone_col, Cell_Type.EMPTY),
							nodes.get(i).state.grid);
					dragon_classes = max_dragon_glasses;
					go(new Cell(stone_row, stone_col, Cell_Type.EMPTY), nodes.get(i).state.john_cell,
							nodes.get(i).state.grid);
				}
				dragon_classes--;
				System.out.println("KILL!!!!");
				print(nodes.get(i + 1).state.grid);
			}
		}
	}

	/**
	 * print the steps from the start to the target
	 *
	 * @param start
	 * @param target
	 * @param grid
	 */
	static void go(Cell start, Cell target, Cell[][] grid) {
		int[] dx = { 0, 0, 1, -1 };
		int[] dy = { 1, -1, 0, 0 };

		Cell[][] parent = new Cell[grid.length][grid[0].length];

		boolean[][] reached = new boolean[grid.length][grid[0].length];
		LinkedList<Cell> queue = new LinkedList<Cell>();
		queue.push(start);
		reached[start.row][start.col] = true;
		while (!queue.isEmpty()) {
			Cell current = queue.pollFirst();
			if (current.row == target.row && current.col == target.col)
				break;
			for (int dir = 0; dir < 4; dir++) {
				int new_row = current.row + dx[dir];
				int new_col = current.col + dy[dir];
				if (valid(new_row, new_col, grid) && !reached[new_row][new_col]
						&& (grid[new_row][new_col].cell_type == Cell_Type.EMPTY
								|| grid[new_row][new_col].cell_type == Cell_Type.STONE
								|| grid[new_row][new_col].cell_type == Cell_Type.JOHN)) {
					reached[new_row][new_col] = true;
					parent[new_row][new_col] = Cell.clone(current);
					queue.addLast(grid[new_row][new_col]);
				}
			}
		}

		ArrayList<Cell> stack = new ArrayList<>();
		Cell current = Cell.clone(target);
		stack.add(Cell.clone(current));
		while (current.row != start.row || current.col != start.col) {
			current = parent[current.row][current.col];
			stack.add(Cell.clone(current));
		}
		Collections.reverse(stack);
		for (int i = 1; i < stack.size(); i++) {
			Cell[][] new_grid = put_john(stack.get(i), grid);
			print(new_grid);
		}
	}

	/**
	 * used to mark the previous agent's cell as empty and mark the new cell that
	 * contains the agent
	 *
	 * @param target
	 * @param grid
	 * @return
	 */
	static Cell[][] put_john(Cell target, Cell[][] grid) {
		Cell[][] new_grid = Cell.clone(grid);
		for (Cell[] cells : new_grid)
			for (Cell cell : cells)
				if (cell.cell_type == Cell_Type.JOHN)
					cell.cell_type = Cell_Type.EMPTY;
		new_grid[target.row][target.col].cell_type = Cell_Type.JOHN;
		return new_grid;
	}

	/**
	 * check that the indices are within the range
	 *
	 * @param row
	 * @param col
	 * @param grid
	 * @return
	 */
	static boolean valid(int row, int col, Cell[][] grid) {
		return row >= 0 && col >= 0 && row < grid.length && col < grid[0].length;
	}
}
