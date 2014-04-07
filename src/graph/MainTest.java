package graph;

import edu.uci.ics.jung.graph.Graph;
import exceptions.NoPossibilityToCreateGraphException;
import genetics.CrossingOver;
import genetics.CrossingOverType;
import genetics.Individual;
import genetics.Mutation;
import genetics.Population;
import genetics.Selection;

public class MainTest {

	// ODPAL, ŁADNIE POKAZUJE ZE W KAZDYM NOWYM POKOLENIU SUMA PRZYSTOSOWANIA OSOBNIKOW WZRASTA!
    public static void main(String[] args) {
    	Graph<Integer, String> myGraph = null;
		try {
			myGraph = GraphFactory.createGraph(20, 180);
		} catch (NoPossibilityToCreateGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(myGraph);
		CrossingOver crossingOver = new CrossingOver(0.7);
		Mutation mutation = new Mutation(0.02);
        Population population = new Population(100, 20, 15);
        population.setMyGraph(myGraph);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
         System.out.println(population);
        population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
        // dla 1000 pokolen
        for (int i = 0; i < 1000; i++) {
            population = Selection.rouletteWheelSelection(population); // dokonaj selekcji, stworz pokolenie rodzicow (posrednie)
            population = crossingOver.crossOver(CrossingOverType.ONEPOINTWITHTWOCHILDREN, population);
            //population.initializeCrossingOver(CrossingOverType.ONEPOINTWITHTWOCHILDREN); // krzyzuj losowe
            mutation.mutate(population); // mutuj losowe
            population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
            //System.out.println(population);
        }
        System.out.println(population);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
        // System.out.println(population);
    }
}
