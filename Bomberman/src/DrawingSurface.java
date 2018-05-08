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
		menu.drawInsPage(this);
		
		
		
		
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
	
}
