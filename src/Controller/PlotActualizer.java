/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
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
                pauseActualizer();
                chartPanelInGUI.repaint();
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
