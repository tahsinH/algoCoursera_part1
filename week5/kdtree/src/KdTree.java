/**
 * Created by thassan on 10/16/18.
 */

import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Stack;


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


    private RectHV getLeftRect(RectHV parentRect, Node node, int level) {
        boolean orientation = (level & 1) != 0; // x orientation
        if (orientation) {
            return new RectHV(parentRect.xmin(), parentRect.ymin(), node.p.x(), parentRect.ymax());
        }
        return new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), node.p.y());
    }

    private RectHV getRightRect(RectHV parentRect, Node node, int level) {
        boolean orientation = (level & 1) != 0; // x orientation
        if (orientation) {
            return new RectHV(node.p.x(), parentRect.ymin(), parentRect.xmax(), parentRect.ymax());
        }
        return new RectHV(parentRect.xmin(), node.p.y(), parentRect.xmax(), parentRect.ymax());
    }

    private Node insert(Node n, Point2D p, RectHV aRectHv, int level) {


        if (n !=null && n.p.equals(p))
        {
            return n; // duplicate point
        }

        if (n == null) {
            theSize++;
            return new Node(p, aRectHv, null, null);
        }


        // a faster way to do modulous to find even or odd level
        // https://stackoverflow.com/questions/7342237/check-whether-number-is-even-or-odd
        boolean orientation = (level & 1) != 0;

        if ((orientation && (p.x() < n.p.x())) ||
                (!orientation && (p.y() < n.p.y()))) {
            RectHV leftRect = getLeftRect(aRectHv, n, level);
            n.lb = insert(n.lb, p, leftRect, level + 1);
        } else {
            RectHV rightRect = getRightRect(aRectHv, n, level);
            n.rt = insert(n.rt, p, rightRect, level + 1);
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
        // the root level contains the entire set, which
        // is in the range of 0 to 1
        RectHV rootLevelFieldRect = new RectHV(0, 0, 1, 1);
        root = insert(root, p, rootLevelFieldRect, 1); // start at level 1;
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

        boolean orientation = (level & 1) != 0;

        if ((orientation && (p.x() < n.p.x())) ||
                (!orientation && (p.y() < n.p.y()))) {
            foundNode = contains(n.lb, p, level + 1);
        } else {
            foundNode = contains(n.rt, p, level + 1);
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


    private void drawPoint(Point2D aPoint) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        aPoint.draw();
    }

    private void drawEmbeddedRect(RectHV aRect, int level) {
        boolean orientation = (level & 1) != 0;
        if (orientation) {
            StdDraw.setPenColor(StdDraw.BLUE);
        } else {
            StdDraw.setPenColor(StdDraw.RED);
        }
        StdDraw.setPenRadius();

        aRect.draw();
    }

    private void drawSplittingLines(Point2D p, int level, RectHV embeddedRect) {
        // get the min and max points of splitting lines
        Point2D min, max;
        boolean orientation = (level & 1) != 0;
        if (orientation) {
            StdDraw.setPenColor(StdDraw.RED);
            min = new Point2D(p.x(), embeddedRect.ymin());
            max = new Point2D(p.x(), embeddedRect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            min = new Point2D(embeddedRect.xmin(), p.y());
            max = new Point2D(embeddedRect.xmax(), p.y());
        }

        StdDraw.setPenRadius();
        min.drawTo(max);
    }

    private void draw(Node n, int level) {
        if (n == null)
            return;

        drawPoint(n.p);
        // you can either choose to draw the embedded rectangles
        // or you can choose to draw the splitting lines.
        // the course site seems to prefer drawing the splitting lines,
        // for a different perspective, you can draw the embedded rectangles.
        // probably need to shade/fill the rectangle boxes for better visualizations.
        //drawEmbeddedRect(n.rect, level);
        drawSplittingLines(n.p, level, n.rect);

        draw(n.lb, level + 1);
        draw(n.rt, level + 1);
    }

    /*
     * draw all points to standard draw
     */
    public void draw() {
        //StdDraw.setScale(0, 1);
        StdDraw.setPenColor(StdDraw.BLACK);
        RectHV rootLevelFieldRect = new RectHV(0, 0, 1, 1);
        rootLevelFieldRect.draw();
        draw(root, 1);
    }

    private void range(Stack<Point2D> inRange, RectHV rect, Node n) {
        if (n == null)
        {
            return;
        }
        if (rect.intersects(n.rect)) {
            if (rect.contains(n.p)) {
                inRange.push(n.p);
            }
            range(inRange, rect, n.lb);
            range(inRange, rect, n.rt);
        }
    }

    /*
     *  all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rectToSearch) {
        if (rectToSearch == null) {
            throw new java.lang.IllegalArgumentException();
        }
       //Instead of checking whether the query rectangle intersects the rectangle
        // corresponding to a node, it suffices to check only whether
        // the query rectangle intersects the splitting line segment:
        // if it does, then recursively search both subtrees;
        // otherwise, recursively search the one subtree where points intersecting the query rectangle could be.

        Stack<Point2D> inRange = new Stack<Point2D>();
        range(inRange, rectToSearch, root);
        return inRange;
    }


    private Point2D nearest(Node n, Point2D pointToSearch, int level, Point2D candidatePoint) {

        // to understand how this works in more detail:
        // https://gopalcdas.com/2017/05/24/construction-of-k-d-tree-and-using-it-for-nearest-neighbour-search/

        if (n == null) {
            return candidatePoint;
        }

        //StdOut.println("Point Visited");
        //StdOut.println(n.p);

        if (pointToSearch.equals(n.p)) {
            return n.p;
        }

        if (candidatePoint == null) {
            candidatePoint = n.p;
        }
        double currRectDist = n.rect.distanceSquaredTo(pointToSearch);
        //StdOut.println("The current rect dist");
        //StdOut.println(currRectDist);

        double candidatePointDist = candidatePoint.distanceSquaredTo(pointToSearch);
        // pruning, first search the "possible bounding box"
        if (currRectDist <= candidatePointDist) {

            if (pointToSearch.distanceSquaredTo(n.p) < (pointToSearch.distanceSquaredTo(candidatePoint))) {
                candidatePoint = n.p;
            }


            /* organize method so that it begins by searching for query point
             * It is a crucial performance optimization because the points encountered
             * while exploring the first subtree may enable pruning of the second subtree.
             * For typical inputs, choosing the direction that
             * goes toward the query point makes it more likely
             * that we will encounter points close to the query point.

             * As such, if we see there is a better chance to find nearest using left,
             * we go left.. else right first

             * Furthermore, we do a bounding rectangle check to see whether, there is a lesser chance or no chance
             * of a smaller nearest point
             */


            boolean orientation = (level & 1) != 0;
            if ((orientation && (pointToSearch.x() < n.p.x())) ||
                    (!orientation && (pointToSearch.y() < n.p.y()))) {
                candidatePoint = nearest(n.lb, pointToSearch, level + 1, candidatePoint);
                candidatePoint = nearest(n.rt, pointToSearch, level + 1, candidatePoint);
            } else {
                candidatePoint = nearest(n.rt, pointToSearch, level + 1, candidatePoint);
                candidatePoint = nearest(n.lb, pointToSearch, level + 1, candidatePoint);
            }
        }
        return candidatePoint;
    }


    /*
     * a nearest neighbor in the set to point p; null if the set is empty
     */

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        return nearest(root, p, 1, null);
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

        StdOut.println("========Contains the point? for circle10.txt (expect true)");
        Point2D containsThePoints = new Point2D(0.975528, 0.345492);
        StdOut.println(kdtree.contains(containsThePoints));

        StdOut.println("========Contains the point? for circle10.txt (expect false)");
        Point2D doesNotContainThePoints = new Point2D(0.500000, 0.001000);
        StdOut.println(kdtree.contains(doesNotContainThePoints));

        StdOut.println("========size for circle10.txt (expect 10)");
        StdOut.println(kdtree.size());

        StdOut.println("========nearest point to  p = (0.81, 0.30) for circle10.txt point2d(0.975528 0.345492)");
        Point2D pointToSearch = new Point2D(0.81, 0.30);
        StdOut.println(kdtree.nearest(pointToSearch));
        kdtree.draw();

        StdOut.println("====when the symbol table is empty, nearest point should return null====");
        KdTree kdtreeEmpty = new KdTree();
        Point2D findPoint = new Point2D(0.6, 0.12);
        StdOut.println(kdtreeEmpty.nearest(findPoint));

    }
}
