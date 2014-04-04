package graph;

import genetics.Population;

public class MainTest {

	// ODPAL, ŁADNIE POKAZUJE ZE W KAZDYM NOWYM POKOLENIU SUMA PRZYSTOSOWANIA OSOBNIKOW WZRASTA!
	
	public static void main(String[] args) {
	
		// 10000 osobnikow, 100 - ilosc wierzcholkow grafu, 20 - szukana k=20 klika, 0.6 - prawdop. krzyzowania, 0.02 - prawdop. mutacji
		// przy tak duzej liczbie osobnikow, tak malym grafie, juz po ok. 1000 iteracjach (pokoleniach) dostajemy najlepszy wynik, kotry potem
		// lekko oscyluje (krzyzowania przestaja miec jakiekolwwiek znaczenie, wynik jest zmieniany jedynie przez mutacje, ale
		// w sposob nieznaczacy)
	    Population population = new Population(10000,100,20,0.6,0.02);
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
	    System.out.println(population);
	    // dla 1000 pokolen
	    for (int i  = 0; i < 1000; i++) {
	       population.dostosowanie(); // oblicz przystosowanie kazdego osobnika
	       population.rouletteSelection(); // stworz nowe pokolenie
		   population.initializeInterbreeding(1); // krzyzuj
		   population.mutate(); // mutuj		  
	    }
	    population.dostosowanie();
	    // przy tak duzej liczbie osobnikow radze zakomentowac ponizsza linijke!
	    System.out.println(population);

	    // LEPIEJ PREZENTOWAC DLA DANYCH: 
	    // Population(100,100,20,0.6,0.02);
	}
}
