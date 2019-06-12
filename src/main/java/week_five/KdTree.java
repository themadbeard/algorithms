package week_five;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.List;

public class KdTree {

  private int size = 0;
  private Node root;

  private class Node {

    private final Point2D point;
    private final RectHV rect;

    private Node left, right;

    private final boolean isVertical;
    private static final double POINT_RADIUS = 0.015;
    private static final double LINE_RADIUS = 0.005;

    public Node(Point2D point, RectHV inRect, boolean isVertical) {
      this.point = point;
      this.rect = inRect;
      this.isVertical = isVertical;

      right = left = null;
    }

    /**
     * draw the node
     */
    public void draw() {
      StdDraw.setPenRadius(POINT_RADIUS);
      StdDraw.setPenColor(StdDraw.BLACK);
      point.draw();

      StdDraw.setPenRadius(LINE_RADIUS);
      if (isVertical) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(point.x(), rect.ymin(), point.x(), rect.ymax());
      } else {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(rect.xmin(), point.y(), rect.xmax(), point.y());
      }
    }

    /**
     * get the rectangle associated to point p given parent node n
     */
    public RectHV getRectangle(Point2D point) {
      if (isVertical) {
        return prepareRectForVerticalNode(point);
      } else {
        return prepareRectForHorizontalNode(point);
      }
    }

    private RectHV prepareRectForHorizontalNode(Point2D point) {
      int cmp = Double.compare(point.y(), this.point.y());
      cmp = (cmp == 0 ? Double.compare(point.x(), this.point.x()) : cmp);
      if (cmp < 0) {
        return new RectHV(this.rect.xmin(), this.rect.ymin(), this.point.x(), this.point.y());
      } else {
        return new RectHV(this.rect.xmin(), this.point.y(), this.rect.xmax(), this.rect.ymax());
      }
    }

    private RectHV prepareRectForVerticalNode(Point2D point) {
      int cmp = Double.compare(point.x(), this.point.x());
      cmp = (cmp == 0 ? Double.compare(point.y(), this.point.y()) : cmp);
      if (cmp < 0) {
        return new RectHV(this.rect.xmin(), this.rect.ymin(), this.point.x(), this.rect.ymax());
      } else {
        return new RectHV(this.point.x(), this.rect.ymin(), this.rect.xmax(), this.rect.ymax());
      }
    }
  }

  public KdTree() {
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public boolean contains(Point2D point) {
    return contains(root, point, true);
  }

  public void insert(Point2D point2D) {
    root = insert(root, point2D, null);
  }

  public void draw() {
    StdDraw.clear();
    draw(root);
  }

  /**
   * a nearest neighbor in the set to point p; null if the set is empty
   */
  public Point2D nearest(Point2D point) {
    if (isEmpty()) {
      return null;
    } else {
      Point2D nearest = null;

      nearest = nearest(root, point, nearest);

      return nearest;
    }
  }

  private Point2D nearest(Node node, Point2D queryPoint, Point2D closest) {
    if (node != null) {
      if (closest == null) {
        closest = node.point;
      }

      if (closest.distanceTo(queryPoint) >= node.rect.distanceSquaredTo(queryPoint)) {
        if (node.point.distanceSquaredTo(queryPoint) < closest.distanceSquaredTo(queryPoint)) {
          closest = node.point;
        }
      }

      if (node.right != null && node.right.rect.contains(queryPoint)) {
        closest = nearest(node.right, queryPoint, closest);
        closest = nearest(node.left, queryPoint, closest);
      } else {
        closest = nearest(node.left, queryPoint, closest);
        closest = nearest(node.right, queryPoint, closest);
      }
    }

    return closest;
  }

  public Iterable<Point2D> range(RectHV rect) {
    List<Point2D> range = new ArrayList<>();

    range(root, rect, range);

    return range;
  }

  private Node insert(Node node, Point2D point, Node parent) {
    if (node == null) {
      size++;
      if (parent == null) {
        return new Node(point, new RectHV(0.0, 0.0, 1.0, 1.0), true);
      }
      return new Node(point, parent.getRectangle(point), !parent.isVertical);
    }

    int cmp = compare(point, node.point, node.isVertical);

    if (cmp < 0) {
      node.left = insert(node.left, point, node);
    } else {
      node.right = insert(node.right, point, node);
    }
    return node;
  }

  private int compare(Point2D a, Point2D b, boolean isVertical) {
    int cmp;
    if (isVertical) {
      cmp = Double.compare(a.x(), b.x());

      if (cmp == 0) {
        return Double.compare(a.y(), b.y());
      }
    } else {
      cmp = Double.compare(a.y(), b.y());

      if (cmp == 0) {
        return Double.compare(a.x(), b.x());
      }
    }

    return cmp;
  }

  private boolean contains(Node node, Point2D point, boolean isVertical) {
    while (node != null) {
      int cmp = compare(point, node.point, isVertical);

      if (cmp < 0) {
        return contains(node.left, point, !node.isVertical);
      } else if (cmp > 0) {
        return contains(node.right, point, !node.isVertical);
      } else {
        return true;
      }
    }

    return false;
  }

  private void range(Node node, RectHV rect, List<Point2D> range) {
    if (node == null || !node.rect.intersects(rect)) {
      return;
    }

    if (rect.contains(node.point)) {
      range.add(node.point);
    }

    range(node.left, rect, range);
    range(node.right, rect, range);
  }

  private void draw(Node node) {
    if (node == null) {
      return;
    }

    node.draw();
    draw(node.left);
    draw(node.right);
  }


}
