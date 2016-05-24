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
		this.speed = 7;
		this.explosion_size = 1;
		this.box = new BoundingBox(x,y,36,36);
		skin = 0;
		running = 0;
		setSkins(character);
	}
	
	private void setSkins(int character){
		if(character == 1){
			for(int i = 0 ; i < 2 ; i++){
				for(int j = 0 ; j < 4 ; j++){
					int running = i + 1;
					skins[j][i] = game.getSprite(j, running);
				}
			}
		}
	}
	
	/**
	 * allows player to move up and changes the current skin of the player
	 */
	public void moveUp(){
		skin = 1;
		if(box.getY() > 40)
		box.moveY(1, -speed);
	}
	
	public void moveDown(){
		skin = 0;
		if(box.getY() < (11*40))
		box.moveY(1,speed);
	}
	
	public void moveLeft(){
		skin = 2;
		if(box.getX() > 40)
		box.moveX(1,-speed);
	}
	
	public void moveRight(){
		skin = 3;
		if((box.getX() < (17*40))) 
		box.moveX(1,speed);
	}
	
	public void play(){
		
		if(leftPressed){
			skin = 2;
			moveLeft();
			if(game.checkCollision(this.box)){
				box.moveX(1,speed);
			}
			game.checkCollision(this.box);
		}
		else if(rightPressed){
			skin = 2;
			moveRight();
			if(game.checkCollision(this.box)){
				box.moveX(1,-speed);
			}
			game.checkCollision(this.box);
		}
		else if(upPressed){
			skin = 3;
			moveUp();
			if(game.checkCollision(this.box)){
				box.moveY(1, speed);
			}
		}
		else if(downPressed){
			skin = 1;
			moveDown();
			if(game.checkCollision(this.box)){
				box.moveY(1,-speed);
			}
			game.checkCollision(this.box);
		}
	}
	
	public void plantBomb(){
		int x = (getX()+box.getWidth()) / 2;
			x = x/40; //to make sure it is within a block
		int y = (getY()+box.getHeight()) / 2;
			y = y/40;
		game.addBomb(new Bomb(x,y,4,explosion_size,game)); //collision check needs to be added
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
	public int getExplosion_size() {
		return explosion_size;
	}
	/**
	 * @param explostion_size the explostion_size to set
	 */
	public void setExplostion_size(int explosion_size) {
		this.explosion_size = explosion_size;
	}
	
	public Image getImage(){
		return skins[skin][running];
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
			running = 1;
		}if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rightPressed = true;
			running = 1;
		}if(e.getKeyCode() == KeyEvent.VK_UP){
			upPressed = true;
			running = 1;
		}if(e.getKeyCode() == KeyEvent.VK_DOWN){
			downPressed = true;
			running = 1;
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
