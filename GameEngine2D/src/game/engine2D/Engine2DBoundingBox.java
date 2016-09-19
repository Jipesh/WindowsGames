package game.engine2D;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;

public abstract class Engine2DBoundingBox {

	public final static String RECTANGLE_BOUNDING_BOX = "RECTANGLE BOUNDING BOX";

	public final static String POLYGON_BOUNDING_BOX = "POLYGON BOUNDING BOX";

	public final static String CIRCLE_BOUNDING_BOX = "CIRCLE BOUNDING BOX";

	private float x, y, width, height, centerX, centerY;

	private String type = null;

	void setType(String type) {
		this.type = type;
	}

	/**
	 * The method checks if there is a collision between two 2D polygon using
	 * area collision
	 * 
	 * @param polyBox1
	 *            a polygon bounding box
	 * @param polyBox2
	 *            another polygon bounding box
	 * 
	 * @return if there is a collision between the two
	 */
	public static boolean checkAreaCollision(final Engine2DPolygonBoundingBox polyBox1,
			final Engine2DPolygonBoundingBox polyBox2) {
		Area a1 = new Area(polyBox1.getPolygon());
		Area a2 = new Area(polyBox2.getPolygon());
		a1.intersect(a2);
		return !a1.isEmpty();
	}

	/**
	 * The method check if there is collision between a polygon shape and a
	 * rectangle bounding box using area collision
	 * 
	 * @param polyBox
	 *            a polygon bounding box object
	 * @param rec
	 *            a rectangle bounding box object
	 * 
	 * @return if a collision exists
	 */
	public static boolean checkAreaCollision(final Engine2DPolygonBoundingBox polyBox,
			final Engine2DRectangleBoundingBox rec) {
		Area a1 = new Area(polyBox.getPolygon());
		Area a2 = new Area(
				new Rectangle((int) rec.getX(), (int) rec.getY(), (int) rec.getWidth(), (int) rec.getHeight()));
		a1.intersect(a2);
		return !a1.isEmpty();
	}

	/**
	 * Move the bounding box in the x axis
	 * 
	 * @param block
	 *            the block in pixels to move in
	 * @param multiplier
	 *            the multiplier
	 */
	public void moveX(int block, float multiplier) {
		if(type.equals(POLYGON_BOUNDING_BOX)){
			Engine2DPolygonBoundingBox polyBox = (Engine2DPolygonBoundingBox) this;
			polyBox.moveXPos(block, multiplier);
		}
		this.x += (block * multiplier);
		setCenterPoints();

	}

	/**
	 * Move the bounding box in the y axis
	 * 
	 * @param block
	 *            the block in pixels to move in
	 * @param multiplier
	 *            the multiplier
	 */
	public void moveY(int block, float multiplier) {
		if(type.equals(POLYGON_BOUNDING_BOX)){
			Engine2DPolygonBoundingBox polyBox = (Engine2DPolygonBoundingBox) this;
			polyBox.moveYPos(block, multiplier);
		}
		this.y += (block * multiplier);
		setCenterPoints();
	}

	/**
	 * 
	 * @return the x position of the bounding box
	 */
	public final float getX() {
		return x;
	}

	/**
	 * 
	 * @param block
	 *            the block size
	 * @param offset
	 *            the offset for the block
	 * @return the exact x position using the block size and offset
	 */
	public float getX(int block, int offset) {
		return (this.x * block) + offset;
	}

	/**
	 * The method set the x position of the bounding box if the bounding box is
	 * an instance of {@linkplain Engine2DPolygonBoundingBox polygon bounding
	 * box} then it will also move the polygon
	 * 
	 * @param x
	 *            the x position to set to
	 */
	public final void setX(float x) {
		if (type.equals(Engine2DBoundingBox.POLYGON_BOUNDING_BOX)) {
			int moveX = (int) (x - getX());
			moveX(moveX, 1);
		}else if (type.equals(Engine2DBoundingBox.RECTANGLE_BOUNDING_BOX)){
			this.x = x;
		}
		setCenterPoints();
	}

	/**
	 * The method set the x position of the bounding box to an exact position
	 * using the block and offset if the bounding box is an instance of
	 * {@linkplain Engine2DPolygonBoundingBox polygon bounding box} then it will
	 * also move the polygon
	 * 
	 * @param block
	 *            the block size
	 * @param offset
	 *            the offset from the position using the block size
	 */
	public void setX(int block, int offset) {
		if (type.equals(Engine2DBoundingBox.POLYGON_BOUNDING_BOX)) {
			int moveX = (int) (((this.x * block) + offset) - this.x);
			moveX(moveX, 1);
		}else if (type.equals(Engine2DBoundingBox.RECTANGLE_BOUNDING_BOX)){
			this.x = (this.getX() * block) + offset;
		}
		setCenterPoints();
	}

