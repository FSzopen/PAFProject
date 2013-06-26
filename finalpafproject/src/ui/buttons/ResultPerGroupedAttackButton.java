package ui.buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ResultPerGroupedAttackButton extends JButton implements MouseListener {

	private final ButtonsRowPanel buttonsRowPanel;

	public ResultPerGroupedAttackButton(ButtonsRowPanel buttonsRowPanel) {
		super("Par groupe d'attaque");
		this.buttonsRowPanel = buttonsRowPanel;
		this.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		buttonsRowPanel.getMainFrame().getController().sortPerGroupedAttack();
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