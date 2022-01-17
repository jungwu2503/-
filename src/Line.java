import java.awt.*;

import javax.swing.JMenuItem;

public class Line extends TwoPointFigure {
	
	boolean hasTail;
	boolean hasHead;
	
	Line(Color color) {
		super(color);
	}
	
	Line(Color color, int x, int y) {		 
		super(color,x,y);
	}
	
	Line(Color color, int x1, int y1, int x2, int y2) {	
		super(color,x1,y1,x2,y2);
	}
	
	void setArrow() {
		/*int x = x2-x1;
		int y = y2-y1;
		
		int headLine = (int) Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y));
		int tailLine = (int) Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1)); */
		
		//int x = (x2 - x1) / 2;
		//int y = (y2 - y1) / 2;
		
		
		if (!hasTail) {
			hasTail = true;
		} else if (!hasHead) {
			hasHead = true;
		} else if (hasTail) {
			hasTail = false;
		} else if (hasHead) {
			hasHead = false;
		}
		
	}
	
	void draw(Graphics g) {
		g.setColor(color);
		g.drawLine(x1, y1, x2, y2); 
		
		if (hasTail) {
			int regionWidth = 20;
			int x = x1;
			int y = y1;
			int w = x2 - x1;
			int h = y2 - y1;
			double alpha;
			double theta = Math.atan((double)h/(double)w);
			
			if (w > 0) {
				alpha = (Math.PI / 2 - (Math.PI / 4 + 0.3) - theta);
			} else {
				alpha = (Math.PI / 2 + (Math.PI / 4 + 0.3) - theta);
			}
			
			int dx = (int)(regionWidth * Math.cos(alpha));
			int dy = (int)(regionWidth * Math.sin(alpha));
			
			int x3 = x2 - dx;
			int y3 = y2 + dy;
			
			g.drawLine(x2, y2, x3, y3);
			
			if (w > 0) {
				alpha = (Math.PI / 2 + (Math.PI / 4 + 0.3) - theta);
			} else {
				alpha = (Math.PI / 2 - (Math.PI / 4 + 0.3) - theta);
			}
			
			dx = (int)(regionWidth * Math.cos(alpha));
			dy = (int)(regionWidth * Math.sin(alpha));
			
			int x4 = x2 + dx;
			int y4 = y2 - dy;
			
			g.drawLine(x2, y2, x4, y4);
		}
		
/*		if (hasTail) {
			double deltaX = (double)(x2-x1-100);
			double deltaY = (double)(y2-y1-100);
			double theta = Math.atan(deltaY/deltaX);
			
			double alpha = Math.PI/2.0 - theta;
			double length = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
			double wingLength = length / Math.sqrt(5.0);
			
			double dx = wingLength * Math.cos(alpha);
			double dy = wingLength * Math.sin(alpha);
		
			int x3 = x2 - (int)dx/3;
			int y3 = y2 + (int)dy/3;
			//g.drawLine(x1, y1, x3, y3);
			
			int x4 = x2 + (int)dx/3;
			int y4 = y2 - (int)dy/3;
			//g.drawLine(x1, y1, x4, y4);
			
			int[] xPoints = {x2, x3, x4};
			int[] yPoints = {y2, y3, y4};
			
			g.drawPolygon(xPoints, yPoints, 3);
		} */
/*		if (hasHead) {
			double deltaX = (double)(x1-x2-100);
			double deltaY = (double)(y1-y2-100);
			double theta = Math.atan(deltaY/deltaX);
			
			double alpha = Math.PI/2.0 - theta;
			double length = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
			double wingLength = length / Math.sqrt(5.0);
			
			double dx = wingLength * Math.cos(alpha);
			double dy = wingLength * Math.sin(alpha);
		
			int x3 = x1 - (int)dx/3;
			int y3 = y1 + (int)dy/3;
			//g.drawLine(x1, y1, x3, y3);
			
			int x4 = x1 + (int)dx/3;
			int y4 = y1 - (int)dy/3;
			//g.drawLine(x1, y1, x4, y4);
			
			int[] xPoints = {x2, x3, x4};
			int[] yPoints = {y2, y3, y4};
			
			g.drawPolygon(xPoints, yPoints, 3);
		} */
	}

	void makeRegion() {
		int regionWidth = 6;
		int x = x1;
		int y = y1;
		int w = x2 - x1;
		int h = y2 - y1;
		int sign_h = 1;
		if (h < 0) sign_h = -1;
		double angle;
		double theta = (w!=0) ? Math.atan((double)(h)/(double)(w)) : sign_h*Math.PI;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI /2);
		int dx = (int)(regionWidth * Math.cos(angle));
		int dy = (int)(regionWidth * Math.sin(angle));
		int xpoints[] = new int[4];
		int ypoints[] = new int[4];
		xpoints[0] = x + dx; ypoints[0] = y + dy;
		xpoints[1] = x - dx; ypoints[1] = y - dy;
		xpoints[2] = x + w - dx; ypoints[2] = y + h - dy;
		xpoints[3] = x + w + dx; ypoints[3] = y + h + dy;
		
		region = new Polygon(xpoints, ypoints, 4);
	}
	
	Figure copy() {
		Line newLine = new Line(color,x1,y1,x2,y2);
		newLine.popup = popup;
		newLine.move(MOVE_DX, MOVE_DY);
		return newLine;
	}
	
}