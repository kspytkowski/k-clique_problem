package GUI;

import exceptions.GeneticAlgorithmException;
import exceptions.NoPossibilityToCreateGraphException;
import genetics.CrossingOverType;
import genetics.IndividualType;
import genetics.Population;
import graph.GraphRepresentation;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

public class probkaTaka implements Runnable {
    
    GraphRepresentation a;
    GraphPanelKRZYSIEK graphPanelVisual;
    Chart myChart;
    
    
    public probkaTaka(GraphRepresentation a, GraphPanelKRZYSIEK graphPanelVisual, Chart myChart) {
        try {
            this.a = new GraphRepresentation(128, 6326, 67, 67);
        } catch (NoPossibilityToCreateGraphException ex) {
            Logger.getLogger(probkaTaka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneticAlgorithmException ex) {
            Logger.getLogger(probkaTaka.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.graphPanelVisual = graphPanelVisual;
        this.myChart = myChart;
    }
    
    @Override
    public void run() {
        Population population = null;
        try {
            population = new Population(50, a, IndividualType.BINARYCODEDINDIVIDUAL);
            // Population population = new Population(50, gr, IndividualType.BINARYCODEDINDIVIDUAL);
        } catch (GeneticAlgorithmException e) {
            e.printStackTrace();
        }

      //  Chart myChart = new Chart("K-clique solver", "Individuals' fitness in population", "Iteration", "Fitness");
      //  ChartFrame myFrame = myChart.getChartFrame();
      //  myFrame.setVisible(true);
      //  myFrame.setSize(500, 400);
        myChart.repaintChart();

        for (int i = 1; i < 10; i++) {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(probkaTaka.class.getName()).log(Level.SEVERE, null, ex);
            }
          //  System.out.println("Iteracja " + i);
          //  System.out.println(population.findBestAdoptedIndividual());
            population.singleLifeCycleKRZYSZTOF(0.6, CrossingOverType.ONEPOINTWITHTWOCHILDREN, 0.05, 0.2);
            myChart.addNewValueToBestSeries(i, population.findBestAdoptedIndividual().getFitness());
            myChart.addNewValueToAverageSeries(i, population.averageIndividualsFitness());
            myChart.addNewValueToWorstSeries(i, population.findWorstAdoptedIndividual().getFitness());
            myChart.repaintChart();
            graphPanelVisual.setBest(population.findBestAdoptedIndividual());
            graphPanelVisual.repaint();
        }
        
    }

}
