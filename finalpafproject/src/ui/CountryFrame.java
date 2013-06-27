package ui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import controller.SubFrameController;

@SuppressWarnings("serial")
public class CountryFrame extends SubJFrame {
	
	public CountryFrame(String name, SubFrameController controller) {
		super(name, controller);
		JTabbedPane tabbedPane = this.getTabbedPane();
		
		JPanel ipPanel = new JPanel();
		ipPanel.setLayout(new BorderLayout());
		Spreadsheet spreadsheetIP = new Spreadsheet(this);
		this.getController().sortPerIP();
		spreadsheetIP.updateList("IP", "Nombre d\'attaques", this.getController().getNames(), this.getController().getAttackNumbers());
		ipPanel.add(spreadsheetIP, BorderLayout.WEST);
		if(this.getController().getParameters().getPerIPGraph()==0) {
			PieChartPanel chartPanelIP = new PieChartPanel("IP", 20, this.getController().getAttackNumbers(), this.getController().getNames());
			ipPanel.add(chartPanelIP.getChartPanel(), BorderLayout.EAST);
		}
		if(this.getController().getParameters().getPerIPGraph()==1) {
			CategoryBarChartPanel barChartIP = new CategoryBarChartPanel("Répartition par IP", "IP", this.getController().getNames(), "Nombre d'attaques", this.getController().getAttackNumbers(), 50, null);
			ipPanel.add(barChartIP.getChartPanel(), BorderLayout.EAST);
		}
		tabbedPane.addTab("Par IP", null, ipPanel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JPanel hourPanel = new JPanel();
		hourPanel.setLayout(new BorderLayout());
		Spreadsheet spreadsheetHour = new Spreadsheet(this);
		this.getController().sortPerHour();
		spreadsheetHour.updateList("Heures", "Nombre d\'attaques", this.getController().getNames(), this.getController().getAttackNumbers());
		hourPanel.add(spreadsheetHour, BorderLayout.WEST);
		PieChartPanel chartPanelHour = new PieChartPanel("Heures", 20, this.getController().getAttackNumbers(), this.getController().getNames());
		hourPanel.add(chartPanelHour.getChartPanel(), BorderLayout.EAST);
		tabbedPane.addTab("Par heure", null, hourPanel);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		JPanel monthPanel = new JPanel();
		monthPanel.setLayout(new BorderLayout());
		Spreadsheet spreadsheetMonth = new Spreadsheet(this);
		this.getController().sortPerMonth();
		spreadsheetMonth.updateList("Mois", "Nombre d\'attaques", this.getController().getNames(), this.getController().getAttackNumbers());
		monthPanel.add(spreadsheetMonth, BorderLayout.WEST);
		CategoryBarChartPanel barChartMonth = new CategoryBarChartPanel("Répartition par mois", "Mois", this.getController().getNames(), "Nombre d'attaques", this.getController().getAttackNumbers(), 50, null);
		monthPanel.add(barChartMonth.getChartPanel(), BorderLayout.EAST);
		tabbedPane.addTab("Par mois", null, monthPanel);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		JPanel dayPanel = new JPanel();
		dayPanel.setLayout(new BorderLayout());
		Spreadsheet spreadsheetDay = new Spreadsheet(this);
		this.getController().sortPerDay();
		spreadsheetDay.updateList("Jour", "Nombre d\'attaques", this.getController().getNames(), this.getController().getAttackNumbers());
		dayPanel.add(spreadsheetDay, BorderLayout.WEST);
		if(this.getController().getParameters().getPerDateGraph()==0) {
			PieChartPanel chartPanelDay = new PieChartPanel("Jour", 20, this.getController().getAttackNumbers(), this.getController().getNames());
			dayPanel.add(chartPanelDay.getChartPanel(), BorderLayout.EAST);
		}
		if(this.getController().getParameters().getPerDateGraph()==1) {
			CategoryBarChartPanel barChartDay = new CategoryBarChartPanel("Répartition par jour", "Jour", this.getController().getNames(), "Nombre d'attaques", this.getController().getAttackNumbers(), 50, null);
			dayPanel.add(barChartDay.getChartPanel(), BorderLayout.EAST);
		}
		tabbedPane.addTab("Par jour", null, dayPanel);
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new BorderLayout());
		Spreadsheet spreadsheetUsername = new Spreadsheet(this);
		this.getController().sortPerUsername();
		spreadsheetUsername.updateList("Username", "Nombre d\'attaques", this.getController().getNames(), this.getController().getAttackNumbers());
		usernamePanel.add(spreadsheetUsername, BorderLayout.WEST);
		if(this.getController().getParameters().getPerUsernameGraph()==0) {
			PieChartPanel chartPanelUsername = new PieChartPanel("Username", 20, this.getController().getAttackNumbers(), this.getController().getNames());
			usernamePanel.add(chartPanelUsername.getChartPanel(), BorderLayout.EAST);
		}
		if(this.getController().getParameters().getPerUsernameGraph()==1) {
			CategoryBarChartPanel barChartUsername = new CategoryBarChartPanel("Répartition par username", "Username", this.getController().getNames(), "Nombre d'attaques", this.getController().getAttackNumbers(), 50, null);
			usernamePanel.add(barChartUsername.getChartPanel(), BorderLayout.EAST);
		}
		tabbedPane.addTab("Par username", null, usernamePanel);
		tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);

		JPanel groupedAttackPanel = new JPanel();
		groupedAttackPanel.setLayout(new BorderLayout());
		Spreadsheet spreadsheetGroup = new Spreadsheet(this);
		this.getController().sortPerGroupedAttack();
		spreadsheetGroup.updateList("Groupe", "Nombre d\'attaques", this.getController().getNames(), this.getController().getAttackNumbers());
		groupedAttackPanel.add(spreadsheetGroup, BorderLayout.WEST);
		if(this.getController().getParameters().getPerAttackGroupGraph()==0) {
			PieChartPanel chartPanelGroup = new PieChartPanel("Groupe", 20, this.getController().getAttackNumbers(), this.getController().getNames());
			groupedAttackPanel.add(chartPanelGroup.getChartPanel(), BorderLayout.EAST);
		}
		if(this.getController().getParameters().getPerAttackGroupGraph()==1) {
			CategoryBarChartPanel barChartGroup = new CategoryBarChartPanel("Répartition par groupe", "Groupe", this.getController().getNames(), "Nombre d'attaques", this.getController().getAttackNumbers(), 50, null);
			groupedAttackPanel.add(barChartGroup.getChartPanel(), BorderLayout.EAST);
		}
		tabbedPane.addTab("Par groupe", null, groupedAttackPanel);
		tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);
	}

}
