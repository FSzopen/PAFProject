package ui;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.util.ArrayList;
import java.lang.Math;

public class PieChartPanel {
	
	private DefaultPieDataset data;
	private JFreeChart chart;
	private JPanel chartPanel;

	public PieChartPanel(String type,int minimum, ArrayList<Integer> valueList, ArrayList<String> legendList) {
		String title="Répartition des attaques par "+type;
		DefaultPieDataset data = new DefaultPieDataset();
		int n=valueList.size();
		int min=Math.min(n,minimum);
		if (n <= min+1)
		for(int i = 0; i < min; i++){
			data.setValue( legendList.get(i), valueList.get(i));
		}
		else{
			for(int i = 0; i < min; i++){
				data.setValue( legendList.get(i), valueList.get(i));
			}
			int otherValue=0;
			for (int i=min; i<n;i++){
				otherValue+=valueList.get(i);
			}
			data.setValue( "Other "+type, otherValue);
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

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public DefaultPieDataset getData() {
		return data;
	}

	public void setData(DefaultPieDataset data) {
		this.data = data;
	}

	public JPanel getChartPanel() {
		return chartPanel;
	}

	public void setChartPanel(JPanel chartPanel) {
		this.chartPanel = chartPanel;
	}
	
}
