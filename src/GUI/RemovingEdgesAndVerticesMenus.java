/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
package GUI;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import graph.Edge;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.JPopupMenu;

public class RemovingEdgesAndVerticesMenus<V, E> extends AbstractPopupGraphMousePlugin {

    private final EdgeMenu edgePopup;
    private final VertexMenu vertexPopup; // menus
    private final VisualizationViewer<V, E> vv; // visualization

    /**
     * Constructor.
     *
     * @param edgePopupMenu - menu for edges
     * @param vertexPopupMenu - menu for vertices
     * @param vv - actual visualization
     */
    public RemovingEdgesAndVerticesMenus(VisualizationViewer<V, E> vv) {
        this.setModifiers(MouseEvent.BUTTON3_MASK);
        this.edgePopup = new EdgeMenu();
        edgePopup.setVv(vv);
        this.vertexPopup = new VertexMenu();
        vertexPopup.setVv(vv);
        this.vv = vv;
    }

    @Override
    protected void handlePopup(MouseEvent evt) {
        Point2D clickedPoint = evt.getPoint();

        GraphElementAccessor<V, E> accessor = vv.getPickSupport();
        if (accessor != null) {
            V vertex = accessor.getVertex(vv.getGraphLayout(), clickedPoint.getX(), clickedPoint.getY());
            if (vertex != null && vertexPopup != null) {
                vertexPopup.actualize(vertex);
                vertexPopup.show(vv, evt.getX(), evt.getY());
            } else {
                E edge = accessor.getEdge(vv.getGraphLayout(), clickedPoint.getX(), clickedPoint.getY());
                if (edge != null && edgePopup != null) {
                    edgePopup.actualize(edge);
                    edgePopup.show(vv, evt.getX(), evt.getY());
                }
            }
        }
    }

    /**
     * Class implementing menu for edges.
     */
    private class EdgeMenu extends JPopupMenu {

        /**
         * Constructor.
         */
        public EdgeMenu() {
            super("Edge Menu");
            this.add(new DeleteEdgeOption<Edge>());
        }

        /**
         * Actualizes component which removes edge with this edge
         *
         * @param vertex
         */
        public void actualize(E edge) {
            ((DeleteEdgeOption) this.getComponent(0)).setEdge(edge);
        }

        /**
         * Setter - sets visualization view in component.
         *
         * @param vv - visualization
         */
        public void setVv(VisualizationViewer vv) {
            ((DeleteEdgeOption) this.getComponent(0)).setVv(vv);
        }

    }

    /**
     * Class implementing menu for vertices.
     */
    private class VertexMenu extends JPopupMenu {

        /**
         * Constructor.
         */
        public VertexMenu() {
            super("Vertex Menu");
            this.add(new DeleteVertexOption<Integer>());
        }

        /**
         * Actualizes component which removes vertex with this vertex
         *
         * @param vertex
         */
        public void actualize(V vertex) {
            ((DeleteVertexOption) this.getComponent(0)).setVertex(vertex);
        }

        /**
         * Setter - sets visualization view in component.
         *
         * @param vv - visualization
         */
        public void setVv(VisualizationViewer vv) {
            ((DeleteVertexOption) this.getComponent(0)).setVv(vv);
        }
    }
}
