package flipline.game;

public class GameConfig {

    public final int rows;
    public final int cols;
    public final int difficulty;
    public final int scrambleMoves;

    public GameConfig(int rows, int cols, int difficulty, int scrambleMoves) {
        this.rows = rows;
        this.cols = cols;
        this.difficulty = difficulty;
        this.scrambleMoves = scrambleMoves;
    }
}
