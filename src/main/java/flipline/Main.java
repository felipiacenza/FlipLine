package flipline;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printTitle();

        int rows;
        int cols;

        switch (readOption(scanner)) {
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
        int scrambleMoves = difficultyToMoves(difficulty);

        BoardGenerator generator = new BoardGenerator();
        Board board = generator.generate(rows, cols, scrambleMoves);

        int moves = 0;

        while (!board.isSolved()) {
            board.printMatrix();

            int userRow = readUserIndex(scanner, "Fila (1 - " + rows + "): ", rows);
            int userCol = readUserIndex(scanner, "Columna (1 - " + cols+ "): ", cols);

            int row = userRow - 1;
            int col = userCol - 1;

            board.selectCell(userRow - 1, userCol - 1);
            moves++;
        }

        board.printMatrix();
        System.out.println("¡Juego resuelto!");
        System.out.println("Movimientos realizados: " + moves);

        scanner.close();
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

    private static int difficultyToMoves(int difficulty) {
        return switch (difficulty) {
            case 1 -> 5;    // Fácil
            case 2 -> 15;   // Normal
            case 3 -> 30;   // Difícil
            case 4 -> 60;   // Extremo
            default -> 15;
        };
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
