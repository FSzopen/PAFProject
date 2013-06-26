package ui.menu;

import ui.MainFrame;

import javax.swing.* ;

@SuppressWarnings("serial")
public class Menu extends JMenuBar {

	private final FilesMenu filesMenu;
	private MainFrame mainFrame;

	public Menu(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		filesMenu = new FilesMenu(this);
		add(filesMenu); //crée et ajoute le menu
	}
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}
	
}