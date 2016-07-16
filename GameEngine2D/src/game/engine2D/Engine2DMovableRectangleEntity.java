package game.engine2D;

import java.util.Timer;
import java.util.TimerTask;

import game.engine2D.Engine2DBoundingPolygon.Engine2DBoundingRectangle;

public abstract class Engine2DMovableRectangleEntity extends Engine2DRectangleEntity implements Runnable{
	private Timer loopTimer;
	
	public Engine2DMovableRectangleEntity(Engine2DBoundingPolygon.Engine2DBoundingRectangle recBox, Engine2DGame game) {
		super(recBox,game);
	}
	
	public Engine2DMovableRectangleEntity(int x, int y, int width, int height, Engine2DGame game) {
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
