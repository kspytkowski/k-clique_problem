package graph;

import javax.swing.JFrame;

import exceptions.NoPossibilityToCreateGraphException;
import genetics.Individual;

/* IT'S JUST TO DEMONSTRATE DISPLAYING OF GRAPH */
public class MainTest {

	public static void main(String[] args) {
		
		//MainTest mainTest = new MainTest();
		MyGraph myGraph = null;
		try {
			myGraph = new MyGraph(10,30);
		} catch (NoPossibilityToCreateGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		GraphVisualisation myGraphVisualisation = new GraphVisualisation(myGraph);
//		myGraphVisualisation.CircleGraphVisualisation();
//		//myGraphVisualisation.FRGraphVisualisation();
		
		// 6 rozmiar grafu, 2 rozmiar poszukiwanej k-kliki
	    Individual i = new Individual(6,2);
	    System.out.println(i);
	    Individual ii = new Individual(6,2);
	    System.out.println(ii);
	    Individual iii = new Individual(6,2);
	    System.out.println(iii);
	    

	}
}
