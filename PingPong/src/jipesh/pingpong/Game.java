package jipesh.pingpong;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game implements KeyListener {
	private final static String MAIN = "inGame";
	private final static String OF_GAME = "setting";
	private final CardLayout LAYOUT = new CardLayout();
	private static int OPTION;
	private JPanel setting;
	private JPanel inGame;
	private JPanel app = new JPanel(LAYOUT); // the main panel which will store
												// the sub panel and use
												// CardLayout
	private Player p1;
	private Player p2;
	private Ball b;
	private JFrame frame;
	private boolean Setup = false;
	private boolean running = false;
	private boolean darkTheme = false;
	private boolean lightTheme = true;
	private Object obj[] = new Object[3];
	private Object[] anim = new Object[4];
	private Timer gameTimer;

	public Game() {
		gameTimer = new Timer();
		setGame();
	}

	/**
	 * This creates a new JFrame and add's the relevant panels into the frame,
	 * and set's the size
	 */
	private void setGame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Test");
		frame.setSize(800, 600);
		frame.setResizable(false);
		inGame = new GameContent(this);
		setting = new SettingScreen(this);
		app.add(setting, OF_GAME);
		app.add(inGame, MAIN);
		frame.add(app);
		frame.addKeyListener(this);
		frame.setVisible(true);
		beginSetup();
	}

	/**
	 * First option a user can choose "Computer vs Computer" It creates the
	 * individual object and assign them to the array obj using the index as
	 * follows
	 * <p>
	 * <li>obj[0] = Left Player
	 * <li>obj[1] = Right player
	 * <li>obj[2] = Ball</li>
	 */
	public void option1() {
		Setup = false;
		OPTION = 1;
		p1 = new Computer(this);
		p2 = new Computer(this);
		b = new Ball(350, 250, p1, p2);
		((Computer)p1).start(18, 300);
		p2.start(760, 300);
		obj[0] = p1;
		obj[1] = p2;
		obj[2] = b;
		((Computer) p1).setup();
		((Computer) p2).setup();
		running = true;
		run();
	}

	/**
	 * First option a user can choose "Player vs Computer" It creates the
	 * individual object and assign them to the array obj using the index as
	 * follows
	 * <p>
	 * <li>obj[0] = Left Player
	 * <li>obj[1] = Right player
	 * <li>obj[2] = Ball</li>
	 */
	public void option2() {
		Setup = false;
		OPTION = 2;
		p1 = new Player(this);
		p2 = new Computer(this);
		b = new Ball(350, 250, p1, p2);
		p1.start(18, 300);
		((Computer)p2).start(760, 300);
		obj[0] = p1;
		obj[1] = p2;
		obj[2] = b;
		((Computer) p2).setup();
		frame.addKeyListener(p1);
		running = true;
		run();
	}

	/**
	 * First option a user can choose "Player vs Player" It creates the
	 * individual object and assign them to the array obj using the index as
	 * follows
	 * <p>
	 * <li>obj[0] = Left Player
	 * <li>obj[1] = Right player
	 * <li>obj[2] = Ball</li>
	 */
	public void option3() {
		Setup = false;
		OPTION = 3;
		p1 = new Player(this);
		p2 = new Player(this);
		b = new Ball(350, 250, p1, p2);
		((Player) p1).start(18, 300);
		((Player) p2).start(760, 300);
		obj[0] = p1;
		obj[1] = p2;
		obj[2] = b;
		frame.addKeyListener(p1);
		frame.addKeyListener(p2);
		running = true;
		run();
	}

	/**
	 * Shows the Menu screen and run's the animation
	 */
	public void beginSetup() {
		Setup = true;
		Player leftP = new Computer(this);
		Player rightP = new Computer(this);
		Ball leftB = new Ball(250, 400, leftP, rightP);
		Ball rightB = new Ball(650, 400, leftP, rightP);
		leftP.start(18, 250);
		rightP.start(760, 250);
		((Computer) leftP).setup(leftB);
		((Computer) rightP).setup(rightB);
		anim[0] = leftP;
		anim[1] = leftB;
		anim[2] = rightB;
		anim[3] = rightP;
		LAYOUT.show(app, OF_GAME);
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				((Computer) leftP).runAnimation();
				((Computer) rightP).runAnimation();
				leftB.runAnimation();
				rightB.runAnimation();
				setting.repaint();
				if (!isSetup()) {
					timer.cancel();
				}
			}
		}, 0, (long) (1000f / 60f));

	}

	public boolean isSetup() {
		return Setup; // Currently not used
	}

	/**
	 * The following method uses a switch statement to determine which loop to
	 * use this is decided via the user option
	 * 
	 * @see Game#option1()
	 * @see Game#option2()
	 * @see Game#option3()
	 * @see Computer#move()
	 * @see Player1#play()
	 */
	private void gameStart() {

		switch (OPTION) {

		case 1:
			if (running) {
				((Computer) p1).move();
				((Computer) p2).move();
				((Ball) b).start();
				inGame.repaint();
			}
			break;
		case 2:
			if (running) {
				p1.play(1);
				((Computer) p2).move();
				((Ball) b).start();
				inGame.repaint(); // repaint's new position
			}
			break;
		case 3:
			if (running) {
				p1.play(1);
				p2.play(2);
				b.start();
				inGame.repaint(); // repaint's new position
			}
			break;
		}
	}

	/**
	 * This function is called at the end of each option
	 * 
	 * @see Game#option1() - Computer vs Computer
	 * @see Game#option2() - Player vs Computer
	 * @see Game#option3() - Player vs Player
	 */
	private void run() {

		LAYOUT.show(app, MAIN); /* shows the inGame panel */

		startInGameLoop();
	}
	
	public void startInGameLoop(){
		gameTimer = new Timer();
		gameTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				gameStart();
			}
		}, 0, (long) (1000f / 60f));
	}

	/**
	 * @param i
	 *            = the index used to get the exact object
	 * @return return's the object using the index this can be of type player or
	 *         ball
	 * @see GameContent#paintComponent(java.awt.Graphics)
	 */
	public Object getObj(int i) {
		return obj[i];
	}

	/**
	 * @param i
	 *            = the index used to get the exact object
	 * @return return's the object using the index this can be of type player or
	 *         ball
	 * @see Setting#paintComponent(java.awt.Graphics)
	 */
	public Object getAnim(int i) {
		return anim[i];
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_P) {

			running = !running; // inverts running
			if (running) {
				startInGameLoop();
			}else{
				inGame.repaint();
				gameTimer.cancel();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			darkTheme = true;
			lightTheme = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_L) {
			lightTheme = true;
			darkTheme = false;
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
	
	/**
	 * @return Running - If the game has started
	 */
	public boolean isRunning(){
		return running;
	}

	public boolean getDark() {
		return darkTheme;
	}

	public boolean getLight() {
		return lightTheme;
	}
}
