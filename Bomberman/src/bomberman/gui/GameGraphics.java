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
	public void paint(Graphics g) throws ConcurrentModificationException{
		super.paint(g);
		Game game = (Game) getGame();

		g.drawImage(background, 0, 0, null);

		/*for (String key : game.getKeys()) {
			if (game.getEntity(key) instanceof Obstacle) {
				Obstacle obs = (Obstacle) game.getEntity(key);
				g.drawImage(obs.getImage(), obs.getX(), obs.getY(), null);
			}
		}
		*/
		for(Obstacle obs : game.getObstacles()){
			g.drawImage(obs.getImage(), obs.getX(), obs.getY(), null);
		}

		for (Player player : game.getPlayers()) {
			if (!(game.getBombs().isEmpty())) {
				Iterator<Bomb> bombs = game.getBombs().iterator();
				while (bombs.hasNext()) {
					// TODO : add explosion check here
					Bomb bomb = bombs.next();
					if (bomb.hasDetonated()) {
						g.drawImage(player.getImage(), player.getX(), player.getY(), null);
						g.drawImage(bomb.getImage(), (bomb.getX() * 40), (bomb.getY() * 40), null);
						for (ExplosionFlame exp : bomb.getExplostions()) {
							BoundingBox box = exp.getExplostionBox();
							g.drawImage(exp.getImage(), (box.getX() * 40), (box.getY() * 40), null);
						}
						if (bomb.delete()) {
							game.makeAvailable(bomb.getX(), bomb.getY());
							bombs.remove();
						}
					} else {
						g.drawImage(bomb.getImage(), (bomb.getX() * 40), (bomb.getY() * 40), null);
						g.drawImage(player.getImage(), player.getX(), player.getY(), null);
					}
				}
			} else {
				g.drawImage(player.getImage(), player.getX(), player.getY(), null);
			}
		}
	}
}
