package genetics;

import java.util.LinkedList;
import java.util.Random;

import exceptions.GeneticAlgorithmException;

/**
 * @author Krzysztof Spytkowski
 * @date 7 kwi 2014
 */
public class CrossingOver {

    private double crossingOverProbability; // individuals' crossing-over probability
    private final Random rand = new Random(); // object that generates random numbers

    /**
     * Constructor
     * 
     * @param crossingOverProbability
     *            - probability of crossing-over
     * @throws GeneticAlgorithmException
     */
    public CrossingOver(double crossingOverProbability) throws GeneticAlgorithmException {
        if (crossingOverProbability < 0 || crossingOverProbability > 1) {
            throw new GeneticAlgorithmException("Probability of crossing-over should be more than 0 and less than 1");
        }
        this.crossingOverProbability = crossingOverProbability;
    }

    /**
     * Getter
     * 
     * @return individuals' crossing-over probability
     */
    public double getCrossingOverProbability() {
        return crossingOverProbability;
    }

    /**
     * Setter
     * 
     * @param crossingOverProbability
     *            - individuals' crossing-over probability
     * @throws GeneticAlgorithmException
     */
    public void setCrossingOverProbability(double crossingOverProbability) throws GeneticAlgorithmException {
        if (crossingOverProbability < 0 || crossingOverProbability > 1) {
            throw new GeneticAlgorithmException("Probability of crossing-over should be more or equal to 0 and less or equal to 1");
        }
        this.crossingOverProbability = crossingOverProbability;
    }

    /**
     * Starts appropriate crossing-over
     * 
     * @param crossingOverType
     *            - type of crossing-over
     * @param population
     *            - population
     * @throws GeneticAlgorithmException
     */
    public void crossOver(CrossingOverType crossingOverType, Population population) throws GeneticAlgorithmException {
        int amountOfIndividualsToCrossOver = (population.getActualIndividualsAmount() % 2 == 0) ? population.getActualIndividualsAmount() : population.getActualIndividualsAmount() - 1;
        LinkedList<AbstractIndividual> newIndividualsList = new LinkedList<>();
        for (int i = 0; i < amountOfIndividualsToCrossOver; i = i + 2) {
            AbstractIndividual firstParent = population.getIndividual(i);
            AbstractIndividual secondParent = population.getIndividual(i + 1);
            if (crossingOverProbability > rand.nextDouble()) {
                switch (crossingOverType) {
                case ONEPOINTWITHTWOCHILDREN:
                    onePointWithTwoChildrenCrossingOver(firstParent, secondParent, newIndividualsList);
                    break;
                case ONEPOINTWITHONECHILD:
                    onePointWithOneChildCrossingOver(firstParent, secondParent, newIndividualsList);
                    break;
                case UNIFORMCROSSOVER:
                    uniformCrossingOver(firstParent, secondParent, newIndividualsList);
                    break;
                case WEIGHTEDUNIFORMCROSSOVER:
                    weightedUniformCrossingOver(firstParent, secondParent, newIndividualsList);
                    break;
                }
            } else {
                newIndividualsList.add(firstParent);
                newIndividualsList.add(secondParent);
            }
        }
        if (population.getActualIndividualsAmount() % 2 == 1)
            newIndividualsList.add(population.getIndividual(population.getActualIndividualsAmount() - 1)); // amoże by go tak podmienić na losowego?
        population.setIndividuals(newIndividualsList);
    }

    /**
     * 
     * Crosses over two Individuals (parents) and makes two new Individuals (children)
     * 
     * @param firstParent
     *            - first parent
     * @param secondParent
     *            - second parent
     * @param newIndividuals
     *            - list of new Individuals
     */
    public void onePointWithTwoChildrenCrossingOver(AbstractIndividual firstParent, AbstractIndividual secondParent, LinkedList<AbstractIndividual> newIndividuals) {
        AbstractIndividual firstChild = firstParent.createIndividual(firstParent);
        AbstractIndividual secondChild = secondParent.createIndividual(secondParent);
        int splitPoint = rand.nextInt(firstChild.getChromosomeLength());
        for (int j = 0; j < firstChild.getChromosomeLength(); j++) {
            firstChild.setGene(j, j < splitPoint ? firstParent.getValueOfGene(j) : secondParent.getValueOfGene(j));
            secondChild.setGene(j, j < splitPoint ? secondParent.getValueOfGene(j) : firstParent.getValueOfGene(j));
        }
        newIndividuals.add(firstChild);
        newIndividuals.add(secondChild);
    }

    /**
     * Crosses over two Individuals (parents) and makes one new Individual (child)
     * 
     * @param firstParent
     *            - first parent
     * @param secondParent
     *            - second parent
     * @param newIndividuals
     *            - list of new Individuals
     */
    public void onePointWithOneChildCrossingOver(AbstractIndividual firstParent, AbstractIndividual secondParent, LinkedList<AbstractIndividual> newIndividuals) {
        AbstractIndividual child = firstParent.createIndividual(firstParent);
        int splitPoint = rand.nextInt(child.getChromosomeLength());
        for (int j = 0; j < child.getChromosomeLength(); j++) {
            child.setGene(j, j < splitPoint ? firstParent.getValueOfGene(j) : secondParent.getValueOfGene(j));
        }
        newIndividuals.add(child);
    }

    /**
     * Crosses over two Individuals (parents) and makes one new Individual (child)
     * 
     * @param firstParent
     *            - first parent
     * @param secondParent
     *            - second parent
     * @param newIndividuals
     *            - list of new Individuals
     */
    public void uniformCrossingOver(AbstractIndividual firstParent, AbstractIndividual secondParent, LinkedList<AbstractIndividual> newIndividuals) {
        AbstractIndividual child = firstParent.createIndividual(firstParent);
        for (int j = 0; j < child.getChromosomeLength(); j++) {
            child.setGene(j, rand.nextBoolean() ? firstParent.getValueOfGene(j) : secondParent.getValueOfGene(j));
        }
        newIndividuals.add(child);
    }

    /**
     * Crosses over two Individuals (parents) and makes one new Individual (child)
     * 
     * @param firstParent
     *            - first parent
     * @param secondParent
     *            - second parent
     * @param newIndividuals
     *            - list of new Individuals
     */
    public void weightedUniformCrossingOver(AbstractIndividual firstParent, AbstractIndividual secondParent, LinkedList<AbstractIndividual> newIndividuals) {
        AbstractIndividual child = firstParent.createIndividual(firstParent);
        double ratio = firstParent.getFitness() / (firstParent.getFitness() + secondParent.getFitness());
        for (int j = 0; j < child.getChromosomeLength(); j++) {
            child.setGene(j, ratio > rand.nextDouble() ? firstParent.getValueOfGene(j) : secondParent.getValueOfGene(j));
        }
        newIndividuals.add(child);
    }
}
