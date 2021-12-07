import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

public class TableDialog extends JDialog {

	static class FigureTableModel implements TableModel{

		@Override
		public int getRowCount() {
			return 5;
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return "Hello";
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return String.class;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return "XXX"+rowIndex+","+columnIndex;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			
		}

		@Override
		public void addTableModelListener(TableModelListener l) {
			
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
			
		}
		
	}
	
	static class FigureTable extends JTable {
		FigureTable(DrawerView view) {
			super();
			setModel(new FigureTableModel());
		}
	}
	
	static class DialogPanel extends JPanel implements ActionListener{		
		JDialog dialog;
		DrawerView view;
		JButton done;
		JButton remove;
		JTable table;
		
		DialogPanel(JDialog dialog, DrawerView view) {
			this.view = view;
			this.dialog = dialog;
			setLayout(new BorderLayout());
			
			table = new FigureTable(view);
			JScrollPane sp = new JScrollPane(table);
			add(sp,BorderLayout.CENTER);
			
			JPanel bottom = new JPanel();
			bottom.add(remove = new JButton("Remove"));
			bottom.add(done = new JButton("Done"));
			add(bottom,BorderLayout.SOUTH);
			
			done.addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == done) {
				dialog.setVisible(false);
			}
		}
		
	}
	
	TableDialog(String title, DrawerView view) {
		super((JFrame)null,title);
		setLocation(200,300);
		setSize(400,300);
		
		Container container = getContentPane();
		JPanel panel = new DialogPanel(this,view);
		container.add(panel);		
	}
	
}
