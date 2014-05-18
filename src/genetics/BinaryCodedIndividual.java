/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
package genetics;

import graph.GraphRepresentation;
import java.util.LinkedList;
import java.util.Random;

public class BinaryCodedIndividual extends AbstractIndividual {

    private int activeGenesAmount; // amount of vertices in subgraph

    @Override
    // there are only two groups in this type of coding so we do not need to remove anything
    public void removeWorstGroupAndSplitIntoOthers() {
    }

    @Override
    // there are only two groups in this type of coding so we do not need to remove anything
    public void removeWorstGroup() {
    }

    /**
     * Constructor - creates subgraph that has size of kCliqueSize - chooses
     * appropriate amount of genes (vertices) and puts them into chromosome
     * (table)
     *
     * @param graphSize - graph's size (amount of vertices)
     */
    public BinaryCodedIndividual(GraphRepresentation graph) {
        this.graph = graph;
        this.activeGenesAmount = graph.getsearchedKCliqueSize();
        chromosome = new int[graph.getVertexCount()];
        LinkedList<Integer> helpList = new LinkedList<>();
        for (int i = 0; i < graph.getVertexCount(); i++) {
            helpList.add(i);
        }
        Random rand = new Random();
        for (int i = 0; i < graph.getsearchedKCliqueSize(); i++) {
            int k = helpList.get(rand.nextInt(graph.getVertexCount() - i));
            chromosome[k] = 1;
            helpList.remove((Integer) k);
        }
        for (int i = 0; i < graph.getVertexCount(); i++) {
            chromosome[i] = (chromosome[i] == 0) ? 1 : 0;
        }
        determineIndividualFitness();
    }

    /**
     * Copy constructor
     *
     * @param i - individual
     */
    public BinaryCodedIndividual(BinaryCodedIndividual i) {
        this.graph = i.getGraph();
        this.activeGenesAmount = i.getActiveGenesAmount();
        this.chromosome = i.getChromosome().clone();
        this.fitness = i.getFitness();
    }

    /**
     * Getter
     *
     * @return amount of active genes
     */
    public int getActiveGenesAmount() {
        return activeGenesAmount;
    }

    @Override
    public final void determineIndividualFitness() {
        int edgesAmount = 0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (getChromosome()[i] == 0) { // 1!
                for (int k = i + 1; k <= graph.getVertexCount(); k++) {
                    if (graph.isNeighbor(i + 1, k) && getChromosome()[k - 1] == 0) {
                        edgesAmount += 1;
                    }
                }
            }
        }
        double isKClique = 0.0;
        if (getActiveGenesAmount() > 1) {
            isKClique = (double) edgesAmount / ((getActiveGenesAmount()
                    * (getActiveGenesAmount() - 1) / 2))
                    * (graph.getsearchedKCliqueSize() - Math.abs(getActiveGenesAmount()
                            - graph.getsearchedKCliqueSize())) / graph.getsearchedKCliqueSize();
        }
        setFitness(isKClique);
    }

    @Override
    public BinaryCodedIndividual createIndividual(AbstractIndividual ind) {
        return new BinaryCodedIndividual((BinaryCodedIndividual) ind);
    }

    @Override
    public void mutateGene(int geneIndex) {
        setGene(geneIndex, (chromosome[geneIndex] == 0) ? 1 : 0);
    }

    @Override
    public void setGene(int geneIndex, int value) {
        if (chromosome[geneIndex] != value && value == 0) { // 1!
            activeGenesAmount++;
        } else if (chromosome[geneIndex] != value && value == 1) { // 0!
            activeGenesAmount--;
        }
        chromosome[geneIndex] = value;
    }
}
