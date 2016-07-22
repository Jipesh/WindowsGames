package game.engine2D;


final class Engine2DGameThread extends Thread {
	
	private final Object monitor = new Object();
	private final Engine2DGame game;
	private final Thread mThread = this;
	
	public Engine2DGameThread(Engine2DGame game, String name){
		super(name);
		this.game = game;
	}
	
	public void startThread(){
		mThread.start();
	}
	
	public void stopThread() throws InterruptedException{
		mThread.join();;
	}

	public Engine2DGame getGame() {
		return game;
	}
	
	@Override
	public void run(){
		loop();
	}
	
	private void loop(){
		long tickTime = (long) (1e3f / getGame().getFPS());
		long now;
		long lastTime = System.currentTimeMillis();
		while (getGame().isAppRunning()) {
			now = System.currentTimeMillis();
			long dif = now - lastTime;
			if (dif >= tickTime) {
				tick();
				lastTime = System.currentTimeMillis();
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	private void tick() {
		if (!game.getGameOver()) {
			if (game.getRunning()) {
				game.gameLoop();
				game.render();
			} else {
				game.pause();
			}
		} else {
			game.gameOver();
		}
	}

	
}