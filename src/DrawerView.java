import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

public class DrawerView extends JPanel
					implements MouseListener, MouseMotionListener {

	int startX;
	int startY;
	int oldX;
	int oldY;
	
	DrawerView() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int minX = Math.min(startX, oldX);
		int minY = Math.min(startY, oldY);
		int width = Math.abs(oldX-startX);
		int height = Math.abs(oldY-startY);	
		
		Graphics g = getGraphics();
		g.setXORMode(getBackground());
		// white : white -> white
		// white : black -> black
		// black : white -> black
		// black : black -> white
		g.drawRect(minX, minY, width, height);	
		//g.setPaintMode();
	///
		int endX = e.getX();
		int endY = e.getY();
		
		minX = Math.min(startX, endX);
		minY = Math.min(startY, endY);
		width = Math.abs(endX-startX);
		height = Math.abs(endY-startY);
		
		g.drawRect(minX, minY, width, height);
		
		oldX = endX;
		oldY = endY;
		
		/*int minX = Math.min(startX, oldX);
		int minY = Math.min(startY, oldY);
		int width = Math.abs(oldX-startX);
		int height = Math.abs(oldY-startY);	
		
		Graphics g = getGraphics();
		g.setColor(getBackground());
		
		g.drawRect(minX, minY, width, height);	
		
	///
		int endX = e.getX();
		int endY = e.getY();
		
		minX = Math.min(startX, endX);
		minY = Math.min(startY, endY);
		width = Math.abs(endX-startX);
		height = Math.abs(endY-startY);
		
		g.setColor(Color.black);
		g.drawRect(minX, minY, width, height);
		
		oldX = endX;
		oldY = endY;		
		*/		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startX = e.getX();
		startY = e.getY();
		
		oldX = startX;
		oldY = startY;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int endX = e.getX();
		int endY = e.getY();
		
		int minX = Math.min(startX, endX);
		int minY = Math.min(startY, endY);
		int width = Math.abs(endX-startX);
		int height = Math.abs(endY-startY);
		
		Graphics g = getGraphics();
		g.drawRect(minX, minY, width, height);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
}
