package objects;

import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.List;

public class Axes extends Object3D{
    public Axes() {
        super(
                List.of(
                        new Point3D(0, 0, 0),
                        new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0),
                        new Point3D(0, 0, 1)),
                List.of(
                        0, 1, 0,2, 0,3),
                new Mat4Identity());
    };

}
