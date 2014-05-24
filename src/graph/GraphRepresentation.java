/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import exceptions.GeneticAlgorithmException;
import exceptions.NoPossibilityToCreateGraphException;
import exceptions.ProblemWithReadingGraphFromFileException;
import java.util.Collection;
import org.apache.commons.collections15.Factory;

public class GraphRepresentation {

    private static final Random rand = new Random(); // object that generates random numbers
    private Graph<Integer, String> graph; // graph
    private int searchedKCliqueSize; // size of K-Clique that we try to find in graph
    private Factory<Integer> vertexFactory; // vertex factory, for drawing
    private Factory<String> edgeFactory; // edge factory, for drawing
    private int nodeCount, edgeCount; // only for drawing, do not use

    /**
     * Constructor - creates random sparse graph with given parameters
     *
     * @param vertices - amount of vertices
     * @param edges - amount of edges
     * @param existedKCliqueSize - minimum k-clique size (amount of vertices)
     * that will exist in created graph
     * @throws NoPossibilityToCreateGraphException
     * @throws GeneticAlgorithmException
     */
    public GraphRepresentation(int vertices, int edges, int existedKCliqueSize) throws NoPossibilityToCreateGraphException, GeneticAlgorithmException {
        String problem;
        if ((problem = checkPossibilityOfCreationNewGraph(vertices, edges)) != null) {
            throw new NoPossibilityToCreateGraphException(problem);
        }
        if (existedKCliqueSize > vertices) {
            throw new NoPossibilityToCreateGraphException("It is impossible to create graph with size of " + vertices + " that contains k-clique with size of " + existedKCliqueSize);
        }
        if ((existedKCliqueSize * (existedKCliqueSize - 1) / 2) > edges) {
            throw new NoPossibilityToCreateGraphException("It is impossible to create graph with size of " + vertices + " that has " + edges + " egdes and contains k-clique with size of " + existedKCliqueSize);
        }
        if (existedKCliqueSize > vertices) {
            throw new NoPossibilityToCreateGraphException("It is impossible to create graph with size of " + vertices + " that has " + edges + " egdes and contains k-clique with size of " + existedKCliqueSize);
        }
        graph = createGraphVertices(vertices);
        LinkedList<Integer> verticesList = new LinkedList<>();
        for (int i = 1; i <= vertices; i++) {
            verticesList.add(i);
        }
        LinkedList<Integer> verticesList2 = new LinkedList<>();
        for (int i = 1; i <= existedKCliqueSize; i++) {
            int randV = rand.nextInt(verticesList.size());
            verticesList2.add(verticesList.get(randV));
            verticesList.remove(randV);
        }
        LinkedList<Edge> edgesList = new LinkedList<>();
        for (int i = 1; i <= existedKCliqueSize; i++) {
            for (int j = i + 1; j <= existedKCliqueSize; j++) {
                edgesList.add(new Edge(verticesList2.get(i - 1), verticesList2.get(j - 1)));
            }
        }
        int kCliqueEgdesAmount = existedKCliqueSize * (existedKCliqueSize - 1) / 2;
        fillGraphWithEdges(graph, edgesList, 0, kCliqueEgdesAmount);
        edgesList = new LinkedList<>();
        for (int i = 1; i <= vertices - existedKCliqueSize; i++) {
            for (int j = i + 1; j <= vertices - existedKCliqueSize; j++) {
                edgesList.add(new Edge(verticesList.get(i - 1), verticesList.get(j - 1)));
            }
        }
        for (int i = 1; i <= vertices - existedKCliqueSize; i++) {
            for (int j = 1; j <= existedKCliqueSize; j++) {
                edgesList.add(new Edge(verticesList.get(i - 1), verticesList2.get(j - 1)));
            }
        }
        fillGraphWithEdges(graph, edgesList, kCliqueEgdesAmount, edges);
    }

    /**
     * Constructor - creates random sparse graph with given parameters
     *
     * @param vertices - amount of vertices
     * @param edges - amount of edges
     * @throws NoPossibilityToCreateGraphException
     * @throws GeneticAlgorithmException
     */
    public GraphRepresentation(int vertices, int edges) throws NoPossibilityToCreateGraphException, GeneticAlgorithmException {
        String problem;
        if ((problem = checkPossibilityOfCreationNewGraph(vertices, edges)) != null) {
            throw new NoPossibilityToCreateGraphException(problem);
        }
        graph = createGraphVertices(vertices);
        LinkedList<Edge> edgesList = createListWithPossibleEdges(vertices);
        fillGraphWithEdges(graph, edgesList, 0, edges);
    }

