package graph;

import java.util.LinkedList;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

/**
 * @author Krzysztof Spytkowski
 * @date 16 mar 2014
 */
public class MyGraph {
	
	public Graph<Integer, String> getRandomGraph(int vertices, int edges) {
		
		Graph<Integer, String> g = new SparseGraph<Integer, String>();
		for(int i = 1; i <= vertices; i++) {
			g.addVertex((Integer)i);
		}
		    
		LinkedList<Edge> edgesList = new LinkedList<Edge>();    
		for(int i = 1; i <= vertices; i++) {
		    for(int j = 1; j <= vertices; j++) {
		    	if (i != j)
		    		edgesList.add(new Edge(i,j));
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
