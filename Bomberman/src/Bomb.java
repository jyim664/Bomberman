import processing.core.PImage;

public class Bomb extends Unit {

	private int range;
	private int timer;
	private boolean exploded;


	public static final int BOMB_WIDTH = 30;
	public static final int BOMB_HEIGHT = 45;

	public Bomb(PImage img, int xLoc, int yLoc) {
		super(1, 3, xLoc, yLoc, BOMB_WIDTH, BOMB_HEIGHT, img);
		range = 1;
		timer = 0;
		exploded = false;

	}

	public void explode(){
		exploded = true;


	}

	public boolean countDown() {
		if(exploded) {
			if (timer < 10 ) {
				timer++;
				return false;
			}
			else {

				timer = 0;
				return true;
			}
		}

		else {
			if (timer < 60 ) {
				timer++;
				return false;
			}
			else {

				timer = 0;
				return true;
			}
		}
	}
	public boolean getStatus() { //if Exploded or not
		return exploded;
	}

//	public boolean canSpreadLeft(LevelOne lvl) {
//		char[][] grid = lvl.getGrid();
//		int[] bombCoords = super.pixeltoGrid();
//		if(bombCoords[0] - 1 >= 0) {
//			return true;
//		}
//		return false;
//	}
//	public boolean canSpreadRight(LevelOne lvl) {
//		char[][] grid = lvl.getGrid();
//		int[] bombCoords = super.pixeltoGrid();
//		if(bombCoords[0] + 1 < grid.length) {
//			return true;
//		}
//		return false;
//	}
//	public boolean canSpreadUp(LevelOne lvl) {
//		char[][] grid = lvl.getGrid();
//		int[] bombCoords = super.pixeltoGrid();
//		if(bombCoords[1] - 1 >= 0) {
//			return true;
//		}
//		return false;
//	}
//	public boolean canSpreadLeftDown(LevelOne lvl) {
//		char[][] grid = lvl.getGrid();
//		int[] bombCoords = super.pixeltoGrid();
//		if(bombCoords[1] + 1 < grid[0].length) {
//			return true;
//		}
//		return false;
//	}
}




