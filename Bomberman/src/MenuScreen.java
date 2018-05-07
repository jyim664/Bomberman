import java.awt.Image;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class MenuScreen {
	
	//private Image bombImage =bomb.png;
	
	private int width = Main.width;
	private int height = Main.height;
	private int xTitleDis = 315, yTitleDis = 75; //(x,y) displacement for the title
	private int bgroundR = 25, bgroundG = 25, bGroundB = 112; //background rgb values
	public int startX = 295, startY = 375, startLen = 200, startWid = 65, startCur = 100;//start button coordinates
	public	int startR = 169, startG = 219, startB = 30;
	public int insX = 200, insY = 450, insLen = 390, insWid = 65;
	public int shopX = 295, shopY = 525, shopLen = 200, shopWid = 65;
	
	public int shopText = 330;
	public int startText = 320;
	public int insText = 230;
	
	public boolean overStart = false;
	public boolean overShop = false;
	public boolean overIns = false;
	
	private String title;
	private String start = "Start";
	private PFont titleFont;
	private PFont buttonFont;
	
	private int alphaValue = 0;
	
	private PImage bg;
	
	public MenuScreen(String gameTitle) {
		title = gameTitle;
		
	}
	
	public void setup(PApplet drawer) {
	bg = drawer.loadImage("C:\\Users\\kpatel426\\Bomberman\\src\\biomes-noisy.png");
	
	titleFont = drawer.createFont("Phosphate", 24);
    
	drawer.frameRate(60);
	buttonFont = drawer.createFont("Copperplate", 24);
	
	}
	
	public void draw(PApplet drawer) {
		drawer.image(bg, 0, 0);
		
		drawer.textFont(titleFont);
		if(alphaValue < 255){
			alphaValue+=1;
		}
	
	drawer.fill(255, 255, 240, alphaValue);
	drawer.textSize(110);	
	drawer.text(title, (width/2) - xTitleDis, (height/2) - yTitleDis);
	
	
	drawer.textFont(buttonFont);

	drawer.stroke(0);
	drawer.strokeWeight(5);
	drawer.fill(startR, startG, startB);
	
	if(drawer.mouseX > shopX && drawer.mouseX < shopX + shopLen && drawer.mouseY > shopY && drawer.mouseY < shopY + shopWid) {
		drawer.fill(255, 215, 0);
		overShop = true;
	}else {
		drawer.fill(startR, startG, startB);
		overShop = false;
	}
	
	
	
	drawer.rect(shopX, shopY, shopLen, shopWid, startCur);
	drawer.fill(0);
	drawer.textSize(50);
	drawer.text("Shop", shopText, shopY + 45);
	
	if(drawer.mouseX > insX && drawer.mouseX < insX + insLen && drawer.mouseY > insY && drawer.mouseY < insY + insWid) {
		drawer.fill(255, 215, 0);
		overIns = true;
	}else {
		drawer.fill(startR, startG, startB);
		overIns = false;
	}
	
	
	drawer.rect(insX, insY, insLen, insWid, startCur);
	drawer.fill(0);
	drawer.textSize(50);
	drawer.text("Instructions", insText, insY+ 45);
	
	if(drawer.mouseX > startX && drawer.mouseX < startX + startLen && drawer.mouseY > startY && drawer.mouseY < startY + startWid) {
		drawer.fill(255, 215, 0);
		overStart = true;
	}else {
		drawer.fill(startR, startG, startB);
		overStart = false;
	}
	
	drawer.rect(startX, startY, startLen, startWid, startCur);
	drawer.fill(0);
	drawer.textSize(50);
	drawer.text("Start", startText, startY + 45);

	
	
	drawer.translate(65, 122);
	float aX1 = 125; //bottom anchor
	  float aY1 = 125;
	  float aX2 = 125; //top anchor
	  float cX2 = 125; //control point
	  //linear variables
	  float cY1 = 187.5f; //first control y
	  float Y2 = (drawer.random(275, 325))/4; //second y
	  float cX1R = (drawer.random(725, 875))/4; //first x control left
	  float cX1L = (200 + (cX1R - 800))/4; //first x conrol right
	  //color
	  float flamecolorG = 155;
	  float flamecolorB = 0;
	 
	  drawer.noStroke();
	 
	   while(cY1 > 250/2) {
	     drawer.fill(255, flamecolorG, flamecolorB, alphaValue); 
	     drawer.curve(cX1L + 50 , cY1, aX1, aY1, aX2, Y2, cX2, Y2);
	     drawer.curve(cX1R + 50, cY1, aX1, aY1, aX2, Y2, cX2, Y2);
	     cY1 = cY1 - 7.5f/2;
	     Y2 = Y2 + 16.5f/2;
	     cX1R = cX1R - 33f/2;
	     cX1L = cX1L + 33f/2;
	     flamecolorB = flamecolorB + 33;
	     flamecolorG = flamecolorG + 33;     
	   }
	drawer.translate(-65, -122);
	drawer.fill(0, 0, 0, 255);
	drawer.ellipse(190, 295, 80, 80);//bomb
	drawer.rect(183, 245, 16, 15);//bomb stub
	
	
	
		
	}
	
	
	
	
}
