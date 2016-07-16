/**
 * 
 * @author Jipesh
 */
package bomberman.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import bomberman.content.Bomb;
import bomberman.content.Bomb.ExplosionFlame;
import bomberman.content.Character;
import game.engine2D.Engine2DBoundingPolygon.Engine2DBoundingRectangle;
import bomberman.content.Game;
import bomberman.content.Obstacle;
import bomberman.content.PowerUp;
import game.engine2D.Screen;

public class GameGraphics extends Screen {
	private Image background;
	private JButton restart;

	public GameGraphics(Game game) {
		super(game);
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/bomberman/resources/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		restart = new JButton("Restart");
		restart.setBounds(250, 580, 60, 40);
		restart.setFocusable(false);
		restart.setVisible(false);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Game game = (Game) getGame();
		g.drawImage(background, 0, 0, null);
		if (game.getGameOver()) {
			restart.setVisible(true);
			g.setColor(Color.WHITE);
			if (game.getPlayers().get(0).getCharacter() == 1) {
				g.drawString("Player 1 Wins", 700 / 2, 558 / 2);
			} else if (game.getPlayers().get(0).getCharacter() == 2) {
				g.drawString("Player 2 Wins", 700 / 2, 558 / 2);
			}
		} else {
			Iterator<Obstacle> obstacles = game.getObstacles().iterator();
			while (obstacles.hasNext()) {
				Obstacle obs = obstacles.next();
				g.drawImage(obs.getImage(), obs.getX(), obs.getY(), null);
			}
			int position = 0;
			for (Character player : game.getPlayers()) {
				g.setColor(Color.DARK_GRAY);
				g.fillRect((position * 40), 13 * 40, 200, 120);
				int id = player.getCharacter();
				g.setColor(Color.WHITE);

				/*
				 * draws the player statistics
				 */
				g.drawString("Player " + id + " Speed           : " + player.getSpeed(), (position * 40), 13 * 40 + 20);
				g.drawString("Player " + id + " Bombs           : " + player.getBombs(), (position * 40), 13 * 40 + 40);
				g.drawString("Player " + id + " explostion size : " + player.getExplosion_size(), (position * 40),
						13 * 40 + 60);
				position += 6;

				if (!(game.getBombs().isEmpty())) {
					Iterator<Bomb> bombs = game.getBombs().iterator();
					while (bombs.hasNext()) {
						// TODO : add explosion check here
						Bomb bomb = bombs.next();
						if (bomb.hasDetonated()) {
							g.drawImage(player.getImage(), player.getX(), player.getY(), null);
							g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), null);
							for (ExplosionFlame exp : bomb.getExplostions()) {
								Engine2DBoundingRectangle box = exp.getBoundingBox();
								g.drawImage(exp.getImage(), box.getX(), box.getY(), null);

								/*
								 * player will be bellow the explosion
								 */
							}
							if (bomb.delete()) {
								bombs.remove();
								game.makeAvailable(bomb.getX() / 40, bomb.getY() / 40);
								bomb.updatePlayer();
							}
						} else {
							g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), null);
							g.drawImage(player.getImage(), player.getX(), player.getY(), null);

							/*
							 * player will be ontop of the box
							 */
						}
					}
				} else {
					g.drawImage(player.getImage(), player.getX(), player.getY(), null);

					// there may be no bombs plated
				}
			}
			for (PowerUp power : game.getSpecials()) {
				g.drawImage(power.getImage(), power.getX(), power.getY(), null);
			}

		}
	}
}
