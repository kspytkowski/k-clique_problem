package graph;

import java.util.LinkedList;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import exceptions.NoPossibilityToCreateGraphException;

/**
 * @author Krzysztof Spytkowski
 * @date 16 mar 2014
 */
public class GraphFactory {

	private static Random rand = new Random(); // object that generates random numbers

	/**
	 * Creates graph with verticesAmount vertices (without any edge)
	 * 
	 * @param verticesAmount
	 *            - amount of vertices
	 * @return graph
	 */
	private static Graph<Integer, String> createGraphVertices(int verticesAmount) {
		Graph<Integer, String> graph = new SparseGraph<>();
		for (int i = 1; i <= verticesAmount; i++) {
			graph.addVertex((Integer) i);
		}
		return graph;
	}
	
	/**
	 * Adds to graph new edges
	 * 
	 * @param graph
	 *            - graph
	 * @param possibleEdges
	 *            - list of edges that could be add
	 * @param existedEdgesAmount
	 *            - amount of edges that already exists in graph
	 * @param demandedEdgesAmount
	 *            - amount of edges that should be in graph
	 */
	private static void fillGraphWithEdges(Graph<Integer, String> graph, LinkedList<Edge> possibleEdges, int existedEdgesAmount, int demandedEdgesAmount) {
		int randEdge;
		for (int i = existedEdgesAmount + 1; i <= demandedEdgesAmount; i++) {
			randEdge = rand.nextInt(possibleEdges.size());
			graph.addEdge("EDGE" + i, possibleEdges.get(randEdge).getFirstVertex(), possibleEdges.get(randEdge).getSecondVertex());
			possibleEdges.remove(randEdge);
		}
	}

	/**
	 * Creates random sparse graph
	 * 
	 * @param vertices
	 *            - amount of vertices
	 * @param edges
	 *            - amount of edges
	 * @throws NoPossibilityToCreateGraphException
	 */
	public static Graph<Integer, String> createGraph(int vertices, int edges) throws NoPossibilityToCreateGraphException {
		if (vertices < 1) {
			throw new NoPossibilityToCreateGraphException("Amount of vertices cannot be less than 1");
		}
		if (edges < 0) {
			throw new NoPossibilityToCreateGraphException("Amount of edges cannot be less than 0");
		}
		if (edges > (vertices * (vertices - 1) / 2)) {
			throw new NoPossibilityToCreateGraphException("To many edges to generate graph");
		}
		Graph<Integer, String> graph = createGraphVertices(vertices);
		LinkedList<Edge> edgesList = new LinkedList<>();
		for (int i = 1; i <= vertices; i++) {
			for (int j = i + 1; j <= vertices; j++) {
				edgesList.add(new Edge(i, j));
			}
		}
		fillGraphWithEdges(graph, edgesList, 0, edges);
		return graph;
	}

	/**
	 * Creates graph that has K-Clique (minimum size of K-Clique is kCliqueSize)
	 * 
	 * @param kCliqueSize
	 *            - size of K-clique (amount of vertices)
	 * @param vertices
	 *            - amount of vertices in graph
	 * @param edges
	 *            - amount of edges in graph
	 * @return graph
	 * @throws NoPossibilityToCreateGraphException
	 */
	public static Graph<Integer, String> createGraph2(int kCliqueSize, int vertices, int edges) throws NoPossibilityToCreateGraphException {
		// kiedys rzuce wyjatki
		Graph<Integer, String> graph = createGraphVertices(vertices);
		for (int i = 1, k = 1; i <= kCliqueSize; i++) {
			for (int j = i + 1; j <= kCliqueSize; j++, k++) {
				graph.addEdge("EDGE" + k, i, j);
			}
		}
		LinkedList<Edge> edgesList = new LinkedList<>();
		for (int i = 1; i <= vertices; i++) {
			for (int j = kCliqueSize + 1; j <= vertices; j++) {
				edgesList.add(new Edge(i, j));
			}
		}
		int existedEdgesAmount = kCliqueSize * (kCliqueSize - 1) / 2;
		fillGraphWithEdges(graph, edgesList, existedEdgesAmount, edges);
		return graph;
	}
}