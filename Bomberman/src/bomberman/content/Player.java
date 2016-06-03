/**
 * 
 * @author Jipesh
 */
package bomberman.content;

import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.engine2D.BoundingBox;
import game.engine2D.Entity;

public class Player extends Character {
	private final Game game;
	private final Image[][] skins = new Image[4][7];
	private boolean leftPressed, rightPressed, upPressed, downPressed, spacePressed, aPressed, dPressed, wPressed,
			sPressed, mPressed;
	private int bombs, explosion_size;
	protected int skin;
	private int running;
	private int last_running;
	private int character;
	private List<Bomb> ontop = new ArrayList<>();
	private double speed;

	/**
	 * Since this is a 2D game and their are collision checks therefore i use a
	 * bounding box which stores the x, y width and height of the player since
	 * the player has multiple skin stored in the sprite sheet each of them are
	 * cut and placed in a multidimensional array where the first array
	 * represent
	 * 
	 * @param character
	 *            the character id currently only 1 available
	 * @param x
	 *            the exact x position of the player
	 * @param y
	 *            the exact y position of the player
	 * @param game
	 *            the game which the player belongs to
	 * 
	 * @see Character#setSkins(int) setSkin(character_id)
	 * @see BoundingBox#BoundingBox(int, int, int, int)
	 *      BoundingBox(x,y,width,height)
	 */
	public Player(int character, int x, int y, Game game) {
		super(character, x, y, game);
		this.game = game;
		this.bombs = 1;
		this.speed = 3;
		this.explosion_size = 1;
		skin = 0;
		skins[1][0] = game.getSprite(1, 4);
		this.character = character;
		setSkins(character);
		last_running = 0;
		game.addKeyListener(new KeyInput());
	}

	/**
	 * for each boolean value it changes the skin first and then moves the
	 * character if their is a collision then the opposite move is used to make
	 * the character look as if it is stationary
	 * 
	 * @see Character#plantBomb() plantBomb()
	 * 
	 */
	public synchronized void play() {
		if (character == 1) {
			if (leftPressed) {
				skin = 2;
				moveLeft();
			} if (rightPressed) {
				skin = 3;
				moveRight();
				game.checkCollision(getBoundingBox());
			} if (upPressed) {
				skin = 1;
				moveUp();
			} if (downPressed) {
				skin = 0;
				moveDown();
			} if (mPressed) {
				plantBomb();
			}
		} else if (character == 2) {
			if (aPressed) {
				skin = 2;
				moveLeft();
			} if (dPressed) {
				skin = 3;
				moveRight();
				game.checkCollision(getBoundingBox());
			} if (wPressed) {
				skin = 1;
				moveUp();
			} if (sPressed) {
				skin = 0;
				moveDown();
			} if (spacePressed) {
				plantBomb();
			}
		}
		game.updateWalkable();
	}
	
	@Override
	/**
	 * what is executed when thread is set to run or start TODO : implement this
	 */
	public void run() {
		play();
		pickPower();
		updateWalkable();
	}
	
	private class KeyInput extends KeyAdapter{
		@Override
		/**
		 * a series of if statement to check if the different key is pressed if so
		 * then changes the boolean value of key...Pressed
		 * 
		 * @see Player#play() play()
		 */
		public void keyPressed(KeyEvent e) {
			if (character == 1) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					leftPressed = true;
					running = 1;
					last_running = ++last_running % 2;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rightPressed = true;
					running = 1;
					last_running = ++last_running % 2;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					upPressed = true;
					running = 1;
					last_running = ++last_running % 2;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					downPressed = true;
					running = 1;
					last_running = ++last_running % 2;
				}
				if (e.getKeyCode() == KeyEvent.VK_M) {
					mPressed = true;
				}
			} else if (character == 2) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					aPressed = true;
					running = 5;
					last_running = ++last_running % 2;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					dPressed = true;
					running = 5;
					last_running = ++last_running % 2;
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					wPressed = true;
					running = 5;
					last_running = ++last_running % 2;
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					sPressed = true;
					running = 5;
					last_running = ++last_running % 2;
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					spacePressed = true;
				}
			}
		}

		@Override
		/**
		 * the method checks if the button is no longer pressed therefore changes
		 * running back to non running skin
		 */
		public void keyReleased(KeyEvent e) {
			if (character == 1) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					leftPressed = false;
					running = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rightPressed = false;
					running = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					upPressed = false;
					running = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					downPressed = false;
					running = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_M) {
					mPressed = false;
				}
			} else if (character == 2) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					aPressed = false;
					running = 3;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					dPressed = false;
					running = 3;
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					wPressed = false;
					running = 3;
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					sPressed = false;
					running = 3;
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					spacePressed = false;
				}
			}
		}
	}

}
