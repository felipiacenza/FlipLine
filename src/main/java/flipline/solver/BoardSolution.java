package flipline.solver;

import flipline.game.Move;
import java.util.List;

public record BoardSolution(
        List<Move> rowMoves,
        List<Move> columnMoves
) {
    public boolean isEmpty() {
        return rowMoves.isEmpty() && columnMoves.isEmpty();
    }
}
