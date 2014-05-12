package genetics;

import static java.lang.Math.ceil;
import exceptions.GeneticAlgorithmException;
import graph.GraphRepresentation;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Krzysztof Spytkowski
 * @date 29th March 2014
 */
public class Population {

    public final ExecutorService ex = Executors.newFixedThreadPool((Runtime.getRuntime().availableProcessors() > 3) ? Runtime.getRuntime().availableProcessors() - 2 : 1);

    private final GraphRepresentation graph; // main graph
    private LinkedList<AbstractIndividual> individuals; // list of individuals
    private final int demandedIndividualsAmount; // amount of individuals that SHOULD BE in population
    private final Random rand = new Random(); // object that creates random numbers
    private IndividualType individualType; // type of individual
    private int numberOfGroups; // number of groups in individual (when group coding)...
    private LinkedList<IndividualsGroup> groupsOfIndividuals; // individuals separated into groups,
    //needed to compute simultanously

    /**
     * Constructor (for binary coded individuals)
     *
     * @param demandedIndividualsAmount - initially amount of individuals
     * @param graph - main graph
     * @param iT - type of individual
     * @throws GeneticAlgorithmException
     */
    public Population(int demandedIndividualsAmount, GraphRepresentation graph, IndividualType iT) throws GeneticAlgorithmException {
        if (demandedIndividualsAmount < 1) {
            throw new GeneticAlgorithmException("Population has to have more than 0 individuals");
        }
        if (graph.getVertexCount() < 1) {
            throw new GeneticAlgorithmException("Graph has to have more than 0 vertices");
        }
        this.individualType = iT;
        this.graph = graph;
        this.demandedIndividualsAmount = demandedIndividualsAmount;
        individuals = new LinkedList<>();
        for (int i = 0; i < demandedIndividualsAmount; i++) {
            individuals.add(new BinaryCodedIndividual(graph));
        }
        makeGroupsOfIndividuals();
    }

    /**
     * Constructor (for group coded individuals)
     *
     * @param demandedIndividualsAmount - initially amount of individuals
     * @param graph - main graph
     * @param iT - type of individual
     * @param numberOfGroups - number of groups in every GroupCodedIndividual,
     * if bigger than number of vertexes in graph, set to number of vertexes in
     * graph
     * @throws GeneticAlgorithmException
     */
    public Population(int demandedIndividualsAmount, GraphRepresentation graph, IndividualType iT, int numberOfGroups) throws GeneticAlgorithmException {
        if (demandedIndividualsAmount < 1) {
            throw new GeneticAlgorithmException("Population has to have more than 0 individuals");
        }
        if (graph.getVertexCount() < 1) {
            throw new GeneticAlgorithmException("Graph has to have more than 0 vertices");
        }
        this.individualType = iT;
        this.numberOfGroups = (numberOfGroups > graph.getVertexCount()) ? graph.getVertexCount() : numberOfGroups;
        this.graph = graph;
        this.demandedIndividualsAmount = demandedIndividualsAmount;
        individuals = new LinkedList<>();
        for (int i = 0; i < demandedIndividualsAmount; i++) {
            individuals.add(new GroupCodedIndividual(numberOfGroups, graph));
        }
        makeGroupsOfIndividuals();
    }

    /**
     * One cycle. //TODO
     *
     * @param specialYear
     * @param selection
     * @param crossingOverProbability
     * @param crossingOverType
     * @param mutationProbability
     * @param toRemove
     */
    public void singleLifeCycle(boolean specialYear, SelectionType selection, double crossingOverProbability, CrossingOverType crossingOverType, double mutationProbability, double toRemove) {
        if (specialYear && numberOfGroups > 2) {
            determineEveryIndividualFitness();
            removeWorstGroupInGroupEncoding();
            numberOfGroups--;
            determineEveryIndividualFitness();
        }
        Selection.proceedSelection(selection, this);
        CrossingOver.crossOver(crossingOverType, this, crossingOverProbability);
        this.keepConstantPopulationSize();
        Mutation.mutate(this, mutationProbability);
        determineEveryIndividualFitness();
        this.removeWorstIndividuals(0.7); // TODO? 
        this.keepConstantPopulationSize();
    }

