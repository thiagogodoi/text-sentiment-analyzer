package org.sentimental.chart;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

public class SentimentalChart {
	
	private static final String GRAPH_y_AXYS_NAME = "Positive";

	private static final String GRAPH_X_AXYS_NAME = "Paragraphs";

	private static final String GRAPH_TITLE = "Text sentiments for paragraphs";

	private LineChartModel lineModel;
	
	private ChartSeries sentimentSerie;
	
	public SentimentalChart() {
		this.lineModel = new LineChartModel();

		this.lineModel.setTitle(GRAPH_TITLE);
		this.lineModel.setShowPointLabels(false);
		this.lineModel.setShowDatatip(false);
		this.lineModel.setShowPointLabels(true);
		this.lineModel.getAxes().put(AxisType.X, new CategoryAxis(GRAPH_X_AXYS_NAME));
		Axis yAxis = this.lineModel.getAxis(AxisType.Y);
		yAxis.setLabel(GRAPH_y_AXYS_NAME);
		yAxis.setMin(-0.1);
		yAxis.setMax(1.2);
	}

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}
	
	public void resetSeries(){
		if(this.sentimentSerie!=null){
			addSeriesToChart();
		}
		this.sentimentSerie = new ChartSeries();
	}
	
	public void addSeries(int paragraphNum, int sentiment){
		if(this.sentimentSerie==null){
			this.sentimentSerie = new ChartSeries();
		}
		this.sentimentSerie.set(paragraphNum, sentiment);
	}
	
	public void addSeriesToChart(){
		this.lineModel.addSeries(sentimentSerie);
	}

}
