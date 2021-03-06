package week_three;

import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {

  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }


  public void draw() {
    StdDraw.point(x, y);
  }

  public void drawTo(Point that) {
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  @Override
  public int compareTo(Point that) {

    if (this.y == that.y && this.x == that.x) {
      return 0;
    }

    if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
      return -1;
    }

    return 1;
  }

  public double slopeTo(Point that) {
    if (this.x == that.x && this.y == that.y) {
      return Double.NEGATIVE_INFINITY;
    }

    double deltaX = (that.x - this.x);
    double deltaY = (that.y - this.y);

    if (deltaX == 0.0) {
      return Double.POSITIVE_INFINITY;
    }

    if (deltaY == 0.0) {
      return +0.0;
    }

    return deltaY / deltaX;
  }

  public Comparator<Point> slopeOrder() {
    return new SlopeComparator();
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }


  private class SlopeComparator implements Comparator<Point> {

    @Override
    public int compare(Point firstPoint, Point secondPoint) {
      double slopeToFirst = slopeTo(firstPoint);
      double slopeToSecond = slopeTo(secondPoint);
      return Double.compare(slopeToFirst, slopeToSecond);
    }
  }
}
