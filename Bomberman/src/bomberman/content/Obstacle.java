package bomberman.content;

import java.awt.Image;

public class Obstacle {
private final Game game;
private final BoundingBox box;
private final Image obstacle;
private int x, y; 
	public Obstacle(int x, int y, Game game) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.obstacle = game.getSprite(0, 0);
		this.box = new BoundingBox(x,y,40,40);
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
		return obstacle;
	}
	
	public BoundingBox getBoundingBox(){
		return box;
	}

}
