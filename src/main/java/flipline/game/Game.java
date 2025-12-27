package flipline.game;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Game {

    private final Board board;
    private final BoardGenerator generator;
    private final Deque<Move> history;
    private final Scanner scanner;

    private int moves;

    public Game() {
        this.scanner = new Scanner(System.in);
        printTitle();
        int size = readOption("Select an option: ");
        int rows, cols;
        switch (size) {
            case 1 -> {
                rows = 4;
                cols = 4;
            }
            case 2 -> {
                rows = 6;
                cols = 6;
            }
            case 3 -> {
                rows = 8;
                cols = 8;
            }
            case 4 -> {
                rows = readPositiveInt("Enter number of rows: ");
                cols = readPositiveInt("Enter number of columns: ");
            }
            default -> throw new IllegalStateException("Invalid option");
        }
        int difficulty = readDifficulty();
        int scrambleMoves = difficultyToMoves(difficulty, rows, cols);
        this.generator = new BoardGenerator();
        this.board = generator.generate(rows, cols, scrambleMoves);
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
            board.printMatrix();
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
        board.printMatrix();
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

    private void printTitle() {
        System.out.println("======================");
        System.out.println(" FLIP LINE  |  v0.1v  ");
        System.out.println("======================");
        System.out.println("1. Small game (4x4)");
        System.out.println("2. Medium game (6x6)");
        System.out.println("3. Large game (8x8)");
        System.out.println("4. Custom");
    }


    private int readDifficulty() {
        System.out.println();
        System.out.println("Select difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Normal");
        System.out.println("3. Hard");
        System.out.println("4. Extreme");

        return readOption("Option: ");
    }

    private int readOption(String message) {
        int option;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                if (option >= 1 && option <= 4) {
                    return option;
                }
            } else {
                scanner.next();
            }
            System.out.println("Invalid option.");
        }
    }

    private int readPositiveInt(String message) {
        int value;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value > 0) {
                    return value;
                }
            } else {
                scanner.next();
            }
            System.out.println("Enter a positive integer.");
        }
    }

    private int difficultyToMoves(int difficulty, int rows, int cols) {
        int cells = rows * cols;
        double factor = switch (difficulty) {
            case 1 -> 0.10; // Easy
            case 2 -> 0.20; // Normal
            case 3 -> 0.35; // Hard
            case 4 -> 0.50; // Extreme
            default -> throw new IllegalStateException("Invalid difficulty");
        };
        int moves = (int) Math.round(cells * factor);
        return Math.max(moves, 1);
    }
}
