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

    /**
     * Creates random sparse graph
     *
     * @param vertices - amount of verticles
     * @param edges - amount of edges
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

        Graph<Integer, String> g = new SparseGraph<>();

        for (int i = 1; i <= vertices; i++) {
            g.addVertex((Integer) i);
        }

        LinkedList<Edge> edgesList = new LinkedList<>();
        for (int i = 1; i <= vertices; i++) {
            for (int j = i + 1; j <= vertices; j++) {
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
        return g;
    }

}
