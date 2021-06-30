package raymarcher;

import java.awt.Color;

import raymarcher.meshes.Sphere;

public class TestScene {
    
    public static void main(String[] args) {

        Scene myScene = new Scene();
        Mesh myMesh = new Mesh() {

            @Override
            public double sdf(Vector3 position) {
                
                Vector3 q = (new Vector3(Math.abs(position.getX()), Math.abs(position.getY()), Math.abs(position.getZ())))
                    .subtract(new Vector3(1, 1, 1));

                Vector3 w = q.clone();
                if (w.getX() < 0) 
                    w.setX(0);
                if (w.getY() < 0) 
                    w.setY(0);
                if (w.getZ() < 0) 
                    w.setZ(0);

                return (w.length()) + Math.min(Math.max(q.getX(), Math.max(q.getY(), q.getZ())), 0);
            }

            @Override
            public Color getMeshColor() {
                
                return Color.RED;
            }
            
        };
        myScene.add(myMesh);
        myScene.add(new Sphere(new Vector3(0,2,-.5), 1, Color.YELLOW));

        Camera camera = new Camera(new Vector3(0,0,5), new Vector3(0,0,-1), new Vector3(0, 1, 0), .1, myScene);

        new Screen(640, 480, camera);
    }
}
