import javax.swing.*;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MarioMain {

	private JFrame window;
	
	private JPanel cardPanel;
	
	private MarioOptionPanel panel1;    
	private MarioDrawingSurface panel2;
	
	private PSurfaceAWT.SmoothCanvas processingCanvas;
	
	public MarioMain() {
		panel2 = new MarioDrawingSurface();
		PApplet.runSketch(new String[]{""}, panel2);
		
		PSurfaceAWT surf = (PSurfaceAWT) panel2.getSurface();
		processingCanvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		window = (JFrame)processingCanvas.getFrame();

		window.setBounds(0,0,800, 600);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		cardPanel = new JPanel();
	    CardLayout cl = new CardLayout();
	    cardPanel.setLayout(cl);
	    
	    window.getContentPane().removeAll();
	    
		panel1 = new MarioOptionPanel(this);    
	    panel2 = new MarioDrawingSurface();
	    
	    cardPanel.add(panel1,"1");
	    cardPanel.add(processingCanvas,"2");
	    
	    window.setLayout(new BorderLayout());
	    
	    window.add(cardPanel);
	    window.revalidate();
	}
	

	public static void main(String[] args)
	{
		MarioMain m = new MarioMain();
	}
  
	public void changePanel() {
		((CardLayout)cardPanel.getLayout()).next(cardPanel);
		processingCanvas.requestFocus();
	}
  
}