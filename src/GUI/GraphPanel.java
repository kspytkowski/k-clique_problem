/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski
 */
package GUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import exceptions.GeneticAlgorithmException;
import exceptions.NoPossibilityToCreateGraphException;
import genetics.AbstractIndividual;
import graph.GraphRepresentation;
import graph.GraphVisualisation;
import graph.LayoutType;

/**
 * 
 * @author wukat
 */
public class GraphPanel extends JPanel {

    
	private VisualizationViewer<Integer, String> vv = null; // visualization viewer of graph
	private Layout<Integer, String> actualGrLayout = null; // displayed layout
	private Graph<Integer, String> graph; // graph
	// private int whichLayout = 3; // actually chosen layout of a graph => zmienione na ponizsze
	private LayoutType layoutType = LayoutType.SPRING; // actually chosen layout of a graph
	private JPanel containing = null; // panel containing graph
	
	/**
	 * Constructor
	 */
	public GraphPanel(JPanel containing) {
		try {
			// graph = new GraphRepresentation(10, 7, 4, true).getGraph(); => mala zmiana, ta linijka == ponizszym szesciom
		    try {
                graph = new GraphRepresentation(10, 7, 4, 4).getGraph();
            } catch (GeneticAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		} catch (NoPossibilityToCreateGraphException ex) {
			Logger.getLogger(KKliqueSolverGUI.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.containing = containing;
		resize();
		actualizeGraph(graph, layoutType, true);
	}

	
	@Override
	public void repaint() {
		super.repaint();
		if (containing != null) {
			resize();
			actualizeGraph(graph, layoutType, false);
		}
	}

	/**
	 * Resizes panel
	 */
	public final void resize() {
		setSize(containing.getSize());
	}

	/**
	 * Function actualizes graph on the screen
	 * 
	 * @param g
	 *            - new graph
	 * @param layoutType - type of layout to visualization of graph
	 */
	public final void actualizeGraph(Graph<Integer, String> g, LayoutType layoutType, boolean change) {
		//if (layoutType != 0) {
			this.layoutType = layoutType;
		//}
		if (graph != g) {
			graph = g;
		}
        removeAll();
		add(actualizeVisualization(null, change), BorderLayout.CENTER);
		resize();
	}

	/**
	 * Creates visualization of a graph with best one from population
	 * 
	 * @param bestOne
	 *            - best subgraph from population
	 * @return visualization
	 */
	private VisualizationViewer<Integer, String> actualizeVisualization(AbstractIndividual bestOne, boolean change) { // Individual => AbstractIndividual
		byte[] arrayWithNoteOfVertex = { 1, 0, 0, 1, 1, 1, 1, 1, 1, 0 };
		// if (bestOne != null) {
		// arrayWithNoteOfVertex = bestOne.getT();
		// }
		if (change) {
			actualGrLayout = GraphVisualisation.getLayout(graph, layoutType);
		}
		vv = new VisualizationViewer<>(actualGrLayout, getSize());
		vv.setBackground(Color.WHITE);
		vv.setGraphMouse(new DefaultModalGraphMouse<String, Number>());
		vv.getRenderContext().setVertexDrawPaintTransformer(new VertexDrawing());
		vv.getRenderContext().setVertexFillPaintTransformer(new VertexPainting(arrayWithNoteOfVertex));
		vv.getRenderContext().setEdgeDrawPaintTransformer(new EdgePainting(arrayWithNoteOfVertex));
		vv.getRenderContext().setEdgeStrokeTransformer(new EdgeThickness(arrayWithNoteOfVertex));

		return vv;
	}

	/**
	 * Paints edges - if it's in a best one, it's blue; otherwise black
	 */
	public class EdgePainting implements Transformer<String, Paint> {

		byte[] arr; // array with notes of vertices

		/**
		 * Constructor
		 * 
		 * @param arr
		 *            - array with notes of vertices
		 */
		public EdgePainting(byte[] arr) {
			this.arr = arr;
		}

		@Override
		public Paint transform(String e) {
			if (arr != null) {
				boolean flag = false;
				for (int i : graph.getEndpoints(e)) {
					if (arr[i - 1] == 0) {
						flag = true;
					}
				}
				if (!flag) {
					return Color.BLUE;
				}
			}
			return Color.BLACK;
		}
	}

	/**
	 * Sets edge thickness, thick - in best one
	 */
	public class EdgeThickness implements Transformer<String, Stroke> {

		byte[] arr; // array with notes of vertices
		protected final Stroke THIN = new BasicStroke(1); // thickness
		protected final Stroke THICK = new BasicStroke(2);

		/**
		 * Constructor
		 * 
		 * @param arr
		 *            - array with notes of vertices
		 */
		public EdgeThickness(byte[] arr) {
			this.arr = arr;
		}

		@Override
		public Stroke transform(String e) {
			if (arr != null) {
				boolean flag = false;
				for (int i : graph.getEndpoints(e)) {
					if (arr[i - 1] == 0) {
						flag = true;
					}
				}
				if (!flag) {
					return THICK;
				}
			}
			return THIN;
		}
	}

	/**
	 * Draw black border of vertex
	 */
	public class VertexDrawing implements Transformer<Integer, Paint> {

		@Override
		public Paint transform(Integer v) {
			return Color.BLACK;
		}

	}

	/**
	 * Sets color of vertex; yellow - in best one; red - otherwise
	 */
	public class VertexPainting implements Transformer<Integer, Paint> {

		byte[] arr; // array with notes of vertices

		/**
		 * Constructor
		 * 
		 * @param arr
		 *            - array with notes of vertices
		 */
		public VertexPainting(byte[] arr) {
			this.arr = arr;
		}

		@Override
		public Paint transform(Integer i) {
			if (arr != null) {
				if (1 == arr[i - 1]) {
					return Color.YELLOW;
				}
			}
			return Color.RED;
		}
	}
}
