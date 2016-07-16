package game.engine2D;

import game.engine2D.Engine2DBoundingPolygon;

public abstract class Engine2DPolygonEntity extends Engine2DEntity {
private final Engine2DBoundingPolygon polygonBox;
	
	public Engine2DPolygonEntity(Engine2DBoundingPolygon polygonBox, Engine2DGame game) {
		super(game);
		this.polygonBox = polygonBox;
	}
	
	public Engine2DPolygonEntity(int[] xpoints, int[] ypoints, Engine2DGame game) {
		super(game);
		this.polygonBox = new Engine2DBoundingPolygon(xpoints, ypoints);
	}
	
	public int[] getXPoints(){
		return polygonBox.getXPoints();
	}
	
	public int[] getY(){
		return polygonBox.getXPoints();
	}
	
	public Engine2DBoundingPolygon getBoundingBox(){
		return polygonBox;
	}

}
