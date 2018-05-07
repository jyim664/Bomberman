import processing.core.PApplet;

public class DrawingSurface extends PApplet {

	private MenuScreen menu;
	
	public DrawingSurface() {
		menu = new MenuScreen("B    mberman");
		
	}
	
	
	// The statements in the setup() function 
	// execute once when the program begins
	public void setup() {
		
		menu.setup(this);
		
	}
	
	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	public void draw() { 
		menu.draw(this);
		
		
		
		
	}
	
	public void mousePressed() {
		if(menu.overShop) {
			menu.shopX+=10;
			menu.shopLen-=20;
			menu.shopText+=3;
		}
		
		if(menu.overStart) {
			menu.startX+=10;
			menu.startLen-=20;
			menu.startText+=3;
		}
		
		if(menu.overIns) {
			menu.insX+=10;
			menu.insLen-=20;
			menu.insText+=3;
		}
	}
	
	public void mouseReleased() {
		if(menu.overShop) {
			menu.shopX-=10;
			menu.shopLen+=20;
			menu.shopText-=3;
		}
		
		if(menu.overStart) {
			menu.startX-=10;
			menu.startLen+=20;
			menu.startText-=3;
		}
		if(menu.overIns) {
			menu.insX-=10;
			menu.insLen+=20;
			menu.insText-=3;
		}
	}
	
	
}
