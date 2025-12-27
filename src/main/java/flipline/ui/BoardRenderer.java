package flipline.ui;

import flipline.game.Board;

public class BoardRenderer {

    public static void render(Board board) {
        printHeader(board);
        printBody(board);
        printFooter(board);
    }

    private static void printHeader(Board board) {
        System.out.print("    ");
        for (int col = 1; col <= board.getColumns(); col++) {
            System.out.printf(" %2d ", col);
        }
        System.out.println();

        System.out.print("    ");
        for (int i = 0; i < board.getColumns(); i++) {
            System.out.print("────");
        }
        System.out.println();
    }

    private static void printBody(Board board) {
        for (int row = 0; row < board.getRows(); row++) {
            System.out.printf("%3d │", row + 1);
            for (int col = 0; col < board.getColumns(); col++) {
                System.out.printf(" %s ", board.getCell(row, col));
            }
            System.out.println();
        }
    }

    private static void printFooter(Board board) {
        System.out.print("    ");
        for (int i = 0; i < board.getColumns(); i++) {
            System.out.print("────");
        }
        System.out.println();
    }
}
