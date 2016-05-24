package bomberman.content;

public class BoundingBox {
	private final int width, height;
	private int x, y;

	/**
	 * set up all the fields of the bounding box
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param height
	 *            the height of the box
	 * @param width
	 *            the width of the box
	 * 
	 * @see BoundingBox#checkCollision(BoundingBox) checkCollision(BoundingBox)
	 */
	public BoundingBox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * The method checks to see if the two bounding box or overlapping each
	 * other
	 * 
	 * @param box
	 *            the bounding box to check for collision
	 * @return true if its overlapping false if its not
	 */
	public boolean checkCollision(BoundingBox box) {
		return ((this.x + this.width >= box.getX()) && (this.x <= box.getX() + box.getWidth()))
				&& (this.y + this.height >= box.getY()) && (this.y <= box.getY() + box.getHeight());
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	public void moveX(int original, double multiplier) {
		this.x += original * multiplier;
	}

	public void moveY(int orginal, double multiplier) {
		this.y += orginal * multiplier;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(double x) {
		this.x = (int) x;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(double y) {
		this.y = (int) y;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

}
