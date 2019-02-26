package week_three;

public class LineSegment {

  private final Point q;
  private final Point p;


  public LineSegment(Point p, Point q) {
    this.p = p;
    this.q = q;
  }

  public void draw() {
    p.draw(q);
  }

  @Override
  public String toString() {
    return String.format("%s -> %s", p.toString(), q.toString());
  }
}
