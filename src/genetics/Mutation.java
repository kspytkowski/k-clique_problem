package genetics;

import java.util.Random;

import exceptions.GeneticAlgorithmException;

/**
 * @author Krzysztof Spytkowski
 * @date 7 kwi 2014
 */
public class Mutation {

    private double mutationProbability; // individuals' mutation probability

    /**
     * Constructor
     * 
     * @param mutationProbability
     *            - probability of mutation
     * @throws GeneticAlgorithmException
     */
    public Mutation(double mutationProbability) throws GeneticAlgorithmException {
        if (mutationProbability < 0 || mutationProbability > 1) {
            throw new GeneticAlgorithmException("Probability of mutation should be more than 0 and less than 1");
        }
        this.mutationProbability = mutationProbability;
    }

    /**
     * Getter
     * 
     * @return individuals' mutation probability
     */
    public double getMutationProbability() {
        return mutationProbability;
    }

    /**
     * Setter
     * 
     * @param mutationProbability
     *            - individuals' mutation probability
     * @throws GeneticAlgorithmException
     */
    public void setMutationProbability(double mutationProbability) throws GeneticAlgorithmException {
        if (mutationProbability < 0 || mutationProbability > 1) {
            throw new GeneticAlgorithmException("Probability of mutation should be more than 0 and less than 1");
        }
        this.mutationProbability = mutationProbability;
    }

    /**
     * Makes small mutations among individuals (changes some genes in their chromosome)
     * 
     * @param population
     *            - population
     */
    public void mutate(Population population) {
        Random rand = new Random();
        for (int i = 0; i < population.getActualIndividualsAmount(); i++) {
            if (rand.nextDouble() < mutationProbability) {
                Individual ind = population.getIndividual(i);
                ind.inverseGene(rand.nextInt(ind.getChromosomeLength()));
            }
        }
    }
}
