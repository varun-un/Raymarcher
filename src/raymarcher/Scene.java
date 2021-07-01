package raymarcher;

import java.util.ArrayList;
import java.awt.Color;

/**
 * The scene which the camera will render
 */
public class Scene {

    /**A List of the meshes to render in this scene */
    private ArrayList<Mesh> meshes;
    /**The background color of the world */
    private int worldColor;

    /**Creates a default, empty scene with a world color of sky blue */
    public Scene() {
        meshes = new ArrayList<Mesh>();
        worldColor = (new Color(0x87ceeb)).getRGB();
    }
    

    /**
     * @return the list of all meshes currently in the scene
     */
    public ArrayList<Mesh> getMeshes() {
        return meshes;
    }

    /**
     * @param meshes the list of meshes to set as the objects for the scene to render
     */
    public void setMeshes(ArrayList<Mesh> meshes) {
        this.meshes = meshes;
    }

    /**
     * @return the color of the world background as an RGB int in the default sRGB
     * {@link java.awt.image.ColorModel ColorModel}.
     * (Bits 24-31 are alpha, 16-23 are red, 8-15 are green, 0-7 are
     * blue).
     */
    public int getWorldColor() {
        return worldColor;
    }

    /**
     * @param worldColor the color to set the world background to as an int
     * in the default sRGB
     * {@link java.awt.image.ColorModel ColorModel}.
     */
    public void setWorldColor(int worldColor) {
        this.worldColor = worldColor;
    }

    /**
     * @param color the color to set the world background to
     */
    public void setWorldColor(Color color) {
        this.worldColor = color.getRGB();
    }


    /**
     * Adds a mesh to the scene to render
     * @param mesh The mesh to add to the scene
     */
    public void add(Mesh mesh) {
        meshes.add(mesh);
    }

}
