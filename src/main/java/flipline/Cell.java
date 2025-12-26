package flipline;

public class Cell {
    private Color color;
    private Orientation orientation;

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

    public void toggleColor(){
        if (color == Color.RED){
            color = Color.BLUE;
        } else {
            color = Color.RED;
        }
    }

    @Override
    public String toString() {
        String symbol = (orientation == Orientation.VERTICAL) ? "|" : "─";

        String colorCode = switch (color) {
            case RED -> "\u001B[31m";
            case BLUE -> "\u001B[34m";
        };

        String reset = "\u001B[0m";

        return colorCode + symbol + reset;
    }
}
