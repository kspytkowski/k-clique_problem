/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
package Controller;

import GUI.GraphPanel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

public class GraphVisualizationAndButtonsStateActualizer extends Thread {

    private boolean paused = true; // flag
    private final ApplicationController controller; // aplication controller
    private final GraphPanel graphPanel; // panel with graph to actualize
    private final JButton stopButton; // button stopping application
    private final JButton startButton; // button starting application
    private final JTabbedPane tabs; // needed to enable/disable one of tab when 
    //its state should be chenged

    /**
     * Constructor
     *
     * @param controller - aplication controller
     * @param graphPanel - panel with graph to actualize
     */
    public GraphVisualizationAndButtonsStateActualizer(ApplicationController controller, GraphPanel graphPanel, JButton stopButton, JButton startButton, JTabbedPane tabs) {
        this.controller = controller;
        this.graphPanel = graphPanel;
        this.stopButton = stopButton;
        this.startButton = startButton;
        this.tabs = tabs;
    }

    /**
     * Pauses thread.
     */
    public void pauseActualizer() {
        paused = true;
    }

    /**
     * Resumes thread.
     */
    public synchronized void actualize() {
        paused = false;
        notify();
    }

    @Override
    public void run() {
        while (true) {
            if (!paused) {
                pauseActualizer();
                graphPanel.actualizeVisualization(controller.getActualBestindividual(), false);
                graphPanel.repaint();
                if (controller.isFinished()) {
                    stopButton.setEnabled(false);
                    startButton.setEnabled(true);
                    tabs.setEnabledAt((tabs.getSelectedIndex() + 1) % 3, true);
                    tabs.setEnabledAt((tabs.getSelectedIndex() + 2) % 3, true);
                }
            }
            synchronized (this) {
                while (paused) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GraphVisualizationAndButtonsStateActualizer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
