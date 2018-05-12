import java.awt.Shape;
import java.util.ArrayList;

import processing.core.PImage;

/**
 * This class represents the player-controlled Unit. Includes methods such as Walk and placeBomb.
 * @author Justin Yim, Johnry Zhao
 *
 */
public class Player extends Unit {
	
	private int bombsCount;
	private int a;
	
	public static final int PLAYER_WIDTH = 30;
	public static final int PLAYER_HEIGHT = 45;
	
	public Player(PImage img, int x, int y) {
		super(1, 3, x, y, PLAYER_WIDTH, PLAYER_HEIGHT, img);
		bombsCount = 2;
	}
	
	public void walkX(int dir) {
		
		xLoc += dir;

		
	}
	
	public void walkY(int dir) {
		
		yLoc += dir;

				
	}
	
	




	public void act(ArrayList<Shape> obstacles) {
		// FALL (and stop when a platform is hit)
	}

	
	
	

	
}
