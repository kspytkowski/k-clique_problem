package graph;

import java.io.IOException;

import org.jfree.chart.ChartFrame;

import GUI.Chart;
import exceptions.GeneticAlgorithmException;
import exceptions.NoPossibilityToCreateGraphException;
import exceptions.NoPossibilityToCreateIndividualWithGivenParameters;
import exceptions.ProblemWithReadingGraphFromFileException;
import genetics.CrossingOver;
import genetics.CrossingOverType;
import genetics.IndividualType;
import genetics.Mutation;
import genetics.Population;
import genetics.Selection;

public class MainTestNowy {

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

        CrossingOver crossingOver = new CrossingOver(0.6);
        Mutation mutation = new Mutation(0.05);
        Population population = new Population(50, gr, IndividualType.GROUPCODEDINDIVIDUAL, 8);
        // Population population = new Population(50, gr, IndividualType.BINARYCODEDINDIVIDUAL);

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

        for (int i = 0; i < 1000; i++) {
            System.out.println("Iteracja " + i);
            System.out.println(population.findBestAdoptedIndividual());

            bestIndividualChart.actualizeChart(population.findBestAdoptedIndividual().getFitness());
            averageFitnessChart.actualizeChart(population.averageIndividualsFitness());
            worstIndividualChart.actualizeChart(population.findWorstAdoptedIndividual().getFitness());

            Selection.rouletteWheelSelection(population);

            crossingOver.crossOver(CrossingOverType.ONEPOINTWITHTWOCHILDREN, population);

            mutation.mutate(population);

            population.removeWorstIndividuals(0.7); // MUSIMY TAK DUZO USUWAC, zeby populacja z iteracji na iteracje nie rosła,
                                                    // potem sie zmieni odpowiednio keepConstantPopulationSize() zeby o to dbała
            population.keepConstantPopulationSize();
            population.printDostatosowanie();

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