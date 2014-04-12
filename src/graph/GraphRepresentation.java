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
public class GraphRepresentation {

	private static final Random rand = new Random(); // object that generates random numbers
	private final Graph<Integer, String> graph; // graph
	private final int kCliqueSize; // size of K-Clique

	/**
	 * Constructor - creates random sparse graph
	 * 
	 * @param vertices
	 *            - amount of vertices
	 * @param edges
	 *            - amount of edges
	 * @param kCliqueSize
	 *            - k-clique size (amount of vertices)
	 * @throws NoPossibilityToCreateGraphException
	 */
	public GraphRepresentation(int vertices, int edges, int kCliqueSize, boolean shouldBeKClique) throws NoPossibilityToCreateGraphException {
		if (vertices < 1) {
			throw new NoPossibilityToCreateGraphException("Amount of vertices cannot be less than 1");
		}
		if (edges < 0) {
			throw new NoPossibilityToCreateGraphException("Amount of edges cannot be less than 0");
		}
		if (edges > (vertices * (vertices - 1) / 2)) {
			throw new NoPossibilityToCreateGraphException("To many edges to generate graph");
		}
		this.kCliqueSize = kCliqueSize;
		graph = createGraphVertices(vertices);
		if (shouldBeKClique) {
			LinkedList<Edge> edgesList = createListWithPossibleEdges(kCliqueSize);
			int kCliqueEgdesAmount = kCliqueSize * (kCliqueSize - 1) / 2;
			fillGraphWithEdges(graph, edgesList, 0, kCliqueEgdesAmount);
			edgesList = new LinkedList<>();
			for (int i = 1; i <= vertices; i++) {
				for (int j = kCliqueSize + 1; j <= vertices; j++) {
					if (i != j && i < j) {
						edgesList.add(new Edge(i, j));
					}
				}
			}
			fillGraphWithEdges(graph, edgesList, kCliqueEgdesAmount, edges);
		} else {
			LinkedList<Edge> edgesList = createListWithPossibleEdges(vertices);
			fillGraphWithEdges(graph, edgesList, 0, edges);
		}
	}

	/**
	 * Getter
	 * 
	 * @return graph
	 */
	public Graph<Integer, String> getGraph() {
		return graph;
	}

	/**
	 * Getter
	 * 
	 * @return k-clique size
	 */
	public int getKCliqueSize() {
		return kCliqueSize;
	}

	/**
	 * Getter
	 * 
	 * @return graph vertex amount
	 */
	public int getVertexCount() {
		return graph.getVertexCount();
	}

	/**
	 * Checks if first and second vertices are connected by edge
	 * 
	 * @param firstVertex
	 *            - first vertex
	 * @param secondVertex
	 *            - second vertex
	 * @return true if between vertices is edge, false otherwise
	 */
	public boolean isNeighbor(int firstVertex, int secondVertex) {
		return graph.isNeighbor(firstVertex, secondVertex);
	}

	/**
	 * Creates graph with verticesAmount vertices (without any edge)
	 * 
	 * @param verticesAmount
	 *            - amount of vertices
	 * @return graph
	 */
	private Graph<Integer, String> createGraphVertices(int verticesAmount) {
		Graph<Integer, String> tempGraph = new SparseGraph<>();
		for (int i = 1; i <= verticesAmount; i++) {
			tempGraph.addVertex((Integer) i);
		}
		return tempGraph;
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
	private void fillGraphWithEdges(Graph<Integer, String> graph, LinkedList<Edge> possibleEdges, int existedEdgesAmount, int demandedEdgesAmount) {
		for (int i = existedEdgesAmount + 1; i <= demandedEdgesAmount; i++) {
			int randEdge = rand.nextInt(possibleEdges.size());
			graph.addEdge("EDGE" + i, possibleEdges.get(randEdge).getFirstVertex(), possibleEdges.get(randEdge).getSecondVertex());
			possibleEdges.remove(randEdge);
		}
	}

	/**
	 * Creates list of all possible edges that can be added to graph
	 * 
	 * @param verticesAmount
	 *            - amount of vertices
	 * @return list of edges
	 */
	private LinkedList<Edge> createListWithPossibleEdges(int verticesAmount) {
		LinkedList<Edge> edgesList = new LinkedList<>();
		for (int i = 1; i <= verticesAmount; i++) {
			for (int j = i + 1; j <= verticesAmount; j++) {
				edgesList.add(new Edge(i, j));
			}
		}
		return edgesList;
	}

	@Override
	public String toString() {
		return graph.toString();
	}
}
