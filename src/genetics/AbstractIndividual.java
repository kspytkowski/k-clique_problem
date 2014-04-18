package genetics;

import exceptions.GeneticAlgorithmException;
import graph.GraphRepresentation;

/**
 * @author Krzysztof Spytkowski
 * @date 17 kwi 2014
 */
public abstract class AbstractIndividual implements Comparable<AbstractIndividual> {

    protected GraphRepresentation graph = null; // main graph
    protected int[] chromosome; // table of subgraph's vertices
    protected double fitness; // shows how well individual is adopted in population

    /**
     * Determines individual's fitness
     * 
     * @throws GeneticAlgorithmException
     *             - TRZA TO USUNAC, nie chce tu wyjatku
     */
    public abstract void determineIndividualFitness() throws GeneticAlgorithmException;

    /**
     * Creates new individual which is the same as given in parameter
     * 
     * @param individual
     *            - individual
     * @return new individual
     */
    public abstract AbstractIndividual createIndividual(AbstractIndividual individual);

    /**
     * Mutates specified gene (changes gene to any other possible), after mutation sets new fitness of individual
     * 
     * @param geneIndex
     *            - index of gene
     */
    public abstract void mutateGene(int geneIndex);

    /**
     * Sets new value to gene, if ganeIndex == chromosomeLength - 1 then sets new fitness of individual, used in crossing-over
     * 
     * @param geneIndex
     *            - gene's index
     * @param value
     *            - new value
     */
    public abstract void setGene(int geneIndex, int value);

    /**
     * Getter
     * 
     * @return graph
     */
    public GraphRepresentation getGraph() {
        return graph;
    }

    /**
     * Setter
     * 
     * @param graph
     *            - graph
     */
    public void setGraph(GraphRepresentation graph) {
        this.graph = graph;
    }

    /**
     * Getter
     * 
     * @return length of chromosome
     */
    public int getChromosomeLength() {
        return chromosome.length;
    }

    /**
     * Getter
     * 
     * @param index
     *            - index of gene
     * @return value of gene
     */
    public int getValueOfGene(int geneIndex) {
        return chromosome[geneIndex];
    }

    /**
     * Getter
     * 
     * @return chromosome - table
     */
    public int[] getChromosome() {
        return chromosome;
    }

    /**
     * Getter
     * 
     * @param geneIndex
     *            - index of gene
     * @return value of gene
     */
    public int getGene(int geneIndex) {
        return chromosome[geneIndex];
    }

    /**
     * Getter
     * 
     * @return fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Setter
     * 
     * @param fitness
     *            - fitness
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * Compares two individuals - better individual has better fitness
     * 
     * @param i
     *            - individual
     */
    @Override
    public int compareTo(AbstractIndividual i) {
        if (this.fitness == i.getFitness()) {
            return 0;
        }
        return (this.fitness > i.getFitness()) ? 1 : -1;
    }

    /**
     * Checks if "second" individual is better than "first" individual
     * 
     * @param first
     *            - first individual to compare to
     * @param second
     *            - second individual to compare to
     * @return true if second individual is better, false otherwise
     */
    public static AbstractIndividual isBetter(AbstractIndividual first, AbstractIndividual second) {
        return first.compareTo(second) > 0 ? first : second;
    }

    @Override
    public String toString() {
        String s = "Individual: ";
        for (int i : chromosome) {
            s += i;
        }
        s += " ";
        s += fitness;
        return s += "\n";
    }
}
