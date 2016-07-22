/**
 * 
 * @author Jipesh
 */
package game.engine2D;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public abstract class Engine2DGame {

	private final List<Engine2DScreen> screens;
	private final JFrame window;
	private final List<Thread> threads;
	private final HashMap<String, GameArrayList<Engine2DEntity>> entityLists;
	private final Engine2DEventDispatcher eventDispatcher;
	private boolean running, gameover, appRunning;
	private int fps;

	private BufferedImage sprite_sheet;
	private Engine2DScreen currentScreen;
	private Engine2DGameThread gameThread;

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
	public Engine2DGame(String title, int width, int height, boolean resizable) {
		this.window = new JFrame(title);
		this.window.setSize(width, height);
		this.window.setResizable(resizable);
		this.appRunning = true;
		this.entityLists = new HashMap<>();
		this.screens = new ArrayList<>();
		this.threads = new ArrayList<>();
		this.gameThread = new Engine2DGameThread(this, "GameThread");
		this.eventDispatcher = new Engine2DEventDispatcher(this, "GameEventDispatcher");
		this.eventDispatcher.startThread();
		this.eventDispatcher.setPriority(Thread.MAX_PRIORITY);
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
	 * The main loop which start's all the threads and the main game loop as
	 * well as setting the frame visible
	 * 
	 * @param fps
	 *            the target fps for the game
	 * 
	 * @see Engine2DGame#startThreads() startTheads()
	 */
	public void start(int fps) {
		setFPS(fps);
		window.setVisible(true);
		gameover = false;
		running = true;
		gameThread.startThread();
		startThreads();
	}

	/**
	 * starts each threads
	 * 
	 * @see Engine2DGame#addThread(Thread) addThread(Thread)
	 */
	public void startThreads() {
		if (!threads.isEmpty()) {
			for (Thread thread : threads) {
				thread.start();
			}
		}
	}

	/**
	 * This adds a thread to the game which will be started in a new thread
	 * 
	 * @param thread
	 *            the thread to add to the game
	 */
	public void addThread(Thread thread) {
		threads.add(thread);
	}

	/**
	 * 
	 * @param screen
	 *            the screen to add to the list
	 */
	public void addScreen(Engine2DScreen screen) {
		screens.add(screen);
	}

	/**
	 * The method set's the screen to screen from the list with the provide
	 * index
	 * 
	 * @param index
	 *            the index of the screen from the list
	 */
	public void setScreen(int index) {
		if (!screens.isEmpty()) {
			Engine2DScreen screen = null;
			try {
				Engine2DScreen temp = screens.get(index);
				if (temp != null) {
					this.currentScreen = temp;
				}
				window.setContentPane(screens.get(index));
			} catch (IndexOutOfBoundsException e) {
				System.err.println("invalid index");
			}
		}
	}

	/**
	 * The method set's the window's contentPane to the screen provided
	 * 
	 * @param screen
	 *            the screen to set
	 * 
	 * @see Engine2DGame#getWindow() getWindow();
	 */
	public void setScreen(Engine2DScreen screen) {
		if (screen != null) {
			window.setContentPane(screen);
			currentScreen = screen;
		}
	}

	public Engine2DScreen getScreen(int index) {
		try {
			return screens.get(index);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("invalid index");
		}
		return null;
	}

	/**
	 * The method renders graphics on the screen by calling the repaint method
	 * on the current screen if there was no screen added then the content pane
	 * of the window will be re-painted
	 * 
	 * @see Container#repaint() repaint()
	 */
	public void render() {
		if (currentScreen != null) {
			currentScreen.repaint();
		} else {
			window.getContentPane().repaint();
		}

	}

	/**
	 * This is the code that will run when running is true and game over is
	 * false
	 */
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

	void pause() {
		running = false;
		onPause();
	}

	/**
	 * 
	 * @return the JFrame object
	 */
	public JFrame getWindow() {
		return window;
	}

	/**
	 * This is what will run when game over is false and running is also false
	 */
	public abstract void onPause();

	/**
	 * Method that will run when game over is true
	 */
	public abstract void gameOver();

	public void addKeyListener(KeyListener listener) {
		window.addKeyListener(listener);
	}

	public void setDefaultCloseOperation(int operation) {
		window.setDefaultCloseOperation(operation);
	}

	public BufferedImage getSpriteSheet() {
		return sprite_sheet;
	}

	public void setRunning(boolean value) {
		running = value;
	}

	public void setGameOver(boolean value) {
		gameover = value;
	}

	public boolean getRunning() {
		return running;
	}

	public boolean getGameOver() {
		return gameover;
	}

	/**
	 * The method clears the screens list
	 * 
	 * @see Engine2DGame#addScreen(Engine2DScreen) addScreen(Engine2DScreen)
	 * @see Engine2DGame#setScreen(int) setScreen(index)
	 * @see Engine2DGame#setScreen(Engine2DScreen) setScreen(Engine2DScreen)
	 */
	public void clearScreenList() {
		screens.clear();
	}

	/**
	 * stops each thread and then clear's the list
	 * 
	 * @throws InterruptedException
	 *             if thread was interrupted when stopping
	 * 
	 * @see Thread#join() join()
	 * @see Engine2DGame#addThread(Thread) addThread(Thread)
	 */
	public void clearThreadList() throws InterruptedException {
		for (Thread thread : threads) {
			thread.join();
		}
		threads.clear();
	}

	/**
	 * This method add a entity to an GameArrayList by submitting task to the
	 * GameEventDispatcher queue
	 * 
	 * @param <E>
	 *            the type
	 * 
	 * @param list
	 *            the GameArrayList object
	 * @param e
	 *            the element you want to add to the list
	 * 
	 * @see Engine2DGame#submitTask(Runnable) submitTask(Runnable)
	 */
	public <E> void addToGameArrayList(GameArrayList<E> list, E e) {
		submitTask(new Runnable() {

			@Override
			public void run() {
				list.add(e);
			}
		});
	}

	/**
	 * This method removes a entity from the GameArrayList by submitting task to
	 * the GameEventDispatcher queue
	 * 
	 * @param <E>
	 *            the type
	 * 
	 * @param list
	 *            the GameArrayList object
	 * @param e
	 *            the element you want to add to the list
	 * 
	 * @see Engine2DGame#submitTask(Runnable) submitTask(Runnable)
	 */
	public <E> void removeFromGameArrayList(GameArrayList<E> list, E e) {
		submitTask(new Runnable() {

			@Override
			public void run() {
				list.remove(e);
			}
		});
	}

	/**
	 * The methods adds the entity to the list present in the entity HashMap,
	 * this is submitted to the GameEventDispatcher queue
	 * 
	 * @param key
	 *            the key to access the list
	 * @param entity
	 *            the entity to remove from the list
	 * 
	 * @see Engine2DGame#addEntityList(String, GameArrayList)
	 *      addEntityList(String, GameThreadList)
	 * @see Engine2DGame#submitTask(Runnable) submitTask(Runnable)
	 */
	public void addEntityToList(String key, Engine2DEntity entity) {
		submitTask(new Runnable() {

			@Override
			public void run() {
				entityLists.get(key).add(entity);
			}
		});
	}

	/**
	 * The methods removes the entity from a GameArrayList present in game
	 * entity list HashMap, this is submitted to the GameEventDispatcher queue
	 * 
	 * @param key
	 *            the key to access the list
	 * @param entity
	 *            the entity to add to the list
	 * 
	 * @see Engine2DGame#addEntityList(String, GameArrayList)
	 *      addEntityList(String,GameArrayList)
	 * @see Engine2DGame#submitTask(Runnable) submitTask(Runnable)
	 */
	public void removeEntityFromList(String key, Engine2DEntity entity) {
		submitTask(new Runnable() {

			@Override
			public void run() {
				entityLists.get(key).remove(entity);
			}
		});
	}

	/**
	 * This method will end the game thread
	 * 
	 * @throws InterruptedException
	 *             if the thread was interrupted
	 */
	public void stopThread() throws InterruptedException {
		gameThread.stopThread();
	}

	/**
	 * 
	 * @param fps
	 *            the target FPS;
	 */
	protected void setFPS(final int fps) {
		this.fps = fps;
	}

	public int getFPS() {
		return fps;
	}

	/**
	 * 
	 * @param key
	 *            to access the list in future
	 * @param list
	 *            the GameThreadList to add to the entity HashMap
	 */
	protected void addEntityList(String key, GameArrayList<Engine2DEntity> list) {
		this.entityLists.put(key, list);
	}

	/**
	 * 
	 * @param key
	 *            to access the list in future
	 * @param list
	 *            the GameArrayList to remove from the entity HashMap
	 */
	protected void removeEntityList(String key, GameArrayList<Engine2DEntity> list) {
		this.entityLists.remove(key);
	}

	protected void clearHashMap() {
		this.entityLists.clear();
	}

	/**
	 * 
	 * @param key
	 *            to return the list from the entity HashMap
	 * 
	 * @return the Entity GameThreadList which has this key
	 * 
	 * @see Engine2DGame#addEntityList(String, GameArrayList)
	 *      addEntityList(String, GameThreadList)
	 */
	public List<Engine2DEntity> getEntityList(String key) {
		return this.entityLists.get(key);
	}

	/**
	 * 
	 * @return the size of the thread list
	 */
	public int getThreadSize() {
		return threads.size();
	}

	/**
	 * Removes the thread from the list and attempt to stop the thread
	 * 
	 * @param index
	 *            the index of the thread in the list
	 * 
	 * @throws InterruptedException
	 *             if thread was interrupted when stopping
	 * 
	 * @see Thread#join() join()
	 */
	public void removeThread(int index) throws InterruptedException {
		Thread thread = getThread(index);
		thread.join();
		threads.remove(index);
	}

	public Thread getThread(int index) {
		try {
			return threads.get(index);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void startGameThread() {
		gameThread.start();
	}

	protected int getGameThreadPriority() {
		return gameThread.getPriority();
	}

	protected long getGameThreadID() {
		return gameThread.getId();
	}

	protected String getGameThreadName() {
		return gameThread.getName();
	}

	protected void sleepGameThread(long millis) throws InterruptedException {
		gameThread.sleep(millis);
	}

	/**
	 * The method submit a runnable which will be added to the process queue
	 * 
	 * @param runnable
	 *            the task to submit
	 */
	protected void submitTask(Runnable runnable) {
		eventDispatcher.sumbitTask(runnable);
	}

	/**
	 * @return the currentScreen
	 */
	public final Engine2DScreen getCurrentScreen() {
		return currentScreen;
	}

	/**
	 * @return the appRunning
	 */
	public final boolean isAppRunning() {
		return appRunning;
	}

	/**
	 * A special type of arrayList which requires modification such as add(E e)
	 * and {@linkplain ArrayList#remove(Object) remove(Object)} and
	 * {@linkplain ArrayList#remove(int) remove(index)} to be submitted to run
	 * on the GameThread()
	 */
	public class GameArrayList<E> extends ArrayList<E> {

		public GameArrayList() {
			super();
		}

		@Override
		public boolean add(E e) {
			if (Thread.currentThread().getName().equals(getGameThreadName())) {
				return super.add(e);
			} else {
				throw new IllegalAccessError(
						"modification to the GameArrayList must be done on " + eventDispatcher.getName());
			}
		}

		@Override
		public boolean remove(Object o) {
			if (Thread.currentThread().getName().equals(eventDispatcher.getName())) {
				return super.remove(o);
			} else {
				throw new IllegalAccessError(
						"modification to the GameArrayList must be done on " + eventDispatcher.getName());
			}
		}

		@Override
		public E remove(int index) {
			if (Thread.currentThread().getName().equals(getGameThreadName())) {
				return super.remove(index);
			} else {
				throw new IllegalAccessError(
						"modification to the GameArrayList must be done on " + eventDispatcher.getName());
			}
		}
	}

}