package objects;


import transforms.*;

import java.util.List;


public class Tetrahedron extends Object3D {
    public Tetrahedron() {
        super(
                List.of(
                        new Point3D(2, 0, 0),
                        new Point3D(0, 2, 0),
                        new Point3D(-0.73205, -0.73205, 0),
                        new Point3D(0.42265,0.42265,2.3094)
                ),
                List.of(0, 1,
                        0, 2,
                        0, 3,
                        1, 2,
                        3, 1,
                        3, 2),
                new Mat4Identity(),
                Colors.PURPLE
        );
        // transformations
        this.setModelMatrix(this.getModelMatrix().mul(new Mat4Scale(0.5)).mul(new Mat4Transl(-2,-2,-2)));
    };
}

