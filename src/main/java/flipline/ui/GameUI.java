package flipline.ui;

import java.util.List;
import java.util.Scanner;

import flipline.game.GameConfig;

public class GameUI {

    private final Scanner scanner;

    public GameUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public GameConfig askForConfig() {
        printMainMenu();
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
            default -> throw new IllegalStateException();
        }

        int difficulty = readDifficulty();
        int scramble = difficultyToMoves(difficulty, rows, cols);

        return new GameConfig(rows, cols, difficulty, scramble);
    }

    private void printMainMenu() {
        MenuPrinter.printMenu(
                "FLIP LINE",
                "v0.1",
                List.of(
                        "1. Small   (4 x 4)",
                        "2. Medium  (6 x 6)",
                        "3. Large   (8 x 8)",
                        "4. Custom"
                )
        );
    }

    private int readDifficulty() {
        MenuPrinter.printMenu(
                "Difficulty",
                "",
                List.of(
                        "1. Easy",
                        "2. Normal",
                        "3. Hard",
                        "4. Extreme"
                )
        );
        return readOption("Option: ");
    }

    private int difficultyToMoves(int difficulty, int rows, int cols) {
        int cells = rows * cols;
        double factor = switch (difficulty) {
            case 1 -> 0.10;
            case 2 -> 0.20;
            case 3 -> 0.35;
            case 4 -> 0.50;
            default -> throw new IllegalStateException();
        };
        return Math.max(1, (int) Math.round(cells * factor));
    }

    private int readOption(String message) {
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                int opt = scanner.nextInt();
                if (opt >= 1 && opt <= 4) return opt;
            } else {
                scanner.next();
            }
            System.out.println("Invalid option.");
        }
    }

    private int readPositiveInt(String message) {
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                int v = scanner.nextInt();
                if (v > 0) return v;
            } else {
                scanner.next();
            }
            System.out.println("Enter a positive integer.");
        }
    }
}
