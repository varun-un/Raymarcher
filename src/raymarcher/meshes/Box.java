package raymarcher.meshes;

import java.awt.Color;

import raymarcher.Mesh;
import raymarcher.Vector3;

/**
 * An implementation of the Mesh interface that renders a box (rectangular prism).
 * Can also be used to produce 2D planes or rectangles if an axis' dimensions is 0.
 */
public class Box implements Mesh {

    /**The position of the box's center */
    private Vector3 position;
    /** A vector representing the box's dimensions */
    private Vector3 size;
    /**The color of the mesh */
    private Color meshColor;

    /**
     * Create a box mesh
     * @param position The position in 3D space to center the box
     * @param size A vector to represent the box's dimensions. The x coordinate is
     * the length of the box in the x-axis, the y-coordinate is the height, and the
     * z-coordinate is the width
     * @param meshColor The color of the box
     */
    public Box(Vector3 position, Vector3 size, Color meshColor) {
        this.position = position;
        this.size = size;
        this.meshColor = meshColor;
    }
    

    /**
     * @return the current position of the center of the box
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * @param position the new center of the box
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * @return the box's current size as a vector
     */
    public Vector3 getSize() {
        return size;
    }

    /**
     * @param size A vector to represent the box's new dimensions. The x-coordinate is
     * the length of the box in the x-axis, the y-coordinate is the height, and the
     * z-coordinate is the width
     */
    public void setSize(Vector3 size) {
        this.size = size;
    }

    /**
     * @param meshColor The new color to set the mesh to
     */
    public void setMeshColor(Color meshColor) {
        this.meshColor = meshColor;
    }

    @Override
    public Color getMeshColor() {
        return meshColor;
    }

    
    @Override
    public double sdf(Vector3 position) {

        Vector3 p = position.subtract(this.position);       //translate the ray position to "fake" the mesh position
        
        Vector3 q = p.abs().subtract(size);

        Vector3 w = q.clone();
        if (w.getX() < 0) 
            w.setX(0);
        if (w.getY() < 0) 
            w.setY(0);
        if (w.getZ() < 0) 
            w.setZ(0);

        return (w.length()) + Math.min(Math.max(q.getX(), Math.max(q.getY(), q.getZ())), 0);
    }

}
