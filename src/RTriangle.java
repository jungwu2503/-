import java.awt.*;

public class RTriangle extends TwoPointFigure {
	
	protected boolean fillFlag;
	
	RTriangle(Color color) {
		super(color);
		fillFlag = false;
	}
	
	RTriangle(Color color, int x, int y) {		 
		super(color,x,y);
		fillFlag = false;
	}
	
	RTriangle(Color color, int x1, int y1, int x2, int y2) {	
		super(color,x1,y1,x2,y2);
		fillFlag = false;
	}
	
	void setFill() {
		fillFlag = !fillFlag;	
	}
	
	void draw(Graphics g) {
		g.setColor(color);
		//g.drawLine(x1, y1, x2, y2); 
		
		double deltaX = (double)(x2-x1);
		double deltaY = (double)(y2-y1);
		double theta = Math.atan(deltaY/deltaX);
		
		double alpha = Math.PI/2.0 - theta;
		double length = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
		double wingLength = length / Math.sqrt(3.0);
		
		double dx = wingLength * Math.cos(alpha);
		double dy = wingLength * Math.sin(alpha);
	
		int x3 = x1 - (int)dx;
		int y3 = y1 + (int)dy;
		//g.drawLine(x1, y1, x3, y3);
		
		int x4 = x1 + (int)dx;
		int y4 = y1 - (int)dy;
		//g.drawLine(x1, y1, x4, y4);
		
		int[] xPoints = {x2, x3, x4};
		int[] yPoints = {y2, y3, y4};
		
		g.drawPolygon(xPoints, yPoints, 3);
		
		if (fillFlag == true) {
			g.fillPolygon(xPoints, yPoints, 3);
		}
		
	}

	void makeRegion() {
		
		double deltaX = (double)(x2-x1);
		double deltaY = (double)(y2-y1);
		double theta = Math.atan(deltaY/deltaX);
		
		double alpha = Math.PI/2.0 - theta;
		double length = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
		double wingLength = length / Math.sqrt(3.0);
		
		double dx = wingLength * Math.cos(alpha);
		double dy = wingLength * Math.sin(alpha);
	
		int x3 = x1 - (int)dx;
		int y3 = y1 + (int)dy;
		
		int x4 = x1 + (int)dx;
		int y4 = y1 - (int)dy;
		
		int[] xPoints = {x2, x3, x4};
		int[] yPoints = {y2, y3, y4};		
		
		region = new Polygon(xPoints, yPoints, 3);
	}
	
	Figure copy() {
		RTriangle newRTriangle = new RTriangle(color,x1,y1,x2,y2);
		newRTriangle.popup = popup;
		newRTriangle.fillFlag = fillFlag;
		newRTriangle.move(MOVE_DX, MOVE_DY);
		return newRTriangle;
	}
	
}