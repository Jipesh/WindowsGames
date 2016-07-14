package jipesh.pingpong;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameContent extends JPanel implements ActionListener{
	private final Game GM;
	private Image platform1, platform2, ball, board, score;
	private JButton menu;

	public GameContent(Game g) {
		GM = g;
		this.setLayout(null);
		menu = new JButton("Main Menu");
		menu.setBounds(310, 300,110,50);
		menu.setVisible(false);
		menu.setFocusable(false);
		menu.addActionListener(this);
		this.add(menu);
		try {
			ball = ImageIO.read(getClass().getResource("Ball.png"));
			platform1 = ImageIO.read(getClass().getResource("Default_Platform1.png"));
			platform2 = ImageIO.read(getClass().getResource("Default_Platform2.png"));
			score = ImageIO.read(getClass().getResource("Score.png"));
			board = ImageIO.read(getClass().getResource("Board.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* Links the field's to image on currant directory */
	}

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		((Graphics2D) g).setStroke(new BasicStroke(5)); // makes the line
														// thick

		if (GM.getLight() == true) {
			this.setBackground(Color.black);
		}

		if (GM.getDark() == false) {
			g.drawImage(board, 0, 0, null);
		}

		g.drawLine(371, 0, 371, 600); // draws a centre line

		g.drawImage(score, 275, 30, null); // draws at the score board

		int fontSize = 50; // so it fit's in the score board better

		g.setFont(new Font("Arial", Font.PLAIN, fontSize));

		g.setColor(new Color(151, 131, 131));

		/**
		 * GM.getObj(0) = P1 GM.getObj(1) = P2 GM.getObj(2) = Ball
		 */

		g.drawString(((Player) GM.getObj(0)).getString(), 300, 85); // Player1 score
		g.drawString(((Player) GM.getObj(1)).getString(), 390, 85); // Player2 score
		

		/* casting is necessary since initial method returns objects */

		g.drawImage(ball, ((Ball) GM.getObj(2)).getPosX(), ((Ball) GM.getObj(2)).getPosY(), null);
		g.drawImage(platform1, ((Player) GM.getObj(0)).getPosX(), ((Player) GM.getObj(0)).getPosY(), null);
		g.drawImage(platform2, ((Player) GM.getObj(1)).getPosX(), ((Player) GM.getObj(1)).getPosY(), null);
		
		if (GM.isRunning()) { //check's that game is still running
			
			menu.setVisible(false);

		} else if(!GM.isSetup()){
			int fontSizePause = 60; // HUGE SIZE FOR PAUSE

			g.setFont(new Font("Arial", Font.PLAIN, fontSizePause));
			
			if(GM.getLight()){
				g.setColor(Color.black);
			}
			else{
				g.setColor(new Color(230,132,005));
			}
			
			g.drawString("PAUSED", 255, 290);
			menu.setVisible(true);
		}

	}
	
	/**
	 * The option is called when the button is pressed and bring's the game back to the setting screen in
	 * the process makes running false and Setup true
	 * @see Game#beginSetup()
	 */
	public void actionPerformed(ActionEvent e) {
		GM.beginSetup();
	}
}
