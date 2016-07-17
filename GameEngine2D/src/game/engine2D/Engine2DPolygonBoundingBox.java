/**
 * 
 * @author Jipesh
 */
package game.engine2D;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Arrays;


public class Engine2DPolygonBoundingBox {
	private final Polygon polygon;
	private Area area;
	private int minX, maxX, minY, maxY, centerX, centerY;

	public Engine2DPolygonBoundingBox(int[] xpoints,int[] ypoints, int points){
		if(xpoints.length != ypoints.length){
			System.err.println("Polygon will fail to give accurate colision");
		}
		this.polygon = new Polygon(xpoints, ypoints, points);
		setMinMax();
		this.area = new Area(polygon);
	}
	
	public Engine2DPolygonBoundingBox(int[] xpoints,int[] ypoints){
		this(xpoints, ypoints, xpoints.length);
	}
	
	/**
	 * Checks to see if there is a collision between the two areas
	 * 
	 * @param area of the other shape
	 * @return if there is a collision between the two area's
	 */
	public static boolean checkAreaCollision(final Polygon poly1, final Polygon poly2){
		Area a1 = new Area(poly1);
		Area a2 = new Area(poly2);
		a1.intersect(a2);
		return !a1.isEmpty();
	}
	
	/**
	 * Checks to see if there is a collision between the two areas
	 * 
	 * @param area of the other shape
	 * @return if there is a collision between the two area's
	 */
	public static boolean checkAreaCollision(final Polygon poly1, final Engine2DRectangleBoundingBox rec){
		Area a1 = new Area(poly1);
		Area a2 = new Area(new Rectangle(rec.x, rec.y, rec.width, rec.height));
		a1.intersect(a2);
		return !a1.isEmpty();
	}
	
	public boolean checkAreaCollision(Polygon polygon){
		return Engine2DPolygonBoundingBox.checkAreaCollision(this.polygon,polygon);
	}
	
	public boolean checkCollision(Engine2DRectangleBoundingBox recBox){
		return Engine2DPolygonBoundingBox.checkAreaCollision(polygon, recBox);
	}
	
	/**
	 * updates x positions using the original offset and the multiplier/speed
	 * 
	 * @param original the original offset
	 * @param multiplier the speed multiplier
	 */
	public void moveX(int original, double multiplier) {
		for(int i = 0 ; i < polygon.npoints ; i++){
			polygon.xpoints [i] += (original * multiplier);
		}
		this.minX += (original * multiplier);
		this.maxX += (original * multiplier);
		setCenterPoints();
	}
	
	/**
	 * updates y position using the original offset and the multiplier/speed
	 * 
	 * @param original the original offset
	 * @param multiplier the speed multiplier
	 */
	public void moveY(int original, double multiplier) {
		for(int i = 0 ; i < polygon.npoints ; i++){
			polygon.ypoints [i] += (original * multiplier);
		}
		this.minY += (original * multiplier);
		this.maxY += (original * multiplier);
		setCenterPoints();
	}
	
	/**
	 * 
	 * @return the polygon shape representing the bounding shape
	 */
	public Polygon getPolygon(){
		return polygon;
	}
	
	/**
	 * 
	 * @return the area object representing the shape
	 */
	public Area getArea(){
		return area;
	}
	
	public void reformArea(){
		this.area = new Area(polygon);
	}
	
	/**
	 * 
	 * @return the x points of this shape
	 */
	public int[] getXPoints(){
		return polygon.xpoints;
	}
	
	/**
	 * 
	 * @return the y points of this shape
	 */
	public int[] getYPoints(){
		return polygon.ypoints;
	}
	
	/**
	 * 
	 * @return the number of point on this shape
	 */
	public int getNumberOfPoints(){
		return polygon.npoints;
	}
	
	/**
	 * 
	 * @param ypoints - set's the x points for the shape
	 */
	public void setXPoints(int[] xpoints){
		getPolygon().xpoints =  xpoints;
		setMinMax();
	}
	
