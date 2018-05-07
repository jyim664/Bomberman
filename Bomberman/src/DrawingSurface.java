import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;


	public class DrawingSurface extends PApplet {

		public static final int DRAWING_WIDTH = 800;
		public static final int DRAWING_HEIGHT = 600;

		private Rectangle screenRect;

		private Player bomberman;
		private ArrayList<Shape> obstacles;

		private ArrayList<Integer> keys;
		
		private ArrayList<PImage> assets; //1 = Front, 2 = Back, 3 = left, 4 = right

		public DrawingSurface() {
			super();
			assets = new ArrayList<PImage>();
			keys = new ArrayList<Integer>();
			screenRect = new Rectangle(0,0,DRAWING_WIDTH,DRAWING_HEIGHT);
			obstacles = new ArrayList<Shape>();
			obstacles.add(new Rectangle(200,400,400,50));
			obstacles.add(new Rectangle(0,250,100,50));
			obstacles.add(new Rectangle(700,250,100,50));
			obstacles.add(new Rectangle(375,300,50,100));
			obstacles.add(new Rectangle(300,250,200,50));
		}


		public void spawnNewMario() {
			bomberman = new Player(assets.get(0),DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
		}
		
		public void runMe() {
			runSketch();
		}

		// The statements in the setup() function 
		// execute once when the program begins
		public void setup() {
			//size(0,0,PApplet.P3D);
			assets.add(loadImage("bombermanFront.png"));
			assets.add(loadImage("bombermanBack.png"));
			assets.add(loadImage("bombermanLeft.png"));
			assets.add(loadImage("bombermanRight.png"));


			
			spawnNewMario();
		}

		// The statements in draw() are executed until the 
		// program is stopped. Each statement is executed in 
		// sequence and after the last line is read, the first 
		// line is executed again.
		public void draw() {

			// drawing stuff

			background(0,255,255);   

			pushMatrix();

			float ratioX = (float)width/DRAWING_WIDTH;
			float ratioY = (float)height/DRAWING_HEIGHT;

			scale(ratioX, ratioY);

			fill(100);
			for (Shape s : obstacles) {
				if (s instanceof Rectangle) {
					Rectangle r = (Rectangle)s;
					rect(r.x,r.y,r.width,r.height);
				}
			}

			bomberman.draw(this);

			popMatrix();


			// modifying stuff

			if (isPressed(KeyEvent.VK_LEFT)) {
				bomberman.setImage(assets.get(2));
				bomberman.walkX(-1);
			}

			if (isPressed(KeyEvent.VK_RIGHT)) {
				bomberman.setImage(assets.get(3));
				bomberman.walkX(1);
			}

			if (isPressed(KeyEvent.VK_DOWN)) {
				bomberman.setImage(assets.get(0));
				bomberman.walkY(1);
			}

			if (isPressed(KeyEvent.VK_UP)) {
		
				bomberman.setImage(assets.get(1));
				bomberman.walkY(-1);
				
			}

//
//			mario.act(obstacles);

//			if (!screenRect.intersects(mario))
//				spawnNewMario();
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


