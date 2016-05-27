package bomberman.content;

import java.awt.Image;

import game.engine2D.Entity;

public class PowerUp extends Entity{
private final int id;
private Image powerup;
	
	public PowerUp(final int id, final int x, final int y,final Game game){
		super(x,y,40,40,game);
		this.id = id;
	}
	
	public void setPower(int id){
		
	}
	
	public Image getImage(int id){
		return null; //TODO: Add sprite file to sprite sheet;
	}
	
	public String toString(){
		return ""+id;
	}

}
