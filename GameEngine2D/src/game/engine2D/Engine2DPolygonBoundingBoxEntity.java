/**
 * 
 * @author Jipesh
 */
package game.engine2D;

import game.engine2D.Engine2DPolygonBoundingBox;

public abstract class Engine2DPolygonBoundingBoxEntity extends Engine2DEntity {
private final Engine2DPolygonBoundingBox polygonBox;

	public Engine2DPolygonBoundingBoxEntity(Engine2DPolygonBoundingBox polygonBox) {
		super();
		this.polygonBox = polygonBox;
	}
	
	public Engine2DPolygonBoundingBoxEntity(int[] xpoints, int[] ypoints) {
		super();
		this.polygonBox = new Engine2DPolygonBoundingBox(xpoints, ypoints);
	}
	
	public Engine2DPolygonBoundingBoxEntity(Engine2DPolygonBoundingBox polygonBox, Engine2DGame game) {
		super(game);
		this.polygonBox = polygonBox;
	}
	
	public Engine2DPolygonBoundingBoxEntity(int[] xpoints, int[] ypoints, Engine2DGame game) {
		super(game);
		this.polygonBox = new Engine2DPolygonBoundingBox(xpoints, ypoints);
	}
	
	public int[] getXPoints(){
		return polygonBox.getXPoints();
	}
	
	public int[] getY(){
		return polygonBox.getXPoints();
	}
	
	public Engine2DPolygonBoundingBox getBoundingBox(){
		return polygonBox;
	}

}
