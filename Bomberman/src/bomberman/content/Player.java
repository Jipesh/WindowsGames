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

import game.engine2D.Engine2DRectangleBoundingBox;

public class Player extends Character {
	private boolean leftPressed, rightPressed, upPressed, downPressed, spacePressed, aPressed, dPressed, wPressed,
			sPressed, mPressed;
	private int character;

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
	public Player(int character, int x, int y) {
		super(character, x, y);
		skin = 0;
		this.character = character;
		setSkins(character);
		getGame().addKeyListener(new KeyInput());
	}

	/**
	 * for each boolean value it changes the skin first and then moves the
	 * character if their is a collision then the opposite move is used to make
	 * the character look as if it is stationary
	 * 
	 * @see Character#plantBomb() plantBomb()
	 * 
	 */
	public void play() {
		if (character == 1) {
			if (leftPressed) {
				setSkin(2);;
				moveLeft();
			} if (rightPressed) {
				setSkin(3);
				moveRight();
			} if (upPressed) {
				setSkin(1);
				moveUp();
			} if (downPressed) {
				setSkin(0);
				moveDown();
			} if (mPressed) {
				plantBomb();
			}
		}else if(character == 2){
			if (aPressed) {
				setSkin(2);
				moveLeft();
			} if (dPressed) {
				setSkin(3);
				moveRight();
			} if (wPressed) {
				setSkin(1);
				moveUp();
			} if (sPressed) {
				setSkin(0);
				moveDown();
			} if (spacePressed) {
				plantBomb();
			}
		}
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
					setRunning(getMinRunning() +getanimationFrame());
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rightPressed = true;
					setRunning(getMinRunning() +getanimationFrame());
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					upPressed = true;
					setRunning(getMinRunning() +getanimationFrame());
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					downPressed = true;
					setRunning(getMinRunning() +getanimationFrame());
				}
				if (e.getKeyCode() == KeyEvent.VK_M) {
					mPressed = true;
				}
			} else if (character == 2) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					aPressed = true;
					setRunning(getMinRunning() +getanimationFrame());
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					dPressed = true;
					setRunning(getMinRunning() +getanimationFrame());
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					wPressed = true;
					setRunning(getMinRunning() +getanimationFrame());
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					sPressed = true;
					setRunning(getMinRunning() +getanimationFrame());
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
					resetPrevious();
					setRunning(getMinRunning());
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rightPressed = false;
					resetPrevious();
					setRunning(getMinRunning());
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					upPressed = false;
					resetPrevious();
					setRunning(getMinRunning());
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					downPressed = false;
					resetPrevious();
					setRunning(getMinRunning());
				}
				if (e.getKeyCode() == KeyEvent.VK_M) {
					mPressed = false;
				}
			} else if (character == 2) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					aPressed = false;
					resetPrevious();
					setRunning(getMinRunning());
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					dPressed = false;
					resetPrevious();
					setRunning(getMinRunning());
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					wPressed = false;
					resetPrevious();
					setRunning(getMinRunning());
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					sPressed = false;
					resetPrevious();
					setRunning(getMinRunning());
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					spacePressed = false;
				}
			}
		}
	}

	@Override
	public void update() {
		play();
		pickPower();
		updateWalkable();
	}
	
	public Engine2DRectangleBoundingBox getBoundingBox(){
		return (Engine2DRectangleBoundingBox)super.getBoundingBox();
	}

}
