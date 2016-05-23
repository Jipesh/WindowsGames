package bomberman.content;

import java.awt.Image;

public class Obstacle {
private final Game game;
private final int x, y; 
private final Image[] skins = new Image[8];	
private int skin;
	public Obstacle(int skin, int x, int y, Game game) {
		this.game = game;
		skin = 0;
		this.x = x;
		this.y = y;
		skins[0] = game.getSpirite(0, 0);
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
		return skins[skin];
	}

}
