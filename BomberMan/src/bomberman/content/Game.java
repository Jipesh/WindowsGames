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
	private BufferedImage spirite_sheet;
	private List<Wall> walls = new ArrayList<>();
	private List<Obstacle> obstacles = new ArrayList<>();
	private List<Player> players = new ArrayList<>();
	private List<Bomb> bombs;
	private Thread player;
	private JPanel gui;
	public Game() {
		super("BomberMan");
		try {
			spirite_sheet = ImageIO.read(getClass().getResource("/bomberman/resources/spirites.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}

	private void init() {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 17; j++) {
				if (j % 2 == 1 && i % 2 == 1 && (i != 0 || i != 16)) {
					BATTLE_FIELD[i][j] = 1;
					walls.add(new Wall(j+1,i+1,this));
				} else if (!((i <= 1 || i >= 9) && (j <= 1 || j >= 15))) {
					Random rnd = new Random();
					int x = rnd.nextInt(3);
					if (x == 1 || x == 2) {
						BATTLE_FIELD[i][j] = 2;
						obstacles.add(new Obstacle(0,j+1,i+1, this));
					}
				}
			}
		}
		players.add(new Player(40,40,this));
		player = new Thread(players.get(0));
		addKeyListener(players.get(0));
		gui = new GameGraphics(this);
		add(gui);
		setSize((19*40),(14*40));
		setResizable(false);
		setVisible(true);
		int fps = 60;
		double timePerTick = 1e9 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		while(true){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			if (delta >= 1) {
				looper();
				delta--;
			}
		}
	}

	public void looper() {
		player.run();
		gui.repaint();
	}
	
	void addBomb(int x, int y, int dur, int size){
		bombs.add(new Bomb(x,y,dur,size));
	}
	
	public List<Wall> getWalls(){
		return walls;
	}
	
	public List<Player> getActivePlayers(){
		return players;
	}
	
	public List<Obstacle> getObjects(){
		return obstacles;
	}
	
	public Image getSpirite(int x, int y){
		return spirite_sheet.getSubimage((x*40),(y*40),40,40);
	}

}
