/**
 * 
 * @author Jipesh
 */
package game.engine2D;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Arrays;

public class Engine2DPolygonBoundingBox extends Engine2DBoundingBox {

	private final Polygon polygon;
	private Area area;
	private int npoints;
	private int[] xpoints, ypoints;

	/**
	 * 
	 * @param xpoints
	 *            the array for x points of the polygon
	 * @param ypoints
	 *            the array for the y points of the polygon
	 * @param points
	 *            the number of points on the polygon
	 */
	public Engine2DPolygonBoundingBox(int[] xpoints, int[] ypoints, int points) {
		this.setType(Engine2DBoundingBox.POLYGON_BOUNDING_BOX);
		if (xpoints.length != ypoints.length) {
			System.err.println("Polygon will fail to give accurate colision");
		}
		if (npoints == 0) {
			if (xpoints.length > ypoints.length) {
				this.polygon = new Polygon(xpoints, ypoints, xpoints.length);
				this.npoints = xpoints.length;
			} else {
				this.polygon = new Polygon(xpoints, ypoints, ypoints.length);
				this.npoints = ypoints.length;
			}
		} else if (npoints < 0) {
			throw new NumberFormatException("Number of points can not be a non positive value");
		} else {
			this.polygon = new Polygon(xpoints, ypoints, points);
		}
		this.xpoints = xpoints;
		this.ypoints = ypoints;
		this.npoints = points;
		setMinMax();
		this.area = new Area(polygon);
	}

	/**
	 * 
	 * @param xpoints
	 *            the array for x points of the polygon
	 * @param ypoints
	 *            the array for the y points of the polygon
	 */
	public Engine2DPolygonBoundingBox(int[] xpoints, int[] ypoints) {
		this(xpoints, ypoints, 0);
	}

	/**
	 * 
	 * @param polyBox
	 *            the polygon bounding box object
	 * 
	 * @return if there is a collision between this bounding box and a polygon
	 *         bounding box
	 * 
	 * @see Engine2DBoundingBox#checkAreaCollision(Engine2DPolygonBoundingBox,
	 *      Engine2DPolygonBoundingBox)
	 *      checkAreaCollision(Engine2DPolygonBoundingBox,
	 *      Engine2DPolygonBoundingBox)
	 */
	public boolean checkAreaCollision(Engine2DPolygonBoundingBox polyBox) {
		return Engine2DBoundingBox.checkAreaCollision(this, polyBox);
	}

	/**
	 * 
	 * @param recBox
	 *            the rectangle bounding box object
	 * @return if there is a collision between this bounding box and a rectangle
	 *         bounding box
	 * 
	 * @see Engine2DBoundingBox#checkAreaCollision(Engine2DPolygonBoundingBox,
	 *      Engine2DRectangleBoundingBox)
	 *      checkAreaCollision(Engine2DPolygonBoundingBox,
	 *      Engine2DRectangleBoundingBox)
	 */
	public boolean checkCollision(Engine2DRectangleBoundingBox recBox) {
		return Engine2DBoundingBox.checkAreaCollision(this, recBox);
	}

	@Override
	/**
	 * updates x positions using the original offset and the multiplier/speed
	 * 
	 * @param original
	 *            the original offset
	 * @param multiplier
	 *            the speed multiplier
	 */
	public void moveX(int block, float ofset) {
		for (int i = 0; i < polygon.npoints; i++) {
			polygon.xpoints[i] += (block * ofset);
		}
		this.setX(getX() + (block * ofset));
		setCenterPoints();
	}

	@Override
	/**
	 * updates y position using the original offset and the multiplier/speed
	 * 
	 * @param original
	 *            the original offset
	 * @param multiplier
	 *            the speed multiplier
	 */
	public void moveY(int block, float ofset) {
		for (int i = 0; i < polygon.npoints; i++) {
			polygon.ypoints[i] += (block * ofset);
		}
		this.setY(getY() + (block * ofset));
		setCenterPoints();
	}

	/**
	 * 
	 * @param xpoints
	 *            - set's the x points for the shape
	 */
	public void setXPoints(int[] xpoints) {
		getPolygon().xpoints = xpoints;
		setMinMax();
	}

	/**
	 * 
	 * @param block
	 *            the block size
	 * @param ofset
	 *            the offset for the block
	 * @return the exact x points using the block size and offset
	 */
	public int[] getXPoints(int block, int ofset) {
		int[] _xpoints = new int[getNumberOfPoints()];
		for (int i = 0; i < getNumberOfPoints(); i++) {
			_xpoints[i] = (getXPoints()[i] * block) + ofset;
		}
		return _xpoints;
	}

	/**
	 * 
	 * @param ypoints
	 *            - set's the y points for the shape
	 */
	public void setYPoints(int[] ypoints) {
		getPolygon().ypoints = ypoints;
		setMinMax();
	}

	/**
	 * 
	 * @param block
	 *            the block size
	 * @param ofset
	 *            the offset for the block
	 * @return the exact y position using the block size and offset
	 */
	public int[] getYPoints(int block, int ofset) {
		int[] _ypoints = new int[getNumberOfPoints()];
		for (int i = 0; i < getNumberOfPoints(); i++) {
			_ypoints[i] = (getYPoints()[i] * block) + ofset;
		}
		return _ypoints;
	}

	/**
	 * 
	 * @return the polygon shape representing the bounding box
	 */
	public Polygon getPolygon() {
		return polygon;
	}

	/**
	 * 
	 * @return the area object representing the shape
	 */
	public Area getArea() {
		return area;
	}

	public void reformArea() {
		this.area = new Area(polygon);
	}

	/**
	 * 
	 * @return the clone of the x points of this shape
	 */
	public int[] getXPoints() {
		return polygon.xpoints.clone();
	}

	/**
	 * 
	 * @return the clone of the y points of this shape
	 */
	public int[] getYPoints() {
		return polygon.ypoints.clone();
	}

	/**
	 * 
	 * @return the number of point on this shape
	 */
	public int getNumberOfPoints() {
		return polygon.npoints;
	}

	/**
	 * 
	 * @param index
	 *            the index for the value of the x points
	 * @param value
	 *            the value to change to
	 */
	public void setXPoint(int index, int value) {
		xpoints[index] = value;
		setMinMax();
	}

	/**
	 * 
	 * @param index
	 *            the index for the value of the x points
	 * @param value
	 *            the value to change to
	 */
	public void setYPoint(int index, int value) {
		ypoints[index] = value;
		setMinMax();
	}

	private void setMinMax() {
		int[] xpoints = new int[npoints];
		int ypoints[] = new int[npoints];
		System.arraycopy(this.xpoints, 0, xpoints, 0, npoints);
		System.arraycopy(this.ypoints, 0, ypoints, 0, npoints);
		Arrays.sort(xpoints);
		Arrays.sort(ypoints);
		this.setX(xpoints[0]);
		this.setWidth(xpoints[xpoints.length - 1]);
		this.setY(ypoints[0]);
		this.setHeight(ypoints[ypoints.length - 1]);
		setCenterPoints();
	}

	private void setCenterPoints() {
		this.setCenterX((this.getWidth() - this.getX()) / 2);
		this.setCenterY((this.getHeight() - this.getY()) / 2);
	}
}