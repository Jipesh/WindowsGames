package bomberman.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import bomberman.content.Game;
import bomberman.content.Obstacle;
import bomberman.content.Player;
import bomberman.content.Wall;

public class GameGraphics extends JPanel {
	private final Game game;

	public GameGraphics(Game game) {
		this.game = game;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Image border = game.getSpirite(3, 0);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, (40*19), (40*13));
		for(int i = 0 ; i < 19 ; i++){
			g.drawImage(border, (i*40), 0, null);
			g.drawImage(border, (i*40), (40*12), null);
		}
		for(int i = 1 ; i < 13 ; i++){
			g.drawImage(border, 0, (i*40), null);
			g.drawImage(border, (18*40), (i*40), null);
		}
		for (Wall wall : game.getWalls()) {
			g.drawImage(wall.getImage(), (wall.getX() * 40), (wall.getY() * 40), null);
		}
		for (Obstacle obs : game.getObjects()) {
			g.drawImage(obs.getImage(), (obs.getX() * 40), (obs.getY() * 40), null);
		}
		for (Player active : game.getActivePlayers()) {
			g.drawImage(active.getImage(), (active.getX()), (active.getY()), null);
		}
	}

}
