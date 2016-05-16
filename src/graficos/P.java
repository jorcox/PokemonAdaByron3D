package graficos;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class P {
public static void main(String[] args) {
	//Where the GUI is created:
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem;

	//Create the menu bar.
	menuBar = new JMenuBar();

	//Build the first menu.
	menu = new JMenu("File");
	menu.setMnemonic(KeyEvent.VK_F);
	menu.getAccessibleContext().setAccessibleDescription(
	        "File menu");
	menuBar.add(menu);

	//JMenuItems show the menu items
	menuItem = new JMenuItem("New",
	                         new ImageIcon("images/new.gif"));
	menuItem.setMnemonic(KeyEvent.VK_N);
	menu.add(menuItem);

	// add a separator
	menu.addSeparator();

	menuItem = new JMenuItem("Pause", new ImageIcon("images/pause.gif"));
	menuItem.setMnemonic(KeyEvent.VK_P);
	menu.add(menuItem);

	menuItem = new JMenuItem("Exit", new ImageIcon("images/exit.gif"));
	menuItem.setMnemonic(KeyEvent.VK_E);
	menu.add(menuItem);
	//1. Create the frame.
	JFrame frame = new JFrame("FrameDemo");

	//2. Optional: What happens when the frame closes?
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//3. Create components and put them in the frame.
	//...create emptyLabel...
	frame.getContentPane().add(new JLabel(), BorderLayout.CENTER);
	
	// add menu bar to frame
		frame.setJMenuBar(menuBar);

	//4. Size the frame.
	frame.pack();

	//5. Show it.
	frame.setVisible(true);

	
}
}
