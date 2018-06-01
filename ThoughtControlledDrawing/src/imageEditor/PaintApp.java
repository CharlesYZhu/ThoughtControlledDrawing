package imageEditor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;



public class PaintApp extends JFrame{

	public DrawPanel drawPanel;
    public TopMenuBar menuBar;
    public ToolBar toolBar;
    private JTextField logger;
    
    public PaintApp(){
    	this.setSize(700, 700);
        this.setLayout(new BorderLayout());
        
        /* Initialize Components */
        drawPanel = new DrawPanel(this);
        menuBar = new TopMenuBar(this);
        toolBar = new ToolBar(drawPanel);
        logger = new JTextField();
        /* Set up the components */
        add(menuBar, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);
        add(toolBar, BorderLayout.EAST);
        add(logger, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    /** Calls the clear method in DrawPanel.java **/
    public void clear() {
    	drawPanel.clear();
    }
    
    public void setLoggerText(String text){
    	logger.setText(text);
    }
	
}
