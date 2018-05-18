import processing.core.PImage;

/**
 * this class represents a ai unit
 * 
 * @author Johnry Zhao
 *
 */
public class Bot extends Unit {

	private int[] destination ;

	private int xUnitsPerMove = 0;
	private int yUnitsPerMove = 0;


	private static final int BOT_WIDTH = 30;
	private static final int BOT_HEIGHT = 45;

	private int bombCount;
	private int currentOnScreen = 0;
	private int oscillateY = 0;

	public Bot(int lives, int speed, int xLoc, int yLoc, PImage img, Levels lvl) {
		super(lives, speed, xLoc, yLoc, BOT_WIDTH, BOT_HEIGHT, img);
		bombCount = 1;
		destination = getNewDst(lvl);
	}

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


	public void RandomMovements(Levels lvl) {
		if (Math.abs(this.getXLoc() - destination[0]) < 80 && Math.abs(this.getYLoc() - destination[1]) < 80) {
			getNewDst(lvl);
		}
		if (this.canMoveX(lvl) == true ) {
			oscillateY = 0;
			this.moveXDirection(xUnitsPerMove * 3);
			
		} else if (this.canMoveY(lvl) == true && oscillateY<3) {
			
			this.moveYDirection(yUnitsPerMove * 3);
			
		}else if (this.canMoveX(lvl) == false && oscillateY<3) {
			
			yUnitsPerMove = -yUnitsPerMove;
			oscillateY ++;
			this.moveYDirection(yUnitsPerMove * 3);
		} else if (this.canMoveY(lvl) == false) {
		
			xUnitsPerMove = -xUnitsPerMove;
			this.moveXDirection(xUnitsPerMove * 3);
		}else  {
			getNewDst(lvl);
		}
		

		/*
		 * else { this.moveXDirection(xUnitsPerMove*3);
		 * this.moveYDirection(yUnitsPerMove*3);
		 * 
		 * }
		 */
	}

	private boolean canMoveY(Levels lvl) {
		int yLocMin = 0;
		int yLocMax = 0;
		int xLocNow = 0;
		yLocMin = (int) (((double) yLoc / 50));
		yLocMax = (int) (((double) (yLoc + 48) / 50));
		xLocNow = (int) (((double) xLoc / 50) + 0.5);
		if (yUnitsPerMove < 0 && !lvl.getUnbreakableStatus(xLocNow, yLocMin)) {
			return true;
		} else if (yUnitsPerMove > 0 && !lvl.getUnbreakableStatus(xLocNow, yLocMax)) {
			return true;
		}

		return false;
	}

	private boolean canMoveX(Levels lvl) {
		int xLocMin = 0;
		int xLocMax = 0;
		int yLocNow = 0;
		xLocMin = (int) (((double) xLoc / 50));
		xLocMax = (int) (((double) (xLoc + 30) / 50));
		yLocNow = (int) (((double) (yLoc ) / 50) + 0.5);
		if (xUnitsPerMove < 0 && !lvl.getUnbreakableStatus(xLocMin, yLocNow)) {
			return true;
		} else if (xUnitsPerMove > 0 && !lvl.getUnbreakableStatus(xLocMax, yLocNow)) {
			return true;
		}
		return false;
	}

	private int[] getNewDst(Levels lvl) {
		int[] dst = new int[2];

		dst[0] = (int) (Math.random() * 600 + 100);
		dst[1] = (int) (Math.random() * 600 + 100);
		

		if(lvl.getUnbreakableStatus(dst[0]/50, dst[1]/50)) {
			return getNewDst(lvl);
		}
		
		if ((dst[0] - this.getXLoc())< 0) {
			xUnitsPerMove = -1;
		} else {
			xUnitsPerMove = 1;
		}

		if ((dst[1] - this.getYLoc())< 0) {
			yUnitsPerMove = -1;
		} else {
			yUnitsPerMove = 1;
		}

		return dst;
	}

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

}
