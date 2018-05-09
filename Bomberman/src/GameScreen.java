import processing.core.PApplet;

public class GameScreen {

	private int width = Main.width;
	private int height = Main.height;
	private boolean grid[][];
	
	public GameScreen() {
		grid = new boolean[15][15];
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
