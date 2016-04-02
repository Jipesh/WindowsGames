/**
 * 
 * @author Jipesh
 */
package arrowgame;

import java.awt.Rectangle;

public class BoundingBox {
private int x, y, width, height;
	
	/**
	 * creates a bounding box for an object 
	 */
	public BoundingBox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean checkCollision(BoundingBox box){
		if ( (box.getX() + box.getWidth()) >= this.x && box.getX() <= (this.x + this.width)
				&& (box.getY() + box.getHeight()) >= this.y && box.getY() <= (this.y + this.height)){
			return true;
		}else{
		
		return false;
		}
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

}
