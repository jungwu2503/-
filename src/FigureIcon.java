import java.awt.*;

import javax.swing.*;

public class FigureIcon implements Icon {

	static int WIDTH = 16;
	static int HEIGHT = 16;
	String figureType;
	
	public FigureIcon(String figureType) {
		this.figureType = figureType;
	}
	
	public int getIconHeight() { return HEIGHT; }
	public int getIconWidth() { return WIDTH; }
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(Color.black);
		if (figureType.equals(DrawerView.figureType[0])) {
			g.drawOval(x+WIDTH/2-1, y+HEIGHT/2-1, 2, 2);
			g.fillOval(x+WIDTH/2-1, y+HEIGHT/2-1, 2, 2);
		} else if (figureType.equals(DrawerView.figureType[1])) {
			g.drawRect(x, y, WIDTH, HEIGHT);
		} else if (figureType.equals(DrawerView.figureType[2])) {
			g.drawRect(x, y, x+WIDTH, y+HEIGHT);
		} else if (figureType.equals(DrawerView.figureType[3])) {
			g.drawRect(x, y, WIDTH, HEIGHT);
		} else if (figureType.equals(DrawerView.figureType[4])) {
			g.drawLine(x+WIDTH*3/4, y, x+WIDTH/2, y+4);
			g.drawLine(x+WIDTH/4, y, x+WIDTH/2, y+4);
			g.drawRect(x+2, y+6, WIDTH-6, HEIGHT-10);
			g.drawRect(x+14, y+8, 1, 1);
			g.drawRect(x+14, y+11, 1, 1);
			g.drawRect(x, y+4, WIDTH, HEIGHT-6);
		} else if (figureType.equals(DrawerView.figureType[5])) {
			g.drawLine(x, y, x+WIDTH, y+HEIGHT);
			g.drawLine(x+WIDTH/2, y, x+WIDTH/2, y+HEIGHT);
			g.drawLine(x+WIDTH/2, y, x, y+HEIGHT);
			g.drawLine(x, y+HEIGHT/2, x+WIDTH, y+HEIGHT/2);
			g.drawOval(x+WIDTH/4, y+HEIGHT/4, WIDTH/2, HEIGHT/2);
			g.drawRect(x, y, WIDTH, HEIGHT);
		} else if (figureType.equals(DrawerView.figureType[5])) {
			Font oldFont = g.getFont();
			g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			g.drawString("T", x+1, y+15);
			g.setFont(oldFont);
		} else {
			Font oldFont = g.getFont();
			g.setFont(new Font("Times New Roman", Font.BOLD, 20));
			g.drawString("?", x+1, y+15);
			g.setFont(oldFont);
		}
	}

	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
