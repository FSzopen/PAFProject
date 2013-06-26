package ui;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class TableNonEdit extends DefaultTableModel {

	public TableNonEdit() {
		super();
	}

	public TableNonEdit(int row, int column) {
		super(row, column);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
