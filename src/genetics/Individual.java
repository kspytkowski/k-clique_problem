package genetics;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Individual implements Comparable<Individual> {

	private final byte t[]; // table of subgraph's vertices (0 - not exists, 1 - exists)
	private final int size; // size of table with vertices
	private double rating; // shows how well individual is adopted in population (0 - 1)

	/**
	 * Constructor
	 * 
	 * @param graphSize
	 *            - size of graph
	 */
	public Individual(int graphSize) {
		this.size = graphSize;
		t = new byte[graphSize];
	}

	/**
	 * Constructor
	 * 
	 * @param graphSize
	 *            - graph's size (amount of vertices)
	 * @param subGraphSize
	 *            - k-clique size (amount of vertices)
	 */
	public Individual(int graphSize, int subGraphSize) {
		this.size = graphSize;
		t = new byte[graphSize];
		Random rand = new Random();
		LinkedList<Integer> helpList = new LinkedList<>();
		for (int i = 0; i < graphSize; i++) {
			helpList.add(i);
		}
		for (int i = 0; i < subGraphSize; i++) {
			int k = helpList.get(rand.nextInt(graphSize - i));
			t[k] = 1;
			helpList.remove((Integer) k); // musi byc rzutowanie, bo chce usunac obiekt, a nie cos o indexie k
		}
	}

	/**
	 * Getter
	 * 
	 * @return rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * Setter
	 * 
	 * @param rating
	 *            - rating
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

	/**
	 * Remove vertex (1 => 0)
	 * 
	 * @param index
	 *            - number of vertex to remove
	 */
	public void removeVertex(int index) {
		t[index] = 0;
	}

	/**
	 * Sets vertex
	 * 
	 * @param index
	 *            - index of vertex
	 * @param value
	 *            - value to set
	 */
	public void setVertex(int index, byte value) {
		t[index] = value;
	}

	/**
	 * Inverses (0 => 1, 1 => 0)
	 * 
	 * @param index
	 *            - index of part to inverse
	 */
	public void inversePartOfGene(int index) {
		if (t[index] == 0)
			setVertex(index, (byte) 1);
		else
			setVertex(index, (byte) 0);
	}

	/**
	 * Getter
	 * 
	 * @param index
	 *            - vertex index
	 * @return value of vertex
	 */
	public byte getValueOfVertex(int index) {
		return t[index];
	}

	/**
	 * Getter
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * To REMOVE!
	 */
	@Override
	public String toString() {
		String s = "Osobnik: ";
		for (int i = 0; i < size; i++) {
			s += t[i];
		}
		s += " ";
		s += rating;
		s += "\n";
		return s;
	}

	/**
	 * Getter
	 * 
	 * @return table of subgraph's vertices
	 */
	public byte[] getT() {
		return t;
	}

	/**
	 * Na pewno poprawnie?! Potem sie sprawdzi :D
	 */
	@Override
	public int compareTo(Individual i) {
		if (this.rating > i.rating)
			return 1;
		else if (rating < i.rating)
			return -1;
		return 0;
	}

}
