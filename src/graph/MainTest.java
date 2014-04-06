package graph;

import edu.uci.ics.jung.graph.Graph;
import exceptions.NoPossibilityToCreateGraphException;
import genetics.Population;

public class MainTest {

	// ODPAL, ŁADNIE POKAZUJE ZE W KAZDYM NOWYM POKOLENIU SUMA PRZYSTOSOWANIA OSOBNIKOW WZRASTA!
    public static void main(String[] args) {
    	Graph<Integer, String> myGraph = null;
		try {
			myGraph = MyGraph.createGraph(20, 150);
		} catch (NoPossibilityToCreateGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(myGraph);
		// 100 osobnikow, 100 - ilosc wierzcholkow grafu, 20 - szukana k=20 klika, 0.6 - prawdop. krzyzowania, 0.02 - prawdop. mutacji
        // przy tak duzej liczbie osobnikow, tak malym grafie, juz po ok. 100 iteracjach (pokoleniach) dostajemy najlepszy wynik, kotry potem
        // lekko oscyluje (krzyzowania przestaja miec jakiekolwwiek znaczenie, wynik jest zmieniany jedynie przez mutacje, ale
        // w sposob nieznaczacy)
        Population population = new Population(100, 20, 8, 0.6, 0.02);
        population.setMyGraph(myGraph);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
        // System.out.println(population);
        population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
        // dla 1000 pokolen
        for (int i = 0; i < 100; i++) {
            population.rouletteSelection(); // dokonaj selekcji, stworz pokolenie rodzicow (posrednie)
            population.initializeCrossingOver(1); // krzyzuj losowe
            population.mutate(); // mutuj losowe
            population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
        //    System.out.println(population);
        }
        System.out.println(population);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
        // System.out.println(population);
    }
}
