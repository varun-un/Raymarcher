
/**
 * Represents a point in 3D Euclidean space (x, y, z) by its vector originating from the origin.
 * A left-hand coordinate system is used, with +x being rightwards, +y being upwards,
 * and +z being backwards.
 */
public class Vector3 {
    
    /**The vector's x-coordinate */
    private double x;
    /**The vector's y-coordinate */
    private double y;
    /**The vector's z-coordinate */
    private double z;

    /**
     * Creates a default Vector3 at (0, 0, 0)
     */
    public Vector3() {
        this(0,0,0);
    }

    /**
     * Creates a Vector3 for the passed coordinates
     * @param x The x-axis coordinate
     * @param y The y-axis coordinate
     * @param z The z-axis coordinate
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Gives the current x-coordinate of the vector
     * @return The current x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gives the current y-coordinate of the vector
     * @return The current y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Gives the current z-coordinate of the vector
     * @return The current z-coordinate
     */
    public double getZ() {
        return z;
    }
    
    /**
     * Gives the current x-coordinate of the vector
     * @param x The new x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gives the current y-coordinate of the vector
     * @param y The new y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gives the current z-coordinate of the vector
     * @param z The new z-coordinate
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Gives the length of the Vector3
     * @return The length of the vector
     */
    public double length() {
        return (Math.sqrt(x*x + y*y + z*z));
    }

    /**
     * Performs a scalar multiplication on the vector
     * @param factor The constant by which to multiply the vector
     * @return The new scaled Vector3
     */
    public Vector3 scale(double factor) {
        return (new Vector3(x * factor, y * factor, z * factor));
    }

    /**
     * Gives the resultant of adding two vectors together
     * @param v2 The vector to be added to the first vector
     * @return The resultant Vector3
     */
    public Vector3 add(Vector3 v2) {
        return(new Vector3(x + v2.getX(), y + v2.getY(), z + v2.getZ()));
    }


}
