import java.awt.Dimension;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * This class runs the entire program.
 * @author Kush Patel
 */
public class Main {

	public static int width = 800, height = 800;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();

		window.setSize(width,height);
		window.setMinimumSize(new Dimension(800,800));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		window.setVisible(true);
		canvas.requestFocus();
	}

}
