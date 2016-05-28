package bomberman.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import bomberman.content.Bomb;
import bomberman.content.Bomb.ExplosionFlame;
import game.engine2D.BoundingBox;
import bomberman.content.Game;
import bomberman.content.Obstacle;
import bomberman.content.Player;
import bomberman.content.PowerUp;
import bomberman.content.Wall;
import game.engine2D.Screen;

public class GameGraphics extends Screen {
	private Image background;

	public GameGraphics(Game game) {
		super(game);
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/bomberman/resources/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Game game = (Game) getGame();

		if (game.gameOver()) {
			if (game.getPlayers().get(0).getCharacter() == 1) {
				g.drawString("Player 1 Wins", 775 / 2, 558 / 2);
			} else if (game.getPlayers().get(0).getCharacter() == 2) {
				g.drawString("Player 2 Wins", 775 / 2, 558 / 2);
			}

		} else {
			g.drawImage(background, 0, 0, null);
			Iterator<Obstacle> obstacles = game.getObstacles().iterator();
			while (obstacles.hasNext()) {
				Obstacle obs = obstacles.next();
				g.drawImage(obs.getImage(), obs.getX(), obs.getY(), null);
			}
			int position = 0;
			for (Player player : game.getPlayers()) {
				g.setColor(Color.DARK_GRAY);
				g.fillRect((position * 40), 13 * 40, 120, 120);
				int id = player.getCharacter();
				g.setColor(Color.WHITE);
				g.drawString("Player " + id + " speed : " + player.getSpeed(), (position * 40), 13 * 40 + 10);
				g.drawString("Player " + id + " bombs : " + player.getBombs(), (position * 40), 13 * 40 + 20);
				g.drawString("Player " + id + " flame : " + player.getExplosion_size(), (position * 40), 13 * 40 + 30);
				position += 4;

				if (!(game.getBombs().isEmpty())) {
					Iterator<Bomb> bombs = game.getBombs().iterator();
					while (bombs.hasNext()) {
						// TODO : add explosion check here
						Bomb bomb = bombs.next();
						if (bomb.hasDetonated()) {
							g.drawImage(player.getImage(), player.getX(), player.getY() - 6, null);
							g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), null);
							for (ExplosionFlame exp : bomb.getExplostions()) {
								BoundingBox box = exp.getExplostionBox();
								g.drawImage(exp.getImage(), box.getX(), box.getY(), null);
							}
							if (bomb.delete()) {
								game.makeAvailable(bomb.getX() / 40, bomb.getY() / 40);
								bombs.remove();
								bomb.updatePlayer();
							}
						} else {
							g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), null);
							g.drawImage(player.getImage(), player.getX(), player.getY() - 6, null);
						}
					}
				} else {
					g.drawImage(player.getImage(), player.getX(), player.getY() - 6, null);
				}
			}
			for (PowerUp power : game.getSpecials()) {
				g.drawImage(power.getImage(), power.getX(), power.getY(), null);
			}

		}
	}
}
