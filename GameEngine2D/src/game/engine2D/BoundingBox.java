package game.engine2D;

public class BoundingBox {
	private int x, y, width, height;

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

	public void moveX(int original, double multiplier) {
		this.x += original * multiplier;
	}

	public void moveY(int orginal, double multiplier) {
		this.y += orginal * multiplier;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * 
	 * @param x
	 *            the x to set
	 */
	public void setX(double x) {
		this.x = (int) x;
	}
	
	/**
	 * set the x to an exact coordinate using a block size and offset
	 * 
	 * @param x
	 *            the x to set
	 */
	public void setX(int block, int offset) {
		this.x *= block + offset;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(double y) {
		this.y = (int) y;
	}
	
	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int block, int offset) {
		this.y *= block + offset;
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * 
	 * @param block the block size
	 * @param ofset the offset for the block
	 * @return the exact x position using the block size and offset 
	 */
	public int getX(int block,int offset){
		return (x*block)+offset;
	}
	
	/**
	 * 
	 * @param block the block size
	 * @param ofset the offset for the block
	 * @return the exact y position using the block size and offset 
	 */
	public int getY(int block, int offset){
		return (y*block)+offset;
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
