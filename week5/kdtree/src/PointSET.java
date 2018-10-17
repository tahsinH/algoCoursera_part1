import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

    private final TreeSet<Point2D> mPointSet;

    /*
     * construct an empty set of points
     */
    public PointSET() {
        mPointSet = new TreeSet<>();
    }

    /*
     * is the set empty?
     */
    public boolean isEmpty() {
        return mPointSet.isEmpty();
    }

    /*
     * number of points in the set
     */
    public int size() {
        return mPointSet.size();
    }

    /*
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        mPointSet.add(p);
    }

    /*
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return mPointSet.contains(p);
    }

    /*
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p : mPointSet) {
            p.draw();
        }
    }

    /*
     *  all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Stack<Point2D> pointsInRange = new Stack<Point2D>();
        for (Point2D p : mPointSet) {
            if (rect.contains(p)) {
                pointsInRange.push(p);
            }
        }
        return pointsInRange;

    }

    /*
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {

        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (mPointSet.isEmpty()) {
            return null;
        }
        double currDistSqr = Double.POSITIVE_INFINITY;
        Point2D currNeighbour = null;
        for (Point2D aPoint : mPointSet) {
            if (p.distanceSquaredTo(aPoint) < currDistSqr) {
                currDistSqr = p.distanceSquaredTo(aPoint);
                currNeighbour = aPoint;
            }
        }
        return currNeighbour;
    }

    /*
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {

    }
}
