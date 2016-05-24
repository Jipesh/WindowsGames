package bomberman.content;

import java.awt.Image;

public class Obstacle {
private final Game game;
private final BoundingBox box;
private final Image obstacle;
private int x, y;

	/**
	 * set up all the fields
	 * 
	 * @param x the x position of the obstacle on the map
	 * @param y the y position of the obstacle on the map
	 * @param game the game which the obstacle is used in
	 */
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
	
	/**
	 * 
	 * @return the obstacle sprite
	 */
	public Image getImage(){
		return obstacle;
	}
	
	/**
	 * 
	 * @return the bounding box of the obstacle
	 */
	public BoundingBox getBoundingBox(){
		return box;
	}

}
