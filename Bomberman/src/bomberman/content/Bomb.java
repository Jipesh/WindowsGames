/**
 * 
 * @author Jipesh
 */
package bomberman.content;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.engine2D.Engine2DBoundingPolygon.Engine2DBoundingRectangle;
import game.engine2D.Engine2DRectangleEntity;

public class Bomb extends Engine2DRectangleEntity {
	private final Game game;
	private final Image bomb[] = new Image[3];
	private final Character player;
	private int skin;
	private boolean delete = false;
	private boolean detonated = false;
	private int duration, size;
	private int timmer;
	private Timer countdown;
	private List<ExplosionFlame> explosions = new ArrayList<>();

	public Bomb(Character character, int x, int y, int dur, int size, Game game) {
		super(x, y, 40, 40, game);
		this.game = game;
		this.timmer = dur;
		this.size = size;
		this.player = character;
		skin = 0;
		setSkin();
		game.addBomb(this);

		if (game.getBombs().contains(this)) {
			countdown = new Timer();
			countdown.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					timmer--;
					if (timmer == 0) {
						detonate();
						countdown.cancel();
					}
				}

			}, 0, 1000); // 4 seconds before detonation
		}
	}

	/**
	 * 
	 * @return if bomb has detonated
	 */
	public boolean hasDetonated() {
		return detonated;
	}

	/**
	 * set the skin profiles by setting it with the corresponding sprite file
	 * TODO: add running stage 2 skin
	 */
	private void setSkin() {
		for (int i = 0; i < 3; i++) {
			bomb[i] = game.getSprite(i, 7);
		}
	}

	/**
	 * set detonated to be true and then makes a new explosion on each direction
	 * but stops when it touches wall or border Then starts a timer to make sure
	 * explosion lasts for 1 second
	 * 
	 * @see Bomb#addExplostion(int, int, boolean, int, int) addExplostion(x, y,
	 *      horizontal, amount, direction)
	 */
	public synchronized void detonate() {
		if (detonated == false) { /*
									 * to stop bombs from detonating which has
									 * already detonated
									 */
			detonated = true;
			int x = (getX() / 40); // to get the position suitable for array
			int y = (getY() / 40);
			int left = 1;
			int right = 1;
			int up = 1;
			int down = 1;
			for (int i = 0; i < size; i++) {
				if (left != -1) {
					left = addExplostion(x, y, true, left, -1);
				} else {
					left = -1;
				}
				if (right != -1) {
					right = addExplostion(x, y, true, right, 1);
				} else {
					right = -1;
				}
				if (up != -1) {
					up = addExplostion(x, y, false, up, -1);
				} else {
					up = -1;
				}
				if (down != -1) {
					down = addExplostion(x, y, false, down, 1);
				} else {
					down = -1;
				}
			}
			// destroyPowerUp();
			skin = 1;

			/*
			 * allows the flames to last for a split second
			 */
			Timer countdown = new Timer();
			countdown.schedule(new TimerTask() {

				@Override
				public void run() {
					delete = true;
				}

			}, 500);
		}
	}

	/**
	 * 
	 * @param x
	 *            the x position of the flame on the map
	 * @param y
	 *            the y position of the flame on the map
	 * @param horizontal
	 *            if it's a explosion going horizontally
	 * @param the
	 *            amount it goes in the direction
	 * @param the
	 *            direction it goes at
	 * @see Bomb#detonate() detonate()
	 * @return
	 */
	private int addExplostion(int x, int y, boolean horizontal, int amount, int direction) {
		if (horizontal) { // since only x values will change
			int xPos = (x + (direction * amount)); // allows the flame depending
													// on the direction and size
			int yPos = y;
			ExplosionFlame exp = new ExplosionFlame((xPos * 40), (y * 40), 40, 40);
			if (game.checkAvailability(xPos, yPos)) {
				explosions.add(exp);
				amount += 1; // allows the loop to continue
			} else if (game.checkMap(xPos, yPos) == 2) {
				destroyObstacle(xPos, yPos);
				explosions.add(exp);
				amount = -1; // stops the loop
			} else if (game.checkMap(xPos, yPos) == 3) {
				detonateBomb(exp.getBoundingBox());
				explosions.add(exp);
				amount = -1;
			} else if (game.checkMap(xPos, yPos) == 4) {
				destroyPowerUp(xPos*40, yPos*40);
				explosions.add(exp);
				amount = -1;
			} else {
				amount = -1;
			}
		} else {
			int xPos = x;
			int yPos = (y + (direction * amount));
			ExplosionFlame exp = new ExplosionFlame((xPos * 40), (yPos * 40), 40, 40);
			if (game.checkAvailability(xPos, yPos)) {
				explosions.add(exp);
				amount += 1;
			} else if (game.checkMap(xPos, yPos) == 2) {
				destroyObstacle(xPos, yPos);
				explosions.add(exp);
				amount = -1;
			} else if (game.checkMap(xPos, yPos) == 3) {
				detonateBomb(exp.getBoundingBox());
				explosions.add(exp);
				amount = -1;
			} else if (game.checkMap(xPos, yPos) == 4) {
				destroyPowerUp(xPos*40, yPos*40);
				explosions.add(exp);
				amount = -1;
			} else {
				amount = -1;
			}
		}
		return amount;
	}

	/**
	 * 
	 * @param box
	 *            the bounding box of the ExplosionFlame
	 */
	public synchronized void destroyPowerUp(int xPos, int yPos) {
		Iterator<PowerUp> specials = game.getSpecials().iterator();
		while (specials.hasNext()) {
			PowerUp power = specials.next();
			if (power.getX() == xPos && power.getY() == yPos) {
				game.makeAvailable(power.getX() / 40, power.getY() / 40);
				specials.remove();
			}
		}
	}

	/**
	 * locates the obstacle by iterating though the list when it finds it it
	 * deletes it from the list then stops the loop
	 * 
	 * @param x
	 *            the x position of the wall on the map
	 * @param y
	 *            the y position of the wall on the map
	 */
	public synchronized void destroyObstacle(int x, int y) {
		int xPos = x - 1;
		int yPos = y - 1;
		Obstacle obs = (Obstacle) game.getEntity(xPos + "x" + yPos + "y");
		game.getObstacles().remove(obs);
		game.removeEntity(xPos, yPos);
		Random rnd = new Random();
		int num = rnd.nextInt(6) + 1; // random number between 1 to 6
		PowerUp power = new PowerUp(num, (xPos + 1) * 40, (yPos + 1) * 40, game);
		game.addSpecials(power);
	}

	public void detonateBomb(Engine2DBoundingRectangle box) {
		for (Bomb bomb : game.getBombs()) {
			if (box.checkCollision(bomb.getBoundingBox()) && bomb.getDetonated() == false) {
				bomb.detonateNow();
			}
		}
	}

	/**
	 * iterator iterates though all players and checks if there is a collision
	 * between the two entities
	 * 
	 * @param x
	 *            the x position of the explosion flame
	 * @param y
	 *            the y position of the explosion flame
	 */
	public void playerHit(int x, int y) {

		Engine2DBoundingRectangle explosion = new Engine2DBoundingRectangle(x, y, 40, 40);
		Engine2DBoundingRectangle playerBox;
		Iterator<Character> players = game.getPlayers().iterator();
		while (players.hasNext()) {
			Character player = players.next();
			playerBox = player.getBoundingBox();
			if (playerBox.checkCollision(explosion) || playerBox.checkCollision(getBoundingBox())) {
				players.remove();
			}
		}
	}

	/**
	 * detonates the bomb
	 */
	public void detonateNow() {
		countdown.cancel();
		detonate();
	}

	/**
	 * 
	 * @return if the object needs to be deleted
	 */
	public boolean delete() {
		return delete;
	}

	/**
	 * The method will only return the list of explosion flame if the bomb has
	 * already detonated
	 * 
	 * @return the list of explosions flames list
	 */
	public List<ExplosionFlame> getExplostions() {
		if (detonated) {
			return explosions;
		}
		return null;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 
	 * @return the image depending on the skin value
	 */
	public Image getImage() {
		return bomb[skin];
	}

	public void updatePlayer() {
		player.recoverBomb();
	}

	public String toString() {
		return getX() + "\\s\t\\s" + getY();
	}

	public boolean getDetonated() {
		return detonated;
	}

	public class ExplosionFlame extends Engine2DRectangleEntity {

		/**
		 * 
		 * @param x
		 *            the x position on the map
		 * @param y
		 *            the y position on the map
		 * @param width
		 *            the width of the box
		 * @param height
		 *            the height of the box
		 * 
		 * @see Bomb#detonate() detonate()
		 */
		private ExplosionFlame(int x, int y, int width, int height) {
			super(x, y, width, height, game);
		}

		public Image getImage() {
			return bomb[2];
		}

		public String toString() {
			return this.getX() + "\\s\t\\s" + this.getY();
		}
	}

}
