package bomberman.content;

import java.awt.Image;

public class Wall {
private final int x, y;
private final Image wall;
	public Wall(int x, int y, Game game) {
		this.x = x;
		this.y= y;
		wall = game.getSpirite(2, 0);
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
