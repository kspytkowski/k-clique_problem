/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski
 */
package Controller;

import exceptions.GeneticAlgorithmException;
import genetics.CrossingOverType;
import genetics.IndividualType;
import genetics.Population;
import genetics.SelectionType;
import graph.GraphRepresentation;
import static java.lang.Math.ceil;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * klasa będzie odpalała wątek z gui
 *
 * @author wukat
 */
public class ApplicationController {

    /**
     * Data collected from GUI, needed to run genetic algorithm Default values
     * also set in GUI
     */
    private GraphRepresentation graph = null; // graph on which algorithm will run
    // can be drawn, loaded from file or generated
    private int numberOfIndividuals = 20; // number of individuals in population
    // in GUI from 10 to 100
    private IndividualType individualEncoding = IndividualType.GROUPCODEDINDIVIDUAL;
    // the way individuals' chromosomes are encoded
    // in GUI group of checkboxes
    private int numberOfGroupsInGroupEncoding = 8; // number of subgraphs in 
    // chromosome, applicable to group coded individual
    // in GUI set from 4 to 32 (or 16)
    private SelectionType howToSelect = SelectionType.ROULETTEWHEELSELECTION;
    // selection type
    // in GUI list of possibilities
    private double crossingOverProbability = 0.6; // probability of crossing over 
    // in GUI form 0 to 1
    private CrossingOverType howToCross = CrossingOverType.ONEPOINTWITHONECHILD;
    // method of crossing over
    // in GUI list of possibilities
    private double mutationProbability = 0.05; // probability that individual will
    // be mutated
    // in GUI from 0.00 to 0.5 ??
    private int numberOfIterations = 1000; // after this amount of iterations 
    // algorithm stops (if hasn't found solution earlier)
    // in GUI from 100 to 2000 ??

    // also reference to GUI here
    /**
     * Setter
     *
     * @param graph
     */
    public void setGraph(GraphRepresentation graph) {
        this.graph = graph;
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
     * Actualizes data to genetic algorithm.
     */
    public void collectDataFromGUI() {
        // invokes GUI method that collects data and - using setters - sets it
    }

    /**
     * Invokes proper algorithm in according to individual encoding.
     */
    public void runAlgorithm() {
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
            Population population = new Population(numberOfIndividuals, graph, individualEncoding, numberOfGroupsInGroupEncoding);
            int rate = (int) ceil(numberOfIterations / numberOfGroupsInGroupEncoding);
            boolean finished = false;
            for (int i = 1; !finished; i++) {
                if (i % rate == 0) {
                    population.singleLifeCycle(true, howToSelect, crossingOverProbability, howToCross, mutationProbability, 0.7);
                } else {
                    population.singleLifeCycle(false, howToSelect, crossingOverProbability, howToCross, mutationProbability, 0.7);
                }
                if (population.findBestAdoptedIndividual().getFitness() == 1 || i == numberOfIterations) {
                    finished = true;
                }
                // GUI actualizeChartAndGraph(i, population, finished);
            }
        } catch (GeneticAlgorithmException ex) {
            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Runs genetic algorithm for individuals with binary encoding.
     */
    private void runWithBinaryEncoding() {
        try {
            Population population = new Population(numberOfIndividuals, graph, individualEncoding);;
            boolean finished = false;
            for (int i = 1; !finished; i++) {
                population.singleLifeCycle(false, howToSelect, crossingOverProbability, howToCross, mutationProbability, 0.7);
                if (population.findBestAdoptedIndividual().getFitness() == 1 || i == numberOfIterations) {
                    finished = true;
                }
                // GUI actualizeChartAndGraph(i, population, finished);
            }
        } catch (GeneticAlgorithmException ex) {
            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
