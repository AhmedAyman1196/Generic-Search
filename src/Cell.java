enum Cell_Type{
    JOHN, WALKER, OBSTACLE, STONE, EMPTY
}
public class Cell implements Comparable<Cell>{
    int row, col;
    Cell_Type cell_type;

    public Cell(int row, int col, Cell_Type cell_type) {
        this.row = row;
        this.col = col;
        this.cell_type = cell_type;
    }

    @Override
    public int compareTo(Cell o) {
        return row != o.row ? row - o.row : col - o.col;
    }

    static Cell[][] clone(Cell[][] cells){
        Cell[][] cloned = new Cell[cells.length][cells[0].length];
        for(int i = 0; i < cells.length; i++)
            for(int j = 0; j < cells[i].length; j++)
                cloned[i][j] = new Cell(cells[i][j].row, cells[i][j].col, cells[i][j].cell_type);
        return cloned;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", col=" + col +
                ", cell_type=" + cell_type +
                '}';
    }

    static Cell clone(Cell cell){
        return new Cell(cell.row, cell.col, cell.cell_type);
    }
}
