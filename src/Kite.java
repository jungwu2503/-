import java.awt.*;
import java.util.*;

public class Kite extends Box {

	Circle circle;
	Line line1;
	Line line2;
	Line line3;
	Line line4;
	ArrayList<Figure> children = new ArrayList<Figure>();
	
	Kite(Color color, int thickness) {	
		super(color,thickness);
		children.add(circle = new Circle(color,thickness));
		children.add(line1 = new Line(color,thickness));
		children.add(line2 = new Line(color,thickness));
		children.add(line3 = new Line(color,thickness));
		children.add(line4 = new Line(color,thickness));		
	}
	
	Kite(Color color, int thickness, int x, int y) {	
		super(color,thickness,x,y);
		children.add(circle = new Circle(color,thickness,x,y));
		children.add(line1 = new Line(color,thickness,x,y));
		children.add(line2 = new Line(color,thickness,x,y));
		children.add(line3 = new Line(color,thickness,x,y));
		children.add(line4 = new Line(color,thickness,x,y));
	}
	
	Kite(Color color, int thickness, int x1, int y1, int x2, int y2) {	
		super(color,thickness,x1,y1,x2,y2);
		int w = Math.abs(x2-x1);
		int h = Math.abs(y2-y1);
		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);
		
		children.add(circle = new Circle(color,thickness,x+w/4,y+h/4,x+3*w/4,y+3*h/4));
		children.add(line1 = new Line(color,thickness,x1,y1,x2,y2));
		children.add(line2 = new Line(color,thickness,x1+w/2,y1,x1+w/2,y2));
		children.add(line3 = new Line(color,thickness,x2,y1,x1,y2));
		children.add(line4 = new Line(color,thickness,x1,y1+h/2,x2,y1+h/2));
	}
	
	void setFill() {
		circle.setFill();
	}
	
	void setXY2(int newX, int newY) {
		super.setXY2(newX, newY);
		
		int w = Math.abs(x2-x1);
		int h = Math.abs(y2-y1);
		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);
		
		circle.setXY12(x+w/4,y+h/4,x+3*w/4,y+3*h/4);
		line1.setXY12(x1,y1,x2,y2);
		line2.setXY12(x+w/2,y,x+w/2,y+h);
		line3.setXY12(x2,y1,x1,y2);
		line4.setXY12(x,y+h/2,x+w,y+h/2);
	}
	
	void setColor(Color color) {
		super.setColor(color);
		
		for (Figure fig : children) {
			fig.setColor(color);
		}
		
	}
	
	void draw(Graphics g) {
		super.draw(g);
		
		for (Figure fig : children) {
			fig.draw(g);
		}
	}	
	
	void move(int dx, int dy) {
		super.move(dx, dy);
		
		for (Figure fig : children) {
			fig.move(dx, dy);
		}
		
	}
	
	Figure copy() {
		Kite newKite = new Kite(color,thickness,x1,y1,x2,y2);
		newKite.popup = popup;
		newKite.circle.setFillFlag(circle.getFillFlag());
		newKite.move(MOVE_DX, MOVE_DY);
		return newKite;
	}
	
}
