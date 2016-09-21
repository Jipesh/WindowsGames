/**
 * 
 * @author Jipesh
 */
package bomberman.content;

import java.awt.Image;

import game.engine2D.Engine2DEntity;
import game.engine2D.Engine2DRectangleBoundingBox;

public class PowerUp extends GameObject{
private final int id;
private Image powerup;
	
	public PowerUp(final int id, final int x, final int y){
		this.setBoundingBox(new Engine2DRectangleBoundingBox(x,y,40,40));
		this.id = id;
		setPower(id);
	}
	
	/**
	 * set's the image according to the id
	 * 
	 * @param id the id of the power-up
	 */
	public void setPower(int id){
		powerup = getGame().getSprite(--id, 8);
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