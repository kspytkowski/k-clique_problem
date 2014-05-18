/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
package genetics;

import graph.GraphRepresentation;
import java.util.LinkedList;
import java.util.Random;

public final class GroupCodedIndividual extends AbstractIndividual {

    private int numberOfSubgraphs; // number of groups that should be in chromosome
    LinkedList<Double> fitnessOfEverySubgraph; // constains fitness of every subgraph

    /**
     * Constructor - creates individual, every index in chromosome is a vertex
     * and value assigned to vertex indicates to subgraph which contains this
     * vertex
     *
     * @param numberOfSubgraphs - amount of groups (subgraphs)
     * @param graph - main graph
     */
    public GroupCodedIndividual(int numberOfSubgraphs, GraphRepresentation graph) {
        if (numberOfSubgraphs > graph.getVertexCount()) {
            numberOfSubgraphs = graph.getVertexCount();
        }
        this.graph = graph;
        this.numberOfSubgraphs = numberOfSubgraphs;
        chromosome = new int[graph.getVertexCount()];
        Random rand = new Random();
        for (int i = 0; i < graph.getVertexCount(); i++) {
            chromosome[i] = rand.nextInt(numberOfSubgraphs); // groups coded from 0 to numberOfGroups - 1
        }
        determineIndividualFitness();
    }

    /**
     * Copy constructor
     *
     * @param i - individual
     */
    public GroupCodedIndividual(GroupCodedIndividual i) {
        this.graph = i.getGraph();
        this.numberOfSubgraphs = i.getNumberOfSubgraphs();
        this.chromosome = i.getChromosome().clone();
        this.fitness = i.getFitness();
        determineIndividualFitness();
    }

    /**
     * Getter of number of subgraphs (groups)
     *
     * @return number of groups
     */
    private int getNumberOfSubgraphs() {
        return numberOfSubgraphs;
    }

