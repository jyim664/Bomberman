import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class resembles the actual game map where the game will be taking place. 
 * @author Justin Yim
 *
 */
public class LevelOne {

	private int width = Main.width;
	private int height = Main.height;
	private boolean grid[][];
	private PImage image;
	
	public LevelOne() {
		grid = new boolean[16][16];
		
		for(int i = 0; i< grid.length; i++) {
			grid[0][i] = true;
			grid[1][i] = true;
			grid[grid.length - 1][i] = true;
			grid[grid.length - 2][i] = true;
		}
		
		for(int i = 0; i < grid.length; i++) {
			grid[i][0] = true;
			grid[i][1] = true;


			grid[i][grid.length-1] = true;
			grid[i][grid.length-2] = true;


		
		}
	} 
	
	public void setup(PApplet drawer) {
		
		
	}
	
	public void draw(PApplet marker, float x, float y, float width, float height) {
		
		marker.pushStyle();
		
		float cellWidth = width / grid.length;
		float cellHeight = height / grid[0].length;

		marker.stroke(0);
		
		for (int i = 0; i < grid[0].length;i++) {
			for (int j = 0; j < grid.length; j ++) {
				if (grid[j][i] == true) {
					marker.fill(0);
				}
				else {
					marker.fill(255);
					
				}
				marker.rect(cellWidth*j + x, cellHeight*i + y, cellWidth, cellHeight);

			}
		}

		marker.popStyle();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
