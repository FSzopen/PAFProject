package ui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class RadioPanel extends JPanel {

	private ButtonGroup buttonGroup = new ButtonGroup();

	public RadioPanel(ButtonGroup buttonGroup) {
		this.buttonGroup = buttonGroup;
	}

	/**
	 * Creates a RadioPanel without any title. Just radio buttons.
	 * @param buttonsList
	 */
	public RadioPanel(ArrayList<String> buttonsList, int select) {
		BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(layout);
		int i = 0;
		for(String b : buttonsList) {
			i++;
			JRadioButton button = new JRadioButton(b);
			button.setMnemonic(KeyEvent.VK_R);
			button.setActionCommand(new Integer(i).toString());
			if (i==select){
				button.setSelected(true);
			}
			buttonGroup.add(button);
			this.add(button);
		}
	}

	/**
	 * Creates a RadioPanel with a title.
	 * @param buttonsList
	 * @param title
	 * @param select
	 */
	public RadioPanel(ArrayList<String> buttonsList, String title, int select) {
		BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(layout);
		this.add(new JLabel(title));
		int i = 0;
		for(String b : buttonsList) {
			JRadioButton button = new JRadioButton(b);
			button.setMnemonic(KeyEvent.VK_R);
			button.setActionCommand(new Integer(i).toString());
			if (i==select){
				button.setSelected(true);
			}
			buttonGroup.add(button);
			this.add(button);
			i++;
		}
	}

	// GETTERS
	public ButtonGroup getButtonGroup() { return this.buttonGroup; }

}