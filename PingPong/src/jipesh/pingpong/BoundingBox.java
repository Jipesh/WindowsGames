package jipesh.pingpong;

/**
 * A box that captures the maximum dimension of an object on the field and can
 * be used to check for collisions.
 * 
 */
public class BoundingBox {
	private int x, y, width, height;

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	public BoundingBox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Move the bounding box by the given value in x direction.
	 * 
	 * @param dx the x to move by
	 */
	public void moveX(int original, double speed) {
		this.x += original * speed;
	}
	
	/**
	 * The method get's the posX if the bounding box moved using this parameters
	 * 
	 * @param original the original pixels it moves
	 * @param speed the multiplier
	 * @return the value if bounding box moved to this position
	 */
	public int getX(int original, double speed) {
		return (int) (this.x + original * speed);
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Move the bounding box by the given value in y direction.
	 * 
	 * @param dy the y to move by
	 */
	public void moveY(int original, double speed) {
		this.y += original * speed;
	}
	
	/**
	 * The method get's the posY if the bounding box moved using this parameters
	 * 
	 * @param original the original pixels it moves
	 * @param speed the multiplier
	 * @return the value if bounding box moved to this position
	 */
	public int getY(int original, double speed) {
		return (int) (this.y + original * speed);
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	public boolean checkCollision(BoundingBox bb){
		return true;
	}

	/**
	 * Checks the current bounding box is colliding with another bounding box on the left
	 * 
	 * @param bb
	 * @return true if the two bounding boxes collide
	 */
	public boolean checkCollidesLeft(BoundingBox bb) {
		return ((this.x <= bb.x + bb.width) && (this.y + this.height >= bb.y && this.y <= bb.y + bb.height && (this.y + (this.height/2) <= bb.y + bb.height)));
	}
	
	public boolean checkColitionRight(BoundingBox bb){
		return ((this.x + this.width >= bb.x) && (this.y + this.height >= bb.y && this.y <= bb.y + bb.height && (this.y + (this.height/2) <= bb.y + bb.height)));
	}
	
}
