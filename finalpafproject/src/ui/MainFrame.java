package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ui.buttons.ButtonsRowPanel;
import ui.maps.JMapPanel;
import ui.menu.Menu;
import controller.ApplicationController;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private ApplicationController controller;
	private Spreadsheet spreadsheet = new Spreadsheet(this);
	private JPanel graph = new JPanel();
	private JMapPanel map = new JMapPanel(this);

	/**
	 * Creates the main frame. Size is 1000x800, title is "Statistiques des attaques par Brute Force", and it contains a menu bar, a main panel (containing a graph or a map), a spreadsheet and buttons.
	 * @param controller
	 */
	public MainFrame(ApplicationController controller) {
		super("Statistiques des attaques par Brute Force") ;
		this.controller = controller;
		this.setLocationRelativeTo(null);
		this.setSize(1000, 550);
		this.setJMenuBar(new Menu(this));
		map.setPreferredSize(new Dimension(600,450));
		ButtonsRowPanel b = new ButtonsRowPanel(this);
		JPanel buttonsWrapper = new JPanel();
		buttonsWrapper.add(b,BorderLayout.CENTER);
		this.add(buttonsWrapper, BorderLayout.SOUTH);
		this.add(spreadsheet, BorderLayout.WEST);
		this.add(map, BorderLayout.CENTER);
		this.setVisible(true);
	}

	@Override
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID()==WindowEvent.WINDOW_CLOSING) { 
			if(true) {
				int response = JOptionPane.showOptionDialog(this,
						"Analyse non sauvegardée. Sauvegarder ?",
						"Quit application",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE,
						null,null,null);
				switch(response) {
				case JOptionPane.CANCEL_OPTION:
					return;
				case JOptionPane.OK_OPTION:
					break;
				case JOptionPane.NO_OPTION:
					break;
				}
			}
			System.exit(0);
		}
	}

	/**
	 * Repaints the spreadsheet.
	 * @param column1
	 * @param column2
	 * @param nameList
	 * @param numberList
	 */
	public void updateSpreadsheet(String column1, String column2, ArrayList<String> nameList, ArrayList<Integer> numberList) {
		this.spreadsheet.updateList(column1, column2, nameList, numberList);
		this.add(graph, BorderLayout.CENTER);
		this.validate();
	}
	
	public void updateFrame(String title1, String title2, ArrayList<String> stringList, ArrayList<Integer> intList,int type1, int type2) {
		if(type2==0) {
			PieChartPanel pieChart = new PieChartPanel(title1,20, intList,stringList);
			getContentPane().removeAll();
			JPanel draw = pieChart.getChartPanel();
			add(draw, BorderLayout.CENTER);
			map.setPreferredSize(new Dimension(600,450));
			ButtonsRowPanel b = new ButtonsRowPanel(this);
			JPanel buttonsWrapper = new JPanel();
			buttonsWrapper.add(b,BorderLayout.CENTER);
			this.spreadsheet.updateList(title1, title2, stringList, intList);
			this.add(buttonsWrapper, BorderLayout.SOUTH);
			this.add(spreadsheet, BorderLayout.WEST);
			this.setVisible(true);
		}
		if(type2==1) {
			CategoryBarChartPanel barChart = new CategoryBarChartPanel("Répartition par IP", title1, stringList, title2, intList, 50, this);
			getContentPane().removeAll();
			JPanel draw = barChart.getChartPanel();
			add(draw, BorderLayout.CENTER);
			map.setPreferredSize(new Dimension(600,450));
			ButtonsRowPanel b = new ButtonsRowPanel(this);
			JPanel buttonsWrapper = new JPanel();
			buttonsWrapper.add(b,BorderLayout.CENTER);
			this.spreadsheet.updateList(title1, title2, stringList, intList);
			this.add(buttonsWrapper, BorderLayout.SOUTH);
			this.add(spreadsheet, BorderLayout.WEST);
			this.setVisible(true);
		}
	}

	public ApplicationController getController() {
		return this.controller;
	}

	/*	private void addBarGraph(String title,String xTitle, ArrayList<String> xList, String yTitle, ArrayList<Integer> yList,double width, int type){
		getContentPane().removeAll();

		setJMenuBar(new Menu(this));
		spreadsheet = new Spreadsheet(this);
		add(new ButtonsRowPanel(this),BorderLayout.SOUTH);
		add(spreadsheet,BorderLayout.WEST);
		switch (type){
		case 0 : graph =new XYBarChartPanel()
		}


		this.setVisible(true);
	}*/
	
	// GETTERS
	public JPanel getGraph() { return this.graph; }
	public JMapPanel getMap() { return this.map; }

}


