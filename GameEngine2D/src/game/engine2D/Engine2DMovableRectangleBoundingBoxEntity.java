package game.engine2D;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Engine2DMovableRectangleBoundingBoxEntity extends Engine2DRectangleBoundingBoxEntity implements Runnable{
	private Timer loopTimer;
	
	public Engine2DMovableRectangleBoundingBoxEntity(Engine2DPolygonBoundingBox.Engine2DRectangleBoundingBox recBox) {
		super(recBox);
	}
	

	public Engine2DMovableRectangleBoundingBoxEntity(int x, int y, int width, int height) {
		super(x,y,width,height);
	}
	
	/**
	 * 
	 * @deprecated calling a game from another thread and updating it's element here as well
	 * 	on the main game thread causes ConcurrentModificationException
	 */
	public Engine2DMovableRectangleBoundingBoxEntity(Engine2DPolygonBoundingBox.Engine2DRectangleBoundingBox recBox, Engine2DGame game) {
		super(recBox,game);
	}
	
	/**
	 * 
	 * @deprecated calling a game from another thread and updating it's element here as well
	 * 	on the main game thread cause ConcurrentModificationException
	 */
	public Engine2DMovableRectangleBoundingBoxEntity(int x, int y, int width, int height, Engine2DGame game) {
		super(x,y,width,height,game);
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
