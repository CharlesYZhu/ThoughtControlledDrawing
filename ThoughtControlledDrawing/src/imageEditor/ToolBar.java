package imageEditor;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ToolBar extends JPanel {
	
	protected JButton[] buttons;
	
	public ToolBar(DrawPanel dp) {
		buttons = new JButton[4];
	}
	
}
