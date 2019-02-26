package week_three;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

  private List<LineSegment> lineSegments;

  public BruteCollinearPoints(Point[] points) {
    if(points == null || points.length == 0) {
      throw new IllegalArgumentException();
    }

    Point[] copy = Arrays.copyOf(points, points.length);
    lineSegments = new ArrayList<>();

    checkPointsForDuplicatesAndNullValues(points);

    //My eyes are bleeding.
    //(points.length - 3) there to avoid ArrayIndexOutOfBounds
    for (int first = 0; first < copy.length - 3; first++) {
      for (int second = first + 1; second < copy.length - 2; second++) {
        double slopeFirstSecond = copy[first].slopeTo(copy[second]);
        for (int third = second + 1; third < copy.length - 1; third++) {
          //compare slopes between first and second and first and third
          double slopeFirstThird = copy[first].slopeTo(copy[third]);
          if(slopeFirstSecond == slopeFirstThird) {
            for (int fourth = third + 1; fourth < copy.length; fourth++) {
              //compare slopes between first and second and first and fourth
              double slopFirstFourth = copy[first].slopeTo(copy[fourth]);
              if(slopeFirstSecond == slopFirstFourth) {
                //all slopes are equal, so points are collinear
                lineSegments.add(new LineSegment(copy[first], copy[fourth]));
              }
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
    return  lineSegments.toArray(new LineSegment[lineSegments.size()]);
  }

  private void checkPointsForDuplicatesAndNullValues(Point[] points) {
    for (int i = 0; i < points.length; i++) {
      if(points[i] == null) {
        throw new IllegalArgumentException("There is a null point.");
      }
      if (points[i].compareTo(points[i+1]) == 0) {
        throw new IllegalArgumentException("You have duplicate points.");
      }
    }
  }

}
