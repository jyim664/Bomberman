import processing.core.PImage;

/**
 * this class represents a ai unit
 * 
 * @author Johnry Zhao
 *
 */
public class Bot extends Unit {

	private int[] destination;

	private int timeUntilBomb = 120;
	private int xUnitsPerMove = 0;
	private int yUnitsPerMove = 0;

	private static final int BOT_WIDTH = 30;
	private static final int BOT_HEIGHT = 45;

	
	private int oscillateY = 0;
	
	private int bombCount;
	private int currentOnScreen = 0;
	private int explosionRadius;

	/**
	 * Creates a Bot with lives amount of lives, speed amount of speed, and with the
	 * top left corner at (xLoc, yLoc). the image drawn will be img and uses lvl to
	 * choose the first spot.
	 * 
	 * @param lives
	 *            number of lives this bot will have
	 * @param speed
	 *            the number of units that the bot can move in one step
	 * @param xLoc
	 *            the x value of the top left corner of the bot spawns here
	 * @param yLoc
	 *            the y value of the top left corner of the bot spawns here
	 * @param img
	 *            the image you will use for the bot
	 * @param lvl
	 *            the level that the bot chooses a location from
	 */
	public Bot(int lives, int speed, int xLoc, int yLoc, PImage img, Levels lvl) {
		super(lives, speed, xLoc, yLoc, BOT_WIDTH, BOT_HEIGHT, img);
		bombCount = 1;
		destination = getNewDst(lvl);
		explosionRadius = 1;
		
		timeUntilBomb = (int) (Math.random() * 120 + 1);
		
	}

	/**
	 * method that is currently useless
	 * 
	 * @param dangerous
	 */
	public void makeDecision(Boolean[][] dangerous) {
		int safeSpotX;
		int safeSpotY;
		if (!dangerous[xLoc][yLoc]) {

		} else if (!dangerous[xLoc][yLoc + 1]) {

			/*
			 * for (int i = 0; i < dangerous.length; i++) { for (int j = 0; j <
			 * dangerous[i].length; j++) { if (!dangerous[i][j]) {
			 * 
			 * 
			 * } }
			 * 
			 * }
			 * 
			 */

		}
	}

	/**
	 * this method chooses a random spot for the bot to move to
	 * 
	 * @param lvl
	 *            it chooses a spot that isn't a wall on this level.
	 */
	public void RandomMovements(Levels lvl) {
		if (Math.abs(this.getXLoc() - destination[0]) < 80 && Math.abs(this.getYLoc() - destination[1]) < 80) {
			getNewDst(lvl);
		}
		if (this.isEmptyX(lvl) == true) {
			oscillateY = 0;
			this.moveXDirection(xUnitsPerMove * 3);

		} else if (this.isEmptyY(lvl) == true && oscillateY < 3) {

			this.moveYDirection(yUnitsPerMove * 3);

		} else if (this.isEmptyX(lvl) == false && oscillateY < 3) {

			yUnitsPerMove = -yUnitsPerMove;
			oscillateY++;
			this.moveYDirection(yUnitsPerMove * 3);
		} else if (this.isEmptyY(lvl) == false) {

			xUnitsPerMove = -xUnitsPerMove;
			this.moveXDirection(xUnitsPerMove * 3);
		} else {
			getNewDst(lvl);
		}

		/*
		 * else { this.moveXDirection(xUnitsPerMove*3);
		 * this.moveYDirection(yUnitsPerMove*3);
		 * 
		 * }
		 */
	}

	private boolean isEmptyY(Levels lvl) {
		int yLocMin = 0;
		int yLocMax = 0;
		int xLocMax = 0;
		int xLocMin = 0;
		yLocMin = (int) (((double) (yLoc - 3) / 50));
		yLocMax = (int) (((double) (yLoc + 48) / 50));
		xLocMin = (int) (((double) xLoc / 50) + 0.5);
		xLocMax = (int) (((double) (xLoc + 30) / 50));
		if (yUnitsPerMove < 0 && !lvl.getBad(xLocMax, yLocMin) && !lvl.getBad(xLocMin, yLocMin)) {
			return true;
		} else if (yUnitsPerMove > 0 && !lvl.getBad(xLocMin, yLocMax) && !lvl.getBad(xLocMax, yLocMax)) {
			return true;
		}

		return false;
	}

	private boolean isEmptyX(Levels lvl) {
		int xLocMin = 0;
		int xLocMax = 0;
		int yLocMax = 0;
		int yLocNow = 0;
		xLocMin = (int) (((double) (xLoc - 3) / 50));
		xLocMax = (int) (((double) (xLoc + 33) / 50));
		yLocNow = (int) (((double) (yLoc) / 50));
		yLocMax = (int) (((double) (yLoc + 45) / 50));
		if (xUnitsPerMove < 0 && !lvl.getBad(xLocMin, yLocNow) && !lvl.getBad(xLocMin, yLocMax)) {
			return true;
		} else if (xUnitsPerMove > 0 && !lvl.getBad(xLocMax, yLocNow) && !lvl.getBad(xLocMax, yLocMax)) {
			return true;
		}
		return false;
	}

	private int[] getNewDst(Levels lvl) {
		int[] dst = new int[2];

		dst[0] = (int) (Math.random() * 600 + 100);
		dst[1] = (int) (Math.random() * 600 + 100);

		if (lvl.getUnbreakableStatus(dst[0] / 50, dst[1] / 50)) {
			return getNewDst(lvl);
		}

		if ((dst[0] - this.getXLoc()) < 0) {
			xUnitsPerMove = -1;
		} else {
			xUnitsPerMove = 1;
		}

		if ((dst[1] - this.getYLoc()) < 0) {
			yUnitsPerMove = -1;
		} else {
			yUnitsPerMove = 1;
		}

		return dst;
	}

	/**
	 * returns the location on the 2D array where the bot drops the bomb
	 */
	public int[] dropBomb(Levels lvl) {
		int[] a =  super.dropBomb();

		if (canDropBomb()) {
			 if(isEmptyX( lvl)) {
				 a[0] += 1;
				 return a;
			 }else if  (isEmptyY(lvl)){
				 a[1] += 1;
			 }
			

			

			currentOnScreen++;
			return a;
		}

		return null;

	}

	/**
	 * 
	 * @return true if the bot has satisfied all conditions to drop a bomb, false if
	 *         not.
	 */
	public int[] dropBomb() {
		if (canDropBomb()) {
			currentOnScreen++;
			return super.dropBomb();

		}

		return null;

	}

	public boolean canDropBomb() {
		if (this.currentOnScreen >= bombCount) {
			return false;
		} else {
			return true;
		}
	}

	public void changeNumBombs(int x) {
		currentOnScreen += x;
	}


	public boolean placeOneMore() {
		this.timeUntilBomb --;
		if (timeUntilBomb <= 0) {
			timeUntilBomb =  (int) (Math.random() * 120 + 1);;
			System.out.println(timeUntilBomb);
			return true;
		}
		else {
			return false;
		}
	}

	public void addRadius() {
		explosionRadius++;
	}

	public int getRadius() {
		return explosionRadius;
	}
}
