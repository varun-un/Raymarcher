package raymarcher.meshes;

import java.awt.Color;

import raymarcher.Mesh;
import raymarcher.Vector3;

/**
 * An implementation of the Mesh interface that renders an equilateral triangular prism 
 */
public class TriangularPrism implements Mesh {

    /**The position of the triangular prism */
    private Vector3 position;
    /** The height of the triangular prism's traingle base */
    private double height;
    /** The length of the prism (distance between bases) */
    private double length;
    /**The color of the mesh */
    private Color meshColor;

    /**
     * Create a triangular prism mesh
     * @param position The position in 3D space to place the prism
     * @param height The height of the triangular prism's traingle base
     * @param length The length of the prism
     * @param meshColor The color of the mesh
     */
    public TriangularPrism(Vector3 position, double height, double length , Color meshColor) {
        this.position = position;
        this.height = height;
        this.length = length;
        this.meshColor = meshColor;
    }
    

    /**
     * @return the current position of the center of the mesh
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * @param position the new center of the prism
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * @return The current height of the triangular prism's traingle base
     */
    public double getHeight() {
        return height;
    }

    /**
     * @param height the new height of the triangular prism's traingle base
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return the prism's current length 
     */
    public double getLength() {
        return length;
    }

    /**
     * @param length the new length for the prism
     */
    public void setLength(double length) {
        this.length = length;
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
        
        Vector3 q = p.abs();
        return Math.max(q.getZ() - 2, Math.max(q.getX() * 0.866025 + p.getY() * .5, -1 * p.getY()) - 1 * .5);
    }

}