    /**
     * Determines the fitness of group in chromosome
     *
     * @param group - index of group, must be >= 0 and < numberOfSubgrphs;
     *
     * @return fitness of subgraph, -1 if subgraph has 0 vertexes
     */
    public double determineFitnessOfSubrgaph(int group) {
        LinkedList<Integer> vertexes = getVertexesInGroup(group);
        double k = vertexes.size(), e = 0, isKlique = 0;
        double differenceBetweenSizes = (k > graph.getsearchedKCliqueSize()) ? k - graph.getsearchedKCliqueSize() : graph.getsearchedKCliqueSize() - k;
        if (k > 1) {
            for (int i = 0; i < k; i++) {
                for (int j = i + 1; j < k; j++) {
                    if (graph.isNeighbor(vertexes.get(i) + 1, vertexes.get(j) + 1)) {
                        e++;
                    }
                }
            }
            if (e / (k * (k - 1) / 2) == 1) {
                isKlique = 1;
            }
            return k > graph.getsearchedKCliqueSize() ? 0.4 * (e / (k * (k - 1) / 2)) + (isKlique * graph.getsearchedKCliqueSize() / k) * 0.5 + 0.2 / (1 + Math.exp(differenceBetweenSizes)) : 0.4 * (e / (k * (k - 1) / 2)) + (isKlique * k / graph.getsearchedKCliqueSize()) * 0.5 + 0.2 / (1 + Math.exp(differenceBetweenSizes));
        } else {
            if (k == 1) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * Determines and sets list of fitnesses, every group has own fitness.
     */
    private void setDeterminedFitnessesOfEverySubgraph() {
        LinkedList<Double> fitnessOfEveryGroup = new LinkedList<>();
        for (int i = 0; i < numberOfSubgraphs; i++) {
            fitnessOfEveryGroup.addLast(determineFitnessOfSubrgaph(i));
        }
        fitnessOfEverySubgraph = fitnessOfEveryGroup;
    }

    /**
     * Function changes genes in chromosome that in result subgraph with the
     * best fitness is labeled as 0 and so on.
     */
    public void relabelIndividual() {
        LinkedList<Double> newFitnessOfEveryGroup = new LinkedList<>();
        LinkedList<Integer> groupsInOrderOfFitness = new LinkedList<>();
        int[] chromosomeTemp = new int[chromosome.length];

        for (int i = 0; i < fitnessOfEverySubgraph.size(); i++) {
            double max = -1;
            int index = -1;
            for (int j = 0; j < fitnessOfEverySubgraph.size(); j++) {
                double temp = fitnessOfEverySubgraph.get(j);
                if (temp > max && !groupsInOrderOfFitness.contains(j)) {
                    max = temp;
                    index = j;
                }
            }
            newFitnessOfEveryGroup.addLast(max);
            groupsInOrderOfFitness.addLast(index);
        }
        fitnessOfEverySubgraph = newFitnessOfEveryGroup;
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
     * Removes the least fit group. After this function invoke relabelIndividual
     * or determineIndividualFitness.
     */
    @Override
    public void removeWorstGroup() {
        numberOfSubgraphs = getRealNumberOfSubgraphs() - 1;
        if (numberOfSubgraphs > 1) {
            for (int i = chromosome.length - 1; i >= 0; i--) {
                if (chromosome[i] == numberOfSubgraphs) {
                    chromosome[i]--;
                }
            }
        }
    }

    /**
     * Removes the least fit group and splits vertexes randomly to other groups
     * (but not to 0 group). After this function invoke relabelIndividual or
     * determineIndividualFitness. Before also relabel.
     */
    @Override
    public void removeWorstGroupAndSplitIntoOthers() {
        Random rand = new Random();
        numberOfSubgraphs = getRealNumberOfSubgraphs() - 1;
        if (numberOfSubgraphs > 1) {
            for (int i = chromosome.length - 1; i >= 0; i--) {
                if (chromosome[i] == numberOfSubgraphs) {
                    chromosome[i] = rand.nextInt(numberOfSubgraphs - 1) + 1;
                }
            }
        }
    }

    /**
     * Repairs chromosome - every group has to have at least 1 element.
     */
    public void repair() {
        LinkedList<Integer> amountOfVertexesInGroup = getAmountOfVertexesInEveryGroup();
        if (amountOfVertexesInGroup.contains(0)) {
            int indexOfFirstEmptyGroup = amountOfVertexesInGroup.indexOf(0);
            for (int i = indexOfFirstEmptyGroup; i < amountOfVertexesInGroup.size(); i++) {
                boolean flag = false;
                for (int j = indexOfFirstEmptyGroup - 1; j >= 0 && !flag; j--) {
                    if (amountOfVertexesInGroup.get(j) >= 2) {
                        changeVertexAssignedGroup(j, i);
                        amountOfVertexesInGroup.set(j, amountOfVertexesInGroup.get(j) - 1);
                        flag = true;
                    }
                }
            }
        }
    }

    /**
     * Changes group assigned to vertex from from group to to group
     *
     * @param from - both in range 0 numberOfGroups - 1
     * @param to
     */
    private void changeVertexAssignedGroup(int from, int to) {
        boolean flag = true;
        for (int i = chromosome.length - 1; i >= 0 && flag; i--) {
            if (chromosome[i] == from) {
                chromosome[i] = to;
                flag = false;
            }
        }
    }

    /**
     * Counts number of groups in graph
     *
     * @return real number of groups
     */
    private int getRealNumberOfSubgraphs() {
        int max = 0;
        for (int i = chromosome.length - 1; i >= 0; i--) {
            if (chromosome[i] > max) {
                max = chromosome[i];
            }
        }
        return max + 1;
    }

    /**
     * Counts amount of vertexes in given group
     *
     * @param group - index of group, must be >= 0 and < numberOfSubgrphs
     *
     * @return amount of vertexes in given group
     */
    public int getAmountOfVertexesInGroup(int group) {
        int k = 0;
        for (int i : chromosome) {
            if (i == group) {
                k++;
            }
        }
        return k;
    }

    /**
     * Counts amount of vertexes in every group
     *
     * @return list containing amount of vertexes in everys group
     */
    private LinkedList<Integer> getAmountOfVertexesInEveryGroup() {
        LinkedList<Integer> amountOfVertexesInGroup = new LinkedList<>();
        for (int i = 0; i < numberOfSubgraphs; i++) {
            amountOfVertexesInGroup.add(i, getAmountOfVertexesInGroup(i));
        }
        return amountOfVertexesInGroup;
    }

    /**
     * Returns list of vertexes' indexes in given group
     *
     * @param group - index of group, must be >= 0 and < numberOfSubgrphs
     *
     * @return list of vertexes' indexes in given group
     */
    private LinkedList<Integer> getVertexesInGroup(int group) {
        LinkedList<Integer> vertexes = new LinkedList<>();
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
    public void determineIndividualFitness() {
        setDeterminedFitnessesOfEverySubgraph();
        relabelIndividual();
        repair();
        setDeterminedFitnessesOfEverySubgraph();
        relabelIndividual();
        double max = -1;
        for (int i = 0; i < fitnessOfEverySubgraph.size(); i++) {
            double temp = fitnessOfEverySubgraph.get(i);
            if (temp > max) {
                max = temp;
            }
        }
        setFitness(max);
    }

    @Override
    public GroupCodedIndividual createIndividual(AbstractIndividual ind) {
        return new GroupCodedIndividual((GroupCodedIndividual) ind);
    }

    @Override
    public void mutateGene(int geneIndex) {
        if (getAmountOfVertexesInGroup(0) < graph.getsearchedKCliqueSize()) {
            chromosome[geneIndex] = 0;
        } else {
            chromosome[geneIndex] = new Random().nextInt(numberOfSubgraphs);
        }
    }

    @Override
    public void setGene(int geneIndex, int value) {
        chromosome[geneIndex] = value;
    }

    @Override
    public String toString() {
        String s = "Individual: " + getRealNumberOfSubgraphs() + " " + getNumberOfSubgraphs() + " "
                + chromosome + "\n " + this.hashCode() + "\n";
        for (int i : chromosome) {
            s += i;
        }
        s += " ";
        s += fitness;
        return s += "\n";
    }
}
