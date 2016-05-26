package game.engine2D;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class AbstractGame {
	private final List<Screen> screens;
	private boolean running;
	private JFrame window;
	private BufferedImage sprite_sheet;
	private List<Thread> threads;

	/**
	 * makes a new window for the game and instantiate the lists
	 * 
	 * @param title
	 *            the title of the frame
	 * @param width
	 *            the width of the frame
	 * @param height
	 *            the height of the frame
	 * @param resizable
	 *            should it be resizable
	 */
	public AbstractGame(String title, int width, int height, boolean resizable) {
		this.window = new JFrame(title);
		window.setSize(width, height);
		window.setResizable(resizable);
		screens = new ArrayList<>();
		threads = new ArrayList<>();
	}

	/**
	 * 
	 * @param res
	 *            the path to the sprite sheet
	 * @throws IOException
	 *             if the path entered is invalid
	 */
	public void setSpriteSheet(String res) throws IOException {
		sprite_sheet = ImageIO.read(getClass().getResourceAsStream(res));
	}

	/**
	 * 
	 * @param x
	 *            the x position on the sheet
	 * @param y
	 *            the y position on the sheet
	 * @param width
	 *            the width on the sheet
	 * @param height
	 *            the height in the sheet
	 * @return the cropped sprite from the sheet
	 * @throws NullPointerException
	 *             if there is no sprite_sheet set
	 * @throws RasterFormatException
	 *             if the coordinate + width or height is of range
	 */
	public Image getSprite(int x, int y, int width, int height) throws NullPointerException, RasterFormatException {
		return sprite_sheet.getSubimage(x, y, width, height);
	}

	/**
	 * The main loop which runs at a constant 60 fps
	 */
	public void start() {
		window.setVisible(true);
		running = true;
		int fps = 60; // the refresh rate
		double timePerTick = 1e9 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		while (running) {

			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			if (delta >= 1) {
				gameLoop();
				delta--;
			}
		}
	}

	/**
	 * run's each threads
	 */
	public void run() {
		if (!threads.isEmpty()) {
			for (Thread thread : threads) {
				thread.run();
			}
		}
	}

	/**
	 * This adds a thread to the game which can be used to run inside the loop
	 * before refreshing
	 * 
	 * @param thread
	 *            the thread to add to the game
	 */
	public void addThread(Thread thread) {
		threads.add(thread);
	}

	public void addScreen(Screen screen) {
		screens.add(screen);
	}

	public void setScreen(int index) {
		try {
			window.setContentPane(screens.get(index));
		} catch (IndexOutOfBoundsException e) {
			System.err.println("invalid index");
		}
	}

	public abstract void init();

	public abstract void gameLoop();

	public void setMinimumSize(int width, int height) {
		window.setMinimumSize(new Dimension(width, height));
	}

	public void setMaximumSize(int width, int height) {
		window.setMaximumSize(new Dimension(width, height));
	}

	public int getScreenCount() {
		return screens.size();
	}

	public void pause() {
		running = false;
		onPause();
	}
	
	public JFrame getWindow(){
		return window;
	}

	public abstract void onPause();

	public void addKeyListener(KeyListener listener) {
		window.addKeyListener(listener);
	}

}
