package imageEditor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;



public class PaintApp extends JFrame{

	public DrawPanel drawPanel;
    public TopMenuBar menuBar;
    public ToolBar toolBar;
    
    public PaintApp(){
    	this.setSize(700, 700);
        this.setLayout(new BorderLayout());
        
        /* Initialize Components */
        drawPanel = new DrawPanel();
        menuBar = new TopMenuBar(this);
        toolBar = new ToolBar(drawPanel);
        
        /* Set up the components */
        add(menuBar, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);
        add(toolBar, BorderLayout.EAST);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void clear() {
    	drawPanel.clear();
    }
	
}
