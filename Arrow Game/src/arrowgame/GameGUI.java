/**
 * 
 * @author Jipesh
 */
package arrowgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import game.engine2D.Engine2DScreen;

public class GameGUI extends Engine2DScreen implements ActionListener{
	private final Game GM;
	private final JButton reset;
	private Image rocket;

	public GameGUI(Game g) {
		super(g);
		this.setLayout(null);
		GM = g;
		reset = new JButton("Retry");
		reset.addActionListener(this);
		reset.setBounds(150, 250, 100, 40);
		this.add(reset);
		reset.setFocusable(false);
		try {
			rocket = ImageIO.read(getClass().getResource("images/rocket.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		reset.setVisible(false);

		g.drawImage(rocket, GM.getPlayer().getBoundingBox().getX(), GM.getPlayer().getBoundingBox().getY(), null);

		/*
		 * checks which range the score falls in and set's the colour of the
		 * square according to that
		 */
		for (int x : GM.getKeys()) { // uses the keys from Game
			int s = GM.getScore();
			if (s <= 50) {
				g.setColor(Color.green);
			} else if (s > 50 && s <= 100) {
				g.setColor(Color.blue);
			} else if (s > 100) {
				g.setColor(Color.ORANGE);
			}

			g.fillRect(GM.getSqrs(x).getBoundingBox().getX(), GM.getSqrs(x).getBoundingBox().getY(), GM.getSqrs(x).getBoundingBox().getWidth(),
					GM.getSqrs(x).getBoundingBox().getHeight());
			g.setColor(Color.BLACK);
			g.drawRect(GM.getSqrs(x).getBoundingBox().getX(), GM.getSqrs(x).getBoundingBox().getY(), GM.getSqrs(x).getBoundingBox().getWidth(),
					GM.getSqrs(x).getBoundingBox().getHeight());

			/*
			 * iterates through all squares and draws the square using their
			 * position as well as draw an outline of square around it to make
			 * it stand out
			 */
		}
		g.setColor(Color.red);

		g.setFont(new Font("Arial", Font.PLAIN, 20));

		g.drawString(Integer.toString(GM.getPlayer().getScore()), 20, 50);
		
		if (GM.getGameOver()) {
			reset.setVisible(true);
		}
	}

	public void actionPerformed(ActionEvent e) {
		GM.resetGame();
		reset.setVisible(false);
	}

}
