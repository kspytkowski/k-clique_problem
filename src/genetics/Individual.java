package genetics;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Individual implements Comparable<Individual> {

	private final byte[] chromosome; // table of subgraph's vertices (0 - not exists, 1 - exists)
	private int activeGenesAmount; // amount of vertices in Individual
	private double fitness; // shows how well individual is adopted in population

	/**
	 * Constructor - creates subgraph that has size of kCliqueSize - chooses appropriate amount of genes (vertices) and puts them into chromosome (table)
	 * 
	 * @param graphSize
	 *            - graph's size (amount of vertices)
	 * @param kCliqueSize
	 *            - k-clique size (amount of vertices)
	 */
	public Individual(int graphSize, int kCliqueSize) {
		// TO DO! wyjatek! subgraph nie moze byc > niz graph
		// a na cholere ci wyjątek, zrob zwykle zabezpieczenia.
		this.activeGenesAmount = kCliqueSize;
		chromosome = new byte[graphSize];
		LinkedList<Integer> helpList = new LinkedList<>();
		for (int i = 0; i < graphSize; i++) {
			helpList.add(i);
		}
		Random rand = new Random();
		for (int i = 0; i < kCliqueSize; i++) {
			int k = helpList.get(rand.nextInt(graphSize - i));
			chromosome[k] = 1;
			helpList.remove((Integer) k);
		}
	}

	/**
	 * Constructor - creates blank Individual (all genes are 0)
	 * 
	 * @param verticesAmount
	 *            - amount of vertices
	 */
	public Individual(int verticesAmount) {
		this.activeGenesAmount = 0;
		this.chromosome = new byte[verticesAmount];
		this.fitness = 0.0;
	}

	/**
	 * Copy constructor
	 * 
	 * @param i
	 *            - individual
	 */
	public Individual(Individual i) {
		this.activeGenesAmount = i.getActiveGenesAmount();
		this.chromosome = i.chromosome.clone();
		this.fitness = i.getFitness();
	}

	/**
	 * Getter
	 * 
	 * @return length of chromosome
	 */
	public int getChromosomeLength() {
		return chromosome.length;
	}

	/**
	 * Getter
	 * 
	 * @return fitness
	 */
	public double getFitness() {
		return fitness;
	}

	/**
	 * Setter
	 * 
	 * @param fitness
	 *            - fitness
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	/**
	 * Removes gene - makes 0 form 1 in chromosome
	 * 
	 * @param geneIndex
	 *            - index of gene to remove
	 */
	public void removeGene(int geneIndex) {
		chromosome[geneIndex] = 0;
		activeGenesAmount--;
	}

	/**
	 * Setter
	 * 
	 * @param geneIndex
	 *            - index of gene
	 * @param value
	 *            - value to set
	 */
	public void setGene(int geneIndex, byte value) {
		if (chromosome[geneIndex] != value && value == 1)
			activeGenesAmount++;
		else if (chromosome[geneIndex] != value && value == 0)
			activeGenesAmount--;
		chromosome[geneIndex] = value;
	}

	/**
	 * Inverses gene - makes 0 form 1 or 1 from 0 in chromosome
	 * 
	 * @param geneIndex
	 *            - index of gene to inverse
	 */
	public void inverseGene(int geneIndex) {
		setGene(geneIndex, (chromosome[geneIndex] == 0) ? (byte) 1 : (byte) 0);
	}

	/**
	 * Getter
	 * 
	 * @param index
	 *            - index of gene
	 * @return value of gene: 0 - not exists, 1 - exists
	 */
	public byte getValueOfGene(int geneIndex) {
		return chromosome[geneIndex];
	}

	/**
	 * Getter
	 * 
	 * @return amount of active genes
	 */
	public int getActiveGenesAmount() {
		return activeGenesAmount;
	}

	/**
	 * Getter
	 * 
	 * @return chromosome - table
	 */
	public byte[] getChromosome() {
		return chromosome;
	}

	/**
	 * Getter
	 * 
	 * @param geneIndex
	 *            - index of gene
	 * @return value of gene
	 */
	public byte getGene(int geneIndex) {
		return chromosome[geneIndex];
	}

	/**
	 * Compares two individuals - better individual has better fitness
	 * 
	 * @param i
	 *            - individual
	 */
	@Override
	public int compareTo(Individual i) {
		if (this.fitness == i.fitness) {
			return 0;
		}
		return (this.fitness > i.fitness) ? 1 : -1;
	}

	@Override
	public String toString() {
		String s = "Individual: ";
		for (int i : chromosome) {
			s += i;
		}
		s += " ";
		s += fitness;
		return s += "\n";
	}
}
