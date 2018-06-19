package thoughtTrainer;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class ThoughtTrainer extends JFrame{
	
	ButtonPanel buttonPanel;
	DisplayPanel displayPanel;
	
	public ThoughtTrainer() {
		this.setSize(700, 700);
        this.setLayout(new BorderLayout());
        
        buttonPanel = new ButtonPanel();
        add(buttonPanel, BorderLayout.NORTH);
        
        displayPanel = new DisplayPanel();
        add(displayPanel);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
	
}
