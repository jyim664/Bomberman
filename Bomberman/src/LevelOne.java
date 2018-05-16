import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class resembles the actual game map where the game will be taking place. 
 * @author Justin Yim
 *
 */
public class LevelOne extends PApplet { //12 x 12 map

	private int width = Main.width;
	private int height = Main.height;
	private char grid[][];
	private int[] pointCoords[][];
	private boolean bombExploded = false;
	
	public LevelOne() {
		 grid = new char[16][16];
		
	}
	
	public LevelOne(String filename) {
		grid = readData(filename);
	}
	
	public void setup(PApplet drawer) {
		

	}
	
	public void draw(PApplet marker, float x, float y, float width, float height,PImage img1, PImage img2, PImage img3, PImage img4, PImage img5) {
		
		marker.pushStyle();
		
		float cellWidth = width / grid.length;
		float cellHeight = height / grid[0].length;

		marker.stroke(0);
		
		for (int i = 0; i < grid[0].length;i++) {
			for (int j = 0; j < grid.length; j ++) {
				if (grid[j][i] == '#') {
					marker.image(img2, cellWidth*j + x, cellHeight*i);

				}
				else if(grid[j][i] == '*') {
					marker.image(img1, cellWidth*j + x, cellHeight*i);
				}
				
				else if(grid[j][i] == '_') {
					
					marker.image(img3, cellWidth*j + x, cellHeight*i + y, cellWidth, cellHeight);

				}
				else {
					if(bombExploded == false) {
						marker.image(img4,cellWidth*j + x, cellHeight*i + y, cellWidth, cellHeight);

					}else {
						marker.image(img5,cellWidth*j + x, cellHeight*i + y, cellWidth, cellHeight);

					}
				}

			}
		}

		marker.popStyle();
		
	}
	
	public char[][] readData (String filename) {
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
						for(int i = 0; i < line.length(); i++)
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

	public void addBomb(int x, int y) {
		grid[x][y] = 'x';
	}
	
	public void resetPlace(int x, int y) {
		grid[x][y] = '_';
	}
	
	public void isExploded(boolean explode) {
		bombExploded = explode;
	}
	
	
	
	
}
