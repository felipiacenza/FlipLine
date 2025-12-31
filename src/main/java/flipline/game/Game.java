package flipline.game;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

import flipline.solver.BoardSolution;
import flipline.solver.BoardSolver;
import flipline.solver.BoardState;
import flipline.ui.GameUI;
import flipline.ui.BoardRenderer;
import flipline.utils.GameConfig;


public class Game {

    private final Board board;
    private final BoardSolver solver;
    private final Deque<Move> history;
    private final Scanner scanner;

    private int moves;

    public Game() {
        this.scanner = new Scanner(System.in);

        GameUI ui = new GameUI(scanner);
        GameConfig config = ui.askForConfig();

        BoardGenerator generator = new BoardGenerator();
        this.board = generator.generate(
                config.rows(),
                config.cols(),
                config.scrambleMoves()
        );

        this.history = new ArrayDeque<>();
        this.moves = 0;

        this.solver = new BoardSolver();
    }


    public void run() {
        gameLoop();
        endGame();
        scanner.close();
    }

    private void gameLoop() {
        while (!board.isSolved()) {
            BoardRenderer.render(board);
            System.out.println("Enter row/column, 'P' to show hint, 'U' to undo, 'H' to view move history, 'S' to show the solution, or 'Q' to quit.");
            String input = readCommand("Row (1 - " + board.getRows() + " | P/U/H/S/Q): ");

            switch (input) {
                case "P":
                    showHint();
                    break;
                case "U":
                    undoMove();
                    break;
                case "H":
                    showHistory();
                    break;
                case "S":
                    showSolution();
                    break;
                case "Q":
                    return;
                default:
                    processMove(input);
            }
        }
    }

    private void endGame() {
        BoardRenderer.render(board);
        System.out.println("Game solved!");
        System.out.println("Moves made: " + moves);
    }

    private String readCommand(String message) {
        System.out.print(message);
        return scanner.next().trim().toUpperCase();
    }

    private void undoMove() {
        if (!history.isEmpty()) {
            Move last = history.pop();
            board.selectCell(last.row(), last.col());
            moves--;
            System.out.println("Last move undone.");
        } else {
            System.out.println("No moves to undo.");
        }
    }

    private void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No moves in history.");
        } else {
            System.out.println("Move history:");
            for (Move move : history) {
                System.out.println("Row: " + (move.row() + 1) + ", Column: " + (move.col() + 1));
            }
        }
    }

    private void showSolution() {
        BoardSolution solution = solveCurrentBoard();

        if (solution.isEmpty()) {
            System.out.println("The board is already solved.");
            return;
        }

        System.out.println("Solution from the current state:");

        for (Move move : solution.rowMoves()) {
            System.out.println("Flip row: " + (move.row() + 1));
        }

        for (Move move : solution.columnMoves()) {
            System.out.println("Flip column: " + (move.col() + 1));
        }
    }


    private void processMove(String input) {
        try {
            int row = Integer.parseInt(input) - 1;
            if (row < 0 || row >= board.getRows()) {
                System.out.println("Row out of range.");
                return;
            }

            int col = readUserIndex(
                    "Column (1 - " + board.getColumns() + "): ",
                    board.getColumns()
            ) - 1;

            board.selectCell(row, col);
            history.push(new Move(row, col));
            moves++;

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }


    private int readUserIndex(String message, int max) {
        int value;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= 1 && value <= max) {
                    return value;
                }
            } else {
                scanner.next();
            }
            System.out.println("Invalid value. Enter a number between 1 and " + max + ".");
        }
    }

    private BoardSolution solveCurrentBoard() {
        BoardState state = board.toState();
        return solver.solve(state);
    }

    private void showHint() {
        BoardSolution solution = solveCurrentBoard();

        if (!solution.rowMoves().isEmpty()) {
            int row = solution.rowMoves().getFirst().row();
            Move cell = board.findCellForRowFlip(row);

            if (cell != null) {
                System.out.println(
                        "Hint: select the cell (" +
                                (cell.row() + 1) + ", " + (cell.col() + 1) +
                                ") to flip the row " + (row + 1)
                );
            }
            return;
        }

        if (!solution.columnMoves().isEmpty()) {
            int col = solution.columnMoves().getFirst().col();
            Move cell = board.findCellForColumnFlip(col);

            if (cell != null) {
                System.out.println(
                        "Hint: select the cell (" +
                                (cell.row() + 1) + ", " + (cell.col() + 1) +
                                ") to flip the column " + (col + 1)
                );
            }
            return;
        }

        System.out.println("The board is already solved.");
    }
}
