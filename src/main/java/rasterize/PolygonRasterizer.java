package rasterize;

import objects.Line;
import objects.Polygon;
import transforms.Point2D;

import java.util.List;

public class PolygonRasterizer {

    private final LineRasterizer rasterizer;

    public PolygonRasterizer( LineRasterizer rasterizer) {
        this.rasterizer = rasterizer;
    }

    /**
     * This function draws a polygon
     * Visually connects polygon's nodes using line rasterization and also connects the first and last node to ensure that the polygon border is enclosed
     * @param polygon polygon to draw
     * @param color border color
     */

    public void drawPolygon(Polygon polygon, int color, Raster raster) {
        List<Point2D> nodes = polygon.getPoints();
        if (nodes.size() > 1) {
            for (int i = 0; i < nodes.size() - 1; i++) {
                Point2D a = nodes.get(i);
                Point2D b = nodes.get(i + 1);
                rasterizer.rasterize(new Line(a, b, color), raster );
            }
            // Connect the last and first points to close the polygon
            Point2D firstPoint = nodes.get(0);
            Point2D lastPoint = nodes.get(nodes.size() - 1);
            rasterizer.rasterize(new Line(lastPoint, firstPoint, color), raster);
        }
    }
}