    // tia, o tym marzyłem - robimy klasę abstrakcyjną, zaślepki, a tu dalej wyspecjalizowane funkcje...
    public void singleLifeCycleKRZYSZTOF(double crossingOverProbability, CrossingOverType crossingOverType, double mutationProbability, double toRemove) {
        Selection.proceedSelection(SelectionType.LINEARRANKINGSELECTION, this);
        CrossingOver.crossOver(crossingOverType, this, crossingOverProbability);
        this.keepConstantPopulationSize();
        Mutation.mutate(this, mutationProbability);
        determineEveryIndividualFitness();
        removeWorstIndividuals(toRemove);
        this.keepConstantPopulationSize();
    }

    /**
     * Getter
     *
     * @param index - index of individual
     * @return individual on specified index or null, if index out of range
     */
    public AbstractIndividual getIndividual(int index) {
        if (index < 0 || index >= individuals.size()) {
            return null;
        }
        return individuals.get(index);
    }

    /**
     * Setter
     *
     * @param numberOfGroups - important while creating new individuals in group
     * encoding
     */
    public void setNumberOfGroups(int numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }

    /**
     * Getter
     *
     * @return size of k-clique
     */
    public int getKCliqueSize() {
        return graph.getsearchedKCliqueSize();
    }

    /**
     * Getter
     *
     * @return type of individuals in population
     */
    public IndividualType getIndividualType() {
        return individualType;
    }

    /**
     * Getter
     *
     * @return list of individuals
     */
    public LinkedList<AbstractIndividual> getIndividuals() {
        return individuals;
    }

    /**
     * Setter
     *
     * @param individuals - list of individuals
     */
    public void setIndividuals(LinkedList<AbstractIndividual> individuals) {
        this.individuals = individuals;
    }

    /**
     * Getter
     *
     * @return amount of individuals that should be in population
     */
    public int getDemandedIndividualsAmount() {
        return demandedIndividualsAmount;
    }

    /**
     * Getter
     *
     * @return graph
     */
    public GraphRepresentation getGraphRepresentation() {
        return graph;
    }

    /**
     * Counts individuals' fitness sum
     *
     * @return individuals' fitness sum
     */
    public double fitnessSum() {
        double sum = 0;
        Iterator<AbstractIndividual> individualsIterator = individuals.iterator();
        while (individualsIterator.hasNext()) {
            sum += individualsIterator.next().getFitness();
        }
        return sum;
    }

