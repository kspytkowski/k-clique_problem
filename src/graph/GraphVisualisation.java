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
 * @date 24th April 2014
 */
public final class GraphVisualisation {

    /**
     * Creates specified layout to given graph
     * 
     * @param g
     *            - graph
     * @param layoutType
     *            - visualization of graph method
     * @return layout
     */
    public static Layout<Integer, String> getLayout(Graph<Integer, String> g, LayoutType layoutType) {
        Layout<Integer, String> layout;
        switch (layoutType) {
        case CIRCLE:
            layout = new CircleLayout<>(g);
            break;
        case KAMADAKAWAI:
            layout = new KKLayout<>(g);
            break;
        case FRUCHTERMANRHEINGOLD:
            layout = new FRLayout<>(g);
            break;
        case SPRING:
            layout = new SpringLayout<>(g);
            break;
        case MEYER:
            layout = new ISOMLayout<>(g);
            break;
        default:
            layout = new SpringLayout<>(g);
            break;
        }
        return layout;
    }
}
