import javax.swing.*;

public class MainPopup extends Popup {
	
	MainPopup(DrawerView view) {
		super("±×¸²");
		JMenuItem boxItem = new JMenuItem("Box(B)");
		popupPtr.add(boxItem);
		boxItem.addActionListener((evt) -> 
				view.setWhatToDraw(DrawerView.DRAW_BOX));
		JMenuItem lineItem = new JMenuItem("Line(L)");
		popupPtr.add(lineItem);
		lineItem.addActionListener((evt) -> 
				view.setWhatToDraw(DrawerView.DRAW_LINE));
		
		
	}
	
}
