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
            throw new GeneticAlgorithmException("Probability of crossing-over should be more than 0 and less than 1");
        }
        this.crossingOverProbability = crossingOverProbability;
    }

    /**
     * Starts appropriate crossing-over
     * 
     * @param crossingOverType
     *            - type of crossing-over
     * @throws GeneticAlgorithmException
     */
    public void crossOver(CrossingOverType crossingOverType, Population population) throws GeneticAlgorithmException {
        // jezeli bedzie nieparzysta liczba individualsow to zwroci populacje o jeden mniejsza => zostawic to tak?!
        int amountOfIndividualsToCrossOver = (population.getActualIndividualsAmount() % 2 == 0) ? population.getActualIndividualsAmount() : population.getActualIndividualsAmount() - 1;
        LinkedList<Individual> newIndividualsList = new LinkedList<>();
        for (int i = 0; i < amountOfIndividualsToCrossOver; i = i + 2) {
            Individual firstParent = population.getIndividual(i);
            Individual secondParent = population.getIndividual(i + 1);
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
    public void onePointWithTwoChildrenCrossingOver(Individual firstParent, Individual secondParent, LinkedList<Individual> newIndividuals) {
        Individual firstChild = new Individual(firstParent.getChromosomeLength());
        Individual secondChild = new Individual(firstParent.getChromosomeLength());
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
    public void onePointWithOneChildCrossingOver(Individual firstParent, Individual secondParent, LinkedList<Individual> newIndividuals) {
        Individual child = new Individual(firstParent.getChromosomeLength());
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
    public void uniformCrossingOver(Individual firstParent, Individual secondParent, LinkedList<Individual> newIndividuals) {
        Individual child = new Individual(firstParent.getChromosomeLength());
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
    public void weightedUniformCrossingOver(Individual firstParent, Individual secondParent, LinkedList<Individual> newIndividuals) {
        Individual child = new Individual(firstParent.getChromosomeLength());
        double ratio = firstParent.getFitness() / (firstParent.getFitness() + secondParent.getFitness());
        for (int j = 0; j < child.getChromosomeLength(); j++) {
            child.setGene(j, ratio > rand.nextDouble() ? firstParent.getValueOfGene(j) : secondParent.getValueOfGene(j));
        }
        newIndividuals.add(child);
    }
}
