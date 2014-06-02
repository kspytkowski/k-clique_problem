/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
package GUI;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

public class DeleteEdgeOption<E> extends JMenuItem {

    private E edge; // edge
    private VisualizationViewer vv; // visualization

    /**
     * Constructor with action listener.
     */
    public DeleteEdgeOption() {
        super("Delete edge");
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vv.getGraphLayout().getGraph().removeEdge(edge);
                vv.repaint();
            }
        });
    }

    /**
     * Setter
     *
     * @param vv - visualization
     */
    public void setVv(VisualizationViewer vv) {
        this.vv = vv;
    }

    /**
     * Setter
     * 
     * @param edge - edge
     */
    public void setEdge(E edge) {
        this.edge = edge;
    }
}
