package game.engine2D;

final class Engine2DGameThread extends Thread {

	private final Object monitor = new Object();
	private final Thread mThread = this;

	public Engine2DGameThread(String name) {
		super(name);
	}

	public void startThread() {
		mThread.start();
	}

	public void stopThread() throws InterruptedException {
		mThread.join();
	}

	public Engine2DGame getGame() {
		return Engine2DGame.GAME;
	}

	@Override
	public void run() {
		loop();
	}

	private void loop() {
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
			synchronized (monitor) {
				try {
					monitor.wait(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void tick() {
		if (!getGame().getGameOver()) {
			if (getGame().getRunning()) {
				getGame().gameLoop();
				getGame().render();
			} else {
				getGame().pause();
			}
		} else {
			getGame().gameOver();
		}
	}

}