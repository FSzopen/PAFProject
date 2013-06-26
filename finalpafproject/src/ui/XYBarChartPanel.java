package ui;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.ArrayList;
import java.lang.String;

public class XYBarChartPanel {

	private XYBarDataset data;
	private JFreeChart chart;
	private JPanel chartPanel;
	private MainFrame mainFrame;

	public XYBarChartPanel(String title,String xTitle, ArrayList<Integer> xList, String yTitle, ArrayList<Integer> yList, double width, MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		XYSeries serie = new XYSeries(title);
		for(int i = 0 ; i<xList.size() ; i++) {
			serie.add(xList.get(i), yList.get(i));
		}
		XYSeriesCollection seriesCollection = new XYSeriesCollection();
		seriesCollection.addSeries(serie);
		this.data = new XYBarDataset(seriesCollection, width);
		JFreeChart chart = ChartFactory.createXYBarChart(title,
				xTitle,  //xAxisLabel
				false,   //booleen datexis
				yTitle,  //yAxixLabel
				data,   //dataset
				PlotOrientation.VERTICAL,  // orientation HORIZONTAL or VERTICAL
				true, // legend?
				true, // tooltips?
				false // URLs?
				);
		this.chart = chart;
		this.chartPanel = new ChartPanel(chart);
	}

	// GETTERS
	public JFreeChart getChart() { return this.chart; }
	public XYBarDataset getData() { return this.data; }
	public JPanel getChartPanel() { return this.chartPanel; }
	public MainFrame getMainFrame() { return this.mainFrame; }

	// SETTERS
	public void setChart(JFreeChart chart) { this.chart = chart; }
	public void setData(XYBarDataset data) { this.data = data; }
	public void setChartPanel(JPanel chartPanel) { this.chartPanel = chartPanel; }

}