package graph;

/**
 * @author Krzysztof Spytkowski
 * @date 16 mar 2014
 */
public class Edge {
	private Vertex first; // first vertex of edge
	private Vertex second; // second vertex of edge

	/**
	 * Constructor
	 * 
	 * @param first
	 *            - first vertex
	 * @param second
	 *            - second vertex
	 */
	public Edge(Vertex first, Vertex second) {
		this.first = first;
		this.second = second;
	}
	
	/**
	 * Getter
	 * 
	 * @return first vertex
	 */
	public Vertex getFirstVertex() {
		return first;
	}
	
	/**
	 * Getter
	 * 
	 * @return second vertex
	 */
	public Vertex getSecondVertex() {
		return second;
	}
}
