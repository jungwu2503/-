import javax.swing.*;

class LinePopup extends FigurePopup {
	
	LinePopup(DrawerView view) {
		super(view,"Line",false);		
		
		JCheckBox arrowBox = new JCheckBox("Arrow?");
		JMenuItem arrowItem = new JMenuItem("ARROW");
		arrowItem.add(arrowBox);
		arrowBox.addActionListener((evt) -> 
			view.setArrow());
		popupPtr.add(arrowItem);
		
	}
	
}