package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import database.Parameters;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import controller.ApplicationController;

@SuppressWarnings("serial")
public class ParameterJFrame extends JFrame {

	private ApplicationController controller;
	private Parameters parameters;
	private RadioPanel timeRadioPanel;
	private RadioPanel dateGraphRadioPanel;
	private RadioPanel countryGraphRadioPanel;
	private RadioPanel ipGraphRadioPanel;
	private RadioPanel attackGroupGraphRadioPanel;
	private RadioPanel usernameGraphRadioPanel;

	/**
	 * Creates a parameters dialog. User chooses which data he/she wants to display.
	 * @param controller
	 */
	public ParameterJFrame(ApplicationController controller) {
		super("Paramètres");
		final ApplicationController controllerTemp = controller;
		final ParameterJFrame parametersJFrameTemp = this;
		this.controller = controller;
		parameters = controller.getParameters();
		this.setSize(600, 250);
		this.setLocationRelativeTo(null);

		ArrayList<String> time = new ArrayList<String>();
		time.add("Par heure");
		time.add("Par jour");
		time.add("Par mois");
		timeRadioPanel= new RadioPanel(time, " Échelle : ", parameters.getPerDateType());

		ArrayList<String> dateGraph = new ArrayList<String>();
		dateGraph.add("Courbe");
		dateGraph.add("Diagramme \"bâton\"");
		dateGraph.add("Camembert");
		dateGraphRadioPanel = new RadioPanel(dateGraph, " Par date : ", parameters.getPerDateType());

		ArrayList<String> countryGraph = new ArrayList<String>();
		countryGraph.add("Courbe");
		countryGraph.add("Diagramme \"bâton\"");
		countryGraph.add("Camembert");
		countryGraphRadioPanel = new RadioPanel(countryGraph, " Par pays : ", parameters.getPerCountryGraph());

		ArrayList<String> ipGraph = new ArrayList<String>();
		ipGraph.add("Courbe");
		ipGraph.add("Diagramme \"bâton\"");
		ipGraph.add("Camembert");
		ipGraphRadioPanel = new RadioPanel(ipGraph, " Par IP : ", parameters.getPerIPGraph());

		ArrayList<String> attackGroupGraph = new ArrayList<String>();
		attackGroupGraph.add("Courbe");
		attackGroupGraph.add("Diagramme \"bâton\"");
		attackGroupGraph.add("Camembert");
		attackGroupGraphRadioPanel = new RadioPanel(attackGroupGraph, " Par groupe d'attaque : ", parameters.getPerAttackGroupGraph());

		ArrayList<String> usernameGraph = new ArrayList<String>();
		usernameGraph.add("Courbe");
		usernameGraph.add("Diagramme \"bâton\"");
		usernameGraph.add("Camembert");
		usernameGraphRadioPanel = new RadioPanel(usernameGraph, " Par nom d'utilisateur : ", parameters.getPerUsernameGraph());

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		add(timeRadioPanel);
		add(dateGraphRadioPanel);
		add(countryGraphRadioPanel);
		add(ipGraphRadioPanel);
		add(attackGroupGraphRadioPanel);
		add(usernameGraphRadioPanel);

		JButton validateButton = new JButton("valider");
		validateButton.addMouseListener(
				new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						parametersJFrameTemp.extractParameters();
						controllerTemp.setParameters(parameters);
						parametersJFrameTemp.dispose();
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
					
				});
		add(validateButton);

		this.validate();
		this.setVisible(true);
	}
	
	/**
	 * Extracts parameters from JFrame and set parameters in ApplicationController.
	 */
	protected void extractParameters(){
		parameters.setPerDateType(Integer.parseInt(timeRadioPanel.getButtonGroup().getSelection().getActionCommand()));
		parameters.setPerDateGraph(Integer.parseInt(dateGraphRadioPanel.getButtonGroup().getSelection().getActionCommand()));
		parameters.setPerCountryGraph(Integer.parseInt(countryGraphRadioPanel.getButtonGroup().getSelection().getActionCommand()));
		parameters.setPerIPGraph(Integer.parseInt(ipGraphRadioPanel.getButtonGroup().getSelection().getActionCommand()));
		parameters.setPerAttackGroupGraph(Integer.parseInt(attackGroupGraphRadioPanel.getButtonGroup().getSelection().getActionCommand()));
		parameters.setPerUsernameGraph(Integer.parseInt(usernameGraphRadioPanel.getButtonGroup().getSelection().getActionCommand()));
	}

	
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID()==WindowEvent.WINDOW_CLOSING) {
			if(true) {
				int response = JOptionPane.showOptionDialog(this,
						"Données non sauvegardées. Sauvegarder ?",
								"Quit application",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE,
								null, null, null);
				switch(response) {
				case JOptionPane.OK_OPTION:
					this.extractParameters();
					controller.setParameters(parameters);
					break;
				case JOptionPane.NO_OPTION:
					break;
				}
			}
			dispose();
		}
	}
}