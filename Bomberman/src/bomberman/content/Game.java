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
	private final int[][] BATTLE_FIELD = new int[11][17];
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
		};
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}

	private void init() {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 17; j++) {
				if (j % 2 == 1 && i % 2 == 1 && (i != 0 || i != 16)) {
					BATTLE_FIELD[i][j] = 1;
					walls.add(new Wall(((j+1)*40),((i+1)*40),this));
				} else if (!((i <= 1 || i >= 9) && (j <= 1 || j >= 15))) {
					Random rnd = new Random();
					int x = rnd.nextInt(3);
					if (x == 1 || x == 2) {
						BATTLE_FIELD[i][j] = 2;
						obstacles.add(new Obstacle(((j+1)*40),((i+1)*40), this));
					}
				}
			}
		}
		//TO DO: Better method to add player
		Player temp = new Player(1,40,40,this); //for starting stage only
		users.add(new Thread(temp));
		players.add(temp);
		addKeyListener(temp);
		gui = new GameGraphics(this);
		add(gui);
		setVisible(true);
		setSize((19*40),(13*40 +10));
		setResizable(false);
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
	 * @return the BATTLE_FIELD
	 */
	public int[][] getBATTLE_FIELD() {
		return BATTLE_FIELD;
	}

	/**
	 * @return the spirite_sheet
	 */
	public BufferedImage getSpirite_sheet() {
		return sprite_sheet;
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
	 * @return the specials
	 */
	public List<PowerUp> getSpecials() {
		return specials;
	}
	
	Image getSprite(int x, int y){
		return sprite_sheet.getSubimage((x*40), (y*40), 40, 40);
	}
	
	public Image getBorder(){
		return getSprite(3,0);
	}
	
	//partial check
		boolean checkCollision(BoundingBox box){
			for(Wall wall : walls){
				if(wall.getBoundingBox().checkCollision(box)){
					return true;
				}
			}
			for(Obstacle obs : obstacles){
				if(obs.getBoundingBox().checkCollision(box)){
					return true;
				}
			}
			return false;
		}

}
