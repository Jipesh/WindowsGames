/**
 * 
 * @author Jipesh
 */
package arrowgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {
	private final JPanel GUI;
	private Player user;
	private boolean gameover = false;
	private HashMap<Integer, Obstacle> obstacles;
	private Thread userThread;
	private List<Thread> obstaclesThread;

	/**
	 * makes a new JFrame and initialises the final JPanel as well as set up the
	 * threads
	 */
	public Game() {
		obstacles = new HashMap<>();
		obstaclesThread = new ArrayList<>();
		user = new Player(this, 100, 500);
		userThread = new Thread(user);
		for (int i = 0; i < 5; i++) {
			obstacles.put(i, newObstacle(i)); // creates new obstacles
			obstaclesThread.add(new Thread(obstacles.get(i)));
		}
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 600);
		GUI = new GameGUI(this);
		frame.addKeyListener(user);
		frame.add(GUI);
		frame.setResizable(false);
		frame.setVisible(true);

		int fps = 60;
		double timePerTick = 1e9 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();

		while (true) { // the main loop

			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			if (delta >= 1) {
				looper();
				delta--;
			}
		}
	}

	/**
	 * a loop which enables movement as well as it to be visualised on the panel
	 * 
	 * @see Game#start()
	 * @see Thread
	 */
	public void looper() {
		if (!gameover) { // stops the loop if true
			userThread.run();
			start();
			if(!gameover) //so the user can see the collision
			GUI.repaint();
		}
	}

	/**
	 * For each obstacle it checks if there is a collision before and after
	 * movement (of the square)
	 */
	public synchronized void start() {

		for (int x : getKeys()) {
			if (obstacles.get(x).playerCollision() == true) {
				gameover = true;
			}
			obstaclesThread.get(x).run();
			if (obstacles.get(x).playerCollision() == true) {
				gameover = true;
			}
		}
		try {
			Thread.sleep(20);
		} catch (Exception e) {
		}
	}

	/**
	 * The method creates a new instance of the object and uses the
	 * spwnCollistion method to see if it collides with any of the other squares
	 * if does the process again and in the end of the recurrence method returns
	 * the new instance of the object
	 * 
	 * @param i
	 *            The index value to get the object as well as the thread
	 * @return The new instance of obstacle
	 * @see Game#Game() Constructor
	 * @see Game#spwnCollision(Obstacle, Obstacle) Collistion check
	 */
	public Obstacle newObstacle(int i) {
		if (i == 0) {
			return new Obstacle(user, this);
		}
		obstacles.put(i, new Obstacle(user, this));
		for (int j : obstacles.keySet()) {
			if ((checkCollision(obstacles.get(i), obstacles.get(j)) == true) && obstacles.get(i).equals(j) != true) {
				return newObstacle(i);

				/*
				 * A recurrence method which re-make the obstacle if
				 * spawn Collision detected
				 */
			} else {
				return obstacles.get(i); // return's the object
			}
		}
		return null;
	}

	/**
	 * A method which first checks collision between onject1 x1 to x2 and obj2
	 * x1 to x2 then does the same for y values
	 */
	public boolean checkCollision(Entity entity1, Entity entity2) {
		return entity1.getBox().checkCollision(entity2.getBox());
	}

	/**
	 * Run's the reset method on all obstacle and on the user using the keys
	 * 
	 * @see Game#getKeys() getKeys()
	 */
	public void reset() {
		user.reset();
		for (int x : getKeys()) {
			obstacles.get(x).reset();
		}
		gameover = false;
	}

	/**
	 * @return The set of keys
	 */
	Set<Integer> getKeys() {
		return obstacles.keySet(); // return's all key used by GameGUI
	}

	public boolean getStatus() {
		return gameover;

	}

	Player getPlayer() {
		return user;
	}

	public int getScore() {
		return user.getScore();
	}

	Obstacle getSqrs(int x) {
		return obstacles.get(x);
	}
	
}
