package week_three;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

  private final List<LineSegment> lineSegments;

  public FastCollinearPoints(Point[] points) {
    if (points == null || points.length == 0) {
      throw new IllegalArgumentException();
    }

    checkPointsForDuplicatesAndNullValues(points);

    Point[] copy = Arrays.copyOf(points, points.length);
    lineSegments = new ArrayList<>();

    for (int i = 0; i < copy.length - 3; ++i) {
      //Sort array to some initial order.
      Arrays.sort(copy);

      //Sort all points in relation of current point;
      Arrays.sort(copy, copy[i].slopeOrder());

      //current point is always first (p = 0)
      for (int p = 0, firstIndex = 1, lastIndex = 2; lastIndex < copy.length; ++lastIndex) {
        double slopePF = copy[p].slopeTo(copy[firstIndex]);

        //Searching for last point collinear to p. Collinear points are together because of sorting.
        while (lastIndex < copy.length && (Double.compare(slopePF, copy[p].slopeTo(copy[lastIndex])) == 0)) {
          lastIndex++;
        }

        //Add only those line segments, if p < point[first]
        if (lastIndex - firstIndex >= 3 && copy[p].compareTo(copy[firstIndex]) < 0) {
          lineSegments.add(new LineSegment(copy[p], copy[lastIndex - 1]));
        }

        firstIndex = lastIndex;
      }
    }


  }

  public int numberOfSegments() {
    return lineSegments.size();
  }

  public LineSegment[] segments() {
    return lineSegments.toArray(new LineSegment[lineSegments.size()]);
  }

  private void checkPointsForDuplicatesAndNullValues(Point[] points) {
    for (int i = 0; i < points.length - 1; i++) {
      if (points[i] == null) {
        throw new IllegalArgumentException("There is a null point.");
      }
      if (points[i].compareTo(points[i + 1]) == 0) {
        throw new IllegalArgumentException("You have duplicate points.");
      }
    }
  }

}
