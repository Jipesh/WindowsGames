package bomberman.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import bomberman.content.Bomb;
import bomberman.content.Game;
import bomberman.content.Obstacle;
import bomberman.content.Player;
import bomberman.content.PowerUp;
import bomberman.content.Wall;

public class GameGraphics extends JPanel {
	private final Game game;

	public GameGraphics(Game game) {
		this.game = game;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, (19 * 40), (13 * 40));
		
		for(int i = 0 ; i < 19 ; i++){
			g.drawImage(game.getBorder(), (i*40), 0, null);
			g.drawImage(game.getBorder(), (i*40), (12*40), null);
		}
		
		for(int i = 1 ; i < 13 ; i++){
			g.drawImage(game.getBorder(), 0, (i*40), null);
			g.drawImage(game.getBorder(), (18*40), (i*40), null);
		}
		
		for (Wall wall : game.getWalls()) {
			g.drawImage(wall.getImage(), wall.getX(), wall.getY(), null);
		}
		for (Obstacle obs : game.getObstacles()) {
				g.drawImage(obs.getImage(), obs.getX(), obs.getY(), null);
		}
		if (!(game.getBombs().isEmpty())) {
			for (Bomb bomb : game.getBombs()) {
				// TODO : add explosion check here
				g.drawImage(bomb.getImage(), (bomb.getX() * 40), (bomb.getY() * 40), null);
			}
		}
		if (!(game.getSpecials().isEmpty())) {
			for (PowerUp power : game.getSpecials()) {
				g.drawImage(power.getImage(), (power.getX() * 40), (power.getY() * 40), null);
			}
		}
		for (Player player : game.getPlayers()) {
			g.drawImage(player.getImage(), player.getX(), player.getY(), null);
		}
	}
}
