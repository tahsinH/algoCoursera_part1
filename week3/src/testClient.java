/**
 * Created by thassan on 9/20/18.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class testClient {



    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();



        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        StdOut.println(collinear.numberOfSegments());

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        StdOut.println("======");
        // for checking immutability call again
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }

        /*
        Point a = new Point(2,3);
        Point b = new Point(4,3);
        Point [] thePoints = new Point [3];
        thePoints[0] = a;
        thePoints[1] = b;
        thePoints[2] = null;
        BruteCollinearPoints collinearWithNull = new BruteCollinearPoints(thePoints);
        */
    }
}
