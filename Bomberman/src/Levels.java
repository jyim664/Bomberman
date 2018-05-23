import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class resembles the actual game map where the game will be taking place.
 * 
 * @author Justin Yim Kush Patel
 *
 */
public class Levels extends PApplet { // 12 x 12 map

	private int width = Main.width;
	private int height = Main.height;
	private char grid[][];
	private boolean unbreakableSpots[][];
	private boolean breakableSpots[][];
	private boolean goodSpots[][]; //grass
	private char items[][];

	private boolean player1BombExploded = false;
	private boolean player2BombExploded = false;
	private boolean bot1BombExploded = false;
	private boolean bot2BombExploded = false;

	private int botBombTimer = 40;

	/**
	 * creates a new level object with a 16 by 16 grid with nothing inside
	 */
	public Levels() {
		grid = new char[16][16];
		unbreakableSpots = new boolean[grid.length][grid[0].length];
	}

	/**
	 * reads in the level data from a text file and makes a new level from that
	 * 
	 * @param filename
	 *            the name of the file that should be read
	 */
	public Levels(String filename) {
		grid = readData(filename);
		unbreakableSpots = new boolean[grid.length][grid.length];
		breakableSpots = new boolean[grid.length][grid.length];
		goodSpots = new boolean[grid.length][grid.length];
		items = new char[grid.length][grid.length];
		

		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[j][i] == '*') {
					unbreakableSpots[j][i] = true;

				}
				if (grid[j][i] == '#') {
					breakableSpots[j][i] = true;
					int a = (int) (Math.random() * 6 );
					if (a == 1) {
						// current filler for health boost is the letter 'h'
						items[j][i] = 'h';
					} else if (a == 2) {
						// current filler for speed boost is the letter 's'
						items[j][i] = 's';
					}
						
				}
				
