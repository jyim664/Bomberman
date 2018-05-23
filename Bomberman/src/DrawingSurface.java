import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class draws the main menu and the game screen.
 * 
 * @author Justin Yim, Kush Patel, Johnry Zhao
 *
 */
public class DrawingSurface extends PApplet {

	private MenuScreen menu;
	private Levels board;
	private boolean winner;

	private Player bomberman1;
	private Player bomberman2;
	private Bot bot1;
	private Bot bot2;

	private PImage boundaryWall;
	private PImage breakableWall;
	private PImage grassTile;
	private PImage bomb;
	private PImage explosion;
	private PImage health;
	private PImage speed;

	private PImage p1Win;
	private PImage p2Win;
	
	private ArrayList<PImage> assets; // all of Bomberman's images
	private ArrayList<Integer> keys;

	private boolean level1; // false == menu, true == game
	private boolean level2; // false == menu, true == game
	private boolean level3; // false == menu, true == game
	
	
	private boolean gameState;


	

	private ArrayList<Bomb> player1Bombs = new ArrayList<Bomb>();
	private ArrayList<Bomb> player2Bombs = new ArrayList<Bomb>();
	private ArrayList<Bomb> bot1Bombs = new ArrayList<Bomb>();
	private ArrayList<Bomb> bot2Bombs = new ArrayList<Bomb>();

	private static final int frameHeight = 800, frameWidth = 800;

	public DrawingSurface() {
		menu = new MenuScreen("B    mberman");
		board = new Levels();
		level1 = false; // menu = false, game screen = true;
		level2 = false; // menu = false, game screen = true;
		level3 = false; // menu = false, game screen = true;
		gameState = false;
		assets = new ArrayList<PImage>();
		keys = new ArrayList<Integer>();

		winner = false;

	}

	public void spawnPlayer1() {
		bomberman1 = new Player(assets.get(0), 2 * 50, 2 * 50); // FOR LEVEL 1
	}

	public void spawnPlayer2() {
		bomberman2 = new Player(assets.get(0), 13 * 50, 2 * 50); // FOR LEVEL 1
	}

	public void spawnBot1() {
		bot1 = new Bot(1, 3, 2 * 50, 13 * 50, assets.get(0), board);

	}

	public void spawnBot2() {
		bot2 = new Bot(1, 3, 13 * 50, 13 * 50, assets.get(0), board);

	}

	// The statements in the setup() function
	// execute once when the program begins
	public void setup() {
		assets.add(loadImage("bombermanFront.png")); // 0 = front
		assets.add(loadImage("bombermanBack.png")); // 1 = back
		assets.add(loadImage("bombermanLeft.png")); // 2 = left
		assets.add(loadImage("bombermanRight.png")); // 3 = right

		boundaryWall = loadImage("UnbreakableStoneTile.png");
		breakableWall = loadImage("BreakableStoneTile.png");
		grassTile = loadImage("GrassTile.png");

		bomb = loadImage("bomb.png");
		explosion = loadImage("explode.png");
		health = loadImage("heart.png");
		speed = loadImage("speed.png");
		p1Win = loadImage("p1win.png");
		p2Win = loadImage("p2win.png");
		menu.setup(this);
		board.setup(this);
		spawnPlayer1();
		spawnPlayer2();

		spawnBot1();
		spawnBot2();


	}

