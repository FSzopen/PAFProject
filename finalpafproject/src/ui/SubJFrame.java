package ui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import controller.SubFrameController;

@SuppressWarnings("serial")
public abstract class SubJFrame extends JFrame {
	
	private SubFrameController controller;
	private JTabbedPane tabbedPane = new JTabbedPane();

	public SubJFrame(String name, SubFrameController controller) {
		super(name);
		this.controller = controller;
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		this.setSize(1000, 500);
		this.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(tabbedPane,BorderLayout.WEST);
		this.setContentPane(panel);
		this.setVisible(true);
	}
	
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID()==WindowEvent.WINDOW_CLOSING){
			dispose();
		}
	}
	
	// GETTERS
	public SubFrameController getController() { return this.controller; }
	public JTabbedPane getTabbedPane() { return this.tabbedPane; }

}
