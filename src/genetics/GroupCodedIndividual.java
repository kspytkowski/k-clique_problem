package genetics;

import exceptions.GeneticAlgorithmException;
import exceptions.NoPossibilityToCreateIndividualWithGivenParameters;
import graph.GraphRepresentation;

import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author wukat
 * @modified Krzysztof Spytkowski
 * @date 18 kwi 2014
 */
public final class GroupCodedIndividual extends AbstractIndividual {

    private int numberOfSubgraphs; // number of groups that should be in chromosome
    LinkedList<Double> fitnessOfEverySubgraph; // constains fitness of every subgraph

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
     * Determines the fitness of group in chromosome
     * 
     * @param group
     *            - index of group, must be >= 0 and < numberOfSubgrphs
     * @return fi tness of subgraph
     */
    public double determineFitness(int group) throws GeneticAlgorithmException {
        if (group < 0 || group >= numberOfSubgraphs) {
            throw new GeneticAlgorithmException("Group index out of range: " + group + " " + numberOfSubgraphs);
        }
        LinkedList<Integer> vertexes = getVertexesInGroup(group);
        double k = vertexes.size(), e = 0, isKlique = 0;
        double differenceBetweenSizes = (k > graph.getKCliqueSize()) ? k - graph.getKCliqueSize() : graph.getKCliqueSize() - k;
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
            return k > graph.getKCliqueSize() ? 0.4 * (e / (k * (k - 1) / 2)) + (isKlique * graph.getKCliqueSize() / k) * 0.5 + 0.2 / (1 + Math.exp(differenceBetweenSizes)) : 0.4 * (e / (k * (k - 1) / 2)) + (isKlique * k / graph.getKCliqueSize()) * 0.5 + 0.2 / (1 + Math.exp(differenceBetweenSizes));
        } else {
            return 0;
        }
    }

    /**
     * Sets list of fitnesses, every group has own fitness.
     * 
     * @throws GeneticAlgorithmException
     */
    public void setFitnesses() throws GeneticAlgorithmException {
        LinkedList<Double> fitnessOfEveryGroup = new LinkedList<>();
        for (int i = 0; i < numberOfSubgraphs; i++) {
            fitnessOfEveryGroup.addLast(determineFitness(i));
        }
        fitnessOfEverySubgraph = fitnessOfEveryGroup;
    }

    /**
     * Function changes genes in chromosome that in result subgraph with the biggest amount of vertexes is labeled as 0 and so on.
     */
    public void relabelIndividual() throws GeneticAlgorithmException {
        LinkedList<Double> newFitnessOfEveryGroup = new LinkedList<>();
        LinkedList<Double> fitnessOfEveryGroup = new LinkedList<>();
        LinkedList<Integer> groupsInOrderOfFitness = new LinkedList<>();
        int[] chromosomeTemp = new int[chromosome.length];

        // new version
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

        // fitnessOfEveryGroup.addLast(determineFitness(0));
        // groupsInOrderOfFitness.addFirst(0);
        // for (int i = 1; i < numberOfSubgraphs; i++) {
        // boolean added = false;
        // double tempFitness = determineFitness(i);
        // for (int j = 0; j < fitnessOfEveryGroup.size() && !added; j++) {
        // if (tempFitness > fitnessOfEveryGroup.get(j)) {
        // fitnessOfEveryGroup.add(j, tempFitness);
        // groupsInOrderOfFitness.add(j, i);
        // added = true;
        // }
        // }
        // if (!added) {
        // fitnessOfEveryGroup.addLast(tempFitness);
        // groupsInOrderOfFitness.addLast(i);
        // }
        // }
        //
        //
        // int k = 0;
        // for (Integer i : groupsInOrderOfFitness) {
        // for (int j = 0; j < chromosome.length; j++) {
        // if (chromosome[j] == i) {
        // chromosomeTemp[j] = k;
        // }
        // }
        // k++;
        // }
        chromosome = chromosomeTemp;
    }

    /**
     * Removes the least fit group. After this function invoke relabelIndividual or determineIndividualFitness.
     * 
     * @return true if there were at least 2 groups and one was removed
     */
    @Override
    public boolean removeWorstGroup() {
        numberOfSubgraphs = getRealNumberOfSubgraphs() - 1;
        if (numberOfSubgraphs > 1) {
            for (int i = chromosome.length - 1; i >= 0; i--) {
                if (chromosome[i] == numberOfSubgraphs) {
                    chromosome[i]--;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Removes the least fit group and splits vertexes randomly to other groups (but not to 0 group). After this function invoke relabelIndividual or determineIndividualFitness. Before also relabel.
     * 
     * @return true if there were at least 2 groups and one was removed
     */
    @Override
    public boolean removeWorstGroupAndSplitIntoOthers() {
        Random rand = new Random();
        numberOfSubgraphs = getRealNumberOfSubgraphs() - 1;
        if (numberOfSubgraphs > 2) {
            for (int i = chromosome.length - 1; i >= 0; i--) {
                if (chromosome[i] == numberOfSubgraphs) {
                    chromosome[i] = rand.nextInt(numberOfSubgraphs - 1) + 1;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Reapirs chromosome - every group has to have at least 1 element works only on relabelled chromosome
     * 
     * @return true if operation was possible
     * @throws GeneticAlgorithmException
     */
    public boolean repair() throws GeneticAlgorithmException {
        LinkedList<Integer> amountOfVertexesInGroup = getAmountOfVertexesInEveryGroup();
        if (amountOfVertexesInGroup.contains(0) && graph.getVertexCount() >= numberOfSubgraphs) {
            int indexOfFirstEmptyGroup = amountOfVertexesInGroup.indexOf(0);
            boolean flag = false;
            for (int i = indexOfFirstEmptyGroup; i < amountOfVertexesInGroup.size(); i++) {
                for (int j = indexOfFirstEmptyGroup - 1; j >= 0 && !flag; j--) {
                    if (amountOfVertexesInGroup.get(j) >= 2) {
                        changeVertexAssignedGroup(j, i);
                        amountOfVertexesInGroup.set(j, amountOfVertexesInGroup.get(j) - 1);
                        flag = true;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Changes group assigned to vertex from from group to to group
     * 
     * @param from
     * @param to
     */
    public void changeVertexAssignedGroup(int from, int to) {
        boolean flag = true;
        for (int i = 0; i < chromosome.length && flag; i++) {
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
    public int getRealNumberOfSubgraphs() {
        int max = 0;
        for (int i = chromosome.length - 1; i > 0; i--) {
            if (chromosome[i] > max) {
                max = chromosome[i];
            }
        }
        return max + 1;
    }

    /**
     * Counts amount of vertexes in given group
     * 
     * @param group
     *            - index of group, must be >= 0 and < numberOfSubgrphs
     * @return am ount of vertexes in given group
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
     * Counts amount of vertexes in every group
     * 
     * @return list containing amount of vertexes in everys group
     * @throws GeneticAlgorithmException
     */
    private LinkedList<Integer> getAmountOfVertexesInEveryGroup() throws GeneticAlgorithmException {
        LinkedList<Integer> amountOfVertexesInGroup = new LinkedList<>();
        for (int i = 0; i < numberOfSubgraphs; i++) {
            amountOfVertexesInGroup.add(i, getAmountOfVertexesInGroup(i));
        }
        return amountOfVertexesInGroup;
    }

    /**
     * Returns list of vertexes' indexes in given group
     * 
     * @param group
     *            - index of group, must be >= 0 and < numberOfSubgrphs
     * @return li st of vertexes' indexes in given group
     * @throws GeneticAlgorithmException
     */
    private LinkedList<Integer> getVertexesInGroup(int group) throws GeneticAlgorithmException {
        if (group < 0 || group >= numberOfSubgraphs) {
            throw new GeneticAlgorithmException("Group index out of range: " + group);
        }
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
    public void determineIndividualFitness() throws GeneticAlgorithmException {
        setFitnesses();
        // relabelIndividual();
        // repair();
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
        try {
            if (getAmountOfVertexesInGroup(0) < graph.getKCliqueSize()) {
                chromosome[geneIndex] = 0;
            } else {
                chromosome[geneIndex] = new Random().nextInt(numberOfSubgraphs);
            }
        } catch (GeneticAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //
        // try { // krzysiek dodal, skoro mutujemy, to odrazu relabelujmy i liczmy nowe przystosowanie
        // determineIndividualFitness();
        // } catch (GeneticAlgorithmException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

    @Override
    public void setGene(int geneIndex, int value) {
        chromosome[geneIndex] = value;
        // na pewno nie w tym miejscu
        // if (geneIndex == getChromosomeLength() - 1) { // jezeli zmienilismy ostatni gen, to liczymy nowe przystosowanie (patrz krzyzowanie)
        // try {
        // determineIndividualFitness();
        // } catch (GeneticAlgorithmException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // }
    }

}
