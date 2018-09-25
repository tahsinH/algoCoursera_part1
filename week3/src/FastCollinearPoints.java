import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by thassan on 9/20/18.
 */

public class FastCollinearPoints {


    private Point[] thePoints;

    /**
     * finds all line segments containing 4 or more points
     *
     * @param points
     */
    public FastCollinearPoints(final Point[] points) {
        // throw if args, or any point inside points collection is null
        // or  if the argument to the constructor contains a repeated point
        if (points == null) {
            throw new IllegalArgumentException("Argument to BruteCollinearPoints cannot be null");
        }

        for (Point aPoint : points) {
            if (aPoint == null) {
                throw new IllegalArgumentException("a Point inside the points array found to be null ");
            }
        }

        Arrays.sort(points);
        // check adjacent points in the sorted array.
        // if the adjacent points are equal , throw exception
        for (int i = 0; i < points.length - 1; i++) {
            Point currPoint = points[i];
            if (currPoint.compareTo(points[i + 1]) == 0) {
                // cannot override the equals method in Point, due to assignment requirements.
                // so using compareTo instead.
                throw new IllegalArgumentException("Duplicated entries in given points.");
            }
        }

        thePoints = Arrays.copyOf(points, points.length);
    }

    /**
     * the number of line segments
     *
     * @return
     */
    public int numberOfSegments() {
        return this.segments().length;
    }

    public LineSegment[] segments() {
        ArrayList<LineSegment> collectionOfAllLineSegments = new ArrayList<LineSegment>();


        for (int i = 0; i < thePoints.length; i++) {
            collectionOfAllLineSegments.addAll(getSegmentWithOnePoint(thePoints[i]));
        }
        return collectionOfAllLineSegments.toArray(new LineSegment[collectionOfAllLineSegments.size()]);
    }

    private ArrayList<LineSegment> getSegmentWithOnePoint(Point currPoint) {
        ArrayList<LineSegment> collectionOfLineSegments = new ArrayList<LineSegment>();

        Point[] pointsSlopeOrder = Arrays.copyOf(thePoints, thePoints.length);
        Arrays.sort(pointsSlopeOrder, currPoint.slopeOrder()); // sort by slopeorder, uses mergesort for object sort, which is stable sort

        /*
        StdOut.println("xxxxxxxxxxxxxxx");
        for(int j = 0; j < pointsSlopeOrder.length; j ++)
        {
            StdOut.println( j + " "  + currPoint.slopeTo(pointsSlopeOrder[j]));
        }
        StdOut.println("xxxxxxxxxxxxxxx");
        */
        // after the slope is ordered , the DOUBLE.NEGATIVE_INFINITY, i.e. the same point
        // would be the first element. As such start the indexing at 1,
        int i = 1; // currPoint
        while (i < pointsSlopeOrder.length) {
            double currSlope = currPoint.slopeTo(pointsSlopeOrder[i]);
            int matchingSlopeCount = 0;
            for (int j = i + 1; // start at the index after currSlope
                 j < pointsSlopeOrder.length;
                 j++)
            {
                if (currSlope != currPoint.slopeTo(pointsSlopeOrder[j])){
                    break;
                }
                matchingSlopeCount++;
            }


            if (matchingSlopeCount >= 2 && // there is atleast 3 more matching slopes, i.e. we have 4 collinear points
                    (currPoint.compareTo(pointsSlopeOrder[i]) == -1)
                    ){
                LineSegment e = new LineSegment(currPoint, pointsSlopeOrder[i + matchingSlopeCount]);
                collectionOfLineSegments.add(e);
            }
            i = i + matchingSlopeCount + 1;
        }
        return collectionOfLineSegments;
    }
}
