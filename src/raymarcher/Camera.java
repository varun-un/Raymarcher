package raymarcher;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Represents the camera in 3D space from which the scene will 
 * be rendered
 */
public class Camera implements KeyListener{
    
    /** The position of the camera in 3D space */
    private Vector3 position;
    /** The vector of the direction the camera is looking. This vector is normal to the screen where the scene is projected onto. */
    private Vector3 direction;
    /** The vector which represents the upwards direction for the camera, perpendicualr to the camera's looking direction */
    private Vector3 upDirection;
    /** The vector which represents the rightward direction of the camera */
    private Vector3 rightDirection;
    /** The distance, in the direction the camera faces, from the camera to the screen where the image is projected upon (similar to
    focal length) */
    private double screenDistance;
    /** The scene which the camera should render */
    private Scene scene;
    /** An array of the rays the camera shoots out, in the order of left to right for each row, from top to bottom */
    private Ray[] rays;
    /** The distance, in scene units between the center of each pixel and the one adjacent to it. Decreasing this has a similar effect
    to increasing screenDistance */
    private double pixelDistance;
    /**The movement speed of the camera, in scene units per frame */
    public double movementSpeed;
    /** The rotation speed of the camera, in radians per frame */
    public double rotationSpeed;
    /**Indicates whether or not the camera is to move in that direction */
    private boolean left, right, forward, back, up, down;
    /**Indicates whether or not the camera is to rotate or tilt in that direction */
    private boolean tiltUp, tiltDown, rotateLeft, rotateRight;


    /**
     * Creates a default Camera at (0,0,0), looking in the 
     * -z direction, with a screen distance of 1, the +y 
     * direction being up, and a screen distance of .1
     */
    public Camera() {
        this(new Vector3(), new Vector3(0, 0, -1), new Vector3(0, 1, 0), .1, new Scene());
    }

    /**
     * Creates a camera in the given position and direction
     * @param position The position of the camera as a vector
     * @param direction The direction the camera is looking at. if
     * a zero vector is passed, a camera will be created looking in
     * the -z direction.
     * @param upDirection The direction that represents "up" for the camera.
     * This direction must be perpendicular to the camera's look direction. If 
     * a non-perpendicular vector is passed, a perpendicular vector will be 
     * used.
     * @param screenDistance The distance, in the direction the camera faces, 
     * from the camera to the screen where the image is projected upon (similar to focal length). This distance
     * must be > 0.
     * @param scene The scene which the camera should render
     */
    public Camera(Vector3 position, Vector3 direction, Vector3 upDirection, double screenDistance, Scene scene) {
        this.position = position;

        //direction vector
        if (direction.equals(0, 0, 0))
            this.direction = new Vector3(0, 0, -1);
        else
            this.direction = direction.getUnitVector();

        //up vector
        if(this.direction.dotProduct(upDirection) != 0) {
            //use cross product of two non parallel vectors to get a perpendicular one
            Vector3 v2 = new Vector3(1, 0, 0);
            if (direction.equals(v2))                                                           //make sure not parallel
                this.upDirection = direction.crossProduct(new Vector3(0,1,0)).getUnitVector(); 
            else
                this.upDirection = direction.crossProduct(direction.add(v2)).getUnitVector(); 
        }
        else
            this.upDirection = upDirection.getUnitVector();

        //right vector
        rightDirection = direction.crossProduct(upDirection);

        //screen distance
        if (screenDistance < 0) 
            this.screenDistance = Math.abs(screenDistance);
        if (screenDistance == 0) 
            this.screenDistance = 1;
        else   
            this.screenDistance = screenDistance;

        //scene
        this.scene = scene;

        //set pixel distance
        this.pixelDistance = 0.00025;

        //set movement speed
        this.movementSpeed = 1/10.0;
        this.rotationSpeed = Math.PI / 50;
    }

    /**
     * @return the position of the camera as a vector
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * @param position the position to set the camera to as a vector
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * @return the direction the camera is facing
     */
    public Vector3 getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set the camera facing to. Cannot
     * be a zero vector
     */
    public void setDirection(Vector3 direction) {
        if (direction.equals(0, 0, 0))
            this.direction = new Vector3(0, 0, -1);
        else
            this.direction = direction.getUnitVector();

        setUpDirection(upDirection);
    }

