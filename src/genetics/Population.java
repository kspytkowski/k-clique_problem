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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Krzysztof Spytkowski
 * @date 29th March 2014
 */
public class Population {

    private static final ExecutorService ex = Executors.newFixedThreadPool(8);

    private final GraphRepresentation graph; // main graph
    private LinkedList<AbstractIndividual> individuals; // list of individuals
    private final int demandedIndividualsAmount; // amount of individuals that SHOULD BE in population
    private final Random rand = new Random(); // object that creates random numbers
    private IndividualType individualType; // type of individual
    private int numberOfGroups; // number of groups in individual (when group coding)...
    private LinkedList<IndividualsGroup> groupsOfIndividuals;

    /**
     * Constructor (for binary coded individuals)
     * 
     * @param demandedIndividualsAmount
     *            - initially amount of individuals
     * @param graph
     *            - main graph
     * @param iT
     *            - type of individual
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
     * @param demandedIndividualsAmount
     *            - initially amount of individuals
     * @param graph
     *            - main graph
     * @param iT
     *            - type of individual
     * @param numberOfGroups
     *            - number of groups in every GroupCodedIndividual
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
        // tu bedzie problem jak uzytkownik da sobie liczbe grup = 10, a graf z 8 wierzcholkami => wowczas tutaj (Population) numberOfGrups ustawi sie na 10
        // a w konstruktorze i klasie GroupCodedIndividual numberOfGraoups ustawi sie na 8 => czy ta roznica miedzy numberOfgroups nie bedzie przeszkadzac?
        this.numberOfGroups = numberOfGroups; // tu mozna dac ten wyjatek sprawdzajacy czy numberOfGroups < rozmiaru grafu (zamiast wyjatku w konstr. GroupCodedIndividual)
        this.graph = graph;
        this.demandedIndividualsAmount = demandedIndividualsAmount;
        individuals = new LinkedList<>();
        for (int i = 0; i < demandedIndividualsAmount; i++) {
            individuals.add(new GroupCodedIndividual(numberOfGroups, graph));
        }
        makeGroupsOfIndividuals();
    }

    public void singleLifeCycle(boolean specialYear, SelectionType selection, double crossingOverProbability, CrossingOverType crossingOverType, double mutationProbability, double toRemove) {
        if (specialYear && numberOfGroups > 2) {
            determineEveryIndividualFitness();
            removeWorstGroupInGroupEncoding();
            numberOfGroups--;
            determineEveryIndividualFitness();
            System.out.println(numberOfGroups);
        }
        Selection.proceedSelection(selection, this);
        CrossingOver.crossOver(crossingOverType, this, crossingOverProbability);
        this.keepConstantPopulationSize();
        Mutation.mutate(this, mutationProbability);
        determineEveryIndividualFitness();
        this.removeWorstIndividuals(0.7);
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
     * @param index
     *            - index of individual
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
     * @param numberOfGroups
     *            - important while creating new individuals in group encoding
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
     * @param individuals
     *            - list of individuals
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
            // LinkedList<IndividualsGroup> groups = new LinkedList<>();
            // for (int i = 0; i < 8 && i < max && i*rat2 < max; i++) {
            // groups.add(new IndividualsGroup(i * rat2, (i + 1) * rat2, individuals));
            // groups.get(i).start();
            /*
             * for (IndividualsGroup gr : groupsOfIndividuals) { ex.submit(gr);
             */

            // }

            /*
             * for (IndividualsGroup indGr : groups) { try { indGr.join(); } catch (InterruptedException ex) { Logger.getLogger(Population.class.getName()).log(Level.SEVERE, null, ex); } }
             */
            /*
             * for (AbstractIndividual i : individuals) i.determineIndividualFitness();
             */
        } catch (InterruptedException ex) {
            Logger.getLogger(Population.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void makeGroupsOfIndividuals() {
        double max = individuals.size();
        int rat2 = (int) ceil(max / 8);
        Runtime.getRuntime().availableProcessors();
        groupsOfIndividuals = new LinkedList<>();
        for (int i = 0; i < 8 && i < max && i * rat2 < max; i++) {
            groupsOfIndividuals.add(new IndividualsGroup(i * rat2, (i + 1) * rat2, individuals));
        }
    }

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
     * Keeps constant amount of individuals in population (adds random individuals on random positions in list).
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
     * @param howManyToRemove
     *            - shows how many individuals should be removed (in percentage)
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
     * @param i
     *            - individual
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

    private class IndividualsGroup implements Runnable {

        int from, to;
        LinkedList<AbstractIndividual> individuals;

        private void setIndividuals(LinkedList<AbstractIndividual> individuals) {
            this.individuals = individuals;
        }

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

    @Override
    public String toString() {
        String s = "Population: \n";
        for (AbstractIndividual ind : individuals) {
            s += ind;
        }
        return s;
    }
}
