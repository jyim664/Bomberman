import processing.core.PImage;

/**
 * this class represents a ai unit
 * 
 * @author Johnry Zhao
 *
 */
public class Bot extends Unit {

	private int[] destination = new int[2];
	private boolean destinationReached;
	private int xUnitsPerMove = 0;
	private int yUnitsPerMove = 0;
	private int numberOfSteps = 0;

	private static final int BOT_WIDTH = 30;
	private static final int BOT_HEIGHT = 45;
	
	private int bombCount;
	private int currentOnScreen = 0;

	public Bot(int lives, int speed, int xLoc, int yLoc, PImage img) {
		super(lives, speed, xLoc, yLoc, BOT_WIDTH, BOT_HEIGHT, img);
		bombCount = 1;
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

	public void RandomMovements() {
		if(numberOfSteps<=0) {
			destinationReached = true;
		}
		if (destinationReached) {
			getNewDst();
			
		}else {
			this.moveXDirection(xUnitsPerMove);
			this.moveYDirection(yUnitsPerMove);
			numberOfSteps --;
		}

	}

	private void getNewDst() {
		
		destination[0] = (int)(Math.random()*600 + 100);
		destination[1] = (int)(Math.random()*600 + 100);
		destinationReached = false;
		xUnitsPerMove = (destination[0] - this.getXLoc())/50;
		yUnitsPerMove = (destination[1] - this.getYLoc())/50;
		numberOfSteps = 50;
	}
	
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
	
	
}
