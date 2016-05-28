/**
 * 
 * @author Jipesh
 */
package bomberman.content;

import java.awt.Image;

import game.engine2D.BoundingBox;
import game.engine2D.Entity;

public class Wall extends Entity{
private final Image wall;

	/**
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param game the game it is used in
	 */
	public Wall(int x, int y, Game game) {
		super(x,y,40,40,game);
		this.wall = game.getSprite(2, 0);
	}
	
	public Image getImage(){
		return wall;
	}
	

}
