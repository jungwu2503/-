import java.awt.*;

abstract public class Figure {

	Figure() {
		
	}
	
	abstract void draw(Graphics g);
	abstract void setXY2(int x, int y);
	void drawing(Graphics g, int newX, int newY) {
		draw(g);
		setXY2(newX,newY);
		draw(g);
	}
	
}
