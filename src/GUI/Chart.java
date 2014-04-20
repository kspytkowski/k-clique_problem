package GUI;

import java.util.LinkedList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author Krzysztof Spytkowski
 * @date 20 kwi 2014
 */
public class Chart {

    private final JFreeChart jFreeChart;
    //private final String applicationTitle; 
    //private final String chartTitle;
    //private final String xLabel; 
    //private final String yLabel; 
    private final XYSeries series;
    private final XYSeriesCollection dataset;
    private final ChartFrame chartFrame;
    private int i;

    public Chart(String applicationTitle, String chartTitle, String xLabel, String yLabel, LinkedList<Double> listOfArgumentsToDisplay) {
        series = new XYSeries("XYGraph"); // zmien na false => true ponizej, zeby sie nazwa (legenda) wyswietlila
        
        for (i = 0; i < listOfArgumentsToDisplay.size(); i++) {
            series.add(i, listOfArgumentsToDisplay.get(i).doubleValue());
        }
        dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        jFreeChart = ChartFactory.createXYLineChart(chartTitle, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, false, true, false);
        chartFrame = new ChartFrame(applicationTitle, jFreeChart);
    }

    public Chart(String applicationTitle, String chartTitle, String xLabel, String yLabel) {
        series = new XYSeries("XYGraph"); // zmien na false => true ponizej, zeby sie nazwa (legenda) wyswietlila
        dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        jFreeChart = ChartFactory.createXYLineChart(chartTitle, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, false, true, false);
        chartFrame = new ChartFrame(applicationTitle, jFreeChart);
    }
    
    public ChartFrame showFrame() {
        chartFrame.setVisible(true);
        chartFrame.setSize(500, 400);
        return chartFrame;
    }
    
    public void actualizeChart(double newValue) {
        series.add(i++, newValue);
        chartFrame.repaint();
    }

}
