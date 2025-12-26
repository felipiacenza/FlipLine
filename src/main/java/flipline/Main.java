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

        Board board = new Board(rows, cols);
        int moves = 0;

        while (!board.isSolved()) {
            board.printMatrix();

            int row = readIndex(scanner, "Fila (0 - " + (rows - 1) + "): ", rows);
            int col = readIndex(scanner, "Columna (0 - " + (cols - 1) + "): ", cols);

            board.selectCell(row, col);
            moves++;
        }

        board.printMatrix();
        System.out.println("¡Juego resuelto!");
        System.out.println("Movimientos realizados: " + moves);

        scanner.close();
    }

    private static int readIndex(Scanner scanner, String message, int limit) {
        int value;

        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= 0 && value < limit) {
                    return value;
                }
            } else {
                scanner.next();
            }
            System.out.println("Índice inválido.");
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
