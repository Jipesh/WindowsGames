package bomberman.content;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb {
	private final Game game;
	private final Image bomb[] = new Image[3];
	private int skin;
	private boolean delete = false;
	private boolean detonated = false;
	private int x, y, duration, size;
	private int timmer;
	private List<ExplosionFlame> explosions = new ArrayList<>();

	public Bomb(int x, int y, int dur, int size, Game game) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.timmer = dur;
		this.size = 1;
		skin = 0;
		setSkin();
		game.addBomb(this);

		if (game.getBombs().contains(this)) {
			Timer countdown = new Timer();
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
			bomb[i] = game.getSprite(i, 3);
		}
	}

	/**
	 * set detonated to be true and then makes a new explosion on each direction
	 * but stops when it touches wall or border
	 * Then starts a timer to make sure explosion lasts for 1 second
	 * 
	 * @see Bomb#addExplostion(int, int, boolean, int, int) addExplostion(x, y, horizontal, amount, direction)
	 */
	public void detonate() {
		detonated = true;
		int left = 1;
		int right = 1;
		int up = 1;
		int down = 1;
		for (int i = 0; i < size; i++) {
			if (left != -1) {
				left = addExplostion(x, y, true, left, -1); 
			}else{
				left = -1;
			}
			if (right != -1) {
				left = addExplostion(x, y, true, right, 1); 
			}else{
				right = -1;
			}
			if (up != -1) {
				up = addExplostion(x, y, false, up, -1); 
			} else{
				up = -1;
			}
			if (down != -1) {
				down = addExplostion(x, y, false, down, 1); 
			}else {
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
	
	/**
	 * 
	 * @param x the x position of the flame on the map
	 * @param y the y position of the flame on the map
	 * @param horizontal if it's a explosion going horizontally
	 * @param the amount it goes in the direction
	 * @param the direction it goes at
	 * @return
	 */
	private int addExplostion(int x,int y,boolean horizontal,int amount, int direction){
		if(horizontal){	
			ExplosionFlame exp = new ExplosionFlame(x+(direction*amount), y, 40, 40);
				if (game.checkAvailability(x + ((direction*amount)), y)) {
					explosions.add(exp);
					amount += 1;
				} else if (game.checkMap(x+(direction*amount), y) == 2) {
					destroyObstacle(x+(direction*amount), y);
					game.makeAvailable(x+(direction*amount), y);
					explosions.add(exp);
					amount = -1;
				} else {
					amount = -1;
				}
		}else{
			ExplosionFlame exp = new ExplosionFlame(x, y+(direction*amount), 40, 40);
			if (game.checkAvailability(x, y+(direction*amount))) {
				explosions.add(exp);
				direction += 1;
			} else if (game.checkMap(x, y+(direction*amount)) == 2) {
				destroyObstacle(x, y+(direction*amount));
				game.makeAvailable(x, y+(direction*amount));
				explosions.add(exp);
				direction = -1;
			} else {
				direction = -1;
			}
		}
		return direction;
	}

	/**
	 * locates the obstacle by iterating though the list when it finds it it deletes 
	 * it from the list then stops the loop
	 * 
	 * @param x the x position of the wall on the map
	 * @param y the y position of the wall on the map
	 */
	public void destroyObstacle(int x, int y) {
		Iterator<Obstacle> obstacles = game.getObstacles().iterator();
		while (obstacles.hasNext()) {
			Obstacle obs = obstacles.next();
			if (obs.getX() / 40 == x && obs.getY() / 40 == y) {
				obstacles.remove();
				return;
			}
		}
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
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
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
		 * @param x the x position on the map
		 * @param y the y position on the map
		 * @param width the width of the box
		 * @param height the height of the box
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
	}

}
