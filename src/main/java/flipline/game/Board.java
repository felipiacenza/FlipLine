package flipline.game;

import flipline.solver.BoardState;
import flipline.utils.Color;
import flipline.utils.Orientation;

import java.util.Random;

public class Board {
    private final Cell[][] cells;

    public Board(int rows, int columns, Color initialColor) {
        cells = new Cell[rows][columns];
        initCells(initialColor);
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }


    public int getRows() {
        return cells.length;
    }

    public int getColumns() {
        return cells[0].length;
    }

    private void initCells(Color initialColor) {
        Random random = new Random();

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                Orientation orientation = random.nextBoolean() ? Orientation.HORIZONTAL : Orientation.VERTICAL;
                cells[row][col] = new Cell(initialColor, orientation);
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

    public BoardState toState() {
        boolean[][] state = new boolean[getRows()][getColumns()];
        Color target = Color.RED;

        for (int r = 0; r < getRows(); r++) {
            for (int c = 0; c < getColumns(); c++) {
                state[r][c] = cells[r][c].getColor() != target;
            }
        }

        return new BoardState(state);
    }

    public Move findCellForRowFlip(int row) {
        for (int c = 0; c < getColumns(); c++) {
            if (cells[row][c].getOrientation() == Orientation.HORIZONTAL) {
                return new Move(row, c);
            }
        }
        return null;
    }

    public Move findCellForColumnFlip(int col) {
        for (int r = 0; r < getRows(); r++) {
            if (cells[r][col].getOrientation() == Orientation.VERTICAL) {
                return new Move(r, col);
            }
        }
        return null;
    }


}
