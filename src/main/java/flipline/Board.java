package flipline;

import java.util.Random;

public class Board {
    private Cell[][] cells;

    public Board(int rows, int columns) {
        cells = new Cell[rows][columns];
        initCells();
    }

    private void initCells() {
        Random random = new Random();

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                Color color = random.nextBoolean() ? Color.RED : Color.BLUE;
                Orientation orientation = random.nextBoolean() ? Orientation.HORIZONTAL : Orientation.VERTICAL;
                cells[row][col] = new Cell(color, orientation);
            }
        }
    }

    public void selectCell(int row, int col) {
        Orientation orientation = cells[row][col].getOrientation();

        if (orientation == Orientation.VERTICAL) {
            toggleColumn(col);
        } else {
            toggleRow(row);
        }
    }

    private void toggleRow(int row) {
        for (int c = 0; c < cells[row].length; c++) {
            cells[row][c].toggleColor();
        }
    }

    private void toggleColumn(int col) {
        for (int r = 0; r < cells.length; r++) {
            cells[r][col].toggleColor();
        }
    }

    public void printMatrix() {
        PrintHeader();
        PrintBody();
        PrintFooter();
    }

    private void PrintHeader() {
        System.out.println("===");
        // TODO: Make header dynamic
    }

    private void PrintFooter() {
        System.out.println("=== FlipLine v0.1v");
        // TODO: Make footer dynamic
    }

    private void PrintBody() {
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[row].length; column++) {
                System.out.print(cells[row][column] + " ");
            }
            System.out.println();
        }
    }

}
