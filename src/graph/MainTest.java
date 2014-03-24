package graph;

import java.awt.Dimension;

import javax.swing.JFrame;

import exceptions.NoPossibilityToCreateGraphException;

/* IT'S JUST TO DEMONSTRATE DISPLAYING OF GRAPH */
public class MainTest extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		MainTest mainTest = new MainTest();
		MyGraph myGraph = null;
		try {
			myGraph = new MyGraph(10,30);
		} catch (NoPossibilityToCreateGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GraphVisualisation myGraphVisualisation = new GraphVisualisation(myGraph);
		myGraphVisualisation.CircleGraphVisualisation();
		myGraphVisualisation.FRGraphVisualisation();
		
		mainTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    mainTest.pack();
	    mainTest.setSize (new Dimension (100, 740));
	    mainTest.setVisible(true);

	}
}
