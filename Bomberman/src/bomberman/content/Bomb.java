package bomberman.content;

import java.awt.Image;

public class Bomb {
private final Game game;
private final Image bomb;
private int x, y, duration, size;

	public Bomb(int x, int y, int dur, int size, Game game) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.duration = dur;
		this.size = size;
		this.bomb = game.getSprite(0, 3);
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
	
	public Image getImage(){
		return bomb;
	}

}
