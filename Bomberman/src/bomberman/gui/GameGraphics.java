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
import game.engine2D.Engine2DRectangleBoundingBox;
import game.engine2D.Engine2DScreen;
import bomberman.content.Game;
import bomberman.content.Obstacle;
import bomberman.content.PowerUp;

public class GameGraphics extends Engine2DScreen {
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
		restart.setBounds(getGame().getWindow().getWidth()/2, getGame().getWindow().getHeight()/2, 60, 40);
		restart.setFocusable(false);
		restart.setVisible(false);
		this.add(restart);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Game game = (Game) getGame();
		g.drawImage(background, 0, 0, null);
		if (game.getGameOver()) {
			restart.setVisible(true);
			g.setColor(Color.WHITE);
			if (game.getPlayers_READONLY().get(0).getCharacter() == 1) {
				g.drawString("Player 1 Wins", 700 / 2, 558 / 2);
			} else if (game.getPlayers_READONLY().get(0).getCharacter() == 2) {
				g.drawString("Player 2 Wins", 700 / 2, 558 / 2);
			}
		} else {
			Iterator<Obstacle> obstacles = game.getObstacles_READONLY().iterator();
			while (obstacles.hasNext()) {
				Obstacle obs = obstacles.next();
				g.drawImage(obs.getImage(), (int)obs.getX(), (int)obs.getY(), null);
			}
			int position = 0;
			for (Character player : game.getPlayers_READONLY()) {
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

				if (!(game.getBombs_READONLY().isEmpty())) {
					Iterator<Bomb> bombs = game.getBombs_READONLY().iterator();
					while (bombs.hasNext()) {
						// TODO : add explosion check here
						Bomb bomb = bombs.next();
						if (bomb.hasDetonated()) {
							g.drawImage(player.getImage(), (int)player.getX(), (int)player.getY(), null);
							g.drawImage(bomb.getImage(), (int)bomb.getX(), (int)bomb.getY(), null);
							for (ExplosionFlame exp : bomb.getExplostions_READONLY()) {
								Engine2DRectangleBoundingBox box = (Engine2DRectangleBoundingBox) exp.getBoundingBox();
								g.drawImage(exp.getImage(), (int)box.getX() - 4, (int)box.getY() - 4, null);
								
								/*
								 * player will be bellow the explosion
								 */
							}
						} else {
							g.drawImage(bomb.getImage(), (int)bomb.getX(), (int)bomb.getY(), null);
							g.drawImage(player.getImage(), (int)player.getX(), (int)player.getY(), null);

							/*
							 * player will be ontop of the box
							 */
						}
					}
				} else {
					g.drawImage(player.getImage(), (int)player.getX(), (int)player.getY(), null);

					// there may be no bombs plated
				}
			}
			for (PowerUp power : game.getSpecials_READONLY()) {
				g.drawImage(power.getImage(), (int)power.getX(), (int)power.getY(), null);
			}

		}
	}
}
