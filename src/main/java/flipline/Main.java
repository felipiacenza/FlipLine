package flipline;

import flipline.game.Game;

public class Main {
    public static void main(String[] args) {

        Deque<Move> history = new ArrayDeque<>();

        Scanner scanner = new Scanner(System.in);

        printTitle();

        int rows;
        int cols;

        int size = readOption(scanner);

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
                rows = readPositiveInt(scanner, "Enter number of rows: ");
                cols = readPositiveInt(scanner, "Enter number of columns: ");
            }
            default -> throw new IllegalStateException("Invalid option");
        }

        int difficulty = readDifficulty(scanner);
        int scrambleMoves = difficultyToMoves(difficulty, rows, cols);

        BoardGenerator generator = new BoardGenerator();
        Board board = generator.generate(rows, cols, scrambleMoves);

        int moves = 0;

        label:
        while (!board.isSolved()) {
            board.printMatrix();

            System.out.println("Enter row/column, 'U' to undo, 'H' to view move history, 'S' to show the solution, or 'Q' to quit.");

            String input = readCommand(scanner, "Row (1 - " + rows + " | U/H/S/Q): ");

            switch (input) {
                case "U":
                    moves = UndoMovement(history, board, moves);
                    continue;
                case "H":
                    showHistory(history);
                    continue;
                case "S":
                    getSolution(generator);
                    continue;
                case "Q":
                    exitGame();
                    break label;
            }

            int userRow;
            try {
                userRow = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (userRow < 1 || userRow > rows) {
                System.out.println("Row out of range.");
                continue;
            }

            int userCol = readUserIndex(scanner, "Column (1 - " + cols + "): ", cols);

            int row = userRow - 1;
            int col = userCol - 1;

            Move move = new Move(row, col);
            board.selectCell(row, col);
            history.push(move);
            moves++;
        }

        if (board.isSolved()) {
            board.printMatrix();
            System.out.println("Game solved!");
            System.out.println("Moves made: " + moves);
        }
        scanner.close();
    }

    private static void exitGame() {
        System.out.println("Exiting the game. Goodbye!");
    }

    private static void getSolution(BoardGenerator generator) {
        System.out.println("Solution:");
        for (Move move : generator.getSolution()) {
            System.out.println("Row: " + (move.row() + 1) + ", Column: " + (move.col() + 1));
        }
    }

    private static int UndoMovement(Deque<Move> history, Board board, int moves) {
        if (!history.isEmpty()) {
            Move last = history.pop();
            board.selectCell(last.row(), last.col());
            moves--;
            System.out.println("Last move undone.");
        } else {
            System.out.println("No moves to undo.");
        }
        return moves;
    }

    private static void showHistory(Deque<Move> history) {
        if (history.isEmpty()) {
            System.out.println("No moves in history.");
        } else {
            System.out.println("Move history:");
            for (Move move : history) {
                System.out.println("Row: " + (move.row() + 1) + ", Column: " + (move.col() + 1));
            }
        }
    }

    private static String readCommand(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.next().trim().toUpperCase();
    }

    private static int readUserIndex(Scanner scanner, String message, int max) {
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


    private static int readOption(Scanner scanner) {
        int option;

        while (true) {
            System.out.print("Select an option: ");
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

    private static int readPositiveInt(Scanner scanner, String message) {
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

    private static int readDifficulty(Scanner scanner) {
        System.out.println();
        System.out.println("Select difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Normal");
        System.out.println("3. Hard");
        System.out.println("4. Extreme");

        int option;

        while (true) {
            System.out.print("Option: ");
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

    private static int difficultyToMoves(int difficulty, int rows, int cols) {
        int cells = rows * cols;

        double factor = switch (difficulty) {
            case 1 -> 0.10; // Fácil
            case 2 -> 0.20; // Normal
            case 3 -> 0.35; // Difícil
            case 4 -> 0.50; // Extremo
            default -> throw new IllegalStateException("Dificultad inválida");
        };

        int moves = (int) Math.round(cells * factor);

        return Math.max(moves, 1);
    }


    private static void printTitle() {
        System.out.println("======================");
        System.out.println(" FLIP LINE  |  v0.1v  ");
        System.out.println("======================");
        System.out.println("1. Small game (4x4)");
        System.out.println("2. Medium game (6x6)");
        System.out.println("3. Large game (8x8)");
        System.out.println("4. Custom");
    }
}