				if(grid[j][i] == '_') {
					goodSpots[j][i] = true;
				}
			}
		}
	}

	/**
	 * draws the layout of the level
	 * 
	 * @param marker
	 *            the surface to draw on
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param img1
	 * @param img2
	 * @param img3
	 * @param img4
	 * @param img5
	 */
	public void draw(PApplet marker, float x, float y, float width, float height, PImage img1, PImage img2, PImage img3,
			PImage img4, PImage img5, PImage img6, PImage img7) {

		marker.pushStyle();

		float cellWidth = width / grid.length;
		float cellHeight = height / grid[0].length;

		marker.stroke(0);

		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[j][i] == '#') {
					marker.image(img2, cellWidth * j + x, cellHeight * i);

				} else if (grid[j][i] == '*') {
					marker.image(img1, cellWidth * j + x, cellHeight * i);
				}

				else if (grid[j][i] == '_') {

					marker.image(img3, cellWidth * j + x, cellHeight * i + y, cellWidth, cellHeight);

				} else if (grid[j][i] == 'x') {
					if (player1BombExploded == false) {
						marker.image(img4, cellWidth * j + x, cellHeight * i + y, cellWidth, cellHeight);

					} else {
						marker.image(img5, cellWidth * j + x, cellHeight * i + y, cellWidth, cellHeight);

					}
				} else if (grid[j][i] == 'v') { // bot1 bomb

					if (bot1BombExploded == false) {
						marker.image(img4, cellWidth * j + x, cellHeight * i + y, cellWidth, cellHeight);

					} else {
						marker.image(img5, cellWidth * j + x, cellHeight * i + y, cellWidth, cellHeight);

					}
				} else if (grid[j][i] == 'b') { // bot 2 bomb

					if (bot2BombExploded == false) {
						marker.image(img4, cellWidth * j + x, cellHeight * i + y, cellWidth, cellHeight);

					} else {
						marker.image(img5, cellWidth * j + x, cellHeight * i + y, cellWidth, cellHeight);

					}
				} 
				else if (grid[j][i] == 'h') {
					marker.image(img6, cellWidth * j + x, cellHeight * i + y, cellWidth, cellHeight);

				}
				else if (grid[j][i] == 's') {
					marker.image(img7, cellWidth * j + x, cellHeight * i + y, cellWidth, cellHeight);

				}
				
				
				
				else { //player 2 bomb
					if (player2BombExploded == false) {
						marker.image(img4, cellWidth * j + x, cellHeight * i + y, cellWidth, cellHeight);
					} else {
						marker.image(img5, cellWidth * j + x, cellHeight * i + y, cellWidth, cellHeight);

					}
				}

			}
		}

		marker.popStyle();

	}

	public char[][] readData(String filename) {
		File dataFile = new File(filename);

		if (dataFile.exists()) {
			char[][] gameData = new char[16][16];

			int count = 0;

			FileReader reader = null;
			try {
				reader = new FileReader(dataFile);
				Scanner in = new Scanner(reader);

				while (in.hasNext() && count < 16) {
					String line = in.nextLine();
					for (int i = 0; i < line.length(); i++)
						gameData[count][i] = line.charAt(i);

					count++;
				}

			} catch (IOException ex) {
				System.out.println("File cannot be read.");
				return null;
			} catch (NumberFormatException ex) {
				System.out.println("File is in the wrong format.");
				return null;
			} finally {
				try {
					reader.close();
				} catch (IOException ex) {
					System.out.println("File cannot be closed.");
					return null;
				}
			}
			return gameData;
		} else {
			throw new IllegalArgumentException("Data file " + filename + " does not exist.");
		}
	}

	public void addP1Bomb(int x, int y) {
		grid[x][y] = 'x';
	}

	public void addP2Bomb(int x, int y) {
		grid[x][y] = 'y';
	}

	public void addBot1Bomb(int x, int y) {

		grid[x][y] = 'v';

	}

	public void addBot2Bomb(int x, int y) {
		grid[x][y] = 'b';
	}

	public void resetPlaceToGrass(int x, int y) {
		grid[x][y] = '_';
	}
	
	public void resetPlaceToItems(int x, int y) {
		grid[x][y] = items[x][y];
	}

	public boolean getUnbreakableStatus(int gridX, int gridY) {
		return unbreakableSpots[gridX][gridY];
	}

	public boolean getBreakableStatus(int gridX, int gridY) {
		return breakableSpots[gridX][gridY];
	}

	public boolean getGoodSpots(int gridX, int gridY) {
		return goodSpots[gridX][gridY];
	}
	public void removeBreakableSpot(int gridX, int gridY) {
		breakableSpots[gridX][gridY] = false;
	}

	public void setBreakableSpot(int gridX, int gridY) {
		breakableSpots[gridX][gridY] = true;
	}

	public void player1BombIsExploded(boolean explode) {
		player1BombExploded = explode;
	}

	public void player2BombIsExploded(boolean explode) {
		player2BombExploded = explode;
	}

	public void bot1BombIsExploded(boolean explode) {
		bot1BombExploded = explode;
	}

	public void bot2BombIsExploded(boolean explode) {
		bot2BombExploded = explode;
	}

	public boolean getBad(int gridX, int gridY) {
		if (breakableSpots[gridX][gridY]) {
			return true;

		} else if (unbreakableSpots[gridX][gridY]) {
			return true;
		}
		return false;

	}

	/**
	 * check to see if there is an item at this spot, and what type of item
	 * 
	 * @param gridX
	 *            x coord on the gme grid
	 * @param gridY
	 *            y coord on the game grid
	 * @return "Health" if the item is a health boost, "Speed" if the item is a
	 *         speed boost, and "None" if the item is non-existent
	 */
	public String getItemType(int gridX, int gridY) {
		if (items[gridX][gridY] == 'h') {
			return "Health";
		} else if (items[gridX][gridY] == 's') {
			return "Speed";
		} else {
			return "None";
		}
	}

	public void setup(DrawingSurface drawingSurface) {

	}
	
	public char getItems(int gridX, int gridY) {
		return items[gridX][gridY];
	}
	public void deleteItem(int gridX, int gridY) {
		items[gridX][gridY] = 'o';
	}

}
