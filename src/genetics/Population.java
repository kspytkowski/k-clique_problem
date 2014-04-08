package genetics;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import edu.uci.ics.jung.graph.Graph;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Population {

	// ponizsze nie moze byc static, bo jak se zrobie nowy graf to i nowa populacje potrzebuje i odwrotnie, napisz to ci wytłumacze :P
	private Graph<Integer, String> graph; // reference to main graph
	private LinkedList<Individual> individuals; // list of individuals
	// zmien nazwe ponizszego na bardziej adekwatna np. bazowa liczba osobnikow... => zrobilem, ze żądana :D
	private int demandedIndividualsAmount; // amount of individuals that SHOULD BE in population => od teraz pokazuje ile POWINNO byc a nie ile jest, ile jest to ind.getSize()
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
	public Population(int individualsAmount, int graphSize, Graph<Integer, String> graph, int kCliqueSize) {
		this.graph = graph;
		this.kCliqueSize = kCliqueSize;
		this.demandedIndividualsAmount = individualsAmount;
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
	public Population(int individualsAmount, Graph<Integer, String> graph, int kCliqueSize) {
		this.graph = graph;
		individuals = new LinkedList<>();
		this.demandedIndividualsAmount = individualsAmount;
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
	 * Keeps constant amount of individuals in population (adds random individuals)
	 */
	public void keepConstantPopulationSize() {
		while (individuals.size() < demandedIndividualsAmount) {
			addIndividual(new Individual(graph.getVertexCount(), kCliqueSize));
		}
	}

	/**
	 * Removes worst individuals from population
	 * 
	 * @param howMuchToRemove
	 *            - shows how many individuals should be removed (in percentage)
	 */
	public void removeWorstIndividuals(double howMuchToRemove) {
		int toRemove = (int)(howMuchToRemove * getActualIndividualsAmount());
		Collections.sort(individuals);
		for(int i = 0; i < toRemove; i++) {
			individuals.removeFirst();
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
	 * Returns actual amount of individuals in population
	 * 
	 * @return amount of individuals
	 */
	public int getActualIndividualsAmount() {
		return individuals.size();
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
	 * @return amount of individuals that should be in population
	 */
	public int getDemandedIndividualsAmount() {
		return demandedIndividualsAmount;
	}

	/**
	 * Getter
	 * 
	 * @return graph
	 */
	public Graph<Integer, String> getMyGraph() {
		return graph;
	}

	/**
	 * Setter
	 * 
	 * @param graph
	 *            - graph to set
	 */
	public void setMyGraph(Graph<Integer, String> graph) {
		this.graph = graph;
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
