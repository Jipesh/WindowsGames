package game.engine2D;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Engine2DMovableEntity extends Entity implements Runnable{
	private Timer loopTimer;
	
	public Engine2DMovableEntity(AbstractGame game) {
		super(game);
	}
	
	public Engine2DMovableEntity(Engine2DBoundingShape box, AbstractGame game) {
		super(box,game);
	}
	
	public Engine2DMovableEntity(Engine2DBoundingShape.RectangleBoundingShape recBox, AbstractGame game) {
		super(recBox,game);
	}
	
	public abstract void update();
	
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