	// The statements in draw() are executed until the
	// program is stopped. Each statement is executed in
	// sequence and after the last line is read, the first
	// line is executed again.
	public void draw() {

		
		this.scale((float) width / frameWidth, (float) height / frameHeight);// scales objects based on framesize
		if (!gameState) {
			menu.draw(this);
			menu.drawInsPage(this);
			menu.drawMapPage(this);
		}
		
		if(winner) {
		
			String whoWon = "";
			fill(255);
			rect(0,0,800,800);
			if(bomberman1.getLives() == 0) {
				image(p2Win,175,175,450,450);
			}
			if(bomberman2.getLives() == 0) {
				image(p1Win,150, 100,500,500);
			}
			textSize(35);
			
		}
		if (gameState && !winner) {
			
			if ((bomberman1.getLives() == 0 || bomberman2.getLives() == 0) ) {
				winner = true;
				System.out.println("GAME END");
				
				
			}
			board.draw(this, 0, 0, Main.width, Main.height, boundaryWall, breakableWall, grassTile, bomb, explosion, health,speed);
			
			//HPS
			textSize(35);
			text(bomberman1.getLives(), bomberman1.getXLoc() + 5, bomberman1.getYLoc() - 5); //player1

			if (bomberman1.getLives() > 0)
				bomberman1.draw(this);

			if (bomberman2.getLives() > 0)
				bomberman2.draw(this);
			
			if(bot1.getLives()>0) 
				bot1.draw(this);
			
			if(bot2.getLives()>0) 
				bot2.draw(this);
			
			bot1.RandomMovements(board);
			bot2.RandomMovements(board);

			if(bot1.placeOneMore() && bot1.getLives() > 0) { //Bot 1 bomb placing timing
				int[] a = bot1.dropBomb();
				if(a != null) {
				board.addBot1Bomb(a[0], a[1]);
				Bomb b1 = new Bomb(bomb, a[0] * 50, a[1] * 50);
				bot1Bombs.add(b1);
				}
				
			}
			
			if(bot2.placeOneMore() && bot2.getLives() > 0) { //Bot 1 bomb placing timing
				int[] a = bot2.dropBomb();
				if(a != null) {
				board.addBot2Bomb(a[0], a[1]);
				Bomb b1 = new Bomb(bomb, a[0] * 50, a[1] * 50);
				bot2Bombs.add(b1);
				}
				
			}
		
			
			
			//BOT 1 BOMB STUFF
			if (bot1Bombs.size() > 0) {

				int explosionRadius = bot1.getRadius();

				if (bot1Bombs.get(0).countDown()) {
					if (!bot1Bombs.get(0).getStatus()) {
						board.bot1BombIsExploded(true);
						bot1Bombs.get(0).explode();

						int[] bomberman1Loc = new int[2];
						int[] bomberman2Loc = new int[2];
						int[] bot1Loc = new int[2];
						int[] bot2Loc = new int[2];

						bomberman1Loc = bomberman1.pixeltoGrid();
						bomberman2Loc = bomberman2.pixeltoGrid();
						bot1Loc = bot1.pixeltoGrid();
						bot2Loc = bot2.pixeltoGrid();



						if (bomberman1Loc[0] == bot1Bombs.get(0).getXLoc() / 50 
								&& bomberman1Loc[1] == bot1Bombs.get(0).getYLoc() / 50) {
							bomberman1.loseLife();

						}
						if (bomberman2Loc[0] == bot1Bombs.get(0).getXLoc() / 50 
								&& bomberman2Loc[1] == bot1Bombs.get(0).getYLoc() / 50) {
							bomberman2.loseLife();

						}
						if (bot1Loc[0] == bot1Bombs.get(0).getXLoc() / 50 
								&& bot1Loc[1] == bot1Bombs.get(0).getYLoc() / 50) {
							bot1.loseLife();

						}
						if (bot2Loc[0] == bot1Bombs.get(0).getXLoc() / 50 
								&& bot2Loc[1] == bot1Bombs.get(0).getYLoc() / 50) {
							bot2.loseLife();

						}



						for (int i = 0; i < explosionRadius; i++) {

							if (!board.getUnbreakableStatus(bot1Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
									bot1Bombs.get(0).getYLoc() / 50)) {
								board.addBot1Bomb(bot1Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
										bot1Bombs.get(0).getYLoc() / 50);
								bot1Bombs.get(0).setRight(true);
								if (bomberman1Loc[0] == bot1Bombs.get(0).getXLoc() / 50 + explosionRadius - i
										&& bomberman1Loc[1] == bot1Bombs.get(0).getYLoc() / 50) {
									bomberman1.loseLife();

								}
								if (bomberman2Loc[0] == bot1Bombs.get(0).getXLoc() / 50 + explosionRadius - i
										&& bomberman2Loc[1] == bot1Bombs.get(0).getYLoc() / 50) {
									bomberman2.loseLife();

								}
								if (bot1Loc[0] == bot1Bombs.get(0).getXLoc() / 50 + explosionRadius - i
										&& bot1Loc[1] == bot1Bombs.get(0).getYLoc() / 50) {
									bot1.loseLife();

								}
								if (bot2Loc[0] == bot1Bombs.get(0).getXLoc() / 50 + explosionRadius - i
										&& bot2Loc[1] == bot1Bombs.get(0).getYLoc() / 50) {
									bot2.loseLife();

								}
								// RIGHT
							}
							if (!board.getUnbreakableStatus(bot1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
									bot1Bombs.get(0).getYLoc() / 50) ) {
								board.addBot1Bomb(bot1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
										bot1Bombs.get(0).getYLoc() / 50);
								bot1Bombs.get(0).setLeft(true);
								if (bomberman1Loc[0] == bot1Bombs.get(0).getXLoc() / 50 - explosionRadius + i
										&& bomberman1Loc[1] == bot1Bombs.get(0).getYLoc() / 50) {
									bomberman1.loseLife();

								}
								if (bomberman2Loc[0] == bot1Bombs.get(0).getXLoc() / 50 - explosionRadius + i
										&& bomberman2Loc[1] == bot1Bombs.get(0).getYLoc() / 50) {
									bomberman2.loseLife();

								}

								if (bot1Loc[0] == bot1Bombs.get(0).getXLoc() / 50 - explosionRadius + i
										&& bot1Loc[1] == bot1Bombs.get(0).getYLoc() / 50) {
									bot1.loseLife();

								}
								if (bot2Loc[0] == bot1Bombs.get(0).getXLoc() / 50 - explosionRadius + i
										&& bot2Loc[1] == bot1Bombs.get(0).getYLoc() / 50) {
									bot2.loseLife();

								}
								// Left
							}

							if (!board.getUnbreakableStatus(bot1Bombs.get(0).getXLoc() / 50,
									bot1Bombs.get(0).getYLoc() / 50 - explosionRadius + i)) {
								board.addBot1Bomb(bot1Bombs.get(0).getXLoc() / 50,
										bot1Bombs.get(0).getYLoc() / 50 - explosionRadius + i);
								bot1Bombs.get(0).setUp(true);

								if (bomberman1Loc[0] == bot1Bombs.get(0).getXLoc() / 50
										&& bomberman1Loc[1] == bot1Bombs.get(0).getYLoc() / 50 - explosionRadius
										+ i) {
									bomberman1.loseLife();

								}
								if (bomberman2Loc[0] == bot1Bombs.get(0).getXLoc() / 50
										&& bomberman2Loc[1] == bot1Bombs.get(0).getYLoc() / 50 - explosionRadius
										+ i) {
									bomberman2.loseLife();

								}
								
								if (bot1Loc[0] == bot1Bombs.get(0).getXLoc() / 50
										&& bot1Loc[1] == bot1Bombs.get(0).getYLoc() / 50 - explosionRadius
										+ i) {
									bot1.loseLife();

								}
								if (bot2Loc[0] == bot1Bombs.get(0).getXLoc() / 50
										&& bot2Loc[1] == bot1Bombs.get(0).getYLoc() / 50 - explosionRadius
										+ i) {
									bot2.loseLife();

								}

								// UP
							}

							if (!board.getUnbreakableStatus(bot1Bombs.get(0).getXLoc() / 50,
									bot1Bombs.get(0).getYLoc() / 50 + explosionRadius - i) ) {
								board.addBot1Bomb(bot1Bombs.get(0).getXLoc() / 50,
										bot1Bombs.get(0).getYLoc() / 50 + explosionRadius - i);
								bot1Bombs.get(0).setDown(true);

								if (bomberman1Loc[0] == bot1Bombs.get(0).getXLoc() / 50
										&& bomberman1Loc[1] == bot1Bombs.get(0).getYLoc() / 50 + explosionRadius
										- i) {
									bomberman1.loseLife();

								}
								if (bomberman2Loc[0] == bot1Bombs.get(0).getXLoc() / 50
										&& bomberman2Loc[1] == bot1Bombs.get(0).getYLoc() / 50 + explosionRadius
										- i) {
									bomberman2.loseLife();

								}
								if (bot1Loc[0] == bot1Bombs.get(0).getXLoc() / 50
										&& bot1Loc[1] == bot1Bombs.get(0).getYLoc() / 50 + explosionRadius
										- i) {
									bot1.loseLife();

								}
								if (bot2Loc[0] == bot1Bombs.get(0).getXLoc() / 50
										&& bot2Loc[1] == bot1Bombs.get(0).getYLoc() / 50 + explosionRadius
										- i) {
									bot2.loseLife();

								}

								// Down
							}
						}
					} else {

						
						if(board.getItems(bot1Bombs.get(0).getXLoc() / 50, bot1Bombs.get(0).getYLoc() / 50) != 0) {
							board.resetPlaceToItems(bot1Bombs.get(0).getXLoc() / 50, bot1Bombs.get(0).getYLoc() / 50);
						}
						else {
							board.resetPlaceToGrass(bot1Bombs.get(0).getXLoc() / 50, bot1Bombs.get(0).getYLoc() / 50);
						}
						
						board.removeBreakableSpot(bot1Bombs.get(0).getXLoc() / 50,
								bot1Bombs.get(0).getYLoc() / 50);

						for (int i = 0; i < explosionRadius; i++) {
							if (bot1Bombs.get(0).getRight()) { // right

								if(board.getItems(bot1Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
										bot1Bombs.get(0).getYLoc() / 50) != 0) {
									board.resetPlaceToItems(bot1Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
											bot1Bombs.get(0).getYLoc() / 50);
								}
								else {
									board.resetPlaceToGrass(bot1Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
											bot1Bombs.get(0).getYLoc() / 50);
								}
								
								board.removeBreakableSpot(bot1Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
										bot1Bombs.get(0).getYLoc() / 50);

							} else {
								bot1Bombs.get(0).setRight(false);

							}

							if (bot1Bombs.get(0).getLeft() &&(board.getGoodSpots(bot1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
									bot1Bombs.get(0).getYLoc() / 50) || (board.getBreakableStatus(bot1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
											bot1Bombs.get(0).getYLoc() / 50)))) { // left
								
								if(board.getItems(bot1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
										bot1Bombs.get(0).getYLoc() / 50) != 0) {
									board.resetPlaceToItems(bot1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
											bot1Bombs.get(0).getYLoc() / 50);
								}
								else {
									board.resetPlaceToGrass(bot1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
											bot1Bombs.get(0).getYLoc() / 50);
								}
								
								
								board.removeBreakableSpot(bot1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
										bot1Bombs.get(0).getYLoc() / 50);

							} else {
								bot1Bombs.get(0).setLeft(false);
							}
							if (bot1Bombs.get(0).getUp() ) { // up

								if(board.getItems(bot1Bombs.get(0).getXLoc() / 50,
										bot1Bombs.get(0).getYLoc() / 50 - explosionRadius + i) != 0) {
									board.resetPlaceToItems(bot1Bombs.get(0).getXLoc() / 50,
											bot1Bombs.get(0).getYLoc() / 50 - explosionRadius + i);
								}
								else {
									board.resetPlaceToGrass(bot1Bombs.get(0).getXLoc() / 50,
											bot1Bombs.get(0).getYLoc() / 50 - explosionRadius + i);
								}
								board.removeBreakableSpot(bot1Bombs.get(0).getXLoc() / 50,
										bot1Bombs.get(0).getYLoc() / 50 - explosionRadius + i);

							} else {
								bot1Bombs.get(0).setUp(false);

							}
							if (bot1Bombs.get(0).getDown()) { // down

								if(board.getItems(bot1Bombs.get(0).getXLoc() / 50,
										bot1Bombs.get(0).getYLoc() / 50 + explosionRadius - i) != 0) {
									board.resetPlaceToItems(bot1Bombs.get(0).getXLoc() / 50,
											bot1Bombs.get(0).getYLoc() / 50 + explosionRadius - i);
								}
								else {
									board.resetPlaceToGrass(bot1Bombs.get(0).getXLoc() / 50,
											bot1Bombs.get(0).getYLoc() / 50 + explosionRadius - i);
								}
								
								board.removeBreakableSpot(bot1Bombs.get(0).getXLoc() / 50,
										bot1Bombs.get(0).getYLoc() / 50 + explosionRadius - i);

							} else {
								bot1Bombs.get(0).setDown(false);

							}

						}
						bot1Bombs.remove(0);
						bot1.changeNumBombs(-1);
						board.bot1BombIsExploded(false);
					}

				}
			}
		}
		
		
		//BOT 2 BOMB STUFF
		
		if (bot2Bombs.size() > 0) {

			int explosionRadius = bot2.getRadius();

			if (bot2Bombs.get(0).countDown()) {
				if (!bot2Bombs.get(0).getStatus()) {
					board.bot2BombIsExploded(true);
					bot2Bombs.get(0).explode();

					int[] bomberman1Loc = new int[2];
					int[] bomberman2Loc = new int[2];
					int[] bot1Loc = new int[2];
					int[] bot2Loc = new int[2];

					bomberman1Loc = bomberman1.pixeltoGrid();
					bomberman2Loc = bomberman2.pixeltoGrid();
					bot1Loc = bot1.pixeltoGrid();
					bot2Loc = bot2.pixeltoGrid();



					if (bomberman1Loc[0] == bot2Bombs.get(0).getXLoc() / 50 
							&& bomberman1Loc[1] == bot2Bombs.get(0).getYLoc() / 50) {
						bomberman1.loseLife();

					}
					if (bomberman2Loc[0] == bot2Bombs.get(0).getXLoc() / 50 
							&& bomberman2Loc[1] == bot2Bombs.get(0).getYLoc() / 50) {
						bomberman2.loseLife();

					}
					if (bot1Loc[0] == bot2Bombs.get(0).getXLoc() / 50 
							&& bot1Loc[1] == bot2Bombs.get(0).getYLoc() / 50) {
						bot1.loseLife();

					}
					if (bot2Loc[0] == bot2Bombs.get(0).getXLoc() / 50 
							&& bot2Loc[1] == bot2Bombs.get(0).getYLoc() / 50) {
						bot2.loseLife();

					}



					for (int i = 0; i < explosionRadius; i++) {

						if (!board.getUnbreakableStatus(bot2Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
								bot2Bombs.get(0).getYLoc() / 50)) {
							board.addBot2Bomb(bot2Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
									bot2Bombs.get(0).getYLoc() / 50);
							bot2Bombs.get(0).setRight(true);
							if (bomberman1Loc[0] == bot2Bombs.get(0).getXLoc() / 50 + explosionRadius - i
									&& bomberman1Loc[1] == bot2Bombs.get(0).getYLoc() / 50) {
								bomberman1.loseLife();

							}
							if (bomberman2Loc[0] == bot2Bombs.get(0).getXLoc() / 50 + explosionRadius - i
									&& bomberman2Loc[1] == bot2Bombs.get(0).getYLoc() / 50) {
								bomberman2.loseLife();

							}
							if (bot1Loc[0] == bot2Bombs.get(0).getXLoc() / 50 + explosionRadius - i
									&& bot1Loc[1] == bot2Bombs.get(0).getYLoc() / 50) {
								bot1.loseLife();

							}
							if (bot2Loc[0] == bot2Bombs.get(0).getXLoc() / 50 + explosionRadius - i
									&& bot2Loc[1] == bot2Bombs.get(0).getYLoc() / 50) {
								bot2.loseLife();

							}
							// RIGHT
						}
						if (!board.getUnbreakableStatus(bot2Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
								bot2Bombs.get(0).getYLoc() / 50)) {
							board.addBot2Bomb(bot2Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
									bot2Bombs.get(0).getYLoc() / 50);
							bot2Bombs.get(0).setLeft(true);
							if (bomberman1Loc[0] == bot2Bombs.get(0).getXLoc() / 50 - explosionRadius + i
									&& bomberman1Loc[1] == bot2Bombs.get(0).getYLoc() / 50) {
								bomberman1.loseLife();

							}
							if (bomberman2Loc[0] == bot2Bombs.get(0).getXLoc() / 50 - explosionRadius + i
									&& bomberman2Loc[1] == bot2Bombs.get(0).getYLoc() / 50) {
								bomberman2.loseLife();

							}

							if (bot1Loc[0] == bot2Bombs.get(0).getXLoc() / 50 - explosionRadius + i
									&& bot1Loc[1] == bot2Bombs.get(0).getYLoc() / 50) {
								bot1.loseLife();

							}
							if (bot2Loc[0] == bot2Bombs.get(0).getXLoc() / 50 - explosionRadius + i
									&& bot2Loc[1] == bot2Bombs.get(0).getYLoc() / 50) {
								bot2.loseLife();

							}
							// Left
						}

						if (!board.getUnbreakableStatus(bot2Bombs.get(0).getXLoc() / 50,
								bot2Bombs.get(0).getYLoc() / 50 - explosionRadius + i)) {
							board.addBot2Bomb(bot2Bombs.get(0).getXLoc() / 50,
									bot2Bombs.get(0).getYLoc() / 50 - explosionRadius + i);
							bot2Bombs.get(0).setUp(true);

							if (bomberman1Loc[0] == bot2Bombs.get(0).getXLoc() / 50
									&& bomberman1Loc[1] == bot2Bombs.get(0).getYLoc() / 50 - explosionRadius
									+ i) {
								bomberman1.loseLife();

							}
							if (bomberman2Loc[0] == bot2Bombs.get(0).getXLoc() / 50
									&& bomberman2Loc[1] == bot2Bombs.get(0).getYLoc() / 50 - explosionRadius
									+ i) {
								bomberman2.loseLife();

							}
							
							if (bot1Loc[0] == bot2Bombs.get(0).getXLoc() / 50
									&& bot1Loc[1] == bot2Bombs.get(0).getYLoc() / 50 - explosionRadius
									+ i) {
								bot1.loseLife();

							}
							if (bot2Loc[0] == bot2Bombs.get(0).getXLoc() / 50
									&& bot2Loc[1] == bot2Bombs.get(0).getYLoc() / 50 - explosionRadius
									+ i) {
								bot2.loseLife();

							}

							// UP
						}

						if (!board.getUnbreakableStatus(bot2Bombs.get(0).getXLoc() / 50,
								bot2Bombs.get(0).getYLoc() / 50 + explosionRadius - i)) {
							board.addBot2Bomb(bot2Bombs.get(0).getXLoc() / 50,
									bot2Bombs.get(0).getYLoc() / 50 + explosionRadius - i);
							bot2Bombs.get(0).setDown(true);

							if (bomberman1Loc[0] == bot2Bombs.get(0).getXLoc() / 50
									&& bomberman1Loc[1] == bot2Bombs.get(0).getYLoc() / 50 + explosionRadius
									- i) {
								bomberman1.loseLife();

							}
							if (bomberman2Loc[0] == bot2Bombs.get(0).getXLoc() / 50
									&& bomberman2Loc[1] == bot2Bombs.get(0).getYLoc() / 50 + explosionRadius
									- i) {
								bomberman2.loseLife();

							}
							if (bot1Loc[0] == bot2Bombs.get(0).getXLoc() / 50
									&& bot1Loc[1] == bot2Bombs.get(0).getYLoc() / 50 + explosionRadius
									- i) {
								bot1.loseLife();

							}
							if (bot2Loc[0] == bot2Bombs.get(0).getXLoc() / 50
									&& bot2Loc[1] == bot2Bombs.get(0).getYLoc() / 50 + explosionRadius
									- i) {
								bot2.loseLife();

							}

							// Down
						}
					}
				} else {

					if(board.getItems(bot2Bombs.get(0).getXLoc() / 50, bot2Bombs.get(0).getYLoc() / 50) != 0) {
						board.resetPlaceToItems(bot2Bombs.get(0).getXLoc() / 50, bot2Bombs.get(0).getYLoc() / 50);
					}
					else {
						board.resetPlaceToGrass(bot2Bombs.get(0).getXLoc() / 50, bot2Bombs.get(0).getYLoc() / 50);
					}
					board.removeBreakableSpot(bot2Bombs.get(0).getXLoc() / 50,
							bot2Bombs.get(0).getYLoc() / 50);

					for (int i = 0; i < explosionRadius; i++) {
						if (bot2Bombs.get(0).getRight()) { // right
							
							if(board.getItems(bot2Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
									bot2Bombs.get(0).getYLoc() / 50) != 0) {
								board.resetPlaceToItems(bot2Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
										bot2Bombs.get(0).getYLoc() / 50);
							}
							else {
								board.resetPlaceToGrass(bot2Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
										bot2Bombs.get(0).getYLoc() / 50);
							}

						
							board.removeBreakableSpot(bot2Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
									bot2Bombs.get(0).getYLoc() / 50);

						} else {
							bot2Bombs.get(0).setRight(false);

						}

						if (bot2Bombs.get(0).getLeft()) { // left

							if(board.getItems(bot2Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
									bot2Bombs.get(0).getYLoc() / 50) != 0) {
								board.resetPlaceToItems(bot2Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
										bot2Bombs.get(0).getYLoc() / 50);
							}
							else {
								board.resetPlaceToGrass(bot2Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
										bot2Bombs.get(0).getYLoc() / 50);
							}

							board.removeBreakableSpot(bot2Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
									bot2Bombs.get(0).getYLoc() / 50);

						} else {
							bot2Bombs.get(0).setLeft(false);
						}
						if (bot2Bombs.get(0).getUp()) { // up

							if(board.getItems(bot2Bombs.get(0).getXLoc() / 50,
									bot2Bombs.get(0).getYLoc() / 50 - explosionRadius + i) != 0) {
								board.resetPlaceToItems(bot2Bombs.get(0).getXLoc() / 50,
										bot2Bombs.get(0).getYLoc() / 50 - explosionRadius + i);
							}
							else {
								board.resetPlaceToGrass(bot2Bombs.get(0).getXLoc() / 50,
										bot2Bombs.get(0).getYLoc() / 50 - explosionRadius + i);
							}

						
							board.removeBreakableSpot(bot2Bombs.get(0).getXLoc() / 50,
									bot2Bombs.get(0).getYLoc() / 50 - explosionRadius + i);

						} else {
							bot2Bombs.get(0).setUp(false);

						}
						if (bot2Bombs.get(0).getDown()) { // down

							
							if(board.getItems(bot2Bombs.get(0).getXLoc() / 50,
									bot2Bombs.get(0).getYLoc() / 50 + explosionRadius - i) != 0) {
								board.resetPlaceToItems(bot2Bombs.get(0).getXLoc() / 50,
										bot2Bombs.get(0).getYLoc() / 50 + explosionRadius - i);
							}
							else {
								board.resetPlaceToGrass(bot2Bombs.get(0).getXLoc() / 50,
										bot2Bombs.get(0).getYLoc() / 50 + explosionRadius - i);
							}
						
							board.removeBreakableSpot(bot2Bombs.get(0).getXLoc() / 50,
									bot2Bombs.get(0).getYLoc() / 50 + explosionRadius - i);

						} else {
							bot2Bombs.get(0).setDown(false);

						}

					}
					bot2Bombs.remove(0);
					bot2.changeNumBombs(-1);
					board.bot2BombIsExploded(false);
				}

			}
		}
	

			// PLAYER 1 BOMB STUFF
			if (player1Bombs.size() > 0) {

				int explosionRadius = bomberman1.getRadius();

				if (player1Bombs.get(0).countDown()) {
					if (!player1Bombs.get(0).getStatus()) {
						board.player1BombIsExploded(true);
						player1Bombs.get(0).explode();

						int[] bomberman1Loc = new int[2];
						int[] bomberman2Loc = new int[2];
						int[] bot1Loc = new int[2];
						int[] bot2Loc = new int[2];

						bomberman1Loc = bomberman1.pixeltoGrid();
						bomberman2Loc = bomberman2.pixeltoGrid();
						bot1Loc = bot1.pixeltoGrid();
						bot2Loc = bot2.pixeltoGrid();



						if (bomberman1Loc[0] == player1Bombs.get(0).getXLoc() / 50 
								&& bomberman1Loc[1] == player1Bombs.get(0).getYLoc() / 50) {
							bomberman1.loseLife();

						}
						if (bomberman2Loc[0] == player1Bombs.get(0).getXLoc() / 50 
								&& bomberman2Loc[1] == player1Bombs.get(0).getYLoc() / 50) {
							bomberman2.loseLife();

						}
						if (bot1Loc[0] == player1Bombs.get(0).getXLoc() / 50 
								&& bot1Loc[1] == player1Bombs.get(0).getYLoc() / 50) {
							bot1.loseLife();

						}
						if (bot2Loc[0] == player1Bombs.get(0).getXLoc() / 50 
								&& bot2Loc[1] == player1Bombs.get(0).getYLoc() / 50) {
							bot2.loseLife();

						}



						for (int i = 0; i < explosionRadius; i++) {

							if (!board.getUnbreakableStatus(player1Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
									player1Bombs.get(0).getYLoc() / 50)) {
								board.addP1Bomb(player1Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
										player1Bombs.get(0).getYLoc() / 50);
								player1Bombs.get(0).setRight(true);
								if (bomberman1Loc[0] == player1Bombs.get(0).getXLoc() / 50 + explosionRadius - i
										&& bomberman1Loc[1] == player1Bombs.get(0).getYLoc() / 50) {
									bomberman1.loseLife();

								}
								if (bomberman2Loc[0] == player1Bombs.get(0).getXLoc() / 50 + explosionRadius - i
										&& bomberman2Loc[1] == player1Bombs.get(0).getYLoc() / 50) {
									bomberman2.loseLife();

								}
								if (bot1Loc[0] == player1Bombs.get(0).getXLoc() / 50 + explosionRadius - i
										&& bot1Loc[1] == player1Bombs.get(0).getYLoc() / 50) {
									bot1.loseLife();

								}
								if (bot2Loc[0] == player1Bombs.get(0).getXLoc() / 50 + explosionRadius - i
										&& bot2Loc[1] == player1Bombs.get(0).getYLoc() / 50) {
									bot2.loseLife();

								}
								// RIGHT
							}
							if (!board.getUnbreakableStatus(player1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
									player1Bombs.get(0).getYLoc() / 50)) {
								board.addP1Bomb(player1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
										player1Bombs.get(0).getYLoc() / 50);
								player1Bombs.get(0).setLeft(true);
								if (bomberman1Loc[0] == player1Bombs.get(0).getXLoc() / 50 - explosionRadius + i
										&& bomberman1Loc[1] == player1Bombs.get(0).getYLoc() / 50) {
									bomberman1.loseLife();

								}
								if (bomberman2Loc[0] == player1Bombs.get(0).getXLoc() / 50 - explosionRadius + i
										&& bomberman2Loc[1] == player1Bombs.get(0).getYLoc() / 50) {
									bomberman2.loseLife();

								}

								if (bot1Loc[0] == player1Bombs.get(0).getXLoc() / 50 - explosionRadius + i
										&& bot1Loc[1] == player1Bombs.get(0).getYLoc() / 50) {
									bot1.loseLife();

								}
								if (bot2Loc[0] == player1Bombs.get(0).getXLoc() / 50 - explosionRadius + i
										&& bot2Loc[1] == player1Bombs.get(0).getYLoc() / 50) {
									bot2.loseLife();

								}
								// Left
							}

							if (!board.getUnbreakableStatus(player1Bombs.get(0).getXLoc() / 50,
									player1Bombs.get(0).getYLoc() / 50 - explosionRadius + i)) {
								board.addP1Bomb(player1Bombs.get(0).getXLoc() / 50,
										player1Bombs.get(0).getYLoc() / 50 - explosionRadius + i);
								player1Bombs.get(0).setUp(true);

								if (bomberman1Loc[0] == player1Bombs.get(0).getXLoc() / 50
										&& bomberman1Loc[1] == player1Bombs.get(0).getYLoc() / 50 - explosionRadius
										+ i) {
									bomberman1.loseLife();

								}
								if (bomberman2Loc[0] == player1Bombs.get(0).getXLoc() / 50
										&& bomberman2Loc[1] == player1Bombs.get(0).getYLoc() / 50 - explosionRadius
										+ i) {
									bomberman2.loseLife();

								}
								
								if (bot1Loc[0] == player1Bombs.get(0).getXLoc() / 50
										&& bot1Loc[1] == player1Bombs.get(0).getYLoc() / 50 - explosionRadius
										+ i) {
									bot1.loseLife();

								}
								if (bot2Loc[0] == player1Bombs.get(0).getXLoc() / 50
										&& bot2Loc[1] == player1Bombs.get(0).getYLoc() / 50 - explosionRadius
										+ i) {
									bot2.loseLife();

								}

								// UP
							}

							if (!board.getUnbreakableStatus(player1Bombs.get(0).getXLoc() / 50,
									player1Bombs.get(0).getYLoc() / 50 + explosionRadius - i)) {
								board.addP1Bomb(player1Bombs.get(0).getXLoc() / 50,
										player1Bombs.get(0).getYLoc() / 50 + explosionRadius - i);
								player1Bombs.get(0).setDown(true);

								if (bomberman1Loc[0] == player1Bombs.get(0).getXLoc() / 50
										&& bomberman1Loc[1] == player1Bombs.get(0).getYLoc() / 50 + explosionRadius
										- i) {
									bomberman1.loseLife();

								}
								if (bomberman2Loc[0] == player1Bombs.get(0).getXLoc() / 50
										&& bomberman2Loc[1] == player1Bombs.get(0).getYLoc() / 50 + explosionRadius
										- i) {
									bomberman2.loseLife();

								}
								if (bot1Loc[0] == player1Bombs.get(0).getXLoc() / 50
										&& bot1Loc[1] == player1Bombs.get(0).getYLoc() / 50 + explosionRadius
										- i) {
									bot1.loseLife();

								}
								if (bot2Loc[0] == player1Bombs.get(0).getXLoc() / 50
										&& bot2Loc[1] == player1Bombs.get(0).getYLoc() / 50 + explosionRadius
										- i) {
									bot2.loseLife();

								}

								// Down
							}
						}
					} else {

						if(board.getItems(player1Bombs.get(0).getXLoc() / 50, player1Bombs.get(0).getYLoc() / 50) != 0) {
							board.resetPlaceToItems(player1Bombs.get(0).getXLoc() / 50, player1Bombs.get(0).getYLoc() / 50);
						}
						else {
							board.resetPlaceToGrass(player1Bombs.get(0).getXLoc() / 50, player1Bombs.get(0).getYLoc() / 50);
						}
//						board.resetPlaceToGrass(player1Bombs.get(0).getXLoc() / 50, player1Bombs.get(0).getYLoc() / 50);
						board.removeBreakableSpot(player1Bombs.get(0).getXLoc() / 50,
								player1Bombs.get(0).getYLoc() / 50);

						for (int i = 0; i < explosionRadius; i++) {
							if (player1Bombs.get(0).getRight()) { // right

								if(board.getItems(player1Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
										player1Bombs.get(0).getYLoc() / 50) != 0 ) {
									System.out.println("YES");
									board.resetPlaceToItems(player1Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
											player1Bombs.get(0).getYLoc() / 50);
								}
								else {
									board.resetPlaceToGrass(player1Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
											player1Bombs.get(0).getYLoc() / 50);
								}
							
								board.removeBreakableSpot(player1Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
										player1Bombs.get(0).getYLoc() / 50);

							} else {
								player1Bombs.get(0).setRight(false);

							}

							if (player1Bombs.get(0).getLeft()) { // left
								
								if(board.getItems(player1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
										player1Bombs.get(0).getYLoc() / 50) != 0) {
									System.out.println("YES");
									board.resetPlaceToItems(player1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
											player1Bombs.get(0).getYLoc() / 50);
								}
								else {
									board.resetPlaceToGrass(player1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
											player1Bombs.get(0).getYLoc() / 50);
								}
								
								board.removeBreakableSpot(player1Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
										player1Bombs.get(0).getYLoc() / 50);

							} else {
								player1Bombs.get(0).setLeft(false);
							}
							if (player1Bombs.get(0).getUp()) { // up

								if(board.getItems(player1Bombs.get(0).getXLoc() / 50,
										player1Bombs.get(0).getYLoc() / 50 - explosionRadius + i) != 0) {
									System.out.println("YES");
									board.resetPlaceToItems(player1Bombs.get(0).getXLoc() / 50,
											player1Bombs.get(0).getYLoc() / 50 - explosionRadius + i);
								}
								else {
									board.resetPlaceToGrass(player1Bombs.get(0).getXLoc() / 50,
											player1Bombs.get(0).getYLoc() / 50 - explosionRadius + i);
								}
								
								board.removeBreakableSpot(player1Bombs.get(0).getXLoc() / 50,
										player1Bombs.get(0).getYLoc() / 50 - explosionRadius + i);

							} else {
								player1Bombs.get(0).setUp(false);

							}
							if (player1Bombs.get(0).getDown()) { // down
								if(board.getItems(player1Bombs.get(0).getXLoc() / 50,
										player1Bombs.get(0).getYLoc() / 50 + explosionRadius - i) != 0) {
									System.out.println("YES");
									board.resetPlaceToItems(player1Bombs.get(0).getXLoc() / 50,
											player1Bombs.get(0).getYLoc() / 50 + explosionRadius - i);
								}
								else {
									board.resetPlaceToGrass(player1Bombs.get(0).getXLoc() / 50,
											player1Bombs.get(0).getYLoc() / 50 + explosionRadius - i);
								}
								
								board.removeBreakableSpot(player1Bombs.get(0).getXLoc() / 50,
										player1Bombs.get(0).getYLoc() / 50 + explosionRadius - i);

							} else {
								player1Bombs.get(0).setDown(false);

							}

						}
						player1Bombs.remove(0);
						bomberman1.changeNumBombs(-1);
						board.player1BombIsExploded(false);
					}

				}
			}
		

		// PLAYER 2 BOMB STUFF
		if (player2Bombs.size() > 0) {

			int explosionRadius = bomberman2.getRadius();

			if (player2Bombs.get(0).countDown()) {
				if (!player2Bombs.get(0).getStatus()) {
					board.player2BombIsExploded(true);
					player2Bombs.get(0).explode();

					int[] bomberman1Loc = new int[2];
					int[] bomberman2Loc = new int[2];
					int[] bot1Loc = new int[2];
					int[] bot2Loc = new int[2];


					bomberman1Loc = bomberman1.pixeltoGrid();
					bomberman2Loc = bomberman2.pixeltoGrid();
					bot1Loc = bot1.pixeltoGrid();
					bot2Loc = bot2.pixeltoGrid();

					if (bomberman1Loc[0] == player2Bombs.get(0).getXLoc() / 50
							&& bomberman1Loc[1] == player2Bombs.get(0).getYLoc() / 50) {
						bomberman1.loseLife();
					}
					if (bomberman2Loc[0] == player2Bombs.get(0).getXLoc() / 50
							&& bomberman2Loc[1] == player2Bombs.get(0).getYLoc() / 50) {
						bomberman2.loseLife();
					}
					if (bot1Loc[0] == player2Bombs.get(0).getXLoc() / 50
							&& bot1Loc[1] == player2Bombs.get(0).getYLoc() / 50) {
						bot1.loseLife();
					}
					if (bot2Loc[0] == player2Bombs.get(0).getXLoc() / 50
							&& bot2Loc[1] == player2Bombs.get(0).getYLoc() / 50) {
						bot2.loseLife();

					}

					
					for (int i = 0; i < explosionRadius; i++) {
						if (!board.getUnbreakableStatus(player2Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
								player2Bombs.get(0).getYLoc() / 50)) {
							board.addP2Bomb(player2Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
									player2Bombs.get(0).getYLoc() / 50);
							player2Bombs.get(0).setRight(true);

							if (bomberman1Loc[0] == player2Bombs.get(0).getXLoc() / 50 + explosionRadius - i
									&& bomberman1Loc[1] == player2Bombs.get(0).getYLoc() / 50) {
								bomberman1.loseLife();

							}
							if (bomberman2Loc[0] == player2Bombs.get(0).getXLoc() / 50 + explosionRadius - i
									&& bomberman2Loc[1] == player2Bombs.get(0).getYLoc() / 50) {
								bomberman2.loseLife();
							}
							if (bot1Loc[0] == player2Bombs.get(0).getXLoc() / 50 + explosionRadius - i
									&& bot1Loc[1] == player2Bombs.get(0).getYLoc() / 50) {
								bot1.loseLife();

							}
							if (bot2Loc[0] == player2Bombs.get(0).getXLoc() / 50 + explosionRadius - i
									&& bot2Loc[1] == player2Bombs.get(0).getYLoc() / 50) {
								bot2.loseLife();
							}
							// RIGHT
						}
						if (!board.getUnbreakableStatus(player2Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
								player2Bombs.get(0).getYLoc() / 50)) {
							board.addP2Bomb(player2Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
									player2Bombs.get(0).getYLoc() / 50);
							player2Bombs.get(0).setLeft(true);

							if (bomberman1Loc[0] == player2Bombs.get(0).getXLoc() / 50 - explosionRadius + i
									&& bomberman1Loc[1] == player2Bombs.get(0).getYLoc() / 50) {
								bomberman1.loseLife();

							}
							if (bomberman2Loc[0] == player2Bombs.get(0).getXLoc() / 50 - explosionRadius + i
									&& bomberman2Loc[1] == player2Bombs.get(0).getYLoc() / 50) {
								bomberman2.loseLife();

							}

							if (bot1Loc[0] == player2Bombs.get(0).getXLoc() / 50 - explosionRadius + i
									&& bot1Loc[1] == player2Bombs.get(0).getYLoc() / 50) {
								bot1.loseLife();

							}
							if (bot2Loc[0] == player2Bombs.get(0).getXLoc() / 50 - explosionRadius + i
									&& bot2Loc[1] == player2Bombs.get(0).getYLoc() / 50) {
								bot2.loseLife();

							}
							// Left
						}

						if (!board.getUnbreakableStatus(player2Bombs.get(0).getXLoc() / 50,
								player2Bombs.get(0).getYLoc() / 50 - explosionRadius + i)) {
							board.addP2Bomb(player2Bombs.get(0).getXLoc() / 50,
									player2Bombs.get(0).getYLoc() / 50 - explosionRadius + i);
							player2Bombs.get(0).setUp(true);

							if (bomberman1Loc[0] == player2Bombs.get(0).getXLoc() / 50
									&& bomberman1Loc[1] == player2Bombs.get(0).getYLoc() / 50 - explosionRadius
									+ i) {
								bomberman1.loseLife();

							}
							if (bomberman2Loc[0] == player2Bombs.get(0).getXLoc() / 50
									&& bomberman2Loc[1] == player2Bombs.get(0).getYLoc() / 50 - explosionRadius
									+ i) {
								bomberman2.loseLife();

							}
							if (bot1Loc[0] == player2Bombs.get(0).getXLoc() / 50
									&& bot1Loc[1] == player2Bombs.get(0).getYLoc() / 50 - explosionRadius
									+ i) {
								bot1.loseLife();

							}
							if (bot2Loc[0] == player2Bombs.get(0).getXLoc() / 50
									&& bot2Loc[1] == player2Bombs.get(0).getYLoc() / 50 - explosionRadius
									+ i) {
								bot2.loseLife();

							}
							// UP
						}

						if (!board.getUnbreakableStatus(player2Bombs.get(0).getXLoc() / 50,
								player2Bombs.get(0).getYLoc() / 50 + explosionRadius - i)) {
							board.addP2Bomb(player2Bombs.get(0).getXLoc() / 50,
									player2Bombs.get(0).getYLoc() / 50 + explosionRadius - i);
							player2Bombs.get(0).setDown(true);

							if (bomberman1Loc[0] == player2Bombs.get(0).getXLoc() / 50
									&& bomberman1Loc[1] == player2Bombs.get(0).getYLoc() / 50 + explosionRadius
									- i) {
								bomberman1.loseLife();

							}
							if (bomberman2Loc[0] == player2Bombs.get(0).getXLoc() / 50
									&& bomberman2Loc[1] == player2Bombs.get(0).getYLoc() / 50 + explosionRadius
									- i) {
								bomberman2.loseLife();

							}
							if (bot1Loc[0] == player2Bombs.get(0).getXLoc() / 50
									&& bot1Loc[1] == player2Bombs.get(0).getYLoc() / 50 + explosionRadius
									- i) {
								bot1.loseLife();

							}
							if (bot2Loc[0] == player2Bombs.get(0).getXLoc() / 50
									&& bot2Loc[1] == player2Bombs.get(0).getYLoc() / 50 + explosionRadius
									- i) {
								bot2.loseLife();

							}

							// Down
						}
					}

				}

				else {

					if(board.getItems(player2Bombs.get(0).getXLoc() / 50, player2Bombs.get(0).getYLoc() / 50) != 0) {
						board.resetPlaceToItems(player2Bombs.get(0).getXLoc() / 50, player2Bombs.get(0).getYLoc() / 50);
					}
					else {
						board.resetPlaceToGrass(player2Bombs.get(0).getXLoc() / 50, player2Bombs.get(0).getYLoc() / 50);
					}
					board.removeBreakableSpot(player2Bombs.get(0).getXLoc() / 50,
							player2Bombs.get(0).getYLoc() / 50);

					for (int i = 0; i < explosionRadius; i++) {
						if (player2Bombs.get(0).getRight()) { // right

							if(board.getItems(player2Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
									player2Bombs.get(0).getYLoc() / 50) != 0) {
								board.resetPlaceToItems(player2Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
										player2Bombs.get(0).getYLoc() / 50);
							}
							else {
								board.resetPlaceToGrass(player2Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
										player2Bombs.get(0).getYLoc() / 50);
							}
							board.removeBreakableSpot(player2Bombs.get(0).getXLoc() / 50 + explosionRadius - i,
									player2Bombs.get(0).getYLoc() / 50);

						} else {
							player2Bombs.get(0).setRight(false);

						}

						if (player2Bombs.get(0).getLeft()) { // left

							

								if(board.getItems(player2Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
										player2Bombs.get(0).getYLoc() / 50) != 0) {
									board.resetPlaceToItems(player2Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
											player2Bombs.get(0).getYLoc() / 50);
								}
								else {
									board.resetPlaceToGrass(player2Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
											player2Bombs.get(0).getYLoc() / 50);
								}
							
							board.removeBreakableSpot(player2Bombs.get(0).getXLoc() / 50 - explosionRadius + i,
									player2Bombs.get(0).getYLoc() / 50);

						} else {
							player2Bombs.get(0).setLeft(false);
						}
						if (player2Bombs.get(0).getUp()) { // up

							if(board.getItems(player2Bombs.get(0).getXLoc() / 50,
									player2Bombs.get(0).getYLoc() / 50 - explosionRadius + i) != 0) {
								board.resetPlaceToItems(player2Bombs.get(0).getXLoc() / 50,
										player2Bombs.get(0).getYLoc() / 50 - explosionRadius + i);
							}
							else {
								board.resetPlaceToGrass(player2Bombs.get(0).getXLoc() / 50,
										player2Bombs.get(0).getYLoc() / 50 - explosionRadius + i);
							}
						
							board.removeBreakableSpot(player2Bombs.get(0).getXLoc() / 50,
									player2Bombs.get(0).getYLoc() / 50 - explosionRadius + i);

						} else {
							player2Bombs.get(0).setUp(false);

						}
						if (player2Bombs.get(0).getDown()) { // down

							if(board.getItems(player2Bombs.get(0).getXLoc() / 50,
									player2Bombs.get(0).getYLoc() / 50 + explosionRadius - i) != 0) {
								board.resetPlaceToItems(player2Bombs.get(0).getXLoc() / 50,
										player2Bombs.get(0).getYLoc() / 50 + explosionRadius - i);
							}
							else {
								board.resetPlaceToGrass(player2Bombs.get(0).getXLoc() / 50,
										player2Bombs.get(0).getYLoc() / 50 + explosionRadius - i);
							}
						
						
							board.removeBreakableSpot(player2Bombs.get(0).getXLoc() / 50,
									player2Bombs.get(0).getYLoc() / 50 + explosionRadius - i);

						} else {
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
		// player 1
		boolean p1key = false;
		if(bomberman1.getLives() > 0) {
		if (isPressed(KeyEvent.VK_A) && !p1key) {
			p1key = true;
			bomberman1.setImage(assets.get(2));
			
			int[] newLoc = bomberman1.pixeltoGrid();
			if (board.getUnbreakableStatus(newLoc[0] - 1, newLoc[1])
					|| board.getBreakableStatus(newLoc[0] - 1, newLoc[1])) {
				int[] pixelWallLoc = bomberman1.gridToPixel(newLoc[0] - 1, newLoc[1]);
				if (bomberman1.getXLoc() - pixelWallLoc[0] >= 50)
					bomberman1.walkX(-1 * bomberman1.getSpeed());

			} else {
				bomberman1.walkX(-1 * bomberman1.getSpeed());

			}

		}
		if (isPressed(KeyEvent.VK_D) && !p1key) {
			p1key = true;
			bomberman1.setImage(assets.get(3));
			int[] newLoc = bomberman1.pixeltoGrid();
			if (board.getUnbreakableStatus(newLoc[0] + 1, newLoc[1])
					|| board.getBreakableStatus(newLoc[0] + 1, newLoc[1])) {
				int[] pixelWallLoc = bomberman1.gridToPixel(newLoc[0] + 1, newLoc[1]);
				if (pixelWallLoc[0] - bomberman1.getXLoc() >= 30)
					bomberman1.walkX(1 * bomberman1.getSpeed());

			} else {
				bomberman1.walkX(1 * bomberman1.getSpeed());

			}

		}
		if (isPressed(KeyEvent.VK_W) && !p1key) {
			p1key = true;
			bomberman1.setImage(assets.get(1));
			int[] newLoc = bomberman1.pixeltoGrid();
			if (board.getUnbreakableStatus(newLoc[0], newLoc[1] - 1)
					|| board.getBreakableStatus(newLoc[0], newLoc[1] - 1)) {
				int[] pixelWallLoc = bomberman1.gridToPixel(newLoc[0], newLoc[1] - 1);
				if (bomberman1.getYLoc() - pixelWallLoc[1] >= 45)
					bomberman1.walkY(-1 * bomberman1.getSpeed());

			} else {
				bomberman1.walkY(-1 * bomberman1.getSpeed());

			}

		}
		if (isPressed(KeyEvent.VK_S) && !p1key) {
			p1key = true;
			bomberman1.setImage(assets.get(0));
			int[] newLoc = bomberman1.pixeltoGrid();
			if (board.getUnbreakableStatus(newLoc[0], newLoc[1] + 1)
					|| board.getBreakableStatus(newLoc[0], newLoc[1] + 1)) {
				int[] pixelWallLoc = bomberman1.gridToPixel(newLoc[0], newLoc[1] + 1);
				if (pixelWallLoc[1] - bomberman1.getYLoc() >= 45)
					bomberman1.walkY(1 * bomberman1.getSpeed());

			} else {
				bomberman1.walkY(1 * bomberman1.getSpeed());

			}

		}
		if (isPressed(KeyEvent.VK_SPACE)) { // BOMB STUFF
			int[] bombLoc = bomberman1.dropBomb();
			if (bombLoc != null) {
				Bomb b1 = new Bomb(bomb, bombLoc[0] * 50, bombLoc[1] * 50);
				board.addP1Bomb(bombLoc[0], bombLoc[1]);
				player1Bombs.add(b1);
				board.setBreakableSpot(bombLoc[0], bombLoc[1]);
			}

		}
		
		}
		// player 2

		boolean p2key = false;
		if(bomberman2.getLives() > 0) {
		if (isPressed(KeyEvent.VK_LEFT) && !p2key) {
			p2key = true;
			bomberman2.setImage(assets.get(2));
			int[] newLoc = bomberman2.pixeltoGrid();
			if (board.getUnbreakableStatus(newLoc[0] - 1, newLoc[1])
					|| board.getBreakableStatus(newLoc[0] - 1, newLoc[1])) {
				int[] pixelWallLoc = bomberman2.gridToPixel(newLoc[0] - 1, newLoc[1]);
				if (bomberman2.getXLoc() - pixelWallLoc[0] >= 50)
					bomberman2.walkX(-1 * bomberman2.getSpeed());

			} else {
				bomberman2.walkX(-1 * bomberman2.getSpeed());

			}

		}
		if (isPressed(KeyEvent.VK_RIGHT) && !p2key) {
			p2key = true;
			bomberman2.setImage(assets.get(3));
			int[] newLoc = bomberman2.pixeltoGrid();
			if (board.getUnbreakableStatus(newLoc[0] + 1, newLoc[1])
					|| board.getBreakableStatus(newLoc[0] + 1, newLoc[1])) {
				int[] pixelWallLoc = bomberman2.gridToPixel(newLoc[0] + 1, newLoc[1]);
				if (pixelWallLoc[0] - bomberman2.getXLoc() >= 30)
					bomberman2.walkX(1 * bomberman2.getSpeed());

			} else {
				bomberman2.walkX(1 * bomberman2.getSpeed());

			}

		}
		if (isPressed(KeyEvent.VK_UP) && !p2key) {
			p2key = true;
			bomberman2.setImage(assets.get(1));
			int[] newLoc = bomberman2.pixeltoGrid();
			if (board.getUnbreakableStatus(newLoc[0], newLoc[1] - 1)
					|| board.getBreakableStatus(newLoc[0], newLoc[1] - 1)) {
				int[] pixelWallLoc = bomberman2.gridToPixel(newLoc[0], newLoc[1] - 1);
				if (bomberman2.getYLoc() - pixelWallLoc[1] >= 45)
					bomberman2.walkY(-1 * bomberman2.getSpeed());

			} else {
				bomberman2.walkY(-1 * bomberman2.getSpeed());

			}

		}
		if (isPressed(KeyEvent.VK_DOWN) && !p2key) {
			p2key = true;
			bomberman2.setImage(assets.get(0));
			int[] newLoc = bomberman2.pixeltoGrid();
			if (board.getUnbreakableStatus(newLoc[0], newLoc[1] + 1)
					|| board.getBreakableStatus(newLoc[0], newLoc[1] + 1)) {
				int[] pixelWallLoc = bomberman2.gridToPixel(newLoc[0], newLoc[1] + 1);
				if (pixelWallLoc[1] - bomberman2.getYLoc() >= 45)
					bomberman2.walkY(1 * bomberman2.getSpeed());

			} else {
				bomberman2.walkY(1 * bomberman2.getSpeed());

			}

		}
		if (isPressed(KeyEvent.VK_ENTER)) {
			int[] bombLoc = bomberman2.dropBomb();
			if (bombLoc != null) {
				Bomb b1 = new Bomb(bomb, bombLoc[0] * 50, bombLoc[1] * 50);
				board.addP2Bomb(bombLoc[0], bombLoc[1]);
				player2Bombs.add(b1);
				board.setBreakableSpot(bombLoc[0], bombLoc[1]);

			}
		}
	}
			
		}
	
		
	
	

	public void mousePressed() {
		if (menu.isOverShop()) {

			menu.shiftShopButton(10, -20, 3);
		}

		if (menu.isOverStart()) {

			menu.shiftStartButton(10, -20, 3);
		}

		if (menu.isOverIns()) {

			menu.shiftInsButton(10, -20, 3);
		}
	}

	public void mouseReleased() {
		if (menu.isOverShop()) {

			menu.shiftShopButton(-10, 20, -3);
		}

		if (menu.isOverStart()) {

			menu.shiftStartButton(-10, 20, -3);
		}
		if (menu.isOverIns()) {

			menu.shiftInsButton(-10, 20, -3);

		}

	}

	public void mouseClicked() {
		if (menu.isOverX()) {
			menu.changeInsPageStatus(false);
		}
		if (menu.isOverIns()) {
			menu.changeInsPageStatus(true);
		}
		if (menu.isOverStart()) {
			menu.changeStartStatus(true);
			menu.changeMapPageStatus(true);

		}
		if (menu.isOverMap1Page()) {
		
			gameState = true;
			board = new Levels("LevelOneMap");
		}
		
		if (menu.isOverMap2Page()) {
			gameState = true;
			board = new Levels("LevelTwoMap");
		}
		
		if (menu.isOverMap3Page()) {
			gameState = true;
			board = new Levels("LevelThreeMap");
		}
	}

	public void keyPressed() {
		keys.add(keyCode);
		if (isPressed(KeyEvent.VK_Q)) { // BOMB STUFF
			int[] newLoc = bomberman1.pixeltoGrid();
			if (newLoc != null) {
				if(board.getItems(newLoc[0], newLoc[1]) == 's') {
					bomberman1.speedUp(1);
					board.resetPlaceToGrass(newLoc[0], newLoc[1]);

				}
				if(board.getItems(newLoc[0], newLoc[1]) == 'h') {
					bomberman1.addLives(1);
					board.resetPlaceToGrass(newLoc[0], newLoc[1]);

				}
				
				
				
			}

		}
		

		
	}

	public void keyReleased() {
		while (keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
		
		

	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}

}