	/**
	 * 
	 * @return the y position of the bounding box
	 */
	public final float getY() {
		return y;
	}

	/**
	 * 
	 * @param block
	 *            the block size
	 * @param offset
	 *            the offset for the block
	 * @return the exact y position using the block size and offset
	 */
	public float getY(int block, int offset) {
		return (this.y * block) + offset;
	}

	/**
	 * The method set the y position of the bounding box if the bounding box is
	 * an instance of {@linkplain Engine2DPolygonBoundingBox polygon bounding
	 * box} then it will also move the polygon
	 * 
	 * @param y
	 *            the y position to set to
	 */
	public final void setY(float y) {
		if (type.equals(Engine2DBoundingBox.POLYGON_BOUNDING_BOX)) {
			Engine2DPolygonBoundingBox polyBox = (Engine2DPolygonBoundingBox) this;
			int moveY = (int) (y - getY());
			polyBox.moveX(moveY, 1);
		}
		this.y = y;
		setCenterPoints();
	}

	/**
	 * The method set the y position of the bounding box to an exact position
	 * using the block and offset if the bounding box is an instance of
	 * {@linkplain Engine2DPolygonBoundingBox polygon bounding box} then it will
	 * also move the polygon
	 * 
	 * @param block
	 *            the block size
	 * @param offset
	 *            the offset from the position using the block size
	 */
	public void setY(int block, int offset) {
		if (type.equals(Engine2DBoundingBox.POLYGON_BOUNDING_BOX)) {
			Engine2DPolygonBoundingBox polyBox = (Engine2DPolygonBoundingBox) this;
			int moveY = (int) (((this.y * block) + offset) - this.y);
			polyBox.moveX(moveY, 1);
		}
		this.y = (this.getY() * block) + offset;
	}

	void setWidth(float width) {
		this.width = width;
		setCenterPoints();
	}

	/**
	 * The method return the width of the bounding box the width is
	 * automatically set for a {@linkplain Engine2DPolygonBoundingBox polygon
	 * bounding box} however a {@linkplain Engine2DRectangleBoundingBox
	 * rectangle bounding box} can be set by casting this bounding box object as
	 * {@linkplain Engine2DRectangleBoundingBox rectangle bounding box}
	 * 
	 * @see Engine2DRectangleBoundingBox#setWidth(float) setWidth(float)
	 * 
	 * @return the width of the bounding box
	 */
	public float getWidth() {
		return width;
	}

	void setHeight(float height) {
		this.height = height;
		setCenterPoints();
	}

	/**
	 * The method return the height of the bounding box the height is
	 * automatically set for a {@linkplain Engine2DPolygonBoundingBox polygon
	 * bounding box} however a {@linkplain Engine2DRectangleBoundingBox
	 * rectangle bounding box} can be set by casting this bounding box object as
	 * {@linkplain Engine2DRectangleBoundingBox rectangle bounding box}
	 * 
	 * @see Engine2DRectangleBoundingBox#setHeight(float) setHeight(float)
	 * 
	 * @return the width of the bounding box
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * The method get's the center x position of the bounding box, this is set
	 * automatically using the {@linkplain Engine2DBoundingBox#getX() getX()}
	 * and {@linkplain Engine2DBoundingBox#getWidth() getWidth()}. The centerX
	 * is automatically calculated whenever you move or
	 * {@linkplain Engine2DBoundingBox#setX(float) setX(float)} or
	 * {@linkplain Engine2DRectangleBoundingBox#setWidth(float) setWidth(float)}
	 * 
	 * @return the center x position of the BoundingBox
	 */
	public final float getCenterX() {
		return centerX;
	}

	final void setCenterX(float centerX) {
		this.centerX = centerX;
	}

	/**
	 * The method get's the center y position of the bounding box, this is set
	 * automatically using the {@linkplain Engine2DBoundingBox#getY() getY()}
	 * and {@linkplain Engine2DBoundingBox#getHeight() getHeight()}. The centerY
	 * is automatically calculated whenever you move or
	 * {@linkplain Engine2DBoundingBox#setY(float) setY(float)} or
	 * {@linkplain Engine2DRectangleBoundingBox#setHeight(float)
	 * setHeight(float)}
	 * 
	 * @return the center y position of the BoundingBox
	 */
	public final float getCenterY() {
		return centerY;
	}

	final void setCenterY(float centerY) {
		this.centerY = centerY;
	}

	private void setCenterPoints() {
		this.centerX = (this.width - this.x) / 2;
		this.centerY = (this.height - this.y) / 2;
	}
}