    /**
     * Invokes counting every individual's fitness.
     */
    public void determineEveryIndividualFitness() {
        try {
            ex.invokeAll(actualizeGroupsOfIndividuals());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Separates individuals to groups, number of groups depends on number of
     * available processors.
     */
    private void makeGroupsOfIndividuals() {
        int processors = Runtime.getRuntime().availableProcessors();
        double max = individuals.size();
        int rate = (int) ceil(max / processors);
        Runtime.getRuntime().availableProcessors();
        groupsOfIndividuals = new LinkedList<>();
        for (int i = 0; i < processors && i < max && i * rate < max; i++) {
            groupsOfIndividuals.add(new IndividualsGroup(i * rate, (i + 1) * rate, individuals));
        }
    }

    /**
     * Actualizes once created groups - sets new list of individuals.
     *
     * @return callable collection based on groups
     */
    private LinkedList<Callable<Object>> actualizeGroupsOfIndividuals() {
        LinkedList<Callable<Object>> calls = new LinkedList<>();
        for (IndividualsGroup gr : groupsOfIndividuals) {
            gr.setIndividuals(individuals);
            calls.add(Executors.callable(gr));
        }
        return calls;
    }

    /**
     * Invokes removing worst group on every individual.
     */
    public void removeWorstGroupInGroupEncoding() {
        for (AbstractIndividual ind : individuals) {
            ind.removeWorstGroup();
        }
    }

    /**
     * Keeps constant amount of individuals in population (adds random
     * individuals on random positions in list).
     */
    public void keepConstantPopulationSize() {
        while (individuals.size() < demandedIndividualsAmount) {
            switch (individualType) {
                case BINARYCODEDINDIVIDUAL:
                    addIndividual(new BinaryCodedIndividual(graph)); // adds new Individual in random place in list
                    break;
                case GROUPCODEDINDIVIDUAL:
                    addIndividual(new GroupCodedIndividual(numberOfGroups, graph)); // adds new Individual in random place in list
                    break;
            }
        }
        if (individuals.size() > demandedIndividualsAmount) {
            Collections.sort(individuals);
            while (individuals.size() > demandedIndividualsAmount) {
                individuals.removeFirst();
            }
        }
    }

    /**
     * Removes worst individuals from population.
     *
     * @param howManyToRemove - shows how many individuals should be removed (in
     * percentage)
     */
    public void removeWorstIndividuals(double howManyToRemove) {
        int toRemove = (int) (howManyToRemove * getActualIndividualsAmount());
        Collections.sort(individuals);
        for (int i = 0; i < toRemove; i++) {
            individuals.removeFirst();
        }
    }

    /**
     * Adds individual to population.
     *
     * @param i - individual
     */
    public void addIndividual(AbstractIndividual i) {
        individuals.add(rand.nextInt(getActualIndividualsAmount() + 1), i);
    }

    /**
     * Returns actual amount of individuals in population.
     *
     * @return amount of individuals
     */
    public int getActualIndividualsAmount() {
        return individuals.size();
    }

    /**
     * Counts sum of individuals' fitnesses in population
     *
     * @return population fitness
     */
    public double getPopulationFitness() {
        double populationFitnessSum = 0;
        Iterator<AbstractIndividual> individualsIterator = getIndividuals().iterator();
        while (individualsIterator.hasNext()) {
            populationFitnessSum += individualsIterator.next().getFitness();
        }
        return populationFitnessSum;
    }

    /**
     * Find individual that has highest fitness
     *
     * @return found individual
     */
    public AbstractIndividual findBestAdoptedIndividual() {
        AbstractIndividual actual = individuals.get(0);
        for (int i = 1; i < individuals.size(); i++) {
            if (individuals.get(i).getFitness() > actual.getFitness()) {
                actual = individuals.get(i);
            }
        }
        return actual;
    }

    /**
     * Find individual that has lowest fitness
     *
     * @return found individual
     */
    public AbstractIndividual findWorstAdoptedIndividual() {
        AbstractIndividual actual = individuals.get(0);
        for (int i = 1; i < individuals.size(); i++) {
            if (individuals.get(i).getFitness() < actual.getFitness()) {
                actual = individuals.get(i);
            }
        }
        return actual;
    }

    /**
     * Counts average individuals' fitness in population
     *
     * @return average individuals' fitness
     */
    public double averageIndividualsFitness() {
        return getPopulationFitness() / getActualIndividualsAmount();
    }

    @Override
    public String toString() {
        String s = "Population: \n";
        for (AbstractIndividual ind : individuals) {
            s += ind;
        }
        return s;
    }

    /**
     * Represents group of individuals (on position in list from from to to),
     * used to determine fitness of individuals using many threads.
     */
    private class IndividualsGroup implements Runnable {

        int from, to; // indicators to positions in list
        LinkedList<AbstractIndividual> individuals; // list of individuals

        /**
         * Setter
         *
         * @param individuals - list of individuals
         */
        private void setIndividuals(LinkedList<AbstractIndividual> individuals) {
            this.individuals = individuals;
        }

        /**
         * Constructs group
         *
         * @param from - indicates position of first individual in group
         * @param to - indicates position of last - 1 individual in group, if
         * bigger than number of individuals, set to number of individuals in
         * list
         * @param individuals - lsit of individuals
         */
        private IndividualsGroup(int from, int to, LinkedList<AbstractIndividual> individuals) {
            this.from = from;
            this.to = (to < individuals.size()) ? to : individuals.size();
            this.individuals = individuals;
        }

        @Override
        public void run() {
            for (int i = from; i < to; i++) {
                individuals.get(i).determineIndividualFitness();
            }
        }
    }
}
