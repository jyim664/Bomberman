import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class draws the main menu and the game screen.
 * @author Justin Yim, Kush Patel, Johnry Zhao
 *
 */
public class DrawingSurface extends PApplet {

	private MenuScreen menu;
	private Levels board;

	private Player bomberman1;
	private Player bomberman2; 
	private Bot bot1;
	private Bot bot2;


	private PImage boundaryWall;
	private PImage breakableWall;
	private PImage grassTile;
	private PImage bomb;
	private PImage explosion;

	private ArrayList<PImage> assets; //all of Bomberman's images
	private ArrayList<Integer> keys;

	private boolean level1; // false == menu, true == game

	private ArrayList<Bomb> player1Bombs = new ArrayList<Bomb>();
	private ArrayList<Bomb> player2Bombs = new ArrayList<Bomb>();


	private static final int frameHeight = 800, frameWidth = 800;

	public DrawingSurface() {
		menu = new MenuScreen("B    mberman");
		board = new Levels("LevelOneMap");
		level1 = false; //menu = false, game screen = true;

		assets = new ArrayList<PImage>();
		keys = new ArrayList<Integer>();



	}

	public void spawnPlayer1() {
		bomberman1 = new Player(assets.get(0), 2*50,2*50); //FOR LEVEL 1
	}
	public void spawnPlayer2() {
		bomberman2 = new Player(assets.get(0), 13*50,2* 50); //FOR LEVEL 1
	}


	public void spawnBot1() {
		bot1 = new Bot(1,3,2*50,13*50,assets.get(0), board);

	}






