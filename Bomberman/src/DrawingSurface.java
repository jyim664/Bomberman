import java.awt.event.KeyEvent;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class draws the main menu and the game screen.
 * @author Justin Yim, Kush Patel, Johnry Zhao
 *
 */
public class DrawingSurface extends PApplet {

	private MenuScreen menu;
	private LevelOne board;
	
	private Player bomberman1;
	private Player bomberman2; 
	private Bot Bot1;
	private PImage boundaryWall;
	private PImage breakableWall;
	
	private ArrayList<PImage> assets; //all of Bomberman's images
	private ArrayList<Integer> keys;

	private boolean gameState; // false == menu, true == game
	
	

	private static final int frameHeight = 800, frameWidth = 800;
	
	public DrawingSurface() {
		menu = new MenuScreen("B  mberman");
		board = new LevelOne();
		gameState = false; //menu = false, game screen = true;
		
		assets = new ArrayList<PImage>();
		keys = new ArrayList<Integer>();


		
	}
	
	public void spawnPlayer1() {
		bomberman1 = new Player(assets.get(0), 0,0);
	}
	public void spawnPlayer2() {
		bomberman2 = new Player(assets.get(0), 750,0);
	}
	
	
	// The statements in the setup() function 
	// execute once when the program begins
	public void setup() {
		assets.add(loadImage("bombermanFront.png")); //0 = front
		assets.add(loadImage("bombermanBack.png")); //1 = back
		assets.add(loadImage("bombermanLeft.png")); //2 = left
		assets.add(loadImage("bombermanRight.png")); //3 = right
		
		boundaryWall = loadImage("UnbreakableStoneTile.png");

	//	Bot1 = new Bot(1, 1, 200, 200, 50, 50, boundaryWall);
		
		menu.setup(this);
		board.setup(this);
		spawnPlayer1();
		spawnPlayer2();

		
	}
	
	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	public void draw() { 
		this.scale((float) width / frameWidth, (float) height / frameHeight);//scales objects based on framesize
		if(gameState == false) {
			menu.draw(this);
			menu.drawInsPage(this);
			menu.drawMapPage(this);
		}
		if(gameState) {
		board.draw(this, 0, 0, Main.width, Main.height, boundaryWall);
		bomberman1.draw(this);
		bomberman2.draw(this);
	//	Bot1.RandomMovements();
	//	Bot1.draw(this);

		}
		
		
		// modifying stuff
				//player 1
				if (isPressed(KeyEvent.VK_A)) {
					bomberman1.setImage(assets.get(2));
					bomberman1.walkX(-1);

				}
				if (isPressed(KeyEvent.VK_D)) {
					bomberman1.setImage(assets.get(3));
					bomberman1.walkX(1);

				}
				if (isPressed(KeyEvent.VK_W)) {
					bomberman1.setImage(assets.get(1));
					bomberman1.walkY(-1);

				}
				if (isPressed(KeyEvent.VK_S)) {
					bomberman1.setImage(assets.get(0));
					bomberman1.walkY(1);

				}
				
				
				//player 2
				if (isPressed(KeyEvent.VK_LEFT)) {
					bomberman2.setImage(assets.get(2));
					bomberman2.walkX(-1);

				}
				if (isPressed(KeyEvent.VK_RIGHT)) {
					bomberman2.setImage(assets.get(3));
					bomberman2.walkX(1);

				}
				if (isPressed(KeyEvent.VK_UP)) {
					bomberman2.setImage(assets.get(1));
					bomberman2.walkY(-1);

				}
				if (isPressed(KeyEvent.VK_DOWN)) {
					bomberman2.setImage(assets.get(0));
					bomberman2.walkY(1);

				}
				
			
				
		
	}
	
	public void mousePressed() {
		if(menu.isOverShop()) {
			
			menu.shiftShopButton(10, -20, 3);
		}
		
		if(menu.isOverStart()) {
			
			menu.shiftStartButton(10, -20, 3);
		}
		
		if(menu.isOverIns()) {
			
			menu.shiftInsButton(10, -20, 3);
		}
	}
	
	public void mouseReleased() {
		if(menu.isOverShop()) {
			
			menu.shiftShopButton(-10, 20, -3);
		}
		
		if(menu.isOverStart()) {
			
			menu.shiftStartButton(-10, 20, -3);
		}
		if(menu.isOverIns()) {
			
			menu.shiftInsButton(-10, 20, -3);
			
			
		}
		
	}
	
	public void mouseClicked() {
		if(menu.isOverX()) {
			menu.changeInsPageStatus(false);
		}
		if(menu.isOverIns()) {
			menu.changeInsPageStatus(true);
		}
		if(menu.isOverStart()) {
			menu.changeStartStatus(true);
			menu.changeMapPageStatus(true);

		}
		if(menu.isOverMapPage()) {
			gameState = true;
		}
	}
	
	public void keyPressed() {
		keys.add(keyCode);
	}

	public void keyReleased() {
		while(keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}

}
