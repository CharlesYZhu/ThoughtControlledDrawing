package imageEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.JComponent;

public class DrawPanel extends JComponent{
	
	private PaintApp _pa;
	private Image image;
	private Graphics2D g2;
	private int currentX, currentY, oldX, oldY;
	public final int PENCIL = 0, ERASER = 1, OVAL = 2, RECTANGLE = 3, POLYGON = 4, BUCKET = 5;
	private int current_tool;
	private Stack<Image> undoStack;
	private Stack<Image> redoStack;
	
	
	public DrawPanel(PaintApp pa) {
		_pa = pa;
		//Start with the pencil tool
		current_tool = PENCIL;
		
		undoStack = new Stack<Image>();
		redoStack = new Stack<Image>();
		
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//Push the current image to the undo stack before changes are made
				undoStack.push(copyImage(image));
				//clear the redoStack if new lines are being drawn
				redoStack = new Stack<Image>();
				
				oldX = e.getX();
				oldY = e.getY();
			}
			public void mouseReleased(MouseEvent e){
				//keep the last drawn image on the redo stack. Resets every new drawing
				if(redoStack.isEmpty()) {
					redoStack.push(copyImage(image));
				}
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				currentX = e.getX();
				currentY = e.getY();
				
				//Pencil Tool
				if (g2 != null && current_tool == PENCIL) {
					g2.drawLine(oldX, oldY, currentX, currentY);
					repaint();
					oldX = currentX;
					oldY = currentY;
				}
			}
		});
	}
	
	protected void paintComponent(Graphics g) {
		if(image == null) {
			image = createImage(getSize().width, getSize().height);
			g2 = (Graphics2D) image.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}
		g.drawImage(image,0,0,null);
	}
	
	public void clear() {
		g2.setPaint(Color.WHITE);
		g2.fillRect(0,0,getSize().width, getSize().height);
		g2.setPaint(Color.BLACK);
		repaint();
	}
	
	public void setTool(int tool) {
		current_tool = tool;
		switch(tool){
			case PENCIL:
				_pa.setLoggerText("Selected: Pencil");
				break;
			case ERASER:
				_pa.setLoggerText("Selected: Eraser");
				break;
			case OVAL:
				_pa.setLoggerText("Selected: Oval");
				break;
			case RECTANGLE:
				_pa.setLoggerText("Selected: Rectangle");
				break;
			case POLYGON:
				_pa.setLoggerText("Selected: Polygon");
				break;
			case BUCKET:
				_pa.setLoggerText("Selected: Bucket");
				break;
		}
		
	}
	
	public void undo(){
		if(!undoStack.empty()){
			//Add last Image to redo stack before undoing
			redoStack.push(undoStack.peek());
			
			System.out.println("Undo");
			//Set the current Image to last saved image on the image stack
			image = undoStack.pop();
			g2 = (Graphics2D) image.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g2.setPaint(Color.black);
			repaint();
		}
	}
	
	/* Creates and returns a copy of the current Image instance */
	private BufferedImage copyImage(Image img){
		BufferedImage copyImg = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
		Graphics g = copyImg.createGraphics();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		return copyImg;
	}
	
	public void redo(){
		//TODO: fix redo
		if(!redoStack.empty()){
			//Add top of redo stack to undo stack so the redo can be undone
			undoStack.push(redoStack.peek());
			
			//Set current image to the 
			image = redoStack.pop();
			g2 = (Graphics2D) image.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g2.setPaint(Color.black);
			repaint();
		}
	}
	
	public int getCurrentTool(){
		return current_tool;
	}
	
}
