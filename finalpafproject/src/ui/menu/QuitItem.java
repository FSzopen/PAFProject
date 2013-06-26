package ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.* ;

@SuppressWarnings("serial")
public class  QuitItem extends JMenuItem implements ActionListener {
	
	private final FilesMenu filesMenu;

	public QuitItem(FilesMenu filesMenu) {
		super("Quitter"); // Text of menu item
		this.filesMenu = filesMenu;
		addActionListener(this);   
	}

	public void actionPerformed(ActionEvent evt) {
		filesMenu.getMenu().getMainFrame().getController().exit();
	}

}


