/**
 * 
 * @author Jipesh
 */
package game.engine2D;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Engine2DEntity {

	private final Engine2DGame game;
	private Engine2DBoundingBox box = null;
	private Image image;

	/**
	 * The game the entity belongs too
	 * 
	 * @param game
	 *            - the game the entity belongs too
	 */
	public Engine2DEntity(Engine2DGame game) {
		this.game = game;
	}

	/**
	 * The method set the bounding box for the object as a rectangle bounding box
	 * 
	 * @param box
	 *            the rectangle bounding box object
	 */
	public void setBoundingBox(Engine2DRectangleBoundingBox box) {
		this.box = box;
	}
	
	/**
	 * The method set the bounding box for the object as a polygon bounding box
	 * 
	 * @param box
	 *            the rectangle bounding box object
	 */
	public void setBoundingBox(Engine2DPolygonBoundingBox box) {
		this.box = box;
	}

	/**
	 * @return the bounding box object for the entity
	 */
	public final Engine2DBoundingBox getBoundingBox() {
		return box;
	}
		
	
	/**
	 * The method set's an image file for the entity from the current directory
	 * 
	 * @param res
	 *            - the path to the image file
	 * @throws IOException
	 *             - if the image was not found
	 */
	public void setImage(String res) throws IOException {
		image = ImageIO.read(getClass().getResourceAsStream(res));
	}

	/**
	 * The method allows the ability to set the image of the entity to a path
	 * outside the current directory.
	 * 
	 * @param file
	 *            - the path to the image file
	 * @throws IOException
	 *             - if the image was not found
	 */
	public void setImage(File file) throws IOException {
		image = ImageIO.read(file);
	}

	/**
	 * The method uses the sprite sheet to cut a sub image and save it as
	 * the entity image, if no sprite sheet was found then an error will be thrown
	 * 
	 * @param x the x position for the sub image
	 * @param y the y position for the sub image
	 * @param width the width for the sub image
	 * @param height the height for the sub image
	 * 
	 */
	public void setImage(int x, int y, int width, int height) {
		if (game.getSpriteSheet() != null) {
			image = game.getSprite(x, y, width, height);
		} else {
			throw new RuntimeException("Spritesheet not set");
		}
	}
	
	/**
	 * The method set the x position of the bounding box if bounding box was set
	 * else throws error
	 * 
	 * @param x the x position to set
	 * 
	 * @see Engine2DBoundingBox#setX(float) setX(float)
	 */
	public void setX(float x){
		if (box != null){
			getBoundingBox().setX(x);
		}else{
			throw new NullPointerException("No bounding box set for entity");
		}
	}
	
	/**
	 * The method return the x position of the bounding box if bounding box was set
	 * else throws error
	 * 
	 * @return the x position of the bounding box
	 * 
	 * @see Engine2DBoundingBox#getX() getX()
	 */
	public float getX(){
		if (box != null){
			return getBoundingBox().getX();
		}else{
			throw new NullPointerException("No bounding box set for entity");
		}
	}
	
	/**
	 * The method set the y position of the bounding box if bounding box was set
	 * else throws error
	 * 
	 * @param y the y position to set
	 * 
	 * @see Engine2DBoundingBox#setY(float) setY(float)
	 */
	public void setY(float y){
		if (box != null){
			getBoundingBox().setY(y);
		}else{
			throw new NullPointerException("No bounding box set for entity");
		}
	}
	
	/**
	 * The method returns the y position of the bounding box if bounding box was set
	 * else throws error
	 * 
	 * @return the y position of the bounding box
	 * 
	 * @see Engine2DBoundingBox#getY() getY()
	 */
	public float getY(){
		if (box != null){
			return getBoundingBox().getY();
		}else{
			throw new NullPointerException("No bounding box set for entity");
		}
	}

	/**
	 * The method returns the width of the bounding box if bounding box was set
	 * else throws error
	 * 
	 * @return the width of the bounding box
	 * 
	 * @see Engine2DBoundingBox#getWidth() getWidth()
	 */
	public float getWidth(){
		if (box != null){
			return getBoundingBox().getWidth();
		}else{
			throw new NullPointerException("No bounding box set for entity");
		}
	}
	
	/**
	 * The method returns the height of the bounding box if bounding box was set
	 * else throws error
	 * 
	 * @return the height of the bounding box
	 * 
	 * @see Engine2DBoundingBox#getHeight() getHeight()
	 */
	public float getHeight(){
		if (box != null){
			return getBoundingBox().getHeight();
		}else{
			throw new NullPointerException("No bounding box set for entity");
		}
	}

	/**
	 * 
	 * @return the game object
	 */
	public Engine2DGame getGame() {
		return game;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
}