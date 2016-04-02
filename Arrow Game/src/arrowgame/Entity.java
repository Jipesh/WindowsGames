/**
 * 
 * @author Jipesh
 */
package arrowgame;

public abstract class Entity {
	
	private final Game game;
	private BoundingBox box;

	public Entity(Game g) {
		this.game = g;
	}

	/**
	 * @return the game object
	 */
	
	public final void setBoundingBox(final BoundingBox box){
		this.box = box; 
	}
	
	public Game getGame() {
		return game;
	}
	
	/**
	 * @return the box
	 */
	public BoundingBox getBox() {
		return box;
	}
}
