package genetics;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Individual {

	private byte t[]; // table of subgraph's vertices (0 - not exists, 1 - exists)
	private int size; // size of table with vertices

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
			
			/*
			 * PRZEMYSL to krzysztof!
			 * 
			 * 		int indexOfFirstParent = numbers.get(rand.nextInt(numbers.size()));
		numbers.remove(indexOfFirstParent);
		int indexOfSecondParent = numbers.get(rand.nextInt(numbers.size()));
			 * 
			 * 
			 */
			
			int j = rand.nextInt(graphSize - i);
			int k = helpList.get(j);
			t[k] = 1;
			helpList.remove(j);
		}
	}

	/**
	 * Adds vertex (0 => 1)
	 * 
	 * @param index
	 *            - number of vertex to add
	 */
	public void addVertex(int index) {
		t[index] = 1;
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
	public String toString() {
		String s = new String("Osobik: ");
		for (int i =0; i < size; i++) {
			s += t[i];
		}
		s += "\n";
		return s;
	}
}
