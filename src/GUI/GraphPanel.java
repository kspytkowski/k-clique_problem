/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski
 */

package GUI;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import exceptions.NoPossibilityToCreateGraphException;
import graph.GraphVisualisation;
import graph.MyGraph;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author wukat
 */
public class GraphPanel extends JPanel {
    private VisualizationViewer<Integer, String> vv;
    private Graph<Integer, String> graph;
    
    public GraphPanel() {
        try {
            graph = MyGraph.createGraph(10, 10);
        } catch (NoPossibilityToCreateGraphException ex) {
            Logger.getLogger(KKliqueSolverGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualizeGraph(graph, 3);
    }
    
    public final void actualizeGraph(Graph<Integer, String> g, int whichLayout) {
        vv = new VisualizationViewer<>(GraphVisualisation.getLayout(g, whichLayout), new Dimension(1000, 550));
        vv.setGraphMouse(new DefaultModalGraphMouse<String, Number>());
        add(vv, BorderLayout.CENTER);
    }
}
