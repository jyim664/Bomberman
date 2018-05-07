import java.awt.Shape;
import java.util.ArrayList;

import processing.core.PImage;

public class Player extends Unit {
	
	private int bombsCount;
	private int a;
	
	public static final int PLAYER_WIDTH = 40;
	public static final int PLAYER_HEIGHT = 60;
	
	public Player(PImage img, int x, int y) {
		super(1, 1, 1, 1, PLAYER_WIDTH, PLAYER_HEIGHT, img);
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
