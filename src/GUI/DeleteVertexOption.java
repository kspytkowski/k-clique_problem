/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
package GUI;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

public class DeleteVertexOption<V> extends JMenuItem {

    private V vertex; // vertex
    private VisualizationViewer vv; // visualization

    /**
     * Constructor with action listener.
     */
    public DeleteVertexOption() {
        super("Delete vertex");
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vv.getGraphLayout().getGraph().removeVertex(vertex);
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
     * @param vertex - vertex
     */
    public void setVertex(V vertex) {
        this.vertex = vertex;
    }

}
