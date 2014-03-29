package graph;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * @author Krzysztof Spytkowski
 * @date 24 mar 2014
 */
public class GraphVisualisation extends JFrame {
	
	private static final long serialVersionUID = 1L; // default serial version number
	private final MyGraph graph; // graph to be displayed

	/**
	 * Constructor
	 * 
         * @param graph
	 */
	public GraphVisualisation(MyGraph graph) {
		this.graph = graph;
	}

	/**
	 * Visualize graph
	 * 
	 * @param l
	 *            - layout
	 */
	private void visualize(Layout<Integer, String> l) {
		setSize(new Dimension(400, 300));
		VisualizationViewer<Integer, String> vv = new VisualizationViewer<>(l, new Dimension(300, 200));
		getContentPane().add(vv);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	};

	/**
	 * Visualize graph (A simple layout places vertices randomly on a circle)
	 */
	public void CircleGraphVisualisation() {
		Layout<Integer, String> layout = new CircleLayout<>(graph.getGraph());
		visualize(layout);
	}

	/**
	 * Visualize graph (The Kamada-Kawai algorithm for node layout)
	 */
	public void KKGraphVisualisation() {
		Layout<Integer, String> layout = new KKLayout<>(graph.getGraph());
		visualize(layout);
	}

	/**
	 * Visualize graph (The Fruchterman-Rheingold algorithm)
	 */
	public void FRGraphVisualisation() {
		Layout<Integer, String> layout = new FRLayout<>(graph.getGraph());
		visualize(layout);
	}

	/**
	 * Visualize graph (A simple force-directed spring-embedder)
	 */
	public void SpringGraphVisualisation() {
		Layout<Integer, String> layout = new SpringLayout<>(graph.getGraph());
		visualize(layout);
	}

	/**
	 * Visualize graph (Meyer's "Self-Organizing Map" layout)
	 */
	public void ISOMLGraphVisualisation() {
		Layout<Integer, String> layout = new ISOMLayout<>(graph.getGraph());
		visualize(layout);
	}
}