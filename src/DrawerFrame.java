import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class DrawerFrame extends JFrame {
	
	DrawerFrame() {
		setTitle("Drawer");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int screenHeight = d.height;
		int screenWidth = d.width;
		setSize(screenWidth*2/3, screenHeight*2/3);
		setLocation(screenWidth/6, screenHeight/6);		
		
		JMenuBar menus = new JMenuBar();
		setJMenuBar(menus);
		
		JMenu fileMenu = new JMenu("파일(F)");
		menus.add(fileMenu);
		
		JMenuItem newFile = new JMenuItem("새파일(N)");
		fileMenu.add(newFile);
		newFile.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("새 파일(N)");
				}				
			});
		JMenuItem openFile = new JMenuItem("열기(O)");
		fileMenu.add(openFile);
		JMenuItem saveFile = new JMenuItem("저장(S)");
		fileMenu.add(saveFile);
		JMenuItem anotherFile = new JMenuItem("다른 이름으로 저장(A)");
		fileMenu.add(anotherFile);
		JMenuItem exit = new JMenuItem("종료(X)");
		fileMenu.add(exit);
		exit.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}
	
}