package point;

public class Point {
    private int x;
    private int y;
    char label;

    public Point () {
        x = 0;
        y = 0;
        this.label = 'X';
    }

    public Point (int x, int y) {
        this.x = x;
        this.y = y;
        this.label = 'X';
    }

    public Point (int x, int y, char label) {
        this.x = x;
        this.y = y;
        this.label = label;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getLabel() {
        return label;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLabel(char label) {
        this.label = label;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
    // (x1,y1)
}
