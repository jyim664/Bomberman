
public class Unit {

	public int lives;
	public int speed;
	public int xLoc;
	public int yLoc;

	public Unit(int lives, int speed, int xLoc, int yLoc) {
		this.lives = lives;
		this.speed = speed;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
	}

	public void addLives(int howMany) {
		lives += howMany;
	}

	public void speedUp(int howFast) {
		speed += howFast;
	}

	public void moveXDirection(int howMany) {
		xLoc += howMany;
	}
	
	public void moveYDirection(int howMany) {
		yLoc += howMany;
	}
	
	public int getLives() {
		return lives;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getXLoc() {
		return xLoc;
	}
	public int getYLoc() {
		return yLoc;
	}
	
	public String toString() {
		String retVal = "Unit with "+ lives +" health, "+ speed +" speed, at ("+xLoc+','+yLoc+").";
				return retVal;
	}
}
