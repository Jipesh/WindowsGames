package game.engine2D;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Entity {
	public Engine2DBoundingShape box;
	public Engine2DBoundingShape.RectangleBoundingShape recBox;
	private final AbstractGame game;
	private Image image;

	/**
	 * 
	 * @param game - the game the entity belongs too
	 */
	public Entity(AbstractGame game) {
		this.game = game;
	}
	
	/**
	 * 
	 * @param box - the bounding box
	 * @param game - the game the entity belongs too
	 */
	public Entity(Engine2DBoundingShape.RectangleBoundingShape recBox, AbstractGame game) {
		this.recBox = recBox;
		this.game = game;
	}

	/**
	 * 
	 * @param box - the bounding box
	 * @param game - the game the entity belongs too
	 */
	public Entity(Engine2DBoundingShape box, AbstractGame game) {
		this.box = box;
		this.game = game;
	}
	
	/**
	 * The method set's a rectangle bounding box for the entity
	 * 
	 * @param recBox - the rectangle bounding box
	 */
	public void setBoundingBox(Engine2DBoundingShape.RectangleBoundingShape recBox){
		this.recBox = recBox;
	}

	/**
	 * The method set's an image file for the entity from the current directory
	 * 
	 * @param res - the path to the image file
	 * @throws IOException - if the image was not found
	 */
	public void setImage(String res) throws IOException {
		image = ImageIO.read(getClass().getResourceAsStream(res));
	}

	/**
	 * The method allows the ability to set the image of the entity to a path
	 * outside the current directory.
	 * 
	 * @param res - the path to the image file
	 * @throws IOException - if the image was not found
	 */
	public void setImage(File file) throws IOException {
		image = ImageIO.read(file);
	}

	/**
	 * The method set's an image to a sub image from the game sprite site
	 * if a sprite sheet was assigned to the game
	 * 
	 * @param res - the path to the image file
	 * @throws IOException - if the image was not found
	 */
	public Image setImage(int x, int y, int width, int height){
		if(game.getSpriteSheet() != null){
			return game.getSprite(x,y,width,height);
		}else{
			throw new RuntimeException("Spritesheet not set");
		}
	}
	
	/**
	 * 
	 * @return the game object
	 */
	public AbstractGame getGame(){
		return game;
	}

}