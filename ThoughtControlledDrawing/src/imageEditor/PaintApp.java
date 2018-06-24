package imageEditor;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;



public class PaintApp extends JFrame{

	public DrawPanel drawPanel;
    public TopMenuBar menuBar;
    public ToolBar toolBar;
    private JLabel logger;
    private PrintWriter printWriter;
    
    public PaintApp(PrintWriter pw) throws AWTException{
    	this.setSize(900, 900);
        this.setLayout(new BorderLayout());
        
        printWriter = pw; //for logging and timestamping user actions
        
        /* Initialize Components */
        drawPanel = new DrawPanel(this);
        menuBar = new TopMenuBar(this);
        toolBar = new ToolBar(drawPanel);
        logger = new JLabel("Press \"F\" to Enable Pen | Move the mouse to draw");
        /* Set up the components */
        add(menuBar, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);
        	//Disabled Tool Bar
        //add(toolBar, BorderLayout.EAST);
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
    
    public void exportImage() throws IOException {
    	BufferedImage export = drawPanel.exportImage();
    	File outputFile = new File("image.bmp");
    	ImageIO.write(export, "BMP", outputFile);
    }
    
    public void logAction(String action){
    	String timestamp = new java.util.Date().toString();
    	printWriter.print("[" + timestamp + "] : " + action + "\n");
    }
    
    public void closeWriter(){
    	printWriter.close();
    }
	
}
