import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class DrawerView extends JPanel
					implements MouseListener, MouseMotionListener {
	
	public static String[] figureType =
			{ "Point", "Box", "Line", "Circle", "TV" };
	
	public static int INIT_WIDTH = 3000;
	public static int INIT_HEIGHT = 1500;
	public static int DELTA = 500;
	
	public static int ID_POINT = 0;
	public static int ID_BOX = 1;
	public static int ID_LINE = 2;
	public static int ID_CIRCLE = 3;
	public static int ID_TV = 4;
	
	public static int NOTHING = 0;
	public static int DRAWING = 1;
	public static int MOVING = 2;
	
	private int actionMode;
	private int whatToDraw;
	private Figure selectedFigure;
	private ArrayList<Figure> figures = new ArrayList<Figure>();
	
	private int currentX;
	private int currentY;
	
	private Popup mainPopup;
	private Popup pointPopup;
	private Popup boxPopup;
	private Popup linePopup;
	private Popup circlePopup;
	private Popup tvPopup;
	
	private SelectAction pointAction;
	private SelectAction boxAction;
	private SelectAction lineAction;
	private SelectAction circleAction;
	private SelectAction tvAction;
	
	private DrawerFrame mainFrame;
	
	private int width = INIT_WIDTH;
	private int height = INIT_HEIGHT;
	
	DrawerView(DrawerFrame mainFrame) {
		//this.statusBar = statusBar;
		this.mainFrame = mainFrame;
		
		pointAction = new SelectAction("Point(P)",new ImageIcon("point.gif"), this, ID_POINT);
		boxAction = new SelectAction("Box(B)",new ImageIcon("box.gif"), this, ID_BOX);
		lineAction = new SelectAction("Line(L)",new ImageIcon("line.gif"), this, ID_LINE);
		circleAction = new SelectAction("Circle(c)",new ImageIcon("Circle.gif"), this, ID_CIRCLE);
		tvAction = new SelectAction("TV(t)",new ImageIcon("tv.gif"), this, ID_TV);
		
		mainPopup = new MainPopup(this);
		pointPopup = new FigurePopup(this, "Point", false);
		boxPopup = new FigurePopup(this, "Box", true);
		linePopup = new FigurePopup(this, "Line", false);
		circlePopup = new FigurePopup(this, "Circle", true);
		tvPopup = new TVPopup(this);
				
		actionMode = NOTHING;
		setWhatToDraw(ID_BOX);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setPreferredSize(new Dimension(width,height));
	}
	
	public void increaseHeight() {
		height += DELTA;
		setPreferredSize(new Dimension(width,height));
	}
	
	public void increaseWidth() {
		width
		+= DELTA;
		setPreferredSize(new Dimension(width,height));
	}
	
	SelectAction getPointAction() {
		return pointAction;
	}
	
	SelectAction getBoxAction() {
		return boxAction;
	}	
	
	SelectAction getLineAction() {
		return lineAction;
	}
	
	SelectAction getCircleAction() {
		return circleAction;
	}
	
	SelectAction getTVAction() {
		return tvAction;
	}
	
	Popup pointPopup() {
		return pointPopup;
	}
	
	Popup boxPopup() {
		return boxPopup;
	}
	
	Popup linePopup() {
		return linePopup;
	}
	
	Popup circlePopup() {
		return circlePopup;
	}
	
	Popup tvPopup() {
		return tvPopup;
	}
	
	void setWhatToDraw(int id) {
		whatToDraw = id;
		mainFrame.writeFigureType(figureType[id]);
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
		
		//statusBar.writePosition("["+x+","+y+"]");
		mainFrame.writePosition("["+x+","+y+"]");
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
		
		if (whatToDraw == ID_POINT) {
			selectedFigure = new Point(new Color(0,0,0),x,y);
			selectedFigure.setPopup(pointPopup);
		} else if (whatToDraw == ID_BOX) {
			selectedFigure = new Box(new Color(0,0,0),x,y);
			selectedFigure.setPopup(boxPopup);
		} else if (whatToDraw == ID_LINE) {
			selectedFigure = new Line(Color.black,x,y);
			selectedFigure.setPopup(linePopup);
		} else if (whatToDraw == ID_CIRCLE) {
			selectedFigure = new Circle(Color.black,x,y);
			selectedFigure.setPopup(circlePopup);
		} else if (whatToDraw == ID_TV) {
			selectedFigure = new TV(Color.black,x,y,true);
			selectedFigure.setPopup(tvPopup);
			addFigure(selectedFigure);
			return;
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
	
	public void onOffTV() {
		if (selectedFigure == null) return;
		if (selectedFigure instanceof TV) {
			TV tv = (TV)selectedFigure;
			tv.pressPowerButton();
			repaint();
		}		
	}

	public void setAntenna() {
		if (selectedFigure == null) return;
		if (selectedFigure instanceof TV) {
			TV tv = (TV)selectedFigure;
			tv.setAntenna();
			repaint();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
}
