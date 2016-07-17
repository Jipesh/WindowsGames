/**
 * 
 * @author Jipesh
 */
package game.engine2D;

import game.engine2D.Engine2DPolygonBoundingBox.Engine2DRectangleBoundingBox;

public abstract class Engine2DRectangleBoundingBoxEntity extends Engine2DEntity{
private final Engine2DRectangleBoundingBox recBox;
	
	public Engine2DRectangleBoundingBoxEntity(Engine2DRectangleBoundingBox recBox) {
		super();
		this.recBox = recBox;
	}

	public Engine2DRectangleBoundingBoxEntity(int x, int y, int width, int height) {
		super();
		this.recBox = new Engine2DRectangleBoundingBox(x,y,width,height);
	}

	public Engine2DRectangleBoundingBoxEntity(Engine2DRectangleBoundingBox recBox, Engine2DGame game) {
		super(game);
		this.recBox = recBox;
	}
	
	public Engine2DRectangleBoundingBoxEntity(int x, int y, int width, int height, Engine2DGame game) {
		super(game);
		this.recBox = new Engine2DRectangleBoundingBox(x,y,width,height);
	}

	public Engine2DRectangleBoundingBox getBoundingBox() {
		return recBox;
	}
	
	public int getX(){
		return recBox.getX();
	}
	
	public int getY(){
		return recBox.getY();
	}
	
	public int getWidth(){
		return recBox.getWidth();
	}
	
	public int getHeight(){
		return recBox.getHeight();
	}

}
