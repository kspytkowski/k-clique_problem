package Controller;

import GUI.GraphPanelKRZYSIEK;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

public class GraphVisualizationActualizer extends Thread {

    private boolean paused = true; // flag
    private final ApplicationController controller; // aplication controller
    private final GraphPanelKRZYSIEK graphPanelKRZYSIEK; // panel with graph to actualize
    private final JButton stopButton; // button stopping application
    private final JButton startButton; // button starting application

    /**
     * Constructor
     *
     * @param controller - aplication controller
     * @param graphPanelKRZYSIEK - panel with graph to actualize
     */
    public GraphVisualizationActualizer(ApplicationController controller, GraphPanelKRZYSIEK graphPanelKRZYSIEK, JButton stopButton, JButton startButton) {
        this.controller = controller;
        this.graphPanelKRZYSIEK = graphPanelKRZYSIEK;
        this.stopButton = stopButton;
        this.startButton = startButton;
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
                System.out.println("tam" + controller.getActualBestindividual());
                graphPanelKRZYSIEK.actualizeVisualization(controller.getActualBestindividual());
                graphPanelKRZYSIEK.repaint();
                if (controller.isFinished()) {
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                }
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
