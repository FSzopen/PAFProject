package ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.* ;

@SuppressWarnings("serial")
public class  ParameterItem  extends JMenuItem implements ActionListener {
	
	private final FilesMenu filesMenu;

	public ParameterItem (FilesMenu filesMenu) {
		super("Paramètres"); // Text of menu item
		this.filesMenu = filesMenu;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		filesMenu.getMenu().getMainFrame().getController().openParameters();
	}

}


