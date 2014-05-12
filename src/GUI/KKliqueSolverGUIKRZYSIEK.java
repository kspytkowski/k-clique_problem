package GUI;

import Controller.GraphVisualizationActualizer;
import Controller.ApplicationController;
import Controller.PlotActualizer;
import exceptions.NoPossibilityToCreateGraphException;
import exceptions.ProblemWithReadingGraphFromFileException;
import genetics.CrossingOverType;
import genetics.IndividualType;
import genetics.SelectionType;
import graph.GraphRepresentation;
import graph.LayoutType;
import java.awt.MenuItem;
import java.awt.PopupMenu;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author wukat & others
 */
public class KKliqueSolverGUIKRZYSIEK extends javax.swing.JFrame {

    ApplicationController controller = new ApplicationController();
    GraphVisualizationActualizer graphActualizer;
    PlotActualizer chartActualizer;

    /**
     * Creates new form KKliqueSolverGUI
     */
    public KKliqueSolverGUIKRZYSIEK() {
        initComponents();
        initChart();
        graphActualizer = new GraphVisualizationActualizer(controller, graphPanelKRZYSIEK);
        chartActualizer = new PlotActualizer(chartPanelInGUI);
        controller.setActualizers(graphActualizer, chartActualizer);
        graphActualizer.start();
        chartActualizer.start();
        controller.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        individualsEncodingCheckBoxGroup = new javax.swing.ButtonGroup();
        buttonsPanel = new javax.swing.JPanel();
        crossingOverPanel = new javax.swing.JPanel();
        crossingOverProbabilityLabel = new javax.swing.JLabel();
        crossingOverTypeLabel = new javax.swing.JLabel();
        crossingOverTypeComboBox = new javax.swing.JComboBox();
        crossingOverProbabilitySpinner = new javax.swing.JSpinner(new SpinnerNumberModel(0.6, 0.00, 1.0, 0.01));
        mutationPanel = new javax.swing.JPanel();
        mutationProbabilityLabel = new javax.swing.JLabel();
        mutationProbabilitySpinner = new javax.swing.JSpinner(new SpinnerNumberModel(0.05, 0.00, 1.0, 0.01));
        selectionPanel = new javax.swing.JPanel();
        selectionTypeComboBox = new javax.swing.JComboBox();
        selectionTypeLabel = new javax.swing.JLabel();
        populationPanel = new javax.swing.JPanel();
        amountOfIndividualsLabel = new javax.swing.JLabel();
        amountOfIndividualsSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(20, 10, 100, 1));
        numberOfGenerationsLabel = new javax.swing.JLabel();
        numberOfGenerationsSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(500, 100, 2000, 1));
        controlPanel = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        searchedKCliqueSizeSlider = new javax.swing.JSlider(0,0,0);
        searchedKCliqueSizeLabel = new javax.swing.JLabel();
        individualsEncodingPanel = new javax.swing.JPanel();
        groupCodingCheckBox = new javax.swing.JCheckBox();
        numberOfGroupsLabel = new javax.swing.JLabel();
        numberOfGroupsSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(4, 4, 16, 1));
        binaryCodingCheckBox = new javax.swing.JCheckBox();
        graphPanel = new javax.swing.JPanel();
        chartPanelInGUI = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        loadGraphMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        individualsEncodingCheckBoxGroup.add(binaryCodingCheckBox);

        individualsEncodingCheckBoxGroup.add(groupCodingCheckBox);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("K-Klique Problem Solver23");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusTraversalPolicyProvider(true);
        setMaximumSize(new java.awt.Dimension(1366, 768));
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setPreferredSize(new java.awt.Dimension(1366, 768));

        buttonsPanel.setBorder(null);
        buttonsPanel.setMaximumSize(new java.awt.Dimension(612, 322));
        buttonsPanel.setPreferredSize(new java.awt.Dimension(612, 366));

        crossingOverPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Crossing-over"));

        crossingOverProbabilityLabel.setText("Probability");

        crossingOverTypeLabel.setText("Type");

        crossingOverTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "one point with two children", "one point with one child", "uniform", "weighted uniform", "two points with two children", "two points with one child" }));

        crossingOverProbabilitySpinner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout crossingOverPanelLayout = new javax.swing.GroupLayout(crossingOverPanel);
        crossingOverPanel.setLayout(crossingOverPanelLayout);
        crossingOverPanelLayout.setHorizontalGroup(
            crossingOverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crossingOverPanelLayout.createSequentialGroup()
                .addComponent(crossingOverProbabilityLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crossingOverProbabilitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(crossingOverTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crossingOverTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        crossingOverPanelLayout.setVerticalGroup(
            crossingOverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crossingOverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(crossingOverProbabilityLabel)
                .addComponent(crossingOverProbabilitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(crossingOverTypeLabel)
                .addComponent(crossingOverTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mutationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Mutation"));

        mutationProbabilityLabel.setText("Probability");

        javax.swing.GroupLayout mutationPanelLayout = new javax.swing.GroupLayout(mutationPanel);
        mutationPanel.setLayout(mutationPanelLayout);
        mutationPanelLayout.setHorizontalGroup(
            mutationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mutationPanelLayout.createSequentialGroup()
                .addComponent(mutationProbabilityLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mutationProbabilitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        mutationPanelLayout.setVerticalGroup(
            mutationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mutationPanelLayout.createSequentialGroup()
                .addGroup(mutationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mutationProbabilityLabel)
                    .addComponent(mutationProbabilitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        selectionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Selection"));

        selectionTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "tournament", "roulette", "linear ranking" }));

        selectionTypeLabel.setText("Type");

        javax.swing.GroupLayout selectionPanelLayout = new javax.swing.GroupLayout(selectionPanel);
        selectionPanel.setLayout(selectionPanelLayout);
        selectionPanelLayout.setHorizontalGroup(
            selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, selectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectionTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(selectionTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        selectionPanelLayout.setVerticalGroup(
            selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(selectionTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(selectionTypeLabel))
        );

        populationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Population"));

        amountOfIndividualsLabel.setText("Amount of individuals");

        numberOfGenerationsLabel.setText("Number of generations");

        javax.swing.GroupLayout populationPanelLayout = new javax.swing.GroupLayout(populationPanel);
        populationPanel.setLayout(populationPanelLayout);
        populationPanelLayout.setHorizontalGroup(
            populationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(populationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(amountOfIndividualsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(amountOfIndividualsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(numberOfGenerationsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberOfGenerationsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        populationPanelLayout.setVerticalGroup(
            populationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(populationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(amountOfIndividualsLabel)
                .addComponent(amountOfIndividualsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(numberOfGenerationsLabel)
                .addComponent(numberOfGenerationsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        controlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Control"));

        startButton.setText("START");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("STOP");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        //searchedKCliqueSizeSlider.setMajorTickSpacing(1);

        //searchedKCliqueSizeSlider.setMinorTickSpacing(1);
        searchedKCliqueSizeSlider.setPaintLabels(true);
        searchedKCliqueSizeSlider.setPaintTicks(true);
        searchedKCliqueSizeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                searchedKCliqueSizeSliderStateChanged(evt);
            }
        });

        searchedKCliqueSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchedKCliqueSizeLabel.setText("Searched K-Clique size");
        searchedKCliqueSizeLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchedKCliqueSizeSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchedKCliqueSizeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(startButton)
                        .addComponent(stopButton))
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(searchedKCliqueSizeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchedKCliqueSizeSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)))
                .addContainerGap())
        );

        individualsEncodingPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Individual's encoding"));

        groupCodingCheckBox.setText("group");
        groupCodingCheckBox.setSelected(false);
        groupCodingCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupCodingCheckBoxActionPerformed(evt);
            }
        });

        numberOfGroupsLabel.setText("Number of groups");

        numberOfGroupsSpinner.setEnabled(false);

        binaryCodingCheckBox.setText("binary");
        binaryCodingCheckBox.setSelected(true);
        binaryCodingCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                binaryCodingCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout individualsEncodingPanelLayout = new javax.swing.GroupLayout(individualsEncodingPanel);
        individualsEncodingPanel.setLayout(individualsEncodingPanelLayout);
        individualsEncodingPanelLayout.setHorizontalGroup(
            individualsEncodingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(individualsEncodingPanelLayout.createSequentialGroup()
                .addComponent(binaryCodingCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(groupCodingCheckBox)
                .addGap(18, 18, 18)
                .addComponent(numberOfGroupsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberOfGroupsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        individualsEncodingPanelLayout.setVerticalGroup(
            individualsEncodingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(individualsEncodingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(groupCodingCheckBox)
                .addComponent(numberOfGroupsLabel)
                .addComponent(numberOfGroupsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(binaryCodingCheckBox))
        );

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(populationPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, buttonsPanelLayout.createSequentialGroup()
                        .addComponent(individualsEncodingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(buttonsPanelLayout.createSequentialGroup()
                        .addComponent(crossingOverPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mutationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(populationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(individualsEncodingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(crossingOverPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mutationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        graphPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(254, 254, 254)));
        graphPanel.setMaximumSize(new java.awt.Dimension(400, 1400));
        graphPanel.setMinimumSize(new java.awt.Dimension(100, 100));
        graphPanel.setLayout(new java.awt.BorderLayout());

        chartPanelInGUI.setBackground(new java.awt.Color(254, 254, 254));
        chartPanelInGUI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(254, 254, 254)));

        javax.swing.GroupLayout chartPanelInGUILayout = new javax.swing.GroupLayout(chartPanelInGUI);
        chartPanelInGUI.setLayout(chartPanelInGUILayout);
        chartPanelInGUILayout.setHorizontalGroup(
            chartPanelInGUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chartPanelInGUILayout.setVerticalGroup(
            chartPanelInGUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );

        menuBar.setToolTipText("K-Klique Problem Solver");

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        fileMenu.add(saveAsMenuItem);

        loadGraphMenuItem.setText("Load graph");
        loadGraphMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadGraphMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadGraphMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 599, Short.MAX_VALUE)
                    .addComponent(chartPanelInGUI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chartPanelInGUI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        graphPanelKRZYSIEK = new GraphPanelKRZYSIEK();
        graphPanel.add(graphPanelKRZYSIEK);

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void binaryCodingCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_binaryCodingCheckBoxActionPerformed
        numberOfGroupsSpinner.setEnabled(false);
    }//GEN-LAST:event_binaryCodingCheckBoxActionPerformed

    private void groupCodingCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupCodingCheckBoxActionPerformed
        numberOfGroupsSpinner.setEnabled(true);
    }//GEN-LAST:event_groupCodingCheckBoxActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        actualizeController();
        if (controller.getGraphRepresentation() != null) {
            controller.getPlot().clearAllSeries();
            controller.resumeSolving();
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        controller.stopSolving();
    }//GEN-LAST:event_stopButtonActionPerformed

    private void searchedKCliqueSizeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_searchedKCliqueSizeSliderStateChanged
        controller.getGraphRepresentation().setsearchedKCliqueSize(searchedKCliqueSizeSlider.getValue());
    }//GEN-LAST:event_searchedKCliqueSizeSliderStateChanged

    /**
     * Adds and shows empty chart in GUI.
     */
    private void initChart() {
        ChartPanel myChartPanel = controller.getPlot().getChartPanel();
        myChartPanel.setSize(chartPanelInGUI.getWidth(), chartPanelInGUI.getHeight());
        chartPanelInGUI.add(myChartPanel);
    }

    /**
     * Actualizes controller with data collected from GUI.
     */
    private void actualizeController() {
        controller.setNumberOfIndividuals((int) amountOfIndividualsSpinner.getValue());
        controller.setCrossingOverProbability((double) crossingOverProbabilitySpinner.getValue());
        controller.setHowToCross(CrossingOverType.getAtIndex(crossingOverTypeComboBox.getSelectedIndex()));
        controller.setHowToSelect(SelectionType.getAtIndex(selectionTypeComboBox.getSelectedIndex()));
        controller.setIndividualEncoding((groupCodingCheckBox.isSelected()) ? IndividualType.GROUPCODEDINDIVIDUAL : IndividualType.BINARYCODEDINDIVIDUAL);
        if (groupCodingCheckBox.isSelected()) {
            controller.setNumberOfGroupsInGroupEncoding((int) numberOfGroupsSpinner.getValue());
        }
        controller.setMutationProbability((double) mutationProbabilitySpinner.getValue());
        controller.setNumberOfIterations((int) numberOfGenerationsSpinner.getValue());
    }

    private void loadGraphMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_loadGraphMenuItemActionPerformed
        JFileChooser databaseFileChooser = new JFileChooser();
        int option = databaseFileChooser.showDialog(this, "Import");
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                controller.setGraphRepresentation(new GraphRepresentation(databaseFileChooser.getSelectedFile().getAbsolutePath()));
                searchedKCliqueSizeSlider.setMinimum(1);
                searchedKCliqueSizeSlider.setMaximum(controller.getGraphRepresentation().getVertexCount());
                searchedKCliqueSizeSlider.setMajorTickSpacing(controller.getGraphRepresentation().getVertexCount() / 10);
                searchedKCliqueSizeSlider.setPaintTicks(true);
                searchedKCliqueSizeSlider.setPaintLabels(true);
                graphPanelKRZYSIEK.setLayoutType(LayoutType.CIRCLE);
                graphPanelKRZYSIEK.displayNewGraph(controller.getGraphRepresentation().getGraph());
            } catch (NoPossibilityToCreateGraphException | ProblemWithReadingGraphFromFileException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }// GEN-LAST:event_loadGraphMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KKliqueSolverGUIKRZYSIEK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KKliqueSolverGUIKRZYSIEK().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JLabel amountOfIndividualsLabel;
    private javax.swing.JSpinner amountOfIndividualsSpinner;
    private javax.swing.JCheckBox binaryCodingCheckBox;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JPanel chartPanelInGUI;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel crossingOverPanel;
    private javax.swing.JLabel crossingOverProbabilityLabel;
    private javax.swing.JSpinner crossingOverProbabilitySpinner;
    private javax.swing.JComboBox crossingOverTypeComboBox;
    private javax.swing.JLabel crossingOverTypeLabel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private GraphPanelKRZYSIEK graphPanelKRZYSIEK;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JCheckBox groupCodingCheckBox;
    private javax.swing.JMenu helpMenu;
    private javax.swing.ButtonGroup individualsEncodingCheckBoxGroup;
    private javax.swing.JPanel individualsEncodingPanel;
    private javax.swing.JMenuItem loadGraphMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel mutationPanel;
    private javax.swing.JLabel mutationProbabilityLabel;
    private javax.swing.JSpinner mutationProbabilitySpinner;
    private javax.swing.JLabel numberOfGenerationsLabel;
    private javax.swing.JSpinner numberOfGenerationsSpinner;
    private javax.swing.JLabel numberOfGroupsLabel;
    private javax.swing.JSpinner numberOfGroupsSpinner;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JPanel populationPanel;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JLabel searchedKCliqueSizeLabel;
    private javax.swing.JSlider searchedKCliqueSizeSlider;
    private javax.swing.JPanel selectionPanel;
    private javax.swing.JComboBox selectionTypeComboBox;
    private javax.swing.JLabel selectionTypeLabel;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
