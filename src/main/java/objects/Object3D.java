package objects;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Object3D {
    private  List<Point3D> vertexBuffer;
    private  List<Integer> indexBuffer;
    public enum Colors {
        PINK,
        LIGHT_BLUE,
        BLACK,
        RED,
        YELLOW,
        WHITE,
        GREEN,
        BLUE,
        PURPLE
    };
    private HashMap<Colors, Integer> colorsToValues = new HashMap<Colors, Integer>(
            Map.of(
                Colors.PINK, 0xFFFF69B4,
                Colors.LIGHT_BLUE, 0xFFADD8E6,
                Colors.BLACK, 0x00000000,
                Colors.RED, 0x00ff0000,
                Colors.YELLOW, 0xFFFFD700,
                Colors.WHITE, 0xFFFFFFFF,
                Colors.GREEN, 0x000000ff,
                Colors.BLUE, 0x0000ff00,
                Colors.PURPLE, 0xFF800080
            )
    );

    private int color;
    /**
     * Model matrix, that represents transformations of the object. If the object is rotated or translated etc.
     */
    private  Mat4 modelMatrix;

    /**
     * Constructor.
     * @param vertexBuffer vertexBuffer
     * @param indexBuffer indexBuffer
     * @param modelMatrix modelMatrix for object transformations such as translations, rotations etc.
     */
    public Object3D(final List<Point3D> vertexBuffer,
                    final List<Integer> indexBuffer,
                    final Mat4 modelMatrix, Colors color) {
        this.vertexBuffer = vertexBuffer;
        this.indexBuffer = indexBuffer;
        this.modelMatrix = modelMatrix;
        this.color = colorsToValues.get(color);
    }

    public Object3D(final List<Point3D> vertexBuffer,
                    final List<Integer> indexBuffer,
                    final Mat4 modelMatrix) {
        this.vertexBuffer = vertexBuffer;
        this.indexBuffer = indexBuffer;
        this.modelMatrix = modelMatrix;
        this.color = colorsToValues.get(Colors.WHITE);
    }
    public Object3D() {
        this.vertexBuffer = new ArrayList<>();
        this.indexBuffer = new ArrayList<>();
        this.modelMatrix = new Mat4Identity();
        this.color = colorsToValues.get(Colors.WHITE);
    }
    public List<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public Mat4 getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Mat4 modelMatrix) {this.modelMatrix = modelMatrix;}

    public int getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = colorsToValues.get(color);
    }
}
