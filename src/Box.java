import java.awt.*;

public class Box extends TwoPointFigure {

	Box() {	
		super();
	}
	
	Box(int x, int y) {	
		super(x,y);
	}
	
	void draw(Graphics g) {
		int minX = Math.min(x1, x2);
		int minY = Math.min(y1, y2);
		int width = Math.abs(x2-x1);
		int height = Math.abs(y2-y1);
		
		g.drawRect(minX, minY, width, height);	

	}	
	
}
