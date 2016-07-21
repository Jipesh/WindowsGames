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
	 * @param x the x position to set
	 * 
	 * @see Engine2DBoundingBox#getX() getX()
	 */
	public void getX(){
		getBoundingBox().getX();
	}
	
	/**
	 * The method set the y position of the bounding box if bounding box was set
	 * else throws error
	 * 
	 * @param y the y position to set
	 * 
	 * @see Engine2DBoundingBox#setX(float) setY(float)
	 */
	public void setY(float y){
		getBoundingBox().setY(y);
	}
	
	/**
	 * The method returns the y position of the bounding box if bounding box was set
	 * else throws error
	 * 
	 * @see Engine2DBoundingBox#getY() getY()
	 */
	public void getY(float y){
		getBoundingBox().getY();
	}

	/**
	 * The method returns the y width of the bounding box if bounding box was set
	 * else throws error
	 * 
	 * @see Engine2DBoundingBox#getWidth() getWidth()
	 */
	public void getWidth(float width){
		getBoundingBox().getWidth();
	}
	
	public void getHeight(float height){
		getBoundingBox().getHeight();
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