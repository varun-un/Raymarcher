package raymarcher;

import java.awt.Color;

import raymarcher.meshes.Box;
import raymarcher.meshes.Sphere;
import raymarcher.meshes.Torus;
import raymarcher.meshes.TriangularPrism;

public class TestScene {
    
    public static void main(String[] args) {

        Scene myScene = new Scene();
        Box box = new Box(new Vector3(0,0,0), new Vector3(1, 1, 1), Color.BLUE);
        // myScene.add(box);
        // myScene.add(new Sphere(new Vector3(0,0,0), 1, Color.BLACK));


        Torus torus = new Torus(new Vector3(), 2, 1, Color.RED);
        // myScene.add(torus);

        TriangularPrism triangularPrism = new TriangularPrism(new Vector3(), 1, 1, Color.WHITE);
        myScene.add(triangularPrism);

        Camera camera = new Camera(new Vector3(0,0,5), new Vector3(0,0,-1), new Vector3(0, 1, 0), .1, myScene);

        new Screen(900, 600, camera, 10);
    }
}
