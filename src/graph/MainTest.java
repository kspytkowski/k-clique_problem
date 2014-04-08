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
    	Graph<Integer, String> myGraph = null;
		try {
			myGraph = GraphFactory.createGraph(20, 150);
		} catch (NoPossibilityToCreateGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(myGraph);
		CrossingOver crossingOver = new CrossingOver(0.7);
		Mutation mutation = new Mutation(0.0);
        Population population = new Population(1024, 20, 18);
        population.setMyGraph(myGraph);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
         System.out.println(population);
        population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
        // dla 1000 pokolen
        for (int i = 0; i < 9; i++) {
            population = Selection.tournament(population, 2); // dokonaj selekcji, stworz pokolenie rodzicow (posrednie)
            population = crossingOver.crossOver(CrossingOverType.ONEPOINTWITHTWOCHILDREN, population);
            mutation.mutate(population); // mutuj losowe
            population.keepConstantPopulationSize();
            population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
            //System.out.println(population);
        }
        System.out.println(population);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
        // System.out.println(population);
    }
}
