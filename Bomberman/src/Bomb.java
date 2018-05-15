import processing.core.PImage;

public class Bomb extends Unit {
	
	private int range;
	private int timer;


	public static final int BOMB_WIDTH = 30;
	public static final int BOMB_HEIGHT = 45;
	
	public Bomb( int xLoc, int yLoc, PImage img) {
		super(1, 3, xLoc, yLoc, BOMB_WIDTH, BOMB_HEIGHT, img);
		range = 1;
		timer = 0;
		
	}

	public void explode(	){
		
		this.addLives(-1);
	}
	
	public boolean countDown() {
		if (timer >= 180) {
		
		 return true;
		}
		else {
			timer++;
			return false;
		}
	}
	
}
