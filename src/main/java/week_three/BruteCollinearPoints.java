package week_three;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

  private final List<LineSegment> lineSegments;

  public BruteCollinearPoints(Point[] points) {
    if (points == null || points.length == 0) {
      throw new IllegalArgumentException();
    }

    Point[] copy = Arrays.copyOf(points, points.length);
    lineSegments = new ArrayList<>();

    checkPointsForDuplicatesAndNullValues(points);

    // My eyes are bleeding.
    // (points.length - 3) there to avoid ArrayIndexOutOfBounds
    Arrays.sort(copy);
    for (int i = 0; i < copy.length; i++) {
      for (int j = i + 1; j < copy.length; j++) {
        for (int k = j + 1; k < copy.length; k++) {
          for (int l = k + 1; l < copy.length; l++) {
            Point p = copy[i], q = copy[j], r = copy[k], s = copy[l];
            if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
              lineSegments.add(new LineSegment(p, s));
            }
          }
        }
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
