package raymarcher;

/**
 * Represents a vector in 3D Euclidean space (x, y, z). Can be used to represent a 
 * point in space or to represent a direction.
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
     * Creates a default zero Vector3 at (0, 0, 0)
     */
    public Vector3() {
        this(0,0,0);
    }

    /**
     * Creates a Vector3 in the direction of the given unit 
     * vector and with the given lentgh (magnitude)
     * @param unitVector The unit vector for the Vector3
     * @param length The length (magnitude) of the Vector3 to be created
     */
    public Vector3(Vector3 unitVector, double length) {
        Vector3 vector = unitVector.multiply(length);
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    /**
     * Creates a Vector3 with the passed coordinates
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
     * Replaces the current vector with the values of a new Vector
     * (Changes the current vector)
     * @param newVector The new Vector for which to match the current vector to
     */
    public void replace(Vector3 newVector) {
        if (!equals(newVector)){
            this.x = newVector.x;
            this.y = newVector.y;
            this.z = newVector.z;
        }
    }

    /**
     * Replaces the current vector with the values of a new Vector
     * (Changes the current vector)
     * @param x The new x-coordinate of the vector
     * @param y The new y-coordinate of the vector
     * @param z The new z-coordinate of the vector
     */
    public void replace(double x, double y, double z) {
        if (!equals(x, y, z)){
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    /**
     * Gives the length of the vector. This is the same as the
     * vector's magnitude.
     * @return The length of the vector
     */
    public double length() {
        return (Math.sqrt(x*x + y*y + z*z));
    }

    /**
     * Performs a scalar multiplication on the vector.
     * Multiplying by negative numbers will negate the vector.
     * @param factor The constant by which to multiply the vector with
     * @return The new scaled Vector3
     */
    public Vector3 multiply(double factor) {
        return (new Vector3(x * factor, y * factor, z * factor));
    }

    /**
     * Scales the current vector by performing a scalar multiplication on it.
     * Scaling by negative numbers will negate the vector.
     * (Changes the current vector)
     * @param factor The constant by which to multiply the vector with
     * @return The new current vector
     */
    public Vector3 scale(double factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
        return this;
    }

    /**
     * Returns the absolute value of the current vector
     * @return the current vector, but with no negative coordinates
     */
    public Vector3 abs() {
        return (new Vector3(Math.abs(x), Math.abs(y), Math.abs(z)));
    }

    /**
     * Gives the unit vector for the current vector.
     * Unit vectors will maintain direction and have a length of
     * 1 unit.
     * @return The unit vector for the current vector. If the vector has a length of 0,
     * A Vector3 at (0,0,0) will be returned.
     */
    public Vector3 getUnitVector() {
        try {
            return (multiply(1 / length()));
        }
        catch(Exception e) {
            return (new Vector3());
        }
    }

    /**
     * Multiplies each of the vector's components using scalar multiplication by 
     * their respective component in the factor vector.
     * i.e. <1,2,3>.scaleByVector(<3,2,1>) = <3, 4, 3>
     * @param factor The Vector3 by which to scale the first vector
     * @return The new scaled Vector3
     */
    public Vector3 multiplyByVector(Vector3 factor) {
        return (new Vector3(x * factor.x, y * factor.y, z * factor.z));
    }

    /**
     * Scales each of the current vector's components using scalar multiplication by 
     * their respective component in the factor vector.
     * (Changes the current vector)
     * i.e. <1,2,3>.scaleByVector(<3,2,1>) = <3, 4, 3>
     * @param factor The Vector3 by which to scale the first vector
     * @return The new current vector
     */
    public Vector3 scaleByVector(Vector3 factor) {
        this.x *= factor.x;
        this.y *= factor.y;
        this.z *= factor.z;
        return this;
    }

    /**
     * Finds the factor by which a scalar multiplication must be applied to the 
     * first vector in order to get the passed vector
     * @param v2 The second vector to compare the first vector to.
     * @return The factor of times bigger (longer) the second vector is than the first.
     * If the length of v2 > length of the first vector, the return value will be > 1, and 
     * if it is smaller, the a value between 0 and 1 will be returned. If the two vectors
     * aren't parallel or aren't both non-zero in length, 0 will be returned.
     */
    public double findFactor(Vector3 v2) {
        if (angleBetween(v2) != 0) {
            return (v2.length() / length());
        }
        return 0;
    }

    /**
     * Gives the resultant of adding two vectors together
     * @param v2 The vector to be added to the first vector
     * @return The resultant Vector3
     */
    public Vector3 add(Vector3 v2) {
        return(new Vector3(x + v2.x, y + v2.y, z + v2.z));
    }

    /**
     * Translates the current vector by adding a given vector to it. 
     * (Changes the current vector)
     * @param v2 The vector to add to the current vector
     * @return The new current vector
     */
    public Vector3 addInPlace(Vector3 v2) {
        this.x += v2.x;
        this.y += v2.y;
        this.z += v2.z;
        return this;
    }

    /**
     * Gives the resultant vector of subtracting two vectors
     * @param v2 The vector to be subtracted from the first vector
     * @return The resultant Vector3
     */
    public Vector3 subtract(Vector3 v2) {
        return(new Vector3(x - v2.x, y - v2.y, z - v2.z));
    }

    /**
     * Translates the current vector by subtracting a given vector from it. 
     * (Changes the current vector)
     * @param v2 The vector to subtract from the current vector
     * @return The new current vector
     */
    public Vector3 subtractInPlace(Vector3 v2) {
        this.x -= v2.x;
        this.y -= v2.y;
        this.z -= v2.z;
        return this;
    }

    /**
     * Translates the current vector by the given values
     * (Changes the current vector)
     * @param x The amount to translate by in the x-axis
     * @param y The amount to translate by in the y-axis
     * @param z The amount to translate by in the z-axis
     * @return The new current vector
     */
    public Vector3 translate(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Gives the vector difference from the first vector to the second vector.
     * i.e. to move from point A to point B, you'd translate by Vector C.
     * Vector C = A.differenceVector(B)
     * @param v2 The second vector
     * @return The vector of the difference, whose length is equal to the distance between 
     * the two vectors and is parallel to the direction of the first vector to the second.
     */
    public Vector3 differenceVector(Vector3 v2) {
        return (v2.subtract(this));
    }

    /**
     * Gives the distance between two points represented by vectors
     * @param v2 The second point to find the distance to
     * @return The distance to the second vector
     */
    public double distance(Vector3 v2) {
        return (Math.sqrt((v2.x - x) * (v2.x - x) + (v2.y - y) * (v2.y - y) + (v2.z - z) * (v2.z - z)));
    }

    /**
     * Gives the dot product between two vectors
     * @param v2 The second vector to use
     * @return The dot product result
     */
    public double dotProduct(Vector3 v2) {
        return (x * v2.x + y * v2.y + z * v2.z);
    }

    /**
     * Gives the vector that results from the cross product of two vectors.
     * The cross product vector will be orthogonal to both initial vectors.
     * @param v2 The second vector to use
     * @return The cross product resultant vector
     */
    public Vector3 crossProduct(Vector3 v2) {
        return (new Vector3(y * v2.z - z * v2.y, z * v2.x - x * v2.z, x * v2.y - y * v2.x));
    }

    /**
     * Gives the angle between two vectors, in radians.
     * @param v2 The second vector to form the angle
     * @return The angle, in radians, as a value in the range of [0.0, pi]. If 
     * either vector has a length of 0, 0 will be returned.
     */
    public double angleBetween(Vector3 v2) {
        try {
            return (Math.acos(dotProduct(v2) / (length() * v2.length())));
        }
        catch(Exception e) {
            return 0;
        }
    }

    /**
     * Gives the angle between the vector and the XZ plane, in radians
     * @return The angle, in radians, as a value in the range of [0.0, pi]. If 
     * the vector has a length of 0, 0 will be returned.
     */
    public double angleToXZ() {
        return (angleBetween(new Vector3(x, 0, z)));
    }

    /**
     * Gives the angle between the vector and the XY plane, in radians
     * @return The angle, in radians, as a value in the range of [0.0, pi]. If 
     * the vector has a length of 0, 0 will be returned.
     */
    public double angleToXY() {
        return (angleBetween(new Vector3(x, y, 0)));
    }

    /**
     * Gives the angle between the vector and the YZ plane, in radians
     * @return The angle, in radians, as a value in the range of [0.0, pi]. If 
     * the vector has a length of 0, 0 will be returned.
     */
    public double angleToYZ() {
        return (angleBetween(new Vector3(0, y, z)));
    }

    /**
     * Gives the vector result of a projection of the first
     * vector onto the second vector
     * @param v2 The second vector on which the first vector is
     * projected onto
     * @return The vector component of the first vector in the direction
     * of the second vector. If the length of the second vector equals 0,
     * a zero vector will be returned.
     */
    public Vector3 projection(Vector3 v2) {
        try {
            return (v2.multiply(dotProduct(v2) / (v2.length() * v2.length())));
        }
        catch(Exception e) {
            return (new Vector3());
        }
    }

    /**
     * Gives the scalar projection of the first vector onto the second vector. The 
     * unit vector of the second vector multiplied by the scalar projection yields
     * the vector projection of the two vectors.
     * @param v2 The second vector on which the first vector is
     * projected onto
     * @return The scalar projection of the first vector in the direction
     * of the second vector. If the length of the second vector equals 0,
     * the length of the first vector will be reurned.
     */
    public double scalarProjection(Vector3 v2) {
        return (length() * Math.cos(angleBetween(v2)));
    }

    /**
     * Give the result vector of a Euler rotation about the x-axis
     * @param angle The angle to rotate, in radians
     * @return The vector that results from this rotation
     */
    public Vector3 rotateX(double angle) {
        return (new Vector3(x, y * Math.cos(angle) - z * Math.sin(angle), y * Math.sin(angle) + z * Math.cos(angle)));
    }

    /**
     * Performs a Euler rotation on the current vector about the x-axis
     * (Changes the current vector)
     * @param angle The angle to rotate, in radians
     * @return The new current vector
     */
    public Vector3 rotateInPlaceX(double angle) {
        y = y * Math.cos(angle) - z * Math.sin(angle);
        z = y * Math.sin(angle) + z * Math.cos(angle);
        return this;
    }

    /**
     * Give the result vector of a Euler rotation about the y-axis
     * @param angle The angle to rotate, in radians
     * @return The vector that results from this rotation
     */
    public Vector3 rotateY(double angle) {
        return (new Vector3(x * Math.cos(angle) + z * Math.sin(angle), y, z * Math.cos(angle) - x * Math.sin(angle)));
    }

    /**
     * Performs a Euler rotation on the current vector about the y-axis
     * (Changes the current vector)
     * @param angle The angle to rotate, in radians
     * @return The new current vector
     */
    public Vector3 rotateInPlaceY(double angle) {
        x = x * Math.cos(angle) + z * Math.sin(angle);
        z = z * Math.cos(angle) - x * Math.sin(angle);
        return this;
    }

    /**
     * Give the result vector of a Euler rotation about the z-axis
     * @param angle The angle to rotate, in radians
     * @return The vector that results from this rotation
     */
    public Vector3 rotateZ(double angle) {
        return (new Vector3(x * Math.cos(angle) - y * Math.sin(angle), x * Math.sin(angle) + y * Math.cos(angle), z));
    }

    /**
     * Performs a Euler rotation on the current vector about the z-axis
     * (Changes the current vector)
     * @param angle The angle to rotate, in radians
     * @return The new current vector
     */
    public Vector3 rotateInPlaceZ(double angle) {
        x = x * Math.cos(angle) - y * Math.sin(angle);
        y = x * Math.sin(angle) + y * Math.cos(angle);
        return this;
    }

    /**
     * Checks if two vectors are equal to each other
     * @param v2 The second vector to compare to
     * @return Whether or not the two vectors are equal
     */
    public boolean equals(Vector3 v2) {
        return (x == v2.x && y == v2.y && z == v2.z);
    }

    /**
     * Checks if the current vectors is equal to the vector
     * with the given coordinates
     * @param x The x-coordinate of the second vector to compare to
     * @param y The y-coordinate of the second vector to compare to
     * @param z The z-coordinate of the second vector to compare to
     * @return Whether or not the two vectors are equal
     */
    public boolean equals(double x, double y, double z) {
        return (this.x == x && this.y == y && this.z == z);
    }

    /**
     * Clones the current vector
     * @return a copy of the current vector
     */
    @Override
    public Vector3 clone() {
        return (new Vector3(x, y, z));
    }

    @Override
    public String toString() {
        return ("(" + x + ", " + y + ", " + z + ")");
    }
}
