package bomberman.content;

import game.engine2D.Engine2DEntity;
import game.engine2D.Engine2DRectangleBoundingBox;

public class GameObject extends Engine2DEntity {

	public Game getGame(){
		return(Game)super.getGame();
	}
	
	public Engine2DRectangleBoundingBox getBoundingBox(){
		return (Engine2DRectangleBoundingBox)super.getBoundingBox();
	}
	
	public void destroy(){
		super.destroy();
		getGame().invokeDestroy(this);
	}
}
