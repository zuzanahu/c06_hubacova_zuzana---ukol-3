package objects;

import transforms.*;

import java.util.List;

public class FergusonCubic extends Object3D {
    public FergusonCubic() {
        super();

        Cubic cubic = new Cubic(Cubic.FERGUSON,
                new Point3D(-1,1,2),
                new Point3D(5,6,7),
                new Point3D(-10,-5,4),
                new Point3D(1,-1,-2));

        for (double i = 0; i < 1; i+=0.01) {
            getVertexBuffer().add(new Point3D(cubic.compute(i)));
        }
        for (int i = 0; i < getVertexBuffer().size()-1; i++){
            getIndexBuffer().add(i);
            getIndexBuffer().add(i+1);
        }
        // transformations
        this.setModelMatrix(this.getModelMatrix().mul(new Mat4Transl(0, 3, 0)));
    };
}
