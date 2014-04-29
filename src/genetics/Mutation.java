package genetics;

import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 7 kwi 2014
 */
public class Mutation {

    /**
     * Makes small mutations among individuals (changes some genes in their chromosome)
     * 
     * @param population
     *            - population
     */
    public static void mutate(Population population, double mutationProbability) {
        Random rand = new Random();
        for (int i = 0; i < population.getActualIndividualsAmount(); i++) {
            if (rand.nextDouble() < mutationProbability) {
                AbstractIndividual ind = population.getIndividual(i);
                ind.mutateGene(rand.nextInt(ind.getChromosomeLength()));
            }
        }
    }
}
