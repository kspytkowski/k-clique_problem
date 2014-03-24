package graph;

/**
 * @author Krzysztof Spytkowski
 * @date 16 mar 2014
 */
public class Edge {
	
	private int first; // first vertex of edge
	private int second; // second vertex of edge

	/**
	 * Constructor
	 * 
	 * @param first
	 *            - first vertex
	 * @param second
	 *            - second vertex
	 */
	public Edge(int first, int second) {
		this.first = first;
		this.second = second;
	}
	
	/**
	 * Getter
	 * 
	 * @return first vertex
	 */
	public int getFirstVertex() {
		return first;
	}
	
	/**
	 * Getter
	 * 
	 * @return second vertex
	 */
	public int getSecondVertex() {
		return second;
	}
	
}
