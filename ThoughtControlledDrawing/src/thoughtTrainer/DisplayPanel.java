package thoughtTrainer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel{
	
	private Graphics2D g2;
	private Rectangle rectangle;
	private Mouse mouse;
	
	public DisplayPanel() {
		this.setBackground(java.awt.Color.WHITE);
		this.setPreferredSize(new java.awt.Dimension(500, 500));
		this.setSize(new java.awt.Dimension(500, 500));
		mouse = new Mouse(318, 218);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;
		mouse.paint(brush);
	}
	
}
