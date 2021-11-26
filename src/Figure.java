import java.awt.*;

abstract public class Figure {

	Polygon region;
	Figure() {
		region = null;
	}
	
	abstract void draw(Graphics g);
	abstract void setXY2(int x, int y);
	abstract void makeRegion();
	abstract void move(int dx, int dy);
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
