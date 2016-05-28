/**
 * 
 * @author Jipesh
 */
package bomberman.content;

import java.awt.Image;

import game.engine2D.Entity;

public class PowerUp extends Entity{
private final int id;
private final Game game;
private Image powerup;
	
	public PowerUp(final int id, final int x, final int y,final Game game){
		super(x,y,40,40,game);
		this.game = game;
		this.id = id;
		setPower(id);
	}
	
	/**
	 * set's the image according to the id
	 * 
	 * @param id the id of the power-up
	 */
	public void setPower(int id){
		powerup = game.getSprite(--id, 8);
	}
	
	public Image getImage(){
		return powerup;
	}
	
	public String toString(){
		return ""+id;
	}

	public int getID() {
		return id;
	}

}
