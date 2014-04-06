package graph;

import edu.uci.ics.jung.graph.Graph;
import exceptions.NoPossibilityToCreateGraphException;
import genetics.CrossingOverType;
import genetics.Individual;
import genetics.Population;

public class MainTest {

	// ODPAL, ŁADNIE POKAZUJE ZE W KAZDYM NOWYM POKOLENIU SUMA PRZYSTOSOWANIA OSOBNIKOW WZRASTA!
    public static void main(String[] args) {
    	
    	Individual one = new Individual(10, 4);
    	Individual two = new Individual(10, 4);
    	
    	
    	
    	
    	
    	Graph<Integer, String> myGraph = null;
		try {
			myGraph = GraphFactory.createGraph(20, 180);
		} catch (NoPossibilityToCreateGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(myGraph);
		// 100 osobnikow, 100 - ilosc wierzcholkow grafu, 20 - szukana k=20 klika, 0.6 - prawdop. krzyzowania, 0.02 - prawdop. mutacji
        // przy tak duzej liczbie osobnikow, tak malym grafie, juz po ok. 100 iteracjach (pokoleniach) dostajemy najlepszy wynik, kotry potem
        // lekko oscyluje (krzyzowania przestaja miec jakiekolwwiek znaczenie, wynik jest zmieniany jedynie przez mutacje, ale
        // w sposob nieznaczacy)
        Population population = new Population(1000, 20, 15, 0.7, 0.02);
        population.setMyGraph(myGraph);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
         System.out.println(population);
        population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
        // dla 1000 pokolen
        for (int i = 0; i < 1000; i++) {
            population.rouletteWheelSelection(); // dokonaj selekcji, stworz pokolenie rodzicow (posrednie)
            population.initializeCrossingOver(CrossingOverType.ONEPOINTWITHTWOCHILDREN); // krzyzuj losowe
            population.mutate(); // mutuj losowe
            population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
        //    System.out.println(population);
        }
        System.out.println(population);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
        // System.out.println(population);
    }
}
