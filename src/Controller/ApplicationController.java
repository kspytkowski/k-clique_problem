/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski
 */

package Controller;

import genetics.CrossingOverType;
import genetics.IndividualType;
import genetics.SelectionType;
import graph.GraphRepresentation;

/**
 * klasa będzie odpalała wątek z gui
 * @author wukat
 */
public class ApplicationController {
    
    /**
     * Data collected from GUI, needed to run genetic alghoritm
     * Default values also set in GUI
    */
    private GraphRepresentation graph; // graph on which algorithm will run
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
    // alghoritms stops (if hasn't found solution earlier)
    // in GUI from 100 to 2000 ??
    
    
    
    
}
