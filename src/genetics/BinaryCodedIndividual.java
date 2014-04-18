package genetics;

import graph.GraphRepresentation;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 18 kwi 2014
 */
public class BinaryCodedIndividual extends AbstractIndividual {

    private int activeGenesAmount; // amount of vertices in subgraph

    /**
     * Constructor - creates subgraph that has size of kCliqueSize - chooses appropriate amount of genes (vertices) and puts them into chromosome (table)
     * 
     * @param graphSize
     *            - graph's size (amount of vertices)
     */
    public BinaryCodedIndividual(GraphRepresentation graph) {
        // wyjatki?! => gdy graph jest nullem?
        this.graph = graph;
        this.activeGenesAmount = graph.getKCliqueSize();
        chromosome = new int[graph.getGraph().getVertexCount()];
        LinkedList<Integer> helpList = new LinkedList<>();
        for (int i = 0; i < graph.getGraph().getVertexCount(); i++) {
            helpList.add(i);
        }
        Random rand = new Random();
        for (int i = 0; i < graph.getKCliqueSize(); i++) {
            int k = helpList.get(rand.nextInt(graph.getGraph().getVertexCount() - i));
            chromosome[k] = 1;
            helpList.remove((Integer) k);
        }
        determineIndividualFitness();
        // TO TEST VERSION
        // chromosome = new byte[graph.getGraph().getVertexCount()];
        // Random rand = new Random();
        // for (int i = 0; i < graph.getGraph().getVertexCount(); i++) {
        // if (rand.nextBoolean()) {
        // chromosome[i] = 1;
        // this.activeGenesAmount++;
        // }
        // }

    }

    /**
     * Copy constructor
     * 
     * @param i
     *            - individual
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

    // calkowicie tymczasowa funkcja, moze jakis lepszy pomysl wpadnie....
    @Override
    public void determineIndividualFitness() {
        int lol = 0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (getChromosome()[i] == 1) {
                for (int k = i + 1; k <= graph.getVertexCount(); k++) {
                    if (graph.isNeighbor(i + 1, k) && getChromosome()[k - 1] == 1) {
                        lol += 1;
                    }
                }
            }
        }
        double lol2;
        double czyJestKKlika = 0.0;
        if (getActiveGenesAmount() != 0 && getActiveGenesAmount() != 1) {
            czyJestKKlika = (double) lol / ((getActiveGenesAmount() * (getActiveGenesAmount() - 1) / 2)) * (graph.getKCliqueSize() - Math.abs(getActiveGenesAmount() - graph.getKCliqueSize())) / graph.getKCliqueSize();
         //   czyJestKKlika = (double) lol / ((graph.getKCliqueSize() * (graph.getKCliqueSize() - 1) / 2));
        }
        lol2 = czyJestKKlika;
        setFitness(lol2);
    }

    @Override
    public BinaryCodedIndividual createIndividual(AbstractIndividual ind) {
        return new BinaryCodedIndividual((BinaryCodedIndividual) ind);
    }

    @Override
    public void mutateGene(int geneIndex) {
        setGene(geneIndex, (chromosome[geneIndex] == 0) ? 1 : 0);
        determineIndividualFitness();
    }

    @Override
    public void setGene(int geneIndex, int value) {
        if (chromosome[geneIndex] != value && value == 1) {
            activeGenesAmount++;
        } else if (chromosome[geneIndex] != value && value == 0) {
            activeGenesAmount--;
        }
        chromosome[geneIndex] = value;
        if (geneIndex == getChromosomeLength() - 1) {
            determineIndividualFitness();
        }
    }
}
