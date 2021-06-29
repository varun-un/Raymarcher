package raymarcher;

import raymarcher.meshes.Sphere;

public class TestScene {
    
    public static void main(String[] args) {

        Scene myScene = new Scene();
        myScene.add(new Sphere(new Vector3(0,0,0), 1));
        myScene.add(new Sphere(new Vector3(0,2,0), 1));

        Camera camera = new Camera(new Vector3(0, 0, 5), new Vector3(0, 0, -1), new Vector3(0, 1, 0), 1, myScene);

        new Screen(camera);
    }
}
