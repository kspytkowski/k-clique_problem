package graph;

import java.util.LinkedList;

import exceptions.NoPossibilityToCreateGraphException;
import genetics.Individual;
import genetics.Population;

/* IT'S JUST TO DEMONSTRATE DISPLAYING OF GRAPH */
public class MainTest {

	public static void main(String[] args) {
		
		//MainTest mainTest = new MainTest();
//		GraphVisualisation myGraphVisualisation = new GraphVisualisation(myGraph);
//		myGraphVisualisation.CircleGraphVisualisation();
//		//myGraphVisualisation.FRGraphVisualisation();
		
		// 6 rozmiar grafu, 2 rozmiar poszukiwanej k-kliki
	    //Individual i = new Individual(6,2);
	    //System.out.println(i);
	    //Individual ii = new Individual(6,2);
	    //System.out.println(ii);
	    Population population = new Population(6,6,2,0.5,0.01);
	    System.out.println(population);
	    population.initializeInterbreeding(1);
	    System.out.println(population);
	    
/*	    Population population2 = new Population(2,6,2,0.001,0.1);
	    System.out.println(population2);
	    population2.interbreeding2();
	    System.out.println(population2);
	    
	    Population population3 = new Population(2,6,2,0.001,0.1);
	    System.out.println(population3);
	    population3.interbreeding3();
	    System.out.println(population3);*/
	    

	}
}
