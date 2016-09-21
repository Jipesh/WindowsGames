/**
 * 
 * @author Jipesh
 */
package bomberman.content;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import game.engine2D.Engine2DRectangleBoundingBox;

public class Bomb extends GameObject {
	private final Image bomb[] = new Image[3];
	private final Character player;
	private int skin;
	private boolean delete = false;
	private boolean detonated = false;
	private int duration, size;
	private int timmer;
	private Timer countdown;
	private List<ExplosionFlame> explosions = new ArrayList<>();

	public Bomb(Character character, int x, int y, int dur, int size) {
		this.setBoundingBox(new Engine2DRectangleBoundingBox(x, y, 40, 40));
		this.timmer = dur;
		this.size = size;
		this.player = character;
		skin = 0;
		setSkin();
	}

	void start() {

		countdown = new Timer();

		this.countdown.scheduleAtFixedRate(new TimerTask() {

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
			bomb[i] = getGame().getSprite(i, 7);
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
			int x = (int) (getX() / 40f); // to get the position suitable for
											// array
			int y = (int) (getY() / 40f);
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
			skin = 1;

			/*
			 * allows the flames to last for a split second
			 */
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					delete = true;
					destroy();
					explosions.clear();
					updatePlayer();
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
			if (getGame().checkMap(xPos, yPos) == 0) {
				explosions.add(exp);
				amount += 1; // allows the loop to continue
			} else if (getGame().checkMap(xPos, yPos) == 2) {
				destroyObstacle(xPos, yPos);
				explosions.add(exp);
				amount = -1; // stops the loop
			} else if (getGame().checkMap(xPos,yPos) == 3) {
				detonateBomb(exp.getBoundingBox());
				explosions.add(exp);
				amount = -1;
			} else if (getGame().checkMap(xPos,yPos) == 4) {
				destroyPowerUp(xPos * 40, yPos * 40);
				explosions.add(exp);
				amount = -1;
			} else {
				amount = -1;
			}
		} else {
			int xPos = x;
			int yPos = (y + (direction * amount));
			ExplosionFlame exp = new ExplosionFlame((xPos * 40), (yPos * 40), 40, 40);
			if (getGame().checkMap(xPos, yPos) == 0) {
				explosions.add(exp);
				amount += 1;
			} else if (getGame().checkMap(xPos, yPos) == 2) {
				destroyObstacle(xPos, yPos);
				explosions.add(exp);
				amount = -1;
			} else if (getGame().checkMap(xPos, yPos) == 3) {
				detonateBomb((Engine2DRectangleBoundingBox) exp.getBoundingBox());
				explosions.add(exp);
				amount = -1;
			} else if (getGame().checkMap(xPos, yPos) == 4) {
				destroyPowerUp(xPos * 40, yPos * 40);
				explosions.add(exp);
				amount = -1;
			} else {
				amount = -1;
			}
		}
		return amount;
	}

	
	void destroyPowerUp(int xPos, int yPos) {
		for (PowerUp power : getGame().getSpecials_READONLY()) {
			if (power.getX() == xPos && power.getY() == yPos) {
				power.destroy();
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
	public void destroyObstacle(int x, int y) {
		Obstacle obs = (Obstacle) getGame().getEntity(x, y);
		obs.destroy();
		Random rnd = new Random();
		int num = rnd.nextInt(6) + 1; // random number between 1 to 6
		PowerUp power = new PowerUp(num, (x * 40), (y * 40));
		getGame().addSpecials(power);
	}

	public void detonateBomb(Engine2DRectangleBoundingBox box) {
		for (Bomb bomb : getGame().getBombs_READONLY()) {
			if (box.checkCollision((Engine2DRectangleBoundingBox) bomb.getBoundingBox())
					&& bomb.getDetonated() == false) {
				bomb.detonateNow();
			}
		}
	}

	/**
	 * iterator iterates though all players and checks if there is a collision
	 * between the two entities
	 * 
	 * @param f
	 *            the x position of the explosion flame
	 * @param g
	 *            the y position of the explosion flame
	 */
	public Character playerHit(float f, float g) {

		Engine2DRectangleBoundingBox explosion = new Engine2DRectangleBoundingBox((int) f, (int) g, 40, 40);
		Engine2DRectangleBoundingBox playerBox;
		for (Character player : getGame().getPlayers_READONLY()) {
			playerBox = (Engine2DRectangleBoundingBox) player.getBoundingBox();
			if (playerBox.checkCollision(explosion)
					|| playerBox.checkCollision((Engine2DRectangleBoundingBox) getBoundingBox())) {
				player.destroy();
				return player;
			}
		}
		return null;
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
	public boolean isdeleted() {
		return delete;
	}

	/**
	 * The method will only return the list of explosion flame if the bomb has
	 * already detonated
	 * 
	 * @return the list of explosions flames list
	 */
	public ArrayList<ExplosionFlame> getExplostions_READONLY() {
		if (detonated) {
			return (ArrayList<ExplosionFlame>) ((ArrayList<ExplosionFlame>) explosions).clone();
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
		return getX()/40 + "\t" + getY()/40;
	}

	public boolean getDetonated() {
		return detonated;
	}

	public class ExplosionFlame extends GameObject {

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
			this.setBoundingBox(new Engine2DRectangleBoundingBox(x, y, width, height));
		}

		public Image getImage() {
			return bomb[2];
		}

		public String toString() {
			return this.getX() + "\t" + this.getY();
		}
	}

	public Engine2DRectangleBoundingBox getBoundingBox() {
		return (Engine2DRectangleBoundingBox) super.getBoundingBox();
	}

}
