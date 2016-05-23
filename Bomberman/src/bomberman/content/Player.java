package bomberman.content;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener, Runnable{
private final Game game; 
private final Image[][] skins = new Image[4][4];
private boolean leftPressed, rightPressed, upPressed, downPressed;
private int x, y, bombs, speed, explosion_size, skin, running; 
	public Player(int character, int x, int y, Game game) {
		super();
		this.game = game;
		this.x = x;
		this.y = y;
		this.bombs = 1;
		this.speed = 1;
		this.explosion_size = 1;
		skin = 0;
		running = 0;
		setSkins(character);
	}
	
	private void setSkins(int character){
		if(character == 1){
			for(int i = 0 ; i < 2 ; i++){
				for(int j = 0 ; j < 4 ; j++){
					skins[j][i] = game.getSprite(j, ++i);
				}
			}
		}
	}
	
	public void play(){
		running = 1;
		if(leftPressed){
			skin = 2;
			if((--x >= 40) && !game.checkColision(this)){
				setX(--x);
			}else{
				setX(++x);
			}
		}else if(rightPressed){
			skin = 3;
			if(++x <= (40*17) && !game.checkColision(this)){
				setX(++x);
			}else{
				setX(--x);
			}
		}else if(upPressed){
			skin = 2;
			if(--y >= 40 && !game.checkColision(this)){
				setY(--y);
			}else{
				setY(++y);
			}
		}else if(downPressed){
				skin = 1;
			if(++y <= (40*11) && !game.checkColision(this)){
				setY(++y);
			}else{
				setY(--y);
			}
		}
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		x *= speed;
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		y *= speed;
		this.y = y;
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
	public int getSpeed() {
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
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			leftPressed = true;
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rightPressed = true;
		}else if(e.getKeyCode() == KeyEvent.VK_UP){
			upPressed = true;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			downPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			leftPressed = false;
			running = 0;
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rightPressed = false;
			running = 0;
		}else if(e.getKeyCode() == KeyEvent.VK_UP){
			upPressed = false;
			running = 0;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			downPressed = false;
			running = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

}
