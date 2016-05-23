package bomberman.content;

import java.awt.Image;

public class PowerUp {
private final int x,y;
	
	public PowerUp(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Image getImage(){
		return null; //TODO: Add sprite file to sprite sheet;
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

}
