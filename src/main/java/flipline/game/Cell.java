package flipline.game;

public class Cell {
    private Color color;
    private final Orientation orientation;

    public Cell(Color color, Orientation orientation) {
        this.color = color;
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Color getColor() {
        return color;
    }

    public void toggleColor() {
        if (color == Color.RED) {
            color = Color.BLUE;
        } else {
            color = Color.RED;
        }
    }

    @Override
    public String toString() {
        String symbol = (orientation == Orientation.VERTICAL) ? "|" : "─";

        if (color == Color.RED) {
            return "\u001B[31m" + symbol + "\u001B[0m";
        } else {
            return "\u001B[34m" + symbol + "\u001B[0m";
        }
    }

}
