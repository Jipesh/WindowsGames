package game.engine2D;

import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public abstract class Screen extends JPanel{
private AbstractGame game;
	
	public Screen(AbstractGame game) {
		this.game = game;
	}
	
	public void setLayout(LayoutManager manager){
		this.setLayout(manager);
	}
	
	public AbstractGame getGame(){
		return game;
	}
}
