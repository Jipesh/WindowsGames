/**
 * 
 * @author Jipesh
 */
package game.engine2D;

import java.awt.Rectangle;

/**
 * 
 * The bounding box will be in a shape of a rectangle and you have to provide the x, 
 * y, width and height of the bounding box. The bounding box also has methods to
 * check for collision with another bounding box
 *
 */
public class Engine2DRectangleBoundingBox extends Engine2DBoundingBox {

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
	 */
	public Engine2DRectangleBoundingBox(int x, int y, int width, int height) {
		this.setType(Engine2DBoundingBox.RECTANGLE_BOUNDING_BOX);
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		setCenterX((getWidth() - getX())/2);
		setCenterY((getHeight() - getY())/2);
	}

	/**
	 * The method checks to see if the two bounding box or overlapping each
	 * other
	 * 
	 * @param recBox
	 *            the bounding box to check for collision
	 * @return true if its overlapping false if its not
	 * 
	 * @author Jipesh
	 */
	public boolean checkCollision(Engine2DRectangleBoundingBox recBox) {
		return ((getX() + getWidth() >= recBox.getX()) && (getX() <= recBox.getX() + recBox.getWidth()))
				&& (getY() + getHeight() >= recBox.getY()) && (getY() <= recBox.getY() + recBox.getHeight());
	}

	public boolean checkCollision(Engine2DPolygonBoundingBox polygonBox) {
		Rectangle rec = new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
		return Engine2DBoundingBox.checkAreaCollision(polygonBox, this);
	}

	@Override
	/**
	 * updates x position using the original offset and the multiplier/speed
	 * 
	 * @param original
	 *            the original offset
	 * @param multiplier
	 *            the speed multiplier
	 */
	public void moveX(int block, float multiplier) {
		super.moveX(block, multiplier);
	}

	@Override
	/**
	 * updates y position using the original offset and the multiplier/speed
	 * 
	 * 
	 * @param original
	 *            the original offset
	 * @param multiplier
	 *            the speed multiplier
	 *            
	 *            
	 */
	public void moveY(int block, float ofset) {
		super.moveY(block, ofset);
	}
	
	/**
	 * 
	 * @param width the width to set for this bounding box
	 */
	public void setWidth(float width){
		super.setWidth(width);
	}
	
	/**
	 * 
	 * @param height the height to set for this bounding box
	 */
	public void setHeight(float height){
		super.setWidth(height);
	}
}
