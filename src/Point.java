import java.awt.Color;
import java.awt.Graphics;

public class Point extends OnePointFigure {

	Point(Color color, int thickness) {	
		super(color, thickness);
	}
	
	Point(Color color, int thickness, int x, int y) {	
		super(color,thickness,x,y);
	}
	
	void draw(Graphics g) {		
		g.setColor(color);
		g.drawOval(x1-GAP, y1-GAP, 2*GAP-1, 2*GAP-1);
		g.fillOval(x1-GAP, y1-GAP, 2*GAP, 2*GAP);
	}	
	
	Figure copy() {
		Point newPoint = new Point(color,thickness,x1,y1);
		newPoint.popup = popup;
		newPoint.move(MOVE_DX, MOVE_DY);
		return newPoint;
	}
	
}