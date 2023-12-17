import objects.*;
import rasterize.LineRasterizer;
import rasterize.Raster;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;
import transforms.Vec3D;
import java.util.List;

public class VisualizationPipeline {
    private final Raster raster;
    private final LineRasterizer lineRasterizer;
    private Mat4 view = new Mat4Identity();
    private Mat4 proj = new Mat4Identity();
    int color;
    public VisualizationPipeline(Raster raster, LineRasterizer lineRasterizer) {
        this.raster = raster;
        this.lineRasterizer = lineRasterizer;
    }
    public void setView(Mat4 view) {
        this.view = view;
    }
    public void setProj(Mat4 proj) {
        this.proj = proj;
    }

    /**
     * To render every object in the Scene.
     * @param scene a scene to render
     */
    public void render(Scene scene){
        for (Object3D object3D : scene.getObject3DS()){
            render(object3D);
        }
    }

    /**
     * To render Object3D objects.
     * @param object3D object to render
     */
    public void render(final Object3D object3D){
        Mat4 transformation;
        if (object3D instanceof Axes) {
            transformation = view.mul(proj);
            //multiply every vertex with the transformation matrix
            final List<Point3D> transformedVertices = object3D.getVertexBuffer().stream().map(point -> point.mul(transformation)).toList();

            // connect vertexes into lines using indexBuffer and render the lines onto the raster
            for(int i = 0; i<object3D.getIndexBuffer().size()-1; i+=2) {
                int indexStart = object3D.getIndexBuffer().get(i);
                int indexEnd = object3D.getIndexBuffer().get(i + 1);
                if(i == 0) {
                    object3D.setColor(Object3D.Colors.RED);
                    color = object3D.getColor();
                }
                if(i == 2) {
                    object3D.setColor(Object3D.Colors.GREEN);
                    color = object3D.getColor();
                }
                if(i == 4) {
                    object3D.setColor(Object3D.Colors.BLUE);
                    color = object3D.getColor();
                }
                Point3D startPoint = transformedVertices.get(indexStart);
                Point3D endPoint = transformedVertices.get(indexEnd);

                Vec3D startVec = new Vec3D();
                Vec3D endVec = new Vec3D();

                if (startPoint.dehomog().isPresent()) {
                    startVec = startPoint.dehomog().get();
                }

                if (endPoint.dehomog().isPresent()) {
                    endVec = endPoint.dehomog().get();
                }
                
                if (isNotInView(startVec) || isNotInView(endVec)) {
                    return;
                }
                drawLine(startVec, endVec);
            }

        } else {
            transformation = object3D.getModelMatrix().mul(view).mul(proj);

            //multiply every vertex with the transformation matrix
            final List<Point3D> transformedVertices = object3D.getVertexBuffer().stream().map(point -> point.mul(transformation)).toList();

            // connect vertexes into lines using indexBuffer and render the lines onto the raster
            for(int i = 0; i<object3D.getIndexBuffer().size()-1; i+=2) {
                int indexStart = object3D.getIndexBuffer().get(i);
                int indexEnd = object3D.getIndexBuffer().get(i + 1);
                color = object3D.getColor();
                Point3D startPoint = transformedVertices.get(indexStart);
                Point3D endPoint = transformedVertices.get(indexEnd);

                Vec3D startVec = new Vec3D();
                Vec3D endVec = new Vec3D();

                if (startPoint.dehomog().isPresent()) {
                    startVec = startPoint.dehomog().get();
                }

                if (endPoint.dehomog().isPresent()) {
                    endVec = endPoint.dehomog().get();
                }

                if (isNotInView(startVec) || isNotInView(endVec)) {
                    return;
                }
                drawLine(startVec, endVec);
            }
        }
    }
    private boolean isNotInView(Vec3D vec){
        return vec.getX()<-1||vec.getX()>1||vec.getY() <-1||vec.getY()>1||vec.getZ()<0||vec.getZ()>1;
    }
    private void drawLine(Vec3D startVec, Vec3D endVec) {
        int x1 = (int) ((startVec.getX() + 1) * (raster.getWidth() - 1) / 2);
        int x2 = (int) ((endVec.getX() + 1) * (raster.getWidth() - 1) / 2);
        int y1 = (int) ((1 - startVec.getY()) * (raster.getHeight() - 1) / 2);
        int y2 = (int) ((1 - endVec.getY()) * (raster.getHeight() - 1) / 2);

        lineRasterizer.drawLine(x1, y1, x2, y2, color, raster);
    }
}
