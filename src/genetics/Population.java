package genetics;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import graph.GraphRepresentation;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Population {

	private GraphRepresentation graph; // main graph
	private final LinkedList<Individual> individuals; // list of individuals
	private final int demandedIndividualsAmount; // amount of individuals that SHOULD BE in population => od teraz pokazuje ile POWINNO byc a nie ile jest, ile jest to ind.getSize()

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
	public Population(int individualsAmount, int graphSize, GraphRepresentation graph, int kCliqueSize) {
		// populacja musi miec individualsAmount > 1
		this.graph = graph;
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
	public Population(int individualsAmount, GraphRepresentation graph, int kCliqueSize) {
		this.graph = graph;
		individuals = new LinkedList<>();
		this.demandedIndividualsAmount = individualsAmount;
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
		return graph.getKCliqueSize();
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
	public GraphRepresentation getGraphRepresentation() {
		return graph;
	}

	/**
	 * Setter
	 * 
	 * @param graph
	 *            - graph to set
	 */
	public void setGraphRepresentation(GraphRepresentation graph) {
		this.graph = graph;
	}

	/**
	 * Counts individuals' fitness sum
	 * 
	 * @return individuals' fitness sum
	 */
	public double fitnessSum() {
		double sum = 0;
		Iterator<Individual> individualsIterator = individuals.iterator();
		while (individualsIterator.hasNext()) {
			sum += individualsIterator.next().getFitness();
		}
		return sum;
	}

	/**
	 * Keeps constant amount of individuals in population (adds random individuals)
	 */
	public void keepConstantPopulationSize() {
		while (individuals.size() < demandedIndividualsAmount) {
			addIndividual(new Individual(graph.getVertexCount(), graph.getKCliqueSize()));
		}
	}

	/**
	 * Removes worst individuals from population
	 * 
	 * @param howMuchToRemove
	 *            - shows how many individuals should be removed (in percentage)
	 */
	public void removeWorstIndividuals(double howMuchToRemove) {
		int toRemove = (int) (howMuchToRemove * getActualIndividualsAmount());
		Collections.sort(individuals);
		for (int i = 0; i < toRemove; i++) {
			individuals.removeFirst();
		}
	}

	/**
	 * Adds individual to population
	 * 
	 * @param i
	 *            - individual
	 */
	public void addIndividual(Individual i) { // w losowe miejsce => teraz dziala zbieznosc :D
		Random rand = new Random();
		individuals.add(rand.nextInt(getActualIndividualsAmount() + 1), i);
	}

	/**
	 * Returns actual amount of individuals in population
	 * 
	 * @return amount of individuals
	 */
	public int getActualIndividualsAmount() {
		return individuals.size();
	}

	@Override
	public String toString() {
		String s = "Population: \n";
		for (Individual ind : individuals) {
			s += ind;
		}
		return s;
	}

	// tylko do pomocy, trzeba cos konkretnego napisac...
	public void dostosowanie() {
		Iterator<Individual> it = individuals.iterator();
		while (it.hasNext()) {
			Individual ind = it.next();
			int lol = 0;
			for (int i = 0; i < graph.getVertexCount(); i++) {
				if (ind.getChromosome()[i] == 1) {
					for (int k = i + 1; k <= graph.getVertexCount(); k++) {
						// System.out.println(graph.getVertexCount());
						if (graph.isNeighbor(i + 1, k) && ind.getChromosome()[k - 1] == 1) {
							lol += 1;
						}
					}
				}
			}
			double lol2;

			double czyJestKKlika = 0.0;

			if (ind.getActiveGenesAmount() != 0 && ind.getActiveGenesAmount() != 1) {
				czyJestKKlika = (double) lol / ((ind.getActiveGenesAmount() * (ind.getActiveGenesAmount() - 1) / 2));
			}
			// if (ind.getActiveGenesAmount() == kCliqueSize)
			// lol2 = czyJestKKlika / (Math.abs(kCliqueSize - ind.getActiveGenesAmount()) + 1);
			// lol2 = czyJestKKlika / (Math.abs(kCliqueSize - ind.getActiveGenesAmount()) - 1 + 2.73);
			// else
			lol2 = czyJestKKlika;
			ind.setFitness(lol2);

			if (czyJestKKlika == 1 && ind.getActiveGenesAmount() >= graph.getKCliqueSize()) {
				// System.out.println("-----POPULACJA-----");
				// System.out.println(this);
				System.out.println("-----K-KLIKA-----");
				System.out.println(ind);
				System.exit(0);

			}
		}

	}

	// tylko do pomocy, trzeba cos konkretnego napisac...
	public void printDostatosowanie() {
		// tylko w celach testowych, zeby sie w mainie pokazalo
		double populationFitnessSum = 0;
		Iterator<Individual> individualsIterator = getIndividuals().iterator();
		while (individualsIterator.hasNext()) {
			populationFitnessSum += individualsIterator.next().getFitness();
		}
		System.out.println(populationFitnessSum);
	}

	// tylko do pomocy, trzeba cos konkretnego napisac...
	public void napraw() {
		Random rand = new Random();
		for (int i = 0; i < getActualIndividualsAmount(); i++) {
			Individual ind = getIndividual(i);
			if (ind.getActiveGenesAmount() > graph.getKCliqueSize()) {

				Map<Integer, Integer> cosik = new HashMap<>();

				// LinkedList<Integer> genesIndexes = new LinkedList<>();
				for (int j = 0; j < ind.getChromosomeLength(); j++) {
					if (ind.getGene(j) == 1) {
						// genesIndexes.add(j);
						int lol = 0;
						for (int ii = 0; ii < graph.getVertexCount(); ii++) {
							if (ind.getChromosome()[ii] == 1) {
								for (int k = ii + 1; k <= graph.getVertexCount(); k++) {
									// System.out.println(graph.getVertexCount());
									if (graph.isNeighbor(ii + 1, k) && ind.getChromosome()[k - 1] == 1) {
										lol += 1;
									}
								}
							}
						}
						cosik.put(j, lol);
					}
				}

				while (ind.getActiveGenesAmount() > graph.getKCliqueSize()) {
					// int index = rand.nextInt(genesIndexes.size());
					// ind.removeGene(genesIndexes.get(index));
					// genesIndexes.remove((Integer) index);
				}
			} else {
				LinkedList<Integer> genesIndexes = new LinkedList<>();
				for (int j = 0; j < ind.getChromosomeLength(); j++) {
					if (ind.getGene(j) == 0) {
						genesIndexes.add(j);
					}
				}
				while (ind.getActiveGenesAmount() < graph.getKCliqueSize()) {
					int index = rand.nextInt(genesIndexes.size());
					ind.addGene(genesIndexes.get(index));
					genesIndexes.remove((Integer) index);
				}
			}
		}
	}
}
