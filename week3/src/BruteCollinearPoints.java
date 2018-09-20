/**
 * Created by thassan on 9/20/18.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatCodePointException;
import java.util.IllegalFormatException;

public class BruteCollinearPoints {

    private Point[] thePoints;

    /**
     * finds all line segments containing 4 points
     *
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
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
        thePoints = new Point [points.length];
        // create a deep copy
        for ( int i = 0; i < points.length ; i++) {
            thePoints[i] = points[i];
        }

    }


    // @todo write a more elegant solution that the following 4-for loop
    private ArrayList<PointTuples> nPointsChoose4(Point[] points) {

        ArrayList<PointTuples> tuples = new ArrayList<PointTuples>(points.length);

        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        tuples.add(new PointTuples(
                                points[i],
                                points[j],
                                points[k],
                                points[l]));
                    }
                }
            }
        }
        return tuples;
    }


    /**
     * the number of line segments
     *
     * @return
     */
    public int numberOfSegments() {
        return this.segments().length;
    }

    /**
     * the line segments
     *
     * @return
     */
    public LineSegment[] segments() {

        ArrayList<LineSegment> collectionOfLineSegments = new ArrayList<LineSegment>();
        ArrayList<PointTuples> validCombinationOfPoints = nPointsChoose4(thePoints);

        for (PointTuples aPointTuple : validCombinationOfPoints) {
            if (aPointTuple.isCollinear()) {
                LineSegment aLineSegment = new LineSegment(
                        aPointTuple.getFirstAscendingSortedEndPoint(),
                        aPointTuple.getLastAscendingSortedEndPoint());

                collectionOfLineSegments.add(aLineSegment);
            }
        }

        return collectionOfLineSegments.toArray(new LineSegment[collectionOfLineSegments.size()]);
    }

}

class PointTuples {

    private final Point m_points[];

    public PointTuples(final Point a, final Point b, final Point c, final Point d) {
        m_points = new Point[]{a, b, c, d};
        Arrays.sort(m_points);  // sort by ascending order. ultimately call in compareTo of the point obj.
    }

    public Point getFirstAscendingSortedEndPoint() {
        return m_points[0];
    }

    public Point getLastAscendingSortedEndPoint() {
        return m_points[3];
    }

    public boolean isCollinear() {
        // the points have already been sorted in the ctor.
        // so now basically now check slopeOrder against the firstEndPoint
        Point firstEndPoint = getFirstAscendingSortedEndPoint();

        double secondPointSlope = firstEndPoint.slopeTo(m_points[1]);
        return ((secondPointSlope == firstEndPoint.slopeTo(m_points[2])) &&
                (secondPointSlope == firstEndPoint.slopeTo(m_points[3])));
    }
}


