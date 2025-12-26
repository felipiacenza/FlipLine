package flipline;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printMenu();

        int rows = readPositiveInt(scanner, "Ingrese número de filas: ");
        int cols = readPositiveInt(scanner, "Ingrese número de columnas: ");

        Board board = new Board(rows, cols);

        board.printMatrix();

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("======================");
        System.out.println("      FlipLine");
        System.out.println("======================");
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
}
