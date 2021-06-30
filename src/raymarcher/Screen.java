package raymarcher;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferStrategy;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;



public class Screen extends JFrame implements Runnable {

    private Thread thread;
    private boolean isRunning;
	private BufferedImage image;
	public int[] pixels;
    private Camera camera;
    public double framerate;

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

    public double getFrameRate() {
        return framerate;
    }

    public void setFrameRate(double fps) {
        if (fps < 0) 
            framerate = Math.abs(fps);
        if (fps == 0) 
            framerate = 10;
        else   
            framerate = fps;
    }

    private synchronized void start() {
		isRunning = true;
		thread.start();
	}
	
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

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
		final double ns = 1000000000.0 / framerate;        //2nd number is framerate
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

