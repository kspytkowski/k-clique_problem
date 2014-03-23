package graph;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class MainTest extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		MainTest mainTest = new MainTest();
		
		MyGraph myGraph = new MyGraph();
		
		mainTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    Graph<Integer, String> g = myGraph.getRandomGraph(100,300);
	    VisualizationViewer<Integer,String> vv = 
	     new VisualizationViewer<Integer,String>(new FRLayout<Integer, String>(g),
	     new Dimension (1000,700));
	    mainTest.getContentPane().add(vv);
	 
	    mainTest.pack();
	    mainTest.setSize (new Dimension (1000, 740));
	    mainTest.setVisible(true);

	}
}
