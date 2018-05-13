import processing.core.PImage;

public class Bomb extends Unit {
	
	private int range;
	
	public Bomb( int xLoc, int yLoc, PImage img) {
		super(1, 0, xLoc, yLoc, 30, 30, img);
		range = 1;
		
	}

	public void explode(	){
		
		this.addLives(-1);
	}
	
}
