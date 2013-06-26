package ui.buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class ResultPerCountryButton extends JButton implements MouseListener {

	private final ButtonsRowPanel buttonsRowPanel;

	public ResultPerCountryButton(ButtonsRowPanel buttonsRowPanel) {
		super("Par pays");
		this.buttonsRowPanel = buttonsRowPanel;
		this.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			buttonsRowPanel.getMainFrame().getController().sortPerCountry();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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