/**
 * 
 * @author Jipesh
 */
package bomberman.content;

import java.awt.Image;

import game.engine2D.Entity;

public class Obstacle extends Entity{
private final Image obstacle;
private final int id;

	/**
	 * set up all the fields
	 * 
	 * @param x the x position of the obstacle on the map
	 * @param y the y position of the obstacle on the map
	 * @param game the game which the obstacle is used in
	 */
	public Obstacle(int index, int x, int y, Game game) {
		super(x,y,40,40,game);
		this.id = index;
		this.obstacle = game.getSprite(0, 0);
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
	 * @return unique id
	 */
	public int getID(){
		return id;
	}
	
	public String toString(){
		return ""+id;
	}

}
