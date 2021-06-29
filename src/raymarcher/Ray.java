package raymarcher;

import java.awt.Color;
import java.util.ArrayList;

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
    /**The maximum error for which a ray will approximate an intersection */
    private double epsilon;

    /**
     * Creates a ray with a given start position and direction
     * @param position the origin point of the ray in 3D space
     * @param direction the direction in which the ray marches
     * @param renderDistance the maximum distance the ray will render objects up to
     */
    public Ray(Vector3 position, Vector3 direction, double renderDistance) {

        this.position = position;
        this.direction = direction.getUnitVector();
        this.renderDistance = renderDistance;
        this.epsilon = .001;
    }
    

    /**
     * @return the current direction the ray is being cast in
     */
    public Vector3 getDirection() {
        return direction;
    }

    /**
     * @param direction the new direction for the ray to be cast in
     */
    public void setDirection(Vector3 direction) {
        this.direction = direction.getUnitVector();
    }

    /**
     * @return the current position the ray is cast from
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * @param position the new starting position of the ray
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * @return the current maximum distance the ray will render to
     */
    public double getRenderDistance() {
        return renderDistance;
    }

    /**
     * @param renderDistance the new maximum distance the ray will render
     */
    public void setRenderDistance(double renderDistance) {
        this.renderDistance = renderDistance;
    }

    /**
     * @return the distance away from the object the ray will
     * consider an intersection
     */
    public double getEpsilon() {
        return epsilon;
    }

    /**
     * @param epsilon the distance away from the object the ray will
     * consider an intersection
     */
    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }


    public int calculate(Scene scene) {
        ArrayList<Mesh> meshes = scene.getMeshes();
        Vector3 marchPosition = position.clone();

        double distToScene = epsilon + 1;
        double distTraveled = 0;

        //keep marching until mesh is hit
        while (distToScene > epsilon && distTraveled < renderDistance) {             

            double minDist = meshes.get(0).sdf(marchPosition);
            for (int i = 1; i < meshes.size(); i++) {
                double sdf = meshes.get(i).sdf(marchPosition);
                if (sdf < minDist) {
                    minDist = sdf;
                }
            }

            distToScene = minDist;
            distTraveled += distToScene;
            marchPosition.addInPlace(direction.multiply(distToScene));
        }


        if (distTraveled > renderDistance)
            return scene.getWorldColor();
        else
            return Color.RED.getRGB();

    }


}
