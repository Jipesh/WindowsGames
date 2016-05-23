package bomberman.content;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener, Runnable{
private final Game game;
private final BoundingBox box;
private final Image[][] skins = new Image[4][2];
private boolean leftPressed, rightPressed, upPressed, downPressed;
private int bombs, explosion_size, skin, running; 
private double speed;	
public Player(int character, int x, int y, Game game) {
		super();
		this.game = game;
		this.bombs = 1;
		this.speed = 3;
		this.explosion_size = 1;
		this.box = new BoundingBox(x,y,36,36);
		skin = 0;
		running = 0;
		//setSkins(character);
	}
	
	private void setSkins(int character){
		if(character == 1){
			for(int i = 0 ; i < 2 ; i++){
				for(int j = 0 ; j < 4 ; j++){
					skins[j][0] = game.getSprite(j, ++i);
				}
			}
		}
	}
	
	public void moveUp(){
		if(box.getY() > 40)
		box.moveY(1, -speed);
	}
	
	public void moveDown(){
		if(box.getY() < (11*40))
		box.moveY(1,speed);
	}
	
	public void moveLeft(){
		if(box.getX() > 40)
		box.moveX(1,-speed);
	}
	
	public void moveRight(){
		if((box.getX() < (17*40))) 
		box.moveX(1,speed);
	}
	
	public void play(){
		running = 1;
		if(leftPressed){
			skin = 2;
			moveLeft();
			if(game.checkColision(this)){
				box.setX(getX() + speed);
			}
			game.checkColision(this);
		}
		if(rightPressed){
			skin = 3;
			moveRight();
			if(game.checkColision(this)){
				box.setX(getX() - speed);
			}
			game.checkColision(this);
		}
		if(upPressed){
			skin = 2;
			moveUp();
			if(game.checkColision(this)){
				box.setY(getY() + speed);
			}
		}
		if(downPressed){
			skin = 1;
			moveDown();
			if(game.checkColision(this)){
				box.setY(getY() - speed);
			}
			game.checkColision(this);
		}
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return box.getX();
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return box.getY();
	}
	
	/**
	 * @return the bombs
	 */
	public int getBombs() {
		return bombs;
	}
	/**
	 * @param bombs the bombs to set
	 */
	public void setBombs(int bombs) {
		this.bombs = bombs;
	}
	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	/**
	 * @return the explostion_size
	 */
	public int getExplostion_size() {
		return explosion_size;
	}
	/**
	 * @param explostion_size the explostion_size to set
	 */
	public void setExplostion_size(int explostion_size) {
		this.explosion_size = explostion_size;
	}
	
	public Image getImage(){
		return game.getSprite(0, 1);
	}

	@Override
	public void run() {
		play();
	}
	
	public BoundingBox getBoundingBox(){
		return box;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			leftPressed = true;
		}if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rightPressed = true;
		}if(e.getKeyCode() == KeyEvent.VK_UP){
			upPressed = true;
		}if(e.getKeyCode() == KeyEvent.VK_DOWN){
			downPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			leftPressed = false;
			running = 0;
		}if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rightPressed = false;
			running = 0;
		}if(e.getKeyCode() == KeyEvent.VK_UP){
			upPressed = false;
			running = 0;
		}if(e.getKeyCode() == KeyEvent.VK_DOWN){
			downPressed = false;
			running = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

}
