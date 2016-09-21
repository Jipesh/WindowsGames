/**
 * 
 * @author Jipesh
 */
package bomberman.content;

import java.awt.Image;

import game.engine2D.Engine2DEntity;
import game.engine2D.Engine2DRectangleBoundingBox;

public class Wall extends GameObject{
private final Image wall;

	/**
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param game the game it is used in
	 */
	public Wall(int x, int y) {
		this.setBoundingBox(new Engine2DRectangleBoundingBox(x,y,40,40));
		this.wall = getGame().getSprite(2, 0);
	}
	
	public Image getImage(){
		return wall;
	}
	
	public Engine2DRectangleBoundingBox getBoundingBox(){
		return (Engine2DRectangleBoundingBox)super.getBoundingBox();
	}
	
}
