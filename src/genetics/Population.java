package genetics;

import java.util.Iterator;
import java.util.LinkedList;

import edu.uci.ics.jung.graph.Graph;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Population {

	private static Graph<Integer, String> graph; // reference to main graph
	private LinkedList<Individual> individuals; // list of individuals
	private int individualsAmount; // amount of individuals that SHOULD BE in population => od teraz pokazuje ile POWINNO byc a nie ile jest, ile jest to ind.getSize()
	private final int kCliqueSize; // size of K-Clique

	/**
	 * Constructor
	 * 
	 * @param individualsAmount
	 *            - initially amount of individuals
	 * @param graphSize
	 *            - graph's size (amount of vertices)
	 * @param kCliqueSize
	 *            - k-clique size (amount of vertices)
	 */
	public Population(int individualsAmount, int graphSize, int kCliqueSize) {
		this.kCliqueSize = kCliqueSize;
		this.individualsAmount = individualsAmount;
		individuals = new LinkedList<>();
		for (int i = 0; i < individualsAmount; i++) {
			individuals.add(new Individual(graphSize, kCliqueSize));
		}
	}

	/**
	 * Constructor - creates blank new population (without any Individual)
	 * 
	 * @param individualsAmount
	 *            - amount of individuals that population should have
	 * @param kCliqueSize
	 *            - size of k-clique
	 */
	public Population(int individualsAmount, int kCliqueSize) {
		individuals = new LinkedList<>();
		this.individualsAmount = individualsAmount;
		this.kCliqueSize = kCliqueSize;
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
				if (ind.getChromosome()[i] == 1) {
					for (int k = i + 1; k <= graph.getVertexCount(); k++) {
						// System.out.println(ind.getVertices().length);
						if (graph.isNeighbor(i + 1, k) && ind.getChromosome()[k - 1] == 1)
							lol += 1;
					}
				}
			}
			double lol2;

			double czyJestKKlika = 0.0;

			if (ind.getActiveGenesAmount() != 0 && ind.getActiveGenesAmount() != 1)
				czyJestKKlika = (double) lol / ((ind.getActiveGenesAmount() * (ind.getActiveGenesAmount() - 1) / 2));
			if (ind.getActiveGenesAmount() != kCliqueSize)
				lol2 = czyJestKKlika * kCliqueSize / (Math.abs(kCliqueSize - ind.getActiveGenesAmount()) + kCliqueSize);
			else
				lol2 = czyJestKKlika;
			ind.setFitness(lol2);

			if (czyJestKKlika == 1 && ind.getActiveGenesAmount() >= kCliqueSize) {
				System.out.println("-----POPULACJA-----");
				System.out.println(this);
				System.out.println("-----K-KLIKA-----");
				System.out.println(ind);
				System.exit(0);

			}
		}

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
	 * Getter
	 * 
	 * @param index
	 *            - index of individual
	 * @return individual on specified index
	 */
	public Individual getIndividual(int index) {
		return individuals.get(index);
	}

	/**
	 * Getter
	 * 
	 * @return size of k-clique
	 */
	public int getKCliqueSize() {
		return kCliqueSize;
	}

	/**
	 * Getter
	 * 
	 * @return list of individuals
	 */
	public LinkedList<Individual> getIndividuals() {
		return individuals;
	}

	/**
	 * Getter
	 * 
	 * @return amount of individuals in population
	 */
	public int getIndividualsAmount() {
		return individualsAmount;
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

	@Override
	public String toString() {
		String s = "Population: \n";
		for (Individual ind : individuals) {
			s += ind;
		}
		return s;
	}
}
