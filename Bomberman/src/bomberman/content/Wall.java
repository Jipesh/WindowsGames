package bomberman.content;

import java.awt.Image;

public class Wall {
private final Image wall;
private int x, y;
	public Wall(int x, int y, Game game) {
		this.x = x;
		this.y= y;
		this.wall = game.getSprite(2, 0);
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
	
	public Image getImage(){
		return wall;
	}

}
