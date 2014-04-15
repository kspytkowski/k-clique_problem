package genetics;

import exceptions.GeneticAlgorithmException;
import java.util.LinkedList;
import java.util.Random;
import exceptions.NoPossibilityToCreateIndividualWithGivenParameters;
import graph.GraphRepresentation;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Individual implements Comparable<Individual> {

    private GraphRepresentation graph = null;
    private int[] chromosome; // table of subgraph's vertices (0 - not exists, 1 - exists)
    private int activeGenesAmount; // amount of vertices in subgraph
    private double fitness; // shows how well individual is adopted in population
    private int numberOfSubgraphs; // actual number of groups in chromosome

    /**
     * Constructor - creates subgraph that has size of kCliqueSize - chooses
     * appropriate amount of genes (vertices) and puts them into chromosome
     * (table)
     *
     * @param graphSize - graph's size (amount of vertices)
     * @param kCliqueSize - k-clique size (amount of vertices)
     */
    public Individual(int graphSize, int kCliqueSize) {
        // wyjatki?!
        this.activeGenesAmount = kCliqueSize;
        chromosome = new int[graphSize];
        LinkedList<Integer> helpList = new LinkedList<>();
        for (int i = 0; i < graphSize; i++) {
            helpList.add(i);
        }
        Random rand = new Random();
        for (int i = 0; i < kCliqueSize; i++) {
            int k = helpList.get(rand.nextInt(graphSize - i));
            chromosome[k] = 1;
            helpList.remove((Integer) k);
        }
        // TO TEST VERSION
        // chromosome = new byte[graphSize];
        // Random rand = new Random();
        // for (int i = 0; i < graphSize; i++) {
        // if (rand.nextBoolean()) {
        // chromosome[i] = 1;
        // this.activeGenesAmount++;
        // }
        // }

    }

    /* MY VERSION */
    /* wukat */
    /* please, don't really care about it, I can be serious */
    /* sometimes */
    /**
     * Constructor - creates graph (maximum size), every index in chromosome is
     * a vertex and value assigned to vertex indicates to subgraph which
     * contains this vertex
     *
     * @param graphSize - graph's size (amount of vertices)
     * @param numberOfGroups - number of subgraphs
     * @throws NoPossibilityToCreateIndividualWithGivenParameters - excetion
     * thrown when number of groups is bigger than graph size, because it
     * doesn't really make sense
     */
    public Individual(int graphSize, int numberOfGroups, GraphRepresentation graph) throws NoPossibilityToCreateIndividualWithGivenParameters { // next version - added temporarily, only to change signature
        if (numberOfGroups > graphSize) {// not nice, may couse some stupid things
            throw new NoPossibilityToCreateIndividualWithGivenParameters("Number of groups too big");
        }
        this.graph = graph;
        numberOfSubgraphs = numberOfGroups;
        chromosome = new int[graphSize];
        Random rand = new Random();
        for (int i = 0; i < graphSize; i++) {
            chromosome[i] = rand.nextInt(numberOfGroups); // groups coded from 0 to numberOfGroups - 1
        }
    }

    /**
     * Copy constructor
     *
     * @param i - individual
     */
    public Individual(Individual i) {
        this.activeGenesAmount = i.getActiveGenesAmount();
        this.chromosome = i.chromosome.clone();
        this.fitness = i.getFitness();
    }

    /**
     * Constructor - creates blank Individual (all genes are 0)
     *
     * @param chromosomeLength - amount of genes
     *
     */
    public Individual(int chromosomeLength) {
        this.activeGenesAmount = 0;
        this.chromosome = new int[chromosomeLength];
        this.fitness = 0.0;
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
     * @param index - index of gene
     * @return value of gene: 0 - not exists, 1 - exists
     */
    public int getValueOfGene(int geneIndex) {
        return chromosome[geneIndex];
    }

    /**
     * Getter
     *
     * @return amount of active genes
     */
    public int getActiveGenesAmount() {
        return activeGenesAmount;
    }

    /**
     * Getter of number of subgraphs (groups)
     *
     * @return number of groups
     */
    public int getNumberOfSubgraphs() {
        return numberOfSubgraphs;
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
     * @param geneIndex - index of gene
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
     * @param fitness - fitness
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * Removes gene - makes 0 form 1 in chromosome
     *
     * @param geneIndex - index of gene to remove
     */
    public void removeGene(int geneIndex) {
        chromosome[geneIndex] = 0;
        activeGenesAmount--;
    }

    /**
     * Adds gene - makes 1 form 0 in chromosome
     *
     * @param geneIndex - index of gene to remove
     */
    public void addGene(int geneIndex) {
        chromosome[geneIndex] = 1;
        activeGenesAmount++;
    }

    /**
     * Sets new value to gene to use in byte coding case
     *
     * @param geneIndex - index of gene
     * @param value - value to set
     */
    public void setGene(int geneIndex, int value) {
        if (chromosome[geneIndex] != value && value == 1) {
            activeGenesAmount++;
        } else if (chromosome[geneIndex] != value && value == 0) {
            activeGenesAmount--;
        }
        chromosome[geneIndex] = value;
    }

    /**
     * Inverses gene - makes 0 form 1 or 1 from 0 in chromosome
     *
     * @param geneIndex - index of gene to inverse
     */
    public void inverseGene(int geneIndex) {
        setGene(geneIndex, (chromosome[geneIndex] == 0) ? 1 : 0);
    }

    /**
     * Mutates gene - change gene to any other possible (from 0 to
     * numberOfSubgraphs - 1)
     *
     * @param geneIndex - index of gene to mutate
     */
    public void mutateGene(int geneIndex) {
        setGene(geneIndex, new Random().nextInt(numberOfSubgraphs));
    }

    /**
     * Determines the fitness of group in chromosome
     *
     * @param group - index of group, must be >= 0 and < numberOfSubgrphs
     * @return fi
     * tness of subgraph
     */
    public double determineFitness(int group) throws GeneticAlgorithmException {
        if (group < 0 || group >= numberOfSubgraphs) {
            throw new GeneticAlgorithmException("Group index out of range: " + group + " " + numberOfSubgraphs);
        }
        LinkedList<Integer> vertexes = getVertexesInGroup(group);
        double k = vertexes.size(), e = 0, isKlique = 0;
        if (k > 1) {
            for (int i = 0; i < k; i++) {
                for (int j = i + 1; j < k; j++) {
                    if (graph.isNeighbor(vertexes.get(i) + 1, vertexes.get(j) + 1)) {
                        e++;
                    }
                }
            }
            System.out.println("vert " + k + " edges " + e);
            if (e / (k * (k - 1) / 2) == 1) {
                isKlique = 1;
            }
            return k > graph.getKCliqueSize() ? 0.5 * (e / (k * (k - 1) / 2) + isKlique * graph.getKCliqueSize() / k)
                    : 0.5 * (e / (k * (k - 1) / 2) + isKlique * k / graph.getKCliqueSize());
        } else {
            return 0;
        }
    }

    /**
     * Determines the fitness of chromosome.
     */
    public void determineIndividualFitness() throws GeneticAlgorithmException {
        double max = 0;
        for (int i = 0; i < numberOfSubgraphs; i++) {
            double temp = determineFitness(i);
            if (temp > max)
                max = temp;
        }
        setFitness(max);
    }
    
    /**
     * Function changes genes in chromosome that in result subgraph with the
     * biggest amount of vertexes is labeled as 0 and so on.
     */
    public void relabelIndividual() throws GeneticAlgorithmException {
        LinkedList<Double> amountOfVertexesInGroup = new LinkedList<>();
        LinkedList<Integer> groupsInOrderOfFitness = new LinkedList<>();
        int[] chromosomeTemp = new int[chromosome.length];
        amountOfVertexesInGroup.addLast(determineFitness(0));
        System.out.println(0 + " " + determineFitness(0));
        groupsInOrderOfFitness.addFirst(0);
        for (int i = 1; i < numberOfSubgraphs; i++) {
            boolean added = false;
            double tempFitness = determineFitness(i);
            System.out.println(i + " " + tempFitness);
            for (int j = 0; j < amountOfVertexesInGroup.size() && !added; j++) {
                if (tempFitness > amountOfVertexesInGroup.get(j)) {
                    amountOfVertexesInGroup.add(j, tempFitness);
                    groupsInOrderOfFitness.add(j, i);
                    added = true;
                }
            }
            if (!added) {
                amountOfVertexesInGroup.addLast(tempFitness);
                groupsInOrderOfFitness.addLast(i);
            }
        }
        System.out.println(groupsInOrderOfFitness);
        int k = 0;
        for (Integer i : groupsInOrderOfFitness) {
            for (int j = 0; j < chromosome.length; j++) {
                if (chromosome[j] == i) {
                    chromosomeTemp[j] = k;
                }
            }
            k++;
        }
        chromosome = chromosomeTemp;
    }

    /**
     * Removes group containg the smallest amount of vertices.
     */
    public void removeWorstGroup() throws GeneticAlgorithmException {
        int max = 0;
        for (int i = chromosome.length - 1; i > 0; i--) {
            if (chromosome[i] > max) {
                max = chromosome[i];
            }
        }
        numberOfSubgraphs = max;
        for (int i = chromosome.length - 1; i > 0; i--) {
            if (chromosome[i] == numberOfSubgraphs) {
                chromosome[i]--;
            }
        }
        relabelIndividual();
    }

    /**
     * Counts amount of vertexes in given group
     *
     * @param group - index of group, must be >= 0 and < numberOfSubgrphs
     * @return am
     * ount
     * @throws GeneticAlgorithmException
     */
    private int getAmountOfVertexesInGroup(int group) throws GeneticAlgorithmException {
        if (group < 0 || group >= numberOfSubgraphs) {
            throw new GeneticAlgorithmException("Group index out of range: " + group);
        }
        int k = 0;
        for (int i : chromosome) {
            if (i == group) {
                k++;
            }
        }
        return k;
    }

    /**
     * Returns list of vertexes' indexes in given group
     *
     * @param group - index of group, must be >= 0 and < numberOfSubgrphs
     * @return li
     * st
     * @throws GeneticAlgorithmException
     */
    private LinkedList<Integer> getVertexesInGroup(int group) throws GeneticAlgorithmException {
        if (group < 0 || group >= numberOfSubgraphs) {
            throw new GeneticAlgorithmException("Group index out of range: " + group);
        }
        LinkedList<Integer> vertexes = new LinkedList<>();
        int k = 0;
        for (int i = 0; i < chromosome.length; i++) {
            if (chromosome[i] == group) {
                vertexes.addLast(i);
            }
        }
        return vertexes;
    }

    /**
     * Compares two individuals - better individual has better fitness
     *
     * @param i - individual
     */
    @Override
    public int compareTo(Individual i) {
        if (this.fitness == i.fitness) {
            return 0;
        }
        return (this.fitness > i.fitness) ? 1 : -1;
    }

    /**
     * Checks if "second" individual is better than "first" individual
     *
     * @param first - first individual to compare to
     * @param second - second individual to compare to
     * @return true if second individual is better, false otherwise
     */
    public static Individual isBetter(Individual first, Individual second) {
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
