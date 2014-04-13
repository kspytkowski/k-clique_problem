package genetics;

import java.util.Random;

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
     */
    public Mutation(double mutationProbability) {
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
     */
    public void setMutationProbability(double mutationProbability) {
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
