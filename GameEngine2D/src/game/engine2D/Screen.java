package game.engine2D;

import javax.swing.JPanel;

public abstract class Screen extends JPanel{
	private Engine2DGame game;
	
	public Screen(Engine2DGame game) {
		this.game = game;
		this.setLayout(null);
	}
	
	public Engine2DGame getGame(){
		return game;
	}

}