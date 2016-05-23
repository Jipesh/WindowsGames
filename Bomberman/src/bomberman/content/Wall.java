package bomberman.content;

import java.awt.Image;

public class Wall {
private final BoundingBox box;
private final Image wall;
	public Wall(int x, int y, Game game) {
		this.wall = game.getSprite(2, 0);
		this.box = new BoundingBox(x,y,40,40);
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return box.getX();
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return box.getY();
	}
	
	public Image getImage(){
		return wall;
	}
	
	public BoundingBox getBoundingBox(){
		return box;
	}

}
