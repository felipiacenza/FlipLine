package flipline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BoardGenerator {

    private final Random random = new Random();
    private final List<Move> solution = new ArrayList<>();

    public Board generate(int rows, int cols, int movesCount) {

        // 1. Tablero resuelto (todo azul)
        Board board = new Board(rows, cols, Color.BLUE);

        List<Move> appliedMoves = new ArrayList<>();
        Move lastMove = null;

        // 2. Aplicar movimientos aleatorios
        for (int i = 0; i < movesCount; i++) {

            Move move;
            do {
                int row = random.nextInt(rows);
                int col = random.nextInt(cols);
                move = new Move(row, col);
            } while (move.equals(lastMove));

            board.selectCell(move.row(), move.col());
            appliedMoves.add(move);
            lastMove = move;
        }

        // 3. Generar solución (orden inverso)
        solution.clear();
        solution.addAll(appliedMoves);
        Collections.reverse(solution);

        return board;
    }

    public List<Move> getSolution() {
        return List.copyOf(solution);
    }
}
