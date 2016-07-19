/**
 * 
 * @author Jipesh
 */
package game.engine2D;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Engine2DMovableRectangleBoundingBoxEntity extends Engine2DRectangleBoundingBoxEntity implements Runnable{
	
	private Timer loopTimer;
	
	public Engine2DMovableRectangleBoundingBoxEntity(Engine2DPolygonBoundingBox.Engine2DRectangleBoundingBox recBox, Engine2DGame game) {
		super(recBox,game);
	}
	
	public Engine2DMovableRectangleBoundingBoxEntity(int x, int y, int width, int height, Engine2DGame game) {
		super(x,y,width,height,game);
	}
	
	/**
	 * This method will be called at the same rate as what was specified
	 * in the game start method
	 * 
	 * @see Engine2DGame#start(int) start(fps)
	 */
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
