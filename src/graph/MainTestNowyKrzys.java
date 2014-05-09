package graph;

import java.io.IOException;
import java.util.Random;

import org.jfree.chart.ChartPanel;

import GUI.Chart;
import exceptions.GeneticAlgorithmException;
import exceptions.NoPossibilityToCreateGraphException;
import exceptions.ProblemWithReadingGraphFromFileException;
import genetics.CrossingOverType;
import genetics.IndividualType;
import genetics.Population;
import genetics.SelectionType;

public class MainTestNowyKrzys {
static Random rand = new Random();
    // TO DO musimy ustawic tak zeby zawsze jakas czesc np. 10% dmandenAmount nowych osobnikow sie dodawalo do populacji,
    // a potem dopiero kasowac jak jest za duzo
    public static void main(String[] args) throws IOException, ProblemWithReadingGraphFromFileException, GeneticAlgorithmException, InterruptedException {

        GraphRepresentation gr = null;
        try {
            // gr = new GraphRepresentation("graph", 4);
            gr = new GraphRepresentation(128, 6326, 67, 67);
            // gr = new GraphRepresentation(64, 300, 20, true);
            // gr = new GraphRepresentation(10, 6, 3, true);
        } catch (NoPossibilityToCreateGraphException e) {
            e.printStackTrace();
        }

        for (int j = 20; j < 30; j++) {
            Population population = new Population(50, gr, IndividualType.GROUPCODEDINDIVIDUAL, 12);
            // Population population = new Population(20, gr, IndividualType.BINARYCODEDINDIVIDUAL);
            // Population population = new Population(50, gr, IndividualType.GROUPCODEDINDIVIDUAL,22);
            Chart myChart = new Chart("K-clique solver", "Przystosowanie osobników w populacji", "Iteracja", "Przystosowanie");

            ChartPanel myFrame = myChart.getChartPanel();
            // myFrame.setVisible(true);
            myFrame.setSize(500, 400);
            // myChart.repaintChart();
            SelectionType ST = SelectionType.values()[rand.nextInt(3)];
            CrossingOverType COT = CrossingOverType.values()[rand.nextInt(6)];
            for (int i = 1; i < 600; i++) {
             //   System.out.println("Iteracja " + i);
             //   System.out.println(population.findBestAdoptedIndividual());
                
                if (i % 100 == 0) {
                    population.singleLifeCycle(true, ST, 0.6, COT, 0.05, 0.7);
                } else {
                    population.singleLifeCycle(false, ST, 0.6, COT, 0.05, 0.7);
                }

                myChart.addNewValueToBestSeries(i, population.findBestAdoptedIndividual().getFitness());
                myChart.addNewValueToAverageSeries(i, population.averageIndividualsFitness());
                myChart.addNewValueToWorstSeries(i, population.findWorstAdoptedIndividual().getFitness());
                myChart.repaintChart();
            }
            myChart.saveChartToFile("tmp " + ST + " " + COT + j);
        }
    }
}