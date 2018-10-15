public class State {
    Cell[][] grid;
    int last_interesting_index;
    Cell john_cell;

    public State(Cell[][] grid, int last_interesting_index, Cell john_cell) {
        this.grid = grid;
        this.last_interesting_index = last_interesting_index;
        this.john_cell = john_cell;
    }
}
