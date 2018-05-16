import java.awt.Shape;
import java.util.ArrayList;

import processing.core.PImage;

/**
 * This class represents the player-controlled Unit. Includes methods such as
 * Walk and placeBomb.
 * 
 * @author Justin Yim, Johnry Zhao
 *
 */
public class Player extends Unit {

	private int bombCount;
	private int currentOnScreen = 0;
	private int explosionRadius;

	public static final int PLAYER_WIDTH = 30;
	public static final int PLAYER_HEIGHT = 45;

	public Player(PImage img, int x, int y) {
		super(1, 3, x, y, PLAYER_WIDTH, PLAYER_HEIGHT, img);
		bombCount = 1;
		explosionRadius = 0;
	}

	public void walkX(int dir) {

		xLoc += dir;

	}

	public void walkY(int dir) {

		yLoc += dir;

	}

	public void act(ArrayList<Shape> obstacles) {
		// FALL (and stop when a platform is hit)
	}

	/**
	 * @return returns a coordinate in the form of a size 2 array (xLoc, Yloc) at
	 *         which to drop a bomb if and only if the current amount of bombs the
	 *         player has on the field is less than the amt that they are allowed
	 */
	public int[] dropBomb() {
		if(canDropBomb()) {
			currentOnScreen++;
			return super.dropBomb();

		}

		

		return null;

	}
	
	public boolean canDropBomb() {
		if (this.currentOnScreen >= bombCount) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void changeNumBombs(int x) {
		currentOnScreen += x;
}
	
	public void addRadius() {
		explosionRadius++;
	}
}
