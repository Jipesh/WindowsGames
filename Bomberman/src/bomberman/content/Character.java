package bomberman.content;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import game.engine2D.Engine2DEntity;
import game.engine2D.Engine2DMovableEntity;
import game.engine2D.Engine2DRectangleBoundingBox;

public abstract class Character extends Engine2DMovableEntity {
	private final Image[][] skins = new Image[4][7];
	private int bombs, explosion_size;
	protected int skin;
	private int running, min_running, animation, character;
	private List<Bomb> ontop = new ArrayList<>();
	private float speed;

	/**
	 * Since this is a 2D game and their are collision checks therefore i use a
	 * bounding box which stores the x, y width and height of the player since
	 * the player has multiple skin stored in the sprite sheet each of them are
	 * cut and placed in a multidimensional array where the first array
	 * represent
	 * 
	 * @param character
	 *            the character id currently only 1 available
	 * @param x
	 *            the exact x position of the player
	 * @param y
	 *            the exact y position of the player
	 * @param game
	 *            the game which the player belongs to
	 * 
	 * @see Player#setSkins(int) setSkin(character_id)
	 * @see BoundingBox#BoundingBox(int, int, int, int)
	 *      BoundingBox(x,y,width,height)
	 */
	public Character(int character, int x, int y) {
		super();
		this.setBoundingBox(new Engine2DRectangleBoundingBox(x, y, 37, 37));
		this.bombs = 3;
		this.speed = 1;
		this.explosion_size = 1;
		this.character = character;
		setSkins(character);
		this.animation = 0;

		new Timer().scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				int temp = ++animation;
				if (temp % 2 == 0) {
					animation = 2;
				} else {
					animation = 1;
				}
			}

		}, 0, 200);
	}

	/**
	 * The skin id(j) which is what position left, right etc. Then the second(i)
	 * represent running state another set of sprite will be added for running =
	 * 2 later to give it a moving animation.
	 * 
	 * @param character
	 *            the ID of the character
	 * 
	 * @see Game#getSprite(int, int) getSprite(x,y)
	 */
	protected void setSkins(int character) {
		int start_y = ((character - 1) * 3) + 1;
		min_running = start_y;
		for (int i = start_y; i < start_y + 3; i++) {
			for (int j = 0; j <= 3; j++) {
				skins[j][i] = getGame().getSprite(j, i);
			}
		}
		skin = 0;
		running = min_running;
	}

	/**
	 * Allows player to move up if it is within the battlefield
	 * <p>
	 * The amount of steps the player moves per button press is dependent on the
	 * speed
	 * 
	 * @see BoundingBox#moveY(int, double) moveY(original, multiplier)
	 * @see BoundingBox#moveX(int, double) moveX(original, multiplaier)
	 */
	public void moveUp() {
		if (getY() > 40) {
			setRunning(getMinRunning() + getanimationFrame());
			Engine2DRectangleBoundingBox box = new Engine2DRectangleBoundingBox((int) getX(), (int) getY(),
					(int) getWidth(), (int) getHeight());
			box.moveY(1, -speed);
			Engine2DEntity entity = getGame().checkCollision(box);
			if (entity != null) {
				if(entity instanceof Bomb){
					Bomb bomb = (Bomb)entity;
					if(getOnTop_READONLY().contains(bomb)){
						getBoundingBox().moveY(1, -speed);
					}
				}else{
					setY(entity.getY()+getHeight()+4);
				}
			}else {
				getBoundingBox().moveY(1, -speed);
			}
			box = null;
		}
	}

	public void moveDown() {
		if (getY() < (11 * 40) + 2) {
			setRunning(getMinRunning() + getanimationFrame());
			Engine2DRectangleBoundingBox box = new Engine2DRectangleBoundingBox((int) getX(), (int) getY(),
					(int) getWidth(), (int) getHeight());
			box.moveY(1, speed);
			Engine2DEntity entity = getGame().checkCollision(box);
			if (entity != null) {
				if(entity instanceof Bomb){
					Bomb bomb = (Bomb)entity;
					if(getOnTop_READONLY().contains(bomb)){
						getBoundingBox().moveY(1, speed);
					}
				}else{
					setY(entity.getY()-38);
				}
			} else {
				getBoundingBox().moveY(1, speed);
			}
			box = null;
		}
	}

	public void moveLeft() {
		if (getX() > 40) {
			setRunning(getMinRunning() + getanimationFrame());
			Engine2DRectangleBoundingBox box = new Engine2DRectangleBoundingBox((int) getX(), (int) getY(),
					(int) getWidth(), (int) getHeight());
			box.moveX(1, -speed);
			Engine2DEntity entity = getGame().checkCollision(box);
			if (entity != null) {
				if(entity instanceof Bomb){
					Bomb bomb = (Bomb)entity;
					if(getOnTop_READONLY().contains(bomb)){
						getBoundingBox().moveX(1, -speed);
					}
				}else{
					setX(entity.getX()+getWidth()+4);
				}
			} else {
				getBoundingBox().moveX(1, -speed);
			}
			box = null;
		}
	}

	public void moveRight() {
		if ((getX() < (17 * 40) + 4)) {
			setRunning(getMinRunning() + getanimationFrame());
			Engine2DRectangleBoundingBox box = new Engine2DRectangleBoundingBox((int) getX(), (int) getY(),
					(int) getWidth(), (int) getHeight());
			box.moveX(1, speed);
			Engine2DEntity entity = getGame().checkCollision(box);
			if (entity != null) {
				if(entity instanceof Bomb){
					Bomb bomb = (Bomb)entity;
					if(getOnTop_READONLY().contains(bomb)){
						getBoundingBox().moveX(1, speed);
					}
				}else{
					setX(entity.getX()-38);
				}
			} else {
				getBoundingBox().moveX(1, speed);
			}
			box = null;
		}
	}

	/**
	 * player position are exact therefore need to be changed to box type to
	 * make it realistic the position of the bomb is decided by the midpoint of
	 * the character
	 * 
	 * @see Bomb#Bomb(int, int, int, int, Game) Bomb(x,y,duration,size,game)
	 * 
	 */
	public void plantBomb() {
		if (bombs > 0) {
			int middleX = (int) (getX() + (getWidth() / 2));
			int x = middleX / 40;
			int middleY = (int) (getY() + (getHeight() / 2));
			int y = middleY / 40;
			Bomb bomb = new Bomb(this, (x * 40), (y * 40), 4, explosion_size);
			if (getGame().checkMap(x, y) == 0) {
				getGame().addBomb(bomb);
				bombs--;
				ontop.add(bomb);
			}
		}
	}

	/**
	 * makes sure that the player is still on top of the bomb else if will
	 * remove the bomb from the list so collision check will be no longer
	 * ignored
	 * 
	 * @see Player#play() play()
	 */
	protected void updateWalkable() {
		for (Bomb bomb : getOnTop_READONLY()) {
			if (!(getBoundingBox().checkCollision(bomb.getBoundingBox()))) {
				removeFromOnTop(bomb);
			}
		}
	}

	/**
	 * checks to see if the player is colliding with any of the power-ups if so
	 * the id of it will update the player speed, explosion size or bombs
	 * 
	 */
	protected void pickPower() {
		for (PowerUp power : getGame().getSpecials_READONLY()) {
			Engine2DRectangleBoundingBox powerBox = power.getBoundingBox();
			if (powerBox.checkCollision(getBoundingBox()) && power.getState() == State.ALIVE) {
				if (power.getID() == 1) {
					explosion_size--;
					if (explosion_size < 1) {
						explosion_size = 1;
					}
				} else if (power.getID() == 2) {
					--bombs;
					if (bombs < 1) {
						bombs = 1;
					}
				} else if (power.getID() == 3) {
					--speed;
					if (speed < 1) {
						speed = 1;
					}
				} else if (power.getID() == 4) {
					explosion_size++;
					System.out.println(explosion_size);
				} else if (power.getID() == 5) {
					++bombs;
				} else if (power.getID() == 6) {
					++speed;
				}
				power.destroy();
			}
		}
	}

	/**
	 * 
	 * @return the character id
	 */
	public int getCharacter() {
		return character;
	}

	protected boolean isOntop(Bomb bomb) {
		return ontop.contains(bomb);
	}

	void recoverBomb() {
		bombs++;
	}

	/**
	 * @return the bombs
	 */
	public int getBombs() {
		return bombs;
	}

	/**
	 * @param bombs
	 *            the bombs to set
	 */
	public void setBombs(int bombs) {
		this.bombs = bombs;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	void setSkin(int skin) {
		this.skin = skin;
	}

	void setRunning(int running) {
		this.running = running;
	}

	int getRunning() {
		return running;
	}

	/**
	 * @return the explostion_size
	 */
	public int getExplosion_size() {
		return explosion_size;
	}

	/**
	 * @param explostion_size
	 *            the explostion_size to set
	 */
	public void setExplostion_size(int explosion_size) {
		this.explosion_size = explosion_size;
	}

	public final int getMinRunning() {
		return min_running;
	}

	public final int getanimationFrame() {
		return animation;
	}

	/**
	 * <b> What skin stands for <b>
	 * <p>
	 * <li>skin[0] = facing_down</li>
	 * <li>skin[1] = facing_up</li>
	 * <li>skin[2] = facing_left</li>
	 * <li>skin[3] = facing_right</li>
	 * 
	 * @return image according to which skin is chosen and whether the running
	 *         is 0 or 1
	 */
	public Image getImage() {
		return skins[skin][running];
	}

	public Engine2DRectangleBoundingBox getBoundingBox() {
		return (Engine2DRectangleBoundingBox) super.getBoundingBox();
	}

	public Game getGame() {
		return (Game) super.getGame();
	}

	public void destroy() {
		super.destroy();
		getGame().invokeDestroy(this);
	}

	ArrayList<Bomb> getOnTop_READONLY() {
		return (ArrayList<Bomb>) ((ArrayList<Bomb>) ontop).clone();
	}

	void addToOnTop(Bomb bomb) {
		this.ontop.add(bomb);
	}

	void removeFromOnTop(Bomb bomb) {
		if (ontop.contains(bomb)) {
			ontop.remove(bomb);
		}
	}

}
