package game.engine2D;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Engine2DEntity {
	private final Engine2DGame game;
	private Image image;

	public Engine2DEntity() {
		this.game = null;
	}
	
	/**
	 * 
	 * @param game - the game the entity belongs too
	 */
	public Engine2DEntity(Engine2DGame game) {
		this.game = game;
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
	public Engine2DGame getGame(){
		if(game == null){
			return Engine2DGame.GAME;
		}else{
			return game;
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}