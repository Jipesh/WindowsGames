package jipesh.pingpong;

import java.util.Random;

public class Ball {
	private final Player P1, P2;
	private BoundingBox bbLocation;
	private int moveX, moveY;
	private double speed; // A speed which effect the pause rate

	public Ball(int x, int y, Player p1, Player p2) {
		bbLocation = new BoundingBox(x, y, 20, 20);
		P1 = p1;
		P2 = p2;
		speed = 4;
		Random rnd = new Random();
		if ((rnd.nextInt(2) + 1) == 1) {
			moveX = -1; // moves left first
			moveY = -1;
		} else {
			moveX = 1; // moves right first
			moveY = -1;
		}

		/**
		 * A method which allows the ball to go either left or right by checking
		 * what random number between 1 to 2 equals
		 */
	}

	/**
	 * method which enables the ball to move as well as react to the left and
	 * right player position
	 */
	public synchronized void start() {
		
		bbLocation.moveX(moveX, speed); 
		
		if (checkCollisionLeft(P1)) {
			moveX *= -1;
			bbLocation.setX(P1.getPosX() + 1 + P1.getBoundingBox().getWidth());
		}else if(checkCollisionRight(P2)){
			moveX *= -1;
			bbLocation.setX(P2.getPosX() - 1 - bbLocation.getWidth());
		}
		
		if(bbLocation.getX() < P1.getPosX() || bbLocation.getX() > P2.getPosX()){
			bbLocation.moveX(moveX, speed); 
		}
		// this
		// can be left or right depending on moveX
		bbLocation.moveY(moveY,speed);
		
		
		if (bbLocation.getX() < 0) {
			P2.win();
			P2.update();
			reset();
		}
		if (bbLocation.getX() > 798) {
			P1.win();
			P1.update();
			reset();
		}

		/* updates the score*/

		if (bbLocation.getY() <= 0 || bbLocation.getY() >= 552) {
			moveY *= -1;
		}
		
		if (speed < 10) {
			speed += 0.001; // slowly increase the speed
		}
	}

	public void runAnimation() {
		
		speed = 4;
		
		bbLocation.moveX(moveX,speed); /*
								 * moves it one pixel left or right depending on
								 * moveX and up or down depending on moveY
								 */
		bbLocation.moveY(moveY,speed);
		
		if (checkCollisionLeft(P1) || (bbLocation.getX() >= 260 && bbLocation.getX() <= 510) || checkCollisionRight(P2)) {
			moveX *= -1;
		}
		if (bbLocation.getY() <= 250 || bbLocation.getY() >= 552) {
			moveY *= -1;
		}
		if (bbLocation.getX() < 2) {
			bbLocation.setX(250);
			bbLocation.setY(400);
		} else if (bbLocation.getX() > 798) {
			bbLocation.setX(650);
			bbLocation.setY(400);
		}
	}
	public void checkCollistion(){
		if (checkCollisionLeft(P1) || checkCollisionRight(P2)){
			moveX *= -1;
		}
			
			
	}

	private boolean checkCollisionLeft(Player p) {
		return bbLocation.checkCollidesLeft(p.getBoundingBox());
	}
	
	private boolean checkCollisionRight(Player p) {
		return bbLocation.checkColitionRight(p.getBoundingBox());
	}

	private void reset() {
		bbLocation.setX(350); // default X
		bbLocation.setY(250); // default Y
		speed = 4; // default speed
		Random rnd = new Random();
		
		/* does the random check to check whether it will go left or right */
		
		if ((rnd.nextInt(2) + 1) == 1) {
			moveX = -1;
			moveY = -1;
		} else {
			moveX = 1;
			moveY = 1;
		}
		start(); // starts the movement again
	}

	public int getPosX() {
		return bbLocation.getX();
	}

	public int getPosY() {
		return bbLocation.getY();
	}
}