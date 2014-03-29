package genetics;

import java.util.LinkedList;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Population {

	private LinkedList<Individual> individuals; // list of individuals

	/**
	 * Constructor
	 * 
	 * @param individualAmount
	 *            - initially amount of individuals
	 * @param graphSize
	 *            - graph's size (amount of vertices)
	 * @param subGraphSize
	 *            - k-clique size (amount of vertices)
	 */
	public Population(int individualAmount, int graphSize, int subGraphSize) {
		individuals = new LinkedList<>();
		for(int i = 0; i < individualAmount; i++)
			individuals.add(new Individual(graphSize, subGraphSize));
	}
}
