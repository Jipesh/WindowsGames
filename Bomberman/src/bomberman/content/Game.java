/**
 * 
 * @author Jipesh
 */
package bomberman.content;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import bomberman.content.Bomb.ExplosionFlame;
import bomberman.gui.GameGraphics;

import game.engine2D.Engine2DGame;
import game.engine2D.Engine2DRectangleBoundingBox;
import game.engine2D.Engine2DScreen;
import game.engine2D.Engine2DEntity;
import game.engine2D.Engine2DEntity.State;

public class Game extends Engine2DGame {
	private final int[][] BATTLE_FIELD = new int[17][11];
	private final List<Character> players = new ArrayList<>();
	private final List<PowerUp> specials = new ArrayList<>();
	private final List<Wall> walls = new ArrayList<>();
	private final List<Obstacle> obstacles = new ArrayList<>();
	private final List<Bomb> bombs = new ArrayList<>();
	private final List<Engine2DEntity> removelist = new ArrayList<>();
	private final HashMap<String, Engine2DEntity> entityList = new HashMap<>();
	private BufferedImage sprite_sheet;
	private boolean gameover = false;
	private Character player1, player2;
	private Engine2DScreen gui;
	private int x, y;

	public Game() {
		super("BomberMan", 766, 620, false);
		try {
			sprite_sheet = ImageIO.read(getClass().getResource("/bomberman/resources/sprite_sheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		init();
	}

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
					Wall wall = new Wall(((j + 1) * 40), ((i + 1) * 40));
					walls.add(wall);
					entityList.put("xPos" + (j + 1) + "yPos" + (i + 1), wall);
					/*
					 * j+1 and i+1 because it should not be placed at the border
					 */

				} else if (!((i <= 1 || i >= 9) && (j <= 1 || j >= 15))) {
					Random rnd = new Random();
					int x = rnd.nextInt(3);
					if (x == 1 || x == 2) { // fill up as many spaces as
											// possible
						BATTLE_FIELD[j][i] = 2;
						Obstacle obs = new Obstacle(index++, ((j + 1) * 40), ((i + 1) * 40), this);
						obstacles.add(obs);
						entityList.put("xPos" + (j + 1) + "yPos" + (i + 1), obs);
					}
				}
			}
		}
		addPlayers();
		gui = new GameGraphics(this);
		addScreen(gui);
		setScreen(0);
		start(60);
	}

	@Override
	public void gameLoop() {
		if (!removelist.isEmpty()) {
			updateLists();
		}
		checkGameover();
		if (players.size() == 1) {
			setGameOver(true);
		}
	}

	public void render() {
		gui.repaint();
	}

	/**
	 * A clone of the obstacles list
	 * 
	 * @return the obstacles
	 */
	public ArrayList<Obstacle> getObstacles_READONLY() {
		return (ArrayList<Obstacle>) ((ArrayList<Obstacle>) obstacles).clone();
	}

	/**
	 * A clone of the players list
	 * 
	 * @return the players
	 */
	public ArrayList<Character> getPlayers_READONLY() {
		return (ArrayList<Character>) ((ArrayList<Character>) players).clone();
	}

	/**
	 * A clone of the bombs list
	 * 
	 * @return the bombs
	 */
	public ArrayList<Bomb> getBombs_READONLY() {
		return (ArrayList<Bomb>) ((ArrayList<Bomb>) bombs).clone();
	}

	/**
	 * A clone of the power-ups list
	 * 
	 * @return the power-ups
	 */
	public ArrayList<PowerUp> getSpecials_READONLY() {
		return (ArrayList<PowerUp>) ((ArrayList<PowerUp>) specials).clone();
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
		int x = (int) (bomb.getX() / 40);
		int y = (int) (bomb.getY() / 40);
		if (checkMap(x, y) == 0) {
			bombs.add(bomb);
			bomb.start();
			BATTLE_FIELD[x - 1][y - 1] = 3;
			entityList.put("xPos" + x + "yPos" + y, bomb);
		}
	}

	public void addSpecials(PowerUp power) {
		specials.add(power);
		int x = ((int) power.getX() / 40);
		int y = ((int) power.getY() / 40);
		entityList.put("xPos" + x + "yPos" + y, power);
	}

	Engine2DEntity getEntity(int x, int y) {
		return entityList.get("xPos" + x + "yPos" + y);
	}

	void invokeDestroy(Engine2DEntity entity) {
		removelist.add(entity);
	}

	/**
	 * checks if the explosions have touched any of the player
	 */
	private void checkGameover() {
		ArrayList<Bomb> list = getBombs_READONLY();
		for (Bomb bomb : list) {
			if (bomb.getDetonated()) {
				for (ExplosionFlame exp : bomb.getExplostions_READONLY()) {
					Character player = bomb.playerHit(exp.getBoundingBox());
					players.remove(player);
				}
			}
		}
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
	 * The method checks if the entity is overlapping/colliding with any of the
	 * walls or obstacles
	 * 
	 * @param box
	 *            the entity bounding box
	 * @return the entity it is colliding with
	 */
	Engine2DEntity checkCollision(Engine2DRectangleBoundingBox box) {
		for (Wall wall : walls) {
			if ((wall.getBoundingBox()).checkCollision(box)) {
				return wall;
			}
		}
		if (!obstacles.isEmpty()) {
			ArrayList<Obstacle> list = getObstacles_READONLY();
			for (Obstacle obs : list) {
				if (obs != null && obs.getState() == State.ALIVE)
					if ((obs.getBoundingBox()).checkCollision(box)) {
						return obs;
					}
			}
		}
		if (!bombs.isEmpty()) {
			Iterator<Bomb> list = getBombs_READONLY().iterator();
			while (list.hasNext()) {
				Bomb bomb = list.next();
				if (bomb != null && bomb.getState() == State.ALIVE) {
					if (bomb.getBoundingBox().checkCollision(box)) {
						return bomb;
					}
				}
			}
		}
		return null;
	}

	public void removeObstacle(int index) {
		obstacles.remove(index);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	public void gameOver() {
	}

	private HashMap<String, Engine2DEntity> getentityList_READONLY() {
		return (HashMap<String, Engine2DEntity>) entityList.clone();
	}

	private ArrayList<Engine2DEntity> getRemoveList_READONLY() {
		return (ArrayList<Engine2DEntity>) ((ArrayList<Engine2DEntity>) removelist).clone();
	}

	private void addPlayers() {
		player1 = new Player(1, 42, 42); // for starting stage only
		addThread(player1.getThread());
		players.add(player1);
		player2 = new Player(2, (17 * 40) + 2, 42); // for starting stage
													// only
		addThread(player2.getThread());
		players.add(player2);

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
	private void makeAvailable(int x, int y) {
		BATTLE_FIELD[x - 1][y - 1] = 0;
	}

	/**
	 * The method removes the entity from the hash-map then makes the position
	 * on the map available
	 * 
	 * @param x the x pos on the map
	 * @param y the y pos on the map
	 */
	private void removeEntity(float x, float y) {
		int _x = (int) x;
		int _y = (int) y;
		entityList.remove("xPos" + _x + "yPos" + _y);
		makeAvailable(_x, _y);
	}

	/**
	 * The method removes the entity in the remove list and calls invoke delete on
	 * any bombs who's ate is "DEAD"
	 */
	private void updateLists() {
		if (!removelist.isEmpty()) {
			for (Engine2DEntity entity : getRemoveList_READONLY()) {
				removeFromList(entity);
			}
		}
	}

	/**
	 * Finds out which Class the entity belongs too, and removes it from the corresponding list
	 * 
	 * @param entity to be removed
	 */
	private void removeFromList(Engine2DEntity entity) {
		if (entity instanceof Bomb) {
			bombs.remove(entity);
			removeEntity((int) entity.getX() / 40, (int) entity.getY() / 40);
			Bomb bomb = (Bomb) entity;
			bomb.finish();
		} else if (entity instanceof PowerUp) {
			specials.remove(entity);
			removeEntity((int) entity.getX() / 40, (int) entity.getY() / 40);
		} else if (entity instanceof Obstacle) {
			obstacles.remove(entity);
			removeEntity((int) entity.getX() / 40, (int) entity.getY() / 40);
			int x = ((int) entity.getX() / 40);
			int y = ((int) entity.getY() / 40);
			BATTLE_FIELD[x - 1][y - 1] = 4;
		}
		removelist.remove(entity);
	}

}
