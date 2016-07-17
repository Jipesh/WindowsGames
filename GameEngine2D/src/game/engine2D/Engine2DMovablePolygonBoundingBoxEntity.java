package game.engine2D;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Engine2DMovablePolygonBoundingBoxEntity extends Engine2DPolygonBoundingBoxEntity implements Runnable{
private Timer loopTimer;

	public Engine2DMovablePolygonBoundingBoxEntity(Engine2DPolygonBoundingBox polygonBox){
		super(polygonBox);
	}
	
	public Engine2DMovablePolygonBoundingBoxEntity(int[] xpoints, int[] ypoints) {
		super(xpoints,ypoints);
	}

	/**
	 * 
	 * @deprecated calling a game from another thread and updating it's element here as well
	 * 	on the main game thread cause ConcurrentModificationException
	 */
	public Engine2DMovablePolygonBoundingBoxEntity(Engine2DPolygonBoundingBox polygonBox, Engine2DGame game) {
		super(polygonBox, game);
	}
	
	/**
	 * 
	 * @deprecated calling a game from another thread and updating it's element here as well
	 * 	on the main game thread cause ConcurrentModificationException
	 */
	public Engine2DMovablePolygonBoundingBoxEntity(int[] xpoints, int[] ypoints, Engine2DGame game) {
		super(xpoints, ypoints, game);
	}
	
	public abstract void update();
	
	public void stopLoop(){
		loopTimer.cancel();
	}
	
	public void resume(){
		startLoop();
	}

	@Override
	public void run(){
		startLoop();
	}
	
	private void startLoop(){
		loopTimer = new Timer();
		loopTimer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				if (!getGame().getRunning()) {
					loopTimer.cancel();
				}
				update();
			}
		}, 0, (long) (1000f/(getGame().getFPS())));
	}

}
