package bomberman.content;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb {
	private final Game game;
	private final Image bomb[] = new Image[3];
	private int skin;
	private boolean detonated = false;
	private int x, y, duration, size;
	private int timmer;
	private List<ExplosionFlame> explosions = new ArrayList<>();

	public Bomb(int x, int y, int dur, int size, Game game) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.timmer = dur;
		this.size = size;
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

	public void detonate() {
		detonated = true;
		int default_ = 40;
		int left = 1;
		int right = 1;
		int up = 1;
		int down = 1;
		for (int i = 0; i < size; i++) {
			ExplosionFlame leftExp = new ExplosionFlame(x - left, y, default_, default_);
			if (left == 100 || !game.wallCollision(x - left, y) || (x - left)*40 >= 40) {
				explosions.add(new ExplosionFlame(x - left, y, default_, default_));
				left += 1;
			} else {
				left = 100;
			}
			ExplosionFlame rightExp = new ExplosionFlame(x + right, y, default_, default_);
			if (right == 100 || !game.wallCollision(x + right, y) || (x + right)*default_ <= (17*default_)) {
				explosions.add(new ExplosionFlame(x + right, y, default_, default_));
				right += 1;
			} else {
				right = 100;
			}
			ExplosionFlame upExp = new ExplosionFlame(x, y - up, default_, default_);
			if (up == 100 || !game.wallCollision(x, y - up) || (y-up)*default_ <= 40) {
				explosions.add(new ExplosionFlame(x, y-up, default_, default_));
				up -= 1;
			} else {
				up = 100;
			}
			ExplosionFlame downExp = new ExplosionFlame(x, y + down, default_, default_);
			if (up == 100 || !game.wallCollision(x, y + down) || (y+down)*default_ <= (11*40)) {
				explosions.add(new ExplosionFlame(x, y+down, default_, default_));
				down += 1;
			} else {
				down = 100;
			}
		}
		skin = 1;
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
