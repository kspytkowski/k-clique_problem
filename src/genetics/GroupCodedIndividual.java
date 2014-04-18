package genetics;

import exceptions.GeneticAlgorithmException;
import exceptions.NoPossibilityToCreateIndividualWithGivenParameters;
import graph.GraphRepresentation;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author wukat
 * @modified Krzysztof Spytkowski
 * @date 18 kwi 2014
 */
public class GroupCodedIndividual extends AbstractIndividual {

    private int numberOfSubgraphs; // actual number of groups in chromosome

    /**
     * Constructor - creates graph (maximum size), every index in chromosome is a vertex and value assigned to vertex indicates to subgraph which contains this vertex
     * 
     * @param numberOfSubgraphs
     *            - amount of groups (subgraphs)
     * @param graph
     *            - main graph
     * @throws NoPossibilityToCreateIndividualWithGivenParameters
     *             - exception thrown when number of groups is bigger than graph size, because it doesn't really make sense
     */
    public GroupCodedIndividual(int numberOfSubgraphs, GraphRepresentation graph) throws NoPossibilityToCreateIndividualWithGivenParameters { // next version - added temporarily, only to change signature
        this.graph = graph;
        this.numberOfSubgraphs = numberOfSubgraphs;
        if (numberOfSubgraphs > graph.getGraph().getVertexCount()) {// not nice, may couse some stupid things => masz racje, trzeba sie tego pozbyc... => mozna sprawdzenie wrzucic do population do konstruktora a stad wyrzucic :D
            throw new NoPossibilityToCreateIndividualWithGivenParameters("Number of groups too big");
        }
        chromosome = new int[graph.getGraph().getVertexCount()];
        Random rand = new Random();
        for (int i = 0; i < graph.getGraph().getVertexCount(); i++) {
            chromosome[i] = rand.nextInt(numberOfSubgraphs); // groups coded from 0 to numberOfGroups - 1
        }
        try { // krzysiek dodal => relabelujemy i liczymy przystosowanie
            determineIndividualFitness();
        } catch (GeneticAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Copy constructor
     * 
     * @param i
     *            - individual
     */
    public GroupCodedIndividual(GroupCodedIndividual i) {
        this.graph = i.getGraph();
        this.numberOfSubgraphs = i.getNumberOfSubgraphs();
        this.chromosome = i.getChromosome().clone();
        this.fitness = i.getFitness();
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
     * Setter
     * 
     * @param numberOfSubgraphs
     *            - number of subgraphs
     */
    public void setNumberOfSubgraphs(int numberOfSubgraphs) {
        this.numberOfSubgraphs = numberOfSubgraphs;
    }

    /**
     * Determines the fitness of group in chromosome
     * 
     * @param group
     *            - index of group, must be >= 0 and < numberOfSubgrphs
     * @return fitness of subgraph
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
            return k > graph.getKCliqueSize() ? 0.5 * (e / (k * (k - 1) / 2) + isKlique * graph.getKCliqueSize() / k) : 0.5 * (e / (k * (k - 1) / 2) + isKlique * k / graph.getKCliqueSize());
        } else {
            return 0;
        }
    }

    /**
     * Function changes genes in chromosome that in result subgraph with the biggest amount of vertexes is labeled as 0 and so on.
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
     * Removes group containing the smallest amount of vertices.
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
        relabelIndividual(); // Wojtek, tu lepiej dac determineIndividualFitness (bedzieodrazu relabeling i liczenie nowego przystosowania)
    }

    /**
     * Counts amount of vertexes in given group
     * 
     * @param group
     *            - index of group, must be >= 0 and < numberOfSubgrphs
     * @return amount of vertexes in given group
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
     * @param group
     *            - index of group, must be >= 0 and < numberOfSubgrphs
     * @return list of vertexes' indexes in given group
     * @throws GeneticAlgorithmException
     */
    private LinkedList<Integer> getVertexesInGroup(int group) throws GeneticAlgorithmException {
        if (group < 0 || group >= numberOfSubgraphs) {
            throw new GeneticAlgorithmException("Group index out of range: " + group);
        }
        LinkedList<Integer> vertexes = new LinkedList<>();
        // int k = 0; => po co to skoro nie uzywasz?
        for (int i = 0; i < chromosome.length; i++) {
            if (chromosome[i] == group) {
                vertexes.addLast(i);
            }
        }
        return vertexes;
    }

    /**
     * Determines the fitness of chromosome.
     */
    @Override
    public void determineIndividualFitness() throws GeneticAlgorithmException {
        relabelIndividual(); // krzysiek dodal, potrzebne to tu chyba jest
        double max = 0;
        for (int i = 0; i < numberOfSubgraphs; i++) {
            double temp = determineFitness(i);
            if (temp > max)
                max = temp;
        }
        setFitness(max);
    }

    @Override
    public GroupCodedIndividual createIndividual(AbstractIndividual ind) {
        return new GroupCodedIndividual((GroupCodedIndividual) ind);
    }

    @Override
    public void mutateGene(int geneIndex) {
        // setGene(geneIndex, new Random().nextInt(numberOfSubgraphs)); // krzysiek => zmienilem na ponizsza linijke
        chromosome[geneIndex] = new Random().nextInt(numberOfSubgraphs);
        try { // krzysiek dodal, skoro mutujemy, to odrazu relabelujmy i liczmy nowe przystosowanie
            determineIndividualFitness();
        } catch (GeneticAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void setGene(int geneIndex, int value) {
        chromosome[geneIndex] = value;
        if (geneIndex == getChromosomeLength() - 1) { // jezeli zmienilismy ostatni gen, to liczymy nowe przystosowanie (patrz krzyzowanie)
            try {
                determineIndividualFitness();
            } catch (GeneticAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
