package graph;

import edu.uci.ics.jung.graph.Graph;
import exceptions.NoPossibilityToCreateGraphException;
import genetics.CrossingOver;
import genetics.CrossingOverType;
import genetics.Mutation;
import genetics.Population;
import genetics.Selection;


public class MainTest {

	// tworzac individualse robimy to tak, ze te nowe sa lepsze/niegorszze od reszty populacji, ktora jest juz po krzyzowaniu,
	// a wiec jest gorsza... przez co rating ogolny nam sie nie polepsza :/
	
	
	// ODPAL, ŁADNIE POKAZUJE ZE W KAZDYM NOWYM POKOLENIU SUMA PRZYSTOSOWANIA OSOBNIKOW WZRASTA!
    public static void main(String[] args) {
    	GraphRepresentation gr = null;
    	try {
			gr = new GraphRepresentation(50, 1000, 20, false);
			System.out.println(gr);
		} catch (NoPossibilityToCreateGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	
   /* 	Graph<Integer, String> myGraph2 = null;
		try {
			myGraph2 = GraphFactory.createGraph2(50, 1000, 20);
		} catch (NoPossibilityToCreateGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(myGraph2);
    	
    	
    	Graph<Integer, String> myGraph = null;
		try {
			myGraph = GraphFactory.createGraph(50, 1000, 20);
		} catch (NoPossibilityToCreateGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//System.out.println(myGraph);
		CrossingOver crossingOver = new CrossingOver(0.0);
		Mutation mutation = new Mutation(0.00);
        Population population = new Population(10, 50, gr, 20);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
        System.out.println(population);
        population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
        System.out.println(population);
        // dla 1000 pokolen
        for (int i = 0; i < 10; i++) {
        	System.out.println("Iteracja " + i);
        	population.dostosowanie();
        	//System.out.println(population);
            population = Selection.tournament(population, 2); // dokonaj selekcji, stworz pokolenie rodzicow (posrednie)
            //System.out.println(population);
            population = crossingOver.crossOver(CrossingOverType.UNIFORMCROSSOVER, population);
            
            //System.out.println(population);
            //System.out.println(population + "BBBBBBBB");
            //mutation.mutate(population); // mutuj losowe
            //System.out.println(population + "CCCCCCCCCCc");
            population.dostosowanie();
            population.napraw();
            population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
            //System.out.println(population + "AAAAAAAAAA");
           // population.removeWorstIndividuals(0.1);
            //System.out.println(population);
            
            population.keepConstantPopulationSize();
            population.dostosowanie();
            //System.out.println(population);
            population.printDostatosowanie();
          System.out.println(population);
        }
        population.dostosowanie();
        //System.out.println(population);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
         System.out.println(population);
    }
}
