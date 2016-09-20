package game.engine2D;

import java.util.concurrent.LinkedBlockingQueue;

public class Engine2DEventDispatcher extends Thread {

	private final Object monitor = new Object();
	private final LinkedBlockingQueue<Runnable> mProcessQueue;
	private final TaskHandler handler;
	private final Thread mThread;
	private boolean running;

	Engine2DEventDispatcher(String name) {
		super(name);
		this.mProcessQueue = new LinkedBlockingQueue<Runnable>();
		this.handler = new TaskHandler();
		this.running = true;
		this.mThread = this;
	}

	final void startThread() {
		mThread.start();
	}

	final void sumbitTask(Runnable runnable) {
		if (runnable != null) {
			mProcessQueue.add(runnable);
		}
	}

	@Override
	public final void run() {
		while (running) {
			if (!mProcessQueue.isEmpty()) {
				if (handler.getStatus().equals(TaskHandler.TASK_NOT_HANDLED)) {
					processTask(handler.getCurrentRunnable());
				}
				handler.setStatus(false, mProcessQueue.peek());
				try {
					processTask(mProcessQueue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				handler.setStatus(false, null);
			}
			synchronized (monitor) {
				try {
					monitor.wait(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	void stopThread() throws InterruptedException {
		running = false;
		mThread.join();
	}

	/**
	 * @return the game
	 */
	public final Engine2DGame getGame() {
		return Engine2DGame.GAME;
	}
	
	private void processTask(Runnable runnable) {
		runnable.run();
		handler.setStatus(true, runnable);
	}

	private class TaskHandler {

		/**
		 * This status is for when handled is false and current runnable is null
		 */
		final static String NOT_HANDLING_TASK = "NOT HANDLING";

		/**
		 * This status is for when handled is false and current runnable is not
		 * null
		 */
		final static String TASK_NOT_HANDLED = "NOT HANDLED";

		/**
		 * This status is for when handled is true and current runnable is not
		 * null
		 */
		final static String HANDLED_TASK = "HANDLED";

		private boolean handled = false;
		private Runnable currentRunnable = null;
		private String status;

		TaskHandler() {
			status = NOT_HANDLING_TASK;
		}

		/**
		 * @return the handled
		 */
		final boolean isHandled() {
			return handled;
		}

		/**
		 * @param handled
		 *            the handled to set
		 */
		final void setStatus(boolean handled, Runnable currentRunnable) {
			this.handled = handled;
			this.currentRunnable = currentRunnable;
			if (currentRunnable != null) {
				if (handled) {
					status = HANDLED_TASK;
				} else {
					status = TASK_NOT_HANDLED;
				}
			} else {
				status = NOT_HANDLING_TASK;
			}
		}

		/**
		 * @return the currentRunnable
		 */
		final Runnable getCurrentRunnable() {
			return currentRunnable;
		}

		/**
		 * 
		 * @return the status of the handler
		 */
		final String getStatus() {
			return status;
		}

	}

}
