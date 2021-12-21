import java.awt.*;

public class Isosceles extends Box {

	protected boolean fillFlag;
	protected boolean EAST = false;
	protected boolean WEST = false;
	protected boolean SOUTH = false;
	protected boolean NORTH = false;
	
	Isosceles(Color color) {
		super(color);
		fillFlag = false;
	}
	
	Isosceles(Color color, int x, int y) {	
		super(color,x,y);
		fillFlag = false;
	}
	
	Isosceles(Color color, int x1, int y1, int x2, int y2) {	
		super(color,x1,y1,x2,y2);
		fillFlag = false;
	}
	
	void setFill() {
		fillFlag = !fillFlag;		
	}
	
	void draw(Graphics g) {
		int minX = Math.min(x1, x2);
		int minY = Math.min(y1, y2);
		int width = Math.abs(x2-x1);
		int height = Math.abs(y2-y1);
		
		g.setColor(color);
		//g.drawLine(minX+width/2, minY, minX, minY+height);
		//g.drawLine(minX+width/2, minY, minX+width, minY+height);
		//g.drawLine(minX, minY+height, minX+width, minY+height);
		int[] xPoints = { minX+width/2, minX, minX+width };
		int[] yPoints = { minY, minY+height, minY+height };
		
		//g.drawPolygon(xPoints, yPoints, yPoints.length); -----default
		
		
		
		//Graphics2D g2d = (Graphics2D)g;
		if (EAST) {
			xPoints[0] = minX;
			xPoints[1] = minX;
			xPoints[2] = minX+width;
			yPoints[0] = minY;
			yPoints[1] = minY+height;
			yPoints[2] = minY+height/2;
			g.drawPolygon(xPoints, yPoints, yPoints.length);
		} else if (WEST) {
			xPoints[0] = minX;
			xPoints[1] = minX+width;
			xPoints[2] = minX+width;
			yPoints[0] = minY+height/2;
			yPoints[1] = minY;
			yPoints[2] = minY+height;
			g.drawPolygon(xPoints, yPoints, yPoints.length);
		} else if (SOUTH) {
			xPoints[0] = minX;
			xPoints[1] = minX+width;
			xPoints[2] = minX+width/2;
			yPoints[0] = minY;
			yPoints[1] = minY;
			yPoints[2] = minY+height;
			g.drawPolygon(xPoints, yPoints, yPoints.length);
		} else if (NORTH) {
			g.drawPolygon(xPoints, yPoints, yPoints.length);
		} else {
			g.drawPolygon(xPoints, yPoints, yPoints.length);
		}
		
		
		if (fillFlag == true) {
			g.fillPolygon(xPoints, yPoints, yPoints.length);
		}
	}	
	
	Figure copy() {
		Isosceles newIsosceles = new Isosceles(color,x1,y1,x2,y2);
		newIsosceles.popup = popup;
		newIsosceles.fillFlag = fillFlag;
		newIsosceles.move(MOVE_DX, MOVE_DY);
		return newIsosceles;
	}
	
	void makeRegion() {
		int minX = Math.min(x1, x2);
		int minY = Math.min(y1, y2);
		int width = Math.abs(x2-x1);
		int height = Math.abs(y2-y1);
		
		int[] xPoints = { minX+width/2, minX, minX+width };
		int[] yPoints = { minY, minY+height, minY+height };
		
		region = new Polygon(xPoints, yPoints, 3);
	}
	
	Figure orientation(String cardinal) {
		if (cardinal.equals("East")) {
			EAST = true;
			WEST = false;
			SOUTH = false;
			NORTH = false;
		} else if (cardinal.equals("West")) {
			EAST = false;
			WEST = true;
			SOUTH = false;
			NORTH = false;
		} else if (cardinal.equals("South")) {
			EAST = false;
			WEST = false;
			SOUTH = true;
			NORTH = false;
		} else if (cardinal.equals("North")) {
			EAST = false;
			WEST = false;
			SOUTH = false;
			NORTH = true;
		} 
		Isosceles newIsosceles = new Isosceles(color,x1,y1,x2,y2);
		return newIsosceles;
	}
	
}