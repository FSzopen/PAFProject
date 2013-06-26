package ui.buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ResultPerUsernameButton extends JButton implements MouseListener {

	private final ButtonsRowPanel buttonsRowPanel;

	public ResultPerUsernameButton(ButtonsRowPanel buttonsRowPanel) {
		super("Par nom d'utilisateur");
		this.buttonsRowPanel = buttonsRowPanel;
		this.addMouseListener(this);

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		buttonsRowPanel.getMainFrame().getController().sortPerUsername();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
	}


}