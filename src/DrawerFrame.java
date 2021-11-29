import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class DrawerFrame extends JFrame {
	DrawerView canvas;
	StatusBar statusBar;
	
	public void writePosition(String s) {
		// delegation
		statusBar.writePosition(s);
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
		//container.setBackground(Color.red);
		
		canvas = new DrawerView(this);
		container.add(canvas,"Center");
		statusBar = new StatusBar();
		container.add(statusBar,"South");
		
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
		
		JMenuItem figurePoint = new JMenuItem("Point (P)");
		figureMenu.add(figurePoint);
		figurePoint.addActionListener((evt) -> 
				canvas.setWhatToDraw(DrawerView.ID_POINT));
		
		JMenuItem figureBox = new JMenuItem(canvas.getBoxAction());
		figureMenu.add(figureBox);

		JMenuItem figureLine = new JMenuItem("Line (L)");
		figureMenu.add(figureLine);
		figureLine.addActionListener( (e) ->
				canvas.setWhatToDraw(DrawerView.ID_LINE) );

		JMenuItem figureCircle = new JMenuItem("Circle (C)");
		figureMenu.add(figureCircle);
		figureCircle.addActionListener( (e) ->
				canvas.setWhatToDraw(DrawerView.ID_CIRCLE) );

		JMenu toolMenu = new JMenu("도구(T)");
		menus.add(toolMenu);
		
		JMenuItem modalTool = new JMenuItem("Modal (M)");
		toolMenu.add(modalTool);
		modalTool.addActionListener( (e) -> {
					FigureDialog dialog = new FigureDialog("Figure Dialog", canvas);
					dialog.setModal(true);
					dialog.setVisible(true);
				});
		
		JMenuItem modalessTool = new JMenuItem("Modaless (S)");
		toolMenu.add(modalessTool);
		modalessTool.addActionListener( (e) -> {
					FigureDialog dialog = new FigureDialog("Figure Dialog", canvas);
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