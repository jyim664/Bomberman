import processing.core.PImage;

/**
 * this class represents a ai unit
 * 
 * @author Johnry Zhao
 *
 */
public class Bot extends Unit {
	
	public static final int BOT_WIDTH = 30;
	public static final int BOT_HEIGHT = 45;

	public Bot(int lives, int speed, int xLoc, int yLoc, PImage img) {
		super(lives, speed, xLoc, yLoc, BOT_WIDTH, BOT_HEIGHT, img);
		// TODO Auto-generated constructor stub
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
		int k = 4;
		while (this.getLives() > 0) {
			int i = (int) (Math.random() * 4 + 1);
			if (i == 1) {
				this.moveXDirection(3);
			} else if (i == 2) {
				this.moveXDirection(-3);
			} else if (i == 3) {
				this.moveYDirection(-3);
			} else if (i == 4) {
				this.moveYDirection(3);
			}

			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
