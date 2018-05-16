import processing.core.PApplet;
import processing.core.PImage;




/**
 * This class represents a basic unit on the game screen, whether it be bot or player. Holds methods that both player and bots will utilize.
 * @author Justin Yim, Johnry Zhao
 *
 */
public class Unit {

	public int lives;
	public int speed;
	public int xLoc;
	public int yLoc;
	public int width, height;
	
	public PImage image;

	public Unit(int lives, int speed, int xLoc, int yLoc, int width, int height, PImage img) {
		this.lives = lives;
		this.speed = speed;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.width = width;
		this.height = height;
		
		
		image = img;
	}

	public void addLives(int howMany) {
		lives += howMany;
	}

	public void speedUp(int howFast) {
		speed += howFast;
	}

	public void moveXDirection(int howMany) {
		xLoc += howMany;
	}
	
	public void moveYDirection(int howMany) {
		yLoc += howMany;
	}
	
	public int getLives() {
		return lives;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getXLoc() {
		return xLoc;
	}
	public int getYLoc() {
		return yLoc;
	}
	
	public void setImage(PImage other) {
		image = other;
	}
	
	public String toString() {
		String retVal = "Unit with "+ lives +" health, "+ speed +" speed, at ("+xLoc+','+yLoc+").";
				return retVal;
	}
	public void draw(PApplet g) {
		g.image(image,(int)xLoc,(int)yLoc,(int)width,(int)height);
	}
	
	public int[] dropBomb(){ //From Pixels to grid index
		int[] retVal =  new int[2];
		retVal[0] = (int)(((double)xLoc/50)+0.5);
		
		retVal[1] = (int)(((double)yLoc/50)+0.5);
		return retVal;
	}
	
	public int[] pixeltoGrid(){ 
		int[] retVal =  new int[2];
		retVal[0] = (int)(((double)xLoc/50)+0.5);
		
		retVal[1] = (int)(((double)yLoc/50)+0.5);
		return retVal;
	}
	
	public int[] gridToPixel(int x, int y){  //x and y = grid indexes
		int[] retVal =  new int[2];
		retVal[0] = (int)(((double)x*50)+0.5);
		
		retVal[1] = (int)(((double)y*50)+0.5);
		return retVal;
	}
	
	
	
	
	
}
