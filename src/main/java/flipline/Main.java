package flipline;

import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.Deque;


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
                rows = readPositiveInt(scanner, "Ingrese número de filas: ");
                cols = readPositiveInt(scanner, "Ingrese número de columnas: ");
            }
            default -> throw new IllegalStateException("Opción inválida");
        }

        int difficulty = readDifficulty(scanner);
        int scrambleMoves = difficultyToMoves(difficulty, rows, cols);

        BoardGenerator generator = new BoardGenerator();
        Board board = generator.generate(rows, cols, scrambleMoves);

        int moves = 0;

        label:
        while (!board.isSolved()) {
            board.printMatrix();

            System.out.println("Ingrese fila/columna, 'U' para deshacer, 'H' para ver el historial de movimientos, 'S' para mostrar la solución o 'Q' para salir.");

            String input = readCommand(scanner, "Fila (1 - " + rows + " | U/H/S/Q): ");

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
                System.out.println("Entrada inválida.");
                continue;
            }

            if (userRow < 1 || userRow > rows) {
                System.out.println("Fila fuera de rango.");
                continue;
            }

            int userCol = readUserIndex(scanner, "Columna (1 - " + cols + "): ", cols);

            int row = userRow - 1;
            int col = userCol - 1;

            Move move = new Move(row, col);
            board.selectCell(row, col);
            history.push(move);
            moves++;
        }

        if (board.isSolved()) {
            board.printMatrix();
            System.out.println("¡Juego resuelto!");
            System.out.println("Movimientos realizados: " + moves);
        }
        scanner.close();
    }

    private static void exitGame() {
        System.out.println("Saliendo del juego. ¡Hasta luego!");
    }

    private static void getSolution(BoardGenerator generator) {
        System.out.println("Solución:");
        for (Move move : generator.getSolution()) {
            System.out.println("Fila: " + (move.row() + 1) + ", Columna: " + (move.col() + 1));
        }
    }

    private static int UndoMovement(Deque<Move> history, Board board, int moves) {
        if (!history.isEmpty()) {
            Move last = history.pop();
            board.selectCell(last.row(), last.col());
            moves--;
            System.out.println("Último movimiento deshecho.");
        } else {
            System.out.println("No hay movimientos para deshacer.");
        }
        return moves;
    }

    private static void showHistory(Deque<Move> history) {
        if (history.isEmpty()) {
            System.out.println("No hay movimientos en el historial.");
        } else {
            System.out.println("Historial de movimientos:");
            for (Move move : history) {
                System.out.println("Fila: " + (move.row() + 1) + ", Columna: " + (move.col() + 1));
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
            System.out.println("Valor inválido. Ingrese un número entre 1 y " + max + ".");
        }
    }


    private static int readOption(Scanner scanner) {
        int option;

        while (true) {
            System.out.print("Seleccione una opción: ");
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                if (option >= 1 && option <= 4) {
                    return option;
                }
            } else {
                scanner.next();
            }
            System.out.println("Opción inválida.");
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
            System.out.println("Ingrese un número entero positivo.");
        }
    }

    private static int readDifficulty(Scanner scanner) {
        System.out.println();
        System.out.println("Seleccione dificultad:");
        System.out.println("1. Fácil");
        System.out.println("2. Normal");
        System.out.println("3. Difícil");
        System.out.println("4. Extremo");

        int option;

        while (true) {
            System.out.print("Opción: ");
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                if (option >= 1 && option <= 4) {
                    return option;
                }
            } else {
                scanner.next();
            }
            System.out.println("Opción inválida.");
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
        System.out.println("1. Juego pequeño (4x4)");
        System.out.println("2. Juego mediano (6x6)");
        System.out.println("3. Juego grande (8x8)");
        System.out.println("4. Personalizado");
    }
}
