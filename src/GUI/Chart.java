package GUI;

import java.util.LinkedList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.RangeType;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author Krzysztof Spytkowski
 * @date 20 kwi 2014
 */
public class Chart {

    /* IT's beginning, still a lot to CHANGE!*/
    
    private final JFreeChart jFreeChart;
    private final XYSeries series;
    private final XYSeriesCollection dataset;
    private final ChartFrame chartFrame;
    private int iterationNumber;

    public Chart(String applicationTitle, String chartTitle, String xLabel, String yLabel, LinkedList<Double> listOfArgumentsToDisplay) {
        series = new XYSeries("XYGraph"); // zmien na false => true ponizej, zeby sie nazwa (legenda) wyswietlila
        for (iterationNumber = 0; iterationNumber < listOfArgumentsToDisplay.size(); iterationNumber++) {
            series.add(iterationNumber, listOfArgumentsToDisplay.get(iterationNumber).doubleValue());
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
        jFreeChart = ChartFactory.createXYLineChart(chartTitle, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
        chartFrame = new ChartFrame(applicationTitle, jFreeChart);
    }

    public ChartFrame getChartFrame() {
        XYPlot plot = (XYPlot) jFreeChart.getPlot();
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setRangeType(RangeType.POSITIVE);
        xAxis.setAutoRange(true);
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        //XYItemRenderer renderer = plot.getRenderer();
        //renderer.setSeriesPaint(0, Color.green);
        return chartFrame;
    }

    public void actualizeChart(double newValue) {
        series.add(iterationNumber++, newValue);
        chartFrame.repaint();
    }
}
