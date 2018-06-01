package imageEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ToolBar extends JPanel {
	
	DrawPanel _dp;
	protected JButton[] buttons;
	
	public ToolBar(DrawPanel dp) {
		_dp = dp;
		ToolBar.ClickListener clickListener = new ToolBar.ClickListener();
		buttons = new JButton[8];
		/*
		 * 0 - Pencil
		 * 1 - Eraser
		 * 2 - Oval
		 * 3 - Rectangle
		 * 4 - Polygon
		 * 5 - Bucket
		 * 6 - Undo
		 * 7 - Redo
		 */
		//Initialize all buttons
		buttons[0] = new JButton("Pencil");
		buttons[1] = new JButton("Eraser");
		buttons[2] = new JButton("Oval");
		buttons[3] = new JButton("Rectangle");
		buttons[4] = new JButton("Polygon");
		buttons[5] = new JButton("Bucket");
		buttons[6] = new JButton("Undo");
		buttons[7] = new JButton("Redo");
		
		//Add click listener to all buttons
		for(int i = 0; i < 8; i++){
			buttons[i].addActionListener(clickListener);
		}
		
		
		
		this.setLayout(new GridLayout(4,2));
		this.setPreferredSize(new Dimension(200,200));
		
		//add all buttons to the panel
		for(int i = 0; i < 8; i ++){
			add(buttons[i]);
		}
	}
	
	private class ClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Color the_color = Color.LIGHT_GRAY;
			//reset button background colors if anything except undo or redo were clicked
			if(e.getSource() != buttons[6] && e.getSource() != buttons[7]) {
				for(int i = 0; i < buttons.length; i++) {
					buttons[i].setBackground(null);
				}
			}
			
			if(e.getSource() == buttons[0]){
				//set tool to pencil
				_dp.setTool(_dp.PENCIL);	
				buttons[0].setBackground(the_color);
			}
			else if (e.getSource() == buttons[1]){
				//set tool to eraser
				_dp.setTool(_dp.ERASER);
				buttons[1].setBackground(the_color);
			}
			else if (e.getSource() == buttons[2]){
				//set tool to oval
				_dp.setTool(_dp.OVAL);
				buttons[2].setBackground(the_color);
			}
			else if (e.getSource() == buttons[3]){
				//set tool to rectangle
				_dp.setTool(_dp.RECTANGLE);
				buttons[3].setBackground(the_color);
			}
			else if (e.getSource() == buttons[4]){
				//set tool to polygon
				_dp.setTool(_dp.POLYGON);
				buttons[4].setBackground(the_color);
			}
			else if (e.getSource() == buttons[5]){
				//set tool to bucket
				_dp.setTool(_dp.BUCKET);
				buttons[5].setBackground(the_color);
			}
			else if (e.getSource() == buttons[6]){
				//set tool to undo
				_dp.undo();
			}
			else if (e.getSource() == buttons[7]){
				//set tool to redo
				_dp.redo();
			}
		}
	}
	
}
