package graph;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;

/**
 * @author Krzysztof Spytkowski
 * @date 24 mar 2014
 */
public class GraphVisualisation {
	
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
//	private void visualize(Layout<Integer, String> l) {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		VisualizationViewer<Integer, String> vv = new VisualizationViewer<>(l, new Dimension(300, 200));
//                vv.setGraphMouse(new DefaultModalGraphMouse<String, Number>());
//		getContentPane().add(vv);
//		pack();
//		setSize(new Dimension(400, 300));
//		setVisible(true);
//	};

	/**
	 * Visualize graph (A simple layout places vertices randomly on a circle)
         * @return layout
	 */
	public Layout<Integer, String> CircleGraphVisualisation() {
		Layout<Integer, String> layout = new CircleLayout<>(graph.getGraph());
                return layout;
	}

	/**
	 * Visualize graph (The Kamada-Kawai algorithm for node layout)
         * @return layout
	 */
	public Layout<Integer, String> KKGraphVisualisation() {
		Layout<Integer, String> layout = new KKLayout<>(graph.getGraph());
		return layout;
	}

	/**
	 * Visualize graph (The Fruchterman-Rheingold algorithm)
         * @return layout
	 */
	public Layout<Integer, String> FRGraphVisualisation() {
		Layout<Integer, String> layout = new FRLayout<>(graph.getGraph());
		return layout;
	}

	/**
	 * Visualize graph (A simple force-directed spring-embedder)
         * @return layout
	 */
	public Layout<Integer, String> SpringGraphVisualisation() {
		Layout<Integer, String> layout = new SpringLayout<>(graph.getGraph());
		return layout;
	}

	/**
	 * Visualize graph (Meyer's "Self-Organizing Map" layout)
         * @return layout
	 */
	public Layout<Integer, String> ISOMLGraphVisualisation() {
		Layout<Integer, String> layout = new ISOMLayout<>(graph.getGraph());
		return layout;
	}
}