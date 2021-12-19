import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

public class DrawerView extends JPanel
					implements MouseListener, MouseMotionListener {
	
	static class TextEditor extends JTextArea implements DocumentListener, MouseListener {
		private DrawerView canvas;
		private int INIT_WIDTH = 100;
		private int INIT_HEIGHT = 150;
		private int DELTA = 30;
		private Font font;
		private FontMetrics fm;
		int x;
		int y;
		int width;
		int height;
		TextEditor(DrawerView canvas) {
			super();
			this.canvas = canvas;
			setBackground(canvas.getBackground());
			getDocument().addDocumentListener(this);
		}
		
		public void start(int x, int y) {
			setText("");
			font = getFont();
			fm = canvas.getGraphics().getFontMetrics();
			this.x = x;
			this.y = y;
			this.width = INIT_WIDTH;
			this.height = INIT_HEIGHT;
			
			setBounds(x,y,width,height);
			Graphics g = canvas.getGraphics();
			g.setColor(Color.blue);
			g.drawRect(x, y, INIT_WIDTH-2, INIT_HEIGHT);
			setBorder(BorderFactory.createLineBorder(Color.blue));
			setCaretPosition(0);
			canvas.removeMouseListener(canvas);
			canvas.removeMouseMotionListener(canvas);
			
			canvas.add(this);
			requestFocus();
			
			canvas.addMouseListener(this);
		}
		
		public void insertUpdate(DocumentEvent e) {
			String text = getText();
			String[] lines = text.split("\n");
			
			int w;
			int maxWidth = 0;
			for (int i = 0; i < lines.length; i++) {
				String s = lines[i];
				w = fm.stringWidth(s);
				if (w > maxWidth) maxWidth = w;
			}
			if (maxWidth > width) {
				width = width + DELTA;
				setBounds(x,y,width,height);
			}
			int maxHeight = lines.length * fm.getHeight();
			if (maxHeight > height) {
				height = height + DELTA;
				setBounds(x,y,width,height);
			}			
		}
		
		public void removeUpdate(DocumentEvent e) {
		}
		public void changeUpdate(DocumentEvent e) {
		}
		
		public void mouseClicked(MouseEvent e) {
			canvas.remove(this);
			canvas.removeMouseListener(this);
			canvas.addMouseListener(canvas);
			canvas.addMouseMotionListener(canvas);
			
			String text = getText();
			String[] lines = text.split("\n");
			int w;
			
			if (lines.length == 1 && lines[0].equals("")) return;
			
			int maxWidth = 0;
			for (int i = 0; i < lines.length; i++) {
				String s = lines[i];
				w = fm.stringWidth(s);
				if (w > maxWidth) maxWidth = w;
			}
			int maxHeight = lines.length * fm.getHeight();
			
			Text newFigure = new Text(Color.black, x, y, x+maxWidth, y+maxHeight, lines);
			
			newFigure.setPopup(canvas.textPopup());
			canvas.addFigure(newFigure);
			
			canvas.repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static String[] figureType =
			{ "Point", "Star", "Box", "Line", "Circle", "TV", "Kite", "Text" };
	public static ArrayList<String> figureTypeNames = new ArrayList<String>();
	static {
		for (int i = 0; i < figureType.length; i++) {
			figureTypeNames.add(figureType[i]);
		}
	}
	
	public static int INIT_WIDTH = 3000;
	public static int INIT_HEIGHT = 1500;
	public static int DELTA = 500;
	
	public static int ID_POINT = 0;
	public static int ID_STAR = 1;
	public static int ID_BOX = 2;
	public static int ID_LINE = 3;
	public static int ID_CIRCLE = 4;
	public static int ID_TV = 5;
	public static int ID_KITE = 6;
	public static int ID_TEXT = 7;
	
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
	private Popup starPopup;
	private Popup boxPopup;
	private Popup linePopup;
	private Popup circlePopup;
	private Popup tvPopup;
	private Popup kitePopup;
	private Popup textPopup;
	private Popup[] popups = new Popup[figureType.length];
	
	private SelectAction pointAction;
	private SelectAction starAction;
	private SelectAction boxAction;
	private SelectAction lineAction;
	private SelectAction circleAction;
	private SelectAction tvAction;
	private SelectAction kiteAction;
	private SelectAction textAction;
	
	private DrawerFrame mainFrame;
	
	private double zoomRatio = 1.0;
	private int width = INIT_WIDTH;
	private int height = INIT_HEIGHT;
	
	private TextEditor textEditor;
	
	DrawerView(DrawerFrame mainFrame) {
		//this.statusBar = statusBar;
		setLayout(null);
		this.mainFrame = mainFrame;
		
		textEditor = new TextEditor(this);
		
		/*
		pointAction = new SelectAction("Point(P)",new ImageIcon("point.gif"), this, ID_POINT);
		boxAction = new SelectAction("Box(B)",new ImageIcon("box.gif"), this, ID_BOX);
		lineAction = new SelectAction("Line(L)",new ImageIcon("line.gif"), this, ID_LINE);
		circleAction = new SelectAction("Circle(c)",new ImageIcon("Circle.gif"), this, ID_CIRCLE);
		tvAction = new SelectAction("TV(t)",new ImageIcon("tv.gif"), this, ID_TV);
		kiteAction = new SelectAction("KITE(k)",new ImageIcon("kite.gif"), this, ID_KITE);
		textAction = new SelectAction("TEXT(X)",new ImageIcon("text.gif"), this, ID_TEXT);
		*/
		pointAction = new SelectAction("Point(P)",new FigureIcon(figureType[0]), this, ID_POINT);
		starAction = new SelectAction("Star(S)",new FigureIcon(figureType[1]), this, ID_STAR);		
		boxAction = new SelectAction("Box(B)",new FigureIcon(figureType[2]), this, ID_BOX);
		lineAction = new SelectAction("Line(L)",new FigureIcon(figureType[3]), this, ID_LINE);
		circleAction = new SelectAction("Circle(c)",new FigureIcon(figureType[4]), this, ID_CIRCLE);
		tvAction = new SelectAction("TV(t)",new FigureIcon(figureType[5]), this, ID_TV);
		kiteAction = new SelectAction("KITE(k)",new FigureIcon(figureType[6]), this, ID_KITE);
		textAction = new SelectAction("TEXT(X)",new FigureIcon(figureType[7]), this, ID_TEXT);
		
		mainPopup = new MainPopup(this);
		pointPopup = new FigurePopup(this, "Point", false);
		starPopup = new FigurePopup(this, "Star", true);
		boxPopup = new FigurePopup(this, "Box", true);
		linePopup = new FigurePopup(this, "Line", false);
		circlePopup = new FigurePopup(this, "Circle", true);
		tvPopup = new TVPopup(this);
		kitePopup = new FigurePopup(this, "Kite", true);
		textPopup = new FigurePopup(this, "Text", false);
				
		int i = 0;
		popups[i++] = pointPopup;
		popups[i++] = starPopup;
		popups[i++] = boxPopup;
		popups[i++] = linePopup;
		popups[i++] = circlePopup;
		popups[i++] = tvPopup;
		popups[i++] = kitePopup;
		popups[i++] = textPopup;
		
		actionMode = NOTHING;
		setWhatToDraw(ID_BOX);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setPreferredSize(new Dimension(width,height));
	}
	
	public ArrayList<Figure> getFigures() {
		return figures;
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
	
	SelectAction getStarAction() {
		return starAction;
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
	
	SelectAction getKiteAction() {
		return kiteAction;
	}
	
	SelectAction getTextAction() {
		return textAction;
	}
	
	Popup pointPopup() {
		return pointPopup;
	}
	
	Popup starPopup() {
		return starPopup;
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

	Popup kitePopup() {
		return kitePopup;
	}
	
	Popup textPopup() {
		return textPopup;
	}
	
	void setWhatToDraw(int id) {
		whatToDraw = id;
		mainFrame.writeFigureType(figureType[id]);
	}
	
	public void doFileNew() {
		figures.clear();
		repaint();
	}
	
	public void doOpen(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			figures = (ArrayList<Figure>)ois.readObject();
			ois.close();
			fis.close();
			
			for (Figure ptr : figures) {
				String figureTypeName = ptr.getClass().getName();
				int index = figureTypeNames.indexOf(figureTypeName);
				ptr.setPopup(popups[index]);
			}
			
			repaint();
		} catch (IOException ex) {
			
		} catch (ClassNotFoundException ex) {
			
		}
		
	}
	
	public void doSave(String fileName) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(figures);
			oos.flush();
			oos.close();
			fos.close();
		} catch(IOException ex) {
			
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		((Graphics2D)g).scale(zoomRatio, zoomRatio);
		for (int i = 0; i < figures.size(); i++) {
			Figure pFigure = figures.get(i);
			pFigure.draw(g);
		}		
		
	}

	public void zoom(int ratio) {
		zoomRatio = (double)ratio/100.0;
		repaint();
		removeMouseListener(this);
		removeMouseMotionListener(this);
		if (ratio == 100) {
			addMouseListener(this);
			addMouseMotionListener(this);
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
		} else if (whatToDraw == ID_STAR) { 
			selectedFigure = new Star(new Color(0,0,0),x,y);
			selectedFigure.setPopup(starPopup);
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
			selectedFigure = null;
			actionMode = NOTHING;
			return;
		} else if (whatToDraw == ID_KITE) {
			selectedFigure = new Kite(new Color(0,0,0),x,y);
			selectedFigure.setPopup(kitePopup);
		} else if (whatToDraw == ID_TEXT) {
			selectedFigure = null;
			actionMode = NOTHING;
			textEditor.start(x,y);
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
		
		if (selectedFigure == null) return;
		
		Graphics g = getGraphics();
		if (actionMode == DRAWING) {
			selectedFigure.setXY2(x, y);
		}
		
		selectedFigure.draw(g);
		addFigure(selectedFigure);
		selectedFigure = null;
	}

	public void removeFromFigures(Figure ptr) {
		selectedFigure = null;
		figures.remove(ptr);
		repaint();
	}
	
	public void removeFromFigures(int index) {
		if (index < 0) return;
		selectedFigure = null;
		figures.remove(index);
		repaint();
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
