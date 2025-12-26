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
        String colorCode = (color == Color.RED) ? "R" : "B";

        return colorCode + symbol;
    }
}
