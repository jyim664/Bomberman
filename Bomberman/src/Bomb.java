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
		System.out.println(timer);
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
	public boolean getStatus() {
		return exploded;
	}
	}
	
	
	

