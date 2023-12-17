package rasterize;


import objects.Line;


public class LineRasterizer {
    Raster raster;
    public LineRasterizer() {

    }

    /**
     * Draws a line segment onto a raster, the algorithm in this method is called "Trivial line algorithm for all quadrants"
     *
     * @param x1     coordinate of the first point ranging from 0 to the width of the raster - 1
     * @param x2     coordinate of the second point ranging from 0 to the width of the raster - 1
     * @param y1     coordinate of the first point ranging from 0 to the height of the raster - 1
     * @param y2     coordinate of the second point ranging from 0 to the height of the raster - 1
     * @param color  color of the line
     */
    public void drawLine( double x1, double y1, double x2, double y2, int color, Raster raster) {

        //checks if k == infinity, or in other words if x1 and x2 are the same
        if (x1 == x2) {
            // Vertical line, handle separately
            double minY = Math.min(y1, y2);
            double maxY = Math.max(y1, y2);

            for (double r = minY; r <= maxY; r++) {
                raster.setColor((int) x1, (int) r, color);
                System.out.println("x:" + x1 + " y: " + r);
            }

        } else {

            final double k =  (y2 - y1) / (x2 - x1);
            final double q = y1 - k * x1;

            if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
                //c == x axis, r == y axis
                if (x2 > x1) {
                    for (double c = x1; c <= x2; c++) {
                        double r =  (k * c + q);
                        raster.setColor((int) c,(int) r, color);
                        System.out.println("x:" + c + " y: " + r);
                    }
                } else {
                    for (double c = x1; x2 <= c; c--) {
                        double r =  (k * c + q);
                        raster.setColor((int) c, (int) r, color);
                        System.out.println("x:" + c + " y: " + r);
                    }
                }
            } else {
                //c == x axis, r == y axis

                if (y2 > y1) {
                    for (double r = y2; y1 <= r; r--) {
                        double c =  ((r - q) / k);
                        raster.setColor((int)c,(int) r, color);
                        System.out.println("x:" + c + " y: " + r);
                    }
                } else {
                    for (double r = y1; y2 <= r; r--) {
                        double c =  ((r - q) / k);
                        raster.setColor((int)c, (int) r, color);
                        System.out.println("x:" + c + " y: " + r);
                    }
                }
            }
        }
    }

    public void rasterize(Line line, Raster raster) {
        drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2(), line.getColor(), raster);
    }
}







