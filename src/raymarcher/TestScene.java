package raymarcher;

import java.awt.Color;
import java.awt.Point;

import javax.swing.Box;

import raymarcher.meshes.Sphere;

public class TestScene {
    
    public static void main(String[] args) {

        Scene myScene = new Scene();
        Mesh box = new Mesh() {

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
        // myScene.add(box);
        myScene.add(new Sphere(new Vector3(0,3,0), 1, Color.YELLOW));


        Mesh torus = new Mesh() {

            @Override
            public double sdf(Vector3 position) {

                //t = (1,1)
                double pxz = Math.sqrt(position.getX() * position.getX() + position.getZ() * position.getZ()) - 2;
                double length = Math.sqrt(pxz * pxz + position.getY() * position.getY());
                return length - .5;
            }

            @Override
            public Color getMeshColor() {
                return Color.red;
            }

        };
        myScene.add(torus);

        Camera camera = new Camera(new Vector3(0,0,5), new Vector3(0,0,-1), new Vector3(0, 1, 0), .1, myScene);

        Screen game = new Screen(640, 480, camera, 10);
    }
}
