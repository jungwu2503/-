import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class DrawerView extends JPanel
					implements MouseListener, MouseMotionListener {
	
	public static int DRAW_BOX = 1;
	public static int DRAW_LINE = 2;
	
	public static int NOTHING = 0;
	public static int DRAWING = 1;
	public static int MOVING = 2;
	
	private int actionMode;
	private int whatToDraw;
	private Figure currentFigure;
	// polymorphic collection
	private ArrayList<Figure> figures = new ArrayList<Figure>();
	
	private int currentX;
	private int currentY;
	
	DrawerView() {
		actionMode = NOTHING;
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
		int x = e.getX();
		int y = e.getY();
		Graphics g = getGraphics();		
		g.setXORMode(getBackground());
		if (actionMode == DRAWING) {
			currentFigure.drawing(g, x, y);
		} else if (actionMode == MOVING) {
			currentFigure.move(g, x-currentX, y-currentY);
			currentX = x;
			currentY = y;
		}
		
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
		int x = e.getX();
		int y = e.getY();
		
		currentFigure = find(x,y);
		if (currentFigure != null) {
			actionMode = MOVING;
			currentX = x;
			currentY = y;
			figures.remove(currentFigure);
			repaint();
			return;
		}
		
		if (whatToDraw == DRAW_BOX) {
			currentFigure = new Box(x,y);
		} else if (whatToDraw == DRAW_LINE) {
			currentFigure = new Line(x,y);
		}
		actionMode = DRAWING;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if (e.isPopupTrigger()) {
			MainPopup popup = new MainPopup(this);
			popup.popup(this,x,y);
			return;
		}
		Graphics g = getGraphics();
		if (actionMode == DRAWING) {
			currentFigure.setXY2(x, y);
		}
		
		currentFigure.draw(g);
		addFigure(currentFigure);
		currentFigure = null;
	}

	public void addFigure(Figure newFigure) {
		newFigure.makeRegion();
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
