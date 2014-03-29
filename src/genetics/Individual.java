package genetics;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Individual {

	private byte t[]; // table of subgraph's vertices

	/**
	 * Constructor
	 * 
	 * @param graphSize
	 *            - graph's size (amount of vertices)
	 * @param subGraphSize
	 *            - k-clique size (amount of vertices)
	 */
	public Individual(int graphSize, int subGraphSize) {
		t = new byte[graphSize];

		Random rand = new Random();
		LinkedList<Integer> helpList = new LinkedList<>();
		for (int i = 0; i < graphSize; i++) {
			helpList.add(i);
		}

		for (int i = 0; i < subGraphSize; i++) {
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
	 * To REMOVE!
	 */
	public String toString() {
		return "aa" + t[0] + t[1] + t[2] + t[3] + t[4] + t[5];
	}
}
