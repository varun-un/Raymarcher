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

    public Screen() {
        thread = new Thread(this);
		image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        setSize(640, 480);
		setResizable(false);
		setTitle("3D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		start();
        Vector3 v = new Vector3(5,4,2);
        System.out.println(v.length());

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
		final double ns = 1000000000.0 / 60.0;//60 times per second
		double delta = 0;
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta = delta + ((now-lastTime) / ns);
			lastTime = now;
			while (delta >= 1)//Make sure update is only happening 60 times a second
			{
				delta--;
			}
			render();//displays to the screen unrestricted time
		}
	}

    public static void main(String[] args) {
        new Screen();
    }
    
}

