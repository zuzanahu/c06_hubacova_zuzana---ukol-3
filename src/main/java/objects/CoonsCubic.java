package objects;

import transforms.Cubic;
import transforms.Mat4Transl;
import transforms.Point3D;

public class CoonsCubic extends Object3D{
    public CoonsCubic() {
        super();

        Cubic cubic = new Cubic(Cubic.COONS,
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
        this.setModelMatrix(this.getModelMatrix().mul(new Mat4Transl(0, 0, 3)));

        this.setColor(Colors.ORANGE);
    };
}
