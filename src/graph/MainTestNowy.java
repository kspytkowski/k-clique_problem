package graph;

import java.io.IOException;

import org.jfree.chart.ChartFrame;

import GUI.Chart;
import exceptions.GeneticAlgorithmException;
import exceptions.NoPossibilityToCreateGraphException;
import exceptions.ProblemWithReadingGraphFromFileException;
import genetics.CrossingOverType;
import genetics.GroupCodedIndividual;
import genetics.IndividualType;
import genetics.Population;
import genetics.SelectionType;

public class MainTestNowy {

    // TO DO musimy ustawic tak zeby zawsze jakas czesc np. 10% dmandenAmount nowych osobnikow sie dodawalo do populacji,
    // a potem dopiero kasowac jak jest za duzo
    
    public static void main(String[] args) throws IOException, ProblemWithReadingGraphFromFileException, GeneticAlgorithmException, InterruptedException{

        GraphRepresentation gr = null;
        try {
            // gr = new GraphRepresentation("graph", 4);
            gr = new GraphRepresentation(128, 6326, 67, 67);
            // gr = new GraphRepresentation(64, 300, 20, true);
            // gr = new GraphRepresentation(10, 6, 3, true);
        } catch (NoPossibilityToCreateGraphException e) {
            e.printStackTrace();
        }


     /*  GroupCodedIndividual a = new GroupCodedIndividual(40, gr);
       System.out.println(a);
        for (int i = 0; i < 40; i++) {
            System.out.println(a.getAmountOfVertexesInGroup(i) + " " + a.determineFitnessOfSubrgaph(i));
        }
        a.removeWorstGroup();
        System.out.println(a);
        for (int i = 0; i < 39; i++) {
            System.out.println(a.getAmountOfVertexesInGroup(i) + " " + a.determineFitnessOfSubrgaph(i));
        }
        a.determineIndividualFitness();
        System.out.println(a);
        for (int i = 0; i < 39; i++) {
            System.out.println(a.getAmountOfVertexesInGroup(i) + " " + a.determineFitnessOfSubrgaph(i));
        }*/
        Population population = new Population(20, gr, IndividualType.GROUPCODEDINDIVIDUAL, 12);
//        Population population = new Population(50, gr, IndividualType.BINARYCODEDINDIVIDUAL);
        // Population population = new Population(50, gr, IndividualType.GROUPCODEDINDIVIDUAL,22);
        
        Chart myChart = new Chart("K-clique solver", "Przystosowanie osobników w populacji", "Iteracja", "Przystosowanie");

        ChartFrame myFrame = myChart.getChartFrame();
        myFrame.setVisible(true);
        myFrame.setSize(500, 400);
        myChart.repaintChart();
        
        for (int i = 1; i < 1000; i++) {
            System.out.println("Iteracja " + i);
            System.out.println(population.findBestAdoptedIndividual());
            if (i % 100 == 0) {
                population.singleLifeCycle(true, SelectionType.ROULETTEWHEELSELECTION, 0.6, CrossingOverType.ONEPOINTWITHONECHILD, 0.05, 0.7);
            } else {
                population.singleLifeCycle(false, SelectionType.ROULETTEWHEELSELECTION, 0.6, CrossingOverType.ONEPOINTWITHONECHILD, 0.05, 0.7);
            }
            
            myChart.addNewValueToBestSeries(i,population.findBestAdoptedIndividual().getFitness());
            myChart.addNewValueToAverageSeries(i,population.averageIndividualsFitness());
            myChart.addNewValueToWorstSeries(i,population.findWorstAdoptedIndividual().getFitness());
            myChart.repaintChart();
        }
    }
}