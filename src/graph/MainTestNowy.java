package graph;

import java.io.IOException;
import java.util.LinkedList;
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
import genetics.Individual;
import genetics.IndividualType;
import genetics.Mutation;
import genetics.Population;
import genetics.Selection;

public class MainTestNowy {

    // tworzac individualse robimy to tak, ze te nowe sa lepsze/niegorszze od reszty populacji, ktora jest juz po krzyzowaniu,
    // a wiec jest gorsza... przez co rating ogolny nam sie nie polepsza :/
    // ODPAL, ŁADNIE POKAZUJE ZE W KAZDYM NOWYM POKOLENIU SUMA PRZYSTOSOWANIA OSOBNIKOW WZRASTA!
    public static void main(String[] args) throws IOException, ProblemWithReadingGraphFromFileException, GeneticAlgorithmException, NoPossibilityToCreateIndividualWithGivenParameters {

        
        
        
        
        GraphRepresentation gr = null;
        try {
            //gr = new GraphRepresentation("graph", 4);
           // gr = new GraphRepresentation(128, 2326, 67, true);
            gr = new GraphRepresentation(64, 300, 20, true);
//            gr = new GraphRepresentation(10, 6, 3, true);
 //           System.out.println(gr);
        } catch (NoPossibilityToCreateGraphException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
     /*   GraphRepresentation gra = null;
        try {
            //gr = new GraphRepresentation("graph", 4);
            gra = new GraphRepresentation(10, 20, 6, true);
            System.out.println(gra);
        } catch (NoPossibilityToCreateGraphException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        
        // System.out.println(myGraph);
        CrossingOver crossingOver = new CrossingOver(0.6);
        Mutation mutation = new Mutation(0.0007);
        //GroupCodedIndividual.setNumberOfSubgraphs(8); 
        Population population = new Population(50, gr, IndividualType.GROUPCODEDINDIVIDUAL,6);
     //   Population population = new Population(600, gr, IndividualType.BINARYCODEDINDIVIDUAL);
   //     Population population = new Population(3, 10, gra, 6, IndividualType.GROUPCODEDINDIVIDUAL);
        // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
    //    System.out.println(population);
    //    population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
   //     System.out.println(population);
        // dla 1000 pokolen
        
        Chart bestIndividualChart = new Chart("K-clique solver", "Przystosowanie najlepszego osobnika w populacji", "Iteracja", "Przystosowanie");
        Chart averageFitnessChart = new Chart("K-clique solver", "Średnie przystosowanie osobników w populacji", "Iteracja", "Przystosowanie");
        Chart worstIndividualChart = new Chart("K-clique solver", "Przystosowanie najgorszego osobnika w populacji", "Iteracja", "Przystosowanie");
        
       
        ChartFrame bestIndividualFrame = bestIndividualChart.getChartFrame();
        bestIndividualFrame.setVisible(true);
        bestIndividualFrame.setSize(500, 400);
        ChartFrame averageFitnessFrame = averageFitnessChart.getChartFrame();
        averageFitnessFrame.setVisible(true);
        averageFitnessFrame.setSize(500, 400);
        ChartFrame worstIndividualFrame = worstIndividualChart.getChartFrame();
        worstIndividualFrame.setVisible(true);
        worstIndividualFrame.setSize(500, 400);
        
      //  frame1.
        
        for (int i = 0; i < 1000; i++) {
            System.out.println("Iteracja " + i);
            System.out.println(population.findBestAdoptedIndividual());
            
            
            bestIndividualChart.actualizeChart(population.findBestAdoptedIndividual().getFitness());
            averageFitnessChart.actualizeChart(population.averageIndividualsFitness());
            worstIndividualChart.actualizeChart(population.findWorstAdoptedIndividual().getFitness());
            
            
            
            Selection.rouletteWheelSelection(population); // dokonaj selekcji, stworz pokolenie ro
             crossingOver.crossOver(CrossingOverType.TWOPOINTSWITHTWOCHILDREN, population);
             population.removeWorstIndividuals(0.1);
            population.keepConstantPopulationSize();
            population.printDostatosowanie();
        }
        

       
        
  //      population.dostosowanie();
    //     System.out.println(population);
        // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
    //    System.out.println(population);
  /*      try {
            GraphRepresentation graph = new GraphRepresentation(10, 20, 5, true);
            Individual a = new Individual(10, graph);
            System.out.println("----------------------");
            System.out.println(graph);
            System.out.println("----------------------");
            System.out.println(a);
            System.out.println("----------------------");
            a.relabelIndividual();
            System.out.println("----------------------");
            System.out.println(a);
            System.out.println("----------------------");
            a.removeWorstGroup();
            System.out.println("----------------------");
            System.out.println(a);
            System.out.println("----------------------");
            a.determineIndividualFitness();
            System.out.println("----------------------");
            System.out.println(a);
            System.out.println("----------------------");
            System.out.println("----------------------");
            GraphRepresentation graph2 = new GraphRepresentation("graph", 3);
            System.out.println(graph2);
            graph.writeGraphToFile("/home/krzysztof/workspace/k-clique_problem", "graph");
        } catch (NoPossibilityToCreateIndividualWithGivenParameters | NoPossibilityToCreateGraphException | GeneticAlgorithmException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}