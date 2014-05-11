/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski
 */
package Controller;

import GUI.Chart;
import exceptions.GeneticAlgorithmException;
import genetics.AbstractIndividual;
import genetics.CrossingOverType;
import genetics.IndividualType;
import genetics.Population;
import genetics.SelectionType;
import graph.GraphRepresentation;
import static java.lang.Math.ceil;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationController extends Thread {

    /**
     * Data collected from GUI, needed to run genetic algorithm.
     */
    private GraphRepresentation graphRepresentation = null; // graphRepresentation on which algorithm will run
    // can be drawn, loaded from file or generated
    private int numberOfIndividuals = 20; // number of individuals in population
    private IndividualType individualEncoding = IndividualType.GROUPCODEDINDIVIDUAL;
    // the way individuals' chromosomes are encoded
    private int numberOfGroupsInGroupEncoding = 8; // number of subgraphs in 
    // chromosome, applicable to group coded individual
    private SelectionType howToSelect = SelectionType.ROULETTEWHEELSELECTION;
    // selection type
    // in GUI list of possibilities
    private double crossingOverProbability = 0.6; // probability of crossing over 
    private CrossingOverType howToCross = CrossingOverType.ONEPOINTWITHONECHILD;
    // method of crossing over
    private double mutationProbability = 0.05; // probability that individual will
    // be mutated
    private int numberOfIterations = 1000; // after this amount of iterations 
    // algorithm stops (if hasn't found solution earlier)
    private final Chart plot = new Chart("K-clique solver", "Individuals' fitness in population", "Generation", "Fitness");
    // plot
    private AbstractIndividual actualBestindividual; // best in last iteration
    private GraphVisualizationActualizer graphActualizer;
    private PlotActualizer chartActualizer;
    private boolean paused = true;

    public void setActualizers(GraphVisualizationActualizer graphActualizer, PlotActualizer chartActualizer) {
        this.graphActualizer = graphActualizer;
        this.chartActualizer = chartActualizer;
    }

    private ArrayList<AbstractIndividual> bestAdoptedInEveryIteration = null;
    // contains array of best adopted individuals

    /**
     * Setter
     *
     * @param graphRepresentation
     */
    public void setGraphRepresentation(GraphRepresentation graphRepresentation) {
        this.graphRepresentation = graphRepresentation;
    }

    /**
     * Setter
     *
     * @param numberOfIndividuals
     */
    public void setNumberOfIndividuals(int numberOfIndividuals) {
        this.numberOfIndividuals = numberOfIndividuals;
    }

    /**
     * Setter
     *
     * @param individualEncoding
     */
    public void setIndividualEncoding(IndividualType individualEncoding) {
        this.individualEncoding = individualEncoding;
    }

    /**
     * Setter
     *
     * @param numberOfGroupsInGroupEncoding
     */
    public void setNumberOfGroupsInGroupEncoding(int numberOfGroupsInGroupEncoding) {
        this.numberOfGroupsInGroupEncoding = numberOfGroupsInGroupEncoding;
    }

    /**
     * Setter
     *
     * @param howToSelect
     */
    public void setHowToSelect(SelectionType howToSelect) {
        this.howToSelect = howToSelect;
    }

    /**
     * Setter
     *
     * @param crossingOverProbability
     */
    public void setCrossingOverProbability(double crossingOverProbability) {
        this.crossingOverProbability = crossingOverProbability;
    }

    /**
     * Setter
     *
     * @param howToCross
     */
    public void setHowToCross(CrossingOverType howToCross) {
        this.howToCross = howToCross;
    }

    /**
     * Setter
     *
     * @param mutationProbability
     */
    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    /**
     * Setter
     *
     * @param numberOfIterations
     */
    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    /**
     * Pauses thread.
     */
    public void pauseSolving() {
        paused = true;
    }

    /**
     * Resumes thread.
     */
    public synchronized void resumeSolving() {
        paused = false;
        notify();
    }

    @Override
    public void run() {
        while (true) {
            if (!paused) {
                runAlgorithm();
                pauseSolving();
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

    /**
     * Invokes proper algorithm in according to individual encoding.
     */
    public void runAlgorithm() {
        bestAdoptedInEveryIteration = new ArrayList<>();
        switch (individualEncoding) {
            case GROUPCODEDINDIVIDUAL:
                runWithGroupEncoding();
                break;
            case BINARYCODEDINDIVIDUAL:
                runWithBinaryEncoding();
                break;
        }
    }

    /**
     * Runs genetic algorithm for individuals with group encoding.
     */
    private void runWithGroupEncoding() {
        try {
            Population population = new Population(numberOfIndividuals, graphRepresentation, individualEncoding, numberOfGroupsInGroupEncoding);
            int rate = (int) ceil(numberOfIterations / numberOfGroupsInGroupEncoding);
            boolean finished = false;
            for (int i = 1; !finished; i++) {
                if (i % rate == 0) {
                    population.singleLifeCycle(true, howToSelect, crossingOverProbability, howToCross, mutationProbability, 0.7);
                } else {
                    population.singleLifeCycle(false, howToSelect, crossingOverProbability, howToCross, mutationProbability, 0.7);
                }
                actualBestindividual = population.findBestAdoptedIndividual();
                bestAdoptedInEveryIteration.add(actualBestindividual);
                if (actualBestindividual.getFitness() == 1 || i == numberOfIterations) {
                    finished = true;
                }
                actualizePlot(i, population);
            }
            population.ex.shutdownNow();
        } catch (GeneticAlgorithmException ex) {
            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Runs genetic algorithm for individuals with binary encoding.
     */
    private void runWithBinaryEncoding() {
        try {
            Population population = new Population(numberOfIndividuals, graphRepresentation, individualEncoding);
            boolean finished = false;
            for (int i = 1; !finished; i++) {
                population.singleLifeCycle(false, howToSelect, crossingOverProbability, howToCross, mutationProbability, 0.7);
                actualBestindividual = population.findBestAdoptedIndividual();
                bestAdoptedInEveryIteration.add(actualBestindividual);
                if (actualBestindividual.getFitness() == 1 || i == numberOfIterations) {
                    finished = true;
                }
                actualizePlot(i, population);
            }
            population.ex.shutdownNow();
        } catch (GeneticAlgorithmException ex) {
            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Actualizes series of data in plot.
     *
     * @param iteration - number of algorithm iteration
     * @param population - population to get data from
     */
    public void actualizePlot(int iteration, Population population) {
        plot.addNewValueToBestSeries(iteration, population.findBestAdoptedIndividual().getFitness());
        plot.addNewValueToAverageSeries(iteration, population.averageIndividualsFitness());
        plot.addNewValueToWorstSeries(iteration, population.findWorstAdoptedIndividual().getFitness());
        chartActualizer.actualize();
        graphActualizer.actualize();
    }

    /**
     * Getter
     *
     * @return current plot
     */
    public Chart getPlot() {
        return this.plot;
    }

    /**
     * Getter
     *
     * @return graph represantation
     */
    public GraphRepresentation getGraphRepresentation() {
        return graphRepresentation;
    }

    /**
     * Getter
     *
     * @return best in last iteration
     */
    public AbstractIndividual getActualBestindividual() {
        return actualBestindividual;
    }
}
