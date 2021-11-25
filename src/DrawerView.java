import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class DrawerView extends JPanel
					implements MouseListener, MouseMotionListener {
	
	public static int DRAW_BOX = 1;
	public static int DRAW_LINE = 2;
	private int whatToDraw;
	private Figure currentFigure;
	// polymorphic collection
	private ArrayList<Figure> figures = new ArrayList<Figure>();
	
	DrawerView() {
		whatToDraw = DRAW_BOX;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	void setWhatToDraw(int figureType) {
		whatToDraw = figureType;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int i = 0; i < figures.size(); i++) {
			Figure pFigure = figures.get(i);
			pFigure.draw(g);
		}		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Graphics g = getGraphics();		
		g.setXORMode(getBackground());
		currentFigure.drawing(g, e.getX(), e.getY());
	}

	private Figure find(int x, int y) {
		for (int i = 0; i < figures.size(); i++) {
			Figure pFigure = figures.get(i);
			if (pFigure.contains(x,y)) {
				return pFigure;
			}
		}	
		return null;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		currentFigure = find(x,y);
		
		if (currentFigure != null) {
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		} else {
			setCursor(Cursor.getDefaultCursor());
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (whatToDraw == DRAW_BOX) {
			currentFigure = new Box(e.getX(),e.getY());
		} else if (whatToDraw == DRAW_LINE) {
			currentFigure = new Line(e.getX(),e.getY());
		}		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Graphics g = getGraphics();
		currentFigure.setXY2(e.getX(), e.getY());
		currentFigure.draw(g);
		currentFigure.makeRegion();
		g.fillPolygon(currentFigure.region);
		figures.add(currentFigure);
		currentFigure = null;
	}

	public void addFigure(Figure newFigure) {
		figures.add(newFigure);
		repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
}
