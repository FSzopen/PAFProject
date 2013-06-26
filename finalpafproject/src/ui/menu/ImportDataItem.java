package ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.* ;
import javax.swing.filechooser.FileNameExtensionFilter;

public class  ImportDataItem  extends JMenuItem implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final FilesMenu filesMenu;

	public ImportDataItem (FilesMenu filesMenu) {
		super("Importer");
		this.filesMenu = filesMenu;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser importer = new JFileChooser("."); //select a file in the computer
		importer.addChoosableFileFilter(new FileNameExtensionFilter("Text txt", "txt")); //display txt file
		if(importer.showDialog(this,"selectionner votre rapport")==JFileChooser.APPROVE_OPTION) {
			String path = importer.getSelectedFile().getPath();
			filesMenu.getMenu().getMainFrame().getController().importData(path);
		}
	}
	
}