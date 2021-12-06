import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class MainPopup extends Popup {
	
	MainPopup(DrawerView view) {
		super("±×¸²");
		
		JMenuItem pointItem = new JMenuItem(view.getPointAction());
		popupPtr.add(pointItem);
		
		JMenuItem boxItem = new JMenuItem(view.getBoxAction());
		popupPtr.add(boxItem);
		
		JMenuItem lineItem = new JMenuItem(view.getLineAction());
		popupPtr.add(lineItem);
		
		JMenuItem circleItem = new JMenuItem(view.getCircleAction());
		popupPtr.add(circleItem);
		
		JMenuItem tvItem = new JMenuItem(view.getTVAction());
		popupPtr.add(tvItem);
		
		JMenuItem kiteItem = new JMenuItem(view.getKiteAction());
		popupPtr.add(kiteItem);
	}
	
}
