package graph;

import exceptions.GeneticAlgorithmException;
import exceptions.NoPossibilityToCreateGraphException;
import exceptions.NoPossibilityToCreateIndividualWithGivenParameters;
import exceptions.ProblemWithReadingGraphFromFileException;
import genetics.CrossingOver;
import genetics.CrossingOverType;
import genetics.GroupCodedIndividual;
import genetics.Individual;
import genetics.IndividualType;
import genetics.Mutation;
import genetics.Population;
import genetics.Selection;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainTestNowy {

    // tworzac individualse robimy to tak, ze te nowe sa lepsze/niegorszze od reszty populacji, ktora jest juz po krzyzowaniu,
    // a wiec jest gorsza... przez co rating ogolny nam sie nie polepsza :/
    // ODPAL, ŁADNIE POKAZUJE ZE W KAZDYM NOWYM POKOLENIU SUMA PRZYSTOSOWANIA OSOBNIKOW WZRASTA!
    public static void main(String[] args) throws IOException, ProblemWithReadingGraphFromFileException, GeneticAlgorithmException, NoPossibilityToCreateIndividualWithGivenParameters {

        
        
        
        
        GraphRepresentation gr = null;
        try {
            //gr = new GraphRepresentation("graph", 4);
            gr = new GraphRepresentation(128, 2326, 67, true);
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
        Population population = new Population(600, gr, IndividualType.BINARYCODEDINDIVIDUAL);
     //   Population population = new Population(600, gr, IndividualType.BINARYCODEDINDIVIDUAL);
   //     Population population = new Population(3, 10, gra, 6, IndividualType.GROUPCODEDINDIVIDUAL);
        // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
    //    System.out.println(population);
    //    population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
   //     System.out.println(population);
        // dla 1000 pokolen
        for (int i = 0; i < 900; i++) {
            System.out.println("Iteracja " + i);
     //       population.dostosowanie();
      //       System.out.println("QQQQQQQQQ" + population);
            Selection.rouletteWheelSelection(population); // dokonaj selekcji, stworz pokolenie rodzicow (posrednie)
     //        System.out.println("QQQQQQQQQ" + population);
      //      population.dostosowanie();
     //       population.dostosowanie();
  /*          for (int j = 0; j < population.getActualIndividualsAmount(); j++) {
                ((GroupCodedIndividual) (population.getIndividual(j))).relabelIndividual();
            }*/
             crossingOver.crossOver(CrossingOverType.ONEPOINTWITHTWOCHILDREN, population);
  /*           for (int j = 0; j < population.getActualIndividualsAmount(); j++) {
                 ((GroupCodedIndividual) (population.getIndividual(j))).relabelIndividual();
             }*/
            // System.out.println(population);
            // System.out.println(population + "BBBBBBBB");
             mutation.mutate(population); // mutuj losowe
            // System.out.println(population + "CCCCCCCCCCc");
           // population.dostosowanie();
           // population.napraw();
      //      population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
            // System.out.println(population + "AAAAAAAAAA");
     //        population.dostosowanie();
             population.removeWorstIndividuals(0.1);
            // System.out.println(population);
   //          population.dostosowanie();
            population.keepConstantPopulationSize();
   //         population.dostosowanie();
            // System.out.println(population);
            population.printDostatosowanie();
            population.findbest();
   //         System.out.println(population);
        }
  //      population.dostosowanie();
    //     System.out.println(population);
        // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
    //    System.out.println(population);
        try {
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
        }
    }
}