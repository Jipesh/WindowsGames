package bomberman.content;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.engine2D.Engine2DEntity;
import game.engine2D.Engine2DRectangleBoundingBox;

public class Computer extends Character {
	private final Game game;
	private final Random rnd;
	private int targetX, targetY, ignore;
	private final Timer moveDecider = new Timer();

	public Computer(int character, int x, int y, Game game) {
	super(character, x, y);
	this.game = game;
	this.rnd = new Random();
	setTarget();
	moveDecider.scheduleAtFixedRate(new TimerTask(){
	  
		 @Override 
		 public void run() {
			 //plantBomb();
			 if(getX() == targetX && getY() == targetX){
				setTarget();
			 }
		 }
	  }, 0, 1000);
	 }

	private void setTarget() {
		int x = randomChecker(ignore, 4);
		switch (x) {

		case 1:
			Engine2DRectangleBoundingBox boxLeft = new Engine2DRectangleBoundingBox((int)(getX() - 40), (int)getY(),
					(int)getWidth(), (int)getHeight());
			if (game.checkMap((int)(boxLeft.getX()/40f), (int)(boxLeft.getY()/40f)) == 0 && x != ignore) {
				targetX = (int) (getX() - 40);
				targetY = (int) getY();
			} else {
				ignore = 1;
				setTarget();
			}
			break;
		case 2:
			Engine2DRectangleBoundingBox boxRight = new Engine2DRectangleBoundingBox((int)(getX() + 40), (int)getY(),
					(int)getWidth(), (int)getHeight());
			if (game.checkMap((int)(boxRight.getX()/40), (int)(boxRight.getY()/40)) == 0 && x != ignore) {
				targetX = (int) (getX() + 40);
				targetY = (int) getY();
			} else {
				ignore = 2;
				setTarget();
			}
			break;
		case 3:
			Engine2DRectangleBoundingBox boxUp = new Engine2DRectangleBoundingBox((int)getX(), (int)(getY() - 40),
					(int)getWidth(), (int)getHeight());
			if (game.checkMap((int)(boxUp.getX()/40), (int)(boxUp.getY()/40)) == 0 && x != ignore) {
				targetY = (int) (getY() - 40);
				targetX = (int) getX();
			} else {
				ignore = 3;
				setTarget();
			}
			break;
		case 4:
			Engine2DRectangleBoundingBox boxDown = new Engine2DRectangleBoundingBox((int)getX(), (int)getY() + 40,
					(int)getWidth(), (int)getHeight());
			if (game.checkMap((int)boxDown.getX() / 40, (int)boxDown.getY() / 40) == 0 && x != ignore) {
				targetY = (int) (getY() + 40);
				targetX = (int) getX();
			} else {
				ignore = 4;
				setTarget();
			}
			break;
		}
	}

	private boolean Obstaclecheck(Engine2DRectangleBoundingBox box) {
		if (game.checkMap((int)(box.getX() / 40) - 1, (int)(box.getY() / 40)) == 2
				|| game.checkMap((int)(box.getX() / 40) + 1, (int)(box.getY() / 40)) == 2
				|| game.checkMap((int)(box.getX() / 40), (int)(box.getY() / 40) - 1) == 2
				|| game.checkMap((int)(box.getX() / 40), (int)(box.getY() / 40) + 1) == 2) {
			return true;
		}
		return false;
	}

	private boolean explostionCheck(Engine2DEntity comp) {
		for (Bomb bomb : game.getBombs()) {
			if(!isOntop(bomb) && bomb.getDetonated() == false){
				Engine2DRectangleBoundingBox box = (Engine2DRectangleBoundingBox) comp.getBoundingBox();
			if (checkLeft(bomb.getX(), bomb.getY(), bomb.getSize(), box)) {
				return true;
			}
			if (checkRight(bomb.getX(), bomb.getY(), bomb.getSize(), box)) {
				return true;
			}
			if (checkUp(bomb.getX(), bomb.getY(), bomb.getSize(), box)) {
				return true;
			}
			if (checkDown(bomb.getX(), bomb.getY(), bomb.getSize(), box)) {
				return true;
			}
			}
		}
		return false;
	}

