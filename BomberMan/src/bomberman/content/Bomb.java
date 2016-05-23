package bomberman.content;

public class Bomb {
private int x, y, duration, size;

	public Bomb(int x, int y, int dur, int size) {
		this.x = x;
		this.y = y;
		this.duration = dur;
		this.size = size;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	private final class Explosion{
		
		private Explosion(int x, int y, int size, Game game){
			
		}
		
	}
}
