package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ui.maps.JMapPanel;
import controller.SubFrameController;

@SuppressWarnings("serial")
public class UsernameFrame extends SubJFrame {

	public UsernameFrame(String name, SubFrameController controller) {
		super(name, controller);
		JTabbedPane tabbedPane = this.getTabbedPane();

		JPanel ipPanel = new JPanel();
		ipPanel.setLayout(new BorderLayout());
		ipPanel.add(new Spreadsheet(null), BorderLayout.WEST);
		ipPanel.add(new JMapPanel(null));
		tabbedPane.addTab("Par IP", null, ipPanel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JPanel hourPanel = new JPanel();
		hourPanel.setLayout(new BorderLayout());
		hourPanel.add(new Spreadsheet(null), BorderLayout.WEST);
		hourPanel.add(new JMapPanel(null));
		tabbedPane.addTab("Par heure", null, hourPanel);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		JPanel monthPanel = new JPanel();
		monthPanel.setLayout(new BorderLayout());
		monthPanel.add(new Spreadsheet(null), BorderLayout.WEST);
		monthPanel.add(new JMapPanel(null));
		tabbedPane.addTab("Par mois", null, monthPanel);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		JPanel dayPanel = new JPanel();
		dayPanel.setLayout(new BorderLayout());
		dayPanel.add(new Spreadsheet(null), BorderLayout.WEST);
		dayPanel.add(new JMapPanel(null));
		tabbedPane.addTab("Par jour", null, dayPanel);
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		JPanel groupedAttackPanel = new JPanel();
		groupedAttackPanel.setLayout(new BorderLayout());
		groupedAttackPanel.add(new Spreadsheet(null), BorderLayout.WEST);
		groupedAttackPanel.add(new JMapPanel(null));
		tabbedPane.addTab("Par groupe d'attaque", null, groupedAttackPanel);
		tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);

		JPanel countryPanel = new JPanel();
		countryPanel.setLayout(new BorderLayout());
		countryPanel.add(new Spreadsheet(null), BorderLayout.WEST);
		countryPanel.add(new JMapPanel(null));
		countryPanel.setPreferredSize(new Dimension(1000, 50));
		tabbedPane.addTab("Par pays", null, countryPanel);
		tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);
	}

}
