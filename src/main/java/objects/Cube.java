package objects;

import transforms.Mat4Identity;
import transforms.Mat4Transl;
import transforms.Point3D;

import java.util.List;

public class Cube extends Object3D {

    public Cube() {
        super(
            List.of(
                new Point3D(0, 0, 0),
                new Point3D(0, 0, 1),
                new Point3D(0, 1, 0),
                new Point3D(0, 1, 1),
                new Point3D(1, 0, 0),
                new Point3D(1, 0, 1),
                new Point3D(1, 1, 0),
                new Point3D(1, 1, 1)),
            List.of(0, 1, 0, 2, 3, 2, 3, 1,
                    4, 5, 4, 6, 7, 5, 7, 6,
                    0, 4, 3, 7, 1, 5, 2, 6),
            new Mat4Identity(),
            Colors.PINK
        );
        // transformations
        this.setModelMatrix(this.getModelMatrix().mul(new Mat4Transl(2, 2, 2)));
    };
}