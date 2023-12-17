package objects;

import transforms.*;

import java.util.List;

public class Cuboid extends Object3D {
    public Cuboid() {
        super(
                List.of(
                        new Point3D(0, 0, 0),
                        new Point3D(1, 0, 0),
                        new Point3D(1, 1, 0),
                        new Point3D(0, 1, 0),
                        new Point3D(0, 0, 2),
                        new Point3D(1, 0, 2),
                        new Point3D(1, 1, 2),
                        new Point3D(0, 1, 2) ),
                List.of(0, 1, 1, 2, 2, 3, 3, 0,
                        4, 5, 5, 6, 6, 7, 7, 4,
                        0, 4, 1, 5, 2, 6, 3, 7),
                new Mat4Identity(),
                Colors.YELLOW
        );
        //transformations
        this.setModelMatrix(this.getModelMatrix().mul(new Mat4Transl(3, 0, 0)).mul(new Mat4RotY(Math.PI / 150)));
    };
}
