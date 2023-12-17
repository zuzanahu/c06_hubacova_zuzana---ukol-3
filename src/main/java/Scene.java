import objects.Object3D;
import transforms.Mat4;

import java.util.ArrayList;

public class Scene {
    private final ArrayList<Object3D> object3DS;

    public Scene() {
        this.object3DS = new ArrayList<>();
    }

    public ArrayList<Object3D> getObject3DS() {
        return object3DS;
    }
    public void addObject3D(Object3D object3D) {
        object3DS.add(object3D);
    }
}
