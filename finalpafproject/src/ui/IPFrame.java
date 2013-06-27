package ui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import controller.SubFrameController;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.IOException;

@SuppressWarnings("serial")
public class IPFrame extends SubJFrame {

	public IPFrame(String name, SubFrameController controller) {
		super(name, controller);
		JTabbedPane tabbedPane = this.getTabbedPane();

		JPanel hourPanel = new JPanel();
		hourPanel.setLayout(new BorderLayout());
		Spreadsheet spreadsheetHour = new Spreadsheet(this);
		this.getController().sortPerHour();
		spreadsheetHour.updateList("Heures", "Nombre d\'attaques", this.getController().getNames(), this.getController().getAttackNumbers());
		hourPanel.add(spreadsheetHour, BorderLayout.WEST);
		PieChartPanel chartPanelHour = new PieChartPanel("Heures", 20, this.getController().getAttackNumbers(), this.getController().getNames());
		hourPanel.add(chartPanelHour.getChartPanel(), BorderLayout.EAST);
		tabbedPane.addTab("Par heure", null, hourPanel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JPanel monthPanel = new JPanel();
		monthPanel.setLayout(new BorderLayout());
		Spreadsheet spreadsheetMonth = new Spreadsheet(this);
		this.getController().sortPerMonth();
		spreadsheetMonth.updateList("Mois", "Nombre d\'attaques", this.getController().getNames(), this.getController().getAttackNumbers());
		monthPanel.add(spreadsheetMonth, BorderLayout.WEST);
		CategoryBarChartPanel barChartMonth = new CategoryBarChartPanel("Répartition par mois", "Mois", this.getController().getNames(), "Nombre d'attaques", this.getController().getAttackNumbers(), 50, null);
		monthPanel.add(barChartMonth.getChartPanel(), BorderLayout.EAST);
		tabbedPane.addTab("Par mois", null, monthPanel);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

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
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

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
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

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
		tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);

		JPanel countryPanel = new JPanel();
		countryPanel.setLayout(new BorderLayout());
		Spreadsheet spreadsheetCountry = new Spreadsheet(this);
		try { this.getController().sortPerCountry(); } catch (IOException e) {}
		spreadsheetCountry.updateList("Pays", "Nombre d\'attaques", this.getController().getNames(), this.getController().getAttackNumbers());
		countryPanel.add(spreadsheetCountry, BorderLayout.WEST);
		if(this.getController().getParameters().getPerCountryGraph()==0) {
			PieChartPanel chartPanelCountry = new PieChartPanel("Pays", 20, this.getController().getAttackNumbers(), this.getController().getNames());
			countryPanel.add(chartPanelCountry.getChartPanel(), BorderLayout.EAST);
		}
		if(this.getController().getParameters().getPerCountryGraph()==1) {
			CategoryBarChartPanel barChartCountry = new CategoryBarChartPanel("Répartition par pays", "Pays", this.getController().getNames(), "Nombre d'attaques", this.getController().getAttackNumbers(), 50, null);
			countryPanel.add(barChartCountry.getChartPanel(), BorderLayout.EAST);
		}
		tabbedPane.addTab("Par pays", null, countryPanel);
		tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);
	}
	
}