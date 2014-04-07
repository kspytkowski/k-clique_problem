package genetics;

import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 7 kwi 2014
 */
public class CrossingOver {

	private final double crossingOverProbability; // individuals' crossing-over probability
	private final Random rand = new Random(); // object that generates random numbers

	/**
	 * Constructor
	 * 
	 * @param crossingOverProbability
	 *            - probability of crossing-over
	 */
	public CrossingOver(double crossingOverProbability) {
		this.crossingOverProbability = crossingOverProbability;
	}

	/**
	 * Starts appropriate crossing-over
	 * 
	 * @param crossingOverType
	 *            - type of crossing-over
	 */
	public Population crossOver(CrossingOverType crossingOverType, Population population) {
		// jezeli bedzie nieparzysta liczba individualsow to zwroci populacje o jeden mniejsza
		int amountOfIndividualsToCrossOver = (population.getIndividualsAmount() % 2 == 0) ? population.getIndividualsAmount() : population.getIndividualsAmount() - 1;
		Population newPopulation = new Population(population.getIndividualsAmount(),population.getKCliqueSize());
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
				}

			} else {
				newPopulation.getIndividuals().add(firstParent);
				newPopulation.getIndividuals().add(secondParent);
			}
		}
		return newPopulation;
	}

	// [0,0,0,0,0,0,0] => [0,0,1,1,1,1]
	// [1,1,1,1,1,1,1] => [1,1,0,0,0,0]
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
		for (int j = 0; j < splitPoint; j++) {
			firstChild.setGene(j, firstParent.getValueOfGene(j));
			secondChild.setGene(j, secondParent.getValueOfGene(j));
		}
		for (int j = splitPoint; j < firstChild.getChromosomeLength(); j++) {
			firstChild.setGene(j, secondParent.getValueOfGene(j));
			secondChild.setGene(j, firstParent.getValueOfGene(j));
		}
		population.getIndividuals().add(firstChild);
		population.getIndividuals().add(secondChild);
	}

	// [0,0,0,0,0,0,0] => [0,0,0,1,1,1]
	// [1,1,1,1,1,1,1] =>
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
		for (int j = 0; j < splitPoint; j++) {
			child.setGene(j, firstParent.getValueOfGene(j));
		}
		for (int j = splitPoint; j < child.getChromosomeLength(); j++) {
			child.setGene(j, secondParent.getValueOfGene(j));
		}
		population.getIndividuals().add(child);
	}

	// [0,0,0,0,0,0,0] => [0,1,1,1,0,1]
	// [1,1,1,1,1,1,1] =>
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
			if (rand.nextBoolean()) {
				child.setGene(j, firstParent.getValueOfGene(j));
			} else {
				child.setGene(j, secondParent.getValueOfGene(j));
			}
		}
		population.getIndividuals().add(child);
	}
}
