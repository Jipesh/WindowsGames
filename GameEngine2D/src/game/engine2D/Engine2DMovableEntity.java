package game.engine2D;

public abstract class Engine2DMovableEntity extends Engine2DEntity {

	private final Object monitor = new Object();
	private EntityThread entityThread;
	
	public Engine2DMovableEntity() {
		super();
		entityThread = new EntityThread();
	}
	
	/**
	 * This method will be called at the same rate as what was specified
	 * in the game start method
	 * 
	 * @see Engine2DGame#start(int) start(fps)
	 * @see Engine2DGame#setFPS(float) setFPS(fps)
	 */
	public abstract void update();
	
	public void stopThread() throws InterruptedException{
		entityThread.join();
	}
	
	/**
	 * re-instantiates the thread, if thread is already instantiated then
	 * it starts the thread
	 * 
	 * @throws InterruptedException if the thread was interrupted
	 */
	public void resetThread() throws InterruptedException{
		if(entityThread != null && entityThread.isAlive()){
			stopThread();
		}
		entityThread = new EntityThread();
	}
	
	public Thread getThread(){
		return entityThread;
	}
	
	private void startLoop(){
		long tickTime = (long) (1000f / getGame().getFPS());
		long now;
		long lastTime = System.currentTimeMillis();
		while (getGame().isAppRunning()) {
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
	
	private class EntityThread extends Thread{
		
		@Override
		public void run(){
			startLoop();
		}
	}

}
