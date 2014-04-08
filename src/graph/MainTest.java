package graph;

import edu.uci.ics.jung.graph.Graph;
import exceptions.NoPossibilityToCreateGraphException;
import genetics.CrossingOver;
import genetics.CrossingOverType;
import genetics.Mutation;
import genetics.Population;
import genetics.Selection;

public class MainTest {

	// ODPAL, ŁADNIE POKAZUJE ZE W KAZDYM NOWYM POKOLENIU SUMA PRZYSTOSOWANIA OSOBNIKOW WZRASTA!
    public static void main(String[] args) {
    	
    	Graph<Integer, String> myGraph2 = null;
		try {
			myGraph2 = GraphFactory.createGraph2(12, 20, 150);
		} catch (NoPossibilityToCreateGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(myGraph2);
    	
    	
    	
    	
    	
    	
    	Graph<Integer, String> myGraph = null;
		try {
			myGraph = GraphFactory.createGraph(20, 150);
		} catch (NoPossibilityToCreateGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(myGraph);
		CrossingOver crossingOver = new CrossingOver(0.7);
		Mutation mutation = new Mutation(0.02);
        Population population = new Population(10000, 20, myGraph2, 12);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
        // System.out.println(population);
        population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
        // dla 1000 pokolen
        for (int i = 0; i < 100; i++) {
        	population.dostosowanie();
        	System.out.println("Iteracja " + i);
        	//System.out.println(population);
            population = Selection.tournament(population, 2); // dokonaj selekcji, stworz pokolenie rodzicow (posrednie)
            //System.out.println(population);
            population = crossingOver.crossOver(CrossingOverType.ONEPOINTWITHTWOCHILDREN, population);
            //System.out.println(population + "BBBBBBBB");
            mutation.mutate(population); // mutuj losowe
            //System.out.println(population + "CCCCCCCCCCc");
            population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
            //System.out.println(population + "AAAAAAAAAA");
            population.removeWorstIndividuals(0.3);
            //System.out.println(population);
            population.keepConstantPopulationSize();
        }
        //population.dostosowanie();
        //System.out.println(population);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
        // System.out.println(population);
    }
}
