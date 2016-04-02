/**
 * 
 * @author Jipesh
 */
package arrowgame;

import java.util.Random;

public class Obstacle extends Entity implements Runnable {
	private final int WIDTH = 350;
	private final int START = 0;
	private final int End = 560;
	private final Random rnd;
	private final Player p1;
	private int x, y, speed;
	private Game GM;

	/**
	 * The constructor gives the obstacle a starting position, it also
	 * set's the speed by checking what a random number between 0 to 2 equals
	 * 
	 * @param p
	 *            The current Player
	 * @param g
	 *            The currant Game
	 */
	public Obstacle(Player p, Game g) {
		super(g);
		GM = g;
		p1 = p;
		rnd = new Random();
		y = 0 - rnd.nextInt(START + 250); // Allows there to be space between
											// when
											// the object comes into range

		x = rnd.nextInt(WIDTH); // Spawns the obstacle on a random x value
								// between 0 to 400
		
		super.setBoundingBox(new BoundingBox(x,y,50,50));
		
		if (rnd.nextInt(3) == 0 || rnd.nextInt(3) == 1) {
			speed = 5;
		} else {
			speed = 8;
		}
	}
	
	/**
	 * Increment speed slowly as well as add the speed to Y to give it the falling animation, the
	 * method also calls the nextStage method for the player which increments the players score
	 * @see Player#nextStage() nextStage()
	 */
	public synchronized void start() {
		y += speed;
		this.getBox().setY(y);
		speed += 0.0001;
		if (y > End) {
			p1.nextStage();
			reposition(); // reset's position
		}
	}

	/**
	 * Does the same thing as the constructor
	 * @see Obstacle#Obstacle(Player, Game) Constroctor
	 */
	public void reset() {
		y = 0 - rnd.nextInt(START + 250); // Allows there to be space between
											// when the object comes into range
		this.getBox().setY(y);
		x = rnd.nextInt(WIDTH); // Spawns the obstacle on a random x value
								// between 0 to 400
		this.getBox().setX(x);
		if (spawnCollision() == true) {
			reset();
		} else {

			if (rnd.nextInt(3) == 0 || rnd.nextInt(3) == 1) {
				speed = 5;
			} else {
				speed = 8;
			}
		}
	}

	/**
	 * Does a similar process to the constructor but with higher speed and have object coming at fairly
	 * close distance in terms of Y values
	 * @see Obstacle#Obstacle(Player, Game) Constroctor
	 */
	private void reposition() {
		y = 0 - rnd.nextInt(START + 200);
		this.getBox().setY(y);
		x = rnd.nextInt(WIDTH);
		this.getBox().setX(x);
		if (spawnCollision() == true) {
			reposition();
		} else {
			if (p1.getScore() > 50) {
				if (rnd.nextInt(3) == 0 && speed < 7) {
					speed = 7;
				} else if (rnd.nextInt(3) == 1 || rnd.nextInt(3) == 2 && speed < 12) {
					speed = 12;
				}
			}
		}
	}
	
	/**
	 * 
	 * @return true or false if there is a collision with any other square apart from it self
	 * @see Game#spwnCollision(Obstacle, Obstacle) spawnCollision()
	 */
	private boolean spawnCollision() {
		for (int x : GM.getKeys()) {
			if (GM.checkCollision(this, GM.getSqrs(x)) == true && this.equals(GM.getSqrs(x)) != true) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return true if it had collision with the player
	 */
	public boolean playerCollision() {
		return GM.checkCollision(p1, this);
	}

	public int getPosY() {
		return y;
	}

	public int getPosX() {
		return x;
	}

	public String toString() {
		return "[" + x + "," + (x + this.getBox().getWidth()) + "," + y + "," + (y + this.getBox().getHeight()) + "]"; // for
																			// debugging
	}

	/**
	 * calls the start method when run method is called by the thread
	 * @see Obstacle#start() start()
	 * @see Game#start()
	 */
	public void run() {
		start();

	}
}
