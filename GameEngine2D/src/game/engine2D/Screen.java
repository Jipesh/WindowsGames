package game.engine2D;

import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class Screen extends JPanel{
private AbstractGame game;
	
	public Screen(AbstractGame game) {
		this.game = game;
		this.setLayout(null);
	}
	
	public AbstractGame getGame(){
		return game;
	}

}
