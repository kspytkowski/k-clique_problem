package GUI;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.RangeType;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

/**
 * @author Krzysztof Spytkowski
 * @date 20th April 2014
 */
public class Chart {

    private final JFreeChart jFreeChart; // chart
    private final XYSeries bestSeries; // series that keeps best Individual's fitness
    private final XYSeries averageSeries; // series that keeps average Individual's fitness
    private final XYSeries worstSeries; // series that keeps worst Individual's fitness
    private final XYSeriesCollection dataset; // set of series
    private final ChartFrame chartFrame; // chart frame

    
    // u lidki siec czynnosci na wgzaminie, nie siec zdarzen, rob klasy, luki z czasem (kazde zadanie min. 2 łuki => dop
    // rowadzajacy i odprowadzajacy

    /**
     * Constructor
     * 
     * @param applicationTitle
     *            - title of main application
     * @param chartTitle
     *            - title of Chart
     * @param xLabel
     *            - label of x-axis
     * @param yLabel
     *            - label of y-axis
     */
    public Chart(String applicationTitle, String chartTitle, String xLabel, String yLabel) {
        bestSeries = new XYSeries("Best");
        averageSeries = new XYSeries("Average");
        worstSeries = new XYSeries("Worst");
        dataset = new XYSeriesCollection();
        dataset.addSeries(bestSeries);
        dataset.addSeries(averageSeries);
        dataset.addSeries(worstSeries);
        jFreeChart = ChartFactory.createXYLineChart(chartTitle, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
        chartFrame = new ChartFrame(applicationTitle, jFreeChart);
    }

    /**
     * Creates new ChartFrame with Chart and special parameters
     * 
     * @return new ChartFrame
     */
    public ChartFrame getChartFrame() {
        XYPlot plot = (XYPlot) jFreeChart.getPlot();
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setRangeType(RangeType.POSITIVE);
        xAxis.setAutoRange(true);
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesPaint(1, Color.green);
        renderer.setSeriesPaint(2, Color.blue);
        return chartFrame;
    }

    /**
     * Adds new value to best series
     * 
     * @param iterationNumber
     *            - number of iteration (x - value)
     * @param newValue
     *            - new value (y - value)
     */
    public void addNewValueToBestSeries(int iterationNumber, double newValue) {
        bestSeries.add(iterationNumber, newValue);
    }

    /**
     * Adds new value to average series
     * 
     * @param iterationNumber
     *            - number of iteration (x - value)
     * @param newValue
     *            - new value (y - value)
     */
    public void addNewValueToAverageSeries(int iterationNumber, double newValue) {
        averageSeries.add(iterationNumber, newValue);
    }

    /**
     * Adds new value to worst series
     * 
     * @param iterationNumber
     *            - number of iteration (x - value)
     * @param newValue
     *            - new value (y - value)
     */
    public void addNewValueToWorstSeries(int iterationNumber, double newValue) {
        worstSeries.add(iterationNumber, newValue);
    }

    /**
     * Repaints ChartFrame
     */
    public void repaintChart() {
        chartFrame.repaint();
    }
    
    public void saveChartToFile(String filename) {
     // Get a DOMImplementation and create an XML document
        DOMImplementation domImpl =
            GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument(null, "svg", null);
        // Create an instance of the SVG Generator
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
        // draw the chart in the SVG generator
        jFreeChart.draw(svgGenerator, new Rectangle(1000,600));
        // Write svg files
        File aa = new File(filename);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(aa);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Writer out;
        try {
            out = new OutputStreamWriter(outputStream, "UTF-8");
            svgGenerator.stream(out, true /* use css */);                       
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