	private boolean checkLeft(float x, float y, float amount, Engine2DRectangleBoundingBox check) {
		Engine2DRectangleBoundingBox left = new Engine2DRectangleBoundingBox((int)x - 40,(int) y, 40, 40);
		for (int i = 0; i < amount; i++) {
			if (check.checkCollision(left)) {
				return true;
			} else {
				left.setX(left.getX() - 40);
			}
		}
		return false;
	}

	private boolean checkRight(float x, float y, int amount, Engine2DRectangleBoundingBox check) {
		Engine2DRectangleBoundingBox right = new Engine2DRectangleBoundingBox((int)x + 40, (int)y, 40, 40);
		for (int i = 0; i < amount; i++) {
			if (check.checkCollision(right)) {
				return true;
			} else {
				right.setX(right.getX() + 40);
			}
		}
		return false;
	}

	private boolean checkUp(float x, float y, int amount, Engine2DRectangleBoundingBox check) {
		Engine2DRectangleBoundingBox up = new Engine2DRectangleBoundingBox((int)x, (int)y - 40, 40, 40);
		for (int i = 0; i < amount; i++) {
			if (check.checkCollision(up)) {
				return true;
			} else {
				up.setX(up.getY() - 40);
			}
		}
		return false;
	}

	private boolean checkDown(float x, float y, int amount, Engine2DRectangleBoundingBox check) {
		Engine2DRectangleBoundingBox down = new Engine2DRectangleBoundingBox((int)x, (int)y + 40, 40, 40);
		for (int i = 0; i < amount; i++) {
			if (check.checkCollision(down)) {
				return true;
			} else {
				down.setY(down.getY() + 40);
			}
		}
		return false;
	}

	public void move() {
		
		if(getX() == targetX && getY() == targetY){
			setTarget();
		}else {
			
			if (targetX < getX() && targetY == getY()) {
				skin = 2;
				moveLeft();
				if(explostionCheck(this)){
					nextAvailable();
					ignore = 1;
				}
			} else if (targetX > getX() && targetY == getY()) {
				skin = 3;
				moveRight();
				if(explostionCheck(this)){
					nextAvailable();
					ignore = 2;
				}
			} else if (targetY < getY() && targetX == getX()) {
				skin = 1;
				moveUp();
				if(explostionCheck(this)){
					nextAvailable();
					ignore = 3;
				}
			} else if (targetY > getY()  && targetX == getX()) {
				skin = 0;
				moveDown();
				if(explostionCheck(this)){
					nextAvailable();
					ignore = 4;
				}
			}
		}
	}
	
	private void nextAvailable(){
		if(game.checkMap( (int)(getX()-40)/40, (int)(getY()/40)) == 0){
			targetX = (int) (getX() - 40);
			targetY = (int) getY();
		}else if(game.checkMap( (int)(getX()+40)/40, (int)(getY()/40)) == 0){
			targetX = (int) (getX() + 40);
			targetY = (int) getY();
		}else if(game.checkMap( (int)getX()/40, (int)(getY()-40)/40) == 0){
			targetX = (int) getX();
			targetY = (int) (getY() - 40);
		}else if(game.checkMap( (int)getX()/40, (int)(getY()+40)/40) == 0){
			targetX = (int) getX();
			targetY = (int) (getY() + 40);
		}
	}

	private int randomChecker(int ignore, int max) {
		int x = rnd.nextInt(max) + 1;
		if (x != ignore) {
			return x;
		} else {
			return randomChecker(ignore, max);
		}
	}

	@Override
	public void update() {
		move();
		pickPower();
		updateWalkable();
		game.render();
	}
	
	public Game getGame(){
		return (Game)super.getGame();
	}

}
