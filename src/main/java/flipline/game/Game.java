package flipline.game;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

import flipline.ui.GameUI;
import flipline.ui.BoardRenderer;
import flipline.utils.GameConfig;


public class Game {

    private final Board board;
    private final BoardGenerator generator;
    private final Deque<Move> history;
    private final Scanner scanner;

    private int moves;

    public Game() {
        this.scanner = new Scanner(System.in);

        GameUI ui = new GameUI(scanner);
        GameConfig config = ui.askForConfig();

        this.generator = new BoardGenerator();
        this.board = generator.generate(
                config.rows(),
                config.cols(),
                config.scrambleMoves()
        );

        this.history = new ArrayDeque<>();
        this.moves = 0;
    }


    public void run() {
        gameLoop();
        endGame();
        scanner.close();
    }

    private void gameLoop() {
        while (!board.isSolved()) {
            BoardRenderer.render(board);
            System.out.println("Enter row/column, 'U' to undo, 'H' to view move history, 'S' to show the solution, or 'Q' to quit.");
            String input = readCommand("Row (1 - " + board.getRows() + " | U/H/S/Q): ");

            switch (input) {
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
        System.out.println("Solution:");
        for (Move move : generator.getSolution()) {
            System.out.println("Row: " + (move.row() + 1) + ", Column: " + (move.col() + 1));
        }
    }

    private void processMove(String input) {
        try {
            int row = Integer.parseInt(input) - 1;
            if (row < 0 || row >= board.getRows()) {
                System.out.println("Row out of range.");
                return;
            }
            int col = readUserIndex("Column (1 - " + board.getColumns() + "): ", board.getColumns()) - 1;
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


}
