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
	private Figure selectedFigure;
	private ArrayList<Figure> figures = new ArrayList<Figure>();
	
	private int currentX;
	private int currentY;
	
	Popup mainPopup;
	Popup boxPopup;
	Popup linePopup;
	
	DrawerView() {
		mainPopup = new MainPopup(this);
		boxPopup = new FigurePopup(this, "Box", true);
		linePopup = new FigurePopup(this, "Line", false);
		
		actionMode = NOTHING;
		whatToDraw = DRAW_BOX;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	Popup boxPopup() {
		return boxPopup;
	}
	
	Popup linePopup() {
		return linePopup;
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
			selectedFigure.drawing(g, x, y);
		} else if (actionMode == MOVING) {
			selectedFigure.move(g, x-currentX, y-currentY);
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
		
		selectedFigure = find(x,y);
		
		if (selectedFigure != null) {
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
		
		if (e.getButton() == MouseEvent.BUTTON3) {
			actionMode = NOTHING;
			return;
		}
		selectedFigure = find(x,y);
		if (selectedFigure != null) {
			actionMode = MOVING;
			currentX = x;
			currentY = y;
			figures.remove(selectedFigure);
			repaint();
			return;
		}
		
		if (whatToDraw == DRAW_BOX) {
			selectedFigure = new Box(new Color(0,0,0),x,y);
			selectedFigure.setPopup(boxPopup);
		} else if (whatToDraw == DRAW_LINE) {
			selectedFigure = new Line(Color.black,x,y);
			selectedFigure.setPopup(linePopup);
		}
		actionMode = DRAWING;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if (e.isPopupTrigger()) {
			selectedFigure = find(x,y);
			if (selectedFigure == null) {
				mainPopup.popup(this,x,y);
			} else {
				selectedFigure.popup(this,x,y);
			}
			
			return;
		}
		Graphics g = getGraphics();
		if (actionMode == DRAWING) {
			selectedFigure.setXY2(x, y);
		}
		
		selectedFigure.draw(g);
		addFigure(selectedFigure);
		selectedFigure = null;
	}

	public void addFigure(Figure newFigure) {
		newFigure.makeRegion();
		figures.add(newFigure);
		repaint();
	}
	
	public void copyFigure() {
		if (selectedFigure == null) return;
		Figure newFigure = selectedFigure.copy();
		addFigure(newFigure);
		selectedFigure = newFigure;
		repaint();
	}
	
	public void deleteFigure() {
		if (selectedFigure == null) return;
		figures.remove(selectedFigure);
		selectedFigure = null;
		repaint();
	}
	
	public void fillFigure() {
		if (selectedFigure == null) return;
		selectedFigure.setFill();
		repaint();
	}
	
	/*
	public void fillFigure() {
		if (selectedFigure == null) return;
		if (selectedFigure instanceof Box) {
			// down casting
			Box pBox = (Box)selectedFigure;
			pBox.setFill();
		}
		repaint();
	} */
	
	public void setColorForSelectedFigure(Color color) {
		if (selectedFigure == null) return;
		selectedFigure.setColor(color);
		repaint();
	}
	
	public void showColorChooser() {
		Color color = JColorChooser.showDialog(null,
									"Color Chooser", Color.black);
		setColorForSelectedFigure(color);
	}
	
	public void setBlackColor() {
		setColorForSelectedFigure(Color.black);
	}
		
	public void setRedColor() {
		setColorForSelectedFigure(Color.red);
	}
	
	public void setGreenColor() {
		setColorForSelectedFigure(Color.green);
	}
	
	public void setBlueColor() {
		setColorForSelectedFigure(Color.blue);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
}
