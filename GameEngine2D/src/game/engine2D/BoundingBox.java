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

	/**
	 * updates x position using the original offset and the multiplier/speed
	 * 
	 * @param original the original offset
	 * @param multiplier the speed multiplier
	 */
	public void moveX(int original, double multiplier) {
		this.x += original * multiplier;
	}

	/**
	 * updates y position using the original offset and the multiplier/speed
	 * 
	 * @param original the original offset
	 * @param multiplier the speed multiplier
	 */
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
	 * set the x position to it's exact position using the block size and a offset
	 * 
	 * @param block the block size
	 * @param offset the offset from the position using the block size
	 */
	public void setX(int block,int offset) {
		this.x *= block + offset;
	}
	
	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(double y){
		this.y = (int) y;
	}
	
	/**
	 * set the x position to it's exact position using the block size and a offset
	 * 
	 * @param block the block size
	 * @param offset the offset from the position using the block size
	 */
	public void setY(int block,int offset) {
		this.y *= block + offset;
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
	public int getX(int block,int ofset){
		return (x*block)+ofset;
	}
	
	/**
	 * 
	 * @param block the block size
	 * @param ofset the offset for the block
	 * @return the exact y position using the block size and offset 
	 */
	public int getY(int block, int ofset){
		return (y*block)+ofset;
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