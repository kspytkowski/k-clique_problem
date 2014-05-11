package Controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class PlotActualizer extends Thread {

    private boolean paused = true; // flag
    private final JPanel chartPanelInGUI; // panel with chart to actualize

    /**
     * Constructor.
     *
     * @param chartPanelInGUI - panel with chart to actualize
     */
    public PlotActualizer(JPanel chartPanelInGUI) {
        this.chartPanelInGUI = chartPanelInGUI;
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
                chartPanelInGUI.repaint();
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
