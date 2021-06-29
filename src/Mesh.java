/**
 * The interface class of the template for each mesh within the scene
 */
public interface Mesh {
    
    /**
     * The signed distance function for the mesh, which tells
     * the mesh's distance to a point in 3D space
     * @param position The vector representation of the point to
     * measure the distance to
     * @return The signed distance of the mesh to the point. Values > 0
     * indicate the point is outside the mesh, values = 0 are points on
     * the surface, and values < 0 are points inside the mesh.
     */
    public double sdf(Vector3 position);
}
