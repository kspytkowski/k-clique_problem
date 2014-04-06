package genetics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Population {

	private static Graph<Integer, String> graph; // reference to main graph
	private LinkedList<Individual> individuals; // list of individuals
	private int individualsAmount; // amount of individuals in population
	private final double crossingOverProbability; // individuals' crossing-over probability
	private final double mutationProbability; // individuals' mutation probability
	private final Random rand = new Random(); // object that generates random numbers
	private final int kCliqueSize; // size of K-Clique

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
	 *            - individuals' crossing-over probability
	 * @param mutationProbability
	 *            - individuals' mutation probability
	 */
	public Population(int individualsAmount, int graphSize, int subGraphSize, double interbreedingProbability, double mutationProbability) {
		this.kCliqueSize = subGraphSize;
		this.individualsAmount = individualsAmount;
		this.crossingOverProbability = interbreedingProbability;
		this.mutationProbability = mutationProbability;
		individuals = new LinkedList<>();
		for (int i = 0; i < individualsAmount; i++) {
			individuals.add(new Individual(graphSize, subGraphSize));
		}
	}

	/**
	 * Selects parents according to their fitness. The better the chromosomes are, the more chances to be selected they have.
	 */
	public void rouletteWheelSelection() {
		double populationRatingSum = 0;
		Iterator<Individual> individualsIterator = individuals.iterator();
		while (individualsIterator.hasNext()) {
			populationRatingSum += individualsIterator.next().getFitness();
		}
		LinkedList<Double> rouletteWheel = new LinkedList<>();
		individualsIterator = individuals.iterator();
		double lastRating = 0;
		while (individualsIterator.hasNext()) {
			lastRating += individualsIterator.next().getFitness() / populationRatingSum;
			rouletteWheel.add(lastRating);
		}
		System.out.println(populationRatingSum); // zeby sie w Main'ie pokazalo ;)
		LinkedList<Individual> individualsParents = new LinkedList<>();
		individualsIterator = individuals.iterator();
		while (individualsIterator.hasNext()) {
			int i = 0;
			double actualRouletteWheelPoint = rand.nextDouble();
			Iterator<Double> rouletteWheelIterator = rouletteWheel.iterator();
			while (rouletteWheelIterator.next() < actualRouletteWheelPoint) {
				i++;
			}
			individualsParents.add(individuals.get(i)); // populacja rodzicow, Individualse moga sie powtarzac
			individualsIterator.next();
		}
		individuals = individualsParents; // populacja rodzicow zastepuje dotychczasowa populacje
	}

	// tylko do pomocy, trzeba cos konkretnego napisac...
	public void dostosowanie() {
		// POLICZY ILE JEST KRAWEDZI MIEDZY WIERZCHOLKAMI W OSOBNIKU
		// tu ustawimy rating osobnikom, tylko dla sprawdzenia poprawnosci ruletki...
		Iterator<Individual> it = individuals.iterator();
		while (it.hasNext()) {
			Individual ind = it.next();
			int lol = 0;
			for (int i = 0; i < graph.getVertexCount(); i++) {
				if (ind.getVertices()[i] == 1) {
					for (int k = i + 1; k <= graph.getVertexCount(); k++) {
						if (graph.isNeighbor(i + 1, k) && ind.getVertices()[k - 1] == 1)
							lol += 1;
					}
				}
			}
			double lol2;
			
			double czyJestKKlika = 0.0;
					
			if (ind.getVerticesAmount() != 0 && ind.getVerticesAmount() != 1)
				czyJestKKlika = (double) lol / ((ind.getVerticesAmount() * (ind.getVerticesAmount() - 1) / 2));
			if (ind.getVerticesAmount() != kCliqueSize)
				lol2 = czyJestKKlika * kCliqueSize / (Math.abs(kCliqueSize - ind.getVerticesAmount()) + kCliqueSize);
			else
				lol2 = czyJestKKlika;
			ind.setFitness(lol2);
			
			if (czyJestKKlika == 1 && ind.getVerticesAmount() == kCliqueSize) {
				System.out.println("-----POPULACJA-----");
				System.out.println(this);
				System.out.println("-----K-KLIKA-----");
				System.out.println(ind);
				System.exit(0);

			}
		}

	}

	/**
	 * Starts appropriate crossing-over
	 * 
	 * @param crossingOverType
	 *            - type of crossing-over
	 */
	public void initializeCrossingOver(CrossingOverType crossingOverType) {
		int amountOfIndividualsToCrossOver = (individualsAmount % 2 == 0) ? individualsAmount : individualsAmount - 1;
		LinkedList<Individual> newIndividuals = new LinkedList<>();
		for (int i = 0; i < amountOfIndividualsToCrossOver; i = i + 2) {
			Individual firstParent = individuals.get(i);
			Individual secondParent = individuals.get(i + 1);
			if (crossingOverProbability > rand.nextDouble()) {
				switch (crossingOverType) {
				case ONEPOINTWITHTWOCHILDREN:
					onePointWithTwoChildrenCrossingOver(firstParent, secondParent, newIndividuals);
					break;
				case ONEPOINTWITHONECHILD:
					onePointWithOneChildCrossingOver(firstParent, secondParent, newIndividuals);
					break;
				case UNIFORMCROSSOVER:
					uniformCrossingOver(firstParent, secondParent, newIndividuals);
					break;
				}

			} else {
				newIndividuals.add(firstParent);
				newIndividuals.add(secondParent);
			}
		}
		individuals = newIndividuals;
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
	public void onePointWithTwoChildrenCrossingOver(Individual firstParent, Individual secondParent, LinkedList<Individual> newIndividuals) {
		Individual firstChild = new Individual(firstParent);
		Individual secondChild = new Individual(secondParent);
		int splitPoint = rand.nextInt(graph.getVertexCount()); // punkt przeciecia  chromosomu 
		for (int j = 0; j < splitPoint; j++) {
			firstChild.setVertex(j, firstParent.getValueOfVertex(j));
			secondChild.setVertex(j, secondParent.getValueOfVertex(j));
		}
		for (int j = splitPoint; j < graph.getVertexCount(); j++) {
			firstChild.setVertex(j, secondParent.getValueOfVertex(j));
			secondChild.setVertex(j, firstParent.getValueOfVertex(j));
		}
		newIndividuals.add(firstChild);
		newIndividuals.addLast(secondChild);
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
	 *            - list of new Individulas
	 */
	public void onePointWithOneChildCrossingOver(Individual firstParent, Individual secondParent, LinkedList<Individual> newIndividuals) {
		Individual child = new Individual(firstParent);
		int splitPoint = rand.nextInt(graph.getVertexCount());
		for (int j = 0; j < splitPoint; j++) {
			child.setVertex(j, firstParent.getValueOfVertex(j));
		}
		for (int j = splitPoint; j < graph.getVertexCount(); j++) {
			child.setVertex(j, secondParent.getValueOfVertex(j));
		}
		newIndividuals.add(child);
		individualsAmount--;
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
	 *            - list of new Individulas
	 */
	public void uniformCrossingOver(Individual firstParent, Individual secondParent, LinkedList<Individual> newIndividuals) {
		Individual child = new Individual(firstParent);
		for (int j = 0; j < graph.getVertexCount(); j++) {
			if (rand.nextBoolean()) {
				child.setVertex(j, firstParent.getValueOfVertex(j));
			} else {
				child.setVertex(j, secondParent.getValueOfVertex(j));
			}
		}
		newIndividuals.add(child);
		individualsAmount--;
	}

	/**
	 * Makes small mutations among individuals in population
	 */
	public void mutate() {
		for (int i = 0; i < individualsAmount; i++) { // mutuj - zmien jeden gen chromosomu (0 => 1 lub 1 => 0)
			if (rand.nextDouble() < mutationProbability) {
				Individual ind = individuals.get(i);
				int positionInGeneToChange = rand.nextInt(graph.getVertexCount());
				ind.inverseVertex(positionInGeneToChange);
			}
		}
	}

	@Override
	public String toString() {
		String s = "Populacja: \n";
		for (Individual ind : individuals) {
			s += ind;
		}
		return s;
	}

	/**
	 * Getter
	 * 
	 * @return graph
	 */
	public static Graph<Integer, String> getMyGraph() {
		return graph;
	}

	/**
	 * Setter
	 * 
	 * @param graph
	 *            - graph to set
	 */
	public static void setMyGraph(Graph<Integer, String> graph) {
		Population.graph = graph;
	}
}
