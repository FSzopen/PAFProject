package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import ui.TableNonEdit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

@SuppressWarnings("serial")
public class Spreadsheet extends JPanel implements TableModelListener {

	private JTable table;
	private TableNonEdit spreadsheetTable;
	private MainFrame mainFrame;
	private SubJFrame subJFrame;

	public Spreadsheet(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		spreadsheetTable = new TableNonEdit();
		spreadsheetTable.addColumn("Entité");
		spreadsheetTable.addColumn("Nombre d'attaques");
		spreadsheetTable.setRowCount(15);
		table = new JTable(spreadsheetTable);
		table.setPreferredScrollableViewportSize(new Dimension(300, 400));
		add(new JScrollPane(table), BorderLayout.CENTER);
		spreadsheetTable.addTableModelListener(this);
	}
	
	public Spreadsheet(SubJFrame subFrame) {
		this.subJFrame = subFrame;
		spreadsheetTable = new TableNonEdit();
		spreadsheetTable.addColumn("Entité");
		spreadsheetTable.addColumn("Nombre d'attaques");
		spreadsheetTable.setRowCount(15);
		table = new JTable(spreadsheetTable);
		table.setPreferredScrollableViewportSize(new Dimension(300, 400));
		add(new JScrollPane(table), BorderLayout.CENTER);
		spreadsheetTable.addTableModelListener(this);
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {}

	public void updateList(String type, String var, ArrayList<String> attacks, ArrayList<Integer> attackNb) {
		final String finalType = type;
		int n = attacks.size();
		Object[] identifiers = {type, var};
		spreadsheetTable.setColumnIdentifiers(identifiers);
		int nbRow = spreadsheetTable.getRowCount();
		for (int i = 0 ; i<nbRow ; i++) {
			spreadsheetTable.removeRow(0);
		}
		for (int i = 0 ; i<n ; i++) {
			Object[] line={attacks.get(i), attackNb.get(i)};
			spreadsheetTable.addRow(line);
		}
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) { 
					int y = e.getY();
					int height = table.getRowHeight();
					String nameRow = (String) table.getValueAt((y/height), 0);
					if(mainFrame!=null) {
					mainFrame.getController().openSubJFrame(finalType, nameRow);
					}
					else {
						subJFrame.getController().openSubJFrame(finalType, nameRow);
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}	

			@Override
			public void mouseReleased(MouseEvent e) {}

		});
	}

	public void updateList(String type, int[] AttackNb) {
		int n = Array.getLength(AttackNb);
		Object[] identifiers = {type, "Nb d'attaques"};
		spreadsheetTable.setColumnIdentifiers(identifiers);
		int nbRow = spreadsheetTable.getRowCount();
		for (int i = 0 ; i<nbRow ; i++) {
			spreadsheetTable.removeRow(0);
		}
		for (int i = 0 ; i<n ; i++) {
			Object[] line={i+"h", AttackNb[i]};
			spreadsheetTable.addRow(line);
		}
	}

	// GETTERS
	public JTable getTable() { return table; }

}
