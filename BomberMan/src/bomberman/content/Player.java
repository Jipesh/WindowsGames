package bomberman.content;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener, Runnable {
	private final Game game;
	private final int DEFAULT_DURATION = 4;
	private final Image[][] skins = new Image[4][4];
	private int skin, running;
	private boolean upPressed, downPressed, leftPressed, rightPressed;

	private int x, y, bombs, speed, duration, explostion_size;

	public Player(int x, int y, Game game) {
		this.game = game;
		this.x = x;
		this.y = y;
		setSkin();
		this.skin = 0;
		this.running = 1;
		this.bombs = 1;
		this.speed = 1;
		this.explostion_size = 1;
	}
	
	private void setSkin(){
		for(int i = 1 ; i < 2 ; i++){
			for(int j = 0 ; j < 4 ; j++){
				skins[j][i] = game.getSpirite(j, i);
			}
		}
	}

	/**
	 * @return the bombs
	 */
	public int getBombs() {
		return bombs;
	}

	/**
	 * @param bombs
	 *            the bombs to set
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
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the explostion_size
	 */
	public int getExplostion_size() {
		return explostion_size;
	}

	/**
	 * @param explostion_size
	 *            the explostion_size to set
	 */
	public void setExplostion_size(int explostion_size) {
		this.explostion_size = explostion_size;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	public void moveUp() {
		if (--y != 0) {
			y -= 1;
		}
	}

	public void moveDwn() {
		if (++y != 18) {
			y += 1;
		}
	}

	public void moveLeft() {
		if (--x != 0) {
			skin = 2;
			running = running++ % 2;
			x -= 1;
		}
	}

	public void moveRight() {
		if (++x != 0) {
			x += 1;
		}
	}
	
	public void play(){
		if (upPressed == true) {
			moveUp();
		}
		if (downPressed == true) {
			moveDwn();
		}
		if (leftPressed == true) {
			moveLeft();
		}
		if (rightPressed == true) {
			moveRight();
		}
	}

	public void plant() {
		game.addBomb(x, y, duration, explostion_size);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftPressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed =  false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	public Image getImage() {
		return skins[skin][running];
	}

	@Override
	public void run() {
		play();
	}

}
