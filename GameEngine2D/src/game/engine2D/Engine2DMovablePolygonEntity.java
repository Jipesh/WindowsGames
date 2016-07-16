package game.engine2D;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Engine2DMovablePolygonEntity extends Engine2DPolygonEntity implements Runnable{
private Timer loopTimer;
	
	public Engine2DMovablePolygonEntity(Engine2DBoundingPolygon polygonBox, Engine2DGame game) {
		super(polygonBox, game);
	}

	public Engine2DMovablePolygonEntity(int[] xpoints, int[] ypoints, Engine2DGame game) {
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
