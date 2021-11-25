import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class FigureDialog extends JDialog {

	static class DialogPanel extends JPanel implements ActionListener{		
		static int TOP_GAP = 30;
		static int LEFT_GAP = 40;
		static int LABEL_WIDTH = 40;
		static int HEIGHT = 30;
		static int RIGHT_GAP = LEFT_GAP + 20;
		static int FIRST_ROW = TOP_GAP;
		static int BOTTOM_GAP = TOP_GAP + 10;
		static int X_LABEL_POS = LEFT_GAP;
		static int X_FIELD_POS = X_LABEL_POS + LABEL_WIDTH;
		static int FIELD_WIDTH = 80;
		static int CENTER_GAP = 20;
		static int Y_LABEL_POS = X_FIELD_POS + FIELD_WIDTH + CENTER_GAP;
		static int Y_FIELD_POS = Y_LABEL_POS + LABEL_WIDTH;
		static int PANEL_WIDTH = Y_FIELD_POS + FIELD_WIDTH + RIGHT_GAP;
		static int SECOND_ROW = 2*FIRST_ROW + HEIGHT;
		static int THIRD_ROW = 3*FIRST_ROW + 2*HEIGHT;
		static int FINAL_ROW = 4*FIRST_ROW + 3*HEIGHT + HEIGHT/2;
		static int BOX_WIDTH = 180;
		static int BUTTON_WIDTH = FIELD_WIDTH;
		static int OK_POS = (PANEL_WIDTH-2*BUTTON_WIDTH)/3;
		static int CANCEL_POS = OK_POS + BUTTON_WIDTH + OK_POS;
		static int PANEL_HEIGHT = FINAL_ROW + 2*HEIGHT + BOTTOM_GAP;
		
		JTextField x1Field;
		JTextField y1Field;
		JTextField x2Field;
		JTextField y2Field;
		String[] figures = { "Box", "Line" };
		JComboBox<String> box;
		
		JDialog dialog;
		DrawerView view;
		
		DialogPanel(JDialog dialog, DrawerView view) {
			this.view = view;
			this.dialog = dialog;
			this.setLayout(null);
			
			JLabel x1Label = new JLabel("x1: ");
			x1Label.setFont(new Font("Courier New",Font.BOLD,16));
			x1Label.setBounds(X_LABEL_POS,FIRST_ROW,LABEL_WIDTH,HEIGHT);
			add(x1Label);
			
			JLabel y1Label = new JLabel("y1: ");
			y1Label.setFont(new Font("Courier New",Font.BOLD,16));
			y1Label.setBounds(Y_LABEL_POS,FIRST_ROW,LABEL_WIDTH,HEIGHT);
			add(y1Label);
			
			JLabel x2Label = new JLabel("x2: ");
			x2Label.setFont(new Font("Courier New",Font.BOLD,16));
			x2Label.setBounds(X_LABEL_POS,SECOND_ROW,LABEL_WIDTH,HEIGHT);
			add(x2Label);
			
			JLabel y2Label = new JLabel("y2: ");
			y2Label.setFont(new Font("Courier New",Font.BOLD,16));
			y2Label.setBounds(Y_LABEL_POS,SECOND_ROW,LABEL_WIDTH,HEIGHT);
			add(y2Label);
			
			x1Field = new JTextField("0");
			x1Field.setBounds(X_FIELD_POS,FIRST_ROW,FIELD_WIDTH,HEIGHT);
			x1Field.setHorizontalAlignment(JTextField.RIGHT);
			add(x1Field);
			
			y1Field = new JTextField("0");
			y1Field.setBounds(Y_FIELD_POS,FIRST_ROW,FIELD_WIDTH,HEIGHT);
			y1Field.setHorizontalAlignment(JTextField.RIGHT);
			add(y1Field);
			
			x2Field = new JTextField("0");
			x2Field.setBounds(X_FIELD_POS,SECOND_ROW,FIELD_WIDTH,HEIGHT);
			x2Field.setHorizontalAlignment(JTextField.RIGHT);
			add(x2Field);
			
			y2Field = new JTextField("0");
			y2Field.setBounds(Y_FIELD_POS,SECOND_ROW,FIELD_WIDTH,HEIGHT);
			y2Field.setHorizontalAlignment(JTextField.RIGHT);
			add(y2Field);
		
			box = new JComboBox<String>(figures);
			box.setBounds((PANEL_WIDTH-BOX_WIDTH)/2, THIRD_ROW, BOX_WIDTH, HEIGHT);
			add(box);
			
			JButton ok = new JButton("OK");
			ok.setBounds(OK_POS,FINAL_ROW,BUTTON_WIDTH,HEIGHT);
			ok.addActionListener(this);
			add(ok);
			JButton cancel = new JButton("Cancel");
			cancel.setBounds(CANCEL_POS,FINAL_ROW,BUTTON_WIDTH,HEIGHT);
			cancel.addActionListener(this);
			add(cancel);
		}
		
		private void onOK() {
			int x1,y1,x2,y2;
			String selection = (String) box.getSelectedItem();
			try {
				x1 = Integer.parseInt(x1Field.getText());
				y1 = Integer.parseInt(y1Field.getText());
				x2 = Integer.parseInt(x2Field.getText());
				y2 = Integer.parseInt(y2Field.getText());
				
			} catch (Exception e) {
				System.out.println("Invalid text! Try again!");
				return;
			}
			Figure newFigure = null;
			if (selection.equals("Box")) {
				newFigure = new Box(x1,y1,x2,y2);
			} else if (selection.equals("Line")) {
				newFigure = new Line(x1,y1,x2,y2);
			}
			view.addFigure(newFigure);
			
			x1Field.setText("0");
			y1Field.setText("0");
			x2Field.setText("0");
			y2Field.setText("0");
		}
		private void onCancel() {
			dialog.setVisible(false);
		}		
		public void actionPerformed(ActionEvent event) {
			String name = event.getActionCommand();
			if (name.equals("OK")) {
				onOK();
			} else if (name.equals("Cancel")) {
				onCancel();
			}
		}
		
		public Dimension getSize() {
			return new Dimension(PANEL_WIDTH,PANEL_HEIGHT);
		}
	}
	
	FigureDialog(String title, DrawerView view) {
		super((JFrame)null,title);
		setLocation(200,300);
		
		
		Container container = getContentPane();
		JPanel panel = new DialogPanel(this,view);
		container.add(panel);
		
		setSize(panel.getSize());
	}
	
}
