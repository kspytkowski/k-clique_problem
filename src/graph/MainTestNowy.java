package graph;

import static java.lang.Thread.sleep;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jfree.chart.ChartFrame;

import GUI.Chart;
import exceptions.GeneticAlgorithmException;
import exceptions.NoPossibilityToCreateGraphException;
import exceptions.NoPossibilityToCreateIndividualWithGivenParameters;
import exceptions.ProblemWithReadingGraphFromFileException;
import genetics.AbstractIndividual;
import genetics.CrossingOver;
import genetics.CrossingOverType;
import genetics.GroupCodedIndividual;
import genetics.IndividualType;
import genetics.Mutation;
import genetics.Population;
import genetics.Selection;

public class MainTestNowy {

    // TO DO musimy ustawic tak zeby zawsze jakas czesc np. 10% dmandenAmount nowych osobnikow sie dodawalo do populacji,
    // a potem dopiero kasowac jak jest za duzo
    
    public static void main(String[] args) throws IOException, ProblemWithReadingGraphFromFileException, GeneticAlgorithmException, NoPossibilityToCreateIndividualWithGivenParameters {

        GraphRepresentation gr = null;
        try {
            // gr = new GraphRepresentation("graph", 4);
            gr = new GraphRepresentation(128, 2326, 67, true);
            // gr = new GraphRepresentation(64, 300, 20, true);
            // gr = new GraphRepresentation(10, 6, 3, true);
        } catch (NoPossibilityToCreateGraphException e) {
            e.printStackTrace();
        }

//        CrossingOver crossingOver = new CrossingOver(0.6);
//        Mutation mutation = new Mutation(0.05);
        Population population = new Population(50, gr, IndividualType.GROUPCODEDINDIVIDUAL, 8);
//        Population population = new Population(50, gr, IndividualType.BINARYCODEDINDIVIDUAL);
        // Population population = new Population(50, gr, IndividualType.GROUPCODEDINDIVIDUAL,22);
        
        Chart myChart = new Chart("K-clique solver", "Przystosowanie osobników w populacji", "Iteracja", "Przystosowanie");
   /*     Chart averageFitnessChart = new Chart("K-clique solver", "Średnie przystosowanie osobników w populacji", "Iteracja", "Przystosowanie");
        Chart worstIndividualChart = new Chart("K-clique solver", "Przystosowanie najgorszego osobnika w populacji", "Iteracja", "Przystosowanie");*/

        ChartFrame myFrame = myChart.getChartFrame();
        myFrame.setVisible(true);
        myFrame.setSize(500, 400);
     /*   ChartFrame averageFitnessFrame = averageFitnessChart.getChartFrame();
        averageFitnessFrame.setVisible(true);
        averageFitnessFrame.setSize(500, 400);
        ChartFrame worstIndividualFrame = worstIndividualChart.getChartFrame();
        worstIndividualFrame.setVisible(true);
        worstIndividualFrame.setSize(500, 400);*/
        
        for (int i = 1; i < 1000; i++) {
            System.out.println("Iteracja " + i);
            System.out.println(population.findBestAdoptedIndividual());
            
            if (i % 100 == 0) {
              //  for (AbstractIndividual ind : population.getIndividuals()){
              //      System.out.println(ind);
              //  }
                population.singleLifeCycle(true, 0.6, CrossingOverType.ONEPOINTWITHONECHILD, 0.05);
//                if (basic > 1) basic--;
//                for (AbstractIndividual a : population.getIndividuals())
//                     a.removeWorstGroup();
//                GroupCodedIndividual a = (GroupCodedIndividual) population.findBestAdoptedIndividual();
//                System.out.println(a.getNumberOfSubgraphs());
//                System.out.println(a.getRealNumberOfSubgraphs());
//                population.setNumberOfGroups(basic);
            } else {
                population.singleLifeCycle(false, 0.6, CrossingOverType.ONEPOINTWITHONECHILD, 0.05);
            }
         //   if (i % 100 == 1) {
         //       for (AbstractIndividual ind : population.getIndividuals()){
         //           System.out.println(ind);
         //       }
        //    }
            
            myChart.addNewValueToBestSeries(i,population.findBestAdoptedIndividual().getFitness());
            myChart.addNewValueToAverageSeries(i,population.averageIndividualsFitness());
            myChart.addNewValueToWorstSeries(i,population.findWorstAdoptedIndividual().getFitness());
            myChart.repaintChart();

            
            

        }
        // try {
        // GraphRepresentation graph = new GraphRepresentation(10, 20, 5, true);
        // Individual a = new Individual(10, graph);
        // System.out.println("----------------------");
        // System.out.println(graph);
        // System.out.println("----------------------");
        // System.out.println(a);
        // System.out.println("----------------------");
        // a.relabelIndividual();
        // System.out.println("----------------------");
        // System.out.println(a);
        // System.out.println("----------------------");
        // a.removeWorstGroup();
        // System.out.println("----------------------");
        // System.out.println(a);
        // System.out.println("----------------------");
        // a.determineIndividualFitness();
        // System.out.println("----------------------");
        // System.out.println(a);
        // System.out.println("----------------------");
        // System.out.println("----------------------");
        // GraphRepresentation graph2 = new GraphRepresentation("graph", 3);
        // System.out.println(graph2);
        // graph.writeGraphToFile("/home/krzysztof/workspace/k-clique_problem", "graph");
        // } catch (NoPossibilityToCreateIndividualWithGivenParameters | NoPossibilityToCreateGraphException | GeneticAlgorithmException ex) {
        // Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        // }
    }
}