package jipesh.pingpong;

public class Computer extends Player {
	private final Game GM;
	private int distanceX;
	private int distanceY;
	private Ball ball;

	public Computer(Game g) {
		super(g);
		GM = g;
	}

	/**
	 * A method to set the ball the computer will react to,
	 * this is currently used only in the animation
	 * 
	 * @param b the ball the computer reacts too
	 * 
	 * @see Game#beginSetup() beginSetup()
	 */
	public void setup(Ball b) {
		ball = b;
	}

	public void setup() {
		ball = (Ball) GM.getObj(2); // set's up the ball via game method
	}

	/**
	 * The method first checks the distance between itself and the ball via
	 * X-axis this can be said to be the computer sensitivity, Then checks to
	 * see if the ball is above by using the updateY method or is bellow it
	 * using the same method
	 */
	public void move() {
		if (Math.abs(updateX()) < 500) {
			if (updateY() > 0) {
				moveUP();
			} else if (updateY() < 0) {
				moveDwn();
			}
		}
	}

	/**
	 * Does the same as the above except is restricted to shorter space to
	 * travel used by animation
	 * 
	 * @see Game#bginSetup()
	 */
	public void runAnimation() {
		move();
	}

	/*
	 * The bellow two methods uses a subtraction method to determine whether to
	 * go up or down depending on the position of the ball
	 */
	private int updateY() {
		distanceY = ((getPosY()) - ball.getPosY());
		return distanceY;
	}

	private int updateX() {
		distanceX = ((getPosX()) - ball.getPosX());
		return distanceX;
	}

	public String toString() {
		return "Computer";
	}
}
