import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;
    private final int y;

    private class SlopeOrder implements Comparator<Point> {
        public int compare (Point v, Point w) {
            if (slopeTo(v) < slopeTo(w)) return -1;
            if (slopeTo(v) > slopeTo(w)) return +1;
            return 0;
        }
    }

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that.x == this.x && that.y != this.y)
            return Float.POSITIVE_INFINITY;
        if (that.x == this.x && that.y == this.y)
            return Float.NEGATIVE_INFINITY;
        if (that.y == this.y)
            return 0.0;
        return (that.y - this.y) * 1.0 / (that.x - this.x) * 1.0;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y == that.y && this.x < that.x) return -1;
        if (this.y > that.y) return +1;
        if (this.y == that.y && this.x > that.x) return +1;
        return 0;
    }

    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}