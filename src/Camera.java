/**
 * Represents the camera in 3D space from which the scene will 
 * be rendered
 */
public class Camera {
    
    /** The position of the camera in 3D space */
    private Vector3 position;
    /**The vector of the direction the camera is looking */
    private Vector3 direction;

    /**
     * Creates a default Camera at (0,0,0) and looking in the 
     * -z direction
     */
    public Camera() {
        this(new Vector3(), new Vector3(0, 0, -1));
    }

    /**
     * Creates a camera in the given position and direction
     * @param position The position of the camera as a vector
     * @param direction The direction the camera is looking at. if
     * a zero vector is passed, a camera will be created looking in
     * the -z direction.
     */
    public Camera(Vector3 position, Vector3 direction) {
        this.position = position;

        if (direction.equals(0, 0, 0))
            this.direction = new Vector3(0, 0, -1);
        else
            this.direction = direction.getUnitVector();
    }
}
