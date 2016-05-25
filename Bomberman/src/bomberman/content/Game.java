package bomberman.content;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import bomberman.gui.GameGraphics;

public class Game extends JFrame {
	private final int[][] BATTLE_FIELD = new int[17][11];
	private JPanel gui;
	private BufferedImage sprite_sheet;
	private List<Wall> walls = new ArrayList<>();
	private List<Obstacle> obstacles = new ArrayList<>();
	private List<Player> players = new ArrayList<>();
	private List<Bomb> bombs = new ArrayList<>();
	private List<PowerUp> specials = new ArrayList<>();
	private List<Thread> users = new ArrayList<>();

	public Game() {
		super("BomberMan");
		try {
			sprite_sheet = ImageIO.read(getClass().getResource("/bomberman/resources/spirites.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}

	/**
	 * initiate method which set up the battlefield with the brick
	 * wall(Obstacle) and walls, Battle field array represent the map in terms
	 * of each block. whereby the battlefield is 17 * 11 (width * height)
	 */
	private void init() {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 17; j++) {
				if (j % 2 == 1 && i % 2 == 1 && (i != 0 || i != 16)) {
					BATTLE_FIELD[j][i] = 1;
					walls.add(new Wall(((j + 1) * 40), ((i + 1) * 40), this));

					/*
					 * j+1 and i+1 because it should not be placed at the border
					 */

				} else if (!((i <= 1 || i >= 9) && (j <= 1 || j >= 15))) {
					Random rnd = new Random();
					int x = rnd.nextInt(3);
					if (x == 1 || x == 2) { // fill up as many spaces as
											// possible
						BATTLE_FIELD[j][i] = 2;
						obstacles.add(new Obstacle(((j + 1) * 40), ((i + 1) * 40), this));
					}
				}
			}
		}

		// TO DO: Better method to add player
		Player temp = new Player(1, 40, 40, this); // for starting stage only
		users.add(new Thread(temp));
		players.add(temp);
		addKeyListener(temp);
		gui = new GameGraphics(this);
		add(gui);
		setVisible(true);
		setSize((19 * 40), (13 * 40 + 10));
		setResizable(false);

		/*
		 * fps method which makes the refresh rate constant for all types of PC
		 * fast or slow
		 */
		int fps = 60;
		double timePerTick = 1e9 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		while (true) { // the main loop

			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			if (delta >= 1) {
				looper();
				delta--;
			}
		}
	}

	private void looper() {
		players.get(0).play();
		gui.repaint();
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
	Image getSprite(int x, int y) {
		return sprite_sheet.getSubimage((x * 40), (y * 40), 40, 40);
	}

	/**
	 * 
	 * @return the border sprite
	 */
	public Image getBorder() {
		return getSprite(3, 0);
	}

	/**
	 * checks to make sure the position is valid if so adds the bomb to the list
	 * and updates map
	 * 
	 * @param bomb
	 *            the bomb to add to the list
	 */
	void addBomb(Bomb bomb) {
		if (checkAvailability(bomb.getX(),bomb.getY())) {
			bombs.add(bomb);
			BATTLE_FIELD[bomb.getX() - 1][bomb.getY() - 1] = 3;
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
		if(x - 1 < 0 || y - 1 < 0){
			return false;
		}
		else if (BATTLE_FIELD[x - 1][y - 1] == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * checks to see it's a valid value then return value which exist within the map
	 * at that x and y position
	 * 
	 * @param x the x position on the map
	 * @param y the y position on the map
	 * @return the value with that x and y on the map
	 */
	int checkMap(int x, int y){
		if(x - 1 < 0 || y - 1 < 0){
			return -1;
		}
		return BATTLE_FIELD[x-1][y-1];
	}
	
	// partial check
	boolean checkCollision(BoundingBox box) {
		for (Wall wall : walls) {
			if (wall.getBoundingBox().checkCollision(box)) {
				return true;
			}
		}
		for (Obstacle obs : obstacles) {
			if (obs.getBoundingBox().checkCollision(box)) {
				return true;
			}
		}
		return false;
	}

}
