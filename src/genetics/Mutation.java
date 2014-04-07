package genetics;

import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 7 kwi 2014
 */
public class Mutation {

	private final double mutationProbability; // individuals' mutation probability

	/**
	 * Mutates some genes in Individual
	 * 
	 * @param mutationProbability
	 */
	public Mutation(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	/**
	 * Makes small mutations among individuals in population
	 * 
	 * @param population
	 *            - population
	 */
	public void mutate(Population population) {
		Random rand = new Random();
		// jak dasz individualsAmount to zobaczysz jak szybko algorytm jest zbiezny, a wiec zly
		for (int i = 0; i < population.getIndividualsAmount(); i++) { // mutuj - zmien jeden gen chromosomu (0 => 1 lub 1 => 0)
			if (rand.nextDouble() < mutationProbability) {
				Individual ind = population.getIndividual(i);
				int positionInChromosomeToChange = rand.nextInt(ind.getChromosomeLength());
				ind.inverseGene(positionInChromosomeToChange);
			}
		}
	}
}
