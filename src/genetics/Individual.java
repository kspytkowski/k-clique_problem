package genetics;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Individual implements Comparable<Individual> {

	private final byte[] vertices; // table of subgraph's vertices (0 - not exists, 1 - exists)
	private int verticesAmount; // amount of vertices in Individual
	private double rating; // shows how well individual is adopted in population

	/**
	 * Copy constructor
	 * 
	 * @param i
	 *            - individual
	 */
	public Individual(Individual i) {
		this.verticesAmount = i.getVerticesAmount();
		this.vertices = i.vertices.clone();
		this.rating = i.getRating();
	}

	/**
	 * Constructor Creates subgraph that has size of subGraphSize - chooses appropriate amount of vertices and puts them into table
	 * 
	 * @param graphSize
	 *            - graph's size (amount of vertices)
	 * @param subGraphSize
	 *            - k-clique size (amount of vertices)
	 */
	public Individual(int graphSize, int subGraphSize) {
		// TO DO! wyjatek! subgraph nie moze byc > niz graph
		// a na cholere ci wyjątek, zrob zwykle zabezpieczenia.
		this.verticesAmount = subGraphSize;
		vertices = new byte[graphSize];
		LinkedList<Integer> helpList = new LinkedList<>();
		for (int i = 0; i < graphSize; i++) {
			helpList.add(i);
		}
		Random rand = new Random();
		for (int i = 0; i < subGraphSize; i++) {
			int k = helpList.get(rand.nextInt(graphSize - i));
			vertices[k] = 1;
			helpList.remove((Integer) k);
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
	 * Removes vertex (1 => 0)
	 * 
	 * @param index
	 *            - number of vertex to remove
	 */
	public void removeVertex(int index) {
		vertices[index] = 0;
		verticesAmount--;
	}

	/**
	 * Setter
	 * 
	 * @param index
	 *            - index of vertex
	 * @param value
	 *            - value to set
	 */
	public void setVertex(int index, byte value) {
		if (vertices[index] != value && value == 1)
			verticesAmount++;
		else if (vertices[index] != value && value == 0)
			verticesAmount--;
		vertices[index] = value;
	}

	/**
	 * Inverses vertex (0 => 1, 1 => 0)
	 * 
	 * @param index
	 *            - index of vertex to inverse
	 */
	public void inverseVertex(int index) { // zmien nazwe tej funkcji
		setVertex(index, (vertices[index] == 0) ? (byte) 1 : (byte) 0);
	}

	/**
	 * Getter
	 * 
	 * @param index
	 *            - index of vertex
	 * @return value of vertex (0 - not exists, 1 - exists)
	 */
	public byte getValueOfVertex(int index) {
		return vertices[index];
	}

	/**
	 * Getter
	 * 
	 * @return amount of vertices
	 */
	public int getVerticesAmount() {
		return verticesAmount;
	}

	/**
	 * To REMOVE!
	 */
	@Override
	public String toString() {
		String s = "Osobnik: ";
		for (int i : vertices) {
			s += i;
		}
		s += " ";
		s += rating;
		return s += "\n";
	}

	/**
	 * Getter
	 * 
	 * @return table of subgraph's vertices
	 */
	public byte[] getVertices() {
		return vertices;
	}

	/**
	 * Getter
	 * 
	 * @param index
	 *            - index of vertex
	 * @return value of vertex
	 */
	public byte getVertex(int index) {
		return vertices[index];
	}

	/**
	 * Compares two individuals (better individual has better rating)
	 */
	@Override
	public int compareTo(Individual i) {
		if (this.rating == i.rating) {
			return 0;
		}
		return (this.rating > i.rating) ? 1 : -1;
	}
}
