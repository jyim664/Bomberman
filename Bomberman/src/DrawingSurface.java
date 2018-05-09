import java.awt.event.KeyEvent;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class DrawingSurface extends PApplet {

	private MenuScreen menu;
	private GameScreen board;
	
	private Player bomberman1;
	private ArrayList<PImage> assets; //all of Bomberman's images
	private ArrayList<Integer> keys;

	private boolean gameState; // false == menu, true == game
	
	private static final int frameHeight = 800;
	private static final int frameWidth = 800;

	
	public DrawingSurface() {
		menu = new MenuScreen("B    mberman");
		board = new GameScreen();
		gameState = false;
		
		assets = new ArrayList<PImage>();
		keys = new ArrayList<Integer>();


		
	}
	
	public void spawnPlayer() {
		bomberman1 = new Player(assets.get(0), Main.width/2-Player.PLAYER_WIDTH/2,50);
	}
	
	
	// The statements in the setup() function 
	// execute once when the program begins
	public void setup() {
		assets.add(loadImage("bombermanFront.png")); //0 = front
		assets.add(loadImage("bombermanBack.png")); //1 = back
		assets.add(loadImage("bombermanLeft.png")); //2 = left
		assets.add(loadImage("bombermanRight.png")); //3 = right

		menu.setup(this);
		spawnPlayer();
		
	}
	
	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	public void draw() { 
	this.scale((float)width/frameWidth, (float)height/frameHeight);
//		if(gameState == false) {
//		menu.draw(this);
//		menu.drawInsPage(this);
//		}
		
		board.draw(this, 0, 0, Main.width, Main.height);
		bomberman1.draw(this);
		
		
		// modifying stuff

				if (isPressed(KeyEvent.VK_LEFT)) {
					bomberman1.setImage(assets.get(2));
					bomberman1.walkX(-1);

				}
				if (isPressed(KeyEvent.VK_RIGHT)) {
					bomberman1.setImage(assets.get(3));
					bomberman1.walkX(1);

				}
				if (isPressed(KeyEvent.VK_UP)) {
					bomberman1.setImage(assets.get(1));
					bomberman1.walkY(-1);

				}
				if (isPressed(KeyEvent.VK_DOWN)) {
					bomberman1.setImage(assets.get(0));
					bomberman1.walkY(1);

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
