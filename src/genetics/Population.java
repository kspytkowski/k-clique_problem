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
     * @param demandedIndividualsAmount - initially amount of individuals
     * @param graph - main graph
     * @param iT - type of individual
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
     * @param demandedIndividualsAmount - initially amount of individuals
     * @param graph - main graph
     * @param iT - type of individual
     * @param numberOfGroups - number of groups in every GroupCodedIndividual
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
     * @param numberOfGroups - important while creating new individuals in group encoding
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
        return graph.getKCliqueSize();
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
     * Keeps constant amount of individuals in population (adds random
     * individuals on random positions in list)
     */
    public void keepConstantPopulationSize() {
        while (individuals.size() < demandedIndividualsAmount) {
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
            Collections.sort(individuals);
            while(individuals.size() > demandedIndividualsAmount) {
                individuals.removeFirst();
            }
        }
    }

    /**
     * Removes worst individuals from population
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
     * Adds individual to population
     *
     * @param i - individual
     */
    public void addIndividual(AbstractIndividual i) {
        individuals.add(rand.nextInt(getActualIndividualsAmount() + 1), i);
    }

    /**
     * Returns actual amount of individuals in population
     *
     * @return amount of individuals
     */
    public int getActualIndividualsAmount() {
        return individuals.size();
    }

    @Override
    public String toString() {
        String s = "Population: \n";
        for (AbstractIndividual ind : individuals) {
            s += ind;
        }
        return s;
    }

    // tylko do pomocy, trzeba cos konkretnego napisac...
    public double printDostatosowanie() {
        // tylko w celach testowych, zeby sie w mainie pokazalo
        double populationFitnessSum = 0;
        Iterator<AbstractIndividual> individualsIterator = getIndividuals().iterator();
        while (individualsIterator.hasNext()) {
            populationFitnessSum += individualsIterator.next().getFitness();
        }
        return populationFitnessSum;
    }

    // tylko do pomocy
    public AbstractIndividual findBestAdoptedIndividual() {
        AbstractIndividual act = individuals.get(0);
        for (int i = 1; i < individuals.size(); i++) {
            if (individuals.get(i).getFitness() > act.getFitness()) {
                act = individuals.get(i);
            }
        }
        return act;
    }
    
    public AbstractIndividual findWorstAdoptedIndividual() {
        AbstractIndividual act = individuals.get(0);
        for (int i = 1; i < individuals.size(); i++) {
            if (individuals.get(i).getFitness() < act.getFitness()) {
                act = individuals.get(i);
            }
        }
        return act;
    }
    
    public double averageIndividualsFitness() {
        return printDostatosowanie() / getActualIndividualsAmount(); // a co to jest..?
    }
    
}
