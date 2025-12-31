package flipline.solver;

public class BoardState {

    private final int rows;
    private final int cols;
    private final boolean[][] state;

    public BoardState(boolean[][] state) {
        this.rows = state.length;
        this.cols = state[0].length;
        this.state = state;
    }

    public boolean get(int r, int c) {
        return state[r][c];
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }
}
