package objects;

/**
 * Object that is currently selected in the EDIT mode.
 */
public class SelectedObject3D {
    private Object3D object3D;

    public SelectedObject3D() {
    }

    public void setSelectedObject(Object3D object3D) {
        this.object3D = object3D;
    }

    public Object3D getObject3D() {
        return object3D;
    }
}
