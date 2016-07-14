package jipesh.pingpong;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener {
	private final Game Gm;
	private final BoundingBox bbLocation;
	private int score = 0;
	private boolean w_Pressed = false;
	private boolean s_Pressed = false;
	private boolean up_Pressed = false;
	private boolean dwn_Pressed = false;
	private String str = "00"; // starting score for looks

	public Player(Game g) {
		Gm = g;
		bbLocation = new BoundingBox(0, 0, 15, 154);
	}

	public synchronized void play(int i) {
		if (i == 1) {
			if (getWprs()) {
				moveUP();
			}
			if (getSprs()) {
				moveDwn();
			}
		} else {
			if (getUPprs()) {
				moveUP();
			}
			if (getDWNprs()) {
				moveDwn();
			}
		}
	}

	public void start(int x, int y) {
		bbLocation.setX(x);
		bbLocation.setY(y);
	}

	public void moveDwn() {
		if (getPosY() >= 10 && getPosY() <= 400) {
			if(bbLocation.getY(1,10) <= 400){
				bbLocation.moveY(1,10);
			}
		}
	}

	public void moveUP() {
		if (getPosY() >= 10 && getPosY() <= 400) {
			if(bbLocation.getY(-1,10) >= 10){
				bbLocation.moveY(-1,10);
			}
		}
	}

	/**
	 * checks which key is pressed to activate the boolean statements
	 * 
	 * @see KeyListener
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up_Pressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			dwn_Pressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			w_Pressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			s_Pressed = true;
		}
	}

	/**
	 * checks which key is no longer pressed to deactivate the boolean
	 * statements
	 * 
	 * @see KeyListener
	 */
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up_Pressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			dwn_Pressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			w_Pressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			s_Pressed = false;
		}
	}

	public void keyTyped(KeyEvent e) {
	} // NOT NECESSARY

	public void win() {
		++score; // increment currant player score
	}

	public int getScore() {
		return score;
	}

	public int getPosX() {
		return bbLocation.getX();
	}

	public int getPosY() {
		return bbLocation.getY();
	}

	public boolean getWprs() {
		return w_Pressed;
	}

	public boolean getSprs() {
		return s_Pressed;
	}

	public boolean getUPprs() {
		return up_Pressed;
	}

	public boolean getDWNprs() {
		return dwn_Pressed;
	}

	public void update() {
		if (score > 9) {
			str = Integer.toString(getScore());
			/* Convert the score which integer to string */
		} else {
			str = 0 + Integer.toString(getScore());
		}
		/* adds a zero in front of the score for looks and consistency */
	}

	public String getString() {
		return str;
	}

	public BoundingBox getBoundingBox() {
		return bbLocation;
	}
}
