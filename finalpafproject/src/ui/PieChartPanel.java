package ui;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.util.ArrayList;

public class PieChartPanel {

	private DefaultPieDataset data;
	private JFreeChart chart;
	private JPanel chartPanel;
	private MainFrame mainFrame;

	public PieChartPanel(java.lang.String title, ArrayList<Integer> valueList, ArrayList<String> legendList, MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		DefaultPieDataset data = new DefaultPieDataset();
		for(int i = 0 ; i<valueList.size() ; i++){
			data.setValue(legendList.get(i), valueList.get(i));
		}
		JFreeChart chart = ChartFactory.createPieChart(	title,
				data,
				true, // legend?
				true, // tooltips?
				false // URLs?
				);
		this.data = data;
		this.chart = chart;
		this.chartPanel = new ChartPanel(chart);
	}

	// GETTERS
	public JFreeChart getChart() { return this.chart; }
	public DefaultPieDataset getData() { return this.data; }
	public JPanel getChartPanel() { return (JPanel) this.chartPanel; }
	public MainFrame getMainFrame() { return this.mainFrame; }

	// SETTERS
	public void setChart(JFreeChart chart) { this.chart = chart; }
	public void setData(DefaultPieDataset data) { this.data = data; }
	public void setChartPanel(JPanel chartPanel) { this.chartPanel = chartPanel; }

}