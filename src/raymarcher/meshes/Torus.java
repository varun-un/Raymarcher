package raymarcher.meshes;

import java.awt.Color;

import raymarcher.Mesh;
import raymarcher.Vector3;

/**
 * An implementation of the Mesh interface that renders a torus
 */
public class Torus implements Mesh {

    /**The position of the mesh's center */
    private Vector3 position;
    /** The size of the outer radius of the torus */
    private double radius;
    /** The thickness of the torus */
    private double thickness;
    /**The color of the mesh */
    private Color meshColor;

    /**
     * Create a torus mesh
     * @param position The position in 3D space to center the torus
     * @param radius The outer radius of the torus
     * @param thickness The thickness of the torus
     * @param meshColor The color of the torus
     */
    public Torus(Vector3 position, double radius, double thickness , Color meshColor) {
        this.position = position;
        this.radius = radius;
        this.thickness = thickness;
        this.meshColor = meshColor;
    }
    

    /**
     * @return the current position of the center of the torus
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * @param position the new center of the torus
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * @return the torus' current outer radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius the new outer radius for the torus
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * @return the torus' current thickness 
     */
    public double getThickness() {
        return thickness;
    }

    /**
     * @param thickness the new thickness for the torus
     */
    public void setThickness(double thickness) {
        this.thickness = thickness;
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
        
        double pxz = Math.sqrt(p.getX() * p.getX() + p.getZ() * p.getZ()) - radius;
        double length = Math.sqrt(pxz * pxz + p.getY() * p.getY());
        return length - thickness;
    }

}
