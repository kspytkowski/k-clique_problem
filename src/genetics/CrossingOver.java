package genetics;

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
    public Population crossOver(CrossingOverType crossingOverType, Population population) throws GeneticAlgorithmException {
        // jezeli bedzie nieparzysta liczba individualsow to zwroci populacje o jeden mniejsza => zostawic to tak?!
        int amountOfIndividualsToCrossOver = (population.getActualIndividualsAmount() % 2 == 0) ? population.getActualIndividualsAmount() : population.getActualIndividualsAmount() - 1;
        Population newPopulation = new Population(population.getDemandedIndividualsAmount(), population.getGraphRepresentation(), population.getKCliqueSize());
        for (int i = 0; i < amountOfIndividualsToCrossOver; i = i + 2) {
            Individual firstParent = population.getIndividual(i);
            Individual secondParent = population.getIndividual(i + 1);
            if (crossingOverProbability > rand.nextDouble()) {
                switch (crossingOverType) {
                case ONEPOINTWITHTWOCHILDREN:
                    onePointWithTwoChildrenCrossingOver(firstParent, secondParent, newPopulation);
                    break;
                case ONEPOINTWITHONECHILD:
                    onePointWithOneChildCrossingOver(firstParent, secondParent, newPopulation);
                    break;
                case UNIFORMCROSSOVER:
                    uniformCrossingOver(firstParent, secondParent, newPopulation);
                    break;
                case WEIGHTEDUNIFORMCROSSOVER:
                    weightedUniformCrossingOver(firstParent, secondParent, newPopulation);
                    break;
                }

            } else {
                newPopulation.getIndividuals().add(firstParent);
                newPopulation.getIndividuals().add(secondParent);
            }
        }
        return newPopulation;
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
    public void onePointWithTwoChildrenCrossingOver(Individual firstParent, Individual secondParent, Population population) {
        Individual firstChild = new Individual(firstParent.getChromosomeLength());
        Individual secondChild = new Individual(firstParent.getChromosomeLength());
        int splitPoint = rand.nextInt(firstChild.getChromosomeLength());
        for (int j = 0; j < firstChild.getChromosomeLength(); j++) {
            firstChild.setGene(j, j < splitPoint ? firstParent.getValueOfGene(j) : secondParent.getValueOfGene(j));
            secondChild.setGene(j, j < splitPoint ? secondParent.getValueOfGene(j) : firstParent.getValueOfGene(j));
        }
        population.getIndividuals().add(firstChild);
        population.getIndividuals().add(secondChild);
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
    public void onePointWithOneChildCrossingOver(Individual firstParent, Individual secondParent, Population population) {
        Individual child = new Individual(firstParent.getChromosomeLength());
        int splitPoint = rand.nextInt(child.getChromosomeLength());
        for (int j = 0; j < child.getChromosomeLength(); j++) {
            child.setGene(j, j < splitPoint ? firstParent.getValueOfGene(j) : secondParent.getValueOfGene(j));
        }
        population.getIndividuals().add(child);
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
    public void uniformCrossingOver(Individual firstParent, Individual secondParent, Population population) {
        Individual child = new Individual(firstParent.getChromosomeLength());
        for (int j = 0; j < child.getChromosomeLength(); j++) {
            child.setGene(j, rand.nextBoolean() ? firstParent.getValueOfGene(j) : secondParent.getValueOfGene(j));
        }
        population.getIndividuals().add(child);
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
    public void weightedUniformCrossingOver(Individual firstParent, Individual secondParent, Population population) {
        Individual child = new Individual(firstParent.getChromosomeLength());
        double ratio = firstParent.getFitness() / (firstParent.getFitness() + secondParent.getFitness());
        for (int j = 0; j < child.getChromosomeLength(); j++) {
            child.setGene(j, ratio > rand.nextDouble() ? firstParent.getValueOfGene(j) : secondParent.getValueOfGene(j));
        }
        population.getIndividuals().add(child);
    }
}
