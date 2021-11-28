import javax.swing.*;

public class FigurePopup extends Popup {
	
	FigurePopup(DrawerView view, String title, boolean fillFlag) {
		super(title);
		
		JMenuItem deleteItem = new JMenuItem("Delete");
		popupPtr.add(deleteItem);
		deleteItem.addActionListener((evt) -> 
			view.deleteFigure());

		JMenuItem copyItem = new JMenuItem("Copy");
		popupPtr.add(copyItem);
		copyItem.addActionListener((evt) -> 
			view.copyFigure());
		
		JMenu colorMenu = new JMenu("Colors");
		popupPtr.add(colorMenu);
		
		JMenuItem blackItem = new JMenuItem("Black");
		blackItem.addActionListener((evt) ->
			view.setBlackColor());
		colorMenu.add(blackItem);
		
		JMenuItem redItem = new JMenuItem("Red");
		redItem.addActionListener((evt) ->
			view.setRedColor());
		colorMenu.add(redItem);
		
		JMenuItem greenItem = new JMenuItem("Green");
		greenItem.addActionListener((evt) ->
			view.setGreenColor());
		colorMenu.add(greenItem);

		JMenuItem blueItem = new JMenuItem("Blue");
		blueItem.addActionListener((evt) ->
			view.setBlueColor());
		colorMenu.add(blueItem);
		
		JMenuItem chooserItem = new JMenuItem("Choose");
		chooserItem.addActionListener((evt) ->
			view.showColorChooser());
		colorMenu.add(chooserItem);
		
		if (fillFlag == true) {
			JMenuItem fillItem = new JMenuItem("Fill");
			fillItem.addActionListener((evt) ->
				view.fillFigure());
			popupPtr.add(fillItem);
		}
		
	}
	
}