    /**
     * Constructor used only for graph drawing.
     */
    public GraphRepresentation() {
        graph = new SparseGraph<>();
        nodeCount = 1;
        edgeCount = 1;
        vertexFactory = new Factory<Integer>() {
            @Override
            public Integer create() {
                return nodeCount++;
            }
        };
        edgeFactory = new Factory<String>() {
            @Override
            public String create() {
                return "EDGE" + edgeCount++;
            }
        };
    }

    /**
     * Checks if there is a possibility to create graph with given amount of
     * vertices and edges
     *
     * @param vertices - amount of vertices
     * @param edges - amount of edges
     * @return message with found problem or null if everything is ok
     */
    private String checkPossibilityOfCreationNewGraph(int vertices, int edges) {
        if (vertices < 1) {
            return "Amount of vertices cannot be less than 1";
        }
        if (edges < 0) {
            return "Amount of edges cannot be less than 0";
        }
        if (edges > (vertices * (vertices - 1) / 2)) {
            return "To many edges to generate graph";
        }
        return null;
    }

    /**
     * Constructor - reads graph from file
     *
     * @param filePath - path to file
     * @throws ProblemWithReadingGraphFromFileException
     * @throws NoPossibilityToCreateGraphException
     */
    public GraphRepresentation(String filePath) throws ProblemWithReadingGraphFromFileException, NoPossibilityToCreateGraphException {
        File file = new File(filePath);
        if (file.exists() == false) {
            throw new ProblemWithReadingGraphFromFileException("File " + file.getName() + " doesn't exist");
        } else if (file.isDirectory() == true) {
            throw new ProblemWithReadingGraphFromFileException("Given path points into folder instead of a file");
        } else if (file.isHidden() == true) {
            throw new ProblemWithReadingGraphFromFileException("File " + file.getName() + " is hidden");
        } else if (file.canRead() == false) {
            throw new ProblemWithReadingGraphFromFileException("Cannot read from file - permission denied");
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int verticesAmount = Integer.parseInt(bufferedReader.readLine());
            if (verticesAmount < 1) {
                throw new NoPossibilityToCreateGraphException("Amount of vertices cannot be less than 1");
            }
            graph = createGraphVertices(verticesAmount);
            int edgesAmount = Integer.parseInt(bufferedReader.readLine());
            if (edgesAmount < 0) {
                throw new NoPossibilityToCreateGraphException("Amount of edges cannot be less than 0");
            }
            if (edgesAmount > (verticesAmount * (verticesAmount - 1) / 2)) {
                throw new NoPossibilityToCreateGraphException("To many edges to generate graph");
            }
            for (int i = 1; i <= edgesAmount; i++) {
                String line = bufferedReader.readLine();
                String[] splitted = line.split(" ");
                int firstVertex = Integer.parseInt(splitted[0]);
                int secondVertex = Integer.parseInt(splitted[1]);
                if (firstVertex < 1 || secondVertex < 1 || firstVertex > verticesAmount || secondVertex > verticesAmount) {
                    throw new ProblemWithReadingGraphFromFileException("It is impossible to creat graph with given vertices and edges");
                }
                if (firstVertex >= secondVertex) {
                    throw new ProblemWithReadingGraphFromFileException("File format is wrong");
                }
                graph.addEdge("EDGE" + i, Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
            }
        } catch (NumberFormatException e) {
            throw new ProblemWithReadingGraphFromFileException("File format is wrong");
        } catch (FileNotFoundException e) {
            throw new ProblemWithReadingGraphFromFileException("File not found");
        } catch (IOException e) {
            throw new ProblemWithReadingGraphFromFileException("For some reason cannot read graph from file");
        }
    }

    /**
     * Writes graph to file
     *
     * @param path - path
     * @param fileName - name of file
     * @throws ProblemWithReadingGraphFromFileException
     * @throws GeneticAlgorithmException
     */
    public void writeGraphToFile(String path, String fileName) throws ProblemWithReadingGraphFromFileException, GeneticAlgorithmException {
        File file = new File(path);
        if (file.isDirectory() == false) {
            throw new ProblemWithReadingGraphFromFileException("Given path doesn't point into folder");
        }
        if (file.canWrite() == false) {
            throw new ProblemWithReadingGraphFromFileException("Cannot write to this folder - permission denied");
        }
        if (graph == null) {
            throw new GeneticAlgorithmException("There is no graph to write to file");
        }
        try (FileWriter fileWriter = new FileWriter(path + File.separatorChar + fileName)) {
            fileWriter.write(graph.getVertexCount() + "\n");
            fileWriter.write(graph.getEdgeCount() + "\n");
            for (int i = 1; i <= graph.getEdgeCount(); i++) {
                Pair<Integer> vertices = graph.getEndpoints("EDGE" + i);
                fileWriter.write(vertices.getFirst() + " ");
                fileWriter.write(vertices.getSecond() + "\n");
            }
        } catch (IOException e) {
            throw new ProblemWithReadingGraphFromFileException("For some reason cannot write graph to file\nChange file name!");
        }
    }

    /**
     * Removes edge if it's source and destination is the same vertex.
     */
    public boolean removeSingleVertexLoopback() {
        boolean removed = false;
        for (String e : graph.getEdges()) {
            int temp = 0;
            boolean first = true;
            for (int i : graph.getEndpoints(e)) {
                if (first) {
                    temp = i;
                    first = false;
                } else if (temp == i) {
                    graph.removeEdge(e);
                    removed = true;
                }
            }
        }
        return removed;
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
    public int getsearchedKCliqueSize() {
        return searchedKCliqueSize;
    }

    /**
     * Getter
     *
     * @return vertex factory
     */
    public Factory<Integer> getVertexFactory() {
        return vertexFactory;
    }

    /**
     * Getter
     *
     * @return edge factory
     */
    public Factory<String> getEdgeFactory() {
        return edgeFactory;
    }

    /**
     * Setter
     *
     * @param searchedKCliqueSize
     */
    public void setsearchedKCliqueSize(int searchedKCliqueSize) {
        this.searchedKCliqueSize = searchedKCliqueSize;
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
     * Getter
     *
     * @return graph edges amount
     */
    public int getEdgeCount() {
        return graph.getEdgeCount();
    }

    /**
     * Checks if first and second vertices are connected by edge
     *
     * @param firstVertex - first vertex
     * @param secondVertex - second vertex
     * @return true if between vertices is edge, false otherwise
     */
    public boolean isNeighbor(int firstVertex, int secondVertex) {
        return graph.isNeighbor(firstVertex, secondVertex);
    }

    /**
     * Creates graph with verticesAmount vertices (without any edge)
     *
     * @param verticesAmount - amount of vertices
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
     * @param graph - graph
     * @param possibleEdges - list of edges that could be add
     * @param existedEdgesAmount - amount of edges that already exists in graph
     * @param demandedEdgesAmount - amount of edges that should be in graph
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
     * @param verticesAmount - amount of vertices
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

    /**
     * Repairs graph if any vertex was removed while drawing. If user removed
     * vertex number 3 (so left f.e. 1,2,4,5), function relabels it to 1,2,3,4.
     */
    public void repairGraphAfterEditing() {
        LinkedList<Integer> vertices = new LinkedList<>(graph.getVertices());
        int last = vertices.get(vertices.size() - 1);
        if (last > vertices.size()) {
            int accumulator = 0;
            for (int j = 0; j < vertices.size() - 1; j++) {
                if (vertices.get(j) != j + 1) {
                    int temp = vertices.get(vertices.size() - 1 - j + accumulator);
                    changeEdge(temp, j + 1);
                    graph.removeVertex(temp);
                    graph.addVertex(j + 1);
                    vertices.remove(vertices.size() - 1 - j);
                    vertices.add(j, j + 1);
                    accumulator++;
                }
            }
        }
    }

    /**
     * Removes edges containig toReplace vertex and inserts same with replacing
     * vertex
     *
     * @param toReplace - number of vertex to replace
     * @param replacing - number of vertex to insert instead of toReplace
     */
    private void changeEdge(int toReplace, int replacing) {
        Collection<String> edges = graph.getEdges();
        for (String e : edges) {
            Pair<Integer> endpoints = graph.getEndpoints(e);
            if (endpoints.getFirst() == toReplace) {
                graph.removeEdge(e);
                graph.addEdge(e, replacing, endpoints.getSecond());
            } else if (endpoints.getSecond() == toReplace) {
                graph.removeEdge(e);
                graph.addEdge(e, replacing, endpoints.getFirst());
            }
        }
    }
}
