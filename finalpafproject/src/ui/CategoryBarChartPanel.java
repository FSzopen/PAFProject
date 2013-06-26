package ui;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.ArrayList;
import java.lang.String;

public class CategoryBarChartPanel {

	private CategoryDataset data;
	private JFreeChart chart;
	private JPanel chartPanel;
	private MainFrame mainFrame;

	public CategoryBarChartPanel(String title, String xTitle, ArrayList<String> xList, String yTitle, ArrayList<Integer> yList, double width, MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		for(int i = 0 ; i<xList.size() ; i++) {
			data.addValue(yList.get(i), title, xList.get(i));
		}
		this.data = data;
		JFreeChart chart = ChartFactory.createBarChart( title,
				xTitle,  //xAxisLabel
				yTitle,  //yAxixLabel
				data,   //dataset
				PlotOrientation.VERTICAL,  // orientation HORIZONTAL or VERTICAL
				false, // legend?
				true, // tooltips?
				false // URLs?
				);
		this.data = data;
		this.chart = chart;
		this.chartPanel = new ChartPanel(chart);
	}

	// GETTERS
	public JFreeChart getChart() { return this.chart; }
	public CategoryDataset getData() { return this.data; }
	public JPanel getChartPanel() { return this.chartPanel; }
	public MainFrame getMainFrame() { return this.mainFrame; }

	// SETTERS
	public void setChart(JFreeChart chart) { this.chart = chart; }
	public void setData(CategoryDataset data) { this.data = data; }
	public void setChartPanel(JPanel chartPanel) { this.chartPanel = chartPanel; }

}