	// The statements in the setup() function 
	// execute once when the program begins
	public void setup() {
		assets.add(loadImage("bombermanFront.png")); //0 = front
		assets.add(loadImage("bombermanBack.png")); //1 = back
		assets.add(loadImage("bombermanLeft.png")); //2 = left
		assets.add(loadImage("bombermanRight.png")); //3 = right

		boundaryWall = loadImage("UnbreakableStoneTile.png");
		breakableWall = loadImage("BreakableStoneTile.png");
		grassTile = loadImage("GrassTile.png");

		bomb = loadImage("bomb.png");
		explosion = loadImage("explode.png");


		menu.setup(this);
		board.setup(this);
		spawnPlayer1();
		spawnPlayer2();

		spawnBot1();
		//	bot1.setUp(board);


	}


	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	public void draw() {
		this.scale((float) width / frameWidth, (float) height / frameHeight);//scales objects based on framesize
		if(level1 == false) {
			menu.draw(this);
			menu.drawInsPage(this);
			menu.drawMapPage(this);
		}
		if(level1) {
			board.draw(this, 0, 0, Main.width, Main.height, boundaryWall, breakableWall, grassTile,bomb, explosion);
			bomberman1.draw(this);
			bomberman2.draw(this);
			bot1.RandomMovements(board);

			bot1.draw(this);

			

			//PLAYER 1 BOMB STUFF
			if(player1Bombs.size() > 0) {

				int explosionRadius = bomberman1.getRadius();


				if(player1Bombs.get(0).countDown()) {
					if(!player1Bombs.get(0).getStatus()) {
						board.player1BombIsExploded(true);
						player1Bombs.get(0).explode();

						for(int i = 0; i < explosionRadius; i++) {
							System.out.print("\n");

							if(!board.getUnbreakableStatus(player1Bombs.get(0).getXLoc()/50 + explosionRadius - i, player1Bombs.get(0).getYLoc()/50)) {
								board.addP1Bomb(player1Bombs.get(0).getXLoc()/50 + explosionRadius - i, player1Bombs.get(0).getYLoc()/50);
								player1Bombs.get(0).setRight(true);
								System.out.print(board.getUnbreakableStatus(player1Bombs.get(0).getXLoc()/50 + explosionRadius - i, player1Bombs.get(0).getYLoc()/50));
								//RIGHT
							}
							if(!board.getUnbreakableStatus(player1Bombs.get(0).getXLoc()/50 - explosionRadius + i, player1Bombs.get(0).getYLoc()/50)) {
								board.addP1Bomb(player1Bombs.get(0).getXLoc()/50 - explosionRadius + i, player1Bombs.get(0).getYLoc()/50);
								player1Bombs.get(0).setLeft(true);

								//Left
							}

							if(!board.getUnbreakableStatus(player1Bombs.get(0).getXLoc()/50, player1Bombs.get(0).getYLoc()/50 - explosionRadius + i)) {
								board.addP1Bomb(player1Bombs.get(0).getXLoc()/50, player1Bombs.get(0).getYLoc()/50 - explosionRadius + i);
								player1Bombs.get(0).setUp(true);

								//UP
							}


							if(!board.getUnbreakableStatus(player1Bombs.get(0).getXLoc()/50, player1Bombs.get(0).getYLoc()/50 + explosionRadius - i)) {
								board.addP1Bomb(player1Bombs.get(0).getXLoc()/50, player1Bombs.get(0).getYLoc()/50 + explosionRadius - i);
								player1Bombs.get(0).setDown(true);

								//Down
							}
						}
					}
					else {
						board.resetPlace(player1Bombs.get(0).getXLoc()/50, player1Bombs.get(0).getYLoc()/50);
						board.removeBreakableSpot(player1Bombs.get(0).getXLoc()/50, player1Bombs.get(0).getYLoc()/50);

						for(int i = 0; i < explosionRadius; i++) {
							if(player1Bombs.get(0).getRight()) { //right
								board.resetPlace(player1Bombs.get(0).getXLoc()/50 + explosionRadius - i, player1Bombs.get(0).getYLoc()/50);
								board.removeBreakableSpot(player1Bombs.get(0).getXLoc()/50 + explosionRadius - i, player1Bombs.get(0).getYLoc()/50);

							}else {
								player1Bombs.get(0).setRight(false);

							}

							if(player1Bombs.get(0).getLeft()) { //left
								board.resetPlace(player1Bombs.get(0).getXLoc()/50 - explosionRadius + i, player1Bombs.get(0).getYLoc()/50);
								board.removeBreakableSpot(player1Bombs.get(0).getXLoc()/50 - explosionRadius + i, player1Bombs.get(0).getYLoc()/50);

							}
							else {
								player1Bombs.get(0).setLeft(false);
							}
							if(player1Bombs.get(0).getUp()) { //up
								board.resetPlace(player1Bombs.get(0).getXLoc()/50, player1Bombs.get(0).getYLoc()/50 - explosionRadius + i);
								board.removeBreakableSpot(player1Bombs.get(0).getXLoc()/50, player1Bombs.get(0).getYLoc()/50 - explosionRadius + i);

							}
							else {
								player1Bombs.get(0).setUp(false);

							}
							if(player1Bombs.get(0).getDown()) { //down
								board.resetPlace(player1Bombs.get(0).getXLoc()/50, player1Bombs.get(0).getYLoc()/50 + explosionRadius - i);
								board.removeBreakableSpot(player1Bombs.get(0).getXLoc()/50, player1Bombs.get(0).getYLoc()/50 + explosionRadius - i);

							}
							else {
								player1Bombs.get(0).setDown(false);

							}

						}	
						player1Bombs.remove(0);
						bomberman1.changeNumBombs(-1);
						board.player1BombIsExploded(false);
					}

				}
			}

			//PLAYER 2 BOMB STUFF
			if(player2Bombs.size() > 0) {

				int explosionRadius = bomberman2.getRadius();

				if(player2Bombs.get(0).countDown()) {
					if(!player2Bombs.get(0).getStatus()) {
						board.player2BombIsExploded(true);
						player2Bombs.get(0).explode();

						for(int i = 0; i < explosionRadius; i++) {
							if(!board.getUnbreakableStatus(player2Bombs.get(0).getXLoc()/50 + explosionRadius - i, player2Bombs.get(0).getYLoc()/50)) {
								board.addP2Bomb(player2Bombs.get(0).getXLoc()/50 + explosionRadius - i, player2Bombs.get(0).getYLoc()/50);
								player2Bombs.get(0).setRight(true);
								//RIGHT
							}
							if(!board.getUnbreakableStatus(player2Bombs.get(0).getXLoc()/50 - explosionRadius + i, player2Bombs.get(0).getYLoc()/50)) {
								board.addP2Bomb(player2Bombs.get(0).getXLoc()/50 - explosionRadius + i, player2Bombs.get(0).getYLoc()/50);
								player2Bombs.get(0).setLeft(true);
								//Left
							}

							if(!board.getUnbreakableStatus(player2Bombs.get(0).getXLoc()/50, player2Bombs.get(0).getYLoc()/50 - explosionRadius + i)) {
								board.addP2Bomb(player2Bombs.get(0).getXLoc()/50, player2Bombs.get(0).getYLoc()/50 - explosionRadius + i);
								player2Bombs.get(0).setUp(true);
								//UP
							}


							if(!board.getUnbreakableStatus(player2Bombs.get(0).getXLoc()/50, player2Bombs.get(0).getYLoc()/50 + explosionRadius - i)) {
								board.addP2Bomb(player2Bombs.get(0).getXLoc()/50, player2Bombs.get(0).getYLoc()/50 + explosionRadius - i);
								player2Bombs.get(0).setDown(true);
								//Down
							}
						}


					

				}
			
			else {
				board.resetPlace(player2Bombs.get(0).getXLoc()/50, player2Bombs.get(0).getYLoc()/50);
				board.removeBreakableSpot(player2Bombs.get(0).getXLoc()/50, player2Bombs.get(0).getYLoc()/50);

				for(int i = 0; i < explosionRadius; i++) {
					if(player2Bombs.get(0).getRight()) { //right
						board.resetPlace(player2Bombs.get(0).getXLoc()/50 + explosionRadius - i, player2Bombs.get(0).getYLoc()/50);
						board.removeBreakableSpot(player2Bombs.get(0).getXLoc()/50 + explosionRadius - i, player2Bombs.get(0).getYLoc()/50);


					}else {
						player2Bombs.get(0).setRight(false);

					}

					if(player2Bombs.get(0).getLeft()) { //left
						board.resetPlace(player2Bombs.get(0).getXLoc()/50 - explosionRadius + i, player2Bombs.get(0).getYLoc()/50);
						board.removeBreakableSpot(player2Bombs.get(0).getXLoc()/50 - explosionRadius + i, player2Bombs.get(0).getYLoc()/50);
						

						
					}
					else {
						player2Bombs.get(0).setLeft(false);
					}
					if(player2Bombs.get(0).getUp()) { //up
						board.resetPlace(player2Bombs.get(0).getXLoc()/50, player2Bombs.get(0).getYLoc()/50 - explosionRadius + i);
						board.removeBreakableSpot(player2Bombs.get(0).getXLoc()/50, player2Bombs.get(0).getYLoc()/50 - explosionRadius + i);

					}
					else {
						player2Bombs.get(0).setUp(false);

					}
					if(player2Bombs.get(0).getDown()) { //down
						board.resetPlace(player2Bombs.get(0).getXLoc()/50, player2Bombs.get(0).getYLoc()/50 + explosionRadius - i);
						board.removeBreakableSpot(player2Bombs.get(0).getXLoc()/50, player2Bombs.get(0).getYLoc()/50 + explosionRadius - i);

					}
					else {
						player2Bombs.get(0).setDown(false);

					}

				}	
				player2Bombs.remove(0);
				bomberman2.changeNumBombs(-1);
				board.player2BombIsExploded(false);
			}

		}
	}










	// modifying stuff (KEYOBOARD)
	//player 1
	if (isPressed(KeyEvent.VK_A)) {
		bomberman1.setImage(assets.get(2));
		int[] newLoc = bomberman1.pixeltoGrid();
		if(board.getUnbreakableStatus(newLoc[0] - 1, newLoc[1]) || board.getBreakableStatus(newLoc[0] - 1, newLoc[1]) ) {
			int[] pixelWallLoc = bomberman1.gridToPixel(newLoc[0] - 1, newLoc[1]);
			if(bomberman1.getXLoc() - pixelWallLoc[0] >= 50) 
				bomberman1.walkX(-1*bomberman1.getSpeed());

		}
		else {
			bomberman1.walkX(-1*bomberman1.getSpeed());

		}

	}
	if (isPressed(KeyEvent.VK_D)) {
		bomberman1.setImage(assets.get(3));
		int[] newLoc = bomberman1.pixeltoGrid();
		if(board.getUnbreakableStatus(newLoc[0] + 1, newLoc[1]) || board.getBreakableStatus(newLoc[0] + 1, newLoc[1]) ) {
			int[] pixelWallLoc = bomberman1.gridToPixel(newLoc[0] + 1, newLoc[1]);
			if(pixelWallLoc[0] - bomberman1.getXLoc() >= 30) 
				bomberman1.walkX(1*bomberman1.getSpeed());

		}
		else {
			bomberman1.walkX(1*bomberman1.getSpeed());

		}

	}
	if (isPressed(KeyEvent.VK_W)) {
		bomberman1.setImage(assets.get(1));
		int[] newLoc = bomberman1.pixeltoGrid();
		if(board.getUnbreakableStatus(newLoc[0], newLoc[1] - 1) || board.getBreakableStatus(newLoc[0], newLoc[1] - 1)) {
			int[] pixelWallLoc = bomberman1.gridToPixel(newLoc[0], newLoc[1] - 1);
			if(bomberman1.getYLoc() - pixelWallLoc[1]  >= 45) 
				bomberman1.walkY(-1*bomberman1.getSpeed());

		}
		else {
			bomberman1.walkY(-1*bomberman1.getSpeed());

		}

	}
	if (isPressed(KeyEvent.VK_S)) {
		bomberman1.setImage(assets.get(0));
		int[] newLoc = bomberman1.pixeltoGrid();
		if(board.getUnbreakableStatus(newLoc[0], newLoc[1] + 1) || board.getBreakableStatus(newLoc[0], newLoc[1] + 1)) {
			int[] pixelWallLoc = bomberman1.gridToPixel(newLoc[0], newLoc[1] + 1);
			if(pixelWallLoc[1] - bomberman1.getYLoc()  >= 45) 
				bomberman1.walkY(1*bomberman1.getSpeed());

		}
		else {
			bomberman1.walkY(1*bomberman1.getSpeed());

		}

	}
	if (isPressed(KeyEvent.VK_SPACE)) { //BOMB STUFF
		int[] bombLoc = bomberman1.dropBomb();
		if(bombLoc != null) {
			Bomb b1 = new Bomb(bomb,bombLoc[0] * 50, bombLoc[1] * 50);
			board.addP1Bomb(bombLoc[0], bombLoc[1]);
			player1Bombs.add(b1);
			board.setBreakableSpot(bombLoc[0], bombLoc[1]);
		}

	}
	//player 2
	if (isPressed(KeyEvent.VK_LEFT)) {
		bomberman2.setImage(assets.get(2));
		int[] newLoc = bomberman2.pixeltoGrid();
		if(board.getUnbreakableStatus(newLoc[0] - 1, newLoc[1]) || board.getBreakableStatus(newLoc[0] - 1, newLoc[1])) {
			int[] pixelWallLoc = bomberman2.gridToPixel(newLoc[0] - 1, newLoc[1]);
			if(bomberman2.getXLoc() - pixelWallLoc[0] >= 50) 
				bomberman2.walkX(-1*bomberman2.getSpeed());

		}
		else {
			bomberman2.walkX(-1*bomberman2.getSpeed());

		}

	}
	if (isPressed(KeyEvent.VK_RIGHT)) {
		bomberman2.setImage(assets.get(3));
		int[] newLoc = bomberman2.pixeltoGrid();
		if(board.getUnbreakableStatus(newLoc[0] + 1, newLoc[1]) || board.getBreakableStatus(newLoc[0] + 1, newLoc[1])) {
			int[] pixelWallLoc = bomberman2.gridToPixel(newLoc[0] + 1, newLoc[1]);
			if(pixelWallLoc[0] - bomberman2.getXLoc() >= 30) 
				bomberman2.walkX(1*bomberman2.getSpeed());

		}
		else {
			bomberman2.walkX(1*bomberman2.getSpeed());

		}

	}
	if (isPressed(KeyEvent.VK_UP)) {
		bomberman2.setImage(assets.get(1));
		int[] newLoc = bomberman2.pixeltoGrid();
		if(board.getUnbreakableStatus(newLoc[0], newLoc[1] - 1) || board.getBreakableStatus(newLoc[0], newLoc[1] - 1)) {
			int[] pixelWallLoc = bomberman2.gridToPixel(newLoc[0], newLoc[1] - 1);
			if(bomberman2.getYLoc() - pixelWallLoc[1]  >= 45) 
				bomberman2.walkY(-1*bomberman2.getSpeed());

		}
		else {
			bomberman2.walkY(-1*bomberman2.getSpeed());

		}


	}
	if (isPressed(KeyEvent.VK_DOWN)) {
		bomberman2.setImage(assets.get(0));
		int[] newLoc = bomberman2.pixeltoGrid();
		if(board.getUnbreakableStatus(newLoc[0], newLoc[1] + 1) || board.getBreakableStatus(newLoc[0], newLoc[1] + 1)) {
			int[] pixelWallLoc = bomberman2.gridToPixel(newLoc[0], newLoc[1] + 1);
			if(pixelWallLoc[1] - bomberman2.getYLoc()  >= 45) 
				bomberman2.walkY(1*bomberman2.getSpeed());

		}
		else {
			bomberman2.walkY(1*bomberman2.getSpeed());

		}

	}
	if (isPressed(KeyEvent.VK_ENTER)) {
		int[] bombLoc = bomberman2.dropBomb();
		if(bombLoc != null) {
			Bomb b1 = new Bomb(bomb,bombLoc[0] * 50, bombLoc[1] * 50);
			board.addP2Bomb(bombLoc[0], bombLoc[1]);
			player2Bombs.add(b1);
			board.setBreakableSpot(bombLoc[0], bombLoc[1]);

		}
	}}
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
		level1 = true;
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
