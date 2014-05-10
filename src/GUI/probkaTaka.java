package GUI;

import exceptions.GeneticAlgorithmException;
import genetics.CrossingOverType;
import genetics.IndividualType;
import genetics.Population;
import graph.GraphRepresentation;

public class probkaTaka implements Runnable {
    
    GraphRepresentation a;
    GraphPanelKRZYSIEK graphPanelVisual;
    Chart myChart;
    
    
    public probkaTaka(GraphRepresentation a, GraphPanelKRZYSIEK graphPanelVisual, Chart myChart) {
        this.a = a;
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
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
