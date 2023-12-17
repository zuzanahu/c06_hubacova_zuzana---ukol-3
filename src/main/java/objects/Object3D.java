package objects;

import transforms.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Object3D {
    private final List<Point3D> vertexBuffer;
    private final List<Integer> indexBuffer;
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
    private final HashMap<Colors, Integer> colorsToValues = new HashMap<Colors, Integer>(
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

    // methods for transforming the object

    /**
     * Translates the object to the X direction.
     */
    public void moveInDirectionX() {
        this.setModelMatrix(this.getModelMatrix().mul(new Mat4Transl(0.5,0, 0)));
    }
    /**
     * Translates the object to the Y direction.
     */
    public void moveInDirectionY() {
        this.setModelMatrix(this.getModelMatrix().mul(new Mat4Transl(0,0.5, 0)));
    }
    /**
     * Translates the object to the Z direction.
     */
    public void moveInDirectionZ() {
        this.setModelMatrix(this.getModelMatrix().mul(new Mat4Transl(0,0, 0.5)));
    }

    /**
     * Rotates the object around axis X.
     */
    public void rotateAroundAxisX() {
            // save the object's translation vector for translation to the coordinate system's beginning and back to the object's original position
            Vec3D translationVector = this.getModelMatrix().getTranslate();
            // translate the object to the beginning
            Mat4 translationToTheBeginning = new Mat4Transl(translationVector.opposite());
            Mat4 modelMatrixInTheBeginning = this.getModelMatrix().mul(translationToTheBeginning);
            // rotate the object in the beginning
            Mat4 rotationInTheBeginning = modelMatrixInTheBeginning.mul(new Mat4RotX(Math.PI/4));
            // now get back to the object's original position and update the object's model matrix
            this.setModelMatrix(rotationInTheBeginning.mul(new Mat4Transl(translationVector)));
    }

    /**
     * Rotates the object around axis Y.
     */
    public void rotateAroundAxisY() {
            // save the object's translation vector for translation to the coordinate system's beginning and back to the object's original position
            Vec3D translationVector = this.getModelMatrix().getTranslate();
            // translate the object to the beginning
            Mat4 translationToTheBeginning = new Mat4Transl(translationVector.opposite());
            Mat4 modelMatrixInTheBeginning = this.getModelMatrix().mul(translationToTheBeginning);
            // rotate the object in the beginning
            Mat4 rotationInTheBeginning = modelMatrixInTheBeginning.mul(new Mat4RotY(Math.PI/4));
            // now get back to the object's original position and update the object's model matrix
            this.setModelMatrix(rotationInTheBeginning.mul(new Mat4Transl(translationVector)));
    }

    /**
     * Rotates the object around axis Z.
     */
    public void rotateAroundAxisZ() {
            // save the object's translation vector for translation to the coordinate system's beginning and back to the object's original position
            Vec3D translationVector = this.getModelMatrix().getTranslate();
            // translate the object to the beginning
            Mat4 translationToTheBeginning = new Mat4Transl(translationVector.opposite());
            Mat4 modelMatrixInTheBeginning = this.getModelMatrix().mul(translationToTheBeginning);
            // rotate the object in the beginning
            Mat4 rotationInTheBeginning = modelMatrixInTheBeginning.mul(new Mat4RotZ(Math.PI/4));
            // now get back to the object's original position and update the object's model matrix
            this.setModelMatrix(rotationInTheBeginning.mul(new Mat4Transl(translationVector)));
    }

    /**
     * Makes the object bigger (zoom/scale).
     */
    public void makeBigger() {
        this.setModelMatrix(this.getModelMatrix().mul(new Mat4Scale(2,2, 2)));
    }
    /**
     * Makes the object smaller (zoom/scale).
     */
    public void makeSmaller() {
        this.setModelMatrix(this.getModelMatrix().mul(new Mat4Scale(0.5,0.5, 0.5)));
    }

}
