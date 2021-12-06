import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class DrawerFrame extends JFrame {
	DrawerView canvas;
	StatusBar statusBar;
	FigureDialog dialog;
	
	public void writePosition(String s) {
		// delegation
		statusBar.writePosition(s);
	}
	
	public void writeFigureType(String s) {
		statusBar.writeFigureType(s); 
	}
	
	DrawerFrame() {
		setTitle("Drawer");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int screenHeight = d.height;
		int screenWidth = d.width;
		setSize(screenWidth*2/3, screenHeight*2/3);
		setLocation(screenWidth/6, screenHeight/6);
		Image img = tk.getImage("England.png");
		setIconImage(img);
		
		Container container = this.getContentPane();		
		statusBar = new StatusBar();
		container.add(statusBar,"South");
		canvas = new DrawerView(this);
		JScrollPane sp = new JScrollPane(canvas);
		container.add(sp,"Center");
		//sp.getVerticalScrollBar().setBlockIncrement(100);
		
		sp.registerKeyboardAction(
				new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						JScrollBar scrollBar = sp.getVerticalScrollBar();
						scrollBar.setValue(scrollBar.getValue() + scrollBar.getBlockIncrement());
					}
				}
				, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN
				, 0), JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		
		sp.registerKeyboardAction(
				new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						JScrollBar scrollBar = sp.getVerticalScrollBar();
						scrollBar.setValue(scrollBar.getValue() - scrollBar.getBlockIncrement());
					}
				}
				, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP
				, 0), JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		
		sp.registerKeyboardAction(
				new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						JScrollBar scrollBar = sp.getHorizontalScrollBar();
						scrollBar.setValue(scrollBar.getValue() + scrollBar.getBlockIncrement());
					}
				}
				, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN
				, InputEvent.CTRL_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		
		sp.registerKeyboardAction(
				new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						JScrollBar scrollBar = sp.getHorizontalScrollBar();
						scrollBar.setValue(scrollBar.getValue() - scrollBar.getBlockIncrement());
					}
				}
				, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP
				, InputEvent.CTRL_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		
		sp.registerKeyboardAction((evt) -> canvas.increaseHeight()				
				, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN
				, InputEvent.ALT_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		
		sp.registerKeyboardAction((evt) -> canvas.increaseWidth()
				, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP
				, InputEvent.ALT_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		
		JToolBar selectToolBar = new JToolBar();
		selectToolBar.add(canvas.getPointAction());
		selectToolBar.add(canvas.getBoxAction());
		selectToolBar.add(canvas.getLineAction());
		selectToolBar.add(canvas.getCircleAction());
		selectToolBar.add(canvas.getTVAction());
		selectToolBar.add(canvas.getKiteAction());
		container.add(selectToolBar,BorderLayout.NORTH);
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Dimension sz = canvas.getSize();
				String s = "" + sz.width + " X " + sz.height + " px";
				statusBar.writeSize(s);
			}
		});
		
		JMenuBar menus = new JMenuBar();
		setJMenuBar(menus);
		
		JMenu fileMenu = new JMenu("파일(F)");
		menus.add(fileMenu);
		
		JMenuItem newFile = new JMenuItem("새파일(N)");
		fileMenu.add(newFile);
		newFile.setMnemonic('N');
		newFile.setIcon(new ImageIcon("house.png"));
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
								InputEvent.CTRL_DOWN_MASK));
		newFile.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("새 파일(N)");
				}				
			});
		JMenuItem openFile = new JMenuItem("열기(O)");
		fileMenu.add(openFile);
		openFile.setMnemonic('O');
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
								InputEvent.CTRL_DOWN_MASK));
		openFile.setIcon(new ImageIcon("magnifier.png"));
		openFile.addActionListener( (e) ->
			System.out.println("열기(O)")			
		);
		JMenuItem saveFile = new JMenuItem("저장(S)");
		fileMenu.add(saveFile);
		saveFile.setMnemonic('S');
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
								InputEvent.CTRL_DOWN_MASK));
		saveFile.setIcon(new ImageIcon("key.png"));
		JMenuItem anotherFile = new JMenuItem("다른 이름으로 저장(A)");
		fileMenu.add(anotherFile);
		
		fileMenu.addSeparator();
		
		JMenuItem exit = new JMenuItem("종료(X)");
		fileMenu.add(exit);
		exit.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenu figureMenu = new JMenu("그림(F)");
		menus.add(figureMenu);
		
		JMenuItem figurePoint = new JMenuItem(canvas.getPointAction());
		figureMenu.add(figurePoint);
		
		JMenuItem figureBox = new JMenuItem(canvas.getBoxAction());
		figureMenu.add(figureBox);

		JMenuItem figureLine = new JMenuItem(canvas.getLineAction());
		figureMenu.add(figureLine);

		JMenuItem figureCircle = new JMenuItem(canvas.getCircleAction());
		figureMenu.add(figureCircle);

		JMenuItem figureTV = new JMenuItem(canvas.getTVAction());
		figureMenu.add(figureTV);
		
		JMenuItem figureKite = new JMenuItem(canvas.getKiteAction());
		figureMenu.add(figureKite);
		
		JMenu toolMenu = new JMenu("도구(T)");
		menus.add(toolMenu);
		
		JMenuItem modalTool = new JMenuItem("Modal (M)");
		toolMenu.add(modalTool);
		modalTool.addActionListener( (e) -> {
					if (dialog == null) {
						dialog = new FigureDialog("Figure Dialog", canvas);
					}
					dialog.setModal(true);
					dialog.setVisible(true);
				});
		
		JMenuItem modalessTool = new JMenuItem("Modaless (S)");
		toolMenu.add(modalessTool);
		modalessTool.addActionListener( (e) -> {
					if (dialog == null) {
						dialog = new FigureDialog("Figure Dialog", canvas);
					}
					dialog.setModal(false);
					dialog.setVisible(true);
				});

		
		JMenu helpMenu = new JMenu("도움말(H)");
		menus.add(helpMenu);
				
		JMenuItem infoHelp = new JMenuItem("Drawer 정보(I)");
		helpMenu.add(infoHelp);
		infoHelp.addActionListener( (e) ->
					JOptionPane.showMessageDialog(null,"Author: Kim\r\nCompany: Kims Company","Information",JOptionPane.INFORMATION_MESSAGE)
				);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}
	
}