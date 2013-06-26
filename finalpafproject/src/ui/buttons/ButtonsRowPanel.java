package ui.buttons;

import ui.MainFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ButtonsRowPanel extends JPanel {
	
	private MainFrame mainFrame;
	
	public ButtonsRowPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		BoxLayout bl = new BoxLayout(this,BoxLayout.X_AXIS);
		this.setLayout(bl);
		add(new ResultPerIPButton(this));
		add(new ResultPerCountryButton(this));
		add(new ResultPerDayButton(this));
		add(new ResultPerUsernameButton(this));
		add(new ResultPerGroupedAttackButton(this));
		add(new MapButton(this));
		add(new UpdateButton(this));
	}
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}

}