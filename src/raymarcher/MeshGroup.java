package raymarcher;

import java.awt.Color;

/**
 * A class that allows for seperate meshes and geometry to be combined 
 * into a single mesh using boolean operations, to create more complex
 * geometry.
 * <a href="https://en.wikipedia.org/wiki/Constructive_solid_geometry">Wikipedia Constructive Solid Geometery</a>
 */
public class MeshGroup implements Mesh{
    
    /** The value for a boolean union operation */
    public static final int UNION = 0;
    /** The value for a boolean subtraction operation */
    public static final int SUBTRACT = 1;
    /** The value for a boolean intersection operation */
    public static final int INTERSECTION = 2;
    /** The color of the mesh group */
    private Color meshColor;
    /** The meshgroup created for the given meshes */
    private Mesh meshGroup;


    /**
     * Creates a mesh group with one of three boolean operations
     * @param mesh1 The first mesh in for the boolean operation
     * @param mesh2 The second mesh in for the boolean operation
     * @param operation The operation used to create the group
     * @param meshColor The color of the new mesh group
     */
    public MeshGroup(Mesh mesh1, Mesh mesh2, int operation, Color meshColor) {

        if (operation == 0) {
            meshGroup = new UnionGroup(mesh1, mesh2, meshColor);
        }
        if (operation == 1) {
            meshGroup = new SubtractGroup(mesh1, mesh2, meshColor);
        }
        if (operation == 2) {
            meshGroup = new IntersectionGroup(mesh1, mesh2, meshColor);
        }

        this.meshColor = meshColor;
    }


    /**
     * @param meshColor the color to set this mesh group to
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
        return meshGroup.sdf(position);
    }

    /**
     * An inner class representing a mesh group made with the union operation
     */
    public class UnionGroup implements Mesh{

        /** The first mesh to be used for the operation in this mesh group */
        private Mesh mesh1;
        /** The second mesh to be used for the operation in this mesh group */
        private Mesh mesh2;
        /** The color of the mesh group */
        private Color meshColor;

        /**
         * Creates a union between two meshes
         * @param mesh1 The first mesh to be used for the operation in this mesh group
         * @param mesh2 The second mesh to be used for the operation in this mesh group
         * @param meshColor The color of the mesh group
         */
        public UnionGroup(Mesh mesh1, Mesh mesh2, Color meshColor) {
            this.mesh1 = mesh1;
            this.mesh2 = mesh2;
            this.meshColor = meshColor;
        }

        @Override
        public Color getMeshColor() {
            return meshColor;
        }

        @Override
        public double sdf(Vector3 position) {
            
            return (Math.min(mesh1.sdf(position), mesh2.sdf(position)));
        }
    }

    /**
     * An inner class representing a mesh group made with the subtract operation
     */
    public class SubtractGroup implements Mesh{

        /** The first mesh to be used for the operation in this mesh group */
        private Mesh mesh1;
        /** The second mesh to be used for the operation in this mesh group */
        private Mesh mesh2;
        /** The color of the mesh group */
        private Color meshColor;

        /**
         * Creates a subtraction between two meshes
         * @param mesh1 The first mesh to be used for the operation in this mesh group
         * @param mesh2 The second mesh to be used for the operation in this mesh group
         * @param meshColor The color of the mesh group
         */
        public SubtractGroup(Mesh mesh1, Mesh mesh2, Color meshColor) {
            this.mesh1 = mesh1;
            this.mesh2 = mesh2;
            this.meshColor = meshColor;
        }

        @Override
        public Color getMeshColor() {
            return meshColor;
        }

        @Override
        public double sdf(Vector3 position) {
            
            return (Math.max(mesh1.sdf(position) * -1, mesh2.sdf(position)));
        }
    }

    /**
     * An inner class representing a mesh group made with the intersection operation
     */
    public class IntersectionGroup implements Mesh{

        /** The first mesh to be used for the operation in this mesh group */
        private Mesh mesh1;
        /** The second mesh to be used for the operation in this mesh group */
        private Mesh mesh2;
        /** The color of the mesh group */
        private Color meshColor;

        /**
         * Creates a intersection between two meshes
         * @param mesh1 The first mesh to be used for the operation in this mesh group
         * @param mesh2 The second mesh to be used for the operation in this mesh group
         * @param meshColor The color of the mesh group
         */
        public IntersectionGroup(Mesh mesh1, Mesh mesh2, Color meshColor) {
            this.mesh1 = mesh1;
            this.mesh2 = mesh2;
            this.meshColor = meshColor;
        }

        @Override
        public Color getMeshColor() {
            return meshColor;
        }

        @Override
        public double sdf(Vector3 position) {
            
            return (Math.max(mesh1.sdf(position), mesh2.sdf(position)));
        }
    }
}
