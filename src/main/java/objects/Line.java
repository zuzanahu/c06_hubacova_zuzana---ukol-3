package objects;

import transforms.Point2D;

public class Line {

    private final double  x1, x2, y1, y2;
    private int color;


    public Line(double x1, double y1, double x2, double y2, int color) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
    }
    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public Line(Point2D p1, Point2D p2, int color) {
        this.x1 = p1.getX();
        this.x2 = p2.getX();
        this.y1 = p1.getY();
        this.y2 = p2.getY();
        this.color = color;
    }
    public Line(Point2D p1, Point2D p2) {
        this.x1 = p1.getX();
        this.x2 = p2.getX();
        this.y1 = p1.getY();
        this.y2 = p2.getY();
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public int getColor() {
        return color;
    }

    /**
     * Calculates if the point is inside (like on the left side on the line) or outside (right side of the line)
     * @param point point, which we want to check
     * @return true or false
     */
    public boolean isInside(Point2D point) {

        Point2D n = new Point2D( y2 - y1 ,- (x2 - x1));
        Point2D v = new Point2D(point.getX() - x1, point.getY() - y1);

        double dotProduct = (v.getX() * n.getX() + v.getY() * n.getY());

        return dotProduct > 0;
    }
}

