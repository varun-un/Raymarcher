package raymarcher;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;


/**
 * Creates a Screen object, or window, to host the Raymarcher engine and to
 * render onto.
 */
public class Screen extends JFrame implements Runnable {

    /**	Default Serial Version ID for JFrame */
	private static final long serialVersionUID = 1L;
	
	
	/** The thread on which this engine will be executed */
    private Thread thread;
    /** Whether or not the renderer is running */
    private boolean isRunning;
    /** The BufferedImage upon which the render is painted */
	private BufferedImage image;
    /** The array of pixel color values, connected to the BufferedImage displayed on
    screen so that any changes to the array are reflected on screen */
	public int[] pixels;
    /** The camera from which to render the scene */
    private Camera camera;
    /** The framerate, in frames per second, at which to run the renderer */
    public double framerate;


    /**
     * Creates a Screen window and sets up the rendering for the camera and scene
     * @param screenWidth The width of the window and render, in pixels
     * @param screenHeight The height of the window and render, in pixels
     * @param camera The camera from which to render the scene
     * @param framerate The framerate, in frames per second, at which to run the renderer
     */
    public Screen(int screenWidth, int screenHeight, Camera camera, double framerate) {
        thread = new Thread(this);
		image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        this.camera = camera;
        camera.createRays(screenWidth, screenHeight, 20);
        addKeyListener(camera);
        this.framerate = framerate;

        setSize(screenWidth, screenHeight);
		setResizable(false);
		setTitle("Raymarcher Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		start();
    }

    /**
     * @return The current framerate, in frames per second, which the engine is
     * running at
     */
    public double getFrameRate() {
        return framerate;
    }

    /**
     * @param fps The new framerate, in frames per second, at which to run 
     * the renderer
     */
    public void setFrameRate(double fps) {
        if (fps < 0) 
            framerate = Math.abs(fps);
        if (fps == 0) 
            framerate = 10;
        else   
            framerate = fps;
    }

    /**
     * Start the thread and the engine
     */
    private synchronized void start() {
		isRunning = true;
		thread.start();
	}
	
	/**
     * Stop the thread and the engine
     */
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

    /**
     * Called every frame to render a buffer and then display it
     */
    public void render() {
		//makes it look better/smoother
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		bs.show();
	}

    @Override
    public void run() {
        long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / framerate;        //Call according to framerate
		double delta = 0;
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta = delta + ((now-lastTime) / ns);
			lastTime = now;
			while (delta >= 1)      //Make sure update is only happening 60 times a second
			{
				delta--;
                camera.render(pixels);
			}
			render();   //displays to the screen unrestricted time
		}
	}
    
}

