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
	private Stack<Image> tempImageStack; //used to save an image state to redraw when making preview images for rectangle and oval
	private boolean tempReset = false;
	
	
	public DrawPanel(PaintApp pa) {
		_pa = pa;
		//Start with the pencil tool
		current_tool = PENCIL;
		
		undoStack = new Stack<Image>();
		redoStack = new Stack<Image>();
		tempImageStack = new Stack<Image>();
		
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//Push the current image to the undo stack before changes are made
				undoStack.push(copyImage(image));
				//clear the redoStack if new lines are being drawn
				redoStack = new Stack<Image>();
				
				oldX = e.getX();
				oldY = e.getY();
				
				if (current_tool == ERASER) {
					g2.setColor(Color.WHITE); //set color to background color
					g2.fillRect(e.getX()-25, e.getY()-25, 50, 50); //fill area with background color
					repaint();
					g2.setColor(Color.BLACK); //set color back to black
				}
				if (current_tool == OVAL || current_tool == RECTANGLE) {
					//push a copy of the currnet image so that it can be redraw between drawing temp images for oval
					tempImageStack.push(copyImage(image));
					System.out.println("Saved Instance");
				}
			}
			public void mouseReleased(MouseEvent e){
				//keep the last drawn image on the redo stack. Resets every new drawing
				if(redoStack.isEmpty()) {
					redoStack.push(copyImage(image));
				}
				if(g2 != null && current_tool == OVAL) {
					//Take the smaller coordinate(between oldX and current) to decide the origin. Absolute value the difference b/t current and old to get size.
					g2.drawOval(Math.min(oldX, e.getX()), Math.min(oldY,e.getY()), Math.abs(e.getX()-oldX), Math.abs(e.getY()-oldY));
					repaint();
					tempImageStack.pop();
				}
				if(g2 != null && current_tool == RECTANGLE) {
					g2.drawRect(Math.min(oldX, e.getX()), Math.min(oldY,e.getY()), Math.abs(e.getX()-oldX), Math.abs(e.getY()-oldY));
					repaint();
					tempImageStack.pop();
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
				//Draw Preview of Oval
				if (current_tool == OVAL) {
					restore(); //clear the last temp drawing
					g2.drawOval(Math.min(oldX, e.getX()), Math.min(oldY,e.getY()), Math.abs(e.getX()-oldX), Math.abs(e.getY()-oldY));
				    repaint();
				}
				
				if (current_tool == RECTANGLE) {
					restore();
					g2.drawRect(Math.min(oldX, e.getX()), Math.min(oldY,e.getY()), Math.abs(e.getX()-oldX), Math.abs(e.getY()-oldY));
					repaint();
					
				}
				if (current_tool == ERASER) {
					g2.setColor(Color.WHITE); //set color to background color
					g2.fillRect(currentX-25, currentY-25, 50, 50); //fill area with background color
					repaint();
					g2.setColor(Color.BLACK); //set color back to black
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
	
	public void restore() {
		if(!tempImageStack.empty()){
			//Set the current Image to last saved image on the image stack

//			System.out.println(tempImageStack.empty());
//			image = tempImageStack.peek();
//			g2 = (Graphics2D) image.getGraphics();
//			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		    g2.setPaint(Color.black);
//			repaint();
			
			//Restoring by redefining image takes too long and is never processed. Need to use this method
			g2.setPaint(Color.WHITE);
			g2.fillRect(0,0,getSize().width, getSize().height);
			g2.setPaint(Color.BLACK);
			g2.drawImage(tempImageStack.peek(), 0, 0, Color.WHITE, null);
			repaint();
		}
	}
	
	public int getCurrentTool(){
		return current_tool;
	}
	
	public BufferedImage exportImage() {
		BufferedImage export = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
		Graphics g = export.createGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		return export;
	}
	
}
