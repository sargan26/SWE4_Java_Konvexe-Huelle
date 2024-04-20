package main;

import point.Point;
import point.PointList;

public class Main {
    public static void main(String[] args) {
        // Test cases for the Point class.
        System.out.println("-- Point Class Tests --");
        Point pointDefault = new Point();
        Point pointCustom = new Point(1, 2);
        System.out.println(pointDefault);
        System.out.println(pointCustom);

        pointDefault.setX(3);
        System.out.println(pointDefault);
        pointDefault.setY(4);
        System.out.println(pointDefault);

        // Test cases for the PointList class.
        System.out.println("-- PointList Class Tests --");
        PointList pointList = new PointList();

        addPointsTo(pointList, new Point[]{new Point(3, 3), new Point(4, 4), new Point(5, 5)});

        printPointListDetails(pointList);

        addPointsTo(pointList, new Point[]{
                new Point(0, 1), new Point(0, 2), new Point(0, 3),
                new Point(0, 4), new Point(0, 5), new Point(0, 0)
        });

        for (int y = 0; y <= 5; y++) {
            pointList.add(new Point(5, y));
        }

        addPointsTo(pointList, new Point[]{new Point(1, 1), new Point(2, 2)});

        printAsciiGraphic(pointList, 12, 6);

        // Test case for the unify method.
        System.out.println("-- Unify Method Test --");
        PointList plUnified = testUnify();

        // Test case for the ConvexHull method.
        System.out.println("-- ConvexHull Method Test --");
        testConvexHull();

        // Test case combined
        System.out.println("-- Combined Method Test --");
        testCombined();

        // Test trivial cases
        System.out.println("-- Trivial Cases --");
        testTrivialCases();
    }

    // Helper method to add multiple points to the list.
    private static void addPointsTo(PointList list, Point[] points) {
        for (Point p : points) {
            list.add(p);
        }
    }

    // Helper method to print details of the point list.
    private static void printPointListDetails(PointList list) {
        System.out.println("List Size = " + list.size());
        System.out.println("List Capacity = " + list.capacity());
        System.out.println("List Content = " + list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("list.get[" + i + "] = " + list.get(i));
        }
        try {
            System.out.println("list.get[OutOfBounds] = " + list.get(list.size()));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Exception occurred as expected when accessing out-of-bounds index.");
        }
    }

    // Helper method to print the ASCII graphic.
    private static void printAsciiGraphic(PointList list, int width, int height) {
        if (list == null) {
            System.out.println("Null PointList. Cannot print ASCII graphic.");
            return;
        }
        if (list.size() == 0) {
            System.out.println("Empty PointList. Cannot print ASCII graphic.");
            return;
        }
        System.out.println(("------ ASCII Graphic Start -------"));
        System.out.println(list.toAsciiGraphic(width, height));
        System.out.println(("------ ASCII Graphic End -------"));
    }

    // Method to test unify functionality.
    private static PointList testUnify() {
        PointList listOne = new PointList();
        PointList listTwo = new PointList();

        addPointsTo(listOne, new Point[]{new Point(1, 1), new Point(2, 2)});
        addPointsTo(listTwo, new Point[]{new Point(3, 3), new Point(4, 4), new Point(5, 5)});

        PointList unifiedList = listOne.unify(listTwo);
        System.out.println("Unified PointList = " + unifiedList);
        return unifiedList;
    }

    // Method to test ConvexHull functionality.
    private static void testConvexHull() {
        PointList list = new PointList();

        addPointsTo(list, new Point[]{
                new Point(2, 4), new Point(3, 3), new Point(4, 2),
                new Point(5, 3), new Point(6, 4), new Point(1, 3),
                new Point(7, 3), new Point(3, 1), new Point(5, 1),
                new Point(4, 0), new Point(2, 2), new Point(6, 2)
        });

        PointList convexHull = list.convexHull();
        System.out.println("Original PointList = " + list);
        System.out.println("Convex Hull = " + convexHull);
        printAsciiGraphic(list, 8, 5);
    }

    // Method to create a test case for combined functionality.
    private static void testCombined() {
        PointList pl = new PointList();

        addPointsTo(pl, new Point[]{
                new Point(2, 1) , new Point(1, 5), new Point(3, 1),
                new Point(2, 3), new Point(3, 3), new Point(1, 2),
                new Point(4, 2), new Point(2, 1), new Point(3, 1),
                new Point(0, 0), new Point(5, 0)
        });

        PointList convexHull = pl.convexHull();
        PointList combined = pl.unify(convexHull);
        System.out.println("Original PointList = " + pl);
        System.out.println("Convex Hull = " + convexHull);
        printAsciiGraphic(pl, 12, 8);
        printAsciiGraphic(convexHull, 12, 8);
        printAsciiGraphic(combined, 12, 8);
    }

    // Method to test trivial cases.
    private static void testTrivialCases() {
        PointList pl = new PointList();

        addPointsTo(pl, new Point[]{new Point(0, 0), new Point(1, 1)});
        PointList convexHull = pl.convexHull();
        PointList combined = pl.unify(convexHull);
        System.out.println("Original PointList = " + pl);
        System.out.println("Convex Hull = " + convexHull);
        printAsciiGraphic(pl, 2, 2);
        printAsciiGraphic(convexHull, 2, 2);
        printAsciiGraphic(combined, 2, 2);
    }
}
