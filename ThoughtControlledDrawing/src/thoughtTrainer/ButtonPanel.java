package thoughtTrainer;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ButtonPanel extends JPanel{
	
	JButton[] buttons;
	
	public ButtonPanel() {
		ButtonPanel.ClickListener clickListener = new ButtonPanel.ClickListener();
		buttons = new JButton[4];
		
		//can only activate 4 mental commands at a time
//		buttons[0] = new JButton("Train Left-Click");
//		buttons[1] = new JButton("Train Right-Click");
		
		buttons[0] = new JButton("Train Up");
		buttons[1] = new JButton("Train Down");
		buttons[2] = new JButton("Train Left");
		buttons[3] = new JButton("Train Right");
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].addActionListener(clickListener);
		}
		
		this.setLayout(new GridLayout(1,4));
		this.setPreferredSize(new Dimension(200,200));
		
		//add all buttons to the panel
		for(int i = 0; i < buttons.length; i ++){
			add(buttons[i]);
		}
		
	}
	
	private class ClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == buttons[0]) {
				System.out.println("Train Left Click");
			}
			else if(e.getSource() == buttons[1]) {
				
			}
			else if(e.getSource() == buttons[2]) {
				
			}
			else if(e.getSource() == buttons[3]) {
							
			}
			else if(e.getSource() == buttons[4]) {
				
			}
			else if(e.getSource() == buttons[5]) {
				
			}
			
		}
		
	}
}
