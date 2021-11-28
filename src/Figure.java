import java.awt.*;

import javax.swing.*;

abstract public class Figure {

	protected static int MOVE_DX = 20;
	protected static int MOVE_DY = 10;
	protected Polygon region;
	protected Popup popup;
	protected Color color;
	Figure(Color color) {
		this.color = color;
		region = null;
		popup = null;
	}
	// hook function
	void setFill() {
	}
	void setColor(Color color) {
		this.color = color;
	}
	void setPopup(Popup popup) {
		this.popup = popup;
	}
	void popup(JPanel view, int x, int y) {
		//delegation
		popup.popup(view,x,y);
	}
	abstract void draw(Graphics g);
	abstract void setXY2(int x, int y);
	abstract void makeRegion();
	abstract void move(int dx, int dy);
	abstract Figure copy();
	boolean contains(int x, int y) {
		if (region == null) return false;
		return region.contains(x,y);
	}
	void move(Graphics g, int dx, int dy) {
		draw(g);
		move(dx,dy);
		draw(g);
	}
	void drawing(Graphics g, int newX, int newY) {
		draw(g);
		setXY2(newX,newY);
		draw(g);
	}
	
}
