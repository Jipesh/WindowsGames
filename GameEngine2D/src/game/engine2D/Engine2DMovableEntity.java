package game.engine2D;

public abstract class Engine2DMovableEntity extends Engine2DEntity implements Runnable{

	private final Object monitor = new Object();
	private final Thread entityThread;
	
	public Engine2DMovableEntity(Engine2DGame game) {
		super(game);
		entityThread = new Thread();
	}
	
	/**
	 * This method will be called at the same rate as what was specified
	 * in the game start method
	 * 
	 * @see Engine2DGame#start(int) start(fps)
	 * @see Engine2DGame#setFPS(float) setFPS(fps)
	 */
	public abstract void update();

	@Override
	public void run(){
		startLoop();
	}
	
	public void stopLoop() throws InterruptedException{
		entityThread.join();
	}
	
	private void startLoop(){
		long tickTime = (long) (1000f / getGame().getFPS());
		long now;
		long lastTime = System.currentTimeMillis();
		while (true) {
			now = System.currentTimeMillis();
			long dif = now - lastTime;
			if (dif >= tickTime) {
				update();
				lastTime = System.currentTimeMillis();
			}
			long temp1 = System.currentTimeMillis();
			if (temp1 - now < 1) {
				synchronized (monitor) {
					try {
						monitor.wait(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
