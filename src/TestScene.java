
import java.awt.Color;

import raymarcher.Camera;
import raymarcher.MeshGroup;
import raymarcher.Scene;
import raymarcher.Screen;
import raymarcher.Vector3;
import raymarcher.meshes.Box;
import raymarcher.meshes.Sphere;
import raymarcher.meshes.Torus;
import raymarcher.meshes.TriangularPrism;

public class TestScene {
    
    public static void main(String[] args) {

        Scene myScene = new Scene();
        Box box = new Box(new Vector3(0,0,0), new Vector3(1, 1, 1), Color.BLUE);
        myScene.add(box);
        Sphere sphere = new Sphere(new Vector3(0,0,0), 1, Color.BLACK);

        
        
        Torus torus = new Torus(new Vector3(), 1, .5, Color.RED);
        // myScene.add(torus);
        
        TriangularPrism triangularPrism = new TriangularPrism(new Vector3(), 1, 1, Color.WHITE);
        // myScene.add(triangularPrism);
        
        // myScene.add(new MeshGroup(torus, sphere, MeshGroup.SUBTRACT, Color.RED));


        Camera camera = new Camera(new Vector3(0,0,5), new Vector3(0,0,-1), new Vector3(0, 1, 0), .1, myScene);

        new Screen(900, 600, camera, 10);
    }
}
