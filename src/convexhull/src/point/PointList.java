package point;
import point.Point;

public class PointList {
    private Point[] points;
    int size;

    public PointList() {
        this.size = 0;
        points = new Point[0];
    }
    // Creates an empty point list with a capacity of 0.

    public PointList(int capacity) {
        this.size = 0;
        points = new Point[capacity];
    }
    // Creates an empty point list with the specified capacity.

    public int capacity() {
        return points.length;
    }
    // Returns the capacity of the point list.

    public int size() {
        return size;
    }
    // Returns the size of the point list.

    public void changeCapacity(int newCapacity) {
        Point[] newPoints = new Point[newCapacity];
        for (int i = 0; i < this.size; i++) {
            newPoints[i] = points[i];
        }
        if (newCapacity < this.size) {
            this.size = newCapacity;
        }
        points = newPoints;
    }
    // Changes the capacity of the point list. newCapacity can be greater than, less than,
    // or equal to the old capacity.

    public void add(Point p) {
        if (size + 1 > capacity()) {
            if (points.length == 0) {
                changeCapacity(1);
            } else {
                changeCapacity(capacity() * 2);
            }
        }
        points[size] = p;
        size++;
    }
    // Adds a point to the set. Increases capacity if necessary.

    public Point get(int i) {
        if (i >= 0 && i < size) {
            return points[i];
        } else {
            throw new IndexOutOfBoundsException("Index " + i + " out of bounds for PointList of size " + size);
        }
    }
    // Retrieves the i-th point of the set.

    public PointList unify(PointList other) {
        if (other == null) {
            return this;
        }
        Point[] newPoints = new Point[this.size + other.size()];
        for (int i = 0; i < this.size; i++) {
            newPoints[i] = points[i];
        }
        int j = 0;
        for (int i = this.size; i < this.size + other.size(); i++, j++) {
            newPoints[i] = other.get(j);
        }
        PointList newPointList = new PointList(newPoints.length);
        for (Point point : newPoints) {
            newPointList.add(point);
        }
        return newPointList;
    }
    // Unifies this point list with another point list and returns a new point list.

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(points[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    // Creates a string in the format [(x1,y1), ..., (xn,yn)].

    public PointList convexHull() {
        if (size < 3) return null; // A convex hull is not possible with less than 3 points.

        PointList hull = new PointList();

        // Find the point furthest to the left
        int l = 0;
        for (int i = 1; i < size; i++) {
            if (points[i].getX() < points[l].getX()) {
                l = i;
            }
        }

        // Start from the leftmost point, move counterclockwise until you return to the start point
        int p = l, q;
        do {
            points[p].setLabel('O');
            hull.add(points[p]);

            // Search for a point 'q' such that orientation(p, x, q) is counterclockwise
            // for all points 'x'
            q = (p + 1) % size;
            for (int i = 0; i < size; i++) {
                if (orientation(points[p], points[i], points[q]) == 2)
                    q = i;
            }

            // Now q is the most counterclockwise with respect to p
            // Set p as q for the next iteration so that q is added to the result 'hull'
            p = q;

        } while (p != l);  // While we don't come to the first point.

        return hull;
    }
    // Determines the convex set of the point list.
    // The returned point list contains the points of the convex hull, sorted counterclockwise.

    // A helper function to find the orientation of the ordered triplet (p, q, r).
    // 0 --> p, q, and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    public int orientation(Point p, Point q, Point r) {
        int val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) return 0;  // collinear
        return (val > 0) ? 1 : 2; // clock or counterclockwise
    }

    public String toAsciiGraphic(int width, int height) {
        char[][] grid = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = ' ';
            }
        }

        // Find the bounds of the points to calculate scaling
        int maxX = 1;
        int maxY = 1;
        for (Point point : points) {
            if (point != null) {
                maxX = Math.max(maxX, point.getX());
                maxY = Math.max(maxY, point.getY());
            }
        }

        // Calculate scaling factors
        double scaleX = maxX > 1 ? (double)(width - 1) / maxX : 1;
        double scaleY = maxY > 1 ? (double)(height - 1) / maxY : 1;

        // Apply points to the grid
        for (Point point : points) {
            if (point != null) {
                int scaledX = (int)(point.getX() * scaleX);
                int scaledY = (int)(point.getY() * scaleY);
                grid[scaledY][scaledX] = point.getLabel();
            }
        }

        // Creating the ASCII graphic as a string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
