package thoughtTrainer;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Mouse {

	private Image _mouse;
	private int _x, _y, _width, _height, _anchorX, _anchorY;
	
	public Mouse(int x, int y) {
		_mouse = new ImageIcon("Images/pointer.png").getImage();
		_x = x;
		_y = y;
		_width = 64;
		_height = 64;
		_anchorX = 250-(_width/2);
		_anchorY = 250-(_height/2);
	}
	
	public void paint(Graphics2D g) {
		g.drawImage(_mouse, _x, _y, null);
	}
	
	public void moveLeft() {
		setLocation(_anchorX-50, _anchorY);
	}
	
	public void moveRight() {
		setLocation(_anchorX+50, _anchorY);
	}
	
	public void moveUp() {
		setLocation(_anchorX, _anchorY-50);
	}
	
	public void moveDown() {
		setLocation(_anchorX, _anchorY+50);
	}
	
	public void resetPosition() {
		setLocation(_anchorX, _anchorY);
	}
	
	public void setLocation(int x, int y) {
		_x = x;
		_y = y;
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
	
}
