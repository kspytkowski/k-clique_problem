package genetics;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Population {

	private LinkedList<Individual> individuals; // list of individuals
	private int individualsAmount; // amount of individuals in population
	private double interbreedingProbability; // individuals' interbreeding probability
	private double mutationProbability; // individuals' mutation probability

	/**
	 * Constructor
	 * 
	 * @param individualsAmount
	 *            - initially amount of individuals
	 * @param graphSize
	 *            - graph's size (amount of vertices)
	 * @param subGraphSize
	 *            - k-clique size (amount of vertices)
	 * @param interbreedingProbability
	 *            - individuals' interbreeding probability
	 * @param mutationProbability
	 *            - individuals' mutation probability
	 */
	public Population(int individualsAmount, int graphSize, int subGraphSize, double interbreedingProbability, double mutationProbability) {
		this.individualsAmount = individualsAmount;
		this.interbreedingProbability = interbreedingProbability;
		this.mutationProbability = mutationProbability;
		individuals = new LinkedList<>();
		for (int i = 0; i < individualsAmount; i++)
			individuals.add(new Individual(graphSize, subGraphSize));
	}

	// TO DO Krzysztof - zmien nazwy tych funkcji i wydziel wspolne fragmenty :)
	
	/**
	 * Interbreeds two Individuals - parents and makes two new Individuals - children
	 */
	public void interbreeding() {
		Random rand = new Random();

		LinkedList<Integer> numbers = new LinkedList<Integer>();
		for (int i = 0; i < individuals.size(); i++) {
			numbers.add(new Integer(i));
		}

		int indexOfFirstParent = numbers.get(rand.nextInt(numbers.size()));
		numbers.remove(indexOfFirstParent);
		int indexOfSecondParent = numbers.get(rand.nextInt(numbers.size()));

		Individual firstParent = individuals.get(indexOfFirstParent);
		Individual secondParent = individuals.get(indexOfSecondParent);
		Individual firstChild = new Individual(firstParent.getSize());
		Individual secondChild = new Individual(secondParent.getSize());

		int splitPoint = rand.nextInt(firstParent.getSize());
		for (int j = 0; j < splitPoint; j++) {
			firstChild.setVertex(j, firstParent.getValueOfVertex(j));
			secondChild.setVertex(j, secondParent.getValueOfVertex(j));
		}
		for (int j = splitPoint; j < firstParent.getSize(); j++) {
			firstChild.setVertex(j, secondParent.getValueOfVertex(j));
			secondChild.setVertex(j, firstParent.getValueOfVertex(j));
		}
		individuals.remove(firstParent);
		individuals.remove(secondParent);
		individuals.add(firstChild);
		individuals.add(secondChild);
	}

	/**
	 * Interbreeds two Individuals - parents and makes one new Individual - child
	 */
	public void interbreeding2() {
		Random rand = new Random();

		LinkedList<Integer> numbers = new LinkedList<Integer>();
		for (int i = 0; i < individuals.size(); i++) {
			numbers.add(new Integer(i));
		}

		int indexOfFirstParent = numbers.get(rand.nextInt(numbers.size()));
		numbers.remove(indexOfFirstParent);
		int indexOfSecondParent = numbers.get(rand.nextInt(numbers.size()));

		Individual firstParent = individuals.get(indexOfFirstParent);
		Individual secondParent = individuals.get(indexOfSecondParent);

		Individual child = new Individual(firstParent.getSize());

		int splitPoint = rand.nextInt(firstParent.getSize());
		for (int j = 0; j < splitPoint; j++) {
			child.setVertex(j, firstParent.getValueOfVertex(j));
		}
		for (int j = splitPoint; j < firstParent.getSize(); j++) {
			child.setVertex(j, secondParent.getValueOfVertex(j));
		}

		individuals.remove(firstParent);
		individuals.remove(secondParent);
		individuals.add(child);
		individualsAmount--;
	}

	/**
	 * Interbreeds two Individuals - parents and makes one new Individual - child
	 */
	public void interbreeding3() {
		Random rand = new Random();

		LinkedList<Integer> numbers = new LinkedList<Integer>();
		for (int i = 0; i < individuals.size(); i++) {
			numbers.add(new Integer(i));
		}

		int indexOfFirstParent = numbers.get(rand.nextInt(numbers.size()));
		numbers.remove(indexOfFirstParent);
		int indexOfSecondParent = numbers.get(rand.nextInt(numbers.size()));

		Individual firstParent = individuals.get(indexOfFirstParent);
		Individual secondParent = individuals.get(indexOfSecondParent);

		Individual child = new Individual(firstParent.getSize());

		for (int j = 0; j < firstParent.getSize(); j++) {
			boolean choice = rand.nextBoolean();
			if (choice == true)
				child.setVertex(j, firstParent.getValueOfVertex(j));
			else
				child.setVertex(j, secondParent.getValueOfVertex(j));
		}

		individuals.remove(firstParent);
		individuals.remove(secondParent);
		individuals.add(child);
		individualsAmount--;

	}
	
	/**
	 * Makes small mutations among individuals in population
	 */
	public void mutate() {
		int amountOfIndividualsToMutate = (int) mutationProbability * individualsAmount;
		Random rand = new Random();
		for (int i = 0; i < amountOfIndividualsToMutate; i++) {
			Individual ind = individuals.get(rand.nextInt(individualsAmount));
			int positionInGeneToChange = rand.nextInt(ind.getSize());
			ind.inversePartOfGene(positionInGeneToChange);
		}
	}

	/**
	 * Getter
	 * 
	 * @param index
	 *            - index of individual
	 * @return individual
	 */
	public Individual getIndividual(int index) {
		return individuals.get(index);
	}

	/**
	 * Adds individual to population
	 * 
	 * @param i
	 *            - individual
	 */
	public void addIndividual(Individual i) {
		individuals.add(i);
	}

	/**
	 * Removes individual from population
	 * 
	 * @param index
	 *            - index of individual
	 */
	public void removeIndividual(int index) {
		individuals.remove(index);
	}

	@Override
	public String toString() {
		String s = new String("Populacja: \n");
		for (int i = 0; i < individuals.size(); i++) {
			s += individuals.get(i).toString();
		}
		return s;
	}
}
