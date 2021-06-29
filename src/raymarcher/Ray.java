package raymarcher;
/**
 * Represents a ray shot from the camera to calculate the 
 * value of a screen pixel
 */
public class Ray {
    
    /**The direction in which this ray will be cast */
    private Vector3 direction;
    /**The ray's starting position */
    private Vector3 position;
    /**The maximum distance the ray will render objects up to */
    private double renderDistance;

    /**
     * Creates a ray with a given start position and direction
     * @param position the origin point of the ray in 3D space
     * @param direction the direction in which the ray marches
     * @param renderDistance the maximum distance the ray will render objects up to
     */
    public Ray(Vector3 position, Vector3 direction, double renderDistance) {

        this.position = position;
        this.direction = direction.getUnitVector();
    }

    public int calculate() {
        return 4;
    }
    
}
