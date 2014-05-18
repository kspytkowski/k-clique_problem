/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
package genetics;

import java.util.Random;

public class Mutation {

    /**
     * Makes small mutations among individuals (changes some genes in their
     * chromosome)
     *
     * @param population - population
     * @param mutationProbability - probability that individual will mutate
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
