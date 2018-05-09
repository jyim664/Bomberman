
import java.awt.Color;
import java.awt.Image;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class MenuScreen {

	// Initial page
	private int width = Main.width;
	private int height = Main.height;
	private int xTitleDis = 315, yTitleDis = 75; // (x,y) displacement for the title
	private int bgroundR = 25, bgroundG = 25, bGroundB = 112; // background rgb values
	private int startX = 295, startY = 375, startLen = 200, startWid = 65, startCur = 100;// start button coordinates
	private int startR = 169, startG = 219, startB = 30;// start button colors
	private int insX = 200, insY = 450, insLen = 390, insWid = 65;// instructions button coordinates
	private int shopX = 295, shopY = 525, shopLen = 200, shopWid = 65;// shop button location

	// Instructions Page
	private int controlX = width / 2, controlY = height / 2, controlLen = 0, controlWid = 0, controlCur = 0;

	// button text locations
	private int shopText = 330;
	private int startText = 320;
	private int insText = 230;
	private int controlText = 255;

	// all mouse conditions
	private boolean overStart = false;
	private boolean overShop = false;
	private boolean overIns = false;
	private boolean activateInsPage = false;
	private boolean overX = false;
	private boolean startPressed = false;

	private String title;
	private String start = "Start";
	private PFont titleFont;
	private PFont buttonFont;

	// fades the starting text
	private int fadeValue = 3;
	private int transparency = 0;

	// background image
	private PImage bg;
	private PImage bomberFace1;
	private PImage bomberFace2;
	private PImage enterKey;
	private PImage spacebar;
	private PImage wasdKeys;
	private PImage arrowKeys;

	public MenuScreen(String gameTitle) {
		title = gameTitle;

	}

	public void setup(PApplet drawer) {
		bg = drawer.loadImage("\\Users\\David Zhao\\git\\Bomberman\\Bomberman\\src\\biomes-noisy.png");
		bomberFace1 = drawer.loadImage("\\Users\\David Zhao\\git\\Bomberman\\Bomberman\\src\\BombermanFace.png");
		bomberFace2 = drawer.loadImage("\\Users\\David Zhao\\git\\Bomberman\\Bomberman\\src\\BombermanFace2.png");
		enterKey = drawer.loadImage("\\Users\\David Zhao\\git\\Bomberman\\Bomberman\\src\\Enter.png");
		spacebar = drawer.loadImage("\\Users\\David Zhao\\git\\Bomberman\\Bomberman\\src\\Space.png");
		wasdKeys = drawer.loadImage("\\Users\\David Zhao\\git\\Bomberman\\Bomberman\\src\\WASD.png");
		arrowKeys = drawer.loadImage("\\Users\\David Zhao\\git\\Bomberman\\Bomberman\\src\\ArrowKeys.png");
		titleFont = drawer.createFont("Phosphate", 24);

		drawer.frameRate(60);
		buttonFont = drawer.createFont("Copperplate", 24);

	}

	public void draw(PApplet drawer) {
		drawer.image(bg, 0, 0);

		drawer.textFont(titleFont);
		if (fadeValue < 255 && startPressed == false) {
			fadeValue += 1;
		} else if (fadeValue > 0 && startPressed == true) {
			fadeValue -= 5;
		}

		drawer.fill(255, 255, 240, fadeValue);
		drawer.textSize(110);
		drawer.text(title, (width / 2) - xTitleDis, (height / 2) - yTitleDis);

		drawer.textFont(buttonFont);

		drawer.stroke(0, fadeValue);
		drawer.strokeWeight(5);
		drawer.fill(startR, startG, startB, fadeValue);

		if (drawer.mouseX > shopX && drawer.mouseX < shopX + shopLen && drawer.mouseY > shopY
				&& drawer.mouseY < shopY + shopWid) {
			drawer.fill(255, 215, 0, fadeValue);
			overShop = true;
		} else {
			drawer.fill(startR, startG, startB, fadeValue);
			overShop = false;
		}

		drawer.rect(shopX, shopY, shopLen, shopWid, startCur);
		drawer.fill(0, fadeValue);
		drawer.textSize(50);
		drawer.text("Shop", shopText, shopY + 45);

		if (drawer.mouseX > insX && drawer.mouseX < insX + insLen && drawer.mouseY > insY
				&& drawer.mouseY < insY + insWid) {
			drawer.fill(255, 215, 0, fadeValue);
			overIns = true;
		} else {
			drawer.fill(startR, startG, startB, fadeValue);
			overIns = false;
		}

		drawer.rect(insX, insY, insLen, insWid, startCur);
		drawer.fill(0, fadeValue);
		drawer.textSize(50);
		drawer.text("Instructions", insText, insY + 45);

		if (drawer.mouseX > startX && drawer.mouseX < startX + startLen && drawer.mouseY > startY
				&& drawer.mouseY < startY + startWid) {
			drawer.fill(255, 215, 0, fadeValue);
			overStart = true;
		} else {
			drawer.fill(startR, startG, startB, fadeValue);
			overStart = false;
		}

		drawer.rect(startX, startY, startLen, startWid, startCur);
		drawer.fill(0, fadeValue);
		drawer.textSize(50);
		drawer.text("Start", startText, startY + 45);

		drawer.translate(65, 122);
		float aX1 = 125; // bottom anchor
		float aY1 = 125;
		float aX2 = 125; // top anchor
		float cX2 = 125; // control point
		// linear variables
		float cY1 = 187.5f; // first control y
		float Y2 = (drawer.random(275, 325)) / 4; // second y
		float cX1R = (drawer.random(725, 875)) / 4; // first x control left
		float cX1L = (200 + (cX1R - 800)) / 4; // first x conrol right
		// color
		float flamecolorG = 155;
		float flamecolorB = 0;

		drawer.noStroke();

		while (cY1 > 250 / 2) {
			drawer.fill(255, flamecolorG, flamecolorB, fadeValue);
			drawer.curve(cX1L + 50, cY1, aX1, aY1, aX2, Y2, cX2, Y2);
			drawer.curve(cX1R + 50, cY1, aX1, aY1, aX2, Y2, cX2, Y2);
			cY1 = cY1 - 7.5f / 2;
			Y2 = Y2 + 16.5f / 2;
			cX1R = cX1R - 33f / 2;
			cX1L = cX1L + 33f / 2;
			flamecolorB = flamecolorB + 33;
			flamecolorG = flamecolorG + 33;
		}
		drawer.translate(-65, -122);
		drawer.fill(0, 0, 0, fadeValue);
		drawer.ellipse(190, 295, 80, 80);// bomb
		drawer.rect(183, 245, 16, 15);// bomb stub

	}

	public void drawInsPage(PApplet drawer) {
		if (activateInsPage) {

			drawer.fill(0);

			drawer.stroke(0);
			drawer.line(100, 100, 700, 100);
			drawer.line(100, 100, 100, 700);
			drawer.line(700, 100, 700, 700);
			drawer.line(100, 700, 700, 700);
			drawer.fill(84, 43, 2);
			drawer.rect(100, 100, 600, 600);

			drawer.strokeWeight(15);
			if (drawer.mouseX > 645 && drawer.mouseX < 675 && drawer.mouseY > 120 && drawer.mouseY < 150) {
				drawer.stroke(0);
				overX = true;
			} else {
				drawer.stroke(255);

			}

			// "X" button
			drawer.line(645, 120, 675, 150);
			drawer.line(675, 120, 645, 150);

			// control title
			drawer.fill(255);
			drawer.textFont(buttonFont);
			drawer.textSize(60);
			drawer.text("Controls", controlText, 150);
			drawer.stroke(255);
			drawer.strokeWeight(3);
			drawer.line(controlText, 165, controlText + 290, 165);

			drawer.translate(0, 50);
			drawer.textSize(40);
			drawer.text("Move:", 135, 340);
			drawer.text("Bomb:", 135, 450);
			drawer.text("P1", 410, 227);
			drawer.text("P2", 610, 227);

			drawer.noFill();
			drawer.rect(120, 250, 150, 275);
			drawer.rect(325, 250, 150, 275);
			drawer.rect(530, 250, 150, 275);

			drawer.image(bomberFace1, 350, 190);
			drawer.image(bomberFace2, 550, 190);
			drawer.image(enterKey, 555, 415);
			spacebar.resize(125, 60);
			drawer.image(spacebar, 340, 425);
			wasdKeys.resize(130, 90);
			drawer.image(wasdKeys, 340, 295);
			arrowKeys.resize(130, 90);
			drawer.image(arrowKeys, 540, 290);

		}
	}

	public void drawMapPage(PApplet drawer) {
		int x = 0;
		if (startPressed == true) {
			if (transparency < 255) {
				transparency += 3;

			}
			drawer.tint(255, transparency);

			drawer.image(bomberFace1, 365, 625);

			if (drawer.mouseX > 365 && drawer.mouseX < 415 && drawer.mouseY > 600 && drawer.mouseY < 675) {
				// drawer.fill(0);
				drawer.stroke(3);
				drawer.strokeWeight(5);
				drawer.ellipse(390, 650, 50, 50);
			}

		}

	}

	public void shiftStartButton(int x, int len, int text) {
		startX += x;
		startLen += len;
		startText += text;

	}

	public void shiftInsButton(int x, int len, int text) {
		insX += x;
		insLen += len;
		insText += text;
	}

	public void shiftShopButton(int x, int len, int text) {
		shopX += x;
		shopLen += len;
		shopText += text;

	}

	public void changeInsPageStatus(boolean check) {
		activateInsPage = check;
	}

	public void changeStartStatus(boolean check) {
		startPressed = check;
	}

	public boolean isOverStart() {
		return overStart;
	}

	public boolean isOverIns() {
		return overIns;
	}

	public boolean isOverShop() {
		return overShop;
	}

	public boolean isOverX() {
		return overX;
	}

}