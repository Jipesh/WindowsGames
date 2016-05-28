package bomberman.content;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.engine2D.BoundingBox;
import game.engine2D.Entity;

public class Bomb extends Entity {
	private final Game game;
	private final Image bomb[] = new Image[3];
	private final Player player;
	private int skin;
	private boolean delete = false;
	private boolean detonated = false;
	private int duration, size;
	private int timmer;
	private Timer countdown;
	private List<ExplosionFlame> explosions = new ArrayList<>();

	public Bomb(Player player, int x, int y, int dur, int size, Game game) {
		super(x, y, 40, 40, game);
		this.game = game;
		this.timmer = dur;
		this.size = size;
		this.player = player;
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

			}, 0, 1000);
		}
	}

	public boolean hasDetonated() {
		return detonated;
	}

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
		if (detonated == false) {
			detonated = true;
			int x = (getX() / 40);
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
			skin = 1;
			Timer countdown = new Timer();
			countdown.schedule(new TimerTask() {

				@Override
				public void run() {
					delete = true;
				}

			}, 1000);
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
	 * @return
	 */
	private int addExplostion(int x, int y, boolean horizontal, int amount, int direction) {
		if (horizontal) {
			int xPos = (x + (direction * amount));
			int yPos = y;
			ExplosionFlame exp = new ExplosionFlame((xPos * 40), (y * 40), 40, 40);
			destroyPowerUp(exp.getExplostionBox());
			if (game.checkAvailability(xPos, yPos)) {
				explosions.add(exp);
				amount += 1;
			} else if (game.checkMap(xPos, yPos) == 2) {
				destroyObstacle(xPos, yPos);
				game.makeAvailable(xPos, yPos);
				explosions.add(exp);
				amount = -1;
			} else if (game.checkMap(xPos, yPos) == 3) {
				detonateBomb(exp.getExplostionBox());
				explosions.add(exp);
				amount = -1;
			}else {
				amount = -1;
			}
		} else {
			int xPos = x;
			int yPos = (y + (direction * amount));
			ExplosionFlame exp = new ExplosionFlame((xPos * 40), (yPos * 40), 40, 40);
			destroyPowerUp(exp.getExplostionBox());
			if (game.checkAvailability(xPos, yPos)) {
				explosions.add(exp);
				amount += 1;
			} else if (game.checkMap(xPos, yPos) == 2) {
				destroyObstacle(xPos, yPos);
				game.makeAvailable(xPos, yPos);
				explosions.add(exp);
				amount = -1;
			} else if (game.checkMap(xPos, yPos) == 3) {
				detonateBomb(exp.getExplostionBox());
				explosions.add(exp);
				amount = -1;
			}else {
				amount = -1;
			}
		}
		return amount;
	}

	public synchronized void destroyPowerUp(BoundingBox box) {
		Iterator<PowerUp> specials = game.getSpecials().iterator();
		while (specials.hasNext()) {
			PowerUp power = specials.next();
			if (power.getX() == box.getX() && power.getY() == box.getY()) {
				game.makeAvailable(power.getX()/40, power.getY()/40);
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
		game.removeEntity(x - 1, y - 1);
		Random rnd = new Random();
		int num = rnd.nextInt(6) + 1;
		PowerUp power = new PowerUp(num, (xPos + 1) * 40, (yPos + 1) * 40, game);
		game.addSpecials(power);
	}

	public void detonateBomb(BoundingBox box) {
		for (Bomb bomb : game.getBombs()) {
			if (box.checkCollision(bomb.getBoundingBox()) && bomb.getDetonated() == false) {
				bomb.detonateNow();
			}
		}
	}

	public void playerHit(int x, int y) {

		BoundingBox explosion = new BoundingBox(x, y, 40, 40);
		BoundingBox playerBox;
		Iterator<Player> players = game.getPlayers().iterator();
		while (players.hasNext()) {
			Player player = players.next();
			playerBox = new BoundingBox((player.getX()), (player.getY()), (player.getWidth()), (player.getHeight()));
			if (playerBox.checkCollision(explosion) || playerBox.checkCollision(getBoundingBox())) {
				players.remove();
			}
		}
	}

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

	public Image getImage() {
		return bomb[skin];
	}

	public class ExplosionFlame {
		private final BoundingBox explosion_box;

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
			explosion_box = new BoundingBox(x, y, width, height);
		}

		public BoundingBox getExplostionBox() {
			return explosion_box;
		}

		public Image getImage() {
			return bomb[2];
		}

		public String toString() {
			return explosion_box.getX() + "\\s\t\\s" + explosion_box.getY();
		}
	}
	
	public void updatePlayer(){
		player.recoverBomb();
	}

	public String toString() {
		return getX() + "\\s\t\\s" + getY();
	}

	public boolean getDetonated() {
		return detonated;
	}

}
