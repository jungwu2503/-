import java.awt.*;

abstract public class Figure {

	Polygon region;
	Figure() {
		region = null;
	}
	
	abstract void draw(Graphics g);
	abstract void setXY2(int x, int y);
	abstract void makeRegion();
	boolean contains(int x, int y) {
		if (region == null) return false;
		return region.contains(x,y);
	}
	void drawing(Graphics g, int newX, int newY) {
		draw(g);
		setXY2(newX,newY);
		draw(g);
	}
	
}
