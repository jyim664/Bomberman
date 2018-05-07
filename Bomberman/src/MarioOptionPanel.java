import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class MarioOptionPanel extends JPanel implements ActionListener {
	
	MarioMain w;
	
	public MarioOptionPanel(MarioMain w) {
		this.w = w;
		JButton button = new JButton("Press me!");
		button.addActionListener(this);
		add(button);
	}
	
	public void actionPerformed(ActionEvent e) {
		w.changePanel();
	}
	
}