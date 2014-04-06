package graph;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.Graph;

/**
 * @author Krzysztof Spytkowski
 * @modified wukat
 * @date 24 mar 2014
 */
public final class GraphVisualisation {

	/**
	 * 
	 * @param g
	 *            - graph
	 * @param which
	 *            - visualization method
	 * @return layout
	 */
	public static Layout<Integer, String> getLayout(Graph<Integer, String> g, int which) {
		Layout<Integer, String> layout;
		switch (which) {
		case 1:
			layout = new CircleLayout<>(g);
			break;
		case 2:
			layout = new KKLayout<>(g);
			break;
		case 3:
			layout = new FRLayout<>(g);
			break;
		case 4:
			layout = new SpringLayout<>(g);
			break;
		case 5:
			layout = new ISOMLayout<>(g);
			break;
		default:
			layout = new SpringLayout<>(g);
			break;
		}
		return layout;
	}

	/**
	 * Visualize graph
	 * 
	 * @param l
	 *            - layout
	 */
	// private void visualize(Layout<Integer, String> l) {
	// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// VisualizationViewer<Integer, String> vv = new VisualizationViewer<>(l, new Dimension(300, 200));
	// vv.setGraphMouse(new DefaultModalGraphMouse<String, Number>());
	// getContentPane().add(vv);
	// pack();
	// setSize(new Dimension(400, 300));
	// setVisible(true);
	// };
	// /**
	// * Visualize graph (A simple layout places vertices randomly on a circle)
	// *
	// * @return layout
	// */
	// private Layout<Integer, String> CircleGraphVisualisation() {
	// Layout<Integer, String> layout = new CircleLayout<>(graph.getGraph());
	// return layout;
	// }
	//
	// /**
	// * Visualize graph (The Kamada-Kawai algorithm for node layout)
	// *
	// * @return layout
	// */
	// private Layout<Integer, String> KKGraphVisualisation() {
	// Layout<Integer, String> layout = new KKLayout<>(graph.getGraph());
	// return layout;
	// }
	//
	// /**
	// * Visualize graph (The Fruchterman-Rheingold algorithm)
	// *
	// * @return layout
	// */
	// private Layout<Integer, String> FRGraphVisualisation() {
	// Layout<Integer, String> layout = new FRLayout<>(graph.getGraph());
	// return layout;
	// }
	//
	// /**
	// * Visualize graph (A simple force-directed spring-embedder)
	// *
	// * @return layout
	// */
	// private Layout<Integer, String> SpringGraphVisualisation() {
	// Layout<Integer, String> layout = new SpringLayout<>(graph.getGraph());
	// return layout;
	// }
	//
	// /**
	// * Visualize graph (Meyer's "Self-Organizing Map" layout)
	// *
	// * @return layout
	// */
	// private Layout<Integer, String> ISOMLGraphVisualisation() {
	// Layout<Integer, String> layout = new ISOMLayout<>(graph.getGraph());
	// return layout;
	// }
}
