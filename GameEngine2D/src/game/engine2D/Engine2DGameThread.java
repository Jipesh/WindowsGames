package game.engine2D;

import java.util.concurrent.LinkedBlockingQueue;

final class Engine2DGameThread extends Thread {

	private final Engine2DGame game;
	private final LinkedBlockingQueue<Runnable> mProcessQueue;
	private final TaskHandler handler;
	private final Thread thread;
	private boolean running;

	Engine2DGameThread(Engine2DGame game,String name) {
		super(name);
		this.game = game;
		this.mProcessQueue = new LinkedBlockingQueue<Runnable>();
		this.handler = new TaskHandler();
		this.running = true;
		this.thread = this;
	}

	public final void startThread() {
		thread.start();
	}

	public final void sumbitTask(Runnable runnable) {
		if (runnable != null) {
			mProcessQueue.add(runnable);
		}
	}

	@Override
	public final void run() {
		while (running) {
			if (!mProcessQueue.isEmpty()) {
				if (handler.status.equals(TaskHandler.TASK_NOT_HANDLED)) {
					processTask(handler.getCurrentRunnable());
				}
				handler.setStatus(false, mProcessQueue.peek());
				try {
					processTask(mProcessQueue.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				handler.setStatus(false, null);
			}
			try {
				thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void stopThread() throws InterruptedException {
		running = false;
		thread.join();
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
		 * This status is for when handled is false and current runnable is not null
		 */
		final static String TASK_NOT_HANDLED = "NOT HANDLED";
		
		/**
		 * This status is for when handled is true and current runnable is not null
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
			if(currentRunnable != null){
				if(handled){
					status = HANDLED_TASK;
				}else{
					status = TASK_NOT_HANDLED;
				}
			}else{
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
		final String getStatus(){
			return status;
		}

	}
}