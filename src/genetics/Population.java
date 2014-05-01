package genetics;

import exceptions.GeneticAlgorithmException;
import exceptions.NoPossibilityToCreateIndividualWithGivenParameters;
import graph.GraphRepresentation;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Population {

    private final GraphRepresentation graph; // main graph
    private LinkedList<AbstractIndividual> individuals; // list of individuals
    private final int demandedIndividualsAmount; // amount of individuals that SHOULD BE in population
    private final Random rand = new Random(); // object that creates random numbers
    private IndividualType individualType; // type of individual
    private int numberOfGroups; // number of groups in individual (when group coding)...

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
        if (graph.getGraph().getVertexCount() < 1) {
            throw new GeneticAlgorithmException("Graph has to have more than 0 vertices");
        }
        this.individualType = iT;
        this.graph = graph;
        this.demandedIndividualsAmount = demandedIndividualsAmount;
        individuals = new LinkedList<>();
        for (int i = 0; i < demandedIndividualsAmount; i++) {
            individuals.add(new BinaryCodedIndividual(graph));
        }
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
        if (graph.getGraph().getVertexCount() < 1) {
            throw new GeneticAlgorithmException("Graph has to have more than 0 vertices");
        }
        this.individualType = iT;
        this.numberOfGroups = numberOfGroups; // tu mozna dac ten wyjatek sprawdzajacy czy numberOfGroups < rozmiaru grafu (zamiast wyjatku w konstr. GroupCodedIndividual)
        this.graph = graph;
        this.demandedIndividualsAmount = demandedIndividualsAmount;
        individuals = new LinkedList<>();
        for (int i = 0; i < demandedIndividualsAmount; i++) {
            try {
                individuals.add(new GroupCodedIndividual(numberOfGroups, graph));
            } catch (NoPossibilityToCreateIndividualWithGivenParameters e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void singleLifeCycle(boolean specialYear, SelectionType selection, double crossingOverProbability, CrossingOverType crossingOverType, double mutationProbability, double toRemove) throws GeneticAlgorithmException {
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
    
    //tia, o tym marzyłem - robimy klasę abstrakcyjną, zaślepki, a tu dalej wyspecjalizowane funkcje...
    public void singleLifeCycleKRZYSZTOF(double crossingOverProbability, CrossingOverType crossingOverType, double mutationProbability, double toRemove) throws GeneticAlgorithmException {
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
    public void determineEveryIndividualFitness() throws GeneticAlgorithmException {
        for (AbstractIndividual ind : individuals) {
            ind.determineIndividualFitness();
        }
    }

    /**
     * Invokes removing worst group on every individual.
     */
    public void removeWorstGroupInGroupEncoding() throws GeneticAlgorithmException {
        for (AbstractIndividual ind : individuals) {
            ind.removeWorstGroup();
        }
    }

    /**
     * Keeps constant amount of individuals in population (adds random individuals on random positions in list).
     */
    public void keepConstantPopulationSize() {
        while (individuals.size() < demandedIndividualsAmount) {
            //System.out.println("AA");
            try {
                switch (individualType) {
                case BINARYCODEDINDIVIDUAL:
                    addIndividual(new BinaryCodedIndividual(graph)); // dodajemy w losowe miejsce w liscie
                    break;
                case GROUPCODEDINDIVIDUAL:
                    addIndividual(new GroupCodedIndividual(numberOfGroups, graph)); // dodajemy w losowe miejsce w liscie
                    break;
                }
            } catch (NoPossibilityToCreateIndividualWithGivenParameters e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (individuals.size() > demandedIndividualsAmount) {
            //System.out.println("BB");
            Collections.sort(individuals);
            while (individuals.size() > demandedIndividualsAmount) {
                individuals.removeFirst();
            }
        }
        //System.out.println(individuals.size());
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

    @Override
    public String toString() {
        String s = "Population: \n";
        for (AbstractIndividual ind : individuals) {
            s += ind;
        }
        return s;
    }
}
