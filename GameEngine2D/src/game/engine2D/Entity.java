package game.engine2D;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Entity {
private final BoundingBox box;
private final AbstractGame game;
private Image image;
	
	public Entity(int x, int y, int width, int height, AbstractGame game) {
		box = new BoundingBox(x,y,width,height);
		this.game = game;
	}
	
	public void setImage(String res) throws IOException{
		image = ImageIO.read(getClass().getResourceAsStream(res));
	}
	
	public void setImage(File file) throws IOException{
		image = ImageIO.read(file);
	}
	
	public Image setImage(int x, int y, int width, int height){
		return game.getSprite(x,y,width,height);
	}
	
	public int getX(){
		return box.getX();
	}
	
	public int getY(){
		return box.getY();
	}
	
	public int getWidth(){
		return box.getWidth();
	}
	
	public int getHeight(){
		return box.getHeight();
	}
	
	public BoundingBox getBoundingBox(){
		return box;
	}
	

}