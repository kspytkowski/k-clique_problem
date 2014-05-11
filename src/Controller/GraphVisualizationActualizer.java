package Controller;

import GUI.GraphPanelKRZYSIEK;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphVisualizationActualizer extends Thread {

    private boolean paused = true; // flag
    private final ApplicationController controller; // aplication controller
    private final GraphPanelKRZYSIEK graphPanelKRZYSIEK; // panel with graph to actualize

    /**
     * Constructor
     *
     * @param controller - aplication controller
     * @param graphPanelKRZYSIEK - panel with graph to actualize
     */
    public GraphVisualizationActualizer(ApplicationController controller, GraphPanelKRZYSIEK graphPanelKRZYSIEK) {
        this.controller = controller;
        this.graphPanelKRZYSIEK = graphPanelKRZYSIEK;
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
                graphPanelKRZYSIEK.actualizeVisualization(controller.getActualBestindividual());
                graphPanelKRZYSIEK.repaint();
                pauseActualizer();
            }
            synchronized (this) {
                while (paused) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GraphVisualizationActualizer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
