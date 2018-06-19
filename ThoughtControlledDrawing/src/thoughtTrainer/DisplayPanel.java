package thoughtTrainer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class DisplayPanel extends JComponent{
	
	private Graphics2D g2;
	private Image image;
	private Rectangle rectangle;
	
	public DisplayPanel() {
		this.setBackground(java.awt.Color.black);
		this.setPreferredSize(new java.awt.Dimension(500, 500));
		this.setSize(new java.awt.Dimension(500, 500));
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
	
}
