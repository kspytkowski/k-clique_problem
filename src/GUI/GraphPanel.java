/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski
 */
package GUI;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import exceptions.NoPossibilityToCreateGraphException;
import genetics.Individual;
import graph.GraphVisualisation;
import graph.MyGraph;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author wukat
 */
public class GraphPanel extends JPanel {

    private VisualizationViewer<Integer, String> vv = null; // visualization of a graph
    private Layout<Integer, String> actualGrLayout = null;
    private Graph<Integer, String> graph; // graph
    private int whichLayout = 3; // actually choosen layout of a graph
    private JPanel containing = null;

    /**
     * Constructor
     */
    public GraphPanel(JPanel containing) {
        try {
            graph = MyGraph.createGraph(10, 7);
        } catch (NoPossibilityToCreateGraphException ex) {
            Logger.getLogger(KKliqueSolverGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.containing = containing;
        resize();
        actualizeGraph(graph, whichLayout, true);
    }

    @Override
    public void repaint() {
        super.repaint();
        if (containing != null) {
            resize();
            actualizeGraph(graph, whichLayout, false);
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
     * @param g - new graph
     * @param whichLayout
     */
    public final void actualizeGraph(Graph<Integer, String> g, int whichLayout, boolean change) {
        if (whichLayout != 0) {
            this.whichLayout = whichLayout;
        }
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
     * @param bestOne - best subgraph from population
     * @return visualization
     */
    private VisualizationViewer<Integer, String> actualizeVisualization(Individual bestOne, boolean change) {
        byte[] arrayWithNoteOfVertex = {1, 0, 0, 1, 1, 1, 1, 1, 1, 0};
//        if (bestOne != null) {
//            arrayWithNoteOfVertex = bestOne.getT();
//        }
        if (change) {
            actualGrLayout = GraphVisualisation.getLayout(graph, whichLayout);
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
         * @param arr - array with notes of vertices
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
         * @param arr - array with notes of vertices
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
         * @param arr - array with notes of vertices
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
