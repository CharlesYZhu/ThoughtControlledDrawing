package imageEditor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;



public class PaintApp extends JFrame{

	public DrawPanel drawPanel;
    public TopMenuBar menuBar; 
    
    public PaintApp(){
    	this.setSize(1024, 768);
        this.setLayout(new BorderLayout());
        
        /* Initialize Components */
        drawPanel = new DrawPanel();
        menuBar = new TopMenuBar();
        
        /* Set up the components */
        add(menuBar, BorderLayout.NORTH);
        add(new JScrollPane(drawPanel), BorderLayout.CENTER);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
	
}
