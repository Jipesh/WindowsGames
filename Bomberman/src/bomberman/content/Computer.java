package bomberman.content;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.engine2D.BoundingBox;
import game.engine2D.Entity;

public class Computer extends Player implements Runnable {
	private final Game game;
	private final Random rnd;
	private int targetX, targetY, ignore;
	private final Timer moveDecider = new Timer();

	public Computer(int character, int x, int y, Game game) {
	super(character, x, y, game);
	this.game = game;
	this.rnd = new Random();
	
	setTarget();
	moveDecider.scheduleAtFixedRate(new TimerTask(){
	  
		 @Override 
		 public void run() {
			 plantBomb();
			 game.updateWalkable();
			 if(getX() == targetX && getY() == targetX){
				setTarget();
			 }
		 }
	  }, 0, 1000);
	 }

	private void setTarget() {
		int x = randomChecker(ignore, 4);
		System.out.println(x);
		switch (x) {

		case 1:
			BoundingBox boxLeft = new BoundingBox(getX() - 40, getY(), getWidth(), getHeight());
			if (game.checkMap(boxLeft.getX() / 40, boxLeft.getY() / 40) == 0 && x != ignore) {
				targetX = getX() - 40;
				targetY = getY();
			} else {
				ignore = 1;
				setTarget();
			}
			break;
		case 2:
			BoundingBox boxRight = new BoundingBox(getX() + 40, getY(), getWidth(), getHeight());
			if (game.checkMap(boxRight.getX() / 40, boxRight.getY() / 40) == 0 && x != ignore) {
				targetX = getX() + 40;
				targetY = getY();
			} else {
				ignore = 2;
				setTarget();
			}
			break;
		case 3:
			BoundingBox boxUp = new BoundingBox(getX(), getY() - 40, getWidth(), getHeight());
			if (game.checkMap(boxUp.getX() / 40, boxUp.getY() / 40) == 0 && x != ignore) {
				targetY = getY() - 40;
				targetX = getX();
			} else {
				ignore = 3;
				setTarget();
			}
			break;
		case 4:
			BoundingBox boxDown = new BoundingBox(getX(), getY() + 40, getWidth(), getHeight());
			if (game.checkMap(boxDown.getX() / 40, boxDown.getY() / 40) == 0 && x != ignore) {
				targetY = getY() + 40;
				targetX = getX();
			} else {
				ignore = 4;
				setTarget();
			}
			break;
		}
	}

	private boolean Obstaclecheck(BoundingBox box) {
		if (game.checkMap((box.getX() / 40) - 1, (box.getY() / 40)) == 2
				|| game.checkMap((box.getX() / 40) + 1, (box.getY() / 40)) == 2
				|| game.checkMap((box.getX() / 40), (box.getY() / 40) - 1) == 2
				|| game.checkMap((box.getX() / 40), (box.getY() / 40) + 1) == 2) {
			return true;
		}
		return false;
	}

	private boolean explostionCheck(BoundingBox comp) {
		for (Bomb bomb : game.getBombs()) {
			if(!isOntop(bomb) && bomb.getDetonated() == false){
			if (checkLeft(bomb.getX(), bomb.getY(), bomb.getSize(), comp)) {
				return true;
			}
			if (checkRight(bomb.getX(), bomb.getY(), bomb.getSize(), comp)) {
				return true;
			}
			if (checkUp(bomb.getX(), bomb.getY(), bomb.getSize(), comp)) {
				return true;
			}
			if (checkDown(bomb.getX(), bomb.getY(), bomb.getSize(), comp)) {
				return true;
			}
			}
		}
		return false;
	}

	private boolean checkLeft(int x, int y, int amount, BoundingBox check) {
		BoundingBox left = new BoundingBox(x - 40, y, 40, 40);
		for (int i = 0; i < amount; i++) {
			if (check.checkCollision(left)) {
				return true;
			} else {
				left.setX(left.getX() - 40);
			}
		}
		return false;
	}

	private boolean checkRight(int x, int y, int amount, BoundingBox check) {
		BoundingBox right = new BoundingBox(x + 40, y, 40, 40);
		for (int i = 0; i < amount; i++) {
			if (check.checkCollision(right)) {
				return true;
			} else {
				right.setX(right.getX() + 40);
			}
		}
		return false;
	}

	private boolean checkUp(int x, int y, int amount, BoundingBox check) {
		BoundingBox up = new BoundingBox(x, y - 40, 40, 40);
		for (int i = 0; i < amount; i++) {
			if (check.checkCollision(up)) {
				return true;
			} else {
				up.setX(up.getY() - 40);
			}
		}
		return false;
	}

	private boolean checkDown(int x, int y, int amount, BoundingBox check) {
		BoundingBox down = new BoundingBox(x, y + 40, 40, 40);
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
				if(explostionCheck(getBoundingBox())){
					nextAvailable();
					ignore = 1;
				}
			} else if (targetX > getX() && targetY == getY()) {
				skin = 3;
				moveRight();
				if(explostionCheck(getBoundingBox())){
					nextAvailable();
					ignore = 2;
				}
			} else if (targetY < getY() && targetX == getX()) {
				skin = 1;
				moveUp();
				if(explostionCheck(getBoundingBox())){
					nextAvailable();
					ignore = 1;
				}
			} else if (targetY > getY()  && targetX == getX()) {
				skin = 0;
				moveDown();
			}
		}
	}
	
	private void nextAvailable(){
		if(game.checkAvailability((getX()-40)/40, getY()/40)){
			targetX = getX() - 40;
			targetY = getY();
		}else if(game.checkAvailability((getX()+40)/40, getY()/40)){
			targetX = getX() + 40;
			targetY = getY();
		}else if(game.checkAvailability(getX()/40, (getY()-40)/40)){
			targetX = getX();
			targetY = getY() - 40;
		}else if(game.checkAvailability(getX()/40, (getY()+40)/40)){
			targetX = getX();
			targetY = getY() + 40;
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

	public void run() {
		move();
		pickPower();
		updateWalkable();
	}

}
