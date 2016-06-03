/**
 * 
 * @author Jipesh
 */
package bomberman.content;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

import bomberman.content.Bomb.ExplosionFlame;
import bomberman.gui.GameGraphics;
import game.engine2D.AbstractGame;
import game.engine2D.BoundingBox;
import game.engine2D.Entity;
import game.engine2D.Screen;

public class Game extends AbstractGame {
	private final int[][] BATTLE_FIELD = new int[17][11];
	private final HashMap<String, Entity> entiteys;
	private final List<Player> players = new ArrayList<>();
	private final List<PowerUp> specials = new ArrayList<>();
	private final List<Wall> walls = new ArrayList<>();
	private final List<Obstacle> obstacles = new ArrayList<>();
	private final List<Bomb> bombs = new ArrayList<>();
	private BufferedImage sprite_sheet;
	private boolean gameover = false;
	private Player player1, player2;
	private Screen gui;

	public Game() {
		super("BomberMan", 766, 620, false);
		try {
			sprite_sheet = ImageIO.read(getClass().getResource("/bomberman/resources/sprite_sheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.entiteys = new HashMap<>();
		init();
	}

	@Override
	/**
	 * initiate method which set up the battlefield with the brick
	 * wall(Obstacle) and walls, Battle field array represent the map in terms
	 * of each block. whereby the battlefield is 17 * 11 (width * height)
	 */
	public void init() {
		int index = 0;
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 17; j++) {
				if (j % 2 == 1 && i % 2 == 1 && (i != 0 || i != 16)) {
					BATTLE_FIELD[j][i] = 1;
					Wall wall = new Wall(((j + 1) * 40), ((i + 1) * 40), this);
					walls.add(wall);

					/*
					 * j+1 and i+1 because it should not be placed at the border
					 */

				} else if (!((i <= 1 || i >= 9) && (j <= 1 || j >= 15))) {
					Random rnd = new Random();
					int x = rnd.nextInt(3);
					if (x == 1 || x == 2) { // fill up as many spaces as
											// possible
						/*BATTLE_FIELD[j][i] = 2;
						Obstacle obs = new Obstacle(index++, ((j + 1) * 40), ((i + 1) * 40), this);
						obstacles.add(obs);
						entiteys.put(j + "x" + i + "y", obs);*/
					}
				}
			}
		}

		// TO DO: Better method to add player
		addPlayers();
		gui = new GameGraphics(this);
		addScreen(gui);
		setScreen(0);
		gameover = false;
		start(60);
	}

	private void addPlayers() {
		player1 = new Player(1, 40, 40, this); // for starting stage only
		addThread(new Thread(player1));
		players.add(player1);
		player2 = new Computer(2, (17 * 40), 40, this); // for starting stage only
		addThread(new Thread(player2));
		players.add(player2);

	}

	@Override
	public void gameLoop() {
		if (!gameover) {
			run();
			gui.repaint();
			if (players.size() == 1) {
				gameover(); //if there is only one player then game will be over
			}
			checkGameover();
		}
	}

	/**
	 * frees up the exact value in the array by setting it to zero which means
	 * empty space
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * 
	 */
	public void makeAvailable(int x, int y) {
		BATTLE_FIELD[x - 1][y - 1] = 0;
	}

	/**
	 * @return the walls
	 */
	public List<Wall> getWalls() {
		return walls;
	}

	/**
	 * @return the obstacles
	 */
	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	/**
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * @return the bombs
	 */
	public List<Bomb> getBombs() {
		return bombs;
	}

	/**
	 * @return the power-ups
	 */
	public List<PowerUp> getSpecials() {
		return specials;
	}

	/**
	 * returns the exact sprite image using the x position and the multiply by
	 * block size which is 40 to locate the image x and y coordinate on the
	 * sprite sheet
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @return the exact sprite from the sprite sheet
	 */
	public Image getSprite(int x, int y, int width, int height) {
		return sprite_sheet.getSubimage((x * 40), (y * 40), width, height);
	}
	
	protected Bomb bombCollision(Entity e){
		for(Bomb bomb : getBombs()){
			if(e.getBoundingBox().checkCollision(bomb.getBoundingBox())){
				return bomb;
			}
		}
		return null;
		
	}

	/**
	 * the method checks if the player is on top of the bomb and around the center 
	 */
	protected void updateWalkable() {
		if (!bombs.isEmpty()) {
			for (Bomb bomb : getBombs()) {
				for (Player player : getPlayers()) {
					if (bomb.getBoundingBox().checkCollision(player.getBoundingBox())){
						BoundingBox box = new BoundingBox(bomb.getX() + 4,bomb.getY()+4,bomb.getWidth()-4,bomb.getHeight()-4);			
						if(player.getBoundingBox().checkCollision(box)){
						player.addWalkable(bomb);
						}
					}
				}
			}
		}
	}

	/**
	 * returns the exact sprite image using the x position and the multiply by
	 * block size which is 40 to locate the image x and y coordinate on the
	 * sprite sheet
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @return the exact sprite from the sprite sheet
	 */
	Image getSprite(int x, int y) {
		return sprite_sheet.getSubimage((x * 40), (y * 40), 40, 40);
	}

	/**
	 * checks to make sure the position is valid if so adds the bomb to the list
	 * and updates map
	 * 
	 * @param bomb
	 *            the bomb to add to the list
	 */
	void addBomb(Bomb bomb) {
		if (checkAvailability((bomb.getX() / 40), (bomb.getY() / 40))) {
			bombs.add(bomb);
			BATTLE_FIELD[(bomb.getX() / 40) - 1][(bomb.getY() / 40) - 1] = 3;
		}
	}

	public void addSpecials(PowerUp power) {
		specials.add(power);
		System.out.println((power.getX()/40) + "\t" + (power.getY()/40) + "\tpower");
		BATTLE_FIELD[(power.getX()/40) - 1][(power.getY()/40) - 1] = 4;
		System.out.println(BATTLE_FIELD[(power.getX()/40) - 1][(power.getY()/40) - 1]);
	}
	
	/**
	 * checks if the explosions have touched any of the player
	 */
	synchronized void checkGameover() {
		for (Bomb bomb : getBombs()) {
			if (bomb.getDetonated()) {
				for (ExplosionFlame exp : bomb.getExplostions()) {
					bomb.playerHit(exp.getBoundingBox().getX(), exp.getBoundingBox().getY());
				}
			}
		}
	}

	/**
	 * A method to check if the x and y position are available on the map
	 * 
	 * @param x
	 *            the x value on first array
	 * @param y
	 *            the y value on second array
	 * @return if the area is empty space
	 */
	boolean checkAvailability(int x, int y) {
		if (x - 1 < 0 || y - 1 < 0 || x - 1 > 16 || y - 1 > 10) {
			return false;
		} else if (BATTLE_FIELD[x - 1][y - 1] == 0) {
			return true;
		}
		return false;
	}

	/**
	 * checks to see it's a valid value then return value which exist within the
	 * map at that x and y position
	 * 
	 * @param x
	 *            the x position on the map
	 * @param y
	 *            the y position on the map
	 * @return the value with that x and y on the map
	 */
	int checkMap(int x, int y) {
		if (x - 1 < 0 || y - 1 < 0 || x - 1 > 16 || y - 1 > 10) {
			return -1;
		}
		return BATTLE_FIELD[x - 1][y - 1];
	}

	/**
	 * The method checks if the entity is overlapping/colliding with any of the walls or obstacles
	 * 
	 * @param box the entity bounding box
	 * @return the entity it is colliding with
	 */
	Entity checkCollision(BoundingBox box) {
		for (Wall wall : walls) {
			if (wall.getBoundingBox().checkCollision(box)) {
				return wall;
			}
		}
		for (Obstacle obs : obstacles) {
			if (obs.getBoundingBox().checkCollision(box)) {
				return obs;
			}
		}
		return null;
	}

	public Entity getEntity(String key) {
		return entiteys.get(key);
	}

	public Set<String> getKeys() {
		return entiteys.keySet();
	}

	public void removeEntity(int x, int y) {
		entiteys.remove(x + "x" + y + "y");
	}

	public void removeObstacle(int index) {
		obstacles.remove(index);
	}

	public void gameover() {
		gameover = true;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	public boolean gameOver() {
		return gameover;
	}

}
