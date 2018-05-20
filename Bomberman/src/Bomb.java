import processing.core.PImage;

/**
 * 
 * @author Johnry Zhao, Kush Patel
 *
 */
public class Bomb extends Unit {

	private int range;
	private int timer;
	private boolean exploded;

	// explode direction
	private boolean ER;
	private boolean EL;
	private boolean EU;
	private boolean ED;

	public static final int BOMB_WIDTH = 30;
	public static final int BOMB_HEIGHT = 45;

	/**
	 * makes a bomb
	 * 
	 * @param img
	 *            the image used for drawing the bomb
	 * @param xLoc
	 *            the x location of the bomb's top left corner
	 * @param yLoc
	 *            the y location of the bomb's top left corner
	 */
	public Bomb(PImage img, int xLoc, int yLoc) {
		super(1, 3, xLoc, yLoc, BOMB_WIDTH, BOMB_HEIGHT, img);
		range = 1;
		timer = 0;
		exploded = false;
		ER = false;
		EL = false;
		EU = false;
		ED = false;

	}

	/**
	 * makes the bomb explode, sets exploded to true
	 */
	public void explode() {
		exploded = true;

	}

	/**
	 * counts down to zero
	 * 
	 * @return true if timer hits zero, false otherwise
	 */
	public boolean countDown() {
		if (exploded) {
			if (timer < 15) {
				timer++;
				return false;
			} else {

				timer = 0;
				return true;
			}
		}

		else {
			if (timer < 40) {
				timer++;
				return false;
			}

			else {
				if (timer < 30) {
					timer++;
					return false;
				} else {

					timer = 0;
					return true;
				}
			}
		}
	}

	/**
	 * 
	 * @return returns true if the bomb has exploded, and false if the bomb has not
	 *         exploded
	 */
	public boolean getStatus() { // if Exploded or not
		return exploded;
	}

	// public boolean canSpreadLeft(LevelOne lvl) {
	// char[][] grid = lvl.getGrid();
	// int[] bombCoords = super.pixeltoGrid();
	// if(bombCoords[0] - 1 >= 0) {
	// return true;
	// }
	// return false;
	// }
	// public boolean canSpreadRight(LevelOne lvl) {
	// char[][] grid = lvl.getGrid();
	// int[] bombCoords = super.pixeltoGrid();
	// if(bombCoords[0] + 1 < grid.length) {
	// return true;
	// }
	// return false;
	// }
	// public boolean canSpreadUp(LevelOne lvl) {
	// char[][] grid = lvl.getGrid();
	// int[] bombCoords = super.pixeltoGrid();
	// if(bombCoords[1] - 1 >= 0) {
	// return true;
	// }
	// return false;
	// }
	// public boolean canSpreadLeftDown(LevelOne lvl) {
	// char[][] grid = lvl.getGrid();
	// int[] bombCoords = super.pixeltoGrid();
	// if(bombCoords[1] + 1 < grid[0].length) {
	// return true;
	// }
	// return false;
	// }

	public void setLeft(boolean x) {
		EL = x;
	}

	public void setRight(boolean x) {
		ER = x;
	}

	public void setUp(boolean x) {
		EU = x;
	}

	public void setDown(boolean x) {
		ED = x;
	}

	public boolean getLeft() {
		return EL;
	}

	public boolean getRight() {
		return ER;
	}

	public boolean getUp() {
		return EU;
	}

	public boolean getDown() {
		return ED;
	}
}
