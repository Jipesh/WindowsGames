/**
 * 
 * @author Jipesh
 */
package game.engine2D;

import javax.swing.JPanel;

public abstract class Engine2DScreen extends JPanel{
	
	private Engine2DGame game;
	
	public Engine2DScreen(Engine2DGame game) {
		this.game = game;
		this.setLayout(null);
	}
	
	public Engine2DGame getGame(){
		return game;
	}

}