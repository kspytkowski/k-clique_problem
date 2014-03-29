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
public class MyGraph {

	public Graph<Integer, String> g; // graph

	/**
	 * Constructor - Creates random sparse graph
	 * 
	 * @param vertices
	 *            - amount of verticles
	 * @param edges
	 *            - amount of edges
	 * @throws NoPossibilityToCreateGraphException
	 */
	public MyGraph(int vertices, int edges) throws NoPossibilityToCreateGraphException {

		if (edges > (vertices * (vertices - 1) / 2))
			throw new NoPossibilityToCreateGraphException("To many edges");

		g = new SparseGraph<>();

		for (int i = 1; i <= vertices; i++) {
			g.addVertex((Integer) i);
		}

		LinkedList<Edge> edgesList = new LinkedList<>();
		for (int i = 1; i <= vertices; i++) {
			for (int j = 1; j <= vertices; j++) {
				if (i != j)
					edgesList.add(new Edge(i, j));
			}
		}

		Random rand = new Random();
		int r;
		for (int i = 1; i <= edges; i++) {
			r = rand.nextInt(edgesList.size());
			g.addEdge("EDGE" + i, edgesList.get(r).getFirstVertex(), edgesList.get(r).getSecondVertex());
			edgesList.remove(r);
		}
	}

	/**
	 * Getter
	 * 
	 * @return graph
	 */
	public Graph<Integer, String> getGraph() {
		return g;
	}

}
