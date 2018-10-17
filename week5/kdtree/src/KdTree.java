/**
 * Created by thassan on 10/16/18.
 */

import edu.princeton.cs.algs4.*;


public class KdTree {

    private static class Node {
        Node(Point2D aP, RectHV aRect, Node aLb, Node aRt) {
            p = aP;
            rect = aRect;
            lb = aLb;
            rt = aRt;
        }

        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }

    private Node root = null;
    private int theSize = 0;

    /*
     * construct an empty set of points
     */
    public KdTree() {

    }

    /*
     * is the set empty?
     */
    public boolean isEmpty() {
        return theSize == 0;
    }



    /*
     * number of points in the set
     */
    public int size() {
        return theSize;
    }


    private Node insert(Node n, Point2D p, int level) {

        if (n == null) {
            return new Node(p, null, null, null);
        }

        // a faster way to do modulous to find even or odd level
        // https://stackoverflow.com/questions/7342237/check-whether-number-is-even-or-odd
        if ((level & 1) != 0) {
            if (p.x() < n.p.x()) {
                n.lb = insert(n.lb, p, level + 1);
            } else {
                n.rt = insert(n.rt, p, level + 1);
            }
        } else {
            if (p.y() < n.p.y()) {
                n.lb = insert(n.lb, p, level + 1);
            } else {
                n.rt = insert(n.rt, p, level + 1);
            }
        }
        return n;
    }

    /*
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        ++theSize;
        root = insert(root, p, 1); // start at level 1;
    }


    private Node contains(Node n, Point2D p, int level) {
        // a faster way to do modulous to find even or odd level
        // https://stackoverflow.com/questions/7342237/check-whether-number-is-even-or-odd

        if (n == null) {
            return null;
        }
        if (p.equals(n.p)) {
            return n;
        }
        Node foundNode = null;

        if ((level & 1) != 0) {
            if (p.x() < n.p.x()) {
                foundNode = contains(n.lb, p, level + 1);
            } else {
                foundNode = contains(n.rt, p, level + 1);
            }
        } else {
            if (p.y() < n.p.y()) {
                foundNode = contains(n.lb, p, level + 1);
            } else {
                foundNode = contains(n.rt, p, level + 1);
            }
        }
        return foundNode;
    }

    /*
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (isEmpty()) {
            return false;
        }
        return contains(root, p, 1) != null;
    }


    private void draw(Node n) {
        if (n == null)
            return;
        n.p.draw();
        draw(n.lb);
        draw(n.rt);
    }

    /*
     * draw all points to standard draw
     */
    public void draw() {
        draw(root);
    }

    /*
     *  all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {

        Stack<Point2D> inRange = new Stack<Point2D>();
        return inRange;
    }

    /*
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        throw new java.lang.UnsupportedOperationException();
    }

    /*
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {
        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        StdOut.println("========Contains the point? for input10.txt (expect true)");
        Point2D containsThePoints = new Point2D(0.083, 0.510);
        StdOut.println(kdtree.contains(containsThePoints));

        StdOut.println("========Contains the point? for input10.txt (expect false)");
        Point2D doesNotcontainThePoints = new Point2D(0.416, 0.362);
        StdOut.println(kdtree.contains(doesNotcontainThePoints));

        StdOut.println("========size for input10.txt (expect 10)");
        StdOut.println(kdtree.size());
    }
}
