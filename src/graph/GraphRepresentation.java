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

/**
 * @author Krzysztof Spytkowski
 * @date 16th March 2014
 */
public class GraphRepresentation {

    private static final Random rand = new Random(); // object that generates random numbers
    private Graph<Integer, String> graph; // graph
    private int searchedKCliqueSize; // size of K-Clique that we try to find in graph

    /**
     * Constructor - creates random sparse graph with given parameters
     * 
     * @param vertices
     *            - amount of vertices
     * @param edges
     *            - amount of edges
     * @param existedKCliqueSize
     *            - minimum k-clique size (amount of vertices) that will exist in created graph
     * @throws NoPossibilityToCreateGraphException
     * @throws GeneticAlgorithmException
     */
    public GraphRepresentation(int vertices, int edges, int existedKCliqueSize) throws NoPossibilityToCreateGraphException, GeneticAlgorithmException {
        String problem;
        if ((problem = checkPossibilityOfCreationNewGraph(vertices, edges)) != null) {
            throw new NoPossibilityToCreateGraphException(problem);
        }
        //if (searchedKCliqueSize > vertices) {
        //    throw new GeneticAlgorithmException("It is impossible to find k-clique with size of " + searchedKCliqueSize + " in graph that has size of " + vertices);
        //}
        if (existedKCliqueSize > vertices) {
            throw new NoPossibilityToCreateGraphException("It is impossible to create graph with size of " + vertices + " that contains k-clique with size of " + existedKCliqueSize);
        }
        //this.searchedKCliqueSize = searchedKCliqueSize;
        graph = createGraphVertices(vertices);
        LinkedList<Edge> edgesList = createListWithPossibleEdges(existedKCliqueSize);
        int kCliqueEgdesAmount = existedKCliqueSize * (existedKCliqueSize - 1) / 2;
        fillGraphWithEdges(graph, edgesList, 0, kCliqueEgdesAmount);
        edgesList = new LinkedList<>();
        for (int i = 1; i <= vertices; i++) {
            for (int j = existedKCliqueSize + 1; j <= vertices; j++) {
                if (i != j && i < j) {
                    edgesList.add(new Edge(i, j));
                }
            }
        }
        fillGraphWithEdges(graph, edgesList, kCliqueEgdesAmount, edges);
    }

    /**
     * Constructor - creates random sparse graph with given parameters
     * 
     * @param vertices
     *            - amount of vertices
     * @param edges
     *            - amount of edges
     * @throws NoPossibilityToCreateGraphException
     * @throws GeneticAlgorithmException
     */
    public GraphRepresentation(int vertices, int edges) throws NoPossibilityToCreateGraphException, GeneticAlgorithmException {
        String problem;
        if ((problem = checkPossibilityOfCreationNewGraph(vertices, edges)) != null) {
            throw new NoPossibilityToCreateGraphException(problem);
        }
        //if (searchedKCliqueSize > vertices) {
        //    throw new GeneticAlgorithmException("It is impossible to find k-clique with size of " + searchedKCliqueSize + " in graph that has size of " + vertices);
        //}
        //this.searchedKCliqueSize = searchedKCliqueSize;
        graph = createGraphVertices(vertices);
        LinkedList<Edge> edgesList = createListWithPossibleEdges(vertices);
        fillGraphWithEdges(graph, edgesList, 0, edges);
    }

    /**
     * Checks if there is a possibility to create graph with given amount of vertices and edges
     * 
     * @param vertices
     *            - amount of vertices
     * @param edges
     *            - amount of edges
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

    // /**
    // * Constructor - creates random sparse graph with given parameters
    // *
    // * @param vertices
    // * - amount of vertices
    // * @param edges
    // * - amount of edges
    // * @param kCliqueSize
    // * - k-clique size (amount of vertices)
    // * @param shouldBeKClique
    // * - says if creating graph has to contain k-clique with kCliqueSize
    // * @throws NoPossibilityToCreateGraphException
    // */
    // public GraphRepresentation(int vertices, int edges, int kCliqueSize, boolean shouldBeKClique) throws NoPossibilityToCreateGraphException {
    // if (vertices < 1) {
    // throw new NoPossibilityToCreateGraphException("Amount of vertices cannot be less than 1");
    // }
    // if (edges < 0) {
    // throw new NoPossibilityToCreateGraphException("Amount of edges cannot be less than 0");
    // }
    // if (edges > (vertices * (vertices - 1) / 2)) {
    // throw new NoPossibilityToCreateGraphException("To many edges to generate graph");
    // }
    // this.searchedKCliqueSize = kCliqueSize;
    // graph = createGraphVertices(vertices);
    // if (shouldBeKClique) {
    // LinkedList<Edge> edgesList = createListWithPossibleEdges(kCliqueSize);
    // int kCliqueEgdesAmount = kCliqueSize * (kCliqueSize - 1) / 2;
    // fillGraphWithEdges(graph, edgesList, 0, kCliqueEgdesAmount);
    // edgesList = new LinkedList<>();
    // for (int i = 1; i <= vertices; i++) {
    // for (int j = kCliqueSize + 1; j <= vertices; j++) {
    // if (i != j && i < j) {
    // edgesList.add(new Edge(i, j));
    // }
    // }
    // }
    // fillGraphWithEdges(graph, edgesList, kCliqueEgdesAmount, edges);
    // } else {
    // LinkedList<Edge> edgesList = createListWithPossibleEdges(vertices);
    // fillGraphWithEdges(graph, edgesList, 0, edges);
    // }
    // }

    /**
     * Constructor - reads graph from file
     * 
     * @param filePath
     *            - path to file
     * @throws ProblemWithReadingGraphFromFileException
     * @throws NoPossibilityToCreateGraphException
     */
    public GraphRepresentation(String filePath) throws ProblemWithReadingGraphFromFileException, NoPossibilityToCreateGraphException {
        //this.searchedKCliqueSize = searchedKCliqueSize;
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
            // code unreachable?! sprawdzilem wyzej ze istnieje
        } catch (IOException e) {
            throw new ProblemWithReadingGraphFromFileException("For some reason cannot read graph from file");
        }
    }

    /**
     * Writes graph to file
     * 
     * @param path
     *            - path
     * @param fileName
     *            - name of file
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
            throw new ProblemWithReadingGraphFromFileException("For some reason cannot write graph to file");
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
    public int getsearchedKCliqueSize() {
        return searchedKCliqueSize;
    }
    
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
