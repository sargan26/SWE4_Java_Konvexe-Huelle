package point;
import point.Point;

public class PointList {
    private Point[] points;
    int size;

    public PointList() {
        this.size = 0;
        points = new Point[0];
    }
    // Erzeugt eine leere Punktliste mit Kapazität 0.

    public PointList(int capacity) {
        this.size = 0;
        points = new Point[capacity];

    }
    // Erzeugt eine leere Punktliste mit Kapazität capacity.

    public int capacity() {
        return points.length;
    }
    // Gibt die Kapazität der Punktliste zurück

    public int size() {
        return size;
    }
    // Gibt die Größe der Punktliste zurück.

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
    // Verändert die Kapazität der Punktliste. newCapacity kann
    // größer als, kleiner als oder gleich wie die alte Kapazität sein.

    public void add(Point p) {
        if (size + 1 > capacity()) {
            if (points.length == 0) {
                changeCapacity(1);
            } else {
                changeCapacity(capacity() * 2);
            }
            //System.out.println("Changed capacity when adding a point. New capacity = " + this.capacity());
        }
        points[size] = p;
        size++;
    }
    // Fügt einen Punkt zur Menge hinzu. Erhöht bei Bedarf die Kapazität.

    public Point get(int i) {
        if (i >= 0 && i < size) {
            return points[i];
        } else {
            throw new IndexOutOfBoundsException("Index " + i + " out of bounds for PointList of size " + size);
        }
    }
    // Liefert den i-ten Punkt der Menge.


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
    // Vereinigt diese Punktliste mit der Punktliste other und gibt eine
    // neue Punktliste zurück.

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(points[i]);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
    // Erzeugt einen String der Form [(x1,y1), ..., (xn,yn)].
        return sb.toString();
    }

    // Ermittelt die konvexe Menge der Punktliste.
    // Die zurückgegebene Punktliste enthält die Punkte der konvexen Hülle.
    // Die Punkte sind im Gegenuhrzeigersinn sortiert.
    public PointList convexHull() {
        if (size < 3) return null; // Eine konvexe Hülle ist nicht möglich mit weniger als 3 Punkten

        PointList hull = new PointList();

        // Finde den am weitesten links liegenden Punkt
        int l = 0;
        for (int i = 1; i < size; i++)
            if (points[i].getX() < points[l].getX())
                l = i;

        // Starte vom am weitesten links liegenden Punkt, bewege dich gegen den Uhrzeigersinn
        // bis du wieder zum Startpunkt kommst
        int p = l, q;
        do {
            points[p].setLabel('O');
            hull.add(points[p]);

            // Suche nach einem Punkt 'q', so dass die Orientierung(p, x, q) gegen den Uhrzeigersinn ist
            // für alle Punkte 'x'
            q = (p + 1) % size;
            for (int i = 0; i < size; i++) {
                if (orientation(points[p], points[i], points[q]) == 2)
                    q = i;
            }

            // Jetzt ist q der am weitesten gegen den Uhrzeigersinn in Bezug auf p
            // Setze p als q für die nächste Iteration, so dass q zum Ergebnis 'hull' hinzugefügt wird
            p = q;

        } while (p != l);  // Solange wir nicht zum ersten Punkt kommen

        return hull;
    }

    // Eine Hilfsfunktion, um die Orientierung des geordneten Tripels (p, q, r) zu finden.
    // 0 --> p, q und r sind kolinear
    // 1 --> Im Uhrzeigersinn
    // 2 --> Gegen den Uhrzeigersinn
    public int orientation(Point p, Point q, Point r) {
        int val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) return 0;  // kolinear
        return (val > 0)? 1: 2; // im oder gegen den Uhrzeigersinn
    }




    public String toAsciiGraphic(int width, int height) {
        char[][] grid = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = ' ';
            }
        }

        // Finden der Grenzen der Punkte, um die Skalierung zu berechnen
        int maxX = 1;
        int maxY = 1;
        for (Point point : points) {
            if (point != null) {
                maxX = Math.max(maxX, point.getX());
                maxY = Math.max(maxY, point.getY());
            }
        }

        // Skalierungsfaktoren berechnen
        double scaleX = maxX > 1 ? (double)(width - 1) / maxX : 1;
        double scaleY = maxY > 1 ? (double)(height - 1) / maxY : 1;

        // Punkte auf das Raster anwenden
        for (Point point : points) {
            if (point != null) {
                int scaledX = (int)(point.getX() * scaleX);
                int scaledY = (int)(point.getY() * scaleY);
                grid[scaledY][scaledX] = point.getLabel();
            }
        }

        // Erstellung der ASCII-Grafik als String
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
