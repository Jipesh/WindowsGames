package game.engine2D;

import game.engine2D.Engine2DBoundingPolygon.Engine2DBoundingRectangle;

public abstract class Engine2DRectangleEntity extends Engine2DEntity{
private final Engine2DBoundingRectangle recBox;
	
	public Engine2DRectangleEntity(Engine2DBoundingRectangle recBox) {
		super();
		this.recBox = recBox;
	}

	public Engine2DRectangleEntity(int x, int y, int width, int height) {
		super();
		this.recBox = new Engine2DBoundingRectangle(x,y,width,height);
	}

	public Engine2DRectangleEntity(Engine2DBoundingRectangle recBox, Engine2DGame game) {
		super(game);
		this.recBox = recBox;
	}
	
	public Engine2DRectangleEntity(int x, int y, int width, int height, Engine2DGame game) {
		super(game);
		this.recBox = new Engine2DBoundingRectangle(x,y,width,height);
	}

	public Engine2DBoundingRectangle getBoundingBox() {
		return recBox;
	}
	
	public int getX(){
		return recBox.getX();
	}
	
	public int getY(){
		return recBox.getY();
	}
	
	public int getWidth(){
		return recBox.getX();
	}
	
	public int getHeight(){
		return recBox.getHeight();
	}

}
