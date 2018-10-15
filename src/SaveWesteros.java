import java.util.ArrayList;

public class SaveWesteros extends Problem {


    public SaveWesteros(State initial_state, ArrayList<Operator> operators){
        this.initial_state = initial_state;
        this.operators = operators;
    }

    public SaveWesteros(){}
    @Override
    public boolean goal_test(State state) {
        int cnt = 0;
        for(Cell[] cells : state.grid)
            for(Cell cell : cells)
                if(cell.cell_type == Cell_Type.WALKER)
                    cnt++;
        return cnt == 0;
    }

    @Override
    public int path_cost(Node cur_node, Operator operator) {
        boolean isKill = (operator instanceof Kill);
        return cur_node.path_cost + (isKill ? 1 : 0);
    }
}
