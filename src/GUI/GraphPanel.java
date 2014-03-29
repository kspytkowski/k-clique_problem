/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski
 */
package GUI;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import exceptions.NoPossibilityToCreateGraphException;
import genetics.Individual;
import graph.GraphVisualisation;
import graph.MyGraph;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author wukat
 */
public class GraphPanel extends JPanel {

    private VisualizationViewer<Integer, String> vv;
    private Graph<Integer, String> graph;
    private int whichLayout = 3;

    public GraphPanel(Dimension dim) {
        try {
            graph = MyGraph.createGraph(10, 10);
        } catch (NoPossibilityToCreateGraphException ex) {
            Logger.getLogger(KKliqueSolverGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        setSize(dim);
        actualizeGraph(graph, whichLayout);
    }

    /**
     * Function actualizes graph on the screen
     *
     * @param g - new graph
     * @param whichLayout
     */
    public final void actualizeGraph(Graph<Integer, String> g, int whichLayout) {
        graph = g;
        if (whichLayout != 0) {
            this.whichLayout = whichLayout;
        }
        removeAll();
        add(actualizeVisualization(null), BorderLayout.CENTER);
    }

    private VisualizationViewer<Integer, String> actualizeVisualization(Individual population) {
        vv = new VisualizationViewer<>(GraphVisualisation.getLayout(graph, whichLayout), getSize());
        vv.setGraphMouse(new DefaultModalGraphMouse<String, Number>());
        vv.getRenderContext().setVertexDrawPaintTransformer(new MyVertexDrawPaintFunction<Integer>());
        vv.getRenderContext().setVertexFillPaintTransformer(new MyVertexFillPaintFunction<Integer>());
       // vv.getRenderContext().setEdgeDrawPaintTransformer(new MyEdgePaintFunction());
       // vv.getRenderContext().setEdgeStrokeTransformer(new MyEdgeStrokeFunction());
        for (Integer vert : GraphVisualisation.getLayout(graph, whichLayout).getGraph().getVertices()) {
            System.out.println(vert);
        }
        for (String vert : graph.getEdges()) {
            System.out.println(vert);

        }
        return vv;
    }

//    public class MyEdgePaintFunction implements Transformer<Number, Paint> {
//
//        @Override
//        public Paint transform(Number e) {
//            if (mPred == null || mPred.size() == 0) {
//                return Color.BLACK;
//            }
//            if (isBlessed(e)) {
//                return new Color(0.0f, 0.0f, 1.0f, 0.5f);
//            } else {
//                return Color.LIGHT_GRAY;
//            }
//        }
//    }
//
//    public class MyEdgeStrokeFunction implements Transformer<Number, Stroke> {
//
//        protected final Stroke THIN = new BasicStroke(1);
//        protected final Stroke THICK = new BasicStroke(1);
//
//        @Override
//        public Stroke transform(Number e) {
//            if (mPred == null || mPred.size() == 0) {
//                return THIN;
//            }
//            if (isBlessed(e)) {
//                return THICK;
//            } else {
//                return THIN;
//            }
//        }
//
//    }

    public class MyVertexDrawPaintFunction<V> implements Transformer<V, Paint> {

        @Override
        public Paint transform(V v) {
            return Color.black;
        }

    }

    public class MyVertexFillPaintFunction<Integer> implements Transformer<Integer, Paint> {

//                public Paint transform(V v) {
//            if (v == mFrom) {
//                return Color.BLUE;
//            }
//            if (v == mTo) {
//                return Color.BLUE;
//            }
//            if (mPred == null) {
//                return Color.LIGHT_GRAY;
//            } else {
//                if (mPred.contains(v)) {
//                    return Color.RED;
//                } else {
//                    return Color.LIGHT_GRAY;
//                }
//            }
//        }

        @Override
        public Paint transform(Integer i) {
            if (i.equals(1)) {
                return Color.BLUE;
            }
            else return Color.RED;
        }
    }

}
