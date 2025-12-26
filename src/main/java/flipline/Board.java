package flipline;

import java.util.Random;

public class Board {
    private final Cell[][] cells;

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

    public boolean isSolved() {
        Color target = cells[0][0].getColor();

        for (Cell[] cell : cells) {
            for (Cell value : cell) {
                if (value.getColor() != target) {
                    return false;
                }
            }
        }
        return true;
    }

    private void toggleRow(int row) {
        for (int c = 0; c < cells[row].length; c++) {
            cells[row][c].toggleColor();
        }
    }

    private void toggleColumn(int col) {
        for (Cell[] cell : cells) {
            cell[col].toggleColor();
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
        for (Cell[] cell : cells) {
            for (int column = 0; column < cell.length; column++) {
                System.out.print(cell[column] + " ");
            }
            System.out.println();
        }
    }

}
