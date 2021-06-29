package raymarcher.meshes;

import raymarcher.Mesh;
import raymarcher.Vector3;

public class Sphere implements Mesh{

    /**The position of the sphere's center */
    private Vector3 position;
    /**The radius of the sphere */
    private double radius;

    /**
     * Create a sphere mesh 
     * @param position The position in 3D space to center the sphere
     * @param radius The radius of the sphere
     */
    public Sphere(Vector3 position, double radius) {
        this.position = position;
        this.radius = Math.abs(radius);
    }
    

    /**
     * @return the current position of the center of the sphere
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * @param position the new center of the sphere
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * @return the sphere's current radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius the new radius of the sphere
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    
    @Override
    public double sdf(Vector3 position) {
        
        return (this.position.subtract(position).length() - radius);
    }

}
