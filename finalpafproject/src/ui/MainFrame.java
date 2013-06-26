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
	private Spreadsheet spreadsheet;
	private JPanel graph = new JPanel();
	private JMapPanel map;

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
		this.spreadsheet = new Spreadsheet(this);
		this.map = new JMapPanel(this);
		map.setPreferredSize(new Dimension(600,450));
		this.getController().displayMap();
		ButtonsRowPanel b = new ButtonsRowPanel(this);
		JPanel buttonsWrapper = new JPanel();
		buttonsWrapper.add(b,BorderLayout.CENTER);
		this.add(buttonsWrapper, BorderLayout.SOUTH);
		this.add(spreadsheet, BorderLayout.WEST);
		graph.add(map, BorderLayout.CENTER);
		this.add(graph, BorderLayout.CENTER);
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

}