	/**
	 * 
	 * @param block the block size
	 * @param ofset the offset for the block
	 * @return the exact x points using the block size and offset 
	 */
	public int[] getXPoints(int block,int ofset){
		int[]_xpoints = new int[getNumberOfPoints()];
		for(int i = 0 ; i < getNumberOfPoints() ; i++){
			_xpoints[i] = (getXPoints()[i]*block)+ofset;
		}
		return _xpoints;
	}
	
	/**
	 * 
	 * @param ypoints - set's the y points for the shape
	 */
	public void setYPoints(int[] ypoints){
		getPolygon().ypoints =  ypoints;
		setMinMax();
	}
	
	/**
	 * 
	 * @param block the block size
	 * @param ofset the offset for the block
	 * @return the exact y position using the block size and offset 
	 */
	public int[] getYPoints(int block, int ofset){
		int[]_ypoints = new int[getNumberOfPoints()];
		for(int i = 0 ; i < getNumberOfPoints() ; i++){
			_ypoints[i] = (getYPoints()[i]*block)+ofset;
		}
		return _ypoints;
	}
	
	/**
	 * @return the minX
	 */
	public final int getMinX() {
		return minX;
	}

	/**
	 * @param minX the minX to set
	 */
	public final void setMinX(int minX) {
		this.minX = minX;
	}

	/**
	 * @return the maxX
	 */
	public final int getMaxX() {
		return maxX;
	}

	/**
	 * @param maxX the maxX to set
	 */
	public final void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	/**
	 * @return the minY
	 */
	public final int getMinY() {
		return minY;
	}

	/**
	 * @param minY the minY to set
	 */
	public final void setMinY(int minY) {
		this.minY = minY;
	}

	/**
	 * @return the maxY
	 */
	public final int getMaxY() {
		return maxY;
	}

	/**
	 * @param maxY the maxY to set
	 */
	public final void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	/**
	 * @return the centerX
	 */
	public final int getCenterX() {
		return centerX;
	}

	/**
	 * @param centerX the centerX to set
	 */
	public final void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	/**
	 * @return the centerY
	 */
	public final int getCenterY() {
		return centerY;
	}

	/**
	 * @param centerY the centerY to set
	 */
	public final void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	private void setMinMax(){
		int[] xpoints = getXPoints().clone();
		int[] ypoints = getYPoints().clone();
		Arrays.sort(xpoints);
		Arrays.sort(ypoints);
		this.minX = xpoints[0];
		this.maxX = xpoints[xpoints.length - 1];
		this.minY = ypoints[0];
		this.maxY = ypoints[ypoints.length - 1];
		setCenterPoints();
	}
	
	private void setCenterPoints(){
		this.centerX = (this.maxX - this.minX)/2;
		this.centerY = (this.maxY - this.minY)/2;
	}
	
	public static class Engine2DRectangleBoundingBox {
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
	 * @see Engine2DPolygonBoundingBox#checkCollision(Engine2DPolygonBoundingBox) checkCollision(BoundingBox)
	 */	
	public Engine2DRectangleBoundingBox(int x, int y, int width, int height){
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		/**
		 * The method checks to see if the two bounding box or overlapping each
		 * other
		 * 
		 * @param recBox
		 *            the bounding box to check for collision
		 * @return true if its overlapping false if its not
		 */
		public boolean checkCollision(Engine2DRectangleBoundingBox recBox) {
			return ((this.x + this.width >= recBox.getX()) && (this.x <= recBox.getX() + recBox.getWidth()))
					&& (this.y + this.height >= recBox.getY()) && (this.y <= recBox.getY() + recBox.getHeight());
		}
		
		public boolean checkCollision(Engine2DPolygonBoundingBox polygonBox) {
			Rectangle rec = new Rectangle(x, y, width, height);
			return Engine2DPolygonBoundingBox.checkAreaCollision(polygonBox.polygon, this);
		}
		
		/**
		 * updates x position using the original offset and the multiplier/speed
		 * 
		 * @param original the original offset
		 * @param multiplier the speed multiplier
		 */
		public void moveX(int original, double multiplier) {
			this.x += (original * multiplier);
		}

		/**
		 * updates y position using the original offset and the multiplier/speed
		 * 
		 * @param original the original offset
		 * @param multiplier the speed multiplier
		 */
		public void moveY(int orginal, double multiplier) {
			this.y += (orginal * multiplier);
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

}