    /**
     * @return the direction which is upwards for the camera
     */
    public Vector3 getUpDirection() {
        return upDirection;
    }

    /**
     * @param upDirection The direction that represents "up" for the camera.
     * This direction must be perpendicular to the camera's look direction. If 
     * a non-perpendicular vector is passed, a perpendicular vector will be 
     * used.
     */
    public void setUpDirection(Vector3 upDirection) {
        if(this.direction.dotProduct(upDirection) != 0) {
            //use cross product of two non parallel vectors to get a perpendicular one
            Vector3 v2 = new Vector3(1, 0, 0);
            if (direction.equals(v2))                                                           //make sure not parallel
                this.upDirection = direction.crossProduct(new Vector3(0,1,0)).getUnitVector(); 
            else
                this.upDirection = direction.crossProduct(direction.add(v2)).getUnitVector(); 
        }
        else
            this.upDirection = upDirection.getUnitVector();


        rightDirection = direction.crossProduct(upDirection);
    }

    /**
     * @return the distance from the camera to the screen it renders to
     */
    public double getScreenDistance() {
        return screenDistance;
    }

    /**
     * @param screenDistance the distance from the screen to set the camera to, which 
     * must be > 0.
     */
    public void setScreenDistance(double screenDistance) {
        if (screenDistance < 0) 
            this.screenDistance = Math.abs(screenDistance);
        if (screenDistance == 0) 
            this.screenDistance = 1;
        else   
            this.screenDistance = screenDistance;
    }

    /**
     * @return the current scene which the camera will render
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * @param newScene the new scene to replace the current one and for the camera
     * to render
     */
    public void setScene(Scene newScene) {
        this.scene = newScene;
    }

    /**
     * Get the distance between each pixel in the camera. A greater value
     * makes the screen's edges more stretched.
     * @return The current distance, in scene units, between the 
     * center of each pixel and the one adjacent to it. 
     */
    public double getPixelDistance() {
        return pixelDistance;
    }

    /**
     * Set the distance between each pixel in the camera. A greater value
     * makes the screen's edges more stretched.
     * @param pixelDistance The new distance, in scene units, between the 
     * center of each pixel and the one adjacent to it. This value should 
     * be > 0.
     */
    public void setPixelDistance(double pixelDistance) {
        if (pixelDistance < 0) 
            this.pixelDistance = Math.abs(pixelDistance);
        if (pixelDistance == 0) 
            this.pixelDistance = 1;
        else   
            this.pixelDistance = pixelDistance;
    }

    /**
     * @return The current movement speed of the camera, in scene units per frame 
     */
    public double getMovementSpeed() {
        return movementSpeed;
    }

    /**
     * @param movementSpeed The new movement speed to set the camera to, in scene units
     * per frame
     */
    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    /**
     * @return The current rotation speed of the camera, in scene units per frame 
     */
    public double getRotationSpeed() {
        return rotationSpeed;
    }

