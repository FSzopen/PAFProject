package ui.buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ResultPerIPButton extends JButton implements MouseListener {

	private final ButtonsRowPanel buttonsRowPanel;

	public ResultPerIPButton(ButtonsRowPanel buttonsRowPanel) {
		super("Par IP");
		this.buttonsRowPanel = buttonsRowPanel;
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		buttonsRowPanel.getMainFrame().getController().sortPerIP();
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