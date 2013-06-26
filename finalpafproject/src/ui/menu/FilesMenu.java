package ui.menu;

import javax.swing.* ;

@SuppressWarnings("serial")
public class FilesMenu extends JMenu {

	private Menu menu;

	public FilesMenu(Menu menu) {
		super("Fichier"); 
		this.menu = menu;
		add(new ParameterItem(this));
		add(new ImportDataItem(this));
		add(new QuitItem(this));
	}
	
	public Menu getMenu() {
		return menu;
	}

}

