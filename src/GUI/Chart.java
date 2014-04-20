package GUI;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author Krzysztof Spytkowski
 * @date 20 kwi 2014
 */
public class Chart extends JFrame {

    private static final long serialVersionUID = 1L;

    public Chart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        // This will create the dataset
        XYSeriesCollection dataset = createDataset();

        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, chartTitle);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);

        // Dodanie wykresu do okna
        ChartFrame frame1 = new ChartFrame("XYArea Chart", chart);
        frame1.setVisible(true);
        frame1.setSize(500, 400);

    }

    /** * Creates a sample dataset */

    private XYSeriesCollection createDataset() {
        // Dane do wykresu 3d
        XYSeries series = new XYSeries("XYGraph");
        series.add(1, 1);
        series.add(1, 2);
        series.add(2, 4);
        series.add(3, 4);
        series.add(4, 2);
        series.add(5, 9);
        series.add(6, 10);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;

    }

    /** * Creates a chart */

    private JFreeChart createChart(XYSeriesCollection dataset, String title) {

        // Tworzymy wykres XY
        JFreeChart chart = ChartFactory.createXYLineChart("Wykres XY",// Tytuł
                "x- Lable", // x-axis Opis
                "y- Lable", // y-axis Opis
                dataset, // Dane
                PlotOrientation.VERTICAL, // Orjentacja wykresu /HORIZONTAL
                true, // pozkaż legende
                true, // podpowiedzi tooltips
                false);
        return chart;

    }

    public static void main(String[] args) {
        new Chart("lol", "lol2");
    }
}
