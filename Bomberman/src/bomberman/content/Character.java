package bomberman.content;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.engine2D.AbstractGame;
import game.engine2D.BoundingBox;
import game.engine2D.Entity;

public abstract class Character extends Entity implements Runnable{
	private final Game game;
	private final Image[][] skins = new Image[4][7];
	private int bombs, explosion_size;
	protected int skin;
	private int running;
	private int last_running;
	private int character;
	private List<Bomb> ontop = new ArrayList<>();
	private double speed;
	
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
	public Character(int character, int x, int y, Game game) {
		super(x, y, 37, 37, game);
		this.game = game;
		this.bombs = 1;
		this.speed = 1;
		this.explosion_size = 1;
		skin = 0;
		skins[1][0] = game.getSprite(1, 4);
		this.character = character;
		setSkins(character);
		last_running = 0;
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
		if (character == 1) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					int running = i + 1;
					skins[j][i] = game.getSprite(j, running, 36, 36);
				}
			}
			running = 0;
		} else if (character == 2) {
			for (int i = 2; i < 7; i++) {
				for (int j = 0; j < 4; j++) {
					int running = i + 1;
					skins[j][i] = game.getSprite(j, running, 36, 36);
				}
			}
			running = 3;
		}
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
		if (getY() > 40){
			getBoundingBox().moveY(1, -speed);
			Entity entity = game.checkCollision(getBoundingBox());
			Bomb bomb = game.bombCollision((Entity)this);
			if (entity != null) {
				moveDown();
				if((this.getX() + (this.getWidth()/2)) <= (entity.getX())){
					moveLeft();
				}else if((this.getX() + (this.getWidth()/2)) >= (entity.getX() + entity.getWidth())){
					moveRight();
				}
				if(getX() % 40 >= 0 && getX() % 40 <= 8){
					int b = 2 - (getX() % 40);
					getBoundingBox().setX(getX() + b);
				}
			}else if (bomb != null && !isOntop(bomb)){
				moveDown();
			}
		}
	}

	public void moveDown() {
		if (getY() < (11 * 40) + 2){
			getBoundingBox().moveY(1, speed);
			Entity entity = game.checkCollision(getBoundingBox());
			Bomb bomb = game.bombCollision((Entity)this);
			if (entity != null) {
				moveUp();
				if((this.getX() + (this.getWidth()/2)) <= entity.getX()){
					moveLeft();
				}else if((this.getX() + (this.getWidth()/2)) >= (entity.getX() + entity.getWidth())){
					moveRight();
				}
				if(getX() % 40 >= 0 && getX() % 40 <= 8){
					int b = 2 - (getX() % 40);
					getBoundingBox().setX(getX() + b);
				}
			}else if (bomb != null && !isOntop(bomb)){
				moveUp();
			}
		}
	}

	public void moveLeft() {
		if (getX() > 40) {
			getBoundingBox().moveX(1, -speed);
			Entity entity = game.checkCollision(getBoundingBox());
			Bomb bomb = game.bombCollision((Entity)this);
			if (entity != null) {
				moveRight();
				if((this.getY() + (this.getHeight()/2)) <= entity.getY()){
					moveUp();
				}else if((this.getY() + (this.getHeight()/2)) >= (entity.getY() + entity.getHeight())){
					moveDown();
				}
				if(getY() % 40 >= 0 && getY() % 40 <= 8){
					int x = 2 - (getY() % 40);
					getBoundingBox().setY(getY() + x);
				}
			}else if (bomb != null && !isOntop(bomb)){
				moveRight();
			}
		}
	}

	public void moveRight() {
		if ((getX() < (17 * 40) + 4)){
			getBoundingBox().moveX(1, speed);
			Entity entity = game.checkCollision(getBoundingBox());
			Bomb bomb = game.bombCollision((Entity)this);
			if (entity != null) {
				moveLeft();
				if((this.getY() + (this.getHeight()/2)) <= (entity.getY())){
					moveUp();
				}else if((this.getY() + (this.getHeight()/2)) >= (entity.getY() + entity.getHeight())){
					moveDown();
				}
				if(getY() % 40 >= 0 && getY() % 40 <= 8){
					int x = 2 - (getY() % 40);
					getBoundingBox().setY(getY() + x);
				}
			}else if (bomb != null && !isOntop(bomb)){
				moveLeft();
			}
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
			int middleX = getX() + (getWidth() / 2);
			int x = middleX / 40;
			int middleY = getY() + (getHeight() / 2);
			int y = middleY / 40;
			Bomb bomb = new Bomb(this, (x * 40), (y * 40), 4, explosion_size, game);
			if (game.getBombs().contains(bomb)) {
				bombs--;
			}
		}
	}
	
	/**
	 * adds a bomb to the walkable list so it's ignored when doing collision check
	 * 
	 * @param bomb the bomb to allow walking over
	 */
	protected void addWalkable(Bomb bomb){
		ontop.add(bomb);
	}
	
	/**
	 * makes sure that the player is still on top of the bomb else if will  remove the bomb
	 * from the list so collision check will be no longer ignored
	 * 
	 * @see
	 * 		Player#play() play()
	 */
	protected void updateWalkable(){
		Iterator<Bomb> bombs = ontop.iterator();
		while(bombs.hasNext()){
			Bomb bomb = bombs.next();
			if(!(this.getBoundingBox().checkCollision(bomb.getBoundingBox()))){
				bombs.remove();
			}
		}
	}

	/**
	 * checks to see if the player is colliding with any of the power-ups if so the id
	 * of it will update the player speed, explosion size or bombs
	 * 
	 */
	protected void pickPower() {
		Iterator<PowerUp> specials = game.getSpecials().iterator();
		while (specials.hasNext()) {
			PowerUp power = specials.next();
			if (power.getBoundingBox().checkCollision(getBoundingBox())) {
				if (power.getID() == 1) {
					explosion_size--;
					if (explosion_size < 1) {
						explosion_size = 1;
					}
				} else if (power.getID() == 2) {
					bombs--;
					if (bombs < 1) {
						bombs = 1;
					}
				} else if (power.getID() == 3) {
					speed--;
					if (speed < 1) {
						speed = 1;
					}
				} else if (power.getID() == 4) {
					explosion_size++;
					System.out.println(explosion_size);
				} else if (power.getID() == 5) {
					bombs++;
				} else if (power.getID() == 6) {
					speed++;
				}
				game.makeAvailable((power.getX()/40), (power.getY()/40));
				specials.remove();
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
	
	protected boolean isOntop(Bomb bomb){
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
		if (running == 0) {
			return skins[skin][running];
		} else {
			return skins[skin][running];
		}
	}
	

}