    /**
     * @param rotationSpeed The new rotation speed to set the camera to, in scene units
     * per frame
     */
    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }
    

    /**
     * Shift's the camera's position by a given vector
     * @param shift The vector to add to the camera's current position
     */
    public void move(Vector3 shift) {
        position.addInPlace(shift);

        for (int i = 0; i < rays.length; i++) {
            rays[i].getPosition().addInPlace(shift);
        }
    }

    /**
     * Rotates the camera around the y-axis by a given amount
     * @param angle The amount to rotate, in radians
     */
    public void rotateY(double angle) {
        direction.rotateInPlaceY(angle);
        upDirection.rotateInPlaceY(angle);
        rightDirection.rotateInPlaceY(angle);

        for (int i = 0; i < rays.length; i++) {
            rays[i].getDirection().rotateInPlaceY(angle);
        } 
    }

    /**
     * Rotates the camera around the x-axis by a given amount
     * @param angle The amount to rotate, in radians
     */
    public void rotateX(double angle) {
        direction.rotateInPlaceX(angle);
        upDirection.rotateInPlaceX(angle);
        rightDirection.rotateInPlaceX(angle);

        for (int i = 0; i < rays.length; i++) {
            rays[i].getDirection().rotateInPlaceX(angle);
        } 
    }



    /**
     * Creates the set or rays to be cast from the camera
     * @param screenWidth The amount of pixels wide the screen/window is
     * @param screenHeight The amount of pixels tall the screen/window is
     * @param renderDistance The maximum distance for a ray to travel before assuming
     * it has hit nothing
     */
    public void createRays(int screenWidth, int screenHeight, double renderDistance) {

        Vector3 screenCenter = position.add(direction.multiply(screenDistance));

        rays = new Ray[screenWidth * screenHeight];

        int i = 0;
        for (double r = screenHeight / 2.0 - .5; r >= screenHeight / -2.0 + .5; r--) {

            //go up/down certain increments of the upDirection vector from screen center for each row
            Vector3 curRowVector = screenCenter.add(upDirection.multiply(pixelDistance * r));

            for (double c = screenWidth / -2.0 + .5; c <= screenWidth / 2.0 - .5; c++) {

                //create the ray
                Vector3 rayPosition = curRowVector.add(rightDirection.multiply(pixelDistance * c));
                rays[i] = new Ray(rayPosition, position.differenceVector(rayPosition), renderDistance);
                i++;
            }
        }
        
    }

    /**
     * Called on every frame refresh to recalculate the screen
     * @param pixels The array of pixel color values, linked to the pixels 
     * of the Screen's BufferedImage
     */
    public void render(int[] pixels) {

        //check for movement
        if (forward) {
            move(direction.multiply(movementSpeed));
        }
        if (back) {
            move(direction.multiply(-1 * movementSpeed));
        }
        if (left) {
            move(rightDirection.multiply(-1 * movementSpeed));
        }
        if (right) {
            move(rightDirection.multiply(movementSpeed));
        }
        if (up) {
            move(upDirection.multiply(movementSpeed));
        }
        if (down) {
            move(upDirection.multiply(-1 * movementSpeed));
        }

        //check for rotation
        if (tiltUp) {
            rotateX(rotationSpeed);
        }
        if (tiltDown) {
            rotateX(rotationSpeed * -1);
        }
        if (rotateLeft) {
            rotateY(rotationSpeed);
        }
        if (rotateRight) {
            rotateY(rotationSpeed * -1);
        }

        //render rays
        for (int i = 0; i < rays.length; i++) {
            pixels[i] = rays[i].calculate(scene);
        }

    }

    /**
     * Detects when and how to move the camera
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if((e.getKeyCode() == KeyEvent.VK_A))
			left = true;
		if((e.getKeyCode() == KeyEvent.VK_D))
			right = true;
		if((e.getKeyCode() == KeyEvent.VK_W))
			forward = true;
		if((e.getKeyCode() == KeyEvent.VK_S))
			back = true;
        if((e.getKeyCode() == KeyEvent.VK_SHIFT))
			down = true;
        if((e.getKeyCode() == KeyEvent.VK_SPACE))
			up = true;
        if((e.getKeyCode() == KeyEvent.VK_UP))
			tiltUp = true;
        if((e.getKeyCode() == KeyEvent.VK_DOWN))
			tiltDown = true;
        if((e.getKeyCode() == KeyEvent.VK_LEFT))
			rotateLeft = true;
        if((e.getKeyCode() == KeyEvent.VK_RIGHT))
			rotateRight = true;
        
	}

    /**
     * Detects when to stop moving the camera
     */
    @Override
	public void keyReleased(KeyEvent e) {
		if((e.getKeyCode() == KeyEvent.VK_A))
			left = false;
		if((e.getKeyCode() == KeyEvent.VK_D))
			right = false;
		if((e.getKeyCode() == KeyEvent.VK_W))
			forward = false;
		if((e.getKeyCode() == KeyEvent.VK_S))
			back = false;
        if((e.getKeyCode() == KeyEvent.VK_SHIFT))
			down = false;
        if((e.getKeyCode() == KeyEvent.VK_SPACE))
			up = false;
        if((e.getKeyCode() == KeyEvent.VK_UP))
			tiltUp = false;
        if((e.getKeyCode() == KeyEvent.VK_DOWN))
			tiltDown = false;
        if((e.getKeyCode() == KeyEvent.VK_LEFT))
			rotateLeft = false;
        if((e.getKeyCode() == KeyEvent.VK_RIGHT))
			rotateRight = false;
	}

    @Override
    public void keyTyped(KeyEvent e) {}

}
