package jipesh.pingpong;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SettingScreen extends JPanel implements ActionListener {
	private final Game GM;
	private Image backdrop;
	private JButton option1, option2, option3;
	private Image platform1, platform2, ball;
	
	/**
	 * 
	 * @param g = the current game, is used to call the object
	 * The constructor also set's up the position and status of the 3 buttons
	 * @see SettingScreen#paintComponent(Graphics)
	 */
	public SettingScreen(Game g) {
		this.setLayout(null);
		GM = g;
		option1 = new JButton("Computer vs Computer");
		option2 = new JButton("Player vs Computer");
		option3 = new JButton("Player vs Player");
		option1.setVisible(false);
		option2.setVisible(false);
		option3.setVisible(false);
		option1.setFocusable(false);
		option2.setFocusable(false);
		option3.setFocusable(false);
		option1.addActionListener(this);
		option2.addActionListener(this);
		option3.addActionListener(this);
		option1.setBounds(310, 330, 166, 50);
		option2.setBounds(310, 400, 166, 50);
		option3.setBounds(310, 470, 166, 50);
		this.add(option1);
		this.add(option2);
		this.add(option3);
		try {
			/*Link's the image to the private image fields*/
			backdrop = ImageIO.read(getClass().getResource("title.png"));
			ball = ImageIO.read(getClass().getResource("Ball.png"));
			platform1 = ImageIO.read(getClass().getResource("Default_Platform1.png"));
			platform2 = ImageIO.read(getClass().getResource("Default_Platform2.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		if (GM.isSetup()) {
		super.paintComponent(g);
			g.drawImage(backdrop, 0, 0, null);
			/*draws a rectangle around the button to make it stand out*/
			g.drawRect(309, 329, 167, 51);
			g.drawRect(309, 399, 167, 51);
			g.drawRect(309, 469, 167, 51);
			option1.setVisible(true);
			option2.setVisible(true);
			option3.setVisible(true);
			/*draws the individual animation object using the the current game and the exact index to return the object and use it's methods*/
			g.drawImage(ball, ((Ball)GM.getAnim(1)).getPosX(), ((Ball) GM.getAnim(1)).getPosY(), null);
			g.drawImage(ball, ((Ball)GM.getAnim(2)).getPosX(), ((Ball) GM.getAnim(2)).getPosY(), null);
			g.drawImage(platform1, ((Player)GM.getAnim(0)).getPosX(), ((Player) GM.getAnim(0)).getPosY(), null);
			g.drawImage(platform2, ((Player) GM.getAnim(3)).getPosX(), ((Player) GM.getAnim(3)).getPosY(), null);
		}
	}

	public void actionPerformed(ActionEvent e) {
		/**
		 * uses a series of if statement to see which button was clicked and then set's all the button invisible
		 */
		if (e.getSource() == option1) {
			GM.option1();
		} else if (e.getSource() == option2) {
			GM.option2();
		} else if (e.getSource() == option3) {
			GM.option3();
		}
		option1.setVisible(false);
		option2.setVisible(false);
		option3.setVisible(false);

	}
}
