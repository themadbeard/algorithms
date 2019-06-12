package week_five;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

  private TreeSet<Point2D> pointSet;
  // construct an empty set of points
  public PointSET() {
    pointSet = new TreeSet<>();
  }

  // is the set empty?
  public boolean isEmpty() {
    return pointSet.isEmpty();
  }

  // number of points in the set
  public int size() {
    return pointSet.size();
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    pointSet.add(p);
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    return pointSet.contains(p);
  }

  // draw all points to standard draw
  public void draw() {
    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(.01);

    for (Point2D point : pointSet) {
      point.draw();
    }
  }

  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    List<Point2D> range = new ArrayList<>();

    for (Point2D point : pointSet) {
      if (rect.contains(point)) {
        range.add(point);
      }
    }

    return range;
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    if (pointSet.isEmpty()) {
      return null;
    }

    Point2D minPoint = null;

    for (Point2D point : pointSet) {
      if (minPoint == null || p.distanceSquaredTo(point) < minPoint.distanceSquaredTo(point)) {
        minPoint = point;
      }
    }

    return minPoint;
  }

}