public abstract class Operator {

    public abstract Node apply(Node node);

    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};

    public static boolean valid(int row, int col, Cell[][] grid) {
        return row >= 0 && col >= 0 && row < grid.length && col < grid[0].length;
    }
    public static boolean walker_around(Cell cell, Cell[][] grid){
        for(int dir = 0; dir < 4; dir++){
            int nrow = cell.row + dx[dir], ncol = cell.col + dy[dir];
            if(valid(nrow, ncol, grid) && grid[nrow][ncol].cell_type == Cell_Type.WALKER)
                return true;
        }
        return false;
    }
}
