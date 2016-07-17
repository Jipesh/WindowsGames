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

import game.engine2D.Engine2DGame;
import game.engine2D.Engine2DMovableRectangleBoundingBoxEntity;
import game.engine2D.Engine2DScreen;


public class Game extends Engine2DGame{
	private JPanel GUI;
	private Player user;
	private HashMap<Integer, Obstacle> obstacles;
	private Thread userThread;
	private List<Thread> obstaclesThread;

	/**
	 * makes a new JFrame and initialises the final JPanel as well as set up the
	 * threads
	 */
	public Game(String title, int width, int height, boolean resizable) {
		super(title,width,height,resizable);
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
	public boolean checkCollision(Engine2DMovableRectangleBoundingBoxEntity entity1, Engine2DMovableRectangleBoundingBoxEntity entity2) {
		return entity1.getBoundingBox().checkCollision(entity2.getBoundingBox());
	}

	/**
	 * Run's the reset method on all obstacle and on the user using the keys
	 * 
	 * @see Game#getKeys() getKeys()
	 */
	public void resetGame() {
		user.reset();
		for (int x : getKeys()) {
			obstacles.get(x).reset();
		}
		setGameOver(false);
		startLoop();
	}

	/**
	 * @return The set of keys
	 */
	Set<Integer> getKeys() {
		return obstacles.keySet(); // return's all key used by GameGUI
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

	@Override
	public void gameLoop() {
		GUI.repaint();
	}

	@Override
	public void gameOver() {
		GUI.repaint();
		stopLoop();
	}

	@Override
	public void init() {
		obstacles = new HashMap<>();
		obstaclesThread = new ArrayList<>();
		
		int[] xpoints = {17,26,26,34,34,24,24,17,12,12,1,1,9,9};
		int[] ypoints = {0,7,30,39,46,46,55,61,55,46,46,39,30,7};
		
		user = new Player(xpoints,ypoints);
		userThread = new Thread(user);
		for (int i = 0; i < 5; i++) {
			obstacles.put(i, newObstacle(i)); // creates new obstacles
			addThread(new Thread(obstacles.get(i)));
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI = new GameGUI(this);
		addKeyListener(user);
		addScreen((Engine2DScreen) GUI);
		setScreen(0);
		addThread(userThread);
		start(60);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}
	
}
