package flipline.solver;

import flipline.game.Move;

import java.util.ArrayList;
import java.util.List;

public class BoardSolver {

    public BoardSolution solve(BoardState state) {

        int R = state.rows();
        int C = state.cols();

        boolean[] flipRow = new boolean[R];
        boolean[] flipCol = new boolean[C];

        flipRow[0] = false;

        for (int c = 0; c < C; c++) {
            flipCol[c] = state.get(0, c);
        }

        for (int r = 1; r < R; r++) {
            flipRow[r] = state.get(r, 0) ^ flipCol[0];
        }

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                boolean expected = flipRow[r] ^ flipCol[c];
                if (expected != state.get(r, c)) {
                    throw new IllegalStateException("Unsolvable board state");
                }
            }
        }

        List<Move> rowMoves = new ArrayList<>();
        List<Move> colMoves = new ArrayList<>();

        for (int r = 0; r < R; r++) {
            if (flipRow[r]) {
                rowMoves.add(new Move(r, -1)); // -1 = fila
            }
        }

        for (int c = 0; c < C; c++) {
            if (flipCol[c]) {
                colMoves.add(new Move(-1, c)); // -1 = columna
            }
        }

        return new BoardSolution(rowMoves, colMoves);
    